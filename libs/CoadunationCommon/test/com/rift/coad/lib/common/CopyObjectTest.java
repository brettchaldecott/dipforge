/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.lib.common;

import com.rift.coad.lib.common.test.SourceObject;
import com.rift.coad.lib.common.test.TargetObject;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class CopyObjectTest extends TestCase {
    
    public CopyObjectTest(String testName) {
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
     * Test of copy method, of class CopyObject.
     */
    public void testCopy_Class_Object() throws Exception {
        System.out.println("copy");
        SourceObject value = new SourceObject(1, "testkey", "testvalue", new String[] {"1","2","3"});
        value.getList().add("test1");
        value.getList().add("test2");
        TargetObject result = CopyObject.copy(TargetObject.class, value);
        assertEquals(value.getId(), result.getId());
        assertEquals(value.getName(), result.getName());
        assertEquals(value.getValue(), result.getValue());
        assertEquals(value.getArrayList()[1], result.getArrayList()[1]);
        assertEquals(value.getList().size(), result.getList().size());
        assertEquals(value.getList().get(1), result.getList().get(1));
    }

    
}
