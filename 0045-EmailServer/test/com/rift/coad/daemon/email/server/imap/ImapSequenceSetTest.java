/*
 * ImapSequenceSetTest.java
 * JUnit based test
 *
 * Created on March 27, 2008, 8:03 AM
 */

package com.rift.coad.daemon.email.server.imap;

import junit.framework.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author brett
 */
public class ImapSequenceSetTest extends TestCase {
    
    public ImapSequenceSetTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of getSequenceSet method, of class com.rift.coad.daemon.email.server.imap.ImapSequenceSet.
     */
    public void testGetSequenceSet() throws Exception {
        System.out.println("getSequenceSet");
        
        ImapSequenceSet instance = new ImapSequenceSet("1");
        
        List expResult = new ArrayList();
        expResult.add(new Long(1));
        List result = instance.getSequenceSet();
        assertEquals(expResult, result);
        
        instance = new ImapSequenceSet("2:4");
        expResult = new ArrayList();
        expResult.add(new Long(2));
        expResult.add(new Long(3));
        expResult.add(new Long(4));
        result = instance.getSequenceSet();
        assertEquals(expResult, result);
        
        instance = new ImapSequenceSet("5:8,9,12,15");
        expResult = new ArrayList();
        expResult.add(new Long(5));
        expResult.add(new Long(6));
        expResult.add(new Long(7));
        expResult.add(new Long(8));
        expResult.add(new Long(9));
        expResult.add(new Long(12));
        expResult.add(new Long(15));
        result = instance.getSequenceSet();
        assertEquals(expResult, result);
        
        
        instance = new ImapSequenceSet("2:4,7:*");
        expResult = new ArrayList();
        expResult.add(new Long(2));
        expResult.add(new Long(3));
        expResult.add(new Long(4));
        expResult.add(new Long(7));
        expResult.add("*");
        result = instance.getSequenceSet();
        assertEquals(expResult, result);
        
        
        try {
            instance = new ImapSequenceSet("2:*,7:8");
            fail("The sequence is not correctly formated");
        } catch (Exception ex) {
            // ignore
        }
        
        
        try {
            instance = new ImapSequenceSet("*:*");
            fail("The sequence is not correctly formated");
        } catch (Exception ex) {
            // ignore
        }
        
        
        instance = new ImapSequenceSet("*");
        expResult = new ArrayList();
        expResult.add("*");
        result = instance.getSequenceSet();
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of testInSequenceSet method, of class com.rift.coad.daemon.email.server.imap.ImapSequenceSet.
     */
    public void testInSequenceSet() throws Exception {
        System.out.println("inSequenceSet");
        
        ImapSequenceSet instance = new ImapSequenceSet("1");
        
        assertEquals(instance.inSequenceSet(1), true);
        assertEquals(instance.inSequenceSet(2), false);
        
        instance = new ImapSequenceSet("*");
        
        assertEquals(instance.inSequenceSet(100000), true);
        assertEquals(instance.inSequenceSet(2), true);
        assertEquals(instance.inSequenceSet(500), true);
        
        instance = new ImapSequenceSet("2:*");
        
        assertEquals(instance.inSequenceSet(1), false);
        assertEquals(instance.inSequenceSet(2), true);
        assertEquals(instance.inSequenceSet(500), true);
    }
    
    
    /**
     * Test of testSetEnd method, of class com.rift.coad.daemon.email.server.imap.ImapSequenceSet.
     */
    public void testSetEnd() throws Exception {
        System.out.println("getSetEnd");
        
        ImapSequenceSet instance = new ImapSequenceSet("1:*");
        instance.setEnd(5);
        
        assertEquals(instance.inSequenceSet(1), true);
        assertEquals(instance.inSequenceSet(2), true);
        assertEquals(instance.inSequenceSet(6), false);
        
        try {
            instance = new ImapSequenceSet("*");
            instance.setEnd(10);
            fail("There was no valid range supplied");
        } catch (Exception ex){
            // ignore
        }
        
        instance = new ImapSequenceSet("2:4");
        instance.setEnd(7);
        assertEquals(instance.inSequenceSet(2), true);
        assertEquals(instance.inSequenceSet(4), true);
        assertEquals(instance.inSequenceSet(7), false);
    }
}
