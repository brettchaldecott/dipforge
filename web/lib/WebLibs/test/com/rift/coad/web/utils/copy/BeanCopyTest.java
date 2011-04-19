/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.web.utils.copy;

import com.rift.coad.web.utils.copy.test.SourceObject;
import com.rift.coad.web.utils.copy.test.TargetObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brettc
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
        TargetObject result = BeanCopy.copy(TargetObject.class, value);
        assertEquals(value.getId(), result.getId());
        assertEquals(value.getName(), result.getName());
        assertEquals(value.getValue(), result.getValue());
        assertEquals(value.getArrayList()[1], result.getArrayList()[1]);
    }

    
}