/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.lib.transaction.jta;

import javax.transaction.TransactionManager;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class LocalTransactionManagerConnectorTest extends TestCase {
    
    public LocalTransactionManagerConnectorTest(String testName) {
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

    public void testDipTransactionManagerConnector() {
        TransactionManager manager = 
                LocalTransactionManagerConnector.getTransactionManager();
        TransactionManager manager2 = 
                LocalTransactionManagerConnector.getTransactionManager();
        assertEquals(manager, manager2);
        
    }
    
}
