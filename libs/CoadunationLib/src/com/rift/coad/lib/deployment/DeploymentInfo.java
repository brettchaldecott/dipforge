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
 * XMLConfigurationException.java
 *
 * DeploymentInfo.java
 *
 * The class that stores all the deployment information.
 */

// class imports
package com.rift.coad.lib.deployment;

// the private member variables
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The 
 *
 * @author Brett Chaldecott
 */
public class DeploymentInfo {
    
    /**
     * The information pertaining to this jar deployment object
     */
    private String version = null;
    private String name = null;
    private String description = null;
    private Map webServices = null;
    private Map jmxBeans = null;
    private Map beans = null;
    
    
    /** 
     * Creates a new instance of DeploymentInfo 
     */
    public DeploymentInfo() {
        webServices = new LinkedHashMap();
        jmxBeans = new LinkedHashMap();
        beans = new LinkedHashMap();
    }
    
    
    /**
     * This method returns the version information.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * This method set the version information.
     *
     * @param version The version of the coadunation deployment
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * The getter method for the name information.
     *
     * @return The name of the deployment jar.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Set the name of for the deployment jar.
     *
     * @param name The name of the deployment jar.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * The description for the deployment information.
     *
     * @return The string containing the description of the deployment 
     *  information
     */
    public String getDescription() {
        return description;
    }
    
    
    /**
     * This method will set the description for this deployment object.
     *
     * @param description The new description for this deployment object.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Retrieve the list of web services.
     *
     * @return The list of web services.
     */
    public Map getWebServices() {
        return webServices;
    }
    
    
    /**
     * This method will add a new web service
     *
     * @param webServiceInfo The class containing the web service information
     */
    public void addWebService(WebServiceInfo webServiceInfo) {
        webServices.put(webServiceInfo.getClassName(),webServiceInfo);
    }
    
    /**
     * Retrieve the list of means for the deployment.
     *
     * @return Return the list of jmx beans.
     */
    public Map getJmxBeans() {
        return jmxBeans;
    }
    
    /**
     * This method will add a new JMX bean to the list of beans.
     *
     * @param beanInfo The bean info object.
     */
    public void addJmxBean(JMXBeanInfo jmxBeanInfo) {
        jmxBeans.put(jmxBeanInfo.getClassName(),jmxBeanInfo);
    }
    
    /**
     * This method will return the list of beans.
     */
    public Map getBeans() {
        return beans;
    }
    
    /**
     * This method will add a bean object to the list of bean object.
     *
     * @param beanInfo The information about a coadunation bean.
     */
    public void addBean(BeanInfo beanInfo) {
        beans.put(beanInfo.getClassName(),beanInfo);
    }
}
