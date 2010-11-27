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
 * ValidatorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.security;

import junit.framework.*;

// java includes
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Brett Chaldecott
 */
public class ValidatorTest extends TestCase {
    
    public ValidatorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ValidatorTest.class);
        
        return suite;
    }

    /**
     * Test of validate method, of class com.rift.coad.lib.security.Validator.
     */
    public void testValidate() throws Exception {
        System.out.println("validate");
        
        Class ref = null;
        String roleName = "";
        
        // initialize the session manager
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        SessionManager.init(permissionContainer);
        SessionManager.getInstance().initSession();
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        try {
            Validator.validate(getClass(), "test");
            fail("The test case is a prototype.");
        } catch (Exception ex) {
            System.out.println("Access failed success : " + ex.getMessage());
        }
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("testuser", set);
        permissionContainer.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        try {
            Validator.validate(getClass(), "test");
        } catch (Exception ex) {
            fail("The test case is a prototype : " + ex.getMessage());
        }
        
        permissionContainer.pushRole("middle");
        permissionContainer.pushRole("master");
        
        try {
            Validator.validate(getClass(), "master");
        } catch (Exception ex) {
            fail("The test case is a prototype : " + ex.getMessage());
        }
        
        permissionContainer.popRole("master");
        permissionContainer.popRole("middle");
        
        try {
            Validator.validate(getClass(), "master");
            fail("Invalid role should not have role master");
        } catch (Exception ex) {
            // ignore
        }

        try {
            Validator.validate(getClass(), "test");
        } catch (Exception ex) {
            fail("The test case is a prototype : " + ex.getMessage());
        }

    }
    
}
