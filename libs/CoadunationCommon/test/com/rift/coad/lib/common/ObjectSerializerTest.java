/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * ObjectSerializerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author mincemeat
 */
public class ObjectSerializerTest extends TestCase {
    
    public static class TestObject implements java.io.Serializable {
        public String key = null;
        public String value = null;
        
        public TestObject() {
            
        }
        
        public TestObject(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    
    public ObjectSerializerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ObjectSerializerTest.class);
        
        return suite;
    }

    /**
     * Test of serialize method, of class com.rift.coad.lib.common.ObjectSerializer.
     */
    public void testSerializer() throws Exception {
        System.out.println("serializer");
        
        TestObject startObject = new TestObject("fred","freds value is here");
        byte[] result = ObjectSerializer.serialize(startObject);
        TestObject resultObject = 
                (TestObject)ObjectSerializer.deserialize(result);
        
        if (!startObject.key.equals(resultObject.key) && 
                startObject.value.equals(resultObject.value)) {
            fail ("The serialization and deserialization test failed");
        }
    }

    
    
}
