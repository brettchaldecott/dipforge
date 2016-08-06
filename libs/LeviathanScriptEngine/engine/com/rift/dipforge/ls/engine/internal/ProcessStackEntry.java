/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.ls.engine.internal;

import com.rift.dipforge.ls.engine.EngineException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author brett chaldecott
 */
/**
 * The process stack entry
 */
public abstract class ProcessStackEntry implements Serializable {

    /**
     * The serial version
     */
    private static final long serialVersionUID = 1L;
    // private member variables
    private ProcessorMemoryManager processorMemoryManager;
    private ProcessStackEntry parent;
    private Map variables;

    /**
     * The process stack entry
     *
     * @param processMemoryManager The reference to the process memory manager
     * @param parent The parent reference.
     */
    public ProcessStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent) {
        this.processorMemoryManager = processorMemoryManager;
        this.parent = parent;
        variables = new HashMap();
        this.processorMemoryManager.pushStack(this);
    }

    /**
     * The process stack entry
     *
     * @param parent The reference to the parent.
     * @param variables The variables.
     */
    public ProcessStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables) {
        this.processorMemoryManager = processorMemoryManager;
        this.parent = parent;
        if (variables != null) {
            this.variables = variables;
        } else {
            this.variables = new HashMap();
        }
        this.processorMemoryManager.pushStack(this);
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
     */
    public boolean containsVariable(String key) {
        return containsVariable(key, true);
    }

    /**
     * This method returns TRUE if the variable is found and FALSE if not.
     *
     * @param key The key to identify this variable
     * @param searchHeap The flag that indicates if
     * @return TRUE if found FALSE if not.
     */
    public boolean containsVariable(String key, Boolean searchHeap) {
        if (variables.containsKey(key)) {
            return true;
        }
        if (parent != null) {
            return parent.containsVariable(key, searchHeap);
        }
        if (searchHeap) {
            return processorMemoryManager.getHeap().containsVariable(key);
        }
        return false;
    }

    
    /**
     * This method returns the reference to the requested variable
     *
     * @param key The key that identifies the variable.
     * @return The object that was retrieved.
     * @exception NoSuchVariable If the variable being retrieved does not exist
     */
    public Object getVariable(String key) throws NoSuchVariable {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }
        if (parent != null) {
            return parent.getVariable(key);
        }
        return processorMemoryManager.getHeap().getVariable(key);
    }

    
    /**
     * This method adds a new variable
     *
     * @param key The key to identify the new variable by.
     * @param value The value to store.
     * @throws DuplicateVariableException If there is a duplicate variable
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
            return;
        }
        if (containsVariable(key)) {
            if (parent != null) {
                parent.setVariable(key,value);
            } else {
                processorMemoryManager.getHeap().setVariable(key, value);
            }
            return;
        }
        throw new NoSuchVariable("No such variable [" + key + "]");
    }

    
    /**
     * This method returns a reference to the processor memory manager.
     * 
     * @return The reference to the processor memory manager
     */
    public ProcessorMemoryManager getProcessorMemoryManager() {
        return processorMemoryManager;
    }
    
    
    /**
     * This method is called to pop an existing stack entry
     */
    public void pop() {
        this.processorMemoryManager.popStack();
    }
    
    
    /**
     * This method is called to execute
     * 
     * @throws EngineException 
     */
    public abstract void execute() throws EngineException;
    
    
    /**
     * This method sets the result of this object.
     * 
     * @param result The result of this object.
     * @throws EngineException 
     */
    public abstract void setResult(Object result) throws EngineException;
    
    
    /**
     * This method returns TRUE if the values are equal, FALSE if not.
     *
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
