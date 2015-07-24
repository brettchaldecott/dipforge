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
 * DeploymentThreadInfo.java
 *
 * This class contains the thread information for either for a bean or jmx bean
 * deployment.
 */

// the package path
package com.rift.coad.lib.deployment;

/**
 * This class contains the thread information for either for a bean or jmx bean
 * deployment.
 *
 * @author Brett Chaldecott
 */
public class DeploymentThreadInfo {
    
    // the deployment information
    private String className = "";
    private String username = "";
    private long threadNumber = 0;
    
    /** 
     * Creates a new instance of DeploymentThreadInfo 
     */
    public DeploymentThreadInfo() {
    }
    
    
    /**
     * This method returns the name of the thread class.
     *
     * @return The string containing the class name of the thread to run.
     */
    public String getClassName() {
        return className;
    }
    
    
    /**
     * This method sets the name of the thread class.
     *
     * @param className The name of the class to run.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    
    /**
     * This method returns the name of the user.
     *
     * @return The string containing the user name.
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * This method set the name of the user that the threads will run as.
     *
     * @param username;
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * This method returns the number of threads that will be started.
     *
     * @return The number of threads to start.
     */
    public long getThreadNumber() {
        return threadNumber;
    }
    
    
    /**
     * Sets the number of threads to run
     *
     * @param threadNumber The number of threads to start.
     */
    public void setThreadNumber(long threadNumber) {
        this.threadNumber = threadNumber;
    }
    
    
    /**
     * This method returns false if this object is not initialized correctly and
     * true if it is.
     *
     * @return TRUE if initialized correctly and FALSE if not.
     */
    public boolean isInitialized() {
        if ((className == null) || (threadNumber == 0)) {
            return false;
        }
        return true;
    }
}
