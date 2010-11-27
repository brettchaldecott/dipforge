/*
 * Tomcat: The deployer for the tomcat daemon
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
 * CoadunationGenericPrincipalTest.java
 */

package com.rift.coad.daemon.tomcat.security;

import junit.framework.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.Realm;
import com.rift.coad.lib.security.UserSession;

/**
 *
 * @author brett
 */
public class CoadunationGenericPrincipalTest extends TestCase {
    
    public CoadunationGenericPrincipalTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getSession method, of class com.rift.coad.daemon.tomcat.security.CoadunationGenericPrincipal.
     */
    public void testGetSession() throws Exception {
        System.out.println("getSession");
        
        UserSession expResult = new UserSession();
        CoadunationGenericPrincipal instance = new CoadunationGenericPrincipal(
                new CoadunationRealm(), "test", "112233",new ArrayList(), 
                expResult);
        
        UserSession result = instance.getSession();
        assertEquals(expResult, result);
        
    }
    
}
