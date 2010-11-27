/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.daemon.rss;

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
public class RSSXMLConfigHelperTest {

    public RSSXMLConfigHelperTest() {
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
     * Test of getFeedMap method, of class RSSXMLConfigHelper.
     */
    @Test
    public void testGetFeedMap() throws Exception {
        System.out.println("getFeedMap");
        RSSXMLConfigHelper instance = new RSSXMLConfigHelper("feeds.xml");
        Map<String, Feed> expResult = instance.getFeedMap();
        Map<String, Feed> result = instance.getFeedMap();
        assertEquals(expResult, result);
        
        assertEquals(4, result.size());
        
        Feed feed = result.get("test3");
        assertEquals("test3", feed.getName());
        assertEquals("application3", feed.getApplication());
        assertEquals("http://url3", feed.getUrl());
    }

}