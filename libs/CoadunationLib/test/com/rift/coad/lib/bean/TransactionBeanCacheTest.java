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
 * TransactionBeanCacheTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.bean;

// java imports
import javax.naming.InitialContext;
import javax.naming.Context;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import java.util.Set;
import java.util.HashSet;
import javax.transaction.UserTransaction;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

// junit imports
import junit.framework.*;

// object web imports
import org.objectweb.jotm.Jotm;

// coadunation imports
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.db.DBSourceManager;
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.transaction.TransactionDirector;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.transaction.TransactionManager;

/**
 *
 * @author Brett Chaldecott
 */
public class TransactionBeanCacheTest extends TestCase {
    
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
    
    
    // private member variables
    private TransactionBeanCache instance = null;
    private UserTransaction ut = null;
    private boolean addedEntry = false;
    private boolean caughtException = false;
    private boolean gotCacheEntry= false;
    

    public TransactionBeanCacheTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }


    public static Test suite() {
        TestSuite suite = new TestSuite(TransactionBeanCacheTest.class);
        
        return suite;
    }

    /**
     * Test of class com.rift.coad.lib.bean.BeanCache.
     */
    public void testCache() throws Exception {
        System.out.println("TransactionBeanCacheTest");
        
        // init the session information
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        InterceptorFactory.init(permissions,sessionManager,userStoreManager);
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("test1", set);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // init the database source
        DBSourceManager.init();
        Context context = new InitialContext();
        ObjectLockFactory.init();
        TransactionManager.init();
        
        ut = (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        
        // the transaction bean cache
        instance = new TransactionBeanCache();
        
        ut.begin();
        TestCacheEntry bob = new TestCacheEntry();
        instance.addCacheEntry(500,"bob","bob",bob);
        Thread testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    ut.begin();
                    instance.addCacheEntry(500,"bob","bob",new TestCacheEntry());
                    addedEntry = true;
                    ut.commit();
                } catch (Exception ex) {
                    System.out.println("Failed to get the queue reference : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                    caughtException = true;
                    try {
                        ut.rollback();
                    } catch (Exception ex2) {
                        System.out.println(
                                "Failed to roll the transaction back : " + 
                                ex2.getMessage());
                    }
                }
            }
        });
        testThread.start();
        
        Thread.sleep(500);
        if (addedEntry) {
            fail("An entry was added");
        } else if (caughtException) {
            fail("Caught an exception");
        }
        ut.commit();
        Thread.sleep(500);
        if (addedEntry) {
            fail("An entry was added");
        } else if (caughtException == false) {
            fail("Failed to catch an exception");
        }
        
        
        ut.begin();
        try {
            instance.addCacheEntry(500,"bob","bob",bob);
            fail("The second add must fail");
        } catch (BeanException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        ut.commit();
        TestCacheEntry fred = new TestCacheEntry();
        ut.begin();
        instance.addCacheEntry(500,"fred","fred",fred);
        try {
            instance.addCacheEntry(500,"fred","fred",fred);
            fail("The second add must fail");
        } catch (BeanException ex) {
            System.out.println(ex.getMessage());
        }
        ut.commit();
        TestCacheEntry mary = new TestCacheEntry();
        ut.begin();
        instance.addCacheEntry(500,"mary","mary","mary",mary);
        ut.commit();
        
        ut.begin();
        if (bob != instance.getCacheEntry("bob").getCacheEntry()) {
            fail("bob could not be found");
        }
        instance.getCacheEntry("bob").setCacheEntry(mary);
        caughtException = false;
        testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    ut.begin();
                    instance.getCacheEntry("bob");
                    gotCacheEntry = true;
                    ut.commit();
                } catch (Exception ex) {
                    System.out.println("Failed to get the queue reference : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                    caughtException = true;
                    try {
                        ut.rollback();
                    } catch (Exception ex2) {
                        System.out.println(
                                "Failed to roll the transaction back : " + 
                                ex2.getMessage());
                    }
                }
            }
        });
        testThread.start();
        
        Thread.sleep(500);
        if (gotCacheEntry) {
            fail("Managed to retrieve the cache entry");
        } else if (caughtException) {
            fail("Caught an exception");
        }
        ut.commit();
        Thread.sleep(500);
        if (!gotCacheEntry) {
            fail("Failed to retrieve the cache entry");
        } else if (caughtException) {
            fail("Caught an exception");
        }
        
        ut.begin();
        if (mary != instance.getCacheEntry("bob").getCacheEntry()) {
            fail("mary could not be found");
        }
        ut.commit();
        ut.begin();
        if (mary != instance.getCacheEntry("mary").getBeanHandler()) {
            fail("mary could not be found");
        }
        instance.getCacheEntry("mary").setBeanHandler(bob);
        ut.commit();
        ut.begin();
        if (bob != instance.getCacheEntry("mary").getBeanHandler()) {
            fail("bob could not be found");
        }
        if (!instance.getCacheEntry("mary").getProxy().equals("mary")) {
            fail("mary could not be found");
        }
        instance.getCacheEntry("mary").setProxy("mary1");
        if (!instance.getCacheEntry("mary").getProxy().equals("mary1")) {
            fail("mary1 could not be found");
        }
        ut.commit();
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            bob.touch();
            mary.touch();
        }
        
        instance.garbageCollect();
        
        ut.begin();
        if (!instance.contains("bob")) {
            fail("bob could not be found");
        }
        if (!instance.contains("mary")) {
            fail("mary could not be found");
        }
        if (instance.contains("fred")) {
            fail("fred was found");
        }
        ut.commit();
        
        ut.begin();
        instance.removeCacheEntry("bob");
        instance.addCacheEntry(500,"fred","fred",new TestCacheEntry());
        if (!instance.contains("fred")) {
            fail("fred was found");
        }
        if (instance.contains("bob")) {
            fail("bob was found");
        }
        ut.rollback();
        ut.begin();
        if (instance.contains("fred")) {
            fail("fred was found");
        }
        if (!instance.contains("bob")) {
            fail("bob was not found");
        }
        ut.commit();
        instance.clear();
        ut.begin();
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
        
        if (TestCacheEntry.count != 4) {
            fail("Release not called on all classes [" + TestCacheEntry.count 
                    + "]");
        }
        ut.rollback();
    }

    
}
