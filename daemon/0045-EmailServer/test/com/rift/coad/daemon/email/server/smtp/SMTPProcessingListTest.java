/*
 * Email Server: The email server interface
 * Copyright (C) 2008  Rift IT Contracting
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
 * SMTPProcessingListTest.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp;

// import path
import junit.framework.*;
import java.util.Map;
import java.util.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


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
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.CoadunationHashMap;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.transaction.TransactionDirector;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.transaction.TransactionException;
import com.rift.coad.daemon.email.smtp.SMTPException;
import com.rift.coad.daemon.email.smtp.Message;


/**
 * The class that implements the test case.
 *
 * @author brett chaldecott
 */
public class SMTPProcessingListTest extends TestCase {
    
    private boolean failed = true;
    
    public SMTPProcessingListTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of addMessage method, of class com.rift.coad.daemon.email.server.smtp.SMTPProcessingList.
     */
    public void testSMTPProcessingList() throws Exception {
        System.out.println("testSMTPProcessingList");
        
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
        
        
        final SMTPProcessingList instance = new SMTPProcessingList();
        // user transaction
        UserTransactionWrapper transaction = new UserTransactionWrapper();
        
        // start a transaction
        transaction.begin();
        
        SMTPServerMessage message1 = new SMTPServerMessage(new Message("test1", 
                1, null, null, null, "data"));
        message1.setMessageServiceId("testid1");
        instance.addMessage(message1);
        
        SMTPServerMessage message2 = new SMTPServerMessage(new Message("test2", 
                1, null, null, null, "data"));
        message2.setMessageServiceId("testid2");
        instance.addMessage(message2);
        
        SMTPServerMessage message3 = new SMTPServerMessage(new Message("test3", 
                1, null, null, null, "data"));
        message3.setMessageServiceId("testid3");
        instance.addMessage(message3);
        
        transaction.commit();
        transaction.release();
        
        // test the timeout
        Thread testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    instance.checkList(1000);
                    System.out.println("Set failed to false");
                    failed = false;
                } catch (Throwable ex) {
                    System.out.println("Failed to check the list : " + 
                            ex.getMessage());
                }
                System.out.println("Finished checking the list");
            }
        });
        testThread.start();
        
        transaction.begin();
        assertEquals(message1, instance.getMessage("testid1"));
        assertEquals(message2, instance.getMessage("testid2"));
        assertEquals(message3, instance.getMessage("testid3"));
        
        transaction.release();
        
        transaction.begin();
        instance.removeMessage("testid1");
        
        transaction.commit();
        transaction.release();
        
        transaction.begin();
        assertEquals(null, instance.getMessage("testid1"));
        assertEquals(message2, instance.getMessage("testid2"));
        assertEquals(message3, instance.getMessage("testid3"));
        
        transaction.release();
        
        transaction.begin();
        instance.removeMessage("testid2");
        
        transaction.release();
        
        transaction.begin();
        assertEquals(null, instance.getMessage("testid1"));
        assertEquals(message2, instance.getMessage("testid2"));
        assertEquals(message3, instance.getMessage("testid3"));
        
        transaction.release();
        
        transaction.begin();
        instance.removeMessage("testid2");
        instance.removeMessage("testid3");
        assertEquals(failed,true);
        
        transaction.commit();
        transaction.release();
        
        transaction.begin();
        assertEquals(null, instance.getMessage("testid1"));
        assertEquals(null, instance.getMessage("testid2"));
        assertEquals(null, instance.getMessage("testid3"));
        
        transaction.release();
        
        testThread.join();
        assertEquals(failed,false);
        
    }

    
}
