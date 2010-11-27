/*
 * Email Server: The email server.
 * Copyright (C) 2008  Rift IT Contracting
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
 * RouteEntryTest.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.route;

// package path
import junit.framework.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.MessageInfo;


/**
 * This object is responsible for testing the route entry object.
 *
 * @author brett chaldecott
 */
public class RouteEntryTest extends TestCase {
    
    public RouteEntryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getName method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testGetName() {
        System.out.println("getName");
        
        RouteEntry instance = new RouteEntry("test","jndi:test");
        
        String expResult = "test";
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getJNDI method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testGetJNDI() {
        System.out.println("getJNDI");
        
        RouteEntry instance = new RouteEntry("test","jndi:test");
        
        String expResult = "jndi:test";
        String result = instance.getJNDI();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of addHeader method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testAddHeader() {
        System.out.println("addHeader");
        
        String key = "test";
        String value = "test value";
        RouteEntry instance = new RouteEntry("test","jndi:test");
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo messageInfo = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        assertEquals(true, instance.checkEntry(messageInfo));
        
        instance.addHeader(key, value);
        
        assertEquals(false, instance.checkEntry(messageInfo));
        headers.add(new Header("test","test value"));
        assertEquals(true, instance.checkEntry(messageInfo));
        
    }

    /**
     * Test of addType method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testAddType() {
        System.out.println("addType");
        
        Integer type = new Integer(1);
        RouteEntry instance = new RouteEntry("test","jndi:test");
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo messageInfo = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        assertEquals(true, instance.checkEntry(messageInfo));
        
        instance.addType(type);
        
        assertEquals(false, instance.checkEntry(messageInfo));
        messageInfo.setType(1);
        assertEquals(true, instance.checkEntry(messageInfo));
    }

    /**
     * Test of addEntry method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testAddEntry() {
        System.out.println("addEntry");
        
        RouteEntry entry = new RouteEntry("test","jndi:test");
        RouteEntry instance = new RouteEntry("test1","jndi:test");
        
        instance.addEntry(entry);
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo messageInfo = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        assertEquals(entry, instance.getEntry(messageInfo));
    }

    /**
     * Test of getEntry method, of class com.rift.coad.daemon.email.server.smtp.route.RouteEntry.
     */
    public void testGetEntry() {
        System.out.println("getEntry");
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo messageInfo = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        
        RouteEntry instance = new RouteEntry("test","jndi:test");
        RouteEntry expResult = new RouteEntry("test","jndi:test");
        instance.addEntry(expResult);
        RouteEntry result = instance.getEntry(messageInfo);
        assertEquals(expResult, result);
        
        expResult.addHeader("test","test");
        assertEquals(null, instance.getEntry(messageInfo));
    }
    
}
