/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic.persistance.jena.http;

import org.apache.jena.rdf.model.Model;
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;

/**
 *
 * @author brett
 */
public class JenaHTTPModelWrapper implements JenaModelWrapper {
    
    private Model model;

    /**
     * This constructor 
     * @param model 
     */
    public JenaHTTPModelWrapper(Model model) {
        this.model = model;
    }
    
    
    /**
     * This method returns the model reference.
     * 
     * @return The reference to the model
     */
    @Override
    public Model getModel() {
        return model;
    }

    
    /**
     * This method is called to enter the critical section.
     * 
     * @param lock Is it a write lock.
     */
    @Override
    public void enterCriticalSection(boolean lock) {
        model.enterCriticalSection(lock);
    }

    
    /**
     * The beginning of the transaction
     */
    @Override
    public void begin() {
        model.begin();
    }

    
    /**
     * This method is called to abort the transaction
     */
    @Override
    public void abort() {
        model.abort();
    }

    
    /**
     * This method to commit the transaction
     */
    @Override
    public void commit() {
        model.commit();
    }

    
    /**
     * This method is called to leave a critical section
     */
    @Override
    public void leaveCriticalSection() {
        model.leaveCriticalSection();
    }
    
}
