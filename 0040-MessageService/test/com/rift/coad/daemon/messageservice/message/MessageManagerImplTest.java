/*
 * MessageService: The message service daemon
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
 * MessageManagerImplTest.java
 */

// package path
package com.rift.coad.daemon.messageservice.message;

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
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.lib.thread.ThreadGroupManager;

/**
 * The test of the message manager
 *
 * @author Brett Chaldecott
 */
public class MessageManagerImplTest extends TestCase {
    
    public MessageManagerImplTest(String testName) {
        super(testName);
        //BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of com.rift.coad.daemon.messageservice.message.MessageManagerImpl.
     */
    public void testMessageManagerImpl() throws Exception {
        System.out.println("testMessageManagerImpl");
        
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
        ChangeLog.init(MessageServiceManager.class);
        ChangeLog.getInstance().start();
        Context context = new InitialContext();
        
        
        List principals = new ArrayList(set);
        Date currentTime = new Date();
        TextMessageImpl textMessage = new TextMessageImpl("test","test","test",
                principals,Message.UNDELIVERED);
        textMessage.setFrom("test");
        textMessage.setTextBody("test text");
        textMessage.setStringProperty("TESTString","TESTvalue");
        textMessage.setBooleanProperty("TESTBoolean",true);
        textMessage.setByteProperty("TESTByte",(byte)1);
        textMessage.setCorrelationId("TESTCorrelationId");
        textMessage.setDoubleProperty("TESTDouble",1.1);
        textMessage.setFloatProperty("TESTFloat",(float)2.2);
        textMessage.setFrom("test@test.com");
        textMessage.setIntProperty("TESTInt",100);
        textMessage.setLongProperty("TESTLong",(long)2000);
        textMessage.setTargetNamedQueue("QUEUEName");
        textMessage.setReplyNamedQueue("ReplyQUEUEName");
        textMessage.setNextProcessDate(currentTime);
        textMessage.setObjectProperty("TESTObject","This is a test obj");
        textMessage.setPriority(1000);
        textMessage.setProcessedDate(currentTime);
        textMessage.setReply(true);
        textMessage.setServices(new String[] {"test1","test2","test3"});
        textMessage.setTarget("target");
        textMessage.addError(1,"test error1");
        textMessage.addError(2,"test error2");
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        ut.begin();
        
        MessageManagerImpl instance = new MessageManagerImpl(textMessage);
        
        ut.commit();
        
        String expResult = "test";
        String result = instance.getID();
        assertEquals(expResult, result);
        
        ut.begin();
        Message retrievedMessage = instance.getMessage();
        ut.commit();
        Date nextProcessTime = new Date(currentTime.getTime() + 1000);
        
        assertEquals(retrievedMessage.getMessageId(), "test");
        System.out.println("[" + 
                retrievedMessage.getStringProperty("TESTString") 
                + "][TESTvalue]");
        assertEquals(retrievedMessage.getStringProperty("TESTString"), 
                "TESTvalue");
        retrievedMessage.setStringProperty("TESTString","TESTvalue2");
        assertEquals(((TextMessage)retrievedMessage).getTextBody(), 
                "test text");
        ((TextMessage)retrievedMessage).setTextBody("testing text again");
        assertEquals(retrievedMessage.getBooleanProperty("TESTBoolean"), 
                true);
        retrievedMessage.setBooleanProperty("TESTBoolean",false);
        assertEquals(retrievedMessage.getByteProperty("TESTByte"), 
                (byte)1);
        retrievedMessage.setByteProperty("TESTByte",(byte)2);
        assertEquals(retrievedMessage.getCorrelationId(), 
                "TESTCorrelationId");
        retrievedMessage.setCorrelationId("TESTCorrelationId2");
        assertEquals(retrievedMessage.getDoubleProperty("TESTDouble"), 
                (double)1.1);
        retrievedMessage.setDoubleProperty("TESTDouble",2.5);
        assertEquals(retrievedMessage.getFloatProperty("TESTFloat"), 
                (float)2.2);
        retrievedMessage.setFloatProperty("TESTFloat",(float)4.4);
        assertEquals(retrievedMessage.getFrom(), 
                "test@test.com");
        assertEquals(retrievedMessage.getIntProperty("TESTInt"), 
                100);
        retrievedMessage.setIntProperty("TESTInt",200);
        assertEquals(retrievedMessage.getLongProperty("TESTLong"), 
                (long)2000);
        retrievedMessage.setLongProperty("TESTLong",(long)4000);
        assertEquals(retrievedMessage.getTargetNamedQueue(), 
                "QUEUEName");
        assertEquals(retrievedMessage.getReplyNamedQueue(), 
                "ReplyQUEUEName");
        assertEquals(((MessageImpl)retrievedMessage).getNextProcessDate().
                getTime()/1000,currentTime.getTime()/1000);
        ((MessageImpl)retrievedMessage).setNextProcessDate(nextProcessTime);
        assertEquals(retrievedMessage.getObjectProperty("TESTObject"),
                "This is a test obj");
        retrievedMessage.setObjectProperty("TESTObject",
                "this is a new object value");
        assertEquals(retrievedMessage.getPriority(),1000);
        retrievedMessage.setPriority(2000);
        assertEquals(retrievedMessage.getProcessedDate().getTime()/1000,
                currentTime.getTime()/1000);
        retrievedMessage.setProcessedDate(nextProcessTime);
        assertEquals(retrievedMessage.getReply(),true);
        assertEquals(retrievedMessage.getState(),Message.UNDELIVERED);
        String[] services = retrievedMessage.getServices();
        int foundCount = 0;
        for (int index = 0; index < 3; index++) {
            if (services[index].equals("test1") ||
                    services[index].equals("test2") ||
                    services[index].equals("test3")) {
                foundCount++;
            }
        }
        assertEquals(foundCount,3);
        assertEquals(retrievedMessage.getTarget(),"target");
        assertEquals(retrievedMessage.getMessageCreater(),"test");
        List retrievedPrincipals = retrievedMessage.getMessagePrincipals();
        if (retrievedPrincipals.size() != 1) {
            fail("Invalid list principals");
        }
        if (!principals.equals(retrievedPrincipals)) {
            fail("The retrieved principals are not equal.");
        }
        List errors = retrievedMessage.getErrors();
        if (errors.size() != 2) {
            fail("Invalid number of errors");
        }
        foundCount = 0;
        for (Iterator iter = errors.iterator(); iter.hasNext();){
            com.rift.coad.daemon.messageservice.MessageError error = 
                    (com.rift.coad.daemon.messageservice.MessageError)iter.next();
            if ((error.getLevel() == 1) && 
                    (error.getMSG().equals("test error1"))) {
                foundCount++;
            } else if ((error.getLevel() == 2) && 
                    (error.getMSG().equals("test error2"))) {
                foundCount++;
            }
        }
        if (foundCount != 2) {
            fail("The errors were not stored correctly");
        }
        retrievedMessage.addError(3,"test error3");
        
        ut.begin();
        Session session = HibernateUtil.getInstance(MessageServiceManager.class).
                getSession();
        MessageQueue messageQueue = new MessageQueue("test");
        session.persist(messageQueue);
        instance.updateMessage(retrievedMessage);
        instance.assignToQueue("test");
        ut.commit();
        
        ut.begin();
        retrievedMessage = instance.getMessage();
        ut.commit();
        
        assertEquals(retrievedMessage.getMessageId(), "test");
        System.out.println("[" + 
                retrievedMessage.getStringProperty("TESTString") 
                + "][TESTvalue2]");
        assertEquals(retrievedMessage.getStringProperty("TESTString"), 
                "TESTvalue2");
        assertEquals(((TextMessage)retrievedMessage).getTextBody(), 
                "testing text again");
        assertEquals(retrievedMessage.getBooleanProperty("TESTBoolean"), 
                false);
        assertEquals(retrievedMessage.getByteProperty("TESTByte"), 
                (byte)2);
        assertEquals(retrievedMessage.getDoubleProperty("TESTDouble"), 
                (double)2.5);
        assertEquals(retrievedMessage.getFloatProperty("TESTFloat"), 
                (float)4.4);
        assertEquals(retrievedMessage.getFrom(), 
                "test@test.com");
        assertEquals(retrievedMessage.getIntProperty("TESTInt"), 
                200);
        assertEquals(retrievedMessage.getLongProperty("TESTLong"), 
                (long)4000);
        assertEquals(retrievedMessage.getTargetNamedQueue(), 
                "QUEUEName");
        assertEquals(retrievedMessage.getReplyNamedQueue(), 
                "ReplyQUEUEName");
        assertEquals(((MessageImpl)retrievedMessage).getNextProcessDate().
                getTime() / 1000,nextProcessTime.getTime() / 1000);
        assertEquals(retrievedMessage.getObjectProperty("TESTObject"),
                "this is a new object value");
        assertEquals(retrievedMessage.getProcessedDate().getTime() / 1000,
                nextProcessTime.getTime() / 1000);
        assertEquals(retrievedMessage.getReply(),true);
        services = retrievedMessage.getServices();
        foundCount = 0;
        for (int index = 0; index < 3; index++) {
            if (services[index].equals("test1") ||
                    services[index].equals("test2") ||
                    services[index].equals("test3")) {
                foundCount++;
            }
        }
        assertEquals(foundCount,3);
        assertEquals(retrievedMessage.getTarget(),"target");
        
        errors = retrievedMessage.getErrors();
        if (errors.size() != 3) {
            fail("Invalid number of errors");
        }
        foundCount = 0;
        for (Iterator iter = errors.iterator(); iter.hasNext();){
            com.rift.coad.daemon.messageservice.MessageError error = 
                    (com.rift.coad.daemon.messageservice.MessageError)iter.next();
            if ((error.getLevel() == 1) && 
                    (error.getMSG().equals("test error1"))) {
                foundCount++;
            } else if ((error.getLevel() == 2) && 
                    (error.getMSG().equals("test error2"))) {
                foundCount++;
            } else if ((error.getLevel() == 3) && 
                    (error.getMSG().equals("test error3"))) {
                foundCount++;
            }
        }
        if (foundCount != 3) {
            fail("The errors were not stored correctly");
        }
        
        ut.begin();
        instance.remove();
        ut.commit();
        
        
        currentTime = new Date();
        RPCMessageImpl rpcMessage = new RPCMessageImpl("test","test","test",
                principals,Message.UNDELIVERED);
        rpcMessage.setFrom("test");
        rpcMessage.setMethodBodyXML("test text");
        rpcMessage.setStringProperty("TESTString","TESTvalue");
        rpcMessage.setBooleanProperty("TESTBoolean",true);
        rpcMessage.setByteProperty("TESTByte",(byte)1);
        rpcMessage.setCorrelationId("TESTCorrelationId");
        rpcMessage.setDoubleProperty("TESTDouble",1.1);
        rpcMessage.setFloatProperty("TESTFloat",(float)2.2);
        rpcMessage.setFrom("test@test.com");
        rpcMessage.setIntProperty("TESTInt",100);
        rpcMessage.setLongProperty("TESTLong",(long)2000);
        rpcMessage.setTargetNamedQueue("QUEUEName");
        rpcMessage.setReplyNamedQueue("ReplyQUEUEName");
        rpcMessage.setNextProcessDate(currentTime);
        rpcMessage.setObjectProperty("TESTObject","This is a test obj");
        rpcMessage.setPriority(1000);
        rpcMessage.setProcessedDate(currentTime);
        rpcMessage.setReply(true);
        rpcMessage.setServices(new String[] {"test1","test2","test3"});
        rpcMessage.setTarget("target");
        
        ut.begin();
        
        MessageManagerImpl instance2 = new MessageManagerImpl(rpcMessage);
        
        ut.commit();
        
        Thread.sleep(30000);
        
        ut.begin();
        
        MessageManagerImpl instance3 = new MessageManagerImpl("test");
        
        ut.commit();
        
        ut.begin();
        retrievedMessage = instance2.getMessage();
        Message retrievedMessage2 = instance3.getMessage();
        ut.commit();
        nextProcessTime = new Date(currentTime.getTime() + 1000);
        
        
        assertEquals(retrievedMessage2.getMessageId(), "test");
        assertEquals(retrievedMessage2.getStringProperty("TESTString"), 
                "TESTvalue");
        assertEquals(((RPCMessage)retrievedMessage2).getMethodBodyXML(), 
                "test text");
        assertEquals(retrievedMessage2.getBooleanProperty("TESTBoolean"), 
                true);
        assertEquals(retrievedMessage2.getByteProperty("TESTByte"), 
                (byte)1);
        assertEquals(retrievedMessage2.getCorrelationId(), 
                "TESTCorrelationId");
        assertEquals(retrievedMessage2.getDoubleProperty("TESTDouble"), 
                (double)1.1);
        assertEquals(retrievedMessage2.getFloatProperty("TESTFloat"), 
                (float)2.2);
        assertEquals(retrievedMessage2.getFrom(), 
                "test@test.com");
        assertEquals(retrievedMessage2.getIntProperty("TESTInt"), 
                100);
        assertEquals(retrievedMessage2.getLongProperty("TESTLong"), 
                (long)2000);
        assertEquals(retrievedMessage2.getTargetNamedQueue(), 
                "QUEUEName");
        assertEquals(retrievedMessage2.getReplyNamedQueue(), 
                "ReplyQUEUEName");
        assertEquals(((MessageImpl)retrievedMessage2).getNextProcessDate().
                getTime()/1000,currentTime.getTime()/1000);
        assertEquals(retrievedMessage2.getObjectProperty("TESTObject"),
                "This is a test obj");
        assertEquals(retrievedMessage2.getPriority(),1000);
        assertEquals(retrievedMessage2.getProcessedDate().getTime()/1000,
                currentTime.getTime()/1000);
        assertEquals(retrievedMessage2.getReply(),true);
        services = retrievedMessage2.getServices();
        foundCount = 0;
        for (int index = 0; index < 3; index++) {
            if (services[index].equals("test1") ||
                    services[index].equals("test2") ||
                    services[index].equals("test3")) {
                foundCount++;
            }
        }
        assertEquals(foundCount,3);
        assertEquals(retrievedMessage2.getTarget(),"target");
        assertEquals(retrievedMessage2.getMessageCreater(),"test");
        retrievedPrincipals = retrievedMessage2.getMessagePrincipals();
        if (retrievedPrincipals.size() != 1) {
            fail("Invalid list principals");
        }
        if (!principals.equals(retrievedPrincipals)) {
            fail("The retrieved principals are not equal.");
        }
        
        
        assertEquals(retrievedMessage.getMessageId(), "test");
        System.out.println("[" + 
                retrievedMessage.getStringProperty("TESTString") 
                + "][TESTvalue]");
        assertEquals(retrievedMessage.getStringProperty("TESTString"), 
                "TESTvalue");
        retrievedMessage.setStringProperty("TESTString","TESTvalue2");
        assertEquals(((RPCMessage)retrievedMessage).getMethodBodyXML(), 
                "test text");
        ((RPCMessage)retrievedMessage).setResult("result");
        ((RPCMessage)retrievedMessage).setThrowable(new Exception("test"));
        assertEquals(retrievedMessage.getBooleanProperty("TESTBoolean"), 
                true);
        retrievedMessage.setBooleanProperty("TESTBoolean",false);
        assertEquals(retrievedMessage.getByteProperty("TESTByte"), 
                (byte)1);
        retrievedMessage.setByteProperty("TESTByte",(byte)2);
        assertEquals(retrievedMessage.getCorrelationId(), 
                "TESTCorrelationId");
        retrievedMessage.setCorrelationId("TESTCorrelationId2");
        assertEquals(retrievedMessage.getDoubleProperty("TESTDouble"), 
                (double)1.1);
        retrievedMessage.setDoubleProperty("TESTDouble",2.5);
        assertEquals(retrievedMessage.getFloatProperty("TESTFloat"), 
                (float)2.2);
        retrievedMessage.setFloatProperty("TESTFloat",(float)4.4);
        assertEquals(retrievedMessage.getFrom(), 
                "test@test.com");
        assertEquals(retrievedMessage.getIntProperty("TESTInt"), 
                100);
        retrievedMessage.setIntProperty("TESTInt",200);
        assertEquals(retrievedMessage.getLongProperty("TESTLong"), 
                (long)2000);
        retrievedMessage.setLongProperty("TESTLong",(long)4000);
        assertEquals(retrievedMessage.getTargetNamedQueue(), 
                "QUEUEName");
        assertEquals(retrievedMessage.getReplyNamedQueue(), 
                "ReplyQUEUEName");
        assertEquals(((MessageImpl)retrievedMessage).getNextProcessDate().
                getTime()/1000,currentTime.getTime()/1000);
        ((MessageImpl)retrievedMessage).setNextProcessDate(nextProcessTime);
        assertEquals(retrievedMessage.getObjectProperty("TESTObject"),
                "This is a test obj");
        retrievedMessage.setObjectProperty("TESTObject",
                "this is a new object value");
        assertEquals(retrievedMessage.getPriority(),1000);
        retrievedMessage.setPriority(2000);
        assertEquals(retrievedMessage.getProcessedDate().getTime()/1000,
                currentTime.getTime()/1000);
        retrievedMessage.setProcessedDate(nextProcessTime);
        assertEquals(retrievedMessage.getReply(),true);
        services = retrievedMessage.getServices();
        foundCount = 0;
        for (int index = 0; index < 3; index++) {
            if (services[index].equals("test1") ||
                    services[index].equals("test2") ||
                    services[index].equals("test3")) {
                foundCount++;
            }
        }
        assertEquals(foundCount,3);
        assertEquals(retrievedMessage.getTarget(),"target");
        assertEquals(retrievedMessage.getMessageCreater(),"test");
        retrievedPrincipals = retrievedMessage.getMessagePrincipals();
        if (retrievedPrincipals.size() != 1) {
            fail("Invalid list principals");
        }
        if (!principals.equals(retrievedPrincipals)) {
            fail("The retrieved principals are not equal.");
        }
        
        ut.begin();
        instance2.updateMessage(retrievedMessage);
        instance2.assignToQueue("test");
        ut.commit();
        
        ut.begin();
        retrievedMessage = instance2.getMessage();
        
        ut.commit();
        
        assertEquals(retrievedMessage.getMessageId(), "test");
        System.out.println("[" + 
                retrievedMessage.getStringProperty("TESTString") 
                + "][TESTvalue2]");
        assertEquals(retrievedMessage.getStringProperty("TESTString"), 
                "TESTvalue2");
        assertEquals(((RPCMessage)retrievedMessage).getMethodBodyXML(), 
                "test text");
        assertEquals(((RPCMessage)retrievedMessage).getResult(), 
                "result");
        assertEquals(((RPCMessage)retrievedMessage).getThrowable().getMessage(), 
                "test");
        assertEquals(retrievedMessage.getBooleanProperty("TESTBoolean"), 
                false);
        assertEquals(retrievedMessage.getByteProperty("TESTByte"), 
                (byte)2);
        assertEquals(retrievedMessage.getDoubleProperty("TESTDouble"), 
                (double)2.5);
        assertEquals(retrievedMessage.getFloatProperty("TESTFloat"), 
                (float)4.4);
        assertEquals(retrievedMessage.getFrom(), 
                "test@test.com");
        assertEquals(retrievedMessage.getIntProperty("TESTInt"), 
                200);
        assertEquals(retrievedMessage.getLongProperty("TESTLong"), 
                (long)4000);
        assertEquals(retrievedMessage.getTargetNamedQueue(), 
                "QUEUEName");
        assertEquals(retrievedMessage.getReplyNamedQueue(), 
                "ReplyQUEUEName");
        assertEquals(((MessageImpl)retrievedMessage).getNextProcessDate().
                getTime() / 1000,nextProcessTime.getTime() / 1000);
        assertEquals(retrievedMessage.getObjectProperty("TESTObject"),
                "this is a new object value");
        assertEquals(retrievedMessage.getProcessedDate().getTime() / 1000,
                nextProcessTime.getTime() / 1000);
        assertEquals(retrievedMessage.getReply(),true);
        assertEquals(retrievedMessage.getState(),Message.UNDELIVERED);
        services = retrievedMessage.getServices();
        foundCount = 0;
        for (int index = 0; index < 3; index++) {
            if (services[index].equals("test1") ||
                    services[index].equals("test2") ||
                    services[index].equals("test3")) {
                foundCount++;
            }
        }
        assertEquals(foundCount,3);
        assertEquals(retrievedMessage.getTarget(),"target");
        
        ut.begin();
        instance2.remove();
        ut.commit();
        
        Thread.sleep(30000);
        
        ChangeLog.getInstance().terminate();
        
    }

    
}
