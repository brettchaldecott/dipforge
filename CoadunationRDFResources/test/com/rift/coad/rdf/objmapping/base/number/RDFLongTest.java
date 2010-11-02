/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base.number;

import com.rift.coad.rdf.objmapping.base.DataType;
import junit.framework.TestCase;

// coadunation semantics
import com.rift.coad.rdf.semantic.*;
import com.rift.coad.rdf.semantic.config.Basic;


/**
 *
 * @author brett chaldecott
 */
public class RDFLongTest extends TestCase {
    
    public RDFLongTest(String testName) {
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
     * Test of getObjId method, of class RDFLong.
     */
    public void testRDFLong() throws Exception {
        System.out.println("testRDFLong");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        RDFLong instance = new RDFLong();
        instance.setValue((long)200);
        session.persist(instance);

        session.dump(System.out, "RDF/XML-ABBREV");

        RDFLong result = session.get(RDFLong.class, RDFLong.class.getName(), instance.getObjId());

        assertEquals(instance.getValue(), result.getValue());
    }

    /**
     * Test of lessThan method, of class RDFLong.
     */
    public void testLessThan() throws Exception {
        System.out.println("lessThan");
        RDFLong value = new RDFLong((long)10);
        RDFLong instance = new RDFLong((long)20);
        boolean expResult = false;
        boolean result = instance.lessThan(value);
        assertEquals(expResult, result);

        expResult = true;
        result = value.lessThan(instance);
        assertEquals(expResult, result);
    }

    /**
     * Test of greaterThan method, of class RDFLong.
     */
    public void testGreaterThan() throws Exception {
        System.out.println("greaterThan");
        RDFLong value = new RDFLong((long)10);
        RDFLong instance = new RDFLong((long)20);
        boolean expResult = true;
        boolean result = instance.greaterThan(value);
        assertEquals(expResult, result);

        expResult = false;
        result = value.greaterThan(instance);
        assertEquals(expResult, result);


    }

}
