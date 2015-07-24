/*
 * CoadunationLib: The coaduntion implementation library.
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
 * TransactionDirectorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.transaction;

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
import javax.transaction.TransactionManager;

// junit imports
import junit.framework.*;

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

/**
 *
 * @author mincemeat
 */
public class TransactionDirectorTest extends TestCase {
    
    public TransactionDirectorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TransactionDirectorTest.class);
        
        return suite;
    }

    /**
     * Test of TransactionDirector, of class com.rift.coad.lib.transaction.TransactionDirector.
     */
    public void testTransactionDirector() throws Exception {
        System.out.println("TransactionDirector");
        
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
        TransactionDirector result = TransactionDirector.init();
        
        if (result != TransactionDirector.getInstance()) {
            fail("Singleton does not operate correctly");
        }
        
        // init the database source
        DBSourceManager.init();
        
        Context context = new InitialContext();
        DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
        Object ref = context.lookup("java:comp/UserTransaction");
        System.out.println("User transaction is [" + ref.getClass().getName() + "]");
        UserTransaction ut = 
                (UserTransaction)context.lookup("java:comp/UserTransaction");
        if (ut == null) {
            fail("User transaction is equal to null");
        }
        if (context.lookup("java:comp/TransactionManager") == null) {
            fail("Cannot retrieve valid transaction manager");
        }
        
        java.sql.Connection conn = ds.getConnection();
        ut.begin();
        
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO test (id) VALUES (1)");
        ResultSet rs = stmt.executeQuery("SELECT * FROM test");
        if (!rs.next()) {
            fail("Failed to retrieve result from database");
        }
        
        ut.rollback();
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM test");
        if (rs.next()) {
            fail("Failed: retrieved result from database");
        }
        
        conn.close();
        
        result.stop();
        
        DataSource ds2 = (DataSource)context.lookup("java:comp/env/jdbc/test2");
        
        if (ds2 == null) {
            fail("Failed to retrieve the second database connection");
        }
        
        NamingDirector.getInstance().shutdown();
    }

    
}
