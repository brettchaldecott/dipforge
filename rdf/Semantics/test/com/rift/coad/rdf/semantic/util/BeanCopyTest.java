/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic.util;

import com.rift.coad.rdf.semantic.util.test.SourceObject;
import com.rift.coad.rdf.semantic.util.test.TargetObject;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class BeanCopyTest {
    
    public BeanCopyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of copy method, of class BeanCopy.
     */
    @Test
    public void testCopy() throws Exception {
        System.out.println("copy");
        SourceObject value = new SourceObject(1, "testkey", "testvalue", new String[] {"1","2","3"});
        value.getList().add("test1");
        value.getList().add("test2");
        TargetObject result = BeanCopy.copy(TargetObject.class, value);
        assertEquals(value.getId(), result.getId());
        assertEquals(value.getName(), result.getName());
        assertEquals(value.getValue(), result.getValue());
        assertEquals(value.getArrayList()[1], result.getArrayList()[1]);
        assertEquals(value.getList().size(), result.getList().size());
        assertEquals(value.getList().get(1), result.getList().get(1));
    }
}
