/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.utils;

import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class HashUtilTest extends TestCase {
    
    public HashUtilTest(String testName) {
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
     * Test of md5 method, of class HashUtil.
     */
    public void testMd5() {
        System.out.println("md5");
        String source = "testvalue";
        String expResult = HashUtil.md5(source);
        String result = HashUtil.md5(source);
        assertEquals(expResult, result);
        System.out.println(result);
    }

    /**
     * Test of ssha method, of class HashUtil.
     */
    public void testSha() {
        System.out.println("sha");
        String source = "testvalue";
        String expResult = HashUtil.sha(source);
        String result = HashUtil.sha(source);
        assertEquals(expResult, result);
        System.out.println(result);
    }

}
