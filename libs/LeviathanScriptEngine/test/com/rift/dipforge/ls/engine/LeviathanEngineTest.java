/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * InvalidOperationException.java
 */
package com.rift.dipforge.ls.engine;

import com.rift.coad.lib.common.RandomGuid;
import com.rift.dipforge.ls.engine.LeviathanConstants.Status;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class LeviathanEngineTest {
    
    public LeviathanEngineTest() {
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
     * Test of buildEngine method, of class LeviathanEngine.
     */
    @Test
    public void testBuildEngine() throws Exception {
        RandomGuid.getInstance();
        System.out.println("buildEngine");
        LeviathanConfig config = LeviathanConfig.createConfig();
        config.getProperties().setProperty(LeviathanConstants.STORAGE_PATH, 
                "./tmp");
        LeviathanEngine instance = LeviathanEngine.buildEngine(config);
        LeviathanEngine expResult = LeviathanEngine.getInstance();
        assertEquals(expResult, instance);
        
        File file = new File("./testing.ls");
        byte[] buffer = new byte[(int)file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        
        Map properties = new HashMap();
        properties.put("out", System.out);
        LeviathanProcessorManager result = instance.initProcess(
                new String(buffer), properties);
        result.getProcessor().execute();
        
        List processors = instance.listProcessors();
        assertEquals(processors.size(), 1);
        
        LeviathanProcessorManager manager = instance.getProcess(
                result.getProcessor().getGUID());
        assertEquals(result, manager);
        
        instance.removeProcess(result.getProcessor().getGUID());
        
        processors = instance.listProcessors();
        assertEquals(processors.size(), 0);
        
        
        assertEquals(Status.RUNNING, instance.getStatus());
        
        instance.shutdown();
        assertEquals(Status.SHUTDOWN, instance.getStatus());
    }

}
