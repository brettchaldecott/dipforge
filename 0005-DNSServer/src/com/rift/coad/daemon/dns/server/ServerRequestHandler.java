/*
 * DNSServer: The dns server implementation.
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
 * ServerConfig.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// java imports
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.Iterator;

// log4j imports
import org.apache.log4j.Logger;
import org.xbill.DNS.CNAMERecord;

/// dns imports
import org.xbill.DNS.DClass;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.Header;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Address;
import org.xbill.DNS.NameTooLongException;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.SetResponse;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.RRset;
import org.xbill.DNS.TSIGRecord;
import org.xbill.DNS.Zone;
import org.xbill.DNS.Cache;
import org.xbill.DNS.Section;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Opcode;
import org.xbill.DNS.OPTRecord;
import org.xbill.DNS.ExtendedFlags;
import org.xbill.DNS.Type;
import org.xbill.DNS.Credibility;

/**
 * This is the server request handler.
 *
 * @author brett chaldecott
 */
public class ServerRequestHandler {
    
    // class constants
    public static final int FLAG_DNSSECOK = 1;
    public static final int FLAG_SIGONLY = 2;
    
    
    // private member variables
    private Logger log = Logger.getLogger(ServerRequestHandler.class);
    private ServerConfig config = null;
    private ServerZoneMap zoneMap = null;
    private ServerLookup lookup = null;
    
    /**
     * Creates a new instance of ServerRequestHandler
     */
    public ServerRequestHandler(ServerConfig config,ServerZoneMap zoneMap,
            ServerLookup lookup)
    throws ServerException {
        this.config = config;
        this.zoneMap = zoneMap;
        this.lookup = lookup;
    }
    
    
    /**
     * This method finds the exact record set match
     *
     * @return The record set that contains the result or NULL.
     * @param name The name of the record to search for.
     * @param type The type of record to perform the search for.
     * @param dclass The type of class to look for
     */
    public RRset findExactMatch(Name name, int type, int dclass, boolean glue) {
        ServerZone serverZone = zoneMap.findBestZone(name);
        if (serverZone == null) {
            RRset [] rrsets = lookup.findRecords(name, type, dclass,
                    glue);
            if (rrsets == null) {
                return null;
            } else {
                return rrsets[0]; /* not quite right */
            }
        } else if (serverZone.getZone() != null) {
            return serverZone.getZone().findExactMatch(name, type);
        } else {
            return null;
        }
    }
    
    
    /**
     * This method adds the SOA to the response message.
     *
     * @param response The response to add the zone soa to.
     * @param zone The zone to retrieve the SOA from.
     */
    private final void addSOA(Message response, Zone zone) {
        response.addRecord(zone.getSOA(), Section.AUTHORITY);
    }
    
    
    /**
     * This method adds the name server information to the response.
     *
     * @param response The message containing the dns response.
     * @param zone The zone containing the information.
     * @param flags The flags to retrieve.
     */
    private final void addNS(Message response, Zone zone, int flags) {
        RRset nsRecords = zone.getNS();
        addRRset(nsRecords.getName(), response, nsRecords,
                Section.AUTHORITY, flags);
    }
    
    
    /**
     * This method adds name server information pulled from cacue or a lookup.
     *
     * @param response The response message to add the records too.
     * @param name The name of the record to look for.
     */
    private final void addCacheNS(Message response, int dclass, Name name) {
        SetResponse sr = this.lookup.lookupRecords(name, Type.NS, 
                dclass, Credibility.HINT);
        if (!sr.isDelegation())
            return;
        RRset nsRecords = sr.getNS();
        Iterator it = nsRecords.rrs();
        while (it.hasNext()) {
            Record r = (Record) it.next();
            response.addRecord(r, Section.AUTHORITY);
        }
    }
    
    
    /**
     * This method adds the glue information to the search message result.
     *
     * @param response The response to perform the lookup on.
     * @param name The name of the lookup.
     * @param flags The flags to perform the search on.
     */
    private void addGlue(Message response, Name name, int flags) {
        RRset a = findExactMatch(name, Type.A, DClass.IN, true);
        if (a == null)
            return;
        addRRset(name, response, a, Section.ADDITIONAL, flags);
    }
    
    
    /**
     * This method addes extra information to the response.
     *
     * @param response The message to add the response information to.
     * @param section The section to add the messages to.
     * @param flags The flags to perform the search for.
     */
    private void addAdditional2(Message response, int section, int flags) {
        Record [] records = response.getSectionArray(section);
        for (int i = 0; i < records.length; i++) {
            Record r = records[i];
            Name glueName = r.getAdditionalName();
            if (glueName != null)
                addGlue(response, glueName, flags);
        }
    }
    
    
    /**
     * This method combines the search results.
     *
     * @param response The response to perform the lookup for.
     * @param flags The flags to look for.
     */
    private final void addAdditional(Message response, int flags) {
        addAdditional2(response, Section.ANSWER, flags);
        addAdditional2(response, Section.AUTHORITY, flags);
    }
    
    
    
    /**
     * This method returns a code the indicates the status of the response and
     * populates the response properly.
     *
     * @return The respnse code.
     * @param response The message containing the response information.
     * @param name The name of the record to perform the lookup for.
     * @param type The type of record.
     * @param dclass The class of record.
     * @param iterations The number of iterations.
     * @param flags The flags.
     */
    private byte addAnswer(Message response, Name name, int type, int dclass,
            int iterations, int flags) {
        SetResponse sr;
        byte rcode = Rcode.NOERROR;
        
        if (iterations > 6)
            return Rcode.NOERROR;
        
        if (type == Type.SIG || type == Type.RRSIG) {
            type = Type.ANY;
            flags |= FLAG_SIGONLY;
        }
        
        ServerZone serverZone = zoneMap.findBestZone(name);
        Zone zone = null;
        if ((serverZone != null) && (serverZone.getZone() != null)) {
            zone = serverZone.getZone();
            sr = zone.findRecords(name, type);
        }
        else {
            sr = lookup.lookupRecords(name, type, 
                    dclass, Credibility.NORMAL);
        }
        
        if (sr.isUnknown()) {
            addCacheNS(response, dclass, name);
        }
        if (sr.isNXDOMAIN()) {
            response.getHeader().setRcode(Rcode.NXDOMAIN);
            if (zone != null) {
                addSOA(response, zone);
                if (iterations == 0)
                    response.getHeader().setFlag(Flags.AA);
            }
            rcode = Rcode.NXDOMAIN;
        } else if (sr.isNXRRSET()) {
            if (zone != null) {
                addSOA(response, zone);
                if (iterations == 0)
                    response.getHeader().setFlag(Flags.AA);
            }
        } else if (sr.isDelegation()) {
            RRset nsRecords = sr.getNS();
            addRRset(nsRecords.getName(), response, nsRecords,
                    Section.AUTHORITY, flags);
        } else if (sr.isCNAME()) {
            CNAMERecord cname = sr.getCNAME();
            RRset rrset = new RRset(cname);
            addRRset(name, response, rrset, Section.ANSWER, flags);
            if (zone != null && iterations == 0)
                response.getHeader().setFlag(Flags.AA);
            rcode = addAnswer(response, cname.getTarget(),
                    type, dclass, iterations + 1, flags);
        } else if (sr.isDNAME()) {
            DNAMERecord dname = sr.getDNAME();
            RRset rrset = new RRset(dname);
            addRRset(name, response, rrset, Section.ANSWER, flags);
            Name newname;
            try {
                newname = name.fromDNAME(dname);
            } catch (NameTooLongException e) {
                return Rcode.YXDOMAIN;
            }
            rrset = new RRset(new CNAMERecord(name, dclass, 0, newname));
            addRRset(name, response, rrset, Section.ANSWER, flags);
            if (zone != null && iterations == 0)
                response.getHeader().setFlag(Flags.AA);
            rcode = addAnswer(response, newname, type, dclass,
                    iterations + 1, flags);
        } else if (sr.isSuccessful()) {
            RRset [] rrsets = sr.answers();
            for (int i = 0; i < rrsets.length; i++)
                addRRset(name, response, rrsets[i],
                        Section.ANSWER, flags);
            if (zone != null) {
                addNS(response, zone, flags);
                if (iterations == 0)
                    response.getHeader().setFlag(Flags.AA);
            } else
                addCacheNS(response, dclass, name);
        }
        return rcode;
    }
    
    
    /**
     * This method adds the records to the set.
     *
     * @param name The name of the record to add.
     * @param response The response to add.
     * @param rrset The record set.
     * @param section The section.
     * @param flags The flags.
     */
    private void addRRset(Name name, Message response, RRset rrset, int section,
            int flags) {
        for (int s = 1; s <= section; s++)
            if (response.findRRset(name, rrset.getType(), s))
                return;
        if ((flags & FLAG_SIGONLY) == 0) {
            Iterator it = rrset.rrs();
            while (it.hasNext()) {
                Record r = (Record) it.next();
                if (r.getName().isWild() && !name.isWild())
                    r = r.withName(name);
                response.addRecord(r, section);
            }
        }
        if ((flags & (FLAG_SIGONLY | FLAG_DNSSECOK)) != 0) {
            Iterator it = rrset.sigs();
            while (it.hasNext()) {
                Record r = (Record) it.next();
                if (r.getName().isWild() && !name.isWild())
                    r = r.withName(name);
                response.addRecord(r, section);
            }
        }
    }
    
    
    /**
     * This method performs the transfer request on behalf of the caller.
     */
    private byte [] doAXFR(Name name, Message query, TSIG tsig,
            TSIGRecord qtsig, Socket s) {
        ServerZone serverZone = zoneMap.getZone(name);
        if ((null == serverZone) || (serverZone.getZone() == null)) {
            return errorMessage(query, Rcode.REFUSED);
        }
        Zone zone = serverZone.getZone();
        boolean first = true;
        Iterator it = zone.AXFR();
        try {
            DataOutputStream dataOut;
            dataOut = new DataOutputStream(s.getOutputStream());
            int id = query.getHeader().getID();
            while (it.hasNext()) {
                RRset rrset = (RRset) it.next();
                Message response = new Message(id);
                Header header = response.getHeader();
                header.setFlag(Flags.QR);
                header.setFlag(Flags.AA);
                addRRset(rrset.getName(), response, rrset,
                        Section.ANSWER, FLAG_DNSSECOK);
                if (tsig != null) {
                    tsig.applyStream(response, qtsig, first);
                    qtsig = response.getTSIG();
                }
                first = false;
                byte [] out = response.toWire();
                dataOut.writeShort(out.length);
                dataOut.write(out);
            }
        } catch (IOException ex) {
            log.error("Transfer failed : " + ex.getMessage(),ex);
        }
        try {
            s.close();
        } catch (IOException ex) {
        }
        return null;
    }
    
    /*
     * Note: a null return value means that the caller doesn't need to do
     * anything.  Currently this only happens if this is an AXFR request over
     * TCP.
     */
    public byte [] generateReply(Message query, byte [] in, int length, Socket s)
    throws IOException {
        Header header;
        boolean badversion;
        int maxLength;
        boolean sigonly;
        SetResponse sr;
        int flags = 0;
        header = query.getHeader();
        if (header.getFlag(Flags.QR))
            return null;
        if (header.getRcode() != Rcode.NOERROR)
            return errorMessage(query, Rcode.FORMERR);
        if (header.getOpcode() != Opcode.QUERY)
            return errorMessage(query, Rcode.NOTIMP);
        
        Record queryRecord = query.getQuestion();
        
        TSIGRecord queryTSIG = query.getTSIG();
        TSIG tsig = null;
        if (queryTSIG != null) {
            tsig = (TSIG) config.getTSIGs().get(queryTSIG.getName());
            if (tsig == null ||
                    tsig.verify(query, in, length, null) != Rcode.NOERROR)
                return formErrMessage(in);
        }
        
        OPTRecord queryOPT = query.getOPT();
        if (queryOPT != null && queryOPT.getVersion() > 0)
            badversion = true;
        
        if (s != null)
            maxLength = 65535;
        else if (queryOPT != null)
            maxLength = Math.max(queryOPT.getPayloadSize(), 512);
        else
            maxLength = 512;
        
        if (queryOPT != null && (queryOPT.getFlags() & ExtendedFlags.DO) != 0)
            flags = FLAG_DNSSECOK;
        
        Message response = new Message(query.getHeader().getID());
        response.getHeader().setFlag(Flags.QR);
        if (query.getHeader().getFlag(Flags.RD))
            response.getHeader().setFlag(Flags.RD);
        response.addRecord(queryRecord, Section.QUESTION);
        
        Name name = queryRecord.getName();
        int type = queryRecord.getType();
        int dclass = queryRecord.getDClass();
        if (type == Type.AXFR && s != null) {
            return doAXFR(name, query, tsig, queryTSIG, s);
        }
        if (!Type.isRR(type) && type != Type.ANY) {
            return errorMessage(query, Rcode.NOTIMP);
        }
        
        byte rcode = addAnswer(response, name, type, dclass, 0, flags);
        if (rcode != Rcode.NOERROR && rcode != Rcode.NXDOMAIN)
            return errorMessage(query, rcode);
        
        addAdditional(response, flags);
        
        if (queryOPT != null) {
            int optflags = (flags == FLAG_DNSSECOK) ? ExtendedFlags.DO : 0;
            OPTRecord opt = new OPTRecord((short)4096, rcode, (byte)0,
                    optflags);
            response.addRecord(opt, Section.ADDITIONAL);
        }
        response.setTSIG(tsig, Rcode.NOERROR, queryTSIG);
        return response.toWire(maxLength);
    }
    
    
    /**
     * This method build the error message.
     *
     * @return A byte array containing the error message.
     * @param header The header for the error messsage.
     * @param rcode The record code for this error message.
     * @param question The question that resulted in this error.
     */
    private byte [] buildErrorMessage(Header header, int rcode, Record question) {
        Message response = new Message();
        response.setHeader(header);
        for (int i = 0; i < 4; i++)
            response.removeAllRecords(i);
        if (rcode == Rcode.SERVFAIL)
            response.addRecord(question, Section.QUESTION);
        header.setRcode(rcode);
        return response.toWire();
    }
    
    
    /**
     * This method forms the error message.
     *
     * @return A butye array containing the newly formed error message.
     * @param in The bytes to wrappe into the message.
     */
    public byte[] formErrMessage(byte [] in) {
        Header header;
        try {
            header = new Header(in);
        } catch (IOException e) {
            return null;
        }
        return buildErrorMessage(header, Rcode.FORMERR, null);
    }
    
    
    /**
     * This method returns the error message using the
     */
    private byte[] errorMessage(Message query, int rcode) {
        return buildErrorMessage(query.getHeader(), rcode,
                query.getQuestion());
    }
    
}
