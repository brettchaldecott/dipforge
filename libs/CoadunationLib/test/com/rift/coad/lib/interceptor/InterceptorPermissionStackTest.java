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
 * InterceptorPermissionStackTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.interceptor;

import junit.framework.*;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;

/**
 *
 * @author Brett Chaldecott
 */
public class InterceptorPermissionStackTest extends TestCase {
    
    public InterceptorPermissionStackTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(InterceptorPermissionStackTest.class);
        
        return suite;
    }
    
    /**
     * Test of InterceptorPermissionStack, of class com.rift.coad.lib.interceptor.InterceptorPermissionStack.
     */
    public void testInterceptorPermissionStack() throws Exception {
        System.out.println("InterceptorPermissionStack");
        
        // initialize the session manager
        ThreadsPermissionContainer permissionContainer =
                new ThreadsPermissionContainer();
        
        InterceptorPermissionStack instance = new InterceptorPermissionStack(
                permissionContainer);
        
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("testuser", set);
        
        instance.push(user);
        
        // retrieve the permission session
        ThreadPermissionSession permissionSession = 
                permissionContainer.getSession(Thread.currentThread().getId());
        if (!permissionSession.getUser().getName().equals("testuser")) {
            fail("Username is not testuser");
        }
        
        Set currentPrincipalSet = permissionSession.getPrincipals();
        if (currentPrincipalSet.size() != 1) {
            fail("The principal set is not the correct size");
        }
        if (!currentPrincipalSet.contains("test")) {
            fail("Principal set does not contain the test principal");
        }
        
        Set newSet = new HashSet();
        newSet.add("fred");
        newSet.add("mary");
        UserSession newUser = new UserSession("fred", newSet);
        UserSession newUser2 = (UserSession)newUser.clone();
        if (!newUser2.getName().equals(newUser.getName())) {
            fail("The clone did not work");
        }
        
        
        instance.push(newUser);
        
        permissionSession = 
                permissionContainer.getSession(Thread.currentThread().getId());
        if (!permissionSession.getUser().getName().equals("fred")) {
            fail("Username is not fred");
        }
        
        currentPrincipalSet = permissionSession.getPrincipals();
        if (currentPrincipalSet.size() != 2) {
            fail("Failed the principal sets are not equal");
        }
        if (!currentPrincipalSet.contains("mary")) {
            fail("Principal set does not contain the fred principal");
        }
        
        
        instance.pop();
        
        permissionSession = 
                permissionContainer.getSession(Thread.currentThread().getId());
        if (!permissionSession.getUser().getName().equals("testuser")) {
            fail("Username is not testuser");
        }
        
        currentPrincipalSet = permissionSession.getPrincipals();
        if (currentPrincipalSet.size() != 1) {
            fail("The principal set is not the correct size");
        }
        if (!currentPrincipalSet.contains("test")) {
            fail("Principal set does not contain the test principal");
        }
        
        instance.pop();
        
        permissionSession = 
                permissionContainer.getSession(Thread.currentThread().getId());
        if (permissionSession != null) {
            fail("The permissions were not removed");
        }
    }
    
    
}
