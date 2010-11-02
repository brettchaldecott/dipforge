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
 * BeanCacheManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.bean;

import junit.framework.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;

/**
 *
 * @author mincemeat
 */
public class BeanCacheManagerTest extends TestCase {
    
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
         * cache release
         */
        public void cacheRelease() {
            count++;
        }
    }
    
    public BeanCacheManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BeanCacheManagerTest.class);
        
        return suite;
    }

    /**
     * Test of BeanCacheManager method, of class com.rift.coad.lib.bean.BeanCacheManager.
     */
    public void testBeanCacheManager() throws Exception {
        System.out.println("BeanCacheManager");
        
        Object ref = null;
        BeanCacheManager instance = new BeanCacheManager();
        
        BeanCache beanCache1 = instance.getBeanCache("beanCache1");
        BeanCache beanCache2 = instance.getBeanCache("beanCache2");
        if (instance.getBeanCache("beanCache1") != beanCache1) {
            fail("Failed to retrieve the bean cache result object");
        }
        if (!instance.contains("beanCache1")) {
            fail("The instance does not contain beanCache1");
        }
        if (instance.getBeanCache("beanCache2") != beanCache2) {
            fail("Failed to retrieve the bean cache 2 result object");
        }
        if (!instance.contains("beanCache2")) {
            fail("The instance does not contain beanCache2");
        }
        
        TestCacheEntry bob = new TestCacheEntry();
        beanCache1.addCacheEntry(600,"bob","bob",bob);
        TestCacheEntry fred = new TestCacheEntry();
        beanCache1.addCacheEntry(500,"fred","fred",fred);
        TestCacheEntry mary = new TestCacheEntry();
        beanCache2.addCacheEntry(700,"mary","mary","mary",mary);
        TestCacheEntry jill = new TestCacheEntry();
        beanCache2.addCacheEntry(500,"jill","jill","jill",jill);
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            bob.touch();
            mary.touch();
        }
        
        instance.garbageCollect();
        
        if (!beanCache1.contains("bob")) {
            fail("bob could not be found");
        }
        if (!beanCache2.contains("mary")) {
            fail("mary could not be found");
        }
        if (beanCache1.contains("fred")) {
            fail("fred was found");
        }
        if (beanCache2.contains("jill")) {
            fail("jill was found");
        }
        
        
        instance.clear();
        
        if (beanCache1.contains("bob")) {
            fail("bob could still be found");
        }
        if (beanCache2.contains("mary")) {
            fail("mary could still be found");
        }
        
        try {
            beanCache1.addCacheEntry(500,"mary","mary","mary",mary);
            fail("Could add an entry should not be allowed");
        } catch (BeanException ex) {
            // ingore
        }
        try {
            beanCache2.addCacheEntry(500,"bob","bob",bob);
            fail("Could add an entry should not be allowed");
        } catch (BeanException ex) {
            // ingore
        }
        
        try {
            beanCache2 = instance.getBeanCache("beanCache2");
            fail("Could add an entry to the bean cache manager.");
        } catch (BeanException ex) {
            // ignore
        }
        
        if (TestCacheEntry.count != 4) {
            fail ("Release not called on all classes");
        }
    }

    
}
