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
 * JMXBean1.java
 *
 * The test jmx bean
 */

// package path
package com.test2;

// java imports
import java.rmi.RemoteException;

// coaduntion imports
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.bean.BeanRunnable;

/**
 * The test jmx bean
 *
 * @author Brett Chaldecott
 */
public class JMXBean1 implements JMXBean1MBean,BeanRunnable {
    
    /**
     * The class responsible for controling the terminate flag.
     */
    public class RunState {
        // terminated flag
        private boolean terminated = false;
        
        /**
         * This method will check the terminated flag.
         */
        public RunState() {
        }
        
        
        /**
         * This method will return true if this object has been terminated.
         *
         * @return TRUE if terminated, FALSE if not.
         */
        public synchronized boolean isTerminated() {
            try {
                if (terminated) {
                    return terminated;
                }
                wait(500);
                return terminated;
            } catch(Exception ex) {
                // ignore any exeption
                return terminated;
            }
        }
        
        
        /**
         * This method will set the state of the terminated flag to true.
         */
        public synchronized void terminate() {
            terminated = true;
            notify();
        }
    }
    
    // class member variables
    private boolean notified = false;
    private RunState state = new RunState();
    
    /** 
     * Creates a new instance of JMXBean1 
     */
    public JMXBean1() {
    }
    
    /**
     * The test JMX Bean.
     *
     * @param msg The message for the server.
     * @return The string message for the client
     */
    public String helloWorld(String msg) {
        System.out.println("Client message: " + msg);
        return "Server message";
    }
    
    
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process() {
        do {
            if (notified == false) {
                try {
                    TestMonitor.getInstance().alert(this.getClass().getName());
                } catch (Exception ex) {
                    // ignore
                }
                notified = true;
            }
        } while (state.isTerminated() == false);
    }
    
    
    /**
     * This method is called to soft terminate the processing thread.
     */
    public void terminate() {
        state.terminate();
    }
}
