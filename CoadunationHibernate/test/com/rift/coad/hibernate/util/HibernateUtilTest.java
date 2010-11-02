/*
 * HibernateUtilTest.java
 * JUnit based test
 *
 * Created on January 13, 2007, 3:47 PM
 */

package com.rift.coad.hibernate.util;

import junit.framework.*;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.*;

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
import com.rift.coad.lib.transaction.TransactionDirector;


/**
 *
 * @author mincemeat
 */
public class HibernateUtilTest extends TestCase {
    
    public HibernateUtilTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.hibernate.util.HibernateUtil.
     */
    public void testHibernateUtil() throws Exception {
        System.out.println("getInstance");
        
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
        
        // check the hibernate util.
        HibernateUtil result = HibernateUtil.getInstance(this.getClass());
        assertEquals(HibernateUtil.getInstance(this.getClass()), result);
        
        // retrieve the user transaction
        Context context = new InitialContext();
        UserTransaction ut =
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        
        ut.begin();
        
        Session session1 = HibernateUtil.getInstance(this.getClass()).
                getSession();
        Session session2 = HibernateUtil.getInstance(this.getClass()).
                getSession();
        assertEquals(session1, session2);
        
        ut.commit();
    }

    
}
