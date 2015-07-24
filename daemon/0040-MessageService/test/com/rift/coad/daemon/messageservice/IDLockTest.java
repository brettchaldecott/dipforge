/*
 * MessageService: The message service daemon
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
 * IDLockTest.java
 */

package com.rift.coad.daemon.messageservice;

// java imports
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.apache.log4j.Logger;
import com.rift.coad.util.transaction.TransactionManager;

// junit imports
import junit.framework.*;

// coadunation imports
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.db.DBSourceManager;
import com.rift.coad.lib.common.ObjectSerializer;
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
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageError;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.TextMessage;
import com.rift.coad.daemon.messageservice.MessageManager;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.MessageServiceManager;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.daemon.messageservice.message.MessageImpl;
import com.rift.coad.daemon.messageservice.message.RPCMessageImpl;
import com.rift.coad.daemon.messageservice.message.TextMessageImpl;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.lib.thread.ThreadGroupManager;


/**
 * This is the test of the id lock object.
 *
 * @author brett
 */
public class IDLockTest extends TestCase {
    
    /**
     * This object is responsible for processing the threads.
     */
    public class IDLockThread extends Thread {
        private UserTransactionWrapper utw = null;
        /**
         * The constructor of the id lock thread
         */
        public IDLockThread() throws Exception {
            utw = new UserTransactionWrapper();
        }
        
        
        /**
         * This method is used to test the id lock
         */
        public void run() {
            try {
                utw.begin();
                IDLock.getInstance().lock("test");
                lockCount++;
                utw.commit();
            } catch (Exception ex) {
                System.out.println("Failed to lock.");
            } finally {
                utw.release();
            }
        }
    }
    
    // private member variables
    private int lockCount = 0;
    
    public IDLockTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.daemon.messageservice.IDLock.
     */
    public void testIDLock() throws Exception {
        System.out.println("testIDLock");
        
        Thread.currentThread().setContextClassLoader(this.getClass().
                getClassLoader());
        
        // init the session information
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(
                sessionManager,userStoreManager);
        
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
        ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
        
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // init the database source
        DBSourceManager.init();
        ObjectLockFactory.init();
        TransactionManager.init();
        
        IDLock result = IDLock.getInstance();
        assertEquals(IDLock.getInstance(), result);
        
        UserTransactionWrapper utw = new UserTransactionWrapper();
        
        utw.begin();
        IDLock.getInstance().lock("test");
        IDLockThread thread1 = new IDLockThread();
        thread1.start();
        IDLockThread thread2 = new IDLockThread();
        thread2.start();
        
        Thread.sleep(500);
        
        if (lockCount == 2) {
            fail("Threads aquired the lock when it has not been released.");
        }
        
        utw.commit();
        utw.release();
        
        Thread.sleep(500);
        
        if (lockCount != 2) {
            fail("Threads could not aquire the lock.");
        }
        
        
        utw.begin();
        IDLock.getInstance().lock("test");
        IDLockThread thread3 = new IDLockThread();
        thread3.start();
        IDLockThread thread4 = new IDLockThread();
        thread4.start();
        
        Thread.sleep(500);
        
        if (lockCount == 4) {
            fail("Threads aquired the lock when it has not been released.");
        }
        
        utw.release();
        
        Thread.sleep(500);
        
        if (lockCount != 4) {
            fail("Threads could not aquire the lock.");
        }
        
    }
}
