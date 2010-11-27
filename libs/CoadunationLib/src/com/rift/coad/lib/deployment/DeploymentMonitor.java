/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007 Rift IT Contracting
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
 * DeploymentMonitor.java
 */

// package path
package com.rift.coad.lib.deployment;

// logging import
import org.apache.log4j.Logger;

/**
 * This object is responsible for supplying information about the deployment
 * process.
 *
 * @author Brett Chaldecott
 */
public class DeploymentMonitor {
    
    // class singleton
    private static DeploymentMonitor singleton = null;
    private static Logger log = Logger.getLogger(DeploymentMonitor.class);
    
    // private member variables
    private boolean initDeployComplete = false;
    private boolean terminated = false;
    
    /** 
     * Creates a new instance of DeploymentMonitor 
     */
    private DeploymentMonitor() {
    }
    
    
    /**
     * This method returns the instance of the DeploymentMonitor.
     */
    public static synchronized DeploymentMonitor getInstance() {
        if (singleton == null) {
            singleton = new DeploymentMonitor();
        }
        return singleton;
    }
    
    
    /**
     * This method returns true if the initial deploy is complete and false if
     * it is not.
     *
     * @return TRUE if initial deploy complete, FALSE if not.
     */
    public synchronized boolean isInitDeployComplete() {
        return initDeployComplete;
    }
    
    
    /**
     * This method will mark the initial deploy as completed.
     */
    public synchronized void initDeployCompleted() {
        if (!initDeployComplete) {
            log.info("Initial deployment complete");
        }
        initDeployComplete = true;
        notifyAll();
        
    }
    
    
    /**
     * This method is called to check if the deployment process is terminated.
     */
    public synchronized boolean isTerminated() {
        return terminated;
    }
    
    
    /**
     * This method terminates the deployment process.
     */
    public synchronized void terminate() {
        terminated = true;
        notifyAll();
    }
    
    
    /**
     * This method when called will wait until the deployment process is 
     * complete.
     */
    public synchronized void waitUntilInitDeployComplete() {
        try {
            if (!initDeployComplete && !terminated) {
                wait();
            }
        } catch (Exception ex) {
            // ignore
        }
    }
}
