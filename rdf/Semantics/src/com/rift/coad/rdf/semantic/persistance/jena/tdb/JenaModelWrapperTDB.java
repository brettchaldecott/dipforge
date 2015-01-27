/*
 * Semantics: The semantic library for dipforge
 * Copyright (C) 2015  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * JenaModelWrapperXML.java
 */

package com.rift.coad.rdf.semantic.persistance.jena.tdb;

// jena imports
import com.rift.coad.rdf.semantic.persistance.jena.xml.*;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.query.ReadWrite;

// dipforge imports
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;

/**
 * The implementation of the jena model wrapper. This simply trunks the calls
 * on to the wrapped model
 * 
 * @author brett chaldecott
 */
public class JenaModelWrapperTDB implements JenaModelWrapper {
    
    // private model
    private Dataset dataset;
    private Model model = null;
    private com.hp.hpl.jena.shared.Lock lock;
    private boolean commit = true;
    private boolean read = true;
    
    /**
     * The constructor responsible for passing the model through
     * 
     * @param model The model
     */
    public JenaModelWrapperTDB(Dataset dataset) {
        this.dataset = dataset;
        lock = dataset.getLock();
    }
    
    /**
     * The constructor responsible for passing the model through
     * 
     * @param model The model
     */
    public JenaModelWrapperTDB(JenaModelWrapperTDB model) {
        this.dataset = model.dataset;
        this.model = model.model;
        lock = dataset.getLock();
    }
    
    /**
     * This method returns the model
     * 
     * @return the model to return
     */
    public Model getModel() {
        if (model == null) {
            model = dataset.getDefaultModel();
        }
        return model;
    }

    
    /**
     * This method is called to enter a critical section
     * @param lock 
     */
    public void enterCriticalSection(boolean lock) {
//        if (lock) {
//            System.out.println("[" + System.identityHashCode(dataset) + "] Enter a critical section and begin read transaction");
//            dataset.begin(ReadWrite.READ);
//            read = true;
//        } else {
            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] Enter a critical section and begin write transaction");
            //dataset.begin(ReadWrite.WRITE);
            this.lock.enterCriticalSection(Lock.WRITE);
            read = false;
            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] After creating a write lock");
//        }
        //this.getModel().enterCriticalSection(false);
    }

    /**
     * This method is called to begin the transaction
     */
    public void begin() {
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] Enter a begin");
//        this.getModel().begin();
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] After the begin");
    }

    /**
     * This method is called to abort action on the model
     */
    public void abort() {
        //this.commit = false;
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] Abort has been called");
//        dataset.abort();
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] After the abort");
    }

    
    /**
     * This method is called to commit the changes
     */
    public void commit() {
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] Commit a transaction");
//        dataset.commit();    
//        System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] After commit");
    }

    
    /**
     * This method is called to leave a critical section
     */
    public void leaveCriticalSection() {
        try {
            //if (commit && !read) {
            //    System.out.println("[" + System.identityHashCode(dataset) + "] Commit the changes");
            //    dataset.commit();   
            //} //else {
            //    dataset.abort();
            //}
            // end the critial section
            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] End ");
            //this.getModel().leaveCriticalSection();
            //dataset.end();
            lock.leaveCriticalSection();
            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] After ending a lock");
            
        } catch (Exception ex) {
            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "]Failed to commit the changes : " + ex.getMessage());
//            try {
//                dataset.end();
//            } catch (Exception ex2) {
//                System.out.println("[" + System.identityHashCode(dataset) + "]Failed to end the changes : " + ex.getMessage());
//            }
//            System.out.println("[" + Thread.currentThread().getId() + "][" + System.identityHashCode(dataset) + "] The lock has been ended.");
        }
    }
    
}
