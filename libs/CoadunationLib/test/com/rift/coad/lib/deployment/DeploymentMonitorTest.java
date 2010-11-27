/*
 * DeploymentMonitorTest.java
 * JUnit based test
 *
 * Created on February 9, 2007, 6:54 AM
 */

package com.rift.coad.lib.deployment;

import junit.framework.*;

/**
 *
 * @author mincemeat
 */
public class DeploymentMonitorTest extends TestCase {
    
    /**
     * This class test the deployment wait method
     */
    public class TestThread extends Thread {
        /**
         * The run method
         */
        public void run() {
            DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
            waiting = false;
        }
    }
    
    private boolean waiting = true;
    
    public DeploymentMonitorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /**
     * Test of com.rift.coad.lib.deployment.DeploymentMonitor.
     */
    public void testDeploymentMonitor() throws Exception {
        System.out.println("testDeploymentMonitor");
        
        DeploymentMonitor expResult = DeploymentMonitor.getInstance();
        DeploymentMonitor result = DeploymentMonitor.getInstance();
        assertEquals(expResult, result);
        
        TestThread testThread = new TestThread();
        testThread.start();
        
        assertEquals(result.isInitDeployComplete(),false);
        Thread.sleep(500);
        assertEquals(waiting,true);
        result.initDeployCompleted();
        assertEquals(result.isInitDeployComplete(),true);
        Thread.sleep(500);
        assertEquals(waiting,false);
        result.terminate();
        assertEquals(result.isTerminated(),true);
    }

}
