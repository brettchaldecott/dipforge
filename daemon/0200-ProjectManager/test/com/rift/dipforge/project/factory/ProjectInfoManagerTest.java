/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.project.factory;

import java.io.File;
import com.rift.dipforge.project.ProjectInfoDTO;
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
public class ProjectInfoManagerTest {

    public ProjectInfoManagerTest() {
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
     * Test of getInfo method, of class ProjectInfoManager.
     */
    @Test
    public void testGetInfo() throws Exception {
        System.out.println("getInfo");
        ProjectInfoManager instance = new ProjectInfoManager(new File("./"));
        ProjectInfoDTO result = instance.getInfo();
        System.out.println(result.toString());
        assertEquals("ProjectInfoDTO{name=testingdescription=Test of stuffcreated=Tue Jul 12 05:35:30 SAST 2011modified=Tue Jul 12 05:35:30 SAST 2011author=testmodifiedBy=bob}", result.toString());
    }

    /**
     * Test of setInfo method, of class ProjectInfoManager.
     */
    @Test
    public void testSetInfo() throws Exception {
        System.out.println("setInfo");
        ProjectInfoManager instance = new ProjectInfoManager(new File("./"));
        ProjectInfoDTO result = instance.getInfo();
        instance.setInfo(result);
        ProjectInfoDTO info = instance.getInfo();
        assertEquals(info.toString(), result.toString());
    }

}