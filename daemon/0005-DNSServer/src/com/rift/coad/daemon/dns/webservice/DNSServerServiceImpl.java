/*
 * DNSServer: The DNS server web service.
 * Copyright (C) 2008  Rift IT Contracting
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
 * DNSServerMBean.java
 */


// package path
package com.rift.coad.daemon.dns.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.dns.server.Server;


/**
 * This is the management interface for the dns server.
 *
 * @author brett chaldecott
 */
public class DNSServerServiceImpl implements DNSServerService {
    
    // private static member variables
    private static Logger log = Logger.getLogger(DNSServerServiceImpl.class);
    
    /**
     * The constructor of the dns server
     */
    public DNSServerServiceImpl() {
        
    }
    
    
    /**
     * This method returns the version of the dns server.
     *
     * @return The string contain the version information.
     */
    public String getVersion() {
        return "1";
    }
    
    
    /**
     * This method returns the name ofe the dns server.
     *
     * @return The string containing the name of the dns server.
     */
    public String getName() {
        return "JavaDNSServer";
    }
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     */
    public String getDescription() {
        return "Java DNS Server";
    }
    
    
    /**
     * This method returns the string containing the status of the dns server.
     *
     * @return The string containing the status of the dns server.
     */
    public String getStatus() throws DNSException {
        try {
            return Server.getInstance().getStatus();
        } catch (Exception ex) {
            log.error("Failed to retrieve the server status : " +
                    ex.getMessage(),ex);
            throwDNSException(
                    "Failed to retrieve the server status : " +
                    ex.getMessage(),ex);
            // this is because the parser will not compile without it
            // it will not ever get reached.
            return null;
        }
    }
    
    
    /**
     * This method lists the zones managed by this server.
     *
     * @return The list of zones.
     * @param type The type of zones [1: Primary, 2: Secondary, 3: All]
     * @exception DNSException
     */
    public String[] listZones(int type) throws DNSException {
        try {
            List<String> results = new ArrayList<String>();
            results.addAll(Server.getInstance().listZones(type));
            return results.toArray(new String[0]);
        } catch (Exception ex) {
            log.error(
                    "Failed to list the zones because : " 
                    + ex.getMessage(),ex);
            throwDNSException(
                    "Failed to list the zones because : " 
                    + ex.getMessage(),ex);
            // this is because the parser will not compile without it
            // it will not ever get reached.
            return null;
        }
    }
    
    
    /**
     * This method is responsible for creating a zone in the dns server
     *
     * @param zoneName The name of the new zone to create.
     * @param zone The zone contents.
     * @exception DNSException
     */
    public void createZone(String zoneName, String zone) throws DNSException {
        try {
            Server.getInstance().createZone(zoneName,zone);
        } catch (Exception ex) {
            log.error(
                    "Failed to create the zone [" + zoneName + "] because : " 
                    + ex.getMessage(),ex);
            throwDNSException(
                    "Failed to create the zone [" + zoneName + "] because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a secondary zone to the server.
     *
     * @param zoneName The name of the zone to create a secondary for.
     * @param remote The remote point for the name server to retrieve the zone
     * form.
     *
     * @throws DNSException
     */
    public void createSecondaryZone(String zoneName, String remote) throws
            DNSException {
        try {
            Server.getInstance().createSecondaryZone(zoneName,remote);
        } catch (Exception ex) {
            log.error(
                    "Failed to create the secondary zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throwDNSException(
                    "Failed to create the secondary zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for getting a zone from the dns server
     *
     * @param zone The zone contents.
     * @param zoneName The name of the zone to retrieve.
     * @exception DNSException
     */
    public String getZone(String zoneName) throws DNSException {
        try {
            return Server.getInstance().getZone(zoneName);
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throwDNSException(
                    "Failed to retrieve the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            // this is because the parser will not compile without it
            // it will not ever get reached.
            return null;
        }
    }
    
    
    /**
     * This method is responsible for updating a zone in the dns server
     *
     * @param zoneName The name of the zone to update.
     * @param zone The zone contents.
     * @exception DNSException
     */
    public void updateZone(String zoneName, String zone) throws DNSException {
        try {
            Server.getInstance().updateZone(zoneName,zone);
        } catch (Exception ex) {
            log.error(
                    "Failed to update the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throwDNSException (
                    "Failed to update the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for removing a zone from the dns server
     *
     * @param zoneName The name of the zone to remove.
     * @exception DNSException
     */
    public void removeZone(String zoneName) throws DNSException {
        try {
            Server.getInstance().removeZone(zoneName);
        } catch (Exception ex) {
            log.error(
                    "Failed to remove the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throwDNSException (
                    "Failed to remove the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @exception DNSException
     */
    private void throwDNSException(String message, Throwable ex) throws
            DNSException {
        DNSException exception = new DNSException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        throw exception;
    }
    
}
