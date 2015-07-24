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
 * XMLUserParserTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.security.user.xml;

import junit.framework.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.user.UserException;

/**
 *
 * @author mincemeat
 */
public class XMLUserParserTest extends TestCase {
    
    public XMLUserParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(XMLUserParserTest.class);
        
        return suite;
    }

    /**
     * Test of getUsers method, of class com.rift.coad.lib.security.user.xml.XMLUserParser.
     */
    public void testGetUsers() throws Exception {
        System.out.println("getUsers");
        
        // the list of users
        XMLUserParser instance = new XMLUserParser();
        Map users = instance.getUsers();
        
        Map result = instance.getUsers();
        assertEquals(users, result);
        
        // retrieve the test data
        UserData userData = (UserData)result.get("test");
        
        // retrieve the password
        assertEquals("112233", userData.getPassword());
        
        // test the principals
        if (userData.getPrincipals().contains("test1") == false) {
            fail("Expecting principal test1 to be set.");
        } else if (userData.getPrincipals().contains("test2") == false) {
            fail("Expecting principal test2 to be set.");
        } else if (userData.getPrincipals().contains("test3") == false) {
            fail("Expecting principal test3 to be set.");
        }
        
        userData = (UserData)result.get("test2");
        
        // retrieve the password
        assertEquals("11223344", userData.getPassword());
        
        // test the principals
        if (userData.getPrincipals().contains("test1") == false) {
            fail("Expecting principal test1 to be set.");
        } else if (userData.getPrincipals().contains("test5") == false) {
            fail("Expecting principal test5 to be set.");
        }
        
    }
    
}
