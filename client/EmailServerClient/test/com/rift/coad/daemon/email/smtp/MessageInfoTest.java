/*
 * Email Server: The email server interface
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
 * MessageInfoTest.java
 */

package com.rift.coad.daemon.email.smtp;

import com.rift.coad.daemon.email.types.Address;
import junit.framework.*;
import java.util.List;
import java.util.ArrayList;

/**
 * This object represents a message test.
 *
 * @author brett chaldecott
 */
public class MessageInfoTest extends TestCase {
    
    public MessageInfoTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getId method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetId() {
        System.out.println("getId");
        
        MessageInfo instance = new MessageInfo("test", 1, 
                MessageInfo.STATUS.DELIVERED, null,null, null);
        
        String expResult = "test";
        String result = instance.getId();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setId method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetId() {
        System.out.println("setId");
        
        String id = "test2";
        MessageInfo instance = new MessageInfo();
        
        instance.setId(id);
        
        String expResult = "test2";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetType() {
        System.out.println("getType");
        
        MessageInfo instance = new MessageInfo("test", 2, 
                MessageInfo.STATUS.DELIVERED, null,null, null);
        
        int expResult = 2;
        int result = instance.getType();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setType method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetType() {
        System.out.println("setType");
        
        int type = 3;
        MessageInfo instance = new MessageInfo();
        
        instance.setType(type);
        
        int expResult = 3;
        int result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatus method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetStatus() {
        System.out.println("getStatus");
        
        MessageInfo instance = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, null);
        
        MessageInfo.STATUS expResult = MessageInfo.STATUS.UNPROCESSED;
        MessageInfo.STATUS result = instance.getStatus();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setStatus method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetStatus() {
        System.out.println("setStatus");
        
        MessageInfo.STATUS status = MessageInfo.STATUS.DELIVERED;
        MessageInfo instance = new MessageInfo();
        
        instance.setStatus(status);
        
        MessageInfo.STATUS expResult = MessageInfo.STATUS.DELIVERED;
        MessageInfo.STATUS result = instance.getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFrom method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetFrom() throws Exception {
        System.out.println("getFrom");
        
        List from = new ArrayList();
        from.add(new Address("bob@bob.com"));
        from.add(new Address("fred@fred.com"));
        MessageInfo instance = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, from,null, null);
        
        List expResult = from;
        List result = instance.getFrom();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFrom method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetFrom() throws Exception {
        System.out.println("setFrom");
        
        List from = new ArrayList();
        from.add(new Address("bob@bob.com"));
        from.add(new Address("fred@fred.com"));
        MessageInfo instance = new MessageInfo();
        
        instance.setFrom(from);
        
        List expResult = from;
        List result = instance.getFrom();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFrom method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testAddFrom() throws Exception {
        System.out.println("addFrom");
        
        Address from = new Address("test@test.com");
        MessageInfo instance = new MessageInfo();
        
        instance.addFrom(from);
        
        List result = instance.getFrom();
        assertEquals(1, result.size());
        assertEquals(from, result.get(0));
    }

    /**
     * Test of getRCPTs method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetRCPTs() throws Exception{
        System.out.println("getRCPTs");
        
        List rcpts = new ArrayList();
        rcpts.add(new Address("new@new.com"));
        rcpts.add(new Address("old@old.com"));
        MessageInfo instance = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,rcpts, null);
        
        List expResult = rcpts;
        List result = instance.getRCPTs();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setRCPTs method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetRCPTs() throws Exception {
        System.out.println("setRCPTs");
        
        List rcpts = new ArrayList();
        rcpts.add(new Address("new@new.com"));
        rcpts.add(new Address("old@old.com"));
        MessageInfo instance = new MessageInfo();
        
        instance.setRCPTs(rcpts);
        
        List expResult = rcpts;
        List result = instance.getRCPTs();
        assertEquals(expResult, result);
    }

    /**
     * Test of addRCPT method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testAddRCPT() throws Exception {
        System.out.println("addRCPT");
        
        Address rcpt = new Address("test@test.com");
        MessageInfo instance = new MessageInfo();
        
        instance.addRCPT(rcpt);
        
        List result = instance.getRCPTs();
        assertEquals(1, result.size());
        assertEquals(rcpt, result.get(0));
    }

    /**
     * Test of getHeaders method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testGetHeaders() {
        System.out.println("getHeaders");
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo instance = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        List expResult = headers;
        List result = instance.getHeaders();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setHeaders method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testSetHeaders() throws Exception {
        System.out.println("setHeaders");
        
        List headers = new ArrayList();
        headers.add(new Header("test1","test value 1"));
        headers.add(new Header("test2","test value 2"));
        MessageInfo instance = new MessageInfo();
        
        instance.setHeaders(headers);
        
        List expResult = headers;
        List result = instance.getHeaders();
        assertEquals(expResult, result);
    }

    /**
     * Test of addHeader method, of class com.rift.coad.daemon.email.smtp.MessageInfo.
     */
    public void testAddHeader() throws Exception {
        System.out.println("addHeader");
        
        Header header = new Header("fred","freds value");
        MessageInfo instance = new MessageInfo();
        
        instance.addHeader(header);
        
        List result = instance.getHeaders();
        assertEquals(1, result.size());
        assertEquals(header, result.get(0));
    }
    
}
