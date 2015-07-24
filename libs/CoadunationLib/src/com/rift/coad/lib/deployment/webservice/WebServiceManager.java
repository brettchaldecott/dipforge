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
 * WebServiceManager.java
 *
 * The web service manager. Responsible for controlling the loading and
 * unloading of web services.
 */

// package paths
package com.rift.coad.lib.deployment.webservice;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.deployment.DeploymentLoader;


/**
 * The web service manager. Responsible for controlling the loading and
 * unloading of web services.
 *
 * @author Brett Chaldecott
 */
public class WebServiceManager {
    
    /**
     * This class wrapps the web service list and supplies thread safe access
     * to the references held within.
     */
    public class WebServiceList {
        // classes private member variables
        private Map services = null;
        
        /**
         * The constructor of the web service list object.
         */
        public WebServiceList() {
            services = new HashMap();
        }
        
        
        /**
         * This method return a list of all the keys that identifying all the
         * services loaded by the web service manager.
         *
         * @return The set containing the web service information.
         */
        public synchronized Set getServices() {
            return services.keySet();
        }
        
        
        /**
         * This method adds the web service to the list of services.
         *
         * @param path The path of the object to add.
         * @param obj The object reference to add.
         */
        public synchronized void addService(String path, Object obj) {
            services.put(path,obj);
        }
        
        
        /**
         * This method returns the reference to the web service wrapper object.
         *
         * @return The reference to the object.
         * @param The path to retrieve.
         */
        public synchronized Object getService(String path) {
            return services.get(path);
        }
        
        
        /**
         * This method removes the service from the list of services.
         *
         * @param path The path to remove.
         */
        public synchronized void removeService(String path) {
            services.remove(path);
        }
        
        
        /**
         * This method return TRUE if the object is found FALSE if not.
         *
         * @param path The path to this object.
         */
        public synchronized boolean contains(String path) {
            return services.containsKey(path);
        }
    }
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(WebServiceManager.class.getName());
    
    // the private member variables
    private Map loaders = null;
    private WebServiceList serviceList = null;
    
    
    /** 
     * Creates a new instance of WebServiceManager.
     */
    public WebServiceManager() {
        loaders = new HashMap();
        serviceList = new WebServiceList();
    }
    
    
    /**
     * This method will load the specified web service using the deployment
     * loader.
     *
     * @param loader The reference to the loader object.
     * @exception WebServiceException
     */
    public void load(DeploymentLoader loader) throws WebServiceException {
        if (loaders.containsKey(loader)) {
            throw new WebServiceException(
                    "This entries has been loaded before.");
        }
        // load the web service using the a new web service loader
        WebServiceLoader serviceLoader = new WebServiceLoader(loader);
        Map services = serviceLoader.getServices();
        serviceClash(services);
        
        // add the services to the service list
        for (Iterator iter = services.keySet().iterator(); iter.hasNext();) {
            String path = (String)iter.next();
            log.info("Load the web service [" + path + "]");
            serviceList.addService(path, services.get(path));
        }
        
        // add the entry to the loaders list
        loaders.put(loader,serviceLoader);
        
    }
    
    
    /**
     * This method unloads the web services from the this object.
     *
     * @param loader The reference to the loader responsible for accessing this
     *          object.
     * @exception WebServiceException
     */
    public void unLoad(DeploymentLoader loader) throws WebServiceException {
        if (false == loaders.containsKey(loader)) {
            // do nothing there is nothing known about this entry
            return;
        }
        
        // retrieve a reference to the web service loader
        WebServiceLoader serviceLoader = (WebServiceLoader)loaders.get(loader);
        
        // remove the services
        Map services = serviceLoader.getServices();
        for (Iterator iter = services.keySet().iterator(); iter.hasNext();) {
            String path = (String)iter.next();
            log.info("Un-load the web service [" + path + "]");
            serviceList.removeService(path);
        }
        
        // remove the loader
        loaders.remove(loader);
    }
    
    /**
     * Retrieve the list of web servies.
     *
     * @return The list of web services managed by this object.
     */
    public Set getServices() {
        return serviceList.getServices();
    }
    
    
    /**
     * This method retrieve the service identified by the path
     *
     * @return The service identified by the path.
     * @param path The path identifying the path.
     */
    public Object getService(String path) {
        return serviceList.getService(path);
    }
    
    
    /**
     * This method checks to see if there is a clash with one of the classes
     * web services.
     *
     * @param services The list of services to make the check on.
     * @exception WebServiceException
     */
    private void serviceClash(Map services) throws WebServiceException {
        Set keySet = services.keySet();
        for (Iterator iter = keySet.iterator(); iter.hasNext();) {
            String path = (String)iter.next();
            if (serviceList.contains(path)) {
                throw new WebServiceException("The service with the path [" +
                        path + "] is already bound");
            }
        }
    }
    
}
