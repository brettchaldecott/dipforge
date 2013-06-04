/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.change.request.action.sleep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class SleepActionInfoComparatorTest {
    
    public SleepActionInfoComparatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compare method, of class SleepActionInfoComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Date firstDate = new Date();
        firstDate.setTime(firstDate.getTime() - (1000 * 60));
        Date secondDate = new Date();
        secondDate.setTime(firstDate.getTime() - (1000 * 60));
        
        SleepActionInfo t = new SleepActionInfo("test1", 1000 * 180, firstDate, 
            "test", "test", new ArrayList());
        SleepActionInfo t1 = new SleepActionInfo("test2", 1000 * 180, secondDate, 
            "test", "test", new ArrayList());
        SleepActionInfoComparator instance = new SleepActionInfoComparator();
        int expResult = 60000;
        int result = instance.compare(t, t1);
        assertEquals(expResult, result);
        
        SortedSet set = new TreeSet(instance);
        set.add(t);
        set.add(t1);
        
        assertEquals(t1, set.first());
    }
}
