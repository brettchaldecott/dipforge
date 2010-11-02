/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.desktop.master;

import com.rift.coad.daemon.desktop.GadgetInfo;
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
public class GadgetParserTest {

    public GadgetParserTest() {
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
     * Test of getGadgets method, of class GadgetParser.
     */
    @Test
    public void testGetGadgets() throws Exception {
        System.out.println("getGadgets");
        GadgetParser instance = new GadgetParser("gadgets.xml");
        List<GadgetInfo> result = instance.getGadgets();
        assertEquals(2, result.size());
        
        GadgetInfo info = result.get(0);
        assertEquals("UserGadget",info.getIdentifier());
        assertEquals("UserGadget" ,info.getName());
        assertEquals("",info.getUrl());
        assertEquals(0, info.getWidth());
        assertEquals(0, info.getHeight());
        assertEquals(null, info.getApp());
        
        info = result.get(1);
        assertEquals("time",info.getIdentifier());
        assertEquals("time" ,info.getName());
        assertEquals("/time/app",info.getUrl());
        assertEquals(10, info.getWidth());
        assertEquals(20, info.getHeight());
        assertEquals("/time/clock", info.getApp().getUrl());
        assertEquals(25,info.getApp().getHeight());
        assertEquals(100,info.getApp().getWidth());
        assertEquals(true,info.getApp().isPopup());
        assertEquals("/time/popup",info.getApp().getPopupURL());
        assertEquals(200,info.getApp().getPopupHeight());
        assertEquals(201,info.getApp().getPopupWidth());
        
        
    }

}