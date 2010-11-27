/*
 * DNSServer: The dns server implementation.
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
 * ServerZoneMapTest.java
 */

package com.rift.coad.daemon.dns.server;

import java.io.FileWriter;
import java.io.File;
import java.util.Iterator;
import junit.framework.*;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.xbill.DNS.Name;
import org.xbill.DNS.Zone;

/**
 *
 * @author brett
 */
public class ServerZoneMapTest extends TestCase {
    
    public ServerZoneMapTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of addZone method, of class com.rift.coad.daemon.dns.server.ServerZoneMap.
     */
    public void testAddZone() throws Exception {
        System.out.println("addZone");
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary zone1.com /etc/namedb/zone1.db\n".toCharArray());
	writer.write("primary zone2.com /etc/namedb/zone2.db\n".toCharArray());
	writer.write("primary zone3.com /etc/namedb/zone3.db\n".toCharArray());
	writer.write("primary zone4.com /etc/namedb/zone4.db\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addPrimary");
        
        ServerConfig serverConfig = new ServerConfig("./test.txt");;
        
        Map result = serverConfig.getZoneNames();
        
        ServerZoneMap instance = new ServerZoneMap();
        
        for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
            ServerZone zone = new ServerZone((ServerConfig.ZoneConfig)
            result.get(iter.next()));
            instance.addZone(zone);
            assertEquals(instance.findBestZone(zone.getZoneName()),zone);
        }
        
        new File("./test.txt").delete();
        
    }

    /**
     * Test of removeZone method, of class com.rift.coad.daemon.dns.server.ServerZoneMap.
     */
    public void testRemoveZone() throws Exception {
        System.out.println("removeZone");
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary zone1.com /etc/namedb/zone1.db\n".toCharArray());
	writer.write("primary zone2.com /etc/namedb/zone2.db\n".toCharArray());
	writer.write("primary zone3.com /etc/namedb/zone3.db\n".toCharArray());
	writer.write("primary zone4.com /etc/namedb/zone4.db\n".toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addPrimary");
        
        ServerConfig serverConfig = new ServerConfig("./test.txt");;
        
        Map result = serverConfig.getZoneNames();
        
        ServerZoneMap instance = new ServerZoneMap();
        
        for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
            ServerZone zone = new ServerZone((ServerConfig.ZoneConfig)
            result.get(iter.next()));
            instance.addZone(zone);
            assertEquals(instance.findBestZone(zone.getZoneName()),zone);
        }
        
        for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
            ServerConfig.ZoneConfig config = (ServerConfig.ZoneConfig)
                result.get(iter.next());
            instance.removeZone(config.getName());
            assertEquals(instance.findBestZone(config.getName()),null);
        }
        
        
        new File("./test.txt").delete();
    }

    /**
     * Test of findBestZone method, of class com.rift.coad.daemon.dns.server.ServerZoneMap.
     */
    public void testFindBestZone() throws Exception {
        System.out.println("findBestZone");
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary zone1.com /etc/namedb/zone1.db\n".toCharArray());
	writer.write("primary sub1.zone1.com /etc/namedb/sub1.zone1.db\n".
                toCharArray());
	writer.write("primary sub2.zone1.com /etc/namedb/sub2.zone1.db\n".
                toCharArray());
	writer.write("primary sub3.sub1.zone1.com /etc/namedb/" +
                "sub3.sub1.zone1.db\n".
                toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addPrimary");
        
        ServerConfig serverConfig = new ServerConfig("./test.txt");;
        
        Map result = serverConfig.getZoneNames();
        
        ServerZoneMap instance = new ServerZoneMap();
        
        ServerZone masterZone = null;
        for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
            ServerZone zone = new ServerZone((ServerConfig.ZoneConfig)
            result.get(iter.next()));
            if (zone.getZoneName().equals("zone1.com")) {
                masterZone = zone;
            }
            instance.addZone(zone);
            assertEquals(instance.findBestZone(zone.getZoneName()),zone);
        }
        
        assertEquals(instance.findBestZone("bob.zone1.com"),masterZone);
        assertEquals(instance.findBestZone("zone3.com"),null);
        new File("./test.txt").delete();
    }
    
    
    /**
     * Test of findBestZone method, of class com.rift.coad.daemon.dns.server.ServerZoneMap.
     */
    public void testGetZone() throws Exception {
        System.out.println("findGetZone");
        
        FileWriter writer = new FileWriter("./test.txt");
        writer.write("primary zone1.com /etc/namedb/zone1.db\n".toCharArray());
	writer.write("primary sub1.zone1.com /etc/namedb/sub1.zone1.db\n".
                toCharArray());
	writer.write("primary sub2.zone1.com /etc/namedb/sub2.zone1.db\n".
                toCharArray());
	writer.write("primary sub3.sub1.zone1.com /etc/namedb/" +
                "sub3.sub1.zone1.db\n".
                toCharArray());
        writer.flush();
        writer.close();
        
        System.out.println("addPrimary");
        
        ServerConfig serverConfig = new ServerConfig("./test.txt");;
        
        Map result = serverConfig.getZoneNames();
        
        ServerZoneMap instance = new ServerZoneMap();
        
        ServerZone masterZone = null;
        for (Iterator iter = result.keySet().iterator(); iter.hasNext();) {
            ServerZone zone = new ServerZone((ServerConfig.ZoneConfig)
            result.get(iter.next()));
            if (zone.getZoneName().equals("zone1.com")) {
                masterZone = zone;
            }
            instance.addZone(zone);
            assertEquals(instance.getZone(zone.getZoneName()),zone);
        }
        
        assertEquals(instance.getZone("bob.zone1.com"),null);
        assertEquals(instance.getZone("zone1.com"),masterZone);
        assertEquals(instance.getZone("zone3.com"),null);
        new File("./test.txt").delete();
    }
}
