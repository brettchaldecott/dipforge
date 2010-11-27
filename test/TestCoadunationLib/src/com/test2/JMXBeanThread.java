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
 * JMXBeanThread.java
 *
 * A test thread.
 */

// package path
package com.test2;

// imports
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.thread.BasicThread;
import com.extra.TestUtil;

/**
 * The test thread
 *
 * @author Brett Chaldecott
 */
public class JMXBeanThread extends BasicThread {
    
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
    private TestUtil testUtil = null;
    
    /**
     * Creates a new instance of JMXBeanThread
     */
    public JMXBeanThread() throws Exception {
        testUtil = new TestUtil();
    }
    
    
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process() throws Exception {
        do {
            int count = 0;
            if (notified == false) {
                try {
                    count++;
                    testUtil.printMessage("Message number [" + count 
                            + "] from : " +  this.getClass().getName());
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