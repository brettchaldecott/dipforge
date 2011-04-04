/*
 * MessageQueueClient: The message queue client library
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
 * MessageQueueTest.java
 */

// package path
package com.rift.coad.daemon.messageservice;

import junit.framework.*;
import java.lang.ThreadLocal;
import java.util.Date;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

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
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;

/**
 * This object tests the Message queue
 *
 * @author Brett Chaldecott
 */
public class MessageQueueTest extends TestCase {
    
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
    
    public MessageQueueTest(String testName) {
        super(testName);
        //BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of com.rift.coad.daemon.messageservice.MessageQueue.
     */
    public void testMessageQueue() throws Exception {
        System.out.println("MessageQueue");
        
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
        ObjectLockFactory.init();
        TransactionManager.init();

        // retrieve the user transaction
        Context context = new InitialContext();
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        ut.begin();
        
        MessageQueue messageQueue = new MessageQueue("test");
        
        TestMessageManager message1 = new TestMessageManager("test1",1);
        TransactionManager.getInstance().bindResource(message1,true);
        messageQueue.addMessage(message1);
        
        ut.rollback();
        
        ut.begin();
        try {
            messageQueue.getMessage("test1");
            fail("Retrieved a message that should not be there");
        } catch (MessageServiceException ex) {
            System.out.println("Failed to find the message : " + 
                    ex.getMessage());
        }
        ut.commit();
        
        
        ut.begin();
        
        TestMessageManager message2 = new TestMessageManager("test2",1);
        TransactionManager.getInstance().bindResource(message1,true);
        messageQueue.addMessage(message1);
        TransactionManager.getInstance().bindResource(message2,true);
        messageQueue.addMessage(message2);
        
        ut.commit();
        
        ut.begin();
        
        if (message1 != messageQueue.getMessage("test1")) {
            fail("Test message 1 not found");
        }
        ut.rollback();
        
        Date nextRunTime = new Date();
        TestMessageManager testRunMessage = 
                (TestMessageManager)messageQueue.popFrontMessage(nextRunTime);
        if (testRunMessage == null) {
            fail("Failed to pop a message from the list");
        }
        if (!testRunMessage.getID().equals("test1")) {
            fail("The pop order is wrong");
        }
        
        ut.begin();
        TransactionManager.getInstance().bindResource(testRunMessage,true);
        testRunMessage.setNextProcessTime(new Date(testRunMessage.
                nextProcessTime().getTime() + 1000));
        ut.commit();
        messageQueue.pushBackMessage(testRunMessage);
        
        testRunMessage = 
                (TestMessageManager)messageQueue.popFrontMessage(nextRunTime);
        if (testRunMessage == null) {
            fail("Failed to pop a message from the list");
        }
        if (!testRunMessage.getID().equals("test2")) {
            fail("The pop order is wrong");
        }
        
        ut.begin();
        TransactionManager.getInstance().bindResource(testRunMessage,true);
        testRunMessage.setNextProcessTime(new Date(testRunMessage.
                nextProcessTime().getTime() + 1000));
        ut.commit();
        messageQueue.pushBackMessage(testRunMessage);
        
        
        testRunMessage = 
                (TestMessageManager)messageQueue.popFrontMessage(nextRunTime);
        if (testRunMessage != null) {
            fail("Popped a message from the list");
        }
        
        Thread.sleep(1500);
        
        testRunMessage = 
                (TestMessageManager)messageQueue.popFrontMessage(nextRunTime);
        if (testRunMessage == null) {
            fail("Failed to pop a message from the list");
        }
        if (!testRunMessage.getID().equals("test1")) {
            fail("The pop order is wrong");
        }
        
        ut.begin();
        TransactionManager.getInstance().bindResource(testRunMessage,true);
        testRunMessage.setNextProcessTime(new Date(testRunMessage.
                nextProcessTime().getTime() + 1000));
        ut.commit();
        messageQueue.pushBackMessage(testRunMessage);
        
        
        testRunMessage = 
                (TestMessageManager)messageQueue.popFrontMessage(nextRunTime);
        if (testRunMessage == null) {
            fail("Failed to pop a message from the list");
        }
        if (!testRunMessage.getID().equals("test2")) {
            fail("The pop order is wrong");
        }
        
        ut.begin();
        messageQueue.removeMessage("test2");
        ut.commit();
        
        ut.begin();
        try {
            messageQueue.getMessage("test2");
            fail("Retrieved a message that should not be there");
        } catch (MessageServiceException ex) {
            System.out.println("Failed to find the message : " + 
                    ex.getMessage());
        }
        ut.commit();
        
        ut.begin();
        messageQueue.removeMessage("test1");
        ut.commit();
        
        ut.begin();
        try {
            messageQueue.getMessage("test1");
            fail("Retrieved a message that should not be there");
        } catch (MessageServiceException ex) {
            System.out.println("Failed to find the message : " + 
                    ex.getMessage());
        }
        ut.commit();
    }

    
}
