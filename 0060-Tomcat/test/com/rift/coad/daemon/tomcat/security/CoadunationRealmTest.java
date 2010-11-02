/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  Rift IT Contracting
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
 * CoadunationRealmTest.java
 * JUnit based test
 */

package com.rift.coad.daemon.tomcat.security;

// java imports
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

// log 4 j imports
import org.apache.log4j.Logger;

// junit imports
import junit.framework.*;

// tomcat imports
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Realm;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.Constants;
import org.apache.catalina.realm.GenericPrincipal;

// coadunation imports
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.security.BasicPrincipal;
import com.rift.coad.lib.security.login.SessionLogin;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Role;

/**
 * The coadunation coadunation realm test
 *
 * @author brett
 */
public class CoadunationRealmTest extends TestCase {
    
    public CoadunationRealmTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getName method, of class com.rift.coad.daemon.tomcat.security.CoadunationRealm.
     */
    public void testGetName() {
        System.out.println("getName");
        
        CoadunationRealm instance = new CoadunationRealm();
        
        String expResult = instance.getClass().getName();
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPassword method, of class com.rift.coad.daemon.tomcat.security.CoadunationRealm.
     */
    public void testGetPassword() {
        System.out.println("getPassword");
        
        String username = "test";
        CoadunationRealm instance = new CoadunationRealm();
        
        String expResult = null;
        String result = instance.getPassword(username);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPrincipal method, of class com.rift.coad.daemon.tomcat.security.CoadunationRealm.
     */
    public void testGetPrincipal() {
        System.out.println("getPrincipal");
        
        String username = "test";
        CoadunationRealm instance = new CoadunationRealm();
        
        Principal expResult = new BasicPrincipal("test");
        Principal result = instance.getPrincipal(username);
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of authenticate method, of class com.rift.coad.daemon.tomcat.security.CoadunationRealm.
     */
    public void testAuthenticate() throws Exception {
        System.out.println("authenticate");
        
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        ThreadsPermissionContainerAccessor.init(permissions);
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserStoreManagerAccessor.init(userStoreManager);
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        UserSessionManagerAccessor.init(sessionManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        String username = "test";
        String credentials = "112233";
        CoadunationRealm instance = new CoadunationRealm();
        
        CoadunationGenericPrincipal result = (CoadunationGenericPrincipal)instance.authenticate(username, credentials);
        assertEquals("test", result.getSession().getName());
        
    }
    
}
