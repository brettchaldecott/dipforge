/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.project.method;

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
 * @author brett
 */
public class XMLMethodMappingParserTest {
    
    public XMLMethodMappingParserTest() {
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
     * Test of getJNDIList method, of class XMLMethodMappingParser.
     */
    @Test
    public void testGetJNDIList() throws Exception {
        System.out.println("getJNDIList");
        XMLMethodMappingParser instance = 
                new XMLMethodMappingParser(readTestFile());
        //List expResult = null;
        List result = instance.getJNDIList();
        assertEquals(2, result.size());
    }

    /**
     * Test of getMethodMapping method, of class XMLMethodMappingParser.
     */
    @Test
    public void testGetMethodMapping() throws Exception {
        System.out.println("getMethodMapping");
        String jndi = "";
        XMLMethodMappingParser instance = 
                new XMLMethodMappingParser(readTestFile());
        List result = instance.getMethodMapping("test/path");
        assertEquals(4, result.size());
    }
    
    
    /**
     * This method returns the test file.
     *
     * @return The string containing the test file.
     * @throws Exception
     */
    private String readTestFile() throws Exception {
        File xmlFile = new File("./TestMethodMapping.xml");
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
