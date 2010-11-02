/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.desktop;

import com.rift.coad.daemon.desktop.DesktopParser.Desktop;
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
public class DesktopParserTest {

    public DesktopParserTest() {
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
     * Test of getDesktops method, of class DesktopParser.
     */
    @Test
    public void testGetDesktops() throws Exception {
        System.out.println("getDesktops");
        DesktopParser instance = new DesktopParser("desktops.xml");
        List<Desktop> result = instance.getDesktops();
        assertEquals(6, result.size());
        assertEquals("Desk 1",result.get(0).getName());
        assertEquals("test 1",result.get(0).getTheme());
        assertEquals("bg1",result.get(0).getBackgroundImage());
        assertEquals(false,result.get(0).isRepeat());
        assertEquals("Desk 2",result.get(1).getName());
        assertEquals("test 2",result.get(1).getTheme());
        assertEquals("bg2",result.get(1).getBackgroundImage());
        assertEquals(true,result.get(1).isRepeat());
        assertEquals("Desk 3",result.get(2).getName());
        assertEquals("test 3",result.get(2).getTheme());
        assertEquals("bg3",result.get(2).getBackgroundImage());
        assertEquals(false,result.get(2).isRepeat());
        assertEquals("Desk 4",result.get(3).getName());
        assertEquals("test 4",result.get(3).getTheme());
        assertEquals("bg4",result.get(3).getBackgroundImage());
        assertEquals(false,result.get(3).isRepeat());
        assertEquals("Desk 5",result.get(4).getName());
        assertEquals("test 5",result.get(4).getTheme());
        assertEquals("bg5",result.get(4).getBackgroundImage());
        assertEquals(true,result.get(4).isRepeat());
        assertEquals("Desk 6",result.get(5).getName());
        assertEquals("test 6",result.get(5).getTheme());
        assertEquals("bg6",result.get(5).getBackgroundImage());
        assertEquals(false,result.get(5).isRepeat());
    }

}