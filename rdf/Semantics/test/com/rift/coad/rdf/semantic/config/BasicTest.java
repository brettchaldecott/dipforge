/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.config;

import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.basic.BasicSessionManager;
import java.net.URL;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class BasicTest extends TestCase {
    
    public BasicTest(String testName) {
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
     * Test of initSessionManager method, of class Basic.
     */
    public void testInitSessionManager() {
        System.out.println("initSessionManager");
        SessionManager expResult = Basic.initSessionManager();;
        SessionManager result = Basic.initSessionManager();
        if (!(expResult instanceof BasicSessionManager)) {
            fail("Expect an instance of session of basic session manager");
        }
        if (!(result instanceof BasicSessionManager)) {
            fail("Expect an instance of session of basic session manager");
        }
        if (result == expResult) {
            fail("The session managers are the same and cannot be out of the basic configuration");
        }
    }

    

}
