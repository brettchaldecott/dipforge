/*
 * DNSServer: The dns server implementation.
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
 * ServerConfigTest.java
 */

package com.rift.coad.daemon.dns.server;

import junit.framework.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Address;
import org.xbill.DNS.TSIG;

/**
 *
 * @author brett
 */
public class ServerConfigTest extends TestCase {
    
    public ServerConfigTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getPorts method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testGetPorts() throws Exception {
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("getPorts");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        List result = instance.getPorts();
        assertEquals(2, result.size());
        
        assertEquals(new Integer(12345), result.get(0));
        assertEquals(new Integer(8080), result.get(1));
        
        new File("./test.txt").delete();
    }

    /**
     * Test of getAddresses method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testGetAddresses() throws Exception {
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("getAddresses");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        List result = instance.getAddresses();
        assertEquals(2, result.size());
        
        assertEquals(Address.getByAddress("127.0.0.1"), result.get(0));
        assertEquals(Address.getByAddress("127.0.0.2"), result.get(1));
        
        new File("./test.txt").delete();

    }

    /**
     * Test of getCache method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testGetCache() throws Exception {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("getCache");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        String result = instance.getCache();
        assertEquals("/etc/namedb/cache.db", result);
        
        new File("./test.txt").delete();
    }

    /**
     * Test of getZoneNames method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testGetZoneNames() throws Exception {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("primary bob.com /etc/namedb/bob.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org.db 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("getZoneNames");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        Map result = instance.getZoneNames();
        assertEquals(3, result.size());
        
        ServerConfig.ZoneConfig config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        new File("./test.txt").delete();
        
    }

    /**
     * Test of getTSIGs method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testGetTSIGs()  throws Exception  {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org 127.0.0.1\n".toCharArray());
        writer.write("secondary bob.org /etc/namedb/bob.org 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("key bob.org 4321\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("getAddresses");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        Map result = instance.getTSIGs();
        assertEquals(2, result.size());
        
        assertEquals(true, result.containsKey(Name.fromString("xbill.org", 
                Name.root)));
        assertEquals(true, result.containsKey(Name.fromString("bob.org",
                Name.root)));
        
        new File("./test.txt").delete();
        
    }

    /**
     * Test of addPrimary method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testAddPrimary()  throws Exception {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("primary bob.com /etc/namedb/bob.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org.db 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addPrimary");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        Map result = instance.getZoneNames();
        assertEquals(3, result.size());
        
        ServerConfig.ZoneConfig config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        config = 
                instance.addPrimary("test.com","./test.db");
        assertEquals("test.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("./test.db", config.getPath());
        
        config = 
                instance.addPrimary("fred.com","./fred.db");
        assertEquals("fred.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("./fred.db", config.getPath());
        
        instance = new ServerConfig("./test.txt");;
        
        result = instance.getZoneNames();
        assertEquals(5, result.size());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("test.com");
        assertEquals("test.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("./test.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("fred.com");
        assertEquals("fred.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("./fred.db", config.getPath());
        
        new File("./test.txt").delete();
        
        
    }

    /**
     * Test of addSecondary method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testAddSecondary() throws Exception {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("primary bob.com /etc/namedb/bob.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org.db 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addSecondary");
        
        ServerConfig instance = new ServerConfig("./test.txt");;
        
        Map result = instance.getZoneNames();
        assertEquals(3, result.size());
        
        ServerConfig.ZoneConfig config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        config = 
                instance.addSecondary("test.com","./test.db","192.168.1.100");
        assertEquals("test.com", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("192.168.1.100", config.getSource());
        assertEquals("./test.db", config.getPath());
        
        config = 
                instance.addSecondary("fred.com","./fred.db","192.168.1.101");
        assertEquals("fred.com", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("192.168.1.101", config.getSource());
        assertEquals("./fred.db", config.getPath());
        
        instance = new ServerConfig("./test.txt");;
        
        result = instance.getZoneNames();
        assertEquals(5, result.size());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("test.com");
        assertEquals("test.com", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("192.168.1.100", config.getSource());
        assertEquals("./test.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("fred.com");
        assertEquals("fred.com", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("192.168.1.101", config.getSource());
        assertEquals("./fred.db", config.getPath());
        
        new File("./test.txt").delete();
    }

    /**
     * Test of removeZone method, of class com.rift.coad.daemon.dns.server.ServerConfig.
     */
    public void testRemoveZone() throws Exception {
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary internal /etc/namedb/internal.db\n".toCharArray());
	writer.write("primary bob.com /etc/namedb/bob.db\n".toCharArray());
	writer.write("secondary xbill.org /etc/namedb/xbill.org.db 127.0.0.1\n".toCharArray());
        writer.write("secondary fred.org /etc/namedb/fred.org.db 127.0.0.1\n".toCharArray());
        writer.write("cache /etc/namedb/cache.db\n".toCharArray());
        writer.write("key xbill.org 1234\n".toCharArray());
	writer.write("address 127.0.0.1\n".toCharArray());
        writer.write("address 127.0.0.2\n".toCharArray());
        writer.write("port 12345\n".toCharArray());
        writer.write("port 8080\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addSecondary");
        
        ServerConfig instance = new ServerConfig("./test.txt");
        
        Map result = instance.getZoneNames();
        assertEquals(4, result.size());
        
        ServerConfig.ZoneConfig config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("bob.com");
        assertEquals("bob.com", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/bob.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("fred.org");
        assertEquals("fred.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/fred.org.db", config.getPath());
        
        System.out.println("removeZone");
        
        instance.removeZone("bob.com");
        instance.removeZone("fred.org");
        
        result = instance.getZoneNames();
        assertEquals(2, result.size());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        assertEquals(false,result.containsKey("bob.com"));
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        assertEquals(false,result.containsKey("fred.org"));
        
        instance = new ServerConfig("./test.txt");
        
        result = instance.getZoneNames();
        assertEquals(2, result.size());
        
        config = 
                (ServerConfig.ZoneConfig)result.get("internal");
        assertEquals("internal", config.getName());
        assertEquals(true, config.getPrimary());
        assertEquals(null, config.getSource());
        assertEquals("/etc/namedb/internal.db", config.getPath());
        
        assertEquals(false,result.containsKey("bob.com"));
        
        config = 
                (ServerConfig.ZoneConfig)result.get("xbill.org");
        assertEquals("xbill.org", config.getName());
        assertEquals(false, config.getPrimary());
        assertEquals("127.0.0.1", config.getSource());
        assertEquals("/etc/namedb/xbill.org.db", config.getPath());
        
        assertEquals(false,result.containsKey("fred.org"));
        
        new File("./test.txt").delete();
    }
    
}
