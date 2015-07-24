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
 * NamingParserTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.naming.cos;

import junit.framework.*;
import java.util.Enumeration;
import java.util.Properties;
import java.io.Serializable;
import javax.naming.CompoundName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

/**
 * This test tests the naming parser
 *
 * @author Brett Chaldecott
 */
public class NamingParserTest extends TestCase {
    
    public NamingParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(NamingParserTest.class);
        
        return suite;
    }

    /**
     * Test of parse method, of class com.rift.coad.lib.naming.cos.NamingParser.
     */
    public void testParse() throws Exception {
        System.out.println("parse");
        
        String name = "";
        NamingParser instance = new NamingParser();
        
        Name result = instance.parse("java:comp/test/freddy");
        int index = 0;
        for (Enumeration enumer = result.getAll(); enumer.hasMoreElements();index++) {
            String value = enumer.nextElement().toString();
            if ((index == 0) && (!value.equals("java:comp"))) {
                fail("Parsing failed");
            }
            if ((index == 1) && (!value.equals("test"))) {
                fail("Parsing failed");
            }
            if ((index == 2) && (!value.equals("freddy"))) {
                fail("Parsing failed");
            }
        }
    }
    
}
