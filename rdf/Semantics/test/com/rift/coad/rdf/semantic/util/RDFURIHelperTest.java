/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.util;

import java.net.URI;
import junit.framework.TestCase;

/**
 *
 * @author brettc
 */
public class RDFURIHelperTest extends TestCase {
    
    public RDFURIHelperTest(String testName) {
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
     * Test of getLocalName method, of class RDFURIHelper.
     */
    public void testGetLocalName() throws Exception {
        System.out.println("getLocalName");
        RDFURIHelper instance = new RDFURIHelper("http://www.test.com/bob#stuff");
        String expResult = "stuff";
        String result = instance.getLocalName();
        assertEquals(expResult, result);
        instance = new RDFURIHelper("http://www.test.com/bob", "stuff");
        result = instance.getLocalName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNamespace method, of class RDFURIHelper.
     */
    public void testGetNamespace() throws Exception {
        System.out.println("getNamespace");
        RDFURIHelper instance = new RDFURIHelper("http://www.test.com/bob#stuff");
        String expResult = "http://www.test.com/bob";
        String result = instance.getNamespace();
        assertEquals(expResult, result);
        instance = new RDFURIHelper("http://www.test.com/bob", "stuff");
        result = instance.getNamespace();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUri method, of class RDFURIHelper.
     */
    public void testGetUri() throws Exception {
        System.out.println("getUri");
        RDFURIHelper instance = new RDFURIHelper("http://www.test.com/bob#stuff");
        URI expResult = new URI("http://www.test.com/bob#stuff");
        URI result = instance.getUri();
        assertEquals(expResult, result);
        instance = new RDFURIHelper("http://www.test.com/bob", "stuff");
        result = instance.getUri();
        assertEquals(expResult, result);
    }

}
