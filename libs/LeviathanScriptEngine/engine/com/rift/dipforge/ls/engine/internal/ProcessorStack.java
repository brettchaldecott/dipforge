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
package com.rift.dipforge.ls.engine.internal;

// imports
import com.rift.dipforge.ls.engine.EngineException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The processor stack containing the stack information for a method scope or
 * the master block scope.
 * 
 * @author brett chaldecott
 */
public class ProcessorStack implements Serializable {
    
    /**
     * The serial version
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The process stack entry
     */
    public class ProcessStackEntry implements Serializable {
        /**
        * The serial version
        */
        private static final long serialVersionUID = 1L;
        
        // private member variables
        private ProcessStackEntry parent;
        private Map variables;
        
        /**
         * The process stack entry
         * 
         * @param parent The parent reference.
         */
        public ProcessStackEntry(ProcessStackEntry parent) {
            this.parent = parent;
            variables = new HashMap();
        }
        
        
        /**
         * The process stack entry
         * 
         * @param parent The reference to the parent.
         * @param variables The variables.
         */
        public ProcessStackEntry(ProcessStackEntry parent, Map variables) {
            this.parent = parent;
            this.variables = variables;
        }

        
        /**
         * This method returns the parent information
         * 
         * @return The reference to this objects parent
         */
        public ProcessStackEntry getParent() {
            return parent;
        }

        
        
        /**
         * This method sets the parent information.
         * 
         * @param parent The reference to the parent.
         */
        public void setParent(ProcessStackEntry parent) {
            this.parent = parent;
        }
        
        
        /**
         * This method returns TRUE if the variable is found and FALSE if not.
         *
         * @param key The key to identify this variable
         * @return TRUE if found FALSE if not.
         * @throws NoSuchVariable
         */
        public boolean containsVariable(String key) {
            return containsVariable(key,true);
        }
        
        
        /**
         * This method returns TRUE if the variable is found and FALSE if not.
         *
         * @param key The key to identify this variable
         * @param searchHeap The flag that indicates if
         * @return TRUE if found FALSE if not.
         * @throws NoSuchVariable
         */
        public boolean containsVariable(String key, Boolean searchHeap) {
            if (variables.containsKey(key)) {
                return true;
            }
            if (parent != null) {
                return parent.containsVariable(key,searchHeap);
            }
            if (searchHeap) {
                return heap.containsVariable(key);
            }
            return false;
        }
        
        
        /**
         * This method returns the reference to the requested variable
         * @param key
         * @return 
         */
        public Object getVariable(String key) throws NoSuchVariable {
            if (variables.containsKey(key)) {
                return variables.get(key);
            }
            if (parent != null) {
                return parent.getVariable(key);
            }
            return heap.getVariable(key);
        }
        
        
        /**
         * This method adds a new variable
         *
         * @param key The key to identify the new variable by.
         * @param value The value to store.
         * @throws DuplicateVariable
         */
        public void addVariable(String key, Object value) throws DuplicateVariableException {
            if (containsVariable(key)) {
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
                
            }
            throw new NoSuchVariable("No such variable [" + key + "]");
        }

        
        /**
         * This method returns TRUE if the values are equal, FALSE if not.
         * @param obj The object to perform the comparision on.
         * @return TRUE if equals, FALSE if not.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ProcessStackEntry other = (ProcessStackEntry) obj;
            if (this.parent != other.parent && (this.parent == null || !this.parent.equals(other.parent))) {
                return false;
            }
            if (this.variables != other.variables && (this.variables == null || !this.variables.equals(other.variables))) {
                return false;
            }
            return true;
        }

        
        /**
         * The integer hash code for this object.
         * 
         * @return The integer hash code for the object.
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 11 * hash + (this.parent != null ? this.parent.hashCode() : 0);
            hash = 11 * hash + (this.variables != null ? this.variables.hashCode() : 0);
            return hash;
        }

        
        /**
         * This method returns the string value for this object.
         * 
         * @return The string value for this object
         */
        @Override
        public String toString() {
            return "ProcessStackEntry{" + "parent=" + parent + ", variables=" + variables + '}';
        }
        
        
        
    }
    
    // private member variables
    private ProcessorHeap heap;
    private Stack<ProcessStackEntry> stack = new Stack<ProcessStackEntry>();
    private ProcessStackEntry currentEntry;

    /**
     * The default constructor of the processor stack
     */
    public ProcessorStack(ProcessorHeap heap) {
        this.heap = heap;
        currentEntry = new ProcessStackEntry(null);
    }
    
    
    /**
     * This is the processor stack information.
     * 
     * @param heap The heap.
     * @param variables The variables on the heap.
     */
    public ProcessorStack(ProcessorHeap heap, Map variables) {
        this.heap = heap;
        currentEntry = new ProcessStackEntry(null, variables);
    }

    
    /**
     * This method returns a reference to the current top stack entry.
     * 
     * @return The reference to the current stack entry that can be queried for
     *      variables.
     */
    public ProcessStackEntry getCurrentEntry() {
        return currentEntry;
    }

    
    /**
     * This method returns a reference to the heap variable.
     * 
     * @return The reference to the heap variable
     */
    public ProcessorHeap getHeap() {
        return heap;
    }
    
    
    /**
     * This method checks to see if the variable can be found.
     * 
     * @param key The name of the variable.
     * @return TRUE if found, FALSE if not.
     */
    public boolean containsVariable(String key) {
        return currentEntry.containsVariable(key);
    }
    
    
    /**
     * This method returns the variables
     * 
     * @param key The string that identifies the variable.
     * @return The reference to the variable
     */
    public Object getVariable(String key) throws NoSuchVariable {
        return currentEntry.getVariable(key);
    }
    
    
    /**
     * This method adds a new variable.
     * 
     * @param key The reference to the variable.
     * @param value The value to set on the variable
     * @throws DuplicateVariable 
     */
    public void addVariable(String key, Object value) throws DuplicateVariableException {
        currentEntry.addVariable(key,value);
    }
    
    
    /**
     * This method sets a new variable
     * 
     * @param key The key that will identify this variable
     * @param value The value for this variable
     * @throws NoSuchVariable 
     */
    public void setVariable(String key, Object value) throws NoSuchVariable {
        currentEntry.setVariable(key,value);
    }
    
 
    /**
     * This method inits a new stack entry and pushes it to the top of the stack
     * @param variables The variables to init the stack with.
     * @throws NoSuchVariable 
     */
    public void pushStackEntry(Map variables) throws NoSuchVariable {
        stack.push(currentEntry);
        currentEntry = new ProcessStackEntry(currentEntry,variables);
    }
    
    
    /**
     * This method pops the top entry off the stack and clears it from memory
     * 
     * @throws EngineException 
     */
    public ProcessStackEntry popStackEntry() throws EngineException {
       return currentEntry = stack.pop();
    }
    
    
    /**
     * This method returns TRUE if the objects are equals and FALSE if they are
     * not
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessorStack other = (ProcessorStack) obj;
        if (this.heap != other.heap && (this.heap == null || !this.heap.equals(other.heap))) {
            return false;
        }
        if (this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) {
            return false;
        }
        if (this.currentEntry != other.currentEntry && (this.currentEntry == null || !this.currentEntry.equals(other.currentEntry))) {
            return false;
        }
        return true;
    }

    
    /**
     * This method retrieves the hash code for this object.
     * 
     * @return The integer hash code for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.heap != null ? this.heap.hashCode() : 0);
        hash = 59 * hash + (this.stack != null ? this.stack.hashCode() : 0);
        hash = 59 * hash + (this.currentEntry != null ? this.currentEntry.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method prints out the string value contained within the stack.
     * 
     * @return The string value.
     */
    @Override
    public String toString() {
        return "ProcessorStack{" + "heap=" + heap + ", stack=" + stack + ", currentEntry=" + currentEntry + '}';
    }
    
    
    
    
}
