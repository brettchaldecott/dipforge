/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * AxisManager.java
 *
 * This object is responsible for controlling access to the axis server or 
 * engine instance.
 */

// package path
package com.rift.coad.lib.thirdparty.axis;

// axis includes
import org.apache.axis.AxisEngine;
import org.apache.axis.server.AxisServer;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.EngineConfiguration;


/**
 * This object is responsible for controlling access to the axis server or 
 * engine instance.
 *
 * @author Brett Chaldecott
 */
public class AxisManager {
    
    // the static member variables
    private static AxisManager singleton = null;
    
    // private member variables
    private AxisServer server = null;
    
    
    /** 
     * Creates a new instance of AxisManager
     *
     * @exception AxisException
     */
    private AxisManager() throws AxisException {
        try {
            EngineConfiguration config = 
                    EngineConfigurationFactoryFinder.newFactory().
                    getServerEngineConfig();
            server = new AxisServer(config);
        } catch (Exception ex) {
            throw new AxisException("Failed to instanciate the Axis Manager: " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for instanciating a new 
     */
    public static void init() throws AxisException {
        if (singleton == null) {
            singleton = new AxisManager();
        }
    }
    
    /**
     * This method returns a reference to the axis manager instance.
     *
     * @return A reference to the axis manager instance.
     * @exception AxisException
     */
    public static AxisManager getInstance() throws AxisException {
        if (singleton == null) {
            throw new AxisException(
                    "The Axis engine has not been initialized.");
        } 
        return singleton;
    }
    
    
    /**
     * The getter for the server member variable.
     *
     * @return The instance of the axis server.
     */
    public AxisServer getServer() {
        return server;
    }
}
