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
 * This object tests the serialization of the rdf double
 *
 * @author brett chaldecott
 */
public class RDFDoubleTest extends TestCase {
    
    public RDFDoubleTest(String testName) {
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
     * Test of getObjId method, of class RDFDouble.
     */
    public void testRDFDouble() throws Exception {
        System.out.println("testRDFDouble");

        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        RDFDouble instance = new RDFDouble();
        instance.setValue(100.10001);
        session.persist(instance);
        session.dump(System.out, "RDF/XML-ABBREV");

        RDFDouble result = session.get(RDFDouble.class, RDFDouble.class.getName(), instance.getObjId());

        assertEquals(instance.getValue(), result.getValue());
    }


    /**
     * Test of lessThan method, of class RDFDouble.
     */
    public void testLessThan() throws Exception {
        System.out.println("lessThan");
        RDFDouble value = new RDFDouble(10);
        RDFDouble instance = new RDFDouble(20);
        boolean expResult = false;
        boolean result = instance.lessThan(value);
        assertEquals(expResult, result);

        expResult = true;
        result = value.lessThan(instance);
        assertEquals(expResult, result);

    }

    /**
     * Test of greaterThan method, of class RDFDouble.
     */
    public void testGreaterThan() throws Exception {
        System.out.println("greaterThan");
        RDFDouble value = new RDFDouble(10);
        RDFDouble instance = new RDFDouble(20);
        boolean expResult = true;
        boolean result = instance.greaterThan(value);
        assertEquals(expResult, result);

        expResult = false;
        result = value.greaterThan(instance);
        assertEquals(expResult, result);

    }
}
