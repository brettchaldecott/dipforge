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
 * WebServiceLoader.java
 *
 * The web service loader. Responsible for loading web services from the
 * coadunation jar files.
 */

// package that the web service can be found in
package com.rift.coad.lib.deployment.webservice;

// java utils
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.webservice.WebServiceWrapper;

/**
 * The web service loader responsible for loading web services from the
 * coadunation jar files.
 *
 * @author Brett Chaldecott
 */
public class WebServiceLoader {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(WebServiceLoader.class.getName());
    
    // The classes private member variables
    private Map services = null;
    private DeploymentLoader deploymentLoader = null;
    
    /** 
     * Creates a new instance of WebServiceLoader 
     *
     * @param deploymentLoader The reference to the deployment loader.
     */
    public WebServiceLoader(DeploymentLoader deploymentLoader) 
            throws WebServiceException {
        this.deploymentLoader = deploymentLoader;
        services = new LinkedHashMap();
        loadServices();
    }
    
    
    /**
     * This method loads the web service information retrieve from the 
     * deployment loader into web service wrappers.
     *
     * @exception WebServiceException
     */
    private void loadServices() throws WebServiceException {
        try {
            log.info("Load the Web Services for [" + 
                    deploymentLoader.getFile().getPath() + "]");
            Map mapList = deploymentLoader.getDeploymentInfo().getWebServices();
            for (Iterator iter = mapList.keySet().iterator(); iter.hasNext();) {
                com.rift.coad.lib.deployment.WebServiceInfo webServiceInfo =
                        (com.rift.coad.lib.deployment.WebServiceInfo)mapList.
                        get(iter.next());
                services.put(webServiceInfo.getPath(),new WebServiceWrapper(
                        webServiceInfo,deploymentLoader));
            }
        } catch (Exception ex) {
            throw new WebServiceException(
                    "Failed to load the web services :" +ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the list of web services loaded by this object.
     *
     * @return The list of services.
     */
    public Map getServices() {
        return services;
    }
    
}
