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
 * Base64Test.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.thirdparty.base64;

// java imports
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;

// junit imports
import junit.framework.*;


/**
 *
 * @author Brett Chaldecot
 */
public class Base64Test extends TestCase {
    
    public Base64Test(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(Base64Test.class);
        
        return suite;
    }

    /**
     * Test of encodeObject method, of class com.rift.coad.lib.thirdparty.base64.Base64.
     */
    public void testEncodeString() {
        System.out.println("encodeString");
        
        java.io.Serializable serializableObject = null;
        
        String startBuff = "This is me testing the starting point";
        String encodedString = Base64.encodeBytes(startBuff.getBytes());
        System.out.println(encodedString);
        String endBuff = new String(Base64.decode(encodedString));
        if (!startBuff.equals(endBuff)) {
            fail("Did not decode properly");
        }
    }

    
}
