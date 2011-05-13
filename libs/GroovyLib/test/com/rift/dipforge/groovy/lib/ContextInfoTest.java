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
public class ContextInfoTest {

    public ContextInfoTest() {
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
     * Test of getPath method, of class ContextInfo.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        ContextInfo instance = new ContextInfo("/bob/test");
        String expResult = "bob";
        String result = instance.getPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of stripContext method, of class ContextInfo.
     */
    @Test
    public void testStripContext() {
        System.out.println("stripContext");
        String uri = "/bob/test";
        ContextInfo instance = new ContextInfo("/bob/test");;
        String expResult = "test";
        String result = instance.stripContext(uri);
        assertEquals(expResult, result);
    }

}