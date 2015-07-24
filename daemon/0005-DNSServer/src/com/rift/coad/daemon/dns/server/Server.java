/*
 * DNSServer: The dns server implementation.
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
 * Server.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// java imports
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

// log4j import
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// dns import
import org.xbill.DNS.Zone;
import org.xbill.DNS.Name;


/**
 * The implementation of the server
 *
 * @author brett chaldecott
 */
public class Server {
    
    // class constants
    private final static String CONFIG_FILE = "jnamed.conf";
    private final static String PRIMARY_PATH = "primary_path";
    private final static String DEFAULT_PRIMARY_PATH = "primary";
    private final static String SECONDARY_PATH = "seccondary_path";
    private final static String DEFAULT_SECONDARY_PATH = "secondary";
    private final static String INSTALLATION_BASE = "installation_base";
    private final static String TEMP_PATH = "tmp_path";
    private final static String DEFAULT_TEMP_PATH = "tmp";
    private final static String POOL_USER = "dns_pool_user";
    private final static String POOL_SIZE = "dns_pool_size";
    private final static long DEFAULT_POOL_SIZE = 10;
    
    
    
    // singleton object
    private static Logger log = Logger.getLogger(Server.class);
    
    // private singleton member variables
    private static Server singleton = null;
    
    // private member variables
    private String serverBase = null;
    private ServerConfig config = null;
    private ThreadStateMonitor state = new ThreadStateMonitor(); 
    private ServerTransferQueue transferQueue = null;
    private ServerZoneMap zoneMap = null;
    private ServerLookup lookup = null;
    private ServerRequestHandler handler = null;
    private List tcpServers = new ArrayList();
    private List udpServers = new ArrayList();
    private String primaryPath = DEFAULT_PRIMARY_PATH;
    private String secondaryPath = DEFAULT_SECONDARY_PATH;
    private String installationBase = null;
    private String tmpPath = DEFAULT_TEMP_PATH;
    private ThreadPoolManager threadPool = null;
    
    /**
     * Creates a new instance of Server
     *
     * @param serverBase The server base path.
     * @exception ServerException
     */
    private Server(String serverBase) throws ServerException {
        this.serverBase = serverBase;
        this.config = new ServerConfig(serverBase + 
                java.io.File.separator + CONFIG_FILE);
        transferQueue = new ServerTransferQueue();
        zoneMap = new ServerZoneMap();
        
        // configuration informa
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            installationBase = config.getString(INSTALLATION_BASE);
            
            primaryPath = config.getString(PRIMARY_PATH,DEFAULT_PRIMARY_PATH);
            secondaryPath = config.getString(SECONDARY_PATH,
                    DEFAULT_SECONDARY_PATH);
            tmpPath = config.getString(TEMP_PATH,DEFAULT_TEMP_PATH);
            
            threadPool = new ThreadPoolManager(
                    (int)config.getLong(POOL_SIZE,DEFAULT_POOL_SIZE), 
                    ServerRequestProcessor.class, config.getString(POOL_USER));
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the configuration information : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the configuration information : " +
                    ex.getMessage(),ex);
        }
        
        // load the zones
        for (Iterator iter = this.config.getZoneNames().keySet().iterator(); 
        iter.hasNext();) {
            String name = (String)iter.next();
            try {
                ServerConfig.ZoneConfig zoneConfig = (ServerConfig.ZoneConfig)
                    config.getZoneNames().get(name);
                ServerZone serverZone = new ServerZone(zoneConfig);
                if (!zoneConfig.getPrimary()) {
                    transferQueue.addServerTransfer(serverZone);
                }
                zoneMap.addZone(serverZone);
                log.info("Loaded zone [" + serverZone.getZoneName() + "]");
            } catch (Exception ex) {
                log.error("Failed to load the zone [" + name + "] because : " +
                        ex.getMessage(),ex);
            }
        }
        
        // instanciate the request handler and lookup handler
        lookup = new ServerLookup();
        handler = new ServerRequestHandler(config,zoneMap,lookup);
    }
    
    
    /**
     * This method returns the singleton server instance.
     *
     * @return The reference to the instanciate server.
     * @param serverBase The server base for the configuration.
     * @throws ServerException
     */
    public static synchronized Server instantiate(String serverBase) throws
    ServerException {
        if (singleton == null) {
            singleton = new Server(serverBase);
        }
        return singleton;
    }
    
    
    /**
     * This method returns the singleton server instance.
     *
     * @return The reference to the instanciate server.
     * @param configFile The path to the configuration file.
     */
    public static synchronized Server getInstance() throws ServerException {
        if (singleton == null) {
            throw new ServerException("The server has not been initialized");
        }
        return singleton;
    }
    
    
    /**
     * method initializes the server from the configuration
     */
    public void initialize() {
        log.info("Start the transfer queue");
        transferQueue.start();
        
        // start the 
        log.info("Start the servers");
        List addresses = this.config.getAddresses();
        List ports = this.config.getPorts();
        for (int addrIndex = 0; addrIndex < addresses.size(); addrIndex++) {
            for (int portIndex = 0; portIndex < ports.size(); portIndex++) {
                Integer port = (Integer)ports.get(portIndex);
                
                // start the tcp server
                TCPServer tcpServer = new TCPServer(this.handler,
                        (InetAddress)addresses.get(addrIndex),port.intValue());
                tcpServer.start();
                this.tcpServers.add(tcpServer);
                
                // start the udp server
                UDPServer udpServer = new UDPServer(this.handler,
                        (InetAddress)addresses.get(addrIndex),port.intValue());
                udpServer.start();
                this.udpServers.add(udpServer);
                
            }
        }
        
        log.info("DNS Server Started.");
    }
    
    
    /**
     * Terminate the transfer server.
     */
    public void terminate() {
        
        // terminate the tcp servers
        log.info("Terminate the UDP servers");
        for (int index = 0; index < this.udpServers.size(); index++) {
            UDPServer server = (UDPServer)this.udpServers.get(index);
            server.terminate();
        }
        
        // terminate the tcp servers
        log.info("Terminate the TCP servers");
        for (int index = 0; index < this.tcpServers.size(); index++) {
            TCPServer server = (TCPServer)this.tcpServers.get(index);
            server.terminate();
        }
        
        // terminate the transfer queue and wait for it to stop.
        log.info("Terminate the transfer queue");
        transferQueue.terminate();
        try {
            transferQueue.join();
        } catch (Exception ex) {
            log.error("Failed to terminate the transfer queue : " + 
                    ex.getMessage(),ex);
        }
        
        // terminate the request manager
        log.info("Terminate the server request manager");
        ServerRequestManager.getInstance().terminate();
        
        // terminate the request manager
        log.info("terminate the thread pool");
        try {
            threadPool.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the thread pool : " + 
                    ex.getMessage());
        }
        
        // final log line
        log.info("DNS Server Stopped.");
    }
    
    
    /**
     * This method retrieves the configuration for the server.
     *
     * @return The reference to the server configuration.
     */
    public ServerConfig getConfig() {
        return config;
    }
    
    
    /**
     * This method returns the status of the server.
     *
     * @return The status of the server.
     */
    public String getStatus() {
        StringBuffer result = new StringBuffer();
        result.append("Thread Pool Size : ").append(threadPool.getSize()) .
                append("\n");
        result.append(this.zoneMap.getStatus());
        result.append(this.transferQueue.getStatus());
        
        // retrieve the information from the tcp server
        for (int index = 0; index < this.tcpServers.size(); index++) {
            TCPServer server = (TCPServer)this.tcpServers.get(index);
            result.append(server.getStatus()).append("\n");
        }
        
        // retrieve the information from the tcp server
        for (int index = 0; index < this.udpServers.size(); index++) {
            UDPServer server = (UDPServer)this.udpServers.get(index);
            result.append(server.getStatus()).append("\n");
        }
        
        return result.toString();
    }
    
    
    /**
     * This method lists the zones managed by this server.
     *
     * @return The list of zones.
     * @param type The type of zones [1: Primary, 2: Secondary, 3: All]
     * @exception DNSException
     * @exception RemoteException
     */
    public synchronized List listZones(int type) throws
            ServerException {
        return zoneMap.listZones(type);
    }
    
    
    /**
     * This method is responsible for create a new zone in the server.
     *
     * @param zone The zone name.
     * @param zoneContents The contents of the zone file.
     * @exception ServerException
     */
    public synchronized void createZone(String zone, String zoneContents) throws
            ServerException {
        try {
            // check for a duplicate zone
            if (zoneMap.getZone(zone) != null) {
                throw new ServerException("The zone [" + zone 
                        + "] is already managed by this server.");
            }
            
            // create the file
            File zoneFile = new File(this.serverBase + File.separator + 
                    this.primaryPath + File.separator + zone + ".db");
            FileWriter writer = new FileWriter(zoneFile);
            writer.write(zoneContents);
            writer.flush();
            writer.close();
            
            Name zoneName = Name.fromString(zone, Name.root);
            Zone zoneRef = new Zone(zoneName,zoneFile.getPath());
            
            ServerConfig.ZoneConfig zoneConfig  = 
                    this.config.addPrimary(zone,zoneFile.getPath());
            ServerZone serverZone = new ServerZone(zoneConfig);
            zoneMap.addZone(serverZone);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for create a new zone in the server.
     *
     * @param zone The zone name.
     * @param zoneContents The contents of the zone file.
     * @exception ServerException
     */
    public synchronized ServerZone createZone(String zone) throws
            ServerException {
        try {
            // check for a duplicate zone
            if (zoneMap.getZone(zone) != null) {
                throw new ServerException("The zone [" + zone 
                        + "] is already managed by this server.");
            }
            
            // create the file
            File zoneFile = new File(this.serverBase + File.separator + 
                    this.primaryPath + File.separator + zone + ".db");
            
            ServerConfig.ZoneConfig zoneConfig  = 
                    this.config.addPrimary(zone,zoneFile.getPath());
            ServerZone serverZone = new ServerZone(zoneConfig);
            zoneMap.addZone(serverZone);
            
            return serverZone;
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for create a new zone in the server.
     *
     * @param zone The zone name.
     * @param source The source for the transfer.
     * @exception ServerException
     */
    public synchronized ServerZone createSecondaryZone(String zone, String source) 
    throws ServerException {
        try {
            // check for a duplicate zone
            if (zoneMap.getZone(zone) != null) {
                throw new ServerException("The zone [" + zone 
                        + "] is already managed by this server.");
            }
            File zoneFile = new File(this.serverBase + File.separator + 
                    this.secondaryPath + File.separator + zone + ".db");
            
            Name zoneName = Name.fromString(zone, Name.root);
            
            ServerConfig.ZoneConfig zoneConfig  = 
                    this.config.addSecondary(zone,zoneFile.getPath(),source);
            ServerZone serverZone = new ServerZone(zoneConfig);
            transferQueue.addServerTransfer(serverZone);
            zoneMap.addZone(serverZone);
            return serverZone;
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to create the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the zone contents
     *
     * @param zone The zone name.
     * @param source The source for the transfer.
     * @exception ServerException
     */
    public synchronized String getZone(String zone) 
    throws ServerException {
        try {
            // check for a duplicate zone
            ServerZone serverZone = zoneMap.getZone(zone);
            if (serverZone == null) {
                throw new ServerException("The zone [" + zone 
                        + "] is not managed by this server.");
            }
            if (serverZone.getZone() == null) {
                return "NA";
            }
            return serverZone.getZone().toString();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the zone contents
     *
     * @param zone The zone name.
     * @param source The source for the transfer.
     * @exception ServerException
     */
    public synchronized ServerZone getServerZone(String zone) 
    throws ServerException {
        try {
            // check for a duplicate zone
            ServerZone serverZone = zoneMap.getZone(zone);
            if (serverZone == null) {
                throw new ServerException("The zone [" + zone 
                        + "] is not managed by this server.");
            }
            return serverZone;
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for updating a zone in the server.
     *
     * @param zone The zone name.
     * @param zoneContents The contents of the zone file.
     * @exception ServerException
     */
    public synchronized void updateZone(String zone, String zoneContents) throws
            ServerException {
        try {
            // check for a duplicate zone
            ServerZone serverZone = zoneMap.getZone(zone);
            if (serverZone == null) {
                throw new ServerException("The zone [" + zone 
                        + "] cannot be found.");
            } else if (!serverZone.getConfig().getPrimary()) {
                throw new ServerException("Cannot update the secondary [" + zone 
                        + "].");
            }
            
            // create the file
            File tmpFile = new File(installationBase + File.separator + 
                    this.tmpPath + File.separator + zone + ".db");
            FileWriter writer = new FileWriter(tmpFile);
            writer.write(zoneContents);
            writer.flush();
            writer.close();
            
            Name zoneName = Name.fromString(zone, Name.root);
            Zone zoneRef = new Zone(zoneName,tmpFile.getPath());
            
            // create the file
            File zoneFile = new File(serverZone.getConfig().getPath());
            writer = new FileWriter(zoneFile);
            writer.write(zoneRef.toString());
            writer.flush();
            writer.close();
            
            // instanciate the zone ref
            zoneRef = new Zone(zoneName,zoneFile.getPath());
            
            // update the zone
            serverZone.setZone(zoneRef);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to update the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for removing a zone from the server.
     *
     * @param zone The zone name.
     * @param zoneContents The contents of the zone file.
     * @exception ServerException
     */
    public synchronized void removeZone(String zone) throws ServerException {
        try {
            // check for a duplicate zone
            ServerZone serverZone = zoneMap.getZone(zone);
            if (serverZone == null) {
                throw new ServerException("The zone [" + zone 
                        + "] cannot be found.");
            }
            
            // remove the transfer entry
            if (!serverZone.getConfig().getPrimary()) {
                this.transferQueue.removeServerTransfer(serverZone);
            }
            this.zoneMap.removeZone(zone);
            config.removeZone(zone);
            
            // remove the file
            File zoneFile = new File(serverZone.getConfig().getPath());
            zoneFile.delete();
            
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to remove the zone [" + zone + "] because : " +
                    ex.getMessage(),ex);
        }
    }
}
