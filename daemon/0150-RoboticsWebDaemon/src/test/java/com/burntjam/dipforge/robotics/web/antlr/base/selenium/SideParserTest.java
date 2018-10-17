/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.robotics.web.antlr.base.selenium;

import com.burntjam.dipforge.robotics.web.antlr.WebRoboticsParserExecutor;
import com.burntjam.dipforge.robotics.web.antlr.WebRoboticsParserManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
public class SideParserTest {
    
    public SideParserTest() {
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
     * Test of generateSource method, of class SideParser.
     */
    @Test
    public void testGenerateSource() throws IOException, Exception {
        System.out.println("generateSource");
        File source = new File("test_side.side");
        String exampleSource = new String(Files.readAllBytes(source.toPath()));
        System.out.println(exampleSource);
        SideParser instance = new SideParser(exampleSource);
        String expResult = "";
        String result = instance.generateSource();
        System.out.println(result);
        WebRoboticsParserExecutor executor = WebRoboticsParserManager.createExecutor(result);
        executor.execute();
        
        System.out.println(result);
    }
        
    @Test
    public void testGenerateMeteringOnline() throws IOException, Exception {
        System.out.println("testGenerateMeteringOnline");
        File source = new File("MeteringOnline.side");
        String exampleSource = new String(Files.readAllBytes(source.toPath()));
        System.out.println(exampleSource);
        SideParser instance = new SideParser(exampleSource);
        String expResult = "";
        String result = instance.generateSource();
        System.out.println(result);
        WebRoboticsParserExecutor executor = WebRoboticsParserManager.createExecutor(result);
        executor.execute();
        
        System.out.println(result);
    }
    
    @Test
    public void testGenerateFronius() throws IOException, Exception {
        System.out.println("testGenerateFronius");
        File source = new File("Fronius.side");
        String exampleSource = new String(Files.readAllBytes(source.toPath()));
        System.out.println(exampleSource);
        SideParser instance = new SideParser(exampleSource);
        String expResult = "";
        String result = instance.generateSource();
        System.out.println(result);
        WebRoboticsParserExecutor executor = WebRoboticsParserManager.createExecutor(result);
        executor.execute();
        
        System.out.println(result);
    }
    
}
