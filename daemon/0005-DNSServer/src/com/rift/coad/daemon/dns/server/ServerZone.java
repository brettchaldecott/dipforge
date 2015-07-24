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
 * ServerZone.java
 */

// the package path
package com.rift.coad.daemon.dns.server;

// java imports
import com.rift.coad.daemon.dns.server.ServerTransfer;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

// log path
import org.apache.log4j.Logger;
import org.xbill.DNS.DClass;

// dns imports
import org.xbill.DNS.Zone;
import org.xbill.DNS.ZoneTransferIn;
import org.xbill.DNS.Name;
import org.xbill.DNS.TSIG;
import org.xbill.DNS.Record;


/**
 * This is the server zone. It is responsible for managing the zone information
 * on behalf of the server.
 *
 * @author brett chaldecott
 */
public class ServerZone implements ServerTransfer {
    
    // class constants
    private final static String REFRESH_TIME = "dns_default_refresh_time";
    private final static long DEFAULT_REFRESH_TIME = 86400;
    private final static String RETRY_TIME = "dns_default_retry_time";
    private final static long DEFAULT_RETRY_TIME = 7200;
    
    
    // static member variables
    private static Logger log = Logger.getLogger(ServerZone.class);
    
    // private member variables
    private ServerConfig.ZoneConfig config = null;
    private Zone zone = null;
    private File zoneFile = null;
    
    // these variables are only used when dealing with a secondary zone.
    private long touchTime = 0;
    private long refreshTime = 0;
    private long retryTime = 0;
    
    
    /**
     * Creates a new instance of ServerZone
     *
     * @param config The configuration object for the zone.
     * @throws ServerException
     */
    public ServerZone(ServerConfig.ZoneConfig config) throws ServerException {
        this.config = config;
        zoneFile = new File(config.getPath());
        loadZone();
        try {
            // set the soa variable information for a secondary zone
            if (config.getPrimary() == false) {
                if (zone == null) {
                    Configuration centralConfig = 
                        ConfigurationFactory.getInstance().getConfig(
                        ServerZone.class);
                    refreshTime = centralConfig.getLong(REFRESH_TIME,
                            DEFAULT_REFRESH_TIME) * 1000;
                    retryTime = centralConfig.getLong(RETRY_TIME,
                            DEFAULT_RETRY_TIME) * 1000;
                } else {
                    getSOAVariables();
                }
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve the configuration information : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the configuration information : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves the zone name managed by this server object.
     *
     * @return The zone object.
     */
    public String getZoneName() {
        return config.getName();
    }
    
    
    
    
    /**
     * This method returns the configuration for the zone.
     */
    public ServerConfig.ZoneConfig getConfig() {
        return config;
    }
    
    /**
     * This method retrieves the zone object managed by this server object.
     *
     * @return The zone object.
     */
    public Zone getZone() {
        return zone;
    }
    
    
    /**
     * This method sets the zone object.
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }
    
    
    /**
     * This method returns true if the zone requires a refresh.
     *
     * @return TRUE if the zone requires a refresh.
     */
    public boolean requiresRefresh() {
        if (config.getPrimary()) {
            return false;
        } else if ((Calendar.getInstance().getTimeInMillis() - refreshTime) 
            > touchTime) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method returns true if the object has expired.
     *
     * @return TRUE if the zone has expired.
     */
    public boolean isExpired() {
        if (config.getPrimary()) {
            return false;
        } else if (zone == null) {
            return true;
        } else if ((Calendar.getInstance().getTimeInMillis() - 
                (1000 * zone.getSOA().getExpire())) > touchTime) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method gets called to perform the transfer of the zone.
     *
     * @exception ServerException
     */
    public void performTransfer() throws ServerException {
        try {
            // transfer the zone
            log.info("Perform transfer for [" + config.getName() + "]");
            Name zname = Name.fromString(config.getName(), Name.root);
            ZoneTransferIn xfrin = ZoneTransferIn.newAXFR(zname, 
                    config.getSource(), null);
            Name origin = xfrin.getName();
            List records = xfrin.run();
            Zone newzone = new Zone(origin,
                    (Record[])records.toArray(new Record[0]));
            
            this.zone = newzone;
            this.persistZone();
            // If the zone could not be presited to disk, it can still be
            // used for lookups.
            if (!zoneFile.exists()) {
                touchTime = new Date().getTime();
            }
            // update the SOA information for the newly transfered zone.
            getSOAVariables();
            log.info("Transfer for [" + config.getName() + "] complete");
        } catch (Exception ex) {
            log.error("Failed to perform the transfer [" + config.getName() + 
                    "]: " + ex.getMessage(),ex);
            // recalculate the touch time
            touchTime = (new Date().getTime() - refreshTime) + retryTime;
            throw new ServerException("Failed to perform the transfer [" + 
                    config.getName() +  "]: " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the time until the zone expires.
     */
    public long getTimeUntilRefresh() {
        if (touchTime == 0) {
            return touchTime;
        }
        return touchTime - 
                (new Date().getTime() - 
                (this.refreshTime));
    }
    
    
    /**
     * This method loads the zone from the disk file.
     *
     * @throws ServerException
     */
    private void loadZone() throws ServerException {
        try {
            if (zoneFile.exists()) {
                // setup the zone origin.
                touchTime = zoneFile.lastModified();
                Name origin = null;
                if (config.getName() != null) {
                    origin = Name.fromString(config.getName(), Name.root);
                }
                zone = new Zone(origin,zoneFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            log.error("Failed to load the zone : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to load the zone : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for persisting the zone to its file store.
     */
    public void persistZone() throws ServerException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.zoneFile);
            writer.write(this.zone.toString().toCharArray());
            writer.flush();
            touchTime = zoneFile.lastModified();
        } catch (Exception ex) {
            log.error("Failed to persist the zone : " + ex.getMessage(),ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ex) {
                    log.error("Failed to close the file : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    /**
     * This method sets the soa variables from the zone. It assumes the zone
     * exists.
     */
    private void getSOAVariables() {
        this.refreshTime = zone.getSOA().getRefresh() * 1000;
        this.retryTime = zone.getSOA().getRetry() * 1000;
    }
    
    /**
     * This method is responsible for persisting the zone to its file store.
     */
    public void removeZone() throws ServerException {
        try {
            this.zoneFile.delete();
        } catch (Exception ex) {
            log.error("Failed to remove the zone : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to remove the zone : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method compares this object with the current object
     */
    public int compareTo(Object rhs) {
        if (rhs == this) {
            return 0;
        } else if (!(rhs instanceof ServerZone)) {
            return this.config.getName().compareTo(rhs.toString());
        }
        ServerZone zone = (ServerZone)rhs;
        boolean rhsIsExpired = zone.isExpired();
        boolean expired = isExpired();
        boolean rhsRefresh = zone.requiresRefresh();
        boolean refresh = requiresRefresh();
        long rhsRefreshTime = zone.getTimeUntilRefresh();
        long refreshTime = getTimeUntilRefresh();
        if (rhsRefreshTime != refreshTime) {
            return (int)(refreshTime - rhsRefreshTime);
        } else if ((rhsIsExpired && expired) || (rhsRefresh && refresh) ||
                (rhsRefreshTime == refreshTime)) {
            return config.getName().compareTo(zone.getConfig().getName());
        } else if ((rhsIsExpired && !expired) || (rhsRefresh && !refresh)) {
            return 1;
        } else if ((!rhsIsExpired && expired) || (!rhsRefresh && refresh)) {
            return -1;
        } 
        return (int)(refreshTime - rhsRefreshTime);
    }
    
    
    /**
     * This method checks for equals on the current object.
     *
     * @param rhs The object to compare to.
     * @return TRUE if equals false if not.
     */
    public boolean equals(Object rhs) {
        if (rhs == this) {
            return true;
        } else if (!(rhs instanceof ServerZone)) {
            return false;
        }
        ServerZone zone = (ServerZone)rhs;
        return getZoneName().equals(zone.getZoneName());
    }
}
