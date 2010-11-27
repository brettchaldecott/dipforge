/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.desktop;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class MimeParserTest {

    public MimeParserTest() {
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
     * Test of getTypes method, of class MimeParser.
     */
    @Test
    public void testGetTypes() throws Exception {
        System.out.println("getTypes");
        MimeParser instance = new MimeParser("mime.xml");
        Map<String, MimeType> expResult = instance.getTypes();
        Map<String, MimeType> result = instance.getTypes();
        assertEquals(expResult, result);
        assertEquals(3,result.size());
    }

}