/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007 2015 Burntjam
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
 * ChangeLogTest.java
 */

package com.rift.coad.util.change;

import com.rift.coad.lib.thread.ThreadGroupManager;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import junit.framework.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.transaction.UserTransaction;

// logger
import org.apache.log4j.Logger;


// coadunation imports
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.db.DBSourceManager;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.transaction.TransactionManager;
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

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.transaction.TransactionDirector;


/**
 * This object is responsible for testing the change log object.
 *
 * @author Brett Chaldecott
 */
public class ChangeLogTest extends TestCase {
    
    /**
     * This object is responsible for representing a change.
     */
    public static class TestChange implements Change {
        
        /**
         * The constructor of the test change.
         */
        public TestChange() {
            
        }
        
        
        /**
         * The definition of the apply method.
         */
        public void applyChanges() throws ChangeException {
            changeCount++;
        }
    }
    
    public ChangeLogTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public static int changeCount = 0;
    /**
     * Test of class com.rift.coad.util.change.ChangeLog.
     */
    public void testChangeLog() throws Exception {
        System.out.println("testChangeLog");
        
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
        
        // instanciate the thread manager
        Thread.currentThread().setContextClassLoader(this.getClass().
                getClassLoader());
        ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
        
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // init the transaction manager
        ObjectLockFactory.init();
        TransactionManager.init();
        
        String username = "";
        
        ChangeLog.init(ChangeLog.class);
        
        // retrieve the user transaction
        Context context = new InitialContext();
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        for (int index = 0; index < 1000; index++) {
            ut.begin();
            for (int count = 0; count < 10; count++) {
                ChangeLog.getInstance().addChange(new TestChange());
            }
            ut.commit();
        }
        
        ChangeLog.terminate();
        
        File dumpFile = new File("./tmp/changelog.dmp");
        if (!dumpFile.exists()) {
            fail("The dump file does not exist changeCount : " + changeCount);
        }
        
        // init the change log
        ChangeLog.init(ChangeLog.class);
        ChangeLog.getInstance().start();
        System.out.println("Before sleeping");
        Thread.sleep(500);
        System.out.println("After sleeping");
        if (changeCount != 10000) {
            fail("Failed to apply 10000 changes [" + changeCount + "]");
        }
        
        
        ChangeLog.terminate();
        
        if (dumpFile.exists()) {
            fail("The dump file does exist should have been removed");
        }
    }

    
}
