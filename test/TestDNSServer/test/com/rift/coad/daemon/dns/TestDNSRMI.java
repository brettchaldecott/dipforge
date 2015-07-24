/*
 * DNSServer: The coadunation dns server implementation
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
 * TestDNSRMI.java
 */

// package path
package com.rift.coad.daemon.dns;

// the java imports
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;


// unit test imports
import junit.framework.*;


/**
 * This class tests the web service.
 *
 * @author brett chaldecott
 */
public class TestDNSRMI extends TestCase {
    
    public TestDNSRMI(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    /**
     * This method is used to test the rmi client.
     */
    public void testRMIClient() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
        env.put("com.rift.coad.username","admin");
        env.put("com.rift.coad.password","112233");
        Context ctx = new InitialContext(env);
        
        Object obj = ctx.lookup(System.getProperty("dns.jndi"));
        System.out.println(obj.getClass().getName());
        DNSManagement dnsManagement = (DNSManagement)PortableRemoteObject.
                narrow(obj,DNSManagement.class);
        ZoneManagement zone = 
                dnsManagement.createSecondaryZone("iburst.co.za","196.30.31.200");
        assertEquals(zone.isPrimary(),false);
        assertEquals(zone.getName(),"iburst.co.za");
        
        try {
            zone.updateRecords(new ArrayList());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        dnsManagement.removeZone("iburst.co.za");
        
        try {
            zone = dnsManagement.getZone("iburst.co.za");
            fail("Failed because zone exists on the backend");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        // create a primary zone
        SOARecord soa = new SOARecord("localhost.", 8000, "bob.localhost.", 
                1,60, 30, 8000, 500);
        List records = new ArrayList();
        records.add(new DNSRecord("bob.com.", 8000, "NS", "ns1.bob.com."));
        records.add(new DNSRecord("bob.com.", 8000, "A", "10.0.0.1"));
        records.add(new DNSRecord("ns1.bob.com.", 8000, "A", "10.0.0.2"));
        
        zone = dnsManagement.createZone("bob.com",soa,records);
        
        assertEquals(zone.isPrimary(),true);
        assertEquals(zone.getName(),"bob.com");
        
        SOARecord soa2 = zone.getSOA();
        
        assertEquals(soa.getAdmin(),soa2.getAdmin());
        assertEquals(soa.getExpiry(),soa2.getExpiry());
        assertEquals(soa.getHost(),soa2.getHost());
        assertEquals(soa.getMinimum(),soa2.getMinimum());
        assertEquals(soa.getRefresh(),soa2.getRefresh());
        assertEquals(soa.getRetry(),soa2.getRetry());
        assertEquals(soa.getSerial(),soa2.getSerial());
        assertEquals(soa.getTTL(),soa2.getTTL());
        
        soa = new SOARecord("10.0.0.1.", 6000, "bob.bob.com.", 
                3,80, 50, 6000, 600);
        zone.updateSOA(soa);
        
        soa2 = zone.getSOA();
        
        assertEquals(soa.getAdmin(),soa2.getAdmin());
        assertEquals(soa.getExpiry(),soa2.getExpiry());
        assertEquals(soa.getHost(),soa2.getHost());
        assertEquals(soa.getMinimum(),soa2.getMinimum());
        assertEquals(soa.getRefresh(),soa2.getRefresh());
        assertEquals(soa.getRetry(),soa2.getRetry());
        assertEquals(soa.getSerial(),soa2.getSerial());
        assertEquals(soa.getTTL(),soa2.getTTL());
        
        List zoneRecords = zone.getRecords();
        
        assertEquals(zoneRecords.size(),records.size());
        for (int index = 0; index < records.size(); index++) {
            DNSRecord record1 = (DNSRecord)records.get(index);
            DNSRecord record2 = (DNSRecord)zoneRecords.get(index);
            
            assertEquals(record1.getPrefix(),record2.getPrefix());
            assertEquals(record1.getSuffix(),record2.getSuffix());
            assertEquals(record1.getTtl(),record2.getTtl());
            assertEquals(record1.getType(),record2.getType());
        }
        
        
        records = new ArrayList();
        records.add(new DNSRecord("bob.com.", 8000, "NS", "ns2.bob.com."));
        records.add(new DNSRecord("bob.com.", 8000, "NS", "ns3.bob.com."));
        records.add(new DNSRecord("bob.com.", 8000, "A", "10.0.0.2"));
        records.add(new DNSRecord("ns2.bob.com.", 8000, "A", "10.0.0.3"));
        records.add(new DNSRecord("ns3.bob.com.", 8000, "A", "10.0.0.4"));
        
        zone.updateRecords(records);
        
        zoneRecords = zone.getRecords();
        
        assertEquals(zoneRecords.size(),records.size());
        for (int index = 0; index < zoneRecords.size(); index++) {
            DNSRecord record2 = (DNSRecord)zoneRecords.get(index);
            System.out.println("[" + record2.getPrefix() + "][" + 
                    record2.getTtl() + "][" + record2.getType() + "][" +
                    record2.getSuffix() + "]");
            //assertEquals(record1.getPrefix(),record2.getPrefix());
            //assertEquals(record1.getSuffix(),record2.getSuffix());
            //assertEquals(record1.getTtl(),record2.getTtl());
            //assertEquals(record1.getType(),record2.getType());
        }
        
        dnsManagement.removeZone("bob.com");
    }
    
}
