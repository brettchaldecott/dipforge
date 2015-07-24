/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  2015 Burntjam
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
 * AuditTrailManager.java
 */

// package path
package com.rift.coad.lib.audit;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object is responsible for managing the audit trails.
 *
 * @author brett chaldecott
 */
public class AuditTrailManager {
    
    // class static member variables
    private static AuditTrailManager singleton = null;
    
    // private member variables
    private Logger log = Logger.getLogger(AuditTrailManager.class.getName());
    private AuditTrailFormatter formatter = null;
    
    /**
     * Creates a new instance of AuditTrailManager
     */
    private AuditTrailManager() {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    AuditTrailManager.class);
            formatter = (AuditTrailFormatter)Class.forName(
                    config.getString("Formatter")).newInstance();
        } catch (Exception ex) {
            log.error("Failed to setup the audit trail manager : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns an instance of the audit trail manager.
     *
     * @return The reference to the newly created audit trail.
     */
    public static synchronized AuditTrailManager getInstance() {
        if (singleton == null) {
            singleton = new AuditTrailManager();
        }
        return singleton;
    }
    
    
    /**
     * This method is used to log an audit trail entry.
     */
    public void addEntry(AuditTrailEntry entry) {
        // if there is no audit trail ignore
        try {
            if (formatter != null)
            {
                formatter.format(entry);
            }
        } catch (Throwable ex) {
            log.error("Failed to log an audit trail entry : " + ex.getMessage(),
                    ex);
        }
    }
}
