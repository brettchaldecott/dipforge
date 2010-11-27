/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.desktop.master;

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
public class MenuParserTest {

    public MenuParserTest() {
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
     * Test of getMenus method, of class MenuParser.
     */
    @Test
    public void testGetMenus() throws Exception {
        System.out.println("getMenus");
        MenuParser instance = new MenuParser("menu.xml");
        List result = instance.getMenus();
        assertEquals(2, result.size());
        MenuParser.Menu menu = (MenuParser.Menu)result.get(0);
        assertEquals("<img src='images/Title.png' border=0/>",menu.getIdentifier());
        assertEquals(4,menu.getItems().size());
        MenuParser.Launcher launch = (MenuParser.Launcher)menu.getItems().get(0);
        assertEquals("Admin Console",launch.getIdentifier());
        assertEquals("Admin Console",launch.getName());
        assertEquals("",launch.getTitle());
        assertEquals("Admin Console",launch.getMouseOver());
        assertEquals("/CoadunationAdmin/",launch.getUrl());
        assertEquals(600,launch.getWidth());
        assertEquals(500,launch.getHeight());
        assertEquals("admin",launch.getRole());
        assertEquals(null,menu.getItems().get(1));
        
        MenuParser.Menu subMenu = (MenuParser.Menu)menu.getItems().get(3);
        assertEquals("sub menu",subMenu.getIdentifier());
        assertEquals(1,subMenu.getItems().size());
        launch = (MenuParser.Launcher)subMenu.getItems().get(0);
        assertEquals("Admin Console",launch.getIdentifier());
        assertEquals("Admin Console",launch.getName());
        assertEquals("",launch.getTitle());
        assertEquals("Admin Console",launch.getMouseOver());
        assertEquals("/CoadunationAdmin/",launch.getUrl());
        assertEquals(600,launch.getWidth());
        assertEquals(500,launch.getHeight());
        assertEquals("admin",launch.getRole());
        
        
        menu = (MenuParser.Menu)result.get(1);
        assertEquals("<img src='images/Title2.png' border=0/>",menu.getIdentifier());
        assertEquals(3,menu.getItems().size());
        launch = (MenuParser.Launcher)menu.getItems().get(0);
        assertEquals("Admin Console",launch.getIdentifier());
        assertEquals("Admin Console",launch.getName());
        assertEquals("",launch.getTitle());
        assertEquals("Admin Console",launch.getMouseOver());
        assertEquals("/CoadunationAdmin/",launch.getUrl());
        assertEquals(600,launch.getWidth());
        assertEquals(500,launch.getHeight());
        assertEquals("admin",launch.getRole());

        
    }

}