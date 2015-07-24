/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  2015 Burntjam
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
 * AuditTrailTest.java
 * JUnit based test
 */

// package path
package com.rift.coad.lib.audit;

// java imports
import java.util.HashSet;
import java.util.Set;

// junit import
import junit.framework.*;

// log4j
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


// coadunation imports
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Login;
import com.rift.coad.lib.interceptor.credentials.Session;
import com.rift.coad.lib.interceptor.authenticator.SessionAuthenticator;
import com.rift.coad.lib.security.AuthorizationException;
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
 * The testing of the audit trail test.
 *
 * @author brett chaldecott
 */
public class AuditTrailTest extends TestCase {
    
    public AuditTrailTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getAudit method, of class com.rift.coad.lib.audit.AuditTrail.
     */
    public void testGetAudit() throws Exception {
        System.out.println("getAudit");
        
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
        
        // setup the audit trail class
        Class target = AuditTrailTest.class;
        
        AuditTrail result = AuditTrail.getAudit(target);
        
        // log an res
        result.logEvent("test");
        result.logEvent("test",new Exception("Test"));
        
        // setup the user
        Set set = new HashSet();
        set.add("test");
        set.add("test1");
        set.add("test2");
        set.add("test3");
        UserSession user = new UserSession("test", set);
        ThreadPermissionSession currentSession = new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                currentSession);
        
        
        result.logEvent("test2");
        result.logEvent("test2",new Exception("Test2"));
        
        
    }

       
}
