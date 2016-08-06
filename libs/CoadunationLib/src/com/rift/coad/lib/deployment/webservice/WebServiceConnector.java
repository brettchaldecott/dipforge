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
 * JMXBeanConnector.java
 *
 * This singleton is responsible for supplying access to the web services.
 */

// package path
package com.rift.coad.lib.deployment.webservice;

// java imports
import java.util.Set;

// coadunation imports
import com.rift.coad.lib.webservice.WebServiceWrapper;

/**
 * This singleton is responsible for supplying access to the web services.
 *
 * @author Brett Chaldecott
 */
public class WebServiceConnector {
    
    // the classes private member variables
    private static WebServiceConnector singleton = null;
    private WebServiceManager webServiceManager = null;
    
    /** 
     * Creates a new instance of WebServiceConnector 
     */
    private WebServiceConnector(WebServiceManager webServiceManager) {
        this.webServiceManager = webServiceManager;
    }
    
    
    /**
     * This method will be responsible for initializing the web service 
     * connector.
     *
     * @param webServiceManager The reference to the web service manager.
     */
    public static synchronized void init(WebServiceManager webServiceManager) {
        if (singleton == null) {
            singleton = new WebServiceConnector(webServiceManager);
        }
    }
    
    
    /**
     * This method returns the WebService connector instance.
     *
     * @return The reference to the web service connector.
     * @exception WebServiceException
     */
    public static synchronized WebServiceConnector getInstance() 
            throws WebServiceException {
        if (singleton == null) {
            throw new WebServiceException (
                    "The Web Service connector has not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * Retrieve the list of web servies.
     *
     * @return The list of web services managed by this object.
     */
    public Set getServices() {
        return webServiceManager.getServices();
    }
    
    
    /**
     * This method retrieve the service identified by the path
     *
     * @return The service identified by the path.
     * @param path The path identifying the path.
     */
    public Object getService(String path) {
        return webServiceManager.getService(path);
    }
}
