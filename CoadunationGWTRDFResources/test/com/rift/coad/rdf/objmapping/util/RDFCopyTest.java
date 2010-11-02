/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  Rift IT Contracting
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
 * RDFCopyTest.java
 */

package com.rift.coad.rdf.objmapping.util;

import junit.framework.TestCase;


/**
 *
 * @author brett chaldecott
 */
public class RDFCopyTest extends TestCase {
    
    public RDFCopyTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of copyToClient method, of class RDFCopy.
     */
    public void testCopyToClient() throws Exception {
        System.out.println("copyToClient");
        com.rift.coad.rdf.objmapping.organisation.Organisation value = new
                com.rift.coad.rdf.objmapping.organisation.Organisation(new
                com.rift.coad.rdf.objmapping.base.DataType[] {
                        new com.rift.coad.rdf.objmapping.base.Phone("011 300 5000"),
                        new com.rift.coad.rdf.objmapping.base.phone.CellPhone("082 300 5000"),
                        new com.rift.coad.rdf.objmapping.base.ip.IPv4("192.168.1.1"),
                        new com.rift.coad.rdf.objmapping.base.name.Surname("surname1")}, "1",
                        "test1");
        com.rift.coad.rdf.objmapping.client.organisation.Organisation result =
                (com.rift.coad.rdf.objmapping.client.organisation.Organisation)RDFCopy.copyToClient(
                value);

    }

    /**
     * Test of copyFromClient method, of class RDFCopy.
     */
    public void testCopyFromClient() throws Exception {
        System.out.println("copyFromClient");
        com.rift.coad.rdf.objmapping.organisation.Organisation value = new
                com.rift.coad.rdf.objmapping.organisation.Organisation(new
                com.rift.coad.rdf.objmapping.base.DataType[] {
                        new com.rift.coad.rdf.objmapping.base.Phone("011 300 5000"),
                        new com.rift.coad.rdf.objmapping.base.phone.CellPhone("082 300 5000"),
                        new com.rift.coad.rdf.objmapping.base.ip.IPv4("192.168.1.1"),
                        new com.rift.coad.rdf.objmapping.base.name.Surname("surname1")}, "1",
                        "test1");
        com.rift.coad.rdf.objmapping.client.organisation.Organisation result =
                (com.rift.coad.rdf.objmapping.client.organisation.Organisation)RDFCopy.copyToClient(value);
        com.rift.coad.rdf.objmapping.organisation.Organisation value2 = 
                (com.rift.coad.rdf.objmapping.organisation.Organisation)RDFCopy.copyFromClient(result);
        assertEquals(value.getId(), value2.getId());
        assertEquals(value.getName(), value2.getName());
        assertEquals(value.getId(), value2.getId());
        assertEquals(value.getName(), value2.getName());
        assertEquals(value2.getAttribute(com.rift.coad.rdf.objmapping.base.Phone.class, "Phone").getValue(),"011 300 5000");
        assertEquals(value2.getAttribute(com.rift.coad.rdf.objmapping.base.phone.CellPhone.class, "CellPhone").getValue(),"082 300 5000");
        assertEquals(value2.getAttributes(com.rift.coad.rdf.objmapping.base.phone.CellPhone.class,com.rift.coad.rdf.objmapping.base.phone.CellPhone.class.getName()).size(),1);

    }

}
