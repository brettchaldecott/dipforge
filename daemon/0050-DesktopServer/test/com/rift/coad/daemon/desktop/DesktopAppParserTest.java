/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.desktop;

import com.rift.coad.daemon.desktop.DesktopAppParser.Desktop;
import java.util.Map;
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
public class DesktopAppParserTest {

    public DesktopAppParserTest() {
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
     * Test of getDesktops method, of class DesktopAppParser.
     */
    @Test
    public void testGetDesktops() throws Exception {
        System.out.println("getDesktops");
        DesktopAppParser instance = new DesktopAppParser("desktopapp.xml");
        Map<String, DesktopAppParser.Desktop> result = instance.getDesktops();
        assertEquals(4, result.size());
        DesktopAppParser.Desktop desktop = result.get("test2");
        assertEquals(4, desktop.getApps().size());
        assertEquals("app1", desktop.getApps().get(0));
        assertEquals("app2", desktop.getApps().get(1));
        assertEquals("app3", desktop.getApps().get(2));
        assertEquals("app4", desktop.getApps().get(3));
    }

}