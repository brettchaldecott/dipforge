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
 * RMICacheTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.rmi;

import junit.framework.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.Set;
import javax.rmi.PortableRemoteObject;
import org.apache.log4j.Logger;
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 *
 * @author Brett Chaldecott
 */
public class RMICacheTest extends TestCase {
    
    /**
     * The test cache class
     */
    public static class RMICacheTestClass implements java.rmi.Remote, 
            CacheEntry {
        // private member variables
        private String id = null;
        private Date lastTouchTime = new Date();
        public static int count = 0;
        
        /**
         * The constructor of the cache entry
         */
        public RMICacheTestClass () throws Exception {
            id = RandomGuid.getInstance().getGuid();
        }
        
        
        /**
         * This method will return true if the date is older than the given expiry
         * date.
         *
         * @return TRUE if expired FALSE if not.
         * @param expiryDate The expiry date to perform the check with. 
         */
        public boolean isExpired(Date expiryDate) {
            System.out.println("Current time : " + lastTouchTime.getTime());
            System.out.println("Expiry time : " + expiryDate.getTime());
            return (lastTouchTime.getTime() < expiryDate.getTime());
        }
        
        /**
         * Release the count
         */
        public void cacheRelease() {
            count++;
        }
        
        /**
         * The touch method
         */
        public void touch() {
            lastTouchTime = new Date();
        }
        
    }
    
    
    public RMICacheTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RMICacheTest.class);
        
        return suite;
    }

    /**
     * Test of garbageCollect method, of class com.rift.coad.lib.deployment.rmi.RMICache.
     */
    public void testRMICache() throws Exception {
        System.out.println("testRMICache");
        
        RMICache instance = new RMICache();
        
        
        RMICacheTestClass cacheObject1 = new RMICacheTestClass();
        RMICacheTestClass cacheObject2 = new RMICacheTestClass();
        
        instance.addCacheEntry(500,cacheObject1);
        instance.addCacheEntry(500,cacheObject2);
        
        System.out.println("Start time is : " + new Date().getTime());
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            cacheObject1.touch();
        }
        System.out.println("End time is : " + new Date().getTime());
        
        instance.garbageCollect();
        
        if (!instance.contains(cacheObject1)) {
            fail("Cache does not contain cache object1");
        } else if (instance.contains(cacheObject2)) {
            fail("Cache contains cache object2");
        }
        
        instance.clear();
        
        if (instance.contains(cacheObject1)) {
            fail("Cache contains cache object1");
        }
        
        try {
            instance.addCacheEntry(500,cacheObject1);
            fail("The cache has not been invalidated");
        } catch (RMIException ex) {
            // ignore
        }
        
        if (RMICacheTestClass.count != 2) {
            fail("Release was not called on both classes");
        }
    }

    
    
}
