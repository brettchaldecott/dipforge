/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.basic;

import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.session.BasicSession;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class BasicSessionManagerTest extends TestCase {
    
    public BasicSessionManagerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getVersion method, of class BasicSessionManager.
     */
    public void testGetVersion() {
        System.out.println("getVersion");
        BasicSessionManager instance = new BasicSessionManager();
        String expResult = "1.0";
        String result = instance.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class BasicSessionManager.
     */
    public void testGetName() {
        System.out.println("getName");
        BasicSessionManager instance = new BasicSessionManager();
        String expResult = instance.getClass().getName();
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class BasicSessionManager.
     */
    public void testGetDescription() {
        System.out.println("getDescription");
        BasicSessionManager instance = new BasicSessionManager();
        String expResult = "A basic session manager";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSession method, of class BasicSessionManager.
     */
    public void testGetSession() throws Exception {
        System.out.println("getSession");
        BasicSessionManager instance = new BasicSessionManager();
        Session expResult = instance.getSession();
        Session result = instance.getSession();
        if (!(expResult instanceof BasicSession)) {
            fail("Not the correct type of session object.");
        }
        if (!(result instanceof BasicSession)) {
            fail("Not the correct type of session object.");
        }
        if (result == expResult) {
            fail("The same session has been return by the session manager.");
        }
    }

    /**
     * Test of shutdown method, of class BasicSessionManager.
     */
    public void testShutdown() throws Exception {
        System.out.println("shutdown");
        BasicSessionManager instance = new BasicSessionManager();
        instance.shutdown();
    }

}
