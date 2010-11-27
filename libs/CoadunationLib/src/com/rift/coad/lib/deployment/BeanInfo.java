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
 * BeanInfo.java
 *
 * The object responsible for storing the information about a Coaduantion bean.
 */

package com.rift.coad.lib.deployment;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;


/**
 * The object that stores the information about a Coadunation bean.
 *
 * @author Brett Chaldecott
 */
public class BeanInfo {
    
    // the classes private member variables
    private String interfaceName = null;
    private String className = null;
    private Vector classes = new Vector();
    private String bindName = null;
    private String role = null;
    private String username = null;
    private boolean cacheResults = false;
    private long cacheTimeout = -1;
    private List threadInfoList = new ArrayList();
    private boolean transaction = false;
    
    
    /** 
     * Creates a new instance of BeanInfo.
     */
    public BeanInfo() {
        
    }
        
    
    /**
     * The getter method for the interface name.
     *
     * @return The string containing the interface name.
     */
    public String getInterfaceName() {
        return interfaceName;
    }
    
    
    /**
     * The setter method for the interface name variable.
     *
     * @param interfaceName The interface name to set.
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
    
    /**
     * The getter method for the class name.
     *
     * @return The string containing the class name.
     */
    public String getClassName() {
        return className;
    }
    
    
    /**
     * This setter method for the class name member variable.
     *
     * @param className The name value for the class to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    
    /**
     * The getter method for the list of classes.
     *
     * @return The vector containing the list of classes.
     */
    public Vector getClasses() {
        return classes;
    }
    
    
    /**
     * The setter method of the extra classes.
     *
     * @param classes The list of classes.
     */
    public void setClasses(Vector classes) {
        this.classes = classes;
    }
    
    
    /**
     * The setter method of the extra classes.
     *
     * @param classes The list of classes.
     */
    public void addClass(String className) {
        this.classes.add(className);
    }
    
    
    /**
     * The getter method for the bind name.
     *
     * @return The string containing the bind name.
     */
    public String getBindName() {
        return bindName;
    }
    
    
    /**
     * The setter method for the bind name member variable.
     *
     * @param bindName The name of the bind variable.
     */
    public void setBindName(String bindName) {
        this.bindName = bindName;
    }
    
    
    /**
     * The getter method for the role name.
     *
     * @return The string containing the role name.
     */
    public String getRole() {
        return role;
    }
    
    
    /**
     * The setter method for the role name.
     *
     * @param The name of the role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    /**
     * The name of the user that this bean will run as.
     *
     * @return The string containing the username information.
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * This method sets the username for the bean.
     *
     * @param username The name of the user that the bean will run as.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * The getter for the cache results method.
     *
     * @return TRUE if set, FALSE if not.
     */
    public boolean getCacheResults() {
        return cacheResults;
    }
    
    
    /**
     * The setter method for the cache results object.
     *
     * @param cacheResults The value to set the cache results flag to.
     */
    public void setCacheResults(boolean cacheResults) {
        this.cacheResults = cacheResults;
    }
    
    
    /**
     * The getter for the cache time out.
     *
     * @return A long containing the cache timeout information.
     */
    public long getCacheTimeout() {
        return cacheTimeout;
    }
    
    
    /**
     * The setter method for the cache time out.
     *
     * @param cacheResults The value to set the cache results flag to.
     */
    public void setCacheTimeout(long cacheTimeout) {
        this.cacheTimeout= cacheTimeout;
    }
    
    
    /**
     * The list of threads.
     *
     * @return The list of threads for this bean.
     */
    public List getThreadInfoList() {
        return threadInfoList;
    }
    
    
    /**
     * This method will add a thread to the list of threads.
     *
     * @param threadInfo The thread information to add to the list.
     */
    public void addThreadInfo(DeploymentThreadInfo threadInfo) {
       threadInfoList.add(threadInfo);
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
     * This method will return true if all the member variables have been
     * initialized.
     *
     * @return TRUE if initialized FALSE if not.
     */
    public boolean isInitialized() {
        if ((interfaceName != null) && (className != null) && (bindName != null)
            && (role != null)) {
            return true;
        }
        return false;
    }
}
