/*
 * TomcatXMLContextTest.java
 * JUnit based test
 *
 * Created on July 15, 2007, 8:19 AM
 */

package com.rift.coad.daemon.tomcat;

import junit.framework.*;

/**
 *
 * @author brett
 */
public class TomcatXMLContextTest extends TestCase {
    
    public TomcatXMLContextTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getContext method, of class com.rift.coad.daemon.tomcat.TomcatXMLContext.
     */
    public void testGetContext() throws Exception {
        System.out.println("getContext");
        
        TomcatXMLContext instance = new TomcatXMLContext(
                TomcatXMLContext.class.getClassLoader());
        
        String expResult = "/test";
        String result = instance.getContext();
        assertEquals(expResult, result);
        
    }
    
}
