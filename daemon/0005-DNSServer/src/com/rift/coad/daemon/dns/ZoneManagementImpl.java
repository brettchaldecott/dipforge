/*
 * DNSServer: The implementation of the zone management object.
 * Copyright (C) 2008  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * DNSManagement.java
 */

// package path
package com.rift.coad.daemon.dns;

// java imports
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.daemon.dns.server.ServerZone;

// dns imports
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Zone;
import org.xbill.DNS.Name;
import org.xbill.DNS.DClass;
import org.xbill.DNS.RRset;


/**
 * This object is responsible for managing a zone object.
 *
 * @author brett chaldecott
 */
public class ZoneManagementImpl implements ZoneManagement,ResourceIndex,
        Resource {
    
    // singleton object
    private static Logger log = Logger.getLogger(ZoneManagementImpl.class);
    
    // private member variable
    private ServerZone serverZone = null;
    
    
    /**
     * The constructor of the zone management object.
     *
     * @param serverZone The object responsible for managing the zone.
     */
    public ZoneManagementImpl(ServerZone serverZone) {
        this.serverZone = serverZone;
    }
    
    /**
     * The constructor of the zone management object.
     *
     * @param serverZone The object responsible for managing the zone.
     * @param soa The soa of the new zone.
     * @param records The list of records.
     */
    public ZoneManagementImpl(ServerZone serverZone, SOARecord soa,
            List records) throws DNSException {
        this.serverZone = serverZone;
        try {
            org.xbill.DNS.Record[] dnsRecords = new org.xbill.DNS.Record[
                    records.size() + 1];
            Name zname = Name.fromString(serverZone.getZoneName(), Name.root);
            Name hostname = Name.fromString(soa.getHost(), Name.root);
            Name admin = Name.fromString(soa.getAdmin(), Name.root);
            
            dnsRecords[0] = new org.xbill.DNS.SOARecord(zname, DClass.IN,
                    soa.getTTL(), hostname, admin, soa.getSerial(),
                    soa.getRefresh(), soa.getRetry(), soa.getExpiry(),
                    soa.getMinimum());
            
            // add all the records to the zone
            for (int index = 0; index < records.size(); index++) {
                DNSRecord record = (DNSRecord)records.get(index);
                Name recordName = Name.fromString(record.getPrefix());
                int type = org.xbill.DNS.Type.value(record.getType());
                dnsRecords[index + 1] = org.xbill.DNS.Record.fromString(
                        recordName,type, DClass.IN,record.getTtl(),
                        record.getSuffix(),zname);
            }
            
            // populate the zone
            serverZone.setZone(new Zone(zname,dnsRecords));
            serverZone.persistZone();
        } catch (Exception ex) {
            log.error("Failed to populate the zone : " +
                    ex.getMessage(),ex);
            throw new DNSException("Failed to populate the zone : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This flag will return true if this zone is a primary.
     */
    public boolean isPrimary() {
        return serverZone.getConfig().getPrimary();
    }
    
    
    /**
     * This method returns the name of the zone.
     *
     * @return The string containing the zone name.
     */
    public String getName() {
        return serverZone.getZoneName();
    }
    
    
    /**
     * This method gets the host for the zone
     *
     * @return The string containing the host information.
     * @throws RemoteException
     */
    public SOARecord getSOA() throws DNSException {
        try {
            Zone zone = serverZone.getZone();
            if (zone == null) {
                return null;
            }
            org.xbill.DNS.SOARecord zoneSOARecord = zone.getSOA();
            return new SOARecord(zoneSOARecord.getHost().toString(),
                    zoneSOARecord.getTTL(),
                    zoneSOARecord.getAdmin().toString(), zoneSOARecord.getSerial(),
                    zoneSOARecord.getRefresh(),zoneSOARecord.getRetry(),
                    zoneSOARecord.getExpire(), zoneSOARecord.getMinimum());
        } catch (Exception ex) {
            log.error("Failed to retrieve the soa : " + ex.getMessage(),ex);
            throw new DNSException(
                    "Failed to retrieve the soa : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the soa record.
     *
     * @param record The soa record.
     * @throws RemoteException
     */
    public void updateSOA(SOARecord record) throws DNSException {
        try {
            if (!serverZone.getConfig().getPrimary()) {
                log.info("Attempting to modify a secondary zone.");
                throw new DNSException(
                        "Attempting to modify a secondary zone.");
            }
            Zone zone = serverZone.getZone();
            if (zone == null) {
                throw new DNSException(
                        "There are no zone contents  to update");
            }
            org.xbill.DNS.SOARecord zoneSOARecord = zone.getSOA();
            Iterator iter = zone.iterator();
            List newRecords = new ArrayList();
            Name zname = Name.fromString(serverZone.getZoneName(), Name.root);
            Name hostname = Name.fromString(record.getHost(), Name.root);
            Name admin = Name.fromString(record.getAdmin(), Name.root);
            
            for (; iter.hasNext();) {
                RRset rrset = (RRset)iter.next();
                Iterator rrs =  rrset.rrs();
                for (; rrs.hasNext();) {
                    Record currentRecord = (Record)rrs.next();
                    if (currentRecord == zoneSOARecord) {
                        newRecords.add(new org.xbill.DNS.SOARecord(zname, DClass.IN,
                                record.getTTL(), hostname, admin, record.getSerial(),
                                record.getRefresh(), record.getRetry(), record.getExpiry(),
                                record.getMinimum()));
                    } else {
                        newRecords.add(currentRecord);
                    }
                }
            }
            
            // populate the zone
            serverZone.setZone(new Zone(zname,(Record[])newRecords.toArray(
                    new Record[0])));
            serverZone.persistZone();
        } catch (DNSException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the soa : " + ex.getMessage(),ex);
            throw new DNSException(
                    "Failed to retrieve the soa : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the list of records for the dns server.
     *
     * @return The list of records for this zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public List getRecords() throws DNSException {
        try {
            
            Zone zone = serverZone.getZone();
            if (zone == null) {
                throw new DNSException(
                        "There is no zone information.");
            }
            Iterator iter = zone.iterator();
            List records = new ArrayList();
            
            for (; iter.hasNext();) {
                RRset rrset = (RRset)iter.next();
                Iterator rrs =  rrset.rrs();
                for (; rrs.hasNext();) {
                    Record currentRecord = (Record)rrs.next();
                    if (currentRecord == zone.getSOA()) {
                        continue;
                    } else {
                        records.add(new DNSRecord(currentRecord.getName().toString(),
                                currentRecord.getTTL(), org.xbill.DNS.Type.string(
                                currentRecord.getType()),
                                currentRecord.rdataToString()));
                    }
                }
            }
            
            return records;
        } catch (DNSException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the soa : " + ex.getMessage(),ex);
            throw new DNSException(
                    "Failed to retrieve the soa : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for updating the records in the dns server.
     *
     * @param records The records to update.
     * @throws DNSException
     * @throws RemoteException
     */
    public void updateRecords(List records) throws DNSException {
        try {
            if (!serverZone.getConfig().getPrimary()) {
                log.info("Attempting to modify a secondary zone.");
                throw new DNSException(
                        "Attempting to modify a secondary zone.");
            }
            
            Zone zone = serverZone.getZone();
            if (zone == null) {
                throw new DNSException(
                        "There is no zone information to update.");
            }
            
            
            org.xbill.DNS.Record[] dnsRecords = new org.xbill.DNS.Record[
                    records.size() + 1];
            Name zname = Name.fromString(serverZone.getZoneName(), Name.root);
            dnsRecords[0] = zone.getSOA();
            
            // add all the records to the zone
            for (int index = 0; index < records.size(); index++) {
                DNSRecord record = (DNSRecord)records.get(index);
                Name recordName = Name.fromString(record.getPrefix());
                int type = org.xbill.DNS.Type.value(record.getType());
                dnsRecords[index + 1] = org.xbill.DNS.Record.fromString(
                        recordName,type, DClass.IN,record.getTtl(),
                        record.getSuffix(),zname);
            }
            
            // populate the zone
            serverZone.setZone(new Zone(zname,dnsRecords));
            serverZone.persistZone();
        } catch (DNSException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to populate the zone : " +
                    ex.getMessage(),ex);
            throw new DNSException("Failed to populate the zone : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the primary key for the zone management object.
     */
    public Object getPrimaryKey() {
        return serverZone.getZoneName();
    }
    
    
    /**
     * This method returns the resource name.
     */
    public String getResourceName() {
        return this.getClass().getName();
    }
    
    
    /**
     * Release all resources for this object.
     */
    public void releaseResource() {
    }
}
