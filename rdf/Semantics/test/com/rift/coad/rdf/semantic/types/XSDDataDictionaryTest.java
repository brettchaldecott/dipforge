/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.types;

import junit.framework.TestCase;

/**
 *
 * @author brettc
 */
public class XSDDataDictionaryTest extends TestCase {
    
    public XSDDataDictionaryTest(String testName) {
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
     * Test of getTypeByName method, of class XSDDataDictionary.
     */
    public void testGetTypeByName() throws Exception {
        System.out.println("getTypeByName");
        String name = "string";
        DataType result = XSDDataDictionary.getTypeByName(name);
        assertEquals("http://www.w3.org/2001/XMLSchema", result.getNamespace());
        assertEquals("string", result.getLocalName());
        assertEquals("http://www.w3.org/2001/XMLSchema#string", result.getURI().toString());
    }

    /**
     * Test of getTypeByURI method, of class XSDDataDictionary.
     */
    public void testGetTypeByURI() throws Exception {
        System.out.println("getTypeByURI");
        String uri = "http://www.w3.org/2001/XMLSchema#string";
        DataType result = XSDDataDictionary.getTypeByURI(uri);
        assertEquals("http://www.w3.org/2001/XMLSchema", result.getNamespace());
        assertEquals("string", result.getLocalName());
        assertEquals("http://www.w3.org/2001/XMLSchema#string", result.getURI().toString());
    }

}
