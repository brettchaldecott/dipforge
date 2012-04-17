/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.project.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class XMLActionInfoParserTest {
    
    public XMLActionInfoParserTest() {
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
     * Test of getActions method, of class XMLActionInfoParser.
     */
    @Test
    public void testGetActions() throws Exception {
        System.out.println("getActions");
        XMLActionInfoParser instance = new XMLActionInfoParser(readTestFile());
        List expResult = instance.getActions();
        List result = instance.getActions();
        assertEquals(expResult, result);
        assertEquals(expResult.size(), 2);
    }
    
    
    private String readTestFile() throws Exception {
        File xmlFile = new File("./action.xml");
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
