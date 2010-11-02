/*
 * MessageService: The message service daemon
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
 * ProcessMonitor.java
 */

package com.rift.coad.daemon.messageservice;

// logging import
import org.apache.log4j.Logger;


/**
 * The object responsible for monitoring the idle message processors.
 *
 * @author Brett Chaldecott
 */
public class ProcessMonitor {
    
    // singleton
    private static ProcessMonitor singleton = null;
    
    // the logger reference
    protected Logger log =
        Logger.getLogger(ProcessMonitor.class.getName());
    
    // private member variables
    private boolean terminated = false;
    
    /** 
     * Creates a new instance of ProcessMonitor 
     */
    private ProcessMonitor() {
    }
    
    
    /**
     * This method returns an instance of the process monitor.
     *
     * @return A reference to the process monitor singleton.
     */
    public static synchronized ProcessMonitor getInstance() {
        if (singleton == null) {
            singleton = new ProcessMonitor();
        }
        return singleton;
    }
    
    
    /**
     * This object will wait for the specified period of time before continuing.
     *
     * @param delay The length of time to delay processing.
     */
    public synchronized void monitor(long delay) {
        try {
            if (terminated) {
                return;
            }
            
            if (delay < 0) {
                log.warn("Negative value of [" + delay 
                        + "] supplied to monitor");
                return;
            } else if (delay == 0) {
                wait();
            } else {
                wait(delay);
            }
        } catch (Exception ex) {
            log.error("The monitor exited abnormally because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method notifies any waiting threads of the fact that they can
     * continue processing.
     */
    public synchronized void notifyProcessor() {
        notify();
    }
    
    
    /**
     * This method terminates this object so no threads will wait on it.
     */
    public synchronized void terminate() {
        terminated = true;
        notify();
    }
    
}
