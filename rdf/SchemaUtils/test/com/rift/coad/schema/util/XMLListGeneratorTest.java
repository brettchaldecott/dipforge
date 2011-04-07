/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.schema.util;

import java.io.File;
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
public class XMLListGeneratorTest {

    public XMLListGeneratorTest() {
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
     * Test of generateXMLList method, of class XMLListGenerator.
     */
    @Test
    public void testGenerateXMLList() throws Exception {
        System.out.println("generateXMLList");
        File path = new File("./");
        XMLListGenerator instance = new XMLListGenerator("http://dipforge.sourceforge.net/", 
                path.getPath(), DirectoryUtil.recurseDirectory(path, "java"));

        String expResult = instance.generateXMLList();
        String result = instance.generateXMLList();
        System.out.println(result);
        assertEquals(expResult, result);

        instance = new XMLListGenerator("http://dipforge.sourceforge.net/",
                path.getPath(), DirectoryUtil.recurseDirectory(path, ".java"),".java");

        expResult = instance.generateXMLList();
        result = instance.generateXMLList();
        System.out.println(result);
        assertEquals(expResult, result);

    }

}