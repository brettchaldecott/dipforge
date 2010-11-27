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
public class RDFFloatTest extends TestCase {
    
    public RDFFloatTest(String testName) {
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
     * Test of getObjId method, of class RDFFloat.
     */
    public void testRDFFloat() throws Exception {
        System.out.println("testRDFFloat");

        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        RDFFloat instance = new RDFFloat();
        instance.setValue((float)100.01);
        session.persist(instance);
        session.dump(System.out, "RDF/XML-ABBREV");

        RDFFloat result = session.get(RDFFloat.class, RDFFloat.class.getName(), instance.getObjId());

        assertEquals(instance.getValue(), result.getValue());
    }

    /**
     * Test of lessThan method, of class RDFFloat.
     */
    public void testLessThan() throws Exception {
        System.out.println("lessThan");
        RDFFloat value = new RDFFloat((float)10.0);
        RDFFloat instance = new RDFFloat((float)20.0);
        boolean expResult = false;
        boolean result = instance.lessThan(value);
        assertEquals(expResult, result);


        expResult = true;
        result = value.lessThan(instance);
        assertEquals(expResult, result);
    }

    /**
     * Test of greaterThan method, of class RDFFloat.
     */
    public void testGreaterThan() throws Exception {
        System.out.println("greaterThan");
        RDFFloat value = new RDFFloat((float)10.0);
        RDFFloat instance = new RDFFloat((float)20.0);
        boolean expResult = true;
        boolean result = instance.greaterThan(value);
        assertEquals(expResult, result);


        expResult = false;
        result = value.greaterThan(instance);
        assertEquals(expResult, result);
    }
}
