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

// package
import com.rift.coad.lib.common.RandomGuid;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.LeviathanConstants.Status;
import com.rift.dipforge.ls.parser.obj.Statement;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

/**
 * This object controls the memory for a processor
 * 
 * @author brett chaldecott
 */
public class ProcessorMemoryManager implements Serializable {
    
    /**
     * The serial version
     */
    private static final long serialVersionUID = 1L;
    
    // private member variables
    private String guid;
    private LeviathanConstants.Status state;
    private Statement currentStatement;
    private Workflow codeSpace;
    private ProcessorHeap heap;
    private Stack<ProcessStackEntry> stack = new Stack<ProcessStackEntry>();
    private ProcessStackEntry currentStack;

    
    /**
     * This is the process memory manager
     * 
     * @param codeSpace The reference to the code space
     */
    public ProcessorMemoryManager(Workflow codeSpace, Map variables) throws
            EngineException {
        try {
            guid = RandomGuid.getInstance().getGuid();
            this.codeSpace = codeSpace;
            heap = new ProcessorHeap(variables);
        } catch (Exception ex) {
            throw new EngineException(
                    "Failed to create the memory manager because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a new stack using the variables and pushes the
     * previous onto the stack.
     * 
     * @param variables The list of variables to create the new stack with.
     * @return The newly created stack
     */
    public void pushStack(ProcessStackEntry entry) {
        if (currentStack != null) {
            stack.push(currentStack);
        }
        currentStack = entry;
    }
    
    
    /**
     * This method pops the entry off the stack.
     * 
     * @return The reference to the stack entry that was popped of the top.
     */
    public ProcessStackEntry popStack() {
        return currentStack = stack.pop();
    }
    
    
    /**
     * This method returns the peek stack information
     * 
     * @return The reference to the stack
     */
    public ProcessStackEntry peekStack() {
        return currentStack;
    }

    
    /**
     * The guid for this object.
     * 
     * @return The string reference to the guid
     */
    public String getGuid() {
        return guid;
    }

    
    /**
     * This method returns a reference to the current status of this process.
     * 
     * @return The current status of this process.
     */
    public Status getState() {
        return state;
    }
    
    
    /**
     * This method sets the state of the object.
     * 
     * @param state The state of the object
     */
    public void setState(Status state) {
        this.state = state;
    }

    
    /**
     * The getter for the current statement.
     * 
     * @return The reference to the current statement.
     */
    public Statement getCurrentStatement() {
        return currentStatement;
    }

    
    /**
     * The setter for the current statemnt.
     * 
     * @param currentStatement The new current statement value
     */
    public void setCurrentStatement(Statement currentStatement) {
        this.currentStatement = currentStatement;
    }

    
    /**
     * This method returns the code space.
     * 
     * @return The reference to the code space.
     */
    public Workflow getCodeSpace() {
        return codeSpace;
    }

    
    /**
     * This method returns the current stack information.
     * 
     * @return The reference to the current stack information.
     */
    public ProcessStackEntry getCurrentStack() {
        return currentStack;
    }

    
    /**
     * The reference to the heap information.
     * 
     * @return The reference to the heap information.
     */
    public ProcessorHeap getHeap() {
        return heap;
    }

    
    /**
     * This method performs the equals operation and returns TRUE if the objects
     * are equal.
     * 
     * @param obj The object to peform the equals on.
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
        final ProcessorMemoryManager other = (ProcessorMemoryManager) obj;
        if ((this.guid == null) ? (other.guid != null) : !this.guid.equals(other.guid)) {
            return false;
        }
        return true;
    }

    
    
    /**
     * This method returns the hash code for this object.
     * 
     * @return The reference to the hashcode for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.guid != null ? this.guid.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string representation of the process memory manager
     * 
     * @return The reference to the guid.
     */
    @Override
    public String toString() {
        return "ProcessorMemoryManager{" + "guid=" + guid + ", codeSpace=" + codeSpace + ", heap=" + heap + ", stack=" + stack + ", currentStack=" + currentStack + '}';
    }

    
        
    
    
    
    
}
