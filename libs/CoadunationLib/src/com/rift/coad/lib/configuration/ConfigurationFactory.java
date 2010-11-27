/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * ConfigurationFactory.java
 *
 * The configuration factory object. This object is responsible for retrieving
 * the configuration information.
 */

// The package
package com.rift.coad.lib.configuration;

/**
 * The configuration factory. Responsible for the initial read of the
 * configuration file.
 *
 * @author Brett Chaldecott
 */
public abstract class ConfigurationFactory {
    
    // classes private member variables
    private static ConfigurationFactory singleton = null;
    
    /** 
     * Creates a new instance of ConfigurationFactory 
     */
    public ConfigurationFactory() {
    }
    
    
    /**
     * This method will return the singleton reference to the configration class
     *
     * @return The reference to the singleton configuration class.
     */
    static synchronized public ConfigurationFactory getInstance() throws
        ConfigurationException{
        if (singleton != null) {
            return singleton;
        }
        try {
            // instanciate the singleton 
            singleton = (ConfigurationFactory)Class.forName(
                    System.getProperty("coad.config")).
                    newInstance();
            return singleton;
        } catch (Exception ex) {
            throw new ConfigurationException("Failed to load the class [" +
                    System.getProperty("coad.config") + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the configuration class scoped for the
     * class reference.
     *
     * @return Configuration The reference to the configuration class.
     * @param classRef The reference to the class that the configuration will be 
     *          retrieve for
     * @exception ConfigurationException
     */
    public abstract Configuration getConfig(Class classRef) 
        throws ConfigurationException;
    
}
