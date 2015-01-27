/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.lib.transaction.jta;

import javax.transaction.Transaction;
import javax.transaction.Status;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import junit.framework.TestCase;

/**
 * This object is responsible for running the local transaction manager test.
 * 
 * @author brett chaldecott
 */
public class LocalTransactionManagerTest extends TestCase {
    
    
    public class Callback {
        
        private int commitCount = 0;
        private int rollbackCount = 0;
        private int startCount = 0;

        public int getcommitCount() {
            return commitCount;
        }

        public void setCommitCount(int commitCount) {
            this.commitCount = commitCount;
        }
        
        public int incrementCommit() {
            return ++commitCount;
        }

        public int getRollbackCount() {
            return rollbackCount;
        }

        public void setRollbackCount(int rollbackCount) {
            this.rollbackCount = rollbackCount;
        }
        
        public int incrementRollbackCount() {
            return ++rollbackCount;
        }

        public int getStartCount() {
            return startCount;
        }

        public void setStartCount(int startCount) {
            this.startCount = startCount;
        }
        
        public int incrementStartCount() {
            return ++startCount;
        }
    }
    
    
    /**
     * 
     */
    public class TransactionObject implements XAResource {

        private Callback callback;
        
        public TransactionObject(Callback callback) {
            this.callback = callback;
        }
        
        @Override
        public void commit(Xid xid, boolean bln) throws XAException {
            callback.incrementCommit();
        }

        @Override
        public void end(Xid xid, int i) throws XAException {
            // ignore
        }

        @Override
        public void forget(Xid xid) throws XAException {
            // ignore
        }

        @Override
        public int getTransactionTimeout() throws XAException {
            return 0;
        }

        @Override
        public boolean isSameRM(XAResource xar) throws XAException {
            return xar == this;
        }

        @Override
        public int prepare(Xid xid) throws XAException {
            // ignore
            return 1;
        }

        @Override
        public Xid[] recover(int i) throws XAException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void rollback(Xid xid) throws XAException {
            callback.incrementRollbackCount();
        }

        @Override
        public boolean setTransactionTimeout(int i) throws XAException {
            
            return true;
        }

        @Override
        public void start(Xid xid, int i) throws XAException {
            callback.incrementStartCount();
        }
        
    }
    
    
    public LocalTransactionManagerTest(String testName) {
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
     * Test of begin method, of class LocalTransactionManager.
     */
    public void testBegin() throws Exception {
        System.out.println("begin");
        LocalTransactionManager instance = new LocalTransactionManager();
        assertEquals(instance.getTransaction(),null);
        instance.begin();
        LocalTransaction transaction = (LocalTransaction)instance.getTransaction();
        assertEquals(instance.getTransaction(),transaction);
        assertEquals(1,transaction.getReferenceCount());
        instance.begin();
        assertEquals(2,transaction.getReferenceCount());
    }

    /**
     * Test of commit method, of class LocalTransactionManager.
     */
    public void testCommit() throws Exception {
        System.out.println("commit");
        LocalTransactionManager instance = new LocalTransactionManager();
        instance.begin();
        LocalTransaction transaction = (LocalTransaction)instance.getTransaction();
        Callback callback = new Callback();
        transaction.enlistResource(new TransactionObject(callback));
        transaction.enlistResource(new TransactionObject(callback));
        
        instance.commit();
        assertEquals(2,callback.commitCount);
    }

    /**
     * Test of getStatus method, of class LocalTransactionManager.
     */
    public void testGetStatus() throws Exception {
        System.out.println("getStatus");
        LocalTransactionManager instance = new LocalTransactionManager();
        int expResult = Status.STATUS_UNKNOWN;
        int result = instance.getStatus();
        assertEquals(expResult, result);
        instance.begin();
        expResult = Status.STATUS_ACTIVE;
        result = instance.getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransaction method, of class LocalTransactionManager.
     */
    public void testGetTransaction() throws Exception {
        System.out.println("getTransaction");
        LocalTransactionManager instance = new LocalTransactionManager();
        Transaction expResult = null;
        Transaction result = instance.getTransaction();
        assertEquals(expResult, result);
        
        instance.begin();
        result = (LocalTransaction)instance.getTransaction();
        assertEquals(instance.getTransaction(),result);
        
    }


    /**
     * Test of rollback method, of class LocalTransactionManager.
     */
    public void testRollback() throws Exception {
        System.out.println("rollback");
        LocalTransactionManager instance = new LocalTransactionManager();
        instance.begin();
        LocalTransaction transaction = (LocalTransaction)instance.getTransaction();
        Callback callback = new Callback();
        transaction.enlistResource(new TransactionObject(callback));
        transaction.enlistResource(new TransactionObject(callback));
        
        instance.rollback();
        assertEquals(2,callback.rollbackCount);
    }

    /**
     * Test of setRollbackOnly method, of class LocalTransactionManager.
     */
    public void testSetRollbackOnly() throws Exception {
        System.out.println("setRollbackOnly");
        LocalTransactionManager instance = new LocalTransactionManager();
        instance.begin();
        LocalTransaction transaction = (LocalTransaction)instance.getTransaction();
        assertEquals(false,transaction.isRollbackOnly());
        instance.setRollbackOnly();
        assertEquals(true,transaction.isRollbackOnly());
    }
    
}
