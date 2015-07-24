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
 * TestDNSWebService.java
 */

// package path
package com.rift.coad.daemon.dns.webservice;

// junit imports
import junit.framework.*;

// java imports
import javax.xml.rpc.Stub;
import java.net.URL;

// test imports
import webservice.dns.daemon.coad.rift.com.*;

/**
 * Test the dns web service
 *
 * @author brett chaldecott
 */
public class TestDNSWebService extends TestCase {
    
    public TestDNSWebService(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /**
     * Test the dns web service
     */
    public void testDNSWebService() throws Exception {
        System.out.println("testDNSWebService");
        DNSServerServiceServiceLocator locator = new 
                DNSServerServiceServiceLocator();
        DNSServerService service = locator.getManagement();
        Stub stub = (Stub)service;
        stub._setProperty(Stub.USERNAME_PROPERTY,"admin");
        stub._setProperty(Stub.PASSWORD_PROPERTY,"112233");
        assertEquals("1",service.getVersion());
        assertEquals("JavaDNSServer",service.getName());
        assertEquals("Java DNS Server",service.getDescription());
        System.out.println(service.getStatus());
        
        service.createZone("test.riftit.co.za",
                "test.riftit.co.za.    604800    IN    SOA    test.riftit.co.za. root.localhost. 1 604800 86400 2419200 604800\n" +
                "test.riftit.co.za.    604800    IN    NS    ns.test.riftit.co.za.\n" +
                "test.riftit.co.za.    604800    IN    A    192.168.1.1\n" +
                "ns.test.riftit.co.za.    604800    IN    A    192.168.1.1\n");
        service.createSecondaryZone("coadunation.net"," 216.127.84.49");
        System.out.println(service.getZone("test.riftit.co.za"));
        
        service.removeZone("test.riftit.co.za");
        service.removeZone("coadunation.net");
        
        try {
            service.getZone("test.riftit.co.za");
            fail("Zone still exists");
        } catch (DNSException ex) {
            System.out.println(ex.getMessage1());
            System.out.println(ex.getCause1());
            
        }

    }

}
