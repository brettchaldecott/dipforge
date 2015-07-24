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
 * BeanHandlerTest.java
 * JUnit based test
 */

package com.rift.coad.lib.bean;

import com.rift.coad.lib.db.DBSourceManager;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.transaction.TransactionDirector;
import com.rift.coad.util.lock.ObjectLockFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import junit.framework.*;
import java.lang.ClassLoader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.UserTransaction;
import java.lang.reflect.Proxy;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.lib.bean.test.*;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.cache.KeySyncCache;
import com.rift.coad.lib.cache.KeySyncCacheManager;
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.util.transaction.TransactionManager;

/**
 *
 * @author Brett Chaldecott
 */
public class BeanHandlerTest extends TestCase {
    
    private UserTransaction ut = null;
    
    
    public BeanHandlerTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BeanHandlerTest.class);
        
        return suite;
    }

    /**
     * Test of invoke method, of class com.rift.coad.lib.bean.BeanHandler.
     */
    public void testBeanHandler() throws Exception {
        System.out.println("testBeanHandler");
        
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
        
        // check the singleton
        CacheRegistry registry = CacheRegistry.init(threadGroup);
        
        // setup the registry for this class loader
        Thread.currentThread().setContextClassLoader(
                BeanHandler.class.getClassLoader());
        registry.initCache();
        
        // instanciate a bean infomration object
        BeanInfo beanInfo1 = new BeanInfo();
        beanInfo1.setCacheResults(true);
        beanInfo1.setRole("test");
        beanInfo1.setCacheTimeout(500);
        BeanInfo beanInfo2 = new BeanInfo();
        beanInfo2.setCacheResults(false);
        beanInfo2.setRole("test");
        beanInfo2.setCacheTimeout(500);
        
        // setup the main interface
        MainInterface1Impl subObject1 = new MainInterface1Impl();
        
        BeanHandler handler = new BeanHandler(beanInfo1, subObject1, 
                beanInfo1.getRole(),permissions,
                BeanHandler.class.getClassLoader());
        MainInterface1 proxy = (MainInterface1)Proxy.newProxyInstance(
                BeanHandler.class.getClassLoader(),
                subObject1.getClass().getInterfaces(),handler);
        
        proxy.callVoid();
        
        try {
            proxy.throwException();
            fail("Failed to throw an exception");
        } catch (TestProxyException ex) {
            // worked
        }
        
        // make the test calls on this object
        if (proxy.getInt() != 1) {
            fail("Failed to retrieve value 1");
        }
        if (!proxy.getString().equals("Test1")) {
            fail("Failed to retrieve value 1");
        }
        TestKey testKey = proxy.getAKeyValue();
        if (!(testKey.getKey1().equals("1")) && 
                !(testKey.getKey2().equals("key1"))) {
            fail("Failed to retrieve the key");
        }
        
        SubInterface1 interface1 = proxy.addSubInterface("key1");
        if (interface1 != proxy.getSubInterface("key1")) {
            fail("Failed to retrieve from cache");
        }
        if (MainInterface1Impl.calledBean != 0) {
            fail("Failed the bean got called");
        }
        
        SubInterface1 interface2 = proxy.addSubInterface("key2");
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            interface1.getName();
        }
        interface1.getName();
        boolean called = false;
        try {
            interface2.getName();
            called = true;
        } catch(Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        if (called) {
            fail("Called the sub object successfully");
        }
        
        // create a new proxy
        handler = new BeanHandler(beanInfo2, subObject1, 
                beanInfo1.getRole(),permissions,
                BeanHandler.class.getClassLoader());
        proxy = (MainInterface1)Proxy.newProxyInstance(
                BeanHandler.class.getClassLoader(),
                subObject1.getClass().getInterfaces(),handler);
        
        MainInterface1Impl.calledBean = 0;
        interface1 = proxy.getSubInterface("key1");
        interface1 = proxy.getSubInterface("key1");
        interface1 = proxy.getSubInterface("key1");
        if (MainInterface1Impl.calledBean != 3) {
            fail("Failed to call sub object hitting cache");
        }
        interface2 = proxy.addSubInterface("key2");
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            interface1.getName();
        }
        interface1.getName();
        called = false;
        try {
            interface2.getName();
            called = true;
        } catch(Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        if (called) {
            fail("Called the sub object successfully");
        }
        
        
        // transaction based methods
        MainInterface1Impl.calledBean = 0;
        beanInfo1.setTransaction(true);
        beanInfo2.setTransaction(true);
        handler = new BeanHandler(beanInfo1, subObject1, 
                beanInfo1.getRole(),permissions,
                BeanHandler.class.getClassLoader());
        proxy = (MainInterface1)Proxy.newProxyInstance(
                BeanHandler.class.getClassLoader(),
                subObject1.getClass().getInterfaces(),handler);
        
        proxy.callVoid();
        
        try {
            proxy.throwException();
            fail("Failed to throw an exception");
        } catch (TestProxyException ex) {
            // worked
        }
        
        // make the test calls on this object
        if (proxy.getInt() != 1) {
            fail("Failed to retrieve value 1");
        }
        if (!proxy.getString().equals("Test1")) {
            fail("Failed to retrieve value 1");
        }
        testKey = proxy.getAKeyValue();
        if (!(testKey.getKey1().equals("1")) && 
                !(testKey.getKey2().equals("key1"))) {
            fail("Failed to retrieve the key");
        }
        
        interface1 = proxy.addSubInterface("key3");
        if (interface1 != proxy.getSubInterface("key3")) {
            fail("Failed to retrieve from cache");
        }
        if (MainInterface1Impl.calledBean != 0) {
            fail("Failed the bean got called");
        }
        
        ut.begin();
        interface2 = proxy.addSubInterface("key4");
        ut.commit();
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            interface1.getName();
        }
        interface1.getName();
        called = false;
        try {
            interface2.getName();
            called = true;
        } catch(Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        if (called) {
            fail("Called the sub object successfully");
        }
        
        // create a new proxy
        handler = new BeanHandler(beanInfo2, subObject1, 
                beanInfo1.getRole(),permissions,
                BeanHandler.class.getClassLoader());
        proxy = (MainInterface1)Proxy.newProxyInstance(
                BeanHandler.class.getClassLoader(),
                subObject1.getClass().getInterfaces(),handler);
        
        MainInterface1Impl.calledBean = 0;
        ut.begin();
        interface1 = proxy.getSubInterface("key3");
        interface1 = proxy.getSubInterface("key3");
        interface1 = proxy.getSubInterface("key3");
        ut.commit();
        if (MainInterface1Impl.calledBean != 3) {
            fail("Failed to call sub object hitting cache");
        }
        interface2 = proxy.addSubInterface("key4");
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            interface1.getName();
        }
        interface1.getName();
        called = false;
        try {
            interface2.getName();
            called = true;
        } catch(Exception ex) {
            System.out.println("Exception " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        if (called) {
            fail("Called the sub object successfully");
        }
        
        // terminate the cache registry
        registry.shutdown();
    }
}
