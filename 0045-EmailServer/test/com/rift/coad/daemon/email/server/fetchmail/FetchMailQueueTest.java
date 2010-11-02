/*
 * Email Server: The email server.
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
 * SMTPMessageQueueTest.java
 */

package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Date;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// log4j imports
import org.apache.log4j.Logger;

// junit imports
import junit.framework.*;


// coadunation imports
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.daemon.email.smtp.SMTPException;
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
 * This test of the fetch mail queue.
 *
 * @author brett chaldecott
 */
public class FetchMailQueueTest extends TestCase {
    
    /**
     * This object represents a test entry.
     */
    public class TestEntry implements FetchEntry {
        // private member variables
        private Date retryTime = null;
        private String id = null;
        private String value = null;
        
        /**
         * The constructor of the test entry
         */
        public TestEntry (String id, String value) {
            this.id = id;
            this.value = value;
        }
        
        
        /**
         * This method returns the id of the test entry.
         *
         * @return the String containing the id of the entry.
         */
        public String getId() {
            return id;
        }
        
        
        /**
         * This method retrieves the value for the entry.
         */
        public String getValue() {
            return value;
        }
        
        
        /**
         * This method sets the value of the test entry.
         *
         * @param value The value of the entry.
         */
        public void setValue(String value) {
            this.value = value;
        }
        
        
        /**
         * This object contains the retry time
         */
        public Date getRetryTime() {
            return retryTime;
        }
        
        /**
         * Re-calculate the retry time
         */
        public void recalculateRetryTime(long retryInteval) {
            if (retryTime == null) {
                retryTime = new Date();
            } else {
                retryTime.setTime(new Date().getTime() + retryInteval);
            }
        }

        public String getEmailAddress() {
            return id + "@" + value;
        }
        
        
        /**
         * This method
         */
        public boolean equals(Object value) {
            if (value == this) {
                return true;
            }
            if (!(value instanceof TestEntry)) {
                return false;
            }
            TestEntry entry = (TestEntry)value;
            return this.getEmailAddress().equals(entry.getEmailAddress());
        }
        
        
        public int compareTo(Object value) {
            if (value == this) {
                return 0;
            } else if (!(value instanceof TestEntry)) {
                return -1;
            }
            TestEntry entry = (TestEntry)value;
            if (entry.getRetryTime().getTime() == this.getRetryTime().getTime()) {
                return this.getEmailAddress().compareTo(
                        entry.getEmailAddress());
            }
            return (int)(this.getRetryTime().getTime() - entry.getRetryTime().getTime());
        }
    }
    
    private int entries = 0;
    private FetchMailQueue instance = null;
    
    public FetchMailQueueTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of add method, of class com.rift.coad.daemon.email.server.smtp.SMTPMessageQueue.
     */
    public void testSMTPMessageQueue() throws Exception {
        System.out.println("SMTPMessageQueue");
        
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
        
        
        instance = new FetchMailQueue();
        
        
        // user transaction
        UserTransactionWrapper transaction = new UserTransactionWrapper();
        
        // start a transaction
        transaction.begin();
        
        TestEntry entry1 = new TestEntry ("test1", "11111");
        TestEntry entry2 = new TestEntry ("test2", "22222");
        TestEntry entry3 = new TestEntry ("test3", "33333");
        
        // add the message
        instance.add(entry1);
        instance.add(entry2);
        instance.add(entry3);
        
        transaction.commit();
        transaction.release();
        
        // start a thread to retrieve the entries from the queue.
        Thread thread = new Thread(new Runnable() {
            public void run() {
                
                try {
                    // user transaction
                    UserTransactionWrapper transaction2 = 
                            new UserTransactionWrapper();
                    for (int index = 0; index < 6; index++) {
                        TestEntry entry = (TestEntry)instance.pop(transaction2);
                        entries++;
                        System.out.println(entry.getId());
                        if (entry.getId().equals("test1")) {
                            transaction2.commit();
                            transaction2.release();
                            continue;
                        } else if (entry.getId().equals("test2") && 
                                (index >= 3)) {
                            transaction2.commit();
                            transaction2.release();
                            continue;
                        } else if (entry.getId().equals("test3") && 
                                (index >= 4)) {
                            transaction2.commit();
                            transaction2.release();
                            break;
                        }
                        transaction2.commit();
                        transaction2.release();
                        transaction2.begin();
                        entry.setValue("" + index);
                        instance.add(entry);
                        transaction2.commit();
                        transaction2.release();
                    }
                } catch (Throwable ex) {
                    System.out.println("Failed to retrieve the queue entries :" 
                            + ex.getMessage());
                    fail("Failed to retrieve the queue entries :" 
                            + ex.getMessage());
                }
            }
        });
        
        
        thread.start();
        
        thread.join();
        
        assertEquals(entries, 5);        
    }

    
}
