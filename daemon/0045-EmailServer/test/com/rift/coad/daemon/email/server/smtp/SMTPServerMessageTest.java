/*
 * Email Server: The email server.
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
 * SMTPServerMessageTest.java
 */

package com.rift.coad.daemon.email.server.smtp;

import junit.framework.*;
import java.util.Date;
import com.rift.coad.daemon.email.smtp.Message;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.SMTPException;

/**
 * This object tests the smtp server.
 *
 * @author brett chaldecott
 */
public class SMTPServerMessageTest extends TestCase {
    
    public SMTPServerMessageTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getId method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testGetId() {
        System.out.println("getId");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        String expResult = "test";
        String result = instance.getId();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of getMessage method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testGetMessage() {
        System.out.println("getMessage");
        
        
        Message message = new Message("test", 
                1, null, null, null, "data");
        SMTPServerMessage instance = new SMTPServerMessage(message);
        
        Message expResult = message;
        Message result = instance.getMessage();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of getMessageServiceId method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testGetMessageServiceId() {
        System.out.println("getMessageServiceId");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        String expResult = "test";
        instance.setMessageServiceId(expResult);
        String result = instance.getMessageServiceId();
        assertEquals(expResult, result);
        
    }

    
    /**
     * Test of getRetryDate method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testGetRetryDate() {
        System.out.println("getRetryDate");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        Date result = instance.getRetryDate();
        assertEquals(null, result);
        
        String expResult = "test";
        instance.setMessageServiceId(expResult);
        
        result = instance.getRetryDate();
        if (result == null) {
            fail("Null value for retry date.");
        }
    }

    /**
     * Test of incrementRetries method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testIncrementRetries() {
        System.out.println("incrementRetries");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        int result1 = instance.incrementRetries();
        int result2 = instance.incrementRetries();
        assertEquals(1, result1);
        assertEquals(2, result2);
        
    }

    /**
     * Test of resetRetries method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testResetRetries() {
        System.out.println("resetRetries");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        int result1 = instance.incrementRetries();
        int result2 = instance.incrementRetries();
        
        assertEquals(1, result1);
        assertEquals(2, result2);
        
        instance.resetRetries();
        
        assertEquals(0, instance.getRetries());
        
    }

    /**
     * Test of getRetries method, of class com.rift.coad.daemon.email.server.smtp.SMTPServerMessage.
     */
    public void testGetRetries() {
        System.out.println("getRetries");
        
        SMTPServerMessage instance = new SMTPServerMessage(new Message("test", 
                1, null, null, null, "data"));
        
        int result1 = instance.incrementRetries();
        assertEquals(instance.getRetries(), result1);
        int result2 = instance.incrementRetries();
        assertEquals(instance.getRetries(), result2);
    }
    
}
