/*
 * EMailServer: The email server implementation.
 * Copyright (C) 2008  2015 Burntjam
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
 * HeaderTest.java
 */

package com.rift.coad.daemon.email.smtp;

import junit.framework.*;

/**
 *
 * @author brett
 */
public class HeaderTest extends TestCase {
    
    public HeaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getKey method, of class com.rift.coad.daemon.email.smtp.Header.
     */
    public void testGetKey() {
        System.out.println("getKey");
        
        Header instance = new Header("bob","bob");
        
        String expResult = "bob";
        String result = instance.getKey();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setKey method, of class com.rift.coad.daemon.email.smtp.Header.
     */
    public void testSetKey() {
        System.out.println("setKey");
        
        Header instance = new Header("bill","bill");
        
        String expResult = "bob";
        instance.setKey(expResult);
        
        String result = instance.getKey();
        assertEquals(expResult, result);    }

    /**
     * Test of getValue method, of class com.rift.coad.daemon.email.smtp.Header.
     */
    public void testGetValue() {
        System.out.println("getValue");
        
        Header instance = new Header("bob","bob");
        
        String expResult = "bob";
        String result = instance.getValue();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setValue method, of class com.rift.coad.daemon.email.smtp.Header.
     */
    public void testSetValue() {
        System.out.println("setValue");
        
        String value = "bill";
        Header instance = new Header("bob","bob");
        
        instance.setValue(value);
        
        String result = instance.getValue();
        assertEquals(value, result);
    }
    
}
