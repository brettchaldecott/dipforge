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
 * ProxyCacheTest.java
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
import com.rift.coad.lib.common.RandomGuid;
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
public class TransactionProxyCacheTest extends TestCase {
    
    /**
     * The test cache class
     */
    public static class ProxyCacheTestClass implements java.rmi.Remote,
            CacheEntry {
        // private member variables
        private String id = null;
        private Date lastTouchTime = new Date();
        public static int count = 0;
        
        /**
         * The constructor of the cache entry
         */
        public ProxyCacheTestClass() throws Exception {
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
    
    // user transaction
    private UserTransaction ut = null;
    private boolean addedEntry = false;
    private boolean caughtException = false;
    private boolean gotCacheEntry= false;
    
    
    public TransactionProxyCacheTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(TransactionProxyCacheTest.class);
        
        return suite;
    }
    
    /**
     * Test of ProxyCache, of class com.rift.coad.lib.bean.ProxyCache.
     */
    public void testProxyCache() throws Exception {
        System.out.println("ProxyCache");
        
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
        
        TransactionProxyCache instance = new TransactionProxyCache();
        
        
        ProxyCacheTestClass cacheObject1 = new ProxyCacheTestClass();
        ProxyCacheTestClass cacheObject2 = new ProxyCacheTestClass();
        ProxyCacheTestClass cacheObject3 = new ProxyCacheTestClass();
        ProxyCacheTestClass cacheObject4 = new ProxyCacheTestClass();
        
        ut.begin();
        instance.addCacheEntry(500,cacheObject3,cacheObject3);
        ut.rollback();
        
        if (instance.contains(cacheObject3)) {
            fail("Cache did not roll back");
        }
        
        ut.begin();
        instance.addCacheEntry(500,cacheObject1,cacheObject1);
        instance.addCacheEntry(500,cacheObject2,cacheObject2);
        ut.commit();
        
        ut.begin();
        instance.addCacheEntry(500,cacheObject4,cacheObject4);
        ut.commit();
        
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
            ut.begin();
            instance.addCacheEntry(500,cacheObject1,cacheObject1);
            fail("The cache has not been invalidated");
            ut.commit();
        } catch (BeanException ex) {
            System.out.println(ex.getMessage());
        }
        
        if (ProxyCacheTestClass.count != 4) {
            fail("Release was not called on both classes : " + 
                    ProxyCacheTestClass.count);
        }
        
    }
    
}
