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
 * CacheRegistryTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.cache;

import junit.framework.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.log4j.Logger;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;


/**
 *
 * @author mincemeat
 */
public class CacheRegistryTest extends TestCase {
    
    public static class CacheEntry {
        // the member variables
        private Date touchTime = new Date();
        private String name = null;
        
        /**
         * The constructor of the cache entry.
         *
         * @param name The name of the entry.
         */
        public CacheEntry (String name) {
            this.name = name;
        }
        
        
        /**
         * This method returns the name of this object.
         */
        public String getName() {
            return name;
        }
        
        
        /**
         * The touch time.
         */
        public synchronized void touch() {
            touchTime = new Date();
        }
        
        
        /**
         * This method returns true if this object is expired.
         *
         * @return TRUE if expired FALSE if not.
         * @param expiryDate The date to be new than in order to remain in
         *          memory.
         */
        public synchronized boolean isExpired(Date expiryDate) {
            if (touchTime.getTime() < expiryDate.getTime()) {
                return true;
            }
            return false;
        }
    }
    
    public static class TestCache implements Cache {
        
        private Map cacheEntries = new HashMap();
        
        /**
         * The constructor of the test cache.
         */
        public TestCache() {
            
        }
        
        /**
         * This method is called to perform garbage collection on the cache entries.
         */
        public synchronized void garbageCollect() {
            Iterator iter = cacheEntries.keySet().iterator();
            Date expiryDate = new Date(new Date().getTime() - 500);
            while(iter.hasNext()) {
                String key = (String)iter.next();
                CacheEntry entry = (CacheEntry)cacheEntries.get(key);
                if (entry.isExpired(expiryDate)) {
                    // remove an reset
                    cacheEntries.remove(key);
                    iter = cacheEntries.keySet().iterator();
                }
            }
        }


        /**
         * This method is called to forcibly remove everything from the cache.
         */
        public synchronized void clear() {
            cacheEntries.clear();
        }
        
        
        /**
         * This mehtod returns true if the cache contains the checked entry.
         *
         * @return TRUE if the cache contains the checked entry.
         * @param cacheEntry The entry to perform the check for.
         */
        public boolean contains(Object cacheEntry) {
            return cacheEntries.containsValue(cacheEntry);
        }
        
        
        /**
         * This method is responsible for adding an entry to the cache.
         *
         * @param entry The entry to add to the cache.
         */
        public synchronized void add(CacheEntry entry) {
            cacheEntries.put(entry.getName(),entry);
        }
        
        
        /**
         * This method returns the cache entry by name
         *
         * @return The CacheEntry to retrieve.
         * @param name The name of the cache entry to retrieve.
         */
        public synchronized CacheEntry get(String name) {
            return (CacheEntry)cacheEntries.get(name);
        }
        
    }
    
    
    public static class TestCache2 implements Cache {
        
        private Map cacheEntries = new HashMap();
        
        /**
         * The constructor of the test cache.
         */
        public TestCache2() {
            
        }
        
        /**
         * This method is called to perform garbage collection on the cache entries.
         */
        public synchronized void garbageCollect() {
            Iterator iter = cacheEntries.keySet().iterator();
            Date expiryDate = new Date(new Date().getTime() - 500);
            while(iter.hasNext()) {
                String key = (String)iter.next();
                CacheEntry entry = (CacheEntry)cacheEntries.get(key);
                if (entry.isExpired(expiryDate)) {
                    // remove an reset
                    cacheEntries.remove(key);
                    iter = cacheEntries.keySet().iterator();
                }
            }
        }


        /**
         * This method is called to forcibly remove everything from the cache.
         */
        public synchronized void clear() {
            cacheEntries.clear();
        }
        
        
        /**
         * This mehtod returns true if the cache contains the checked entry.
         *
         * @return TRUE if the cache contains the checked entry.
         * @param cacheEntry The entry to perform the check for.
         */
        public boolean contains(Object cacheEntry) {
            return cacheEntries.containsValue(cacheEntry);
        }
        
        
        /**
         * This method is responsible for adding an entry to the cache.
         *
         * @param entry The entry to add to the cache.
         */
        public synchronized void add(CacheEntry entry) {
            cacheEntries.put(entry.getName(),entry);
        }
        
        
        /**
         * This method returns the cache entry by name
         *
         * @return The CacheEntry to retrieve.
         * @param name The name of the cache entry to retrieve.
         */
        public synchronized CacheEntry get(String name) {
            return (CacheEntry)cacheEntries.get(name);
        }
        
    }
    
    public CacheRegistryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CacheRegistryTest.class);
        
        return suite;
    }

    /**
     * Test of init method, of class com.rift.coad.lib.cache.CacheRegistry.
     */
    public void testCache() throws Exception {
        System.out.println("init");
        
        // initialize the thread permissions
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // instanciate the thread group
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
                userStoreManager);
        
        // check the singleton
        CacheRegistry result = CacheRegistry.init(threadGroup);
        assertEquals(CacheRegistry.getInstance(), result);
        
        // init class loaders
        ClassLoader loader1 = new URLClassLoader(new URL[] {});
        ClassLoader loader2 = new URLClassLoader(new URL[] {});
        
        // set the current thread
        Thread.currentThread().setContextClassLoader(loader1);
        result.initCache();
        TestCache testCache = (TestCache)result.getCache(TestCache.class);
        TestCache2 testCache2 = (TestCache2)result.getCache(TestCache2.class);
        testCache.add(new CacheEntry("bob"));
        testCache.add(new CacheEntry("fred"));
        testCache2.add(new CacheEntry("mary"));
        testCache2.add(new CacheEntry("jill"));
        
        CacheEntry fred = testCache.get("fred");
        CacheEntry mary = testCache2.get("mary");
        
        Thread.currentThread().setContextClassLoader(loader2);
        result.initCache();
        TestCache testCache_2 = (TestCache)result.getCache(TestCache.class);
        TestCache2 testCache2_2 = (TestCache2)result.getCache(TestCache2.class);
        testCache_2.add(new CacheEntry("ben"));
        testCache_2.add(new CacheEntry("john"));
        testCache2_2.add(new CacheEntry("tamzin"));
        testCache2_2.add(new CacheEntry("chevaughn"));
        
        CacheEntry ben = testCache_2.get("ben");
        CacheEntry chevaughn = testCache2_2.get("chevaughn");
        
        // loop for 2 seconds
        for (int index = 0; index < 4; index++) {
            fred.touch();
            mary.touch();
            ben.touch();
            chevaughn.touch();
            Thread.sleep(450);
        }
        
        
        Thread.currentThread().setContextClassLoader(loader1);
        assertEquals(result.getCache(TestCache.class), testCache);
        assertEquals(result.getCache(TestCache2.class), testCache2);
        
        assertEquals(testCache.get("bob"),null);
        assertEquals(testCache.get("fred"),fred);
        assertEquals(testCache2.get("mary"),mary);
        assertEquals(testCache2.get("jill"),null);
        
        
        Thread.currentThread().setContextClassLoader(loader2);
        assertEquals(result.getCache(TestCache.class), testCache_2);
        assertEquals(result.getCache(TestCache2.class), testCache2_2);
        
        assertEquals(testCache_2.get("ben"),ben);
        assertEquals(testCache_2.get("john"),null);
        assertEquals(testCache2_2.get("tamzin"),null);
        assertEquals(testCache2_2.get("chevaughn"),chevaughn);
        
        result.terminateCache();
        Thread.sleep(100);
        assertEquals(testCache_2.get("ben"),null);
        assertEquals(testCache_2.get("john"),null);
        assertEquals(testCache2_2.get("tamzin"),null);
        assertEquals(testCache2_2.get("chevaughn"),null);
        
        try {
            result.getCache(TestCache.class);
            fail("The cache is still valid for this class loader");
        } catch (CacheException ex) {
            // ignore
        }
        
        Thread.currentThread().setContextClassLoader(loader1);
        result.shutdown();
        
        assertEquals(testCache_2.get("ben"),null);
        assertEquals(testCache_2.get("john"),null);
        assertEquals(testCache2_2.get("tamzin"),null);
        assertEquals(testCache2_2.get("chevaughn"),null);
        try {
            result.getCache(TestCache.class);
            fail("The cache is still valid for this class loader");
        } catch (CacheException ex) {
            // ignore
        }
    }

    
}
