/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base;

import com.rift.coad.rdf.objmapping.base.CountryLookup;
import com.rift.coad.rdf.objmapping.base.Country;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class CountryLookupTest extends TestCase {
    
    public CountryLookupTest(String testName) {
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
     * Test of getCountry method, of class CountryLookup.
     */
    public void testGetCountry() throws Exception {
        System.out.println("getCountry");
        String code = "";
        Country expResult = Country.ZA;
        Country result = CountryLookup.getCountry("ZA");
        assertEquals(expResult, result);
    }

}
