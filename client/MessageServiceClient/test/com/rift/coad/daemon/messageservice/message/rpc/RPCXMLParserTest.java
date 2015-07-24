/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2007 2015 Burntjam
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
 * RPCXMLParserTest.java
 */

// package path
package com.rift.coad.daemon.messageservice.message.rpc;

// java imports
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;


// junit imports
import junit.framework.*;

// coadunation imports
import com.rift.coad.daemon.messageservice.message.RPCMessageImpl;

/**
 * This object is responsible for testing the RPC xml parser.
 *
 * @author Brett Chaldecott
 */
public class RPCXMLParserTest extends TestCase {
    
    public RPCXMLParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.daemon.messageservice.message.rpc.RPCXMLParser.
     */
    public void testRPCXMLParser() throws Exception {
        System.out.println("testRPCXMLParser");
        
        // message
        RPCMessageImpl message = new RPCMessageImpl("test", "test", "test", 
            null, 1);
        message.defineMethod(java.lang.Long.class,"test",new Class[] {
            java.lang.String.class,java.lang.Integer.class,int[].class,
            int.class});
        
        System.out.println(message.getMethodBodyXML());
        
        com.rift.coad.daemon.messageservice.message.rpc.RPCXMLParser instance = 
                new com.rift.coad.daemon.messageservice.message.rpc.RPCXMLParser
                (message.getMethodBodyXML());
        
        assertEquals(message.getMethodBodyXML(), instance.getRPCXML());
        
        assertEquals(java.lang.Long.class, instance.getReturnType());
        assertEquals("test", instance.getMethodName());
        
        Object[] arguments = instance.getArgumentTypes();
        assertEquals(4, arguments.length);
        assertEquals(java.lang.String.class, arguments[0]);
        assertEquals(java.lang.Integer.class, arguments[1]);
        assertEquals(int[].class, arguments[2]);
        assertEquals(int.class, arguments[3]);
    }
    
    
}
