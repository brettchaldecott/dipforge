/*
 * Semantics: The semantic library for dipforge
 * Copyright (C) 2015  2015 Burntjam
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

package com.rift.coad.rdf.semantic.persistance.jena.xml;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;

// dipforge imports
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;

/**
 * The implementation of the jena model wrapper. This simply trunks the calls
 * on to the wrapped model
 * 
 * @author brett chaldecott
 */
public class JenaModelWrapperXML implements JenaModelWrapper {
    
    // private model
    private Model model;
    
    /**
     * The constructor responsible for passing the model through
     * 
     * @param model The model
     */
    public JenaModelWrapperXML(Model model) {
        this.model = model;
    }
    
    /**
     * This method returns the model
     * 
     * @return the model to return
     */
    public Model getModel() {
        return model;
    }

    
    /**
     * This method is called to enter a critical section
     * @param lock 
     */
    public void enterCriticalSection(boolean lock) {
        this.model.enterCriticalSection(lock);
    }

    /**
     * This method is called to begin the transaction
     */
    public void begin() {
        this.model.begin();
    }

    /**
     * This method is called to abort action on the model
     */
    public void abort() {
        this.model.abort();
    }

    
    /**
     * This method is called to commit the changes
     */
    public void commit() {
        this.model.commit();
    }

    
    /**
     * This method is called to leave a critical section
     */
    public void leaveCriticalSection() {
        this.model.leaveCriticalSection();
    }
    
}
