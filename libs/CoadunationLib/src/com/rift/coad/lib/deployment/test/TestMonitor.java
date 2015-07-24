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
 * TestMonitor.java
 *
 * This file contains the definition of the test monitor. It can be setup to
 * monitor a test such as the loading of a test jar.
 */

// The package path
package com.rift.coad.lib.deployment.test;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Date;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This file contains the definition of the test monitor. It can be setup to
 * monitor a test such as the loading of a test jar.
 *
 * @author Brett Chaldecott
 */
public class TestMonitor {
    
    // class static singleton object
    private static TestMonitor singleton = null;
    
    // the class member variables
    private long timeout = 0;
    private List entries = null;
    
    
    /** 
     * Creates a new instance of TestMonitor
     *
     * @exception TestException
     */
    private TestMonitor() throws TestException {
        try {
            // init the array list
            entries = new ArrayList();
            
            // retrieve the configuration
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                        this.getClass());
            
            // create the string tokenizer
            timeout = config.getLong("Timeout");
            StringTokenizer stringTok = new StringTokenizer(
                    config.getString("Monitor"),",");
            while (stringTok.hasMoreTokens()) {
                entries.add(stringTok.nextToken());
            }
            
        } catch (Exception ex) {
            throw new TestException("Failed to init the test monitor.",ex);
        }
    }
    
    
    /**
     * This method is responsible for initializing the test monitor singleton.
     *
     * @exception TestException
     */
    public static synchronized void init() throws TestException {
        singleton = new TestMonitor();
    }
    
    
    /**
     * This method returns an instance of the test monitor object.
     *
     * @return The reference to the test object.
     * @exception TestException
     */
    public static synchronized TestMonitor getInstance() throws TestException {
        if (singleton != null) {
            return singleton;
        }
        throw new TestException("The test monitor has not been initialized.");
    }
    
    
    /**
     * This method alerts the monitoring thread to the fact that it is complete.
     *
     * @param element The element to monitor.
     * @exception TestException
     */
    public synchronized void alert(String element) throws TestException {
        if (entries.contains(element)) {
            entries.remove(element);
            notify();
        }
    }
    
    
    /**
     * This method will be called to monitor the test to determine if the test
     * was successfull.
     *
     * @exception TestException
     */
    public synchronized void monitor() throws TestException {
        try {
            Date startTime = new Date();
            while (entries.size() > 0) {
                wait(timeout);
                Date currentTime = new Date();
                if ((currentTime.getTime() - timeout) > startTime.getTime()) {
                    break;
                }
            }
        } catch (Exception ex) {
            throw new TestException("Monitoring failed because : " + 
                    ex.getMessage(),ex);
        }
        
        // the entries to monitor
        if (entries.size() > 0) {
            String entryString = new String();
            String sep = "";
            for (int count = 0; count < entries.size(); count++) {
                entryString += sep + entries.get(count).toString();
                sep = ":";
            }
            throw new TestException(
                    "Monitor test failed because not all monitored entries " +
                    "reported successfully [" + entryString + "]");
        }
    }
}
