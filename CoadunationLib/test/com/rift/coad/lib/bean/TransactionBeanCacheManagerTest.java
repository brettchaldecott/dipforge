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
 * @author mincemeat
 */
public class TransactionBeanCacheManagerTest extends TestCase {
    
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
    
    // private member variables
    private UserTransaction ut = null;
    
    public TransactionBeanCacheManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TransactionBeanCacheManagerTest.class);
        
        return suite;
    }

    /**
     * Test of BeanCacheManager method, of class com.rift.coad.lib.bean.BeanCacheManager.
     */
    public void testBeanCacheManager() throws Exception {
        System.out.println("TransactionBeanCacheManager");
        
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
        
        Object ref = null;
        TransactionBeanCacheManager instance = new TransactionBeanCacheManager();
        
        TransactionBeanCache beanCache1 = instance.getBeanCache("beanCache1");
        TransactionBeanCache beanCache2 = instance.getBeanCache("beanCache2");
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
        
        ut.begin();
        TestCacheEntry bob = new TestCacheEntry();
        beanCache1.addCacheEntry(500,"bob","bob",bob);
        System.out.println("Class : " + beanCache1.getClass().getName());
        ut.commit();
        ut.begin();
        TestCacheEntry fred = new TestCacheEntry();
        beanCache1.addCacheEntry(500,"fred","fred",fred);
        ut.commit();
        ut.begin();
        TestCacheEntry mary = new TestCacheEntry();
        beanCache2.addCacheEntry(500,"mary","mary","mary",mary);
        ut.commit();
        ut.begin();
        TestCacheEntry jill = new TestCacheEntry();
        beanCache2.addCacheEntry(500,"jill","jill","jill",jill);
        ut.commit();
        
        ut.begin();
        if (!beanCache1.contains("bob")) {
            fail("bob could not be found");
        }
        
        if (!beanCache2.contains("mary")) {
            fail("mary could not be found");
        }
        if (!beanCache1.contains("fred")) {
            fail("fred was found");
        }
        if (!beanCache2.contains("jill")) {
            fail("jill was found");
        }
        ut.commit();
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            bob.touch();
            mary.touch();
        }
        
        instance.garbageCollect();
        ut.begin();
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
        ut.commit();
        
        instance.clear();
        
        ut.begin();
        if (beanCache1.contains("bob")) {
            fail("bob could still be found");
        }
        if (beanCache2.contains("mary")) {
            fail("mary could still be found");
        }
        ut.commit();
        try {
            ut.begin();
            beanCache1.addCacheEntry(500,"mary","mary","mary",mary);
            ut.commit();
            fail("Could add an entry should not be allowed");
            
        } catch (BeanException ex) {
            ut.rollback();
        }
        try {
            ut.begin();
            
            beanCache2.addCacheEntry(500,"bob","bob",bob);
            fail("Could add an entry should not be allowed");
        } catch (BeanException ex) {
            ut.rollback();
        }
        
        try {
            ut.begin();
            beanCache2 = instance.getBeanCache("beanCache2");
            ut.commit();
            fail("Could add an entry to the bean cache manager.");
        } catch (BeanException ex) {
            ut.rollback();
        }
        
        if (TestCacheEntry.count != 4) {
            fail ("Release not called on all classes");
        }
    }

    
}
