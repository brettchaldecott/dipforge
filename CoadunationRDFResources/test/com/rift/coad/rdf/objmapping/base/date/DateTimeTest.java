/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base.date;

import com.rift.coad.rdf.objmapping.base.DataType;
import java.util.Date;
import junit.framework.TestCase;

// coadunation semantics
import com.rift.coad.rdf.semantic.*;
import com.rift.coad.rdf.semantic.config.Basic;


/**
 *
 * @author brett chaldecott
 */
public class DateTimeTest extends TestCase {
    
    public DateTimeTest(String testName) {
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
     * Test of getObjId method, of class DateTime.
     */
    public void testDateTime() throws Exception {
        System.out.println("testDateTime");
        SessionManager manager = Basic.initSessionManager();
        Session session = manager.getSession();

        DateTime instance = new DateTime();
        instance.setValue(new Date());
        session.persist(instance);

        session.dump(System.out, "RDF/XML-ABBREV");

        DateTime result = session.get(DateTime.class, DateTime.class.getName(), instance.getObjId());

        assertEquals(instance.getValue(), result.getValue());

    }


    /**
     * Test of equals method, of class DateTime.
     */
    public void testEquals() throws Exception {
        System.out.println("equals");
        Object obj = null;
        DateTime instance = new DateTime();
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
        
        Thread.sleep(100);

        result = instance.equals(new DateTime());
        assertEquals(result, false);
    }

    /**
     * Test of lessThan method, of class DateTime.
     */
    public void testLessThan() throws Exception {
        System.out.println("lessThan");
        DataType value = null;
        DateTime instance = new DateTime();
        boolean expResult = false;
        boolean result = instance.lessThan(instance);
        assertEquals(expResult, result);

        Thread.sleep(100);
        
        result = instance.lessThan(new DateTime());
        assertEquals(result, true);
    }

    /**
     * Test of greaterThan method, of class DateTime.
     */
    public void testGreaterThan() throws Exception {
        System.out.println("greaterThan");
        DataType value = new DateTime();
        Thread.sleep(100);
        DateTime instance = new DateTime();
        boolean expResult = false;
        boolean result = instance.greaterThan(instance);
        assertEquals(expResult, result);

        result = instance.greaterThan(value);
        assertEquals(result, true);
    }

}
