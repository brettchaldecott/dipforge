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
 * ThreadsPermissionContainerAccessorTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.security;

// junit imports
import junit.framework.*;

// java imports
import java.util.Set;
import java.util.HashSet;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

/**
 *
 * @author Brett Chaldecott
 */
public class ThreadsPermissionContainerAccessorTest extends TestCase {
    
    public ThreadsPermissionContainerAccessorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ThreadsPermissionContainerAccessorTest.class);
        
        return suite;
    }

    /**
     * Test of init method, of class com.rift.coad.lib.security.ThreadsPermissionContainerAccessor.
     */
    public void testAccessor() throws Exception {
        System.out.println("testAccessor");
        
        Class ref = null;
        String roleName = "";
        
        // initialize the session manager
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        SessionManager.init(permissionContainer);
        SessionManager.getInstance().initSession();
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // instanciate the container
        ThreadsPermissionContainerAccessor result =
                ThreadsPermissionContainerAccessor.init(permissionContainer);
        
        try {
            ThreadsPermissionContainerAccessor.getInstance().
                    getThreadsPermissionContainer();
            fail("Was granted access to the permission container");
        } catch (AuthorizationException ex) {
            // ignore this was a success
        }
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("testuser", set);
        permissionContainer.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // failed to get access to the perimission container
        try {
            ThreadsPermissionContainerAccessor.getInstance().
                    getThreadsPermissionContainer();
        } catch (AuthorizationException ex) {
            fail("Failed to get access to the permission container");
        }
    }

    
}
