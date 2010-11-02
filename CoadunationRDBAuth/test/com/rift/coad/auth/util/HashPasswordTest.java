/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.auth.util;

import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class HashPasswordTest extends TestCase {
    
    public HashPasswordTest(String testName) {
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
     * Test of generateSha method, of class HashPassword.
     */
    public void testGenerateSha() throws Exception {
        System.out.println("generateSha");
        String password = "112233";
        String expResult = HashPassword.generateSha(password);
        String result = HashPassword.generateSha(password);
        assertEquals(expResult, result);
        
        expResult = HashPassword.generateSha("11223344");
        result = HashPassword.generateSha(password);
        
        if (expResult.equals(result)) {
            fail("The sha values are the same and should not be");
        }
    }

}
