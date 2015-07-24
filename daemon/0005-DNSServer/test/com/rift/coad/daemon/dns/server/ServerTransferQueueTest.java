/*
 * DNSServer: The dns server implementation.
 * Copyright (C) 2008  2015 Burntjam
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
 * ServerTransferQueueTest.java
 */

package com.rift.coad.daemon.dns.server;

import java.util.Date;
import junit.framework.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 *
 * @author brett
 */
public class ServerTransferQueueTest extends TestCase {
    
    public static class ServerTransferImpl implements ServerTransfer {
        
        // static data
        private static Map count = new HashMap();
        
        // private member variables
        private long touchTime = 0;
        private String zoneName = null;
        private long refreshTime = 0;
        private long expiryTime = 0;
        
        /**
         * The constructor of the server transfer test object implementation.
         *
         * @param zoneName The name of the zone.
         * @param requiresRefresh True if it requires a referesh.
         * @param expired True if expired.
         */
        public ServerTransferImpl(String zoneName, long refreshTime,
                long expiryTime) {
            this.zoneName = zoneName;
            this.touchTime = new Date().getTime();
            this.refreshTime = refreshTime;
            this.expiryTime = expiryTime;
        }
        
        
        /**
         * This method is called to return true if a refresh is required.
         *
         * @return TRUE if a refresh is required.
         */
        public boolean requiresRefresh() {
            long difference = touchTime - new Date().getTime();
            return difference < refreshTime;
        }
        
        
        /**
         * Returns true if this object is expired.
         *
         * @return TRUE if expired.
         */
        public boolean isExpired() {
            long difference = touchTime - new Date().getTime();
            return difference < expiryTime;
        }
        
        
        /**
         * This method is called to perform the transfer.
         *
         * @exception ServerException
         */
        public void performTransfer() throws ServerException {
            touchTime = 50 + new Date().getTime();
            called(zoneName);
            if (zoneName.equals("test3.com")) {
                throw new ServerException("Got test exception");
            }
        }
        
        
        /**
         * This method returns the time until refresh.
         */
        public long getTimeUntilRefresh() {
            long difference = touchTime - new Date().getTime() - refreshTime;
            if (difference < 0) {
                difference = 0;
            }
            return difference;
        }
        
        
        /**
         * This method returns the id of the server transfer object.
         */
        public String getZoneName() {
            return zoneName;
        }
        
        
        /**
         * This method compares this object to another as a result orders them
         * in the set.
         */
        public int compareTo(Object rhs) {
            if (rhs == this) {
                return 0;
            } else if (!(rhs instanceof ServerTransferImpl)) {
                return -1;
            }
            ServerTransferImpl zone = (ServerTransferImpl)rhs;
            boolean rhsIsExpired = zone.isExpired();
            boolean expired = isExpired();
            boolean rhsRefresh = zone.requiresRefresh();
            boolean refresh = requiresRefresh();
            long rhsRefreshTime = zone.getTimeUntilRefresh();
            long refreshTime = getTimeUntilRefresh();
            if ((rhsIsExpired && expired) || (rhsRefresh && refresh) ||
                    (rhsRefreshTime == refreshTime)) {
                System.out.println("Compare the zone names");
                return getZoneName().compareTo(zone.getZoneName());
            } else if ((rhsIsExpired && !expired) || (rhsRefresh && !refresh)) {
                return 1;
            } else if ((!rhsIsExpired && expired) || (!rhsRefresh && refresh)) {
                return -1;
            }
            System.out.println("Compare refresh times : " +
                    (rhsRefreshTime - refreshTime));
            return (int)(refreshTime - rhsRefreshTime);
        }
        
        
        /**
         * This method checks for equals on the current object.
         *
         * @param rhs The object to compare to.
         * @return TRUE if equals false if not.
         */
        public boolean equals(Object rhs) {
            if (rhs == this) {
                return true;
            } else if (!(rhs instanceof ServerTransferImpl)) {
                return false;
            }
            ServerTransferImpl zone = (ServerTransferImpl)rhs;
            return getZoneName().equals(zone.getZoneName());
        }
        
        
        /**
         * This method set the called list
         */
        public static void called(String zoneName) {
            synchronized (count) {
                if (!count.containsKey(zoneName)) {
                    count.put(zoneName,new Integer(1));
                } else {
                    int i = (Integer)count.get(zoneName) + 1;
                    count.put(zoneName,new Integer(i));
                }
                count.notifyAll();
            }
        }
        
        
        /**
         * This method is called to check
         */
        public static void check(int entries, int times) {
            synchronized (count) {
                while(true) {
                    if (count.size() < entries) {
                        try {
                            count.wait();
                        } catch (Exception ex) {
                            System.out.println("Failed to wait : " +
                                    ex.getMessage());
                        }
                        continue;
                    }
                    boolean found = true;
                    for (Iterator iter = count.keySet().iterator();
                    iter.hasNext();) {
                        if ((Integer)count.get(iter.next()) < times) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        System.out.println("Found test results");
                        break;
                    }
                    try {
                        System.out.println("The entries have all been called.");
                        count.wait();
                    } catch (Exception ex) {
                        System.out.println("Failed to wait : " +
                                ex.getMessage());
                    }
                }
            }
        }
    }
    
    
    public ServerTransferQueueTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of run method, of class com.rift.coad.daemon.dns.server.ServerTransferQueue.
     */
    public void testServerTransferQueue() throws Exception {
        System.out.println("ServerTransferQueue");
        
        ServerTransferQueue instance = new ServerTransferQueue();
        
        instance.addServerTransfer(new ServerTransferImpl("test1.com",10,5));
        instance.addServerTransfer(new ServerTransferImpl("test2.com",10,5));
        instance.addServerTransfer(new ServerTransferImpl("test3.com",10,5));
        
        instance.start();
        
        ServerTransferImpl.check(3,3);
        
        instance.terminate();
        
        instance.join();
        
        List errors = instance.getTransferErrors();
        
        if (errors.size() < 3) {
            fail("Not enough errors");
        }
        assertEquals("Got test exception", errors.get(0));
        assertEquals("Got test exception", errors.get(1));
        assertEquals("Got test exception", errors.get(2));
    }
    
    
    /**
     * Test of run method, of class com.rift.coad.daemon.dns.server.ServerTransferQueue.
     */
    public void testRemove() throws Exception {
        System.out.println("remove");
        
        ServerTransferQueue instance = new ServerTransferQueue();
        
        instance.addServerTransfer(new ServerTransferImpl("test1.com",10,5));
        instance.addServerTransfer(new ServerTransferImpl("test2.com",10,5));
        instance.addServerTransfer(new ServerTransferImpl("test3.com",10,5));
        
        assertEquals(3, instance.getNumberSecondaries());
        
        instance.removeServerTransfer(new ServerTransferImpl("test3.com",10,5));
        try {
            instance.removeServerTransfer(new ServerTransferImpl("test3.com",10,5));
            fail("Could remove an entry.");
        } catch (Exception ex) {
            // ignore
        }
        assertEquals(2, instance.getNumberSecondaries());
    }
    
    
}
