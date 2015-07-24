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
 * XMLConfigurationException.java
 *
 * WebServiceInfo.java
 *
 * The class containing the web service information
 */

// package imports
package com.rift.coad.lib.deployment;

// java imports
import java.util.Vector;

/**
 * This class contains web service information
 *
 * @author Brett Chaldecott
 */
public class WebServiceInfo {
    
    // member variables
    private String path = null;
    private String className = null;
    private String wsdlPath = null;
    private String role = null;
    private boolean transaction = false;
    private Vector extraClassNames = new Vector();
    
    
    /** 
     * Creates a new instance of WebServiceInfo 
     */
    public WebServiceInfo() {
    }
    
    
    /**
     * The getter method for the path.
     *
     * @return The string containing the path information.
     */
    public String getPath() {
        return path;
    }
    
    
    /**
     * This method will set the path information for the web service.
     *
     * @param path The path the web service will be available on.
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
    /**
     * This getter method for the class name.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }
    
    
    /**
     * The method responsible for setting the class name.
     *
     * @param className The name of the class to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    
    /**
     * This method will return the path to the WSDL file
     *
     * @return The string containing the wsdl path.
     */
    public String getWSDLPath() {
        return wsdlPath;
    }
    
    
    /**
     * The method responsible for setting the path to the WSDL file.
     *
     * @param WSDLPath The name of the target to set.
     */
    public void setWSDLPath(String wsdlPath) {
        this.wsdlPath = wsdlPath;
    }
    
    
    /**
     * The role information
     *
     * @return The name of the role for this web service.
     */
    public String getRole() {
        return role;
    }
    
    
    /**
     * This method sets the role information.
     *
     * @param role The role that the web service will run as.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    /**
     * This method returns true if the container must control the transaction.
     *
     * @return TRUE if it must control the transaction.
     */
    public boolean getTransaction() {
        return transaction;
    }
    
    
    /**
     * This method sets the transaction
     *
     * @param TRUE if this container must control the transaction.
     */
    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }
    
    
    /**
     * This method will return true if all values have been correctly initialized
     *
     * @return TRUE if all values have been initialized.
     */
    public boolean isInitialized() {
        if ((this.className != null) && (this.path != null) && 
                (this.role != null) && (this.wsdlPath != null)) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method adds to the classes list.
     *
     * @param className The class name to add to the list.
     */
    public void addToClasses(String className) {
        extraClassNames.add(className);
    }
    
    
    /**
     * This method returns the list of class names for this web service.
     *
     * @return The list of classes.
     */
    public Vector getClasses() {
        return extraClassNames;
    }
}
