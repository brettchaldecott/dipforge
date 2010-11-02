/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.transaction;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class TransactionManagerTest extends TestCase {
    
    public class Trans implements XAResource {

        public void commit(Xid arg0, boolean arg1) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void end(Xid arg0, int arg1) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void forget(Xid arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getTransactionTimeout() throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isSameRM(XAResource arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int prepare(Xid arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Xid[] recover(int arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void rollback(Xid arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean setTransactionTimeout(int arg0) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void start(Xid arg0, int arg1) throws XAException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

    public TransactionManagerTest(String testName) {
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
     * Test of getInstance method, of class TransactionManager.
     */
    public void testGetInstance() throws Exception {
        System.out.println("getInstance");
        TransactionManager expResult = TransactionManager.getInstance();
        TransactionManager result = TransactionManager.getInstance();
        assertEquals(expResult, result);
        if (!(result instanceof DefaultTransactionManager)) {
            fail("Not the correct transaction manager");
        }
        result.enlist(new Trans());
    }

    

}
