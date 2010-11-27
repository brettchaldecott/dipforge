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
 * BeanCacheTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.bean;

import junit.framework.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import javax.rmi.PortableRemoteObject;
import org.apache.log4j.Logger;
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

/**
 *
 * @author Brett Chaldecott
 */
public class BeanCacheTest extends TestCase {
    
    /**
     * The cache entry to add
     */
    public static class TestCacheEntry implements CacheEntry, java.rmi.Remote {
        
        // touch date
        private Date touchTime = new Date();
        public static int count = 0;
        
        /**
         * The constructor of the test cache entry object.
         */
        public TestCacheEntry() {
            
        }
        
        
        /**
         * The touch method of the test cache entry object.
         */
        public void touch() {
            touchTime = new Date();
        }
        
        
        /**
         * This method returns true if this object has expired.
         *
         * @return TRUE if expired FALSE if not.
         * @param expiryDate The date of the expiry.
         */
        public boolean isExpired(Date expiryDate) {
            System.out.println("Touch time : " + touchTime.getTime() + 
                    " expiry date : " + expiryDate.getTime());
            return touchTime.getTime() < expiryDate.getTime();
        }
        
        
        /**
         * Release the cache
         */
        public void cacheRelease() {
            count++;
        }
    }
    
    public BeanCacheTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }


    public static Test suite() {
        TestSuite suite = new TestSuite(BeanCacheTest.class);
        
        return suite;
    }

    /**
     * Test of testCache method, of class com.rift.coad.lib.bean.BeanCache.
     */
    public void testCache() throws Exception {
        System.out.println("garbageCollect");
        
        BeanCache instance = new BeanCache();
        
        TestCacheEntry bob = new TestCacheEntry();
        instance.addCacheEntry(600,"bob","bob",bob);
        instance.addCacheEntry(600,"bob","bob",bob);
        TestCacheEntry fred = new TestCacheEntry();
        instance.addCacheEntry(500,"fred","fred",fred);
        instance.addCacheEntry(500,"fred","fred",fred);
        TestCacheEntry mary = new TestCacheEntry();
        instance.addCacheEntry(600,"mary","mary","mary",mary);
        
        if (bob != instance.getEntry("bob").getCacheEntry()) {
            fail("bob could not be found");
        }
        instance.getEntry("bob").setCacheEntry(mary);
        if (mary != instance.getEntry("bob").getCacheEntry()) {
            fail("mary could not be found");
        }
        if (mary != instance.getEntry("mary").getBeanHandler()) {
            fail("mary could not be found");
        }
        instance.getEntry("mary").setBeanHandler(bob);
        if (bob != instance.getEntry("mary").getBeanHandler()) {
            fail("bob could not be found");
        }
        if (!instance.getEntry("mary").getProxy().equals("mary")) {
            fail("mary could not be found");
        }
        instance.getEntry("mary").setProxy("mary1");
        if (!instance.getEntry("mary").getProxy().equals("mary1")) {
            fail("mary1 could not be found");
        }
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            bob.touch();
            mary.touch();
        }
        
        instance.garbageCollect();
        
        if (!instance.contains("bob")) {
            fail("bob could not be found");
        }
        if (!instance.contains("mary")) {
            fail("mary could not be found");
        }
        if (instance.contains("fred")) {
            fail("fred was found");
        }
        
        instance.clear();
        
        if (instance.contains("bob")) {
            fail("bob could still be found");
        }
        if (instance.contains("mary")) {
            fail("bob could still be found");
        }
        
        try {
            instance.addCacheEntry(500,"mary","mary","mary",mary);
            fail("Could add an entry should not be allowed");
        } catch (BeanException ex) {
            // ingore
        }
        try {
            instance.addCacheEntry(500,"bob","bob",bob);
            fail("Could add an entry should not be allowed");
        } catch (BeanException ex) {
            // ingore
        }
        
        if (TestCacheEntry.count != 3) {
            fail("Release not called on all classes");
        }
    }

    
}
