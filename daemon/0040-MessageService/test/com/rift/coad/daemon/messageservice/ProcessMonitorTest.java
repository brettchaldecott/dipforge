/*
 * ProcessMonitorTest.java
 * JUnit based test
 *
 * Created on February 1, 2007, 9:23 AM
 */

package com.rift.coad.daemon.messageservice;

import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.*;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


/**
 * The class responsible for testing the ProcessMonitor.
 *
 * @author Brett Chaldecott
 */
public class ProcessMonitorTest extends TestCase {
    
    
    /**
     * This class is responsible for testing the ProcessMonitor
     */
    public class TestThread extends Thread {
        
        // private member variables
        private long delay = 0;
        
        /**
         * The delay
         */
        public TestThread (long delay) {
            this.delay = delay;
        }
        
        /**
         * This method tests the delay
         */
        public void run() {
            callCount.incrementAndGet();
            waitCount.incrementAndGet();
            ProcessMonitor.getInstance().monitor(delay);
            waitCount.decrementAndGet();
        }
    }
    
    
    public ProcessMonitorTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    // call count
    private AtomicInteger callCount = new AtomicInteger(0);
    private AtomicInteger waitCount = new AtomicInteger(0);
    
    /**
     * Test of class com.rift.coad.daemon.messageservice.ProcessMonitor.
     */
    public void testProcessMonitor() throws Exception {
        System.out.println("testProcessMonitor");
        
        ProcessMonitor expResult = ProcessMonitor.getInstance();
        ProcessMonitor result = ProcessMonitor.getInstance();
        assertEquals(expResult, result);
        
        TestThread testThread1 = new TestThread(400);
        testThread1.start();
        TestThread testThread2 = new TestThread(0);
        testThread2.start();
        TestThread testThread3 = new TestThread(400);
        testThread3.start();
        
        Thread.sleep(100);
        
        assertEquals(3,callCount.get());
        assertEquals(3,waitCount.get());
        
        Thread.sleep(500);
        
        assertEquals(3,callCount.get());
        assertEquals(1,waitCount.get());
        
        ProcessMonitor.getInstance().notifyProcessor();
        Thread.sleep(100);
        
        assertEquals(3,callCount.get());
        assertEquals(0,waitCount.get());
        
        testThread1 = new TestThread(400);
        testThread1.start();
        
        Thread.sleep(100);
        
        assertEquals(4,callCount.get());
        assertEquals(1,waitCount.get());
        
        ProcessMonitor.getInstance().terminate();
        
        testThread2 = new TestThread(0);
        testThread2.start();
        
        Thread.sleep(100);
        
        assertEquals(5,callCount.get());
        assertEquals(0,waitCount.get());
    }

    
    
}
