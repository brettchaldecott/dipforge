/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  2015 Burntjam
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
 * VarCallHandlerStackEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack.nonnative;

// private member variables
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.TypeManagerLookup;
import com.rift.dipforge.ls.engine.internal.stack.StatementStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.Map;

/**
 * This is the var call handler
 * 
 * @author brett chaldecott
 */
public class VarCallHandlerStackEntry extends StatementStackEntry {

    // private member variables
    private CallStatement callStatement;
    private Object variable;
    private Object assignmentValue;
    private boolean hasAssignment;
    private boolean executed = false;
    private Object result;
    
    /**
     * The var call handler stack entry
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The reference to the parent object.
     * @param callStatement The call that this object is handling
     * @param variable The variable that is being accessed
     * @param assignmentValue The new value
     * @param hasAssignment If an assignment is being called.
     */
    public VarCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, CallStatement callStatement,
            Object variable, Object assignmentValue, boolean hasAssignment) {
        super(processorMemoryManager, parent);
        this.callStatement = callStatement;
        this.variable = variable;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
    }

    
    /**
     * This constructor sets up all the variables managed by this object.
     * 
     * @param processorMemoryManager The reference to the processor memory manager.
     * @param parent The reference to the parent
     * @param variables The variables passed in for the object.
     * @param callStatement The call statement.
     * @param variable The variable that is being managed.
     * @param assignmentValue The assignment value.
     * @param hasAssignment If the value within the variable is to be replaced.
     */
    public VarCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object variable, Object assignmentValue, boolean hasAssignment) {
        super(processorMemoryManager, parent, variables);
        this.callStatement = callStatement;
        this.variable = variable;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
    }
    
    
    /**
     * This method executes the var call.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (!executed) {
            // set the parent to null so the method will not recurse
            // into it variables for a look up.
            TypeManager manager = TypeManagerLookup.getInstance().
                    getManager(variable);
            ProcessStackEntry entry = manager.createStackEntryForVariable
                    (this,null, callStatement, 
                    variable, this.assignmentValue,
                    this.hasAssignment);
            executed = true;
        } else if (executed) {
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(result);
            pop();
        }
    }
    
    
    /**
     * This method sets the result.
     * 
     * @param result The result of this call
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
    
}
