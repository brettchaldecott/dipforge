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
 * DNSServer.java
 */

// package path
package com.rift.coad.daemon.dns;

// java imports
import java.rmi.RemoteException;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.daemon.dns.server.Server;


/**
 * The implementation of the dns server
 *
 * @author brett chaldecott
 */
public class DNSServer implements DNSServerMBean, BeanRunnable {
    
    // class constants
    private final static String DNS_BASE = "dns_base";
    
    // singleton object
    private static Logger log = Logger.getLogger(DNSServer.class);
    
    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();
    
    
    /**
     * Creates a new instance of DNSServer
     *
     * @throws DNSException
     */
    public DNSServer() throws DNSException{
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(DNSServer.class);
            Server.instantiate(config.getString(DNS_BASE));
        } catch (Exception ex) {
            log.error("Failed to initialize the dns manager : " + 
                    ex.getMessage(),ex);
            throw new DNSException(
                    "Failed to initialize the dns manager : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the version information for this dns server.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1";
    }
    
    
    /**
     * This method returns the name of this server
     *
     * @return The name of this server.
     */
    public String getName() {
        return "JavaDnsServer";
    }
    
    
    /**
     * This method returns the description of the server.
     *
     * @return The string containing the description of this server.
     */
    public String getDescription() {
        return "Java DNS Server";
    }
    
    
    /**
     * This method returns the status of the dns server.
     *
     * @return The string containing the status of the dns server.
     */
    public String getStatus() throws DNSException {
        try {
            return Server.getInstance().getStatus();
        } catch (Exception ex) {
            log.error("Failed to retrieve the server status : " + 
                    ex.getMessage(),ex);
            throw new DNSException ("Failed to retrieve the server status : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the zones managed by this server.
     *
     * @return The list of zones.
     * @param type The type of zones [1: Primary, 2: Secondary, 3: All]
     * @exception DNSException
     */
    public List listZones(int type) throws DNSException {
        try {
            return Server.getInstance().listZones(type);
        } catch (Exception ex) {
            log.error(
                    "Failed to list the zones because : " 
                    + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to list the zones because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a new zone.
     *
     * @param zoneName The name of the zone to create.
     * @param zone The zone to create.
     * @throw DNSException
     */
    public void createZone(String zoneName, String zone) throws DNSException {
        try {
            Server.getInstance().createZone(zoneName,zone);
        } catch (Exception ex) {
            log.error(
                    "Failed to create the zone [" + zoneName + "] because : " 
                    + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to create the zone [" + zoneName + "] because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a secondary zone using the remote host as source.
     *
     * @param zoneName The name of the zone.
     * @param remote The host to retrieve the dns information from.
     * @throw DNSException
     */
    public void createSecondaryZone(String zoneName, String remote) throws
            DNSException {
        try {
            Server.getInstance().createSecondaryZone(zoneName,remote);
        } catch (Exception ex) {
            log.error(
                    "Failed to create the secondary zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to create the secondary zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the zone contents for the given name.
     *
     * @return The zone contents for the given name.
     * @param zoneName The name of zone to retrieve.
     * @throw DNSException
     */
    public String getZone(String zoneName) throws DNSException {
        try {
            return Server.getInstance().getZone(zoneName);
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to retrieve the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the zone.
     *
     * @param zoneName The name of the zone to update.
     * @param zone The zone contents to update.
     * @throw DNSException
     */
    public void updateZone(String zoneName, String zone) throws DNSException {
        try {
            Server.getInstance().updateZone(zoneName,zone);
        } catch (Exception ex) {
            log.error(
                    "Failed to update the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to update the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the zone from the dns server.
     *
     * @param zoneName The name of the zone to remove.
     * @throw DNSException
     */
    public void removeZone(String zoneName) throws DNSException {
        try {
            Server.getInstance().removeZone(zoneName);
        } catch (Exception ex) {
            log.error(
                    "Failed to remove the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
            throw new DNSException (
                    "Failed to remove the zone [" + zoneName 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process
     */
    public void process() {
        
        // start the dns server
        try {
            Server.getInstance().initialize();
        } catch (Exception ex) {
            log.error("Failed to initialie the DNS Server : " + 
                    ex.getMessage(),ex);
            return;
        }
        log.info("DNS Server Processing Requests");
        // wait until the thread is terminate
        while(!state.isTerminated()) {
            state.monitor();
        }
        
        // do anything else that might be required
    }
    
    
    /**
     * This method terminates the processing of the dns server.
     */
    public void terminate() {
        // shut down the
        try {
            Server.getInstance().terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the server : " + 
                    ex.getMessage(),ex);
        }
        state.terminate(true);    
    }
    
    
    
}
