/*
 * MessageQueueClient: The message queue client library
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
 * MessageProducerImplTest.java
 */

// package path
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
import com.rift.coad.lib.thread.ThreadGroupManager;
import com.rift.coad.util.change.ChangeLog;


/**
 *
 * @author Brett Chaldecott
 */
public class MessageProducerImplTest extends TestCase {
    
    public MessageProducerImplTest(String testName) {
        super(testName);
        //BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of  com.rift.coad.daemon.messageservice.MessageProducerImpl.
     */
    public void testMessageProducer() throws Exception {
        System.out.println("testMessageProducer");
        
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
        ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
        
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // init the database source
        DBSourceManager.init();
        Context context = new InitialContext();
        ObjectLockFactory.init();
        TransactionManager.init();
        ChangeLog.init(MessageServiceManager.class);
        ChangeLog.getInstance().start();
        
        MessageProducerImpl instance = new MessageProducerImpl();
        
        Producer result = instance.createProducer("test1");
        Producer result2 = instance.createProducer("test1");
        if (result == result2) {
            fail("Should get a new producer every time");
        }
        
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        
        RPCMessage rpcMessage = result.createRPCMessage(Message.POINT_TO_POINT);
        if (rpcMessage.getProcessedDate() == null) {
            fail("The processed date is equal to null");
        }
        rpcMessage.setMethodBodyXML("xml body");
        ut.begin();
        result.submit(rpcMessage);
        ut.commit();
        
        TextMessage textMessage = result.createTextMessage(
                Message.POINT_TO_POINT);
        textMessage.setTextBody("Text body");
        ut.begin();
        result.submit(textMessage);
        ut.commit();
        
        Thread.sleep(30000);
        
        ut.begin();
        Session session = HibernateUtil.getInstance(MessageServiceManager.class).
                getSession();
        List messages = session.createQuery("From Message as msg").list();
        if (messages.size() != 2) {
            fail("Did not create enough messages");
        }
        ut.commit();
        
        Thread.sleep(30000);
        
        ChangeLog.getInstance().terminate();
    }
    
}
