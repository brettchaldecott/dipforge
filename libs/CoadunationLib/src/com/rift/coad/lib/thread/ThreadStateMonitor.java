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
 * ThreadStateMonitor.java
 *
 * This object is responsible for monitoring the state of a thread.
 */

package com.rift.coad.lib.thread;

/**
 * This object is responsible for monitoring the state of a thread.
 *
 * @author Brett Chaldecott
 */
public class ThreadStateMonitor {
    
    // the private member variables
    private boolean terminated = false;
    private long delay = 0;
    
    /** 
     * Creates a new instance of ThreadStateMonitor
     */
    public ThreadStateMonitor() {
    }
    
    
    /**
     * The constructor that sets the delay time for the thread state monitor.
     *
     * @param delay The length of time the thread should what before continuing
     *          to the next processing iteration.
     */
    public ThreadStateMonitor(long delay) {
        this.delay = delay;
    }
    
    
    /**
     * This method returns true if this object is terminated and false if it
     * is not.
     *
     * @return TRUE if terminated, FALSE if not.
     */
    public synchronized boolean isTerminated() {
        return terminated;
    }
    
    
    /**
     * This method sets the terminated flag to true and notifies waiting threads
     * of this fact.
     *
     * @param broadCast Set to true if there can be more than one thread blocking.
     */
    public synchronized void terminate(boolean broadCast) {
        terminated = true;
        if (broadCast) {
            notifyAll();
        } else {
            notify();
        }
    }
    
    
    /**
     * This method will get called to monitor the state of the thread.
     */
    public synchronized void monitor() {
        try {
            if (terminated) {
                return;
            }
            if (delay > 0) {
                wait(delay);
            } else {
                wait();
            }
        } catch (Exception ex) {
            // ignore
        }
    }
    
    
    /**
     * This method will get called to monitor the state of the thread.
     * 
     * @param delay The duration to delay
     */
    public synchronized void monitor(long delay) {
        try {
            if (terminated) {
                return;
            }
            if (delay > 0) {
                wait(delay);
            } else {
                wait();
            }
        } catch (Exception ex) {
            // ignore
        }
    }
    
    
    /**
     * This method is called to notify the sleeping thread.
     */
    public synchronized void notifyThread() {
        notifyAll();
    }
}
