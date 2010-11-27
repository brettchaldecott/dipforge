/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.event;

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
public class FeedXMLConfigHelperTest {

    public FeedXMLConfigHelperTest() {
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
     * Test of listFeedIdentifiers method, of class FeedXMLConfigHelper.
     */
    @Test
    public void testListFeedIdentifiers() throws Exception {
        System.out.println("listFeedIdentifiers");
        String desktopName = "test1";
        FeedXMLConfigHelper instance = new FeedXMLConfigHelper("FeedConfig.xml");
        List<String> expResult = instance.listFeedIdentifiers(desktopName);
        List<String> result = instance.listFeedIdentifiers(desktopName);
        assertEquals(3, result.size());
        assertEquals(expResult, result);
    }

    /**
     * Test of getFilters method, of class FeedXMLConfigHelper.
     */
    @Test
    public void testGetFilters() throws Exception {
        System.out.println("getFilters");
        String feed = "feed1";
        FeedXMLConfigHelper instance = new FeedXMLConfigHelper("FeedConfig.xml");
        List<String[]> expResult = instance.getFilters(feed);
        List<String[]> result = instance.getFilters(feed);
        assertEquals(expResult, result);
        assertEquals(1, result.size());
        String[] filter = result.get(0);
        assertEquals("*", filter[0]);
        assertEquals("*", filter[1]);
    }

}