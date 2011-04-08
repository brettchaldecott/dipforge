/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.schema.util;

import java.io.File;
import java.util.List;
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
public class XMLListParserTest {

    public XMLListParserTest() {
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
     * Test of getURLs method, of class XMLListParser.
     */
    @Test
    public void testGetURLs() throws Exception {
        System.out.println("getURLs");
        File path = new File("./");
        XMLListGenerator listGenerator = new XMLListGenerator("http://dipforge.sourceforge.net/",
                path.getPath(), DirectoryUtil.recurseDirectory(path, ".java"),".java");

        String listXML = listGenerator.generateXMLList();

        XMLListParser instance = new XMLListParser(listXML);
        List expResult = instance.getURLs();
        List result = instance.getURLs();
        System.out.println(result);
        assertEquals(expResult, result);

    }

}