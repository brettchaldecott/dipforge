/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * ConcurrentBlockTest.java
 */

package com.rift.coad.change.rdf.objmapping.change.task;

import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.number.RDFLong;
import junit.framework.TestCase;

/**
 * This object tests the concurrent block
 *
 * @author brett chaldecott
 */
public class ConcurrentBlockTest extends TestCase {
    
    public ConcurrentBlockTest(String testName) {
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
     * Test of setParameters method, of class ConcurrentBlock.
     */
    public void testSetParameters() {
        System.out.println("setParameters");
        RDFLong value1 = new RDFLong((long)20);
        RDFLong value2 = new RDFLong((long)20);
        RDFLong value3 = new RDFLong((long)20);

        DataType[] parameters = new DataType[] {value1,value2,value3};
        ConcurrentBlock instance = new ConcurrentBlock();
        instance.setParameters(parameters);
        assertEquals(value1.getAssociatedObject(), instance.getIdForDataType() + "/" + instance.getObjId() + "/" + instance.getName());
        assertEquals(value2.getAssociatedObject(), instance.getIdForDataType() + "/" + instance.getObjId() + "/" + instance.getName());
        assertEquals(value3.getAssociatedObject(), instance.getIdForDataType() + "/" + instance.getObjId() + "/" + instance.getName());
    }

    /**
     * Test of addParameter method, of class ConcurrentBlock.
     */
    public void testAddParameter() {
        System.out.println("addParameter");
        RDFLong value1 = new RDFLong((long)20);
        ConcurrentBlock instance = new ConcurrentBlock();
        instance.addParameter(value1);
        assertEquals(value1.getAssociatedObject(), instance.getIdForDataType() + "/" + instance.getObjId() + "/" + instance.getName());
    }

}
