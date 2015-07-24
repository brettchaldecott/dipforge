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
 * CookieWrapperTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.httpd;

import junit.framework.*;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 *
 * @author mincemeat
 */
public class CookieWrapperTest extends TestCase {
    
    public CookieWrapperTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CookieWrapperTest.class);
        
        return suite;
    }

    /**
     * Test creation of a cookie class com.rift.coad.lib.httpd.CookieWrapper.
     */
    public void testCookieGenerate() throws Exception {
        System.out.println("testCookieGenerate");
        
        CookieWrapper instance = new CookieWrapper("test","value");
        java.util.Date testDate = new java.util.Date();
        instance.setDomain("test.com");
        instance.setPath("/test");
        if (!instance.getName().equals("test")){
            fail("Name set incorrectly");
        } else if (!instance.getValue().equals("value")){
            fail("Value set incorrectly");
        } else if (!instance.getDomain().equals("test.com")){
            fail("Domain name set incorrectly");
        } else if (!instance.getPath().equals("/test")){
            fail("The path has not been set correctly");
        }
        
        
        System.out.println(instance.getSetCookieString());
        instance.setPath(null);
        System.out.println(instance.getSetCookieString());
    }
}
