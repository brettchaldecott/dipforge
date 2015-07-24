/*
 * MessageService: The message service daemon
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
 * MessageQueueManagerTest.java
 */

package com.rift.coad.daemon.messageservice;

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
import org.hibernate.*;
import org.hibernate.cfg.*;
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

/**
 * A test of the message queue manager
 *
 * @author Brett Chaldecott
 */
public class MessageQueueManagerTest extends TestCase {
    
    /**
     * This object represents a test message manager.
     */
    public class TestMessageManager implements MessageManager {
        // the classes private member variables
        public String id = null;
        public Date nextProcessTime = new Date();
        public int priority = 0;
        /**
         * The constructor that returns the test message.
         */
        public TestMessageManager (String id, int priority) {
            this.id = id;
            this.priority = priority;
        }
        
        
        /**
         * Returns the id of the test message.
         */
        public String getID() {
            return id;
        }
        
        /**
         * Returns null for this test.
         */
        public Message getMessage() throws MessageServiceException {
            return null;
        }
        
        /**
         * does nothing for this test.
         */
        public void updateMessage(Message updatedMessage) throws MessageServiceException {
            
        }
        
        /**
         * This method returns the next process time.
         */
        public Date nextProcessTime() {
            return nextProcessTime;
        }
        
        /**
         * This method returns the next process time.
         */
        public void setNextProcessTime(Date nextProcessTime) {
            this.nextProcessTime = nextProcessTime;
        }

        /**
         * This message returns the priority.
         */
        public int getPriority() {
            return priority;
        }
        
        
        /**
         * Perform the comparision between the object.
         *
         * @return The integer value of message to perform the comparison on.
         * @param o The object to perform the comparison on.
         */
        public int compareTo(Object o)  {
            TestMessageManager tmsg =(TestMessageManager)o;
            if (tmsg.nextProcessTime().getTime() > nextProcessTime().getTime()) {
                return -1;
            } else if (nextProcessTime().getTime() > tmsg.nextProcessTime().getTime()) {
                return 1;
            } else if (tmsg.getPriority() > getPriority()) {
                return -1;
            } else if (getPriority() > tmsg.getPriority()) {
                return 1;
            }
            return 0;
        }
        
        
        public void commit(Xid xid, boolean b) throws XAException {
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
            return 0;
        }

        public Xid[] recover(int i) throws XAException {
            return null;
        }

        public void rollback(Xid xid) throws XAException {
        }

        public boolean setTransactionTimeout(int i) throws XAException {
            return true;
        }

        public void start(Xid xid, int i) throws XAException {
        }

        public String getMessageQueueName() {
            return "test";
        }

        public void remove() throws MessageServiceException {
        }
        
    }
    
    boolean gotRef = false;
    
    public MessageQueueManagerTest(String testName) {
        super(testName);
        //BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /**
     * Test of class com.rift.coad.daemon.messageservice.MessageQueueManager.
     */
    public void testMessageQueueManager() throws Exception {
        System.out.println("testMessageQueueManager");
        
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

        MessageQueueManager expResult = MessageQueueManager.getInstance();
        MessageQueueManager result = MessageQueueManager.getInstance();
        assertEquals(expResult, result);
        
        
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        ut.begin();
        
        MessageQueue queue = result.getQueue("test");
        
        ut.commit();
        
        ut.begin();
        assertEquals(queue, result.getQueue("test"));
        ut.commit();
        
        
        ut.begin();
        Session session = HibernateUtil.getInstance(MessageServiceManager.class).
                getSession();
        com.rift.coad.daemon.messageservice.db.MessageQueue messageQueue = 
                new com.rift.coad.daemon.messageservice.db.MessageQueue("test2");
        session.persist(messageQueue);
        ut.commit();
        
        
        ut.begin();
        
        MessageQueue queue2 = result.getQueue("test2");
        
        ut.commit();
        
        ut.begin();
        assertEquals(queue2, result.getQueue("test2"));
        ut.commit();
        
        ut.begin();
        result.getQueue("test3");
        Thread testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    MessageQueueManager.getInstance().getQueue("test3");
                    gotRef = true;
                } catch (Exception ex) {
                    System.out.println("Failed to get the queue reference : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        testThread.start();
        Thread.sleep(500);
        if (gotRef) {
            fail("Managed aquire the queue reference");
        }
        ut.commit();
        Thread.sleep(500);
        if (gotRef == false) {
            fail("Failed aquire the queue reference");
        }
        
        ut.begin();
        TestMessageManager message1 = new TestMessageManager("test1",1);
        queue.addMessage(message1);
        TestMessageManager message2 = new TestMessageManager("test2",1);
        queue2.addMessage(message2);
        ut.commit();
        
        Date nextDate = new Date();
        System.out.println("Get message");
        MessageProcessInfo messageProcessInfo = result.getNextMessage(nextDate);
        System.out.println("After retrieving the message");
        if ((messageProcessInfo == null) || 
                ((messageProcessInfo.getMessageManager() != message1) && 
                (messageProcessInfo.getMessageManager() != message2))) {
            fail("Failed to retrieve the next message");
        }
        
        System.out.println("Get message");
        messageProcessInfo = result.getNextMessage(nextDate);
        System.out.println("After retrieving the message");
        if ((messageProcessInfo == null) || 
                ((messageProcessInfo.getMessageManager() != message1) && 
                (messageProcessInfo.getMessageManager() != message2))) {
            fail("Failed to retrieve the next message");
        }
        
        System.out.println("Get message");
        messageProcessInfo = result.getNextMessage(nextDate);
        System.out.println("After retrieving the message");
        if (messageProcessInfo != null) {
            fail("Succeeded in retrieving a message");
        }
        
        queue.pushBackMessage(message1);
        
        messageProcessInfo = result.getNextMessage(nextDate);
        if ((messageProcessInfo == null) || 
                ((messageProcessInfo.getMessageManager() != message1) && 
                (messageProcessInfo.getMessageManager() != message2))) {
            fail("Failed to retrieve the next message");
        }
        
        
        ut.begin();
        session = HibernateUtil.getInstance(MessageServiceManager.class).
                getSession();
        com.rift.coad.daemon.messageservice.db.MessageQueue messageQueue2 = 
                new com.rift.coad.daemon.messageservice.db.MessageQueue("test5");
        messageQueue2.setNamed(new Integer(1));
        session.persist(messageQueue2);
        ut.commit();
        
        
        ut.begin();
        
        try {
            result.getQueue("test5");
            fail("Was able to retrieve the named queue");
        } catch (MessageServiceException ex) {
            System.out.println(ex.getMessage());
        }
        
        ut.commit();
    }

    
}
