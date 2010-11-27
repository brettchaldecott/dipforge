/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2007 Rift IT Contracting
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
 * RPCMessageImplTest.java
 */

package com.rift.coad.daemon.messageservice.message;

import junit.framework.*;
import java.util.Date;
import java.util.List;
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.message.rpc.RPCXMLParser;

/**
 *
 * @author mincemeat
 */
public class RPCMessageImplTest extends TestCase {
    
    public RPCMessageImplTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.daemon.messageservice.message.RPCMessageImpl.
     */
    public void testRPCMessageImpl() throws Exception {
        System.out.println("testClearBody");
        
        RPCMessageImpl message = new RPCMessageImpl("test", "test", "test", 
            null, 1);
        message.defineMethod(java.lang.Long.class,"test",new Class[] {
            java.lang.String.class,java.lang.Integer.class});
        message.setArguments(new Object[] {new String("test"),new Integer(10)});
        
        System.out.println(message.getMethodBodyXML());
        
        assertEquals(java.lang.Long.class, message.getReturnType());
        assertEquals("test", message.getMethodName());
        
        Object[] arguments = message.getArgumentTypes();
        assertEquals(2, arguments.length);
        assertEquals(java.lang.String.class, arguments[0]);
        assertEquals(java.lang.Integer.class, arguments[1]);
        
        message.clearBody();
        
        System.out.println(message.getMethodBodyXML());
    }

    
}
