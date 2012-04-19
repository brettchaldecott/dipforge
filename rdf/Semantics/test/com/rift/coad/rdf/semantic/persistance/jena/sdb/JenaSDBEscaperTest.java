/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic.persistance.jena.sdb;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class JenaSDBEscaperTest {
    
    public JenaSDBEscaperTest() {
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
     * Test of escape method, of class JenaSDBEscaper.
     */
    @Test
    public void testEscape() {
        System.out.println("escape");
        String value = "";
        JenaSDBEscaper instance = new JenaSDBEscaper();
        String expResult = "bob '' test";
        String result = instance.escape("bob ' test");
        assertEquals(expResult, result);
    }
}
