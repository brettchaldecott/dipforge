/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ubuntu
 */
public class WebRoboticsParserManagerTest {
    
    public WebRoboticsParserManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createExecutor method, of class WebRoboticsParserManager.
     */
    @org.junit.Test
    public void testCreateExecutor() throws Exception {
        System.out.println("createExecutor");
        File source = new File("test_script.js");
        WebRoboticsParserExecutor expResult = null;
        WebRoboticsParserExecutor result = WebRoboticsParserManager.createExecutor(source);
        result.execute();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
