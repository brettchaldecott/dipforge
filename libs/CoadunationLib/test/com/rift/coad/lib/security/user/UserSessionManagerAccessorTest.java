/*
 * CoadunationLib: The coaduntion implementation library.
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
 * UserSessionManagerAccessorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.security.user;

// java imports
import java.util.Set;
import java.util.HashSet;

// junit imports
import junit.framework.*;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.SecurityException;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.UserSession;

/**
 * This object is responsible for testing the accessor class.
 *
 * @author Brett Chaldecott
 */
public class UserSessionManagerAccessorTest extends TestCase {
    
    public UserSessionManagerAccessorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(UserSessionManagerAccessorTest.class);
        
        return suite;
    }

    /**
     * Test of init method, of class com.rift.coad.lib.security.user.UserSessionManagerAccessor.
     */
    public void testUserSessionManagerAccessor() throws Exception {
        System.out.println("testUserSessionManagerAccessor");
        
        // initialize the session manager
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        SessionManager.init(permissionContainer);
        SessionManager.getInstance().initSession();
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // init the user session manager
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager userSessionManager = new UserSessionManager(
                permissionContainer,userStoreManager);
        
        // init the user session manager accessor
        UserSessionManagerAccessor userSessionManagerAccessor = 
                UserSessionManagerAccessor.init(userSessionManager);
        
        // get the user session manager
        try {
            userSessionManagerAccessor.getUserSessionManager();
            fail("Was granted access to the user session manager");
        } catch (AuthorizationException ex) {
            // ignore
        }
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("testuser", set);
        permissionContainer.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // get the user session manager
        try {
            userSessionManagerAccessor.getUserSessionManager();
        } catch (AuthorizationException ex) {
            fail("Was not granted access to the user session manager accessor");
        }
    }

    
    
}
