/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.project.timer;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class XMLTimerInfoParserTest {
    
    public XMLTimerInfoParserTest() {
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
     * Test of getActions method, of class XMLTimerInfoParser.
     */
    @Test
    public void testGetActions() throws Exception {
        System.out.println("getActions");
        XMLTimerInfoParser instance = new XMLTimerInfoParser(readTestFile());
        List<XMLTimerInfoParser.Action> expResult = instance.getActions();
        List<XMLTimerInfoParser.Action> result = instance.getActions();
        assertEquals("test", instance.getProject());
        assertEquals(expResult.size(),2);
        assertEquals(expResult, result);
        assertEquals("groovy/Timer", result.get(0).getJndi());
        assertEquals("test2.groovy", result.get(1).getScript());
    }

    
    /**
     * This method returns the test file.
     *
     * @return The string containing the test file.
     * @throws Exception
     */
    private String readTestFile() throws Exception {
        File xmlFile = new File("./timer.xml");
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
