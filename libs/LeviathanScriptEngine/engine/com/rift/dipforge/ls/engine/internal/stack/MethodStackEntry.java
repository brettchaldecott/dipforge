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
 * CallStatementStackEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

// imports
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.util.VariableUtil;
import com.rift.dipforge.ls.parser.obj.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This object contains the method stack information.
 * 
 * @author brett chaldecott
 */
public class MethodStackEntry extends BlockStackEntry {

    // private member variables.
    private ProcessStackEntry caller;
    private MethodDefinition method;
    private boolean executed = false;
    private List<Variable> variables = new ArrayList<Variable>();
    private Variable currentVariable = null;
    private Map parameters = new HashMap();
    
    /**
     * The method stack entry information.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     * @param method The method that is being called 
     */
    public MethodStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry caller, 
            CallStatement callStatement,
            MethodDefinition method) throws EngineException {
        super(processorMemoryManager, caller);
        this.caller = caller;
        this.method = method;
        variables.addAll(VariableUtil.cloneVariables(
                callStatement,method));
    }

    
    /**
     * The constructor of the method stack entry.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param caller The caller.
     * @param variables The map of variables
     * @param method The method definition.
     */
    public MethodStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry caller, Map variables, 
            CallStatement callStatement,
            MethodDefinition method) throws EngineException {
        super(processorMemoryManager, caller, null);
        this.caller = caller;
        this.method = method;
        this.variables.addAll(VariableUtil.cloneVariables(
                callStatement,method));
    }
    
    
    /**
     * This method is called to execute this stack entry.
     * 
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        
        if (this.variables.size() > 0) {
            currentVariable = this.variables.remove(0);
            VariableStackEntry stackEntry = new VariableStackEntry(
                    this.getProcessorMemoryManager(),
                    this.getParent(), currentVariable);
        } else if (!executed) {
            // set the parent to null so the method will not recurse
            // into it variables for a look up.
            this.setParent(null);
            currentVariable = null;
            parameters.put(Constants.RESULT, null);
            parameters.put(Constants.BLOCK, method);
            BlockStackEntry entry = new 
                    BlockStackEntry(this.getProcessorMemoryManager(),
                    this, parameters);
            executed = true;
        } else if (executed) {
            ProcessStackEntry assignment =
                    (ProcessStackEntry) caller;
            assignment.setResult(parameters.get(Constants.RESULT));
            pop();
        }
        
    }

    
    /**
     * This method sets the result.
     * 
     * @param result The result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (currentVariable != null) {
            parameters.put(currentVariable.getName(), result);
        } else {
            parameters.put(Constants.RESULT, result);
        }
    }
    
}
