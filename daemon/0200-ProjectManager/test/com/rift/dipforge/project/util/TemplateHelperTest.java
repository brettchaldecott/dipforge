/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.project.util;

import java.util.HashMap;
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
public class TemplateHelperTest {
    
    public TemplateHelperTest() {
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
     * Test of parse method, of class TemplateHelper.
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");
        TemplateHelper instance = new TemplateHelper("./testfile.tmp");
        String expResult = "# this is a test file\n" +
            "\n" +
            "value1=bob\n" +
            "value2=tom\n" +
            "value2.2=tom\n" +
            "value3=jill";
        Map<String,String> values = new HashMap<String,String>();
        values.put("value1", "bob");
        values.put("value2", "tom");
        values.put("value3", "jill");
        instance.setParameters(values);
        String result = instance.parse();
        assertEquals(expResult, result);
    }
}
