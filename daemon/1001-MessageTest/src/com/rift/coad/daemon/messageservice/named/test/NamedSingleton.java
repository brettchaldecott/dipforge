/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 2015 Burntjam
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
 * NamedSingleton.java
 */

package com.rift.coad.daemon.messageservice.named.test;

// java imports
import java.util.Date;

// log imports
import org.apache.log4j.Logger;

/**
 * This object is used to test the named queue.
 *
 * @author Brett Chaldecott
 */
public class NamedSingleton {
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(NamedSingleton.class.getName());
    
    // singleton
    private static NamedSingleton singleton = null;
    
    // private member variables
    private int count = 0;
    
    /** Creates a new instance of NamedSingleton */
    private NamedSingleton() {
        
    }
    
    /**
     * This method returns an instance of the named singleton.
     *
     * @return The instance of the named singleton.
     */
    public synchronized static NamedSingleton getInstance() {
        if (singleton == null) {
            singleton = new NamedSingleton();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the count.
     *
     * @return The count value.
     */
    public synchronized int incrementCount() {
        count++;
        notify();
        return count;
    }
    
    /**
     * This method resets the count.
     */
    public synchronized void resetCount() {
        count = 0;        
    }
    
    
    /**
     * This method checks on the count;
     */
    public synchronized boolean checkCount(int number, long timeout) {
        Date startTime = new Date();
        while(number > count) {
            Date currentTime = new Date();
            long difference = (startTime.getTime() + timeout) - 
                    currentTime.getTime();
            if (number <= count) {
                return true;
            } else if (difference <= 0) {
                return false;
            }
            try {
                wait(difference);
            } catch (Exception ex) {
                log.error("Failed to wait for more entries");
            }
        }
        return true;
    }
}
