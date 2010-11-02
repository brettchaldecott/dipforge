/*
 * CoadunationLib: The coaduntion library.
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
 * UserTransactionWrapperTest.java
 */

package com.rift.coad.util.transaction;

// java imports
import junit.framework.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.transaction.Status;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


// object web imports
import org.objectweb.jotm.Jotm;

// coadunation imports
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.db.DBSourceManager;

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
 * This object is here to test the user transaction wrapper.
 *
 * @author Brett Chaldecott
 */
public class UserTransactionWrapperTest extends TestCase {
    
    public class TransactionTestResource implements XAResource {
        
        // private member variables
        private String name = null;
        
        /**
         * The transaction test resource contructor
         */
        public TransactionTestResource(String name) {
            this.name = name;
        }
        
        public void commit(Xid xid, boolean b) throws XAException {
            commit ++;
        }
        
        public void end(Xid xid, int i) throws XAException {
        }
        
        public void forget(Xid xid) throws XAException {
        }
        
        public int getTransactionTimeout() throws XAException {
            return -1;
        }
        
        public boolean isSameRM(XAResource xAResource) throws XAException {
            return this == xAResource;
        }
        
        public int prepare(Xid xid) throws XAException {
            return -1;
        }
        
        public Xid[] recover(int i) throws XAException {
            return null;
        }
        
        public void rollback(Xid xid) throws XAException {
            rollback++;
        }
        
        public boolean setTransactionTimeout(int i) throws XAException {
            return true;
        }
        
        public void start(Xid xid, int i) throws XAException {
            System.out.println("Start on : " + name + " id [" + xid + "]");
        }
        
    }
    
    public UserTransactionWrapperTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    private int rollback = 0;
    private int commit = 0;

    /**
     * Test of class com.rift.coad.util.transaction.UserTransactionWrapper.
     */
    public void testUserTransactionWrapper() throws Exception {
        System.out.println("testUserTransactionWrapper");
        
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
        
        try {
            TransactionManager.getInstance();
            fail("Could retrieve a tranaction manager reference before init");
        } catch (TransactionException ex) {
            System.out.println(ex.getMessage());
        }
        
        // init the transaction manager
        ObjectLockFactory.init();
        TransactionManager.init();
        
        UserTransactionWrapper instance = new UserTransactionWrapper();
        
        try {
            instance.begin();
            try {
                instance.begin();
                TransactionManager.getInstance().bindResource(
                        new TransactionTestResource("test1"),false);
            } finally {
                instance.release();
            } 
        } finally {
            instance.release();
        }
        
        if (rollback != 1) {
            fail("Rollback was not called on the object : " + rollback);
        }
        if (commit == 1) {
            fail("Commit was called on the object");
        }
        
        try {
            instance.begin();
            try {
                instance.begin();
                TransactionManager.getInstance().bindResource(
                        new TransactionTestResource("test2"),false);
            } finally {
                instance.release();
            } 
            instance.commit();
        } finally {
            instance.release();
        }
        
        if (rollback != 1) {
            fail("Rollback was not called on the object");
        }
        if (commit != 1) {
            fail("Commit was called on the object");
        }
    }

    
}
