/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.groovy.lib;

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
public class ContextUtilsTest {

    public ContextUtilsTest() {
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
     * Test of stripUri method, of class ContextUtils.
     */
    @Test
    public void testStripUri() {
        System.out.println("stripUri");
        String uri = "/bob/stuff";
        String expResult = "bob";
        String result = ContextUtils.stripUri(uri);
        assertEquals(expResult, result);
        
    }

}