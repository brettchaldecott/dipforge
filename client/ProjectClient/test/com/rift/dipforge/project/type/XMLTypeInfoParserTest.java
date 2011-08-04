/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.project.type;

import java.io.FileInputStream;
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
public class XMLTypeInfoParserTest {

    public XMLTypeInfoParserTest() {
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
     * Test of getNamespace method, of class XMLTypeInfoParser.
     */
    @Test
    public void testGetNamespace() throws Exception {
        System.out.println("getNamespace");
        XMLTypeInfoParser instance = new XMLTypeInfoParser(readTestFile());
        String expResult = "http://dipforge.sourceforge.net/test";
        String result = instance.getNamespace();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProject method, of class XMLTypeInfoParser.
     */
    @Test
    public void testGetProject() throws Exception {
        System.out.println("getProject");
        XMLTypeInfoParser instance = new XMLTypeInfoParser(readTestFile());
        String expResult = "test";
        String result = instance.getProject();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTypes method, of class XMLTypeInfoParser.
     */
    @Test
    public void testGetTypes() throws Exception {
        System.out.println("getTypes");
        XMLTypeInfoParser instance = new XMLTypeInfoParser(readTestFile());
        List result = instance.getTypes();
        assertEquals(3, result.size());
    }


    /**
     * This method returns the test file.
     *
     * @return The string containing the test file.
     * @throws Exception
     */
    private String readTestFile() throws Exception {
        File xmlFile = new File("./type.xml");
        FileInputStream in = new FileInputStream(xmlFile);
        StringBuffer xml = new StringBuffer();
        byte[] buffer = new byte[1024];
        int size = 0;
        while((size = in.read(buffer)) != -1) {
            xml.append(new String(buffer,0,size));
            buffer = new byte[1024];
        }
        return xml.toString();
    }
}