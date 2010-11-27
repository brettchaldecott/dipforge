/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base.number;

// coadunation semantics
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.semantic.*;
import com.rift.coad.rdf.semantic.config.Basic;


import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class RDFIntegerTest extends TestCase {
    
    public RDFIntegerTest(String testName) {
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
     * Test of getObjId method, of class RDFInteger.
     */
    public void testRDFInteger() throws Exception {
        System.out.println("testRDFInteger");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        RDFInteger instance = new RDFInteger();
        instance.setValue(200);
        session.persist(instance);

        session.dump(System.out, "RDF/XML-ABBREV");

        RDFInteger result = session.get(RDFInteger.class, RDFInteger.class.getName(), instance.getObjId());

        assertEquals(instance.getValue(), result.getValue());

    }

    /**
     * Test of lessThan method, of class RDFInteger.
     */
    public void testLessThan() throws Exception {
        System.out.println("lessThan");
        RDFInteger value = new RDFInteger(10);
        RDFInteger instance = new RDFInteger(20);
        boolean expResult = false;
        boolean result = instance.lessThan(value);
        assertEquals(expResult, result);

        expResult = true;
        result = value.lessThan(instance);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of greaterThan method, of class RDFInteger.
     */
    public void testGreaterThan() throws Exception {
        System.out.println("greaterThan");
        RDFInteger value = new RDFInteger(10);
        RDFInteger instance = new RDFInteger(20);
        boolean expResult = true;
        boolean result = instance.greaterThan(value);
        assertEquals(expResult, result);

        expResult = false;
        result = value.greaterThan(instance);
        assertEquals(expResult, result);
    }

}
