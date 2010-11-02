/*
 * EMailServer: The email server implementation.
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
 * DNSServer.java
 */

// package information
package com.rift.coad.daemon.email.server.dns;

// java imports
import java.net.InetAddress;
import java.net.UnknownHostException;

// log4j imports
import org.apache.log4j.Logger;

// the dns imports
import org.xbill.DNS.Name;
import org.xbill.DNS.RRset;
import org.xbill.DNS.SetResponse;
import org.xbill.DNS.Cache;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Record;
import org.xbill.DNS.Address;
import org.xbill.DNS.Type;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;


/**
 * This object is responsible for performing the necessary lookups against the
 * specified name server on behalf of the email server.
 *
 * @author brett chaldecott
 */
public class NSTool {
    
    // classx constants
    private final static String NAME_SERVERS = "dns_name_servers";
    private final static String DEFAULT_NAME_SERVERS = "127.0.0.1";
    
    
    // class singleton
    private static Logger log = Logger.getLogger(NSTool.class);
    private static NSTool singleton = null;
    
    // class member variables
    private String nsConfig = null;
    
    
    /**
     * Creates a new instance of NSTool
     */
    private NSTool() throws NSException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(NSTool.class);
            nsConfig = config.getString(NAME_SERVERS,DEFAULT_NAME_SERVERS);
            Lookup.setDefaultResolver(new ExtendedResolver(nsConfig
                    .split("[,]")));
            
        } catch (Exception ex) {
            log.error("Failed to setup the name server information : " +
                    ex.getMessage(),ex);
            throw new NSException(
                    "Failed to setup the name server information : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns an instance of the ns tool
     *
     * @return Reference to the ns tool.
     */
    public synchronized static NSTool getInstance() throws NSException {
        if (singleton == null) {
            singleton = new NSTool();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the status of the ns tool.
     */
    public String getStatus() {
        return "NS Config: " + nsConfig + "\n";
    }
    
    
    /**
     * This method returns the InetAddress associated with the host name passed
     * in.
     *
     * @return The inet address if found or null if the address does not exist.
     * @param host The host to perform the lookup for.
     * @exception NSException
     */
    public InetAddress getByName(String host) throws NSException {
        try {
            return Address.getByName(stripTrailingDot(host));
        } catch (UnknownHostException ex) {
            return null;
        } catch (Exception ex) {
            log.error("Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
            throw new NSException(
                    "Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns the InetAddress associated with the host name passed
     * in.
     *
     * @return The inet address list if found or null if the address does not
     * exist.
     * @param host The host to perform the lookup for.
     * @exception NSException
     */
    public InetAddress[] getAllByName(String host) throws NSException {
        try {
            return Address.getAllByName(stripTrailingDot(host));
        } catch (UnknownHostException ex) {
            return null;
        } catch (Exception ex) {
            log.error("Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
            throw new NSException(
                    "Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
        }   
    }
    
    
    /**
     * 
     */
    public Record[] getMXRecords(String domain) throws NSException {
        try {
            // Lookup the MX Entries
            return new Lookup(domain, Type.MX).run();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
            throw new NSException(
                    "Failed to retrieve the host by name : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is used to strip the trailling dot off a fully formed domain.
     *
     * @return The string containing the dot information.
     * @param host The host to strip.
     */
    private String stripTrailingDot(String host) {
        if ((host.charAt(host.length() - 1) == '.')) {
            String possible_ip_literal = host.substring(0, host.length() - 1);
            if (org.xbill.DNS.Address.isDottedQuad(possible_ip_literal)) {
                host = possible_ip_literal;
            }
        }
        return host;
    }
}
