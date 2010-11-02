/*
 * MessageManagerFactoryTest.java
 * JUnit based test
 *
 * Created on January 15, 2007, 9:31 PM
 */

package com.rift.coad.daemon.messageservice.message;

// java imports
import com.rift.coad.lib.thread.ThreadGroupManager;
import com.rift.coad.util.change.ChangeLog;
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
 *
 * @author mincemeat
 */
public class MessageManagerFactoryTest extends TestCase {
    
    public MessageManagerFactoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.daemon.messageservice.message.MessageManagerFactory.
     */
    public void testMessageManagerFactory() throws Exception {
        System.out.println("MessageManagerFactory");
        
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
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        ut.begin();
        
        MessageManager instance = MessageManagerFactory.getInstance().
                getMessageManager(textMessage);
        
        ut.commit();
        
        Thread.sleep(30000);
        
        ut.begin();
        
        MessageManager instance2 = MessageManagerFactory.getInstance().
                getMessageManager("test");
        
        ut.commit();
        
        assertEquals(instance2.getID(),instance.getID());
        
        ut.begin();
        
        instance2.remove();
        
        ut.commit();
        
        Thread.sleep(30000);
        
        ChangeLog.getInstance().terminate();
    }

    
}
