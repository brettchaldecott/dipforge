/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.schema.util;

import com.rift.coad.schema.util.DirectoryUtil;
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
public class DirectoryUtilTest {

    public DirectoryUtilTest() {
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
     * Test of recurseDirectory method, of class DirectoryUtil.
     */
    @Test
    public void testRecurseDirectory() throws Exception {
        System.out.println("recurseDirectory");
        File path = new File("./");
        String suffix = "java";
        DirectoryUtil instance = new DirectoryUtil();
        List expResult = DirectoryUtil.recurseDirectory(path, suffix);
        List result = DirectoryUtil.recurseDirectory(path, suffix);
        System.out.println(result);
        assertEquals(expResult, result);
        
    }

}