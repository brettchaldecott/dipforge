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
 * RouteManagerTest.java
 */

package com.rift.coad.daemon.email.server.smtp.route;

import junit.framework.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.MessageInfo;

/**
 *
 * @author brett
 */
public class RouteManagerTest extends TestCase {
    
    public RouteManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getBase method, of class com.rift.coad.daemon.email.server.smtp.route.RouteManager.
     */
    public void testGetBase() throws Exception {
        System.out.println("getBase");
        
        RouteManager instance = new RouteManager("./route.xml");
        
        RouteEntry result = instance.getBase();
        assertEquals("testlocal", result.getName());
        assertEquals("/test/local", result.getJNDI());
        
        List headers = new ArrayList();
        headers.add(new Header("spam","spam"));
        MessageInfo messageInfo = new MessageInfo("test", 1, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        RouteEntry local = result.getEntry(messageInfo);
        
        assertEquals("testspam", local.getName());
        assertEquals("/test/spam", local.getJNDI());
        
        local = local.getEntry(messageInfo);
        
        assertEquals("spamdelivery", local.getName());
        assertEquals("/test/spamdelivery", local.getJNDI());
        
        headers = new ArrayList();
        headers.add(new Header("test","bob"));
        messageInfo = new MessageInfo("test", 2, 
                MessageInfo.STATUS.UNPROCESSED, null,null, headers);
        
        local = result.getEntry(messageInfo);
        
        assertEquals("deliverremote", local.getName());
        assertEquals("/test/deliverremote", local.getJNDI());
    }
    
    
    /**
     * Test of getEntry method, of class com.rift.coad.daemon.email.server.smtp.route.RouteManager.
     */
    public void testGetEntry() throws Exception {
        System.out.println("getEntry");
        
        RouteManager instance = new RouteManager("./route.xml");
        
        RouteEntry result = instance.getEntry("testlocal");
        assertEquals("/test/local", result.getJNDI());
        
        result = instance.getEntry("testspam");
        assertEquals("/test/spam", result.getJNDI());
        
        result = instance.getEntry("spamdelivery");
        assertEquals("/test/spamdelivery", result.getJNDI());
        
        result = instance.getEntry("deliverlocal");
        assertEquals("/test/deliverlocal", result.getJNDI());
        
        result = instance.getEntry("deliverremote");
        assertEquals("/test/deliverremote", result.getJNDI());
    }
    
}
