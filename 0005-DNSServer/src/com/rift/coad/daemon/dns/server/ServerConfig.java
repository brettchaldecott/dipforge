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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

// log4j imports
import org.apache.log4j.Logger;

// java dns imports
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Address;
import org.xbill.DNS.TSIG;

/**
 * This object is responsible for processing the server configuration
 * information.
 *
 * @author brett chaldecott
 */
public class ServerConfig {
    
    /**
     * This class represents the configuration for a zone.
     */
    public class ZoneConfig {
        private String name = null;
        private boolean primary = true;
        private String path = null;
        private String source = null;
        
        /**
         * The constructor for the zone configuration.
         *
         * @param name The name of the zone.
         * @param path The path to the configuration file.
         */
        public ZoneConfig(String name, String path) {
            this.name = name;
            primary = true;
            this.path = path;
        }
        
        
        /**
         * The secondary constructor
         *
         * @param name The name of the zone.
         * @param path The path to the configuration file.
         * @param source The source of the configuration.
         */
        public ZoneConfig(String name, String path,
                String source) {
            this.name = name;
            primary = false;
            this.path = path;
            this.source = source;
        }
        
        
        /**
         * This method returns the name.
         *
         * @return The string containing the name of the zone.
         */
        public String getName() {
            return name;
        }
        
        
        /**
         * This method will return true if this dns server is primary for this
         * zone.
         *
         * @return TRUE if this dns server is primary for this zone.
         */
        public boolean getPrimary(){
            return primary;
        }
        
        
        /**
         * This method returns the path of the zone file.
         *
         * @return The string containing the path
         */
        public String getPath() {
            return path;
        }
        
        
        /**
         * This method returns the source for a secondary zone.
         *
         * @return The string containing the source.
         */
        public String getSource() {
            return source;
        }
        
        
        /**
         * This method will return the string value of the is server
         * configuration entry.
         */
        public String toString() {
            if (this.primary) {
                return "primary " + name + " " + path;
            } else {
                return "secondary " + name + " " + path + " " + source;
            }
        }
    }
    
    
    // class constants
    private final static String CACHE_PATH = "cache.db";
    
    // static member variable
    private static Logger log = Logger.getLogger(ServerConfig.class);
    
    // private member variables
    private String configPath = null;
    private List fileContents = new ArrayList();
    private List ports = new ArrayList();
    private List addresses = new ArrayList();
    private String cache = CACHE_PATH;
    private Map zoneNames = new LinkedHashMap();
    private Map TSIGs = new HashMap();
    
    
    
    /**
     * Creates a new instance of ServerConfig
     *
     * @param configPath The path to the configuration file.
     */
    public ServerConfig(String configPath) throws ServerException {
        FileInputStream fs = null;
        this.configPath = configPath;
        try {
            fs = new FileInputStream(configPath);
            InputStreamReader isr = new InputStreamReader(fs);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);
                
                StringTokenizer st = new StringTokenizer(line);
                if (!st.hasMoreTokens())
                    continue;
                String keyword = st.nextToken();
                if (!st.hasMoreTokens()) {
                    log.info("Invalid line: " + line);
                    continue;
                }
                if (keyword.charAt(0) == '#')
                    continue;
                if (keyword.equals("primary")) {
                    String zoneName = st.nextToken();
                    zoneNames.put(zoneName, new ZoneConfig(zoneName,
                            st.nextToken()));
                } else if (keyword.equals("secondary")) {
                    String zoneName = st.nextToken();
                    zoneNames.put(zoneName, new ZoneConfig(zoneName,
                            st.nextToken(),st.nextToken()));
                } else if (keyword.equals("cache")) {
                    this.cache = st.nextToken();
                } else if (keyword.equals("key")) {
                    String s1 = st.nextToken();
                    String s2 = st.nextToken();
                    if (st.hasMoreTokens())
                        addTSIG(s1, s2, st.nextToken());
                    else
                        addTSIG("hmac-md5", s1, s2);
                } else if (keyword.equals("port")) {
                    ports.add(Integer.valueOf(st.nextToken()));
                } else if (keyword.equals("address")) {
                    String addr = st.nextToken();
                    addresses.add(Address.getByAddress(addr));
                } else {
                    log.info("unknown keyword: " +
                            keyword);
                }
                
            }
            
            // se the default address and port information
            if (ports.size() == 0)
                ports.add(new Integer(53));
            
            if (addresses.size() == 0)
                addresses.add(Address.getByAddress("0.0.0.0"));
            
            
        } catch (Throwable ex) {
            log.error("Failed to initialize the configuration : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to initialize the configuration : " +
                    ex.getMessage(),ex);
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (Throwable ex) {
                    log.error("Failed to close the configuration file : " +
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    
    
    /**
     * This method adds the tsig entries
     */
    private void addTSIG(String algstr, String namestr, String key) throws IOException {
        Name name = Name.fromString(namestr, Name.root);
        TSIGs.put(name, new TSIG(algstr, namestr, key));
    }
    
    
    /**
     * This method returns the list of ports.
     *
     * @return The list of ports.
     */
    public List getPorts() {
        return ports;
    }
    
    
    /**
     * This method returns the list of addresses.
     *
     * @return The list of addresses
     */
    public List getAddresses() {
        return addresses;
    }
    
    
    /**
     * This method returns the list of caches.
     *
     * @return The string containing the cache information.
     */
    public String getCache() {
        return cache;
    }
    
    
    /**
     * This method returns the list of zones.
     *
     * @return The map containing the zone names.
     */
    public Map getZoneNames() {
        return zoneNames;
    }
    
    
    /**
     * This method returns a map of tsig entries.
     *
     * @return The map containing the TSIGS entries
     */
    public Map getTSIGs() {
        return TSIGs;
    }
    
    
    
    /**
     * This method adds a new primary zone to the list.
     *
     * @param zone The zone to add
     */
    public ZoneConfig addPrimary(String zone, String path) throws ServerException {
        if (this.zoneNames.containsKey(zone)) {
            throw new ServerException("The zone [" + zone
                    + "] is configured already");
        }
        ZoneConfig config = new ZoneConfig(zone,path);
        this.zoneNames.put(zone,config);
        this.fileContents.add(config.toString());
        persistConfig();
        return config;
    }
    
    
    /**
     * This method adds a secondary zone.
     */
    public ZoneConfig addSecondary(String zone, String path, String source)  throws
            ServerException {
        if (this.zoneNames.containsKey(zone)) {
            throw new ServerException("The zone [" + zone
                    + "] is configured already");
        }
        ZoneConfig config = new ZoneConfig(zone,path,source);
        this.zoneNames.put(zone,config);
        this.fileContents.add(config.toString());
        persistConfig();
        return config;
    }
    
    
    /**
     * This method removes the specified zone.
     *
     * @param zone The zone to remove.
     */
    public void removeZone(String zone) throws ServerException {
        for (int index = 0; index < this.fileContents.size(); index++) {
            StringTokenizer st = new StringTokenizer(
                    (String)fileContents.get(index));
            if (!st.hasMoreTokens())
                continue;
            String keyword = st.nextToken();
            if (!st.hasMoreTokens()) {
                continue;
            }
            if (keyword.charAt(0) == '#')
                continue;
            if (keyword.equals("primary") || keyword.equals("secondary")) {
                String zoneName = st.nextToken();
                if (zoneName.equals(zone)) {
                    fileContents.remove(index);
                    break;
                }
            }
        }
        zoneNames.remove(zone);
        persistConfig();
    }
    
    
    /**
     * This method is responsible for persisting the configuration.
     */
    private void persistConfig() throws ServerException {
        try {
            FileWriter writer = new FileWriter(this.configPath);
            for (int index = 0; index < this.fileContents.size(); index++) {
                writer.write(this.fileContents.get(index).toString().
                        toCharArray());
                writer.write('\n');
            }
            writer.flush();
            writer.close();
        } catch (Throwable ex) {
            log.error("Failed to persist the configuration : " + ex.getMessage(),
                    ex);
            throw new ServerException(
                    "Failed to persist the configuration : " + ex.getMessage(),
                    ex);
        }
    }
}
