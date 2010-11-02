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
 * UserSessionManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.security.user;

import java.util.HashSet;
import junit.framework.*;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;

/**
 * The definition of the user session manager class.
 *
 * @author Brett Chaldecott
 */
public class UserSessionManagerTest extends TestCase {
    
    public UserSessionManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(UserSessionManagerTest.class);
        
        return suite;
    }

    /**
     * Test of initSessionForUser method, of class com.rift.coad.lib.security.user.UserSessionManager.
     */
    public void testInitSessionForUser() throws Exception {
        System.out.println("initSessionForUser");
        
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager instance = new UserSessionManager(permissions,
                userStoreManager);
        instance.startCleanup();
        // attempt to init the user
        instance.initSessionForUser("test1");
        
        // create a test user
        UserSession user = new UserSession("test2",new HashSet());
        user.setExpiryTime(500);
        instance.initSessionForUser(user);
        UserSession user2 = new UserSession("test2",new HashSet());
        user2.setExpiryTime(500);
        instance.initSessionForUser(user2);
        
        // retrieve the session via user
        if (user != instance.getSessionById(user.getSessionId())) {
            fail("The user session could not be found by id.");
        }
        if (user2 != instance.getSessionById(user2.getSessionId())) {
            fail("The user 2 session could not be found by id.");
        }
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(450);
            user2.touch();
        }
        
        try {
            instance.getSessionById(user.getSessionId());
            fail("The user session could be found.");
        } catch (UserException ex) {
            // ignore
        }
        if (user2 != instance.getSessionById(user2.getSessionId())) {
            fail("The user 2 session could be found.");
        }
        
        instance.shutdown();
    }
    
}
