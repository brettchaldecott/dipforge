/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
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
 * LeviathanProcessor.java
 */

// package path
package com.rift.dipforge.ls.engine.internal;

// imports
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The processor heap information.
 * 
 * @author brett chaldecott
 */
public class ProcessorHeap implements Serializable {
    
    /**
     * The serial version
     */
    private static final long serialVersionUID = 1L;
    
    // private member variables
    private Map variables;

    /**
     * The default constructor.
     */
    public ProcessorHeap() {
        variables = new HashMap();
    }

    
    /**
     * This constructor sets the internal variables.
     * 
     * @param variables The internal variables.
     */
    public ProcessorHeap(Map variables) {
        this.variables = variables;
    }
    
    /**
     * This method returns TRUE if the variable is found and FALSE if not.
     * 
     * @param key The key to identify this variable
     * @return TRUE if found FALSE if not.
     * @throws NoSuchVariable 
     */
    public boolean containsVariable(String key) {
        return variables.containsKey(key);
    }
    
    
    /**
     * This method returns the variable information.
     * 
     * @param key The key to identify the variable by.
     * @return The reference to the object.
     */
    public Object getVariable(String key) throws NoSuchVariable {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }
        throw new NoSuchVariable("No such variable [" + key + "]");
    }
    
    
    /**
     * This method adds a new variable
     * @param key The key to identify the new variable by.
     * @param value The value to store.
     * @throws DuplicateVariable 
     */
    public void addVariable(String key, Object value) throws DuplicateVariableException {
        if (variables.containsKey(key)) {
            throw new DuplicateVariableException("Duplicate variable [" + key + "]");
        }
        variables.put(key, value);
    }
    
    
    /**
     * This method sets an existing variable value.
     * 
     * @param key The key to set the value for.
     * @param value The value to set.
     * @throws NoSuchVariable 
     */
    public void setVariable(String key, Object value) throws NoSuchVariable {
        if (variables.containsKey(key)) {
            variables.put(key, value);
            return;
        }
        throw new NoSuchVariable("No such variable [" + key + "]");
    }
}
