/*
 * EMailServer: The email server implementation.
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
 * MessageTest.java
 */

// package path
package com.rift.coad.daemon.email.smtp;

// package imports
import com.rift.coad.daemon.email.types.Address;
import junit.framework.*;
import java.util.List;
import java.util.ArrayList;

/**
 * This object is responsible for testing the message.
 *
 * @author brett chaldecott
 */
public class MessageTest extends TestCase {
    
    public MessageTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getFrom method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testGetFrom() throws Exception {
        System.out.println("getFrom");
        
        List expResult = new ArrayList();
        expResult.add(new Address("test@bob.com"));
        expResult.add(new Address("test@bob2.com"));
        Message instance = new Message(expResult,null,null,null);
        instance.setFrom(expResult);
        List result = instance.getFrom();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getFrom method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testSetFrom() throws Exception {
        System.out.println("getFrom");
        
        Message instance = new Message();
        
        List expResult = new ArrayList();
        expResult.add(new Address("test@bob.com"));
        expResult.add(new Address("test@bob2.com"));
        instance.setFrom(expResult);
        List result = instance.getFrom();
        assertEquals(expResult, result);
        
    }


    /**
     * Test of addFrom method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testAddFrom() throws Exception {
        System.out.println("addFrom");
        
        Address from = new Address("man@bob.com");
        Message instance = new Message();
        
        instance.addFrom(from);
        
        List result = instance.getFrom();
        assertEquals(((Address)result.get(0)).getAddress(),"man@bob.com");
        
    }
    
    
    /**
     * Test of getRCPTs method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testGetRCPTs() throws Exception {
        System.out.println("getRCPTs");
        
        
        List expResult = new ArrayList();
        expResult.add(new Address("test@bob.com"));
        expResult.add(new Address("test@bob2.com"));
        Message instance = new Message(null,expResult,null,null);
        instance.setRCPTs(expResult);
        List result = instance.getRCPTs();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of getRCPTs method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testSetRCPTs() throws Exception {
        System.out.println("getRCPTs");
        
        Message instance = new Message();
        
        List expResult = new ArrayList();
        expResult.add(new Address("test@bob.com"));
        expResult.add(new Address("test@bob2.com"));
        instance.setRCPTs(expResult);
        List result = instance.getRCPTs();
        assertEquals(expResult, result);
        
    }


    /**
     * Test of addRCPT method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testAddRCPT() throws Exception {
        System.out.println("addRCPT");
        
        Address rcpt = new Address("man@bob.com");
        Message instance = new Message();
        
        instance.addRCPT(rcpt);
        
        List result = instance.getRCPTs();
        assertEquals(((Address)result.get(0)).getAddress(),"man@bob.com");
    }

    
    /**
     * Test of getHeaders method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testGetHeaders() throws Exception {
        System.out.println("getHeaders");
        
        
        List expResult = new ArrayList();
        expResult.add(new Header("bob","bill"));
        expResult.add(new Header("nill","none"));
        Message instance = new Message(null,null,expResult,null);
        List result = instance.getHeaders();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of getHeaders method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testHasHeader() throws Exception {
        System.out.println("hasHeader");
        
        
        List expResult = new ArrayList();
        expResult.add(new Header("bob","bill"));
        expResult.add(new Header("nill","none"));
        Message instance = new Message(null,null,expResult,null);
        
        assertEquals(true, instance.hasHeader("bob"));
        assertEquals(false, instance.hasHeader("fill"));
        
    }
    
    
    /**
     * Test of setHeaders method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testSetHeaders() {
        System.out.println("setHeaders");
        
        List expResult = new ArrayList();
        expResult.add(new Header("bob","bill"));
        expResult.add(new Header("nill","none"));
        Message instance = new Message();
        
        instance.setHeaders(expResult);
        
        List result = instance.getHeaders();
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of addHeader method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testAddHeader() {
        System.out.println("addHeader");
        
        Header header = new Header("bob","bill");
        Message instance = new Message();
        
        instance.addHeader(header);
        
        List result = instance.getHeaders();
        assertEquals(((Header)result.get(0)),header);

    }

    /**
     * Test of getData method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testGetData() {
        System.out.println("getData");
        
        String expResult = "test";
        Message instance = new Message(null,null,null,expResult);
        
        String result = instance.getData();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of setData method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testSetData() {
        System.out.println("setData");
        
        String data = "test";
        Message instance = new Message();
        
        instance.setData(data);
        
        String result = instance.getData();
        assertEquals(data, result);
    }
    
    
    /**
     * Test of getData method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testGetInfo() throws Exception {
        System.out.println("getInfo");
        
        List from = new ArrayList();
        from.add(new Address("from@from.com"));
        List rcpts = new ArrayList();
        rcpts.add(new Address("rcpt@rcpt.com"));
        List headers = new ArrayList();
        headers.add(new Header("header","value"));
        Message instance = new Message(
                "test", 1, from, rcpts, headers,"test");
        
        MessageInfo info = instance.getInfo();
        assertEquals("test", info.getId());
        assertEquals(MessageInfo.STATUS.UNPROCESSED, info.getStatus());
        assertEquals(1, info.getType());
        assertEquals(from, info.getFrom());
        assertEquals(rcpts, info.getRCPTs());
        assertEquals(headers, info.getHeaders());
    }
    
    
    /**
     * Test of setData method, of class com.rift.coad.daemon.email.smtp.Message.
     */
    public void testSetInfo() throws Exception {
        System.out.println("setInfo");
        
        List from = new ArrayList();
        from.add(new Address("from@from.com"));
        List rcpts = new ArrayList();
        rcpts.add(new Address("rcpt@rcpt.com"));
        List headers = new ArrayList();
        headers.add(new Header("header","value"));
        Message instance = new Message(
                "test", 1, from, rcpts, headers,"test");
        
        MessageInfo info = instance.getInfo();
        assertEquals("test", info.getId());
        assertEquals(MessageInfo.STATUS.UNPROCESSED, info.getStatus());
        assertEquals(1, info.getType());
        assertEquals(from, info.getFrom());
        info.addFrom(new Address("from2@from2.com"));
        assertEquals(rcpts, info.getRCPTs());
        info.addRCPT(new Address("rcpt2@rcpt2.com"));
        assertEquals(headers, info.getHeaders());
        info.addHeader(new Header("header2","value2"));
        info.setType(2);
        
        instance.updateInfo(info);
        assertEquals(instance.getType(), info.getType());
        assertEquals(instance.getFrom(), info.getFrom());
        assertEquals(instance.getRCPTs(), info.getRCPTs());
        assertEquals(instance.getHeaders(), info.getHeaders());
        
    }
}
