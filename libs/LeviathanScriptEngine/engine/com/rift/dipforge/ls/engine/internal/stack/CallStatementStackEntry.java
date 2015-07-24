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

// the package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.internal.NoSuchMethod;
import com.rift.dipforge.ls.engine.internal.NoSuchVariable;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.stack.nonnative.ListCallHandlerStackEntry;
import com.rift.dipforge.ls.engine.internal.stack.nonnative.MethodCallHandlerStackEntry;
import com.rift.dipforge.ls.engine.internal.stack.nonnative.VarCallHandlerStackEntry;
import com.rift.dipforge.ls.parser.obj.*;
import java.util.Map;


/**
 * The call statement stack entry.
 * 
 * @author brett chaldecott
 */
public class CallStatementStackEntry extends StatementStackEntry {

    // private member variables
    private CallStatement callStatement;
    private Assignment assignment;
    private Object value;
    private Object result;
    private Object initialValue;
    private boolean setInitialValue = false;
    
    /**
     * The constructor of the call statement
     * 
     * @param processorMemoryManager The reference to the processor memory manager.
     * @param parent The parent reference.
     * @param callStatement The call statement reference.
     */
    public CallStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, CallStatement callStatement) {
        super(processorMemoryManager, parent);
        this.callStatement = callStatement;
        this.assignment = callStatement.getAssignment();
    }
    
    
    /**
     * The call statement stack entry.
     * 
     * @param processorMemoryManager The reference to the processor
     * @param parent
     * @param variables
     * @param callStatement 
     */
    public CallStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement) {
        super(processorMemoryManager, parent, variables);
        this.callStatement = callStatement;
        this.assignment = callStatement.getAssignment();
    }
    
    
    /**
     * This method executes the call statement.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (this.assignment != null && !setInitialValue) {
            new AssignmentStatementComponent(this.getProcessorMemoryManager(),
                    this, this.assignment);
        } else if (callStatement != null) {
            // check for method
            String var = callStatement.getEntries().get(0).getName();
            if (isMethodCall()) {
                if (callStatement.getEntries().size() > 1) {
                    // handle none native type method call
                    Object variable = this.getVariable(var);
                    MethodCallHandlerStackEntry entry = new
                            MethodCallHandlerStackEntry(
                                this.getProcessorMemoryManager(),this, 
                                null,callStatement, variable);
                } else {
                    MethodDefinition method = getMethod(var);
                    if (method != null) {
                        // and an internal method call
                        MethodStackEntry entry = new MethodStackEntry(
                                this.getProcessorMemoryManager(),this,
                                callStatement,method);
                    } else {
                        // handle the exception
                        throw new NoSuchMethod("The method [" + var + 
                                "] does not exist");
                    }
                }
            } else if (isListCall()) {
                if (!this.containsVariable(var)) {
                    throw new NoSuchVariable("Fatal error no such variable [" +
                            var + "]");
                }
                Object variable = this.getVariable(var);
                if (callStatement.getEntries().size() > 1) {
                    ListCallHandlerStackEntry entry = new 
                            ListCallHandlerStackEntry(
                            this.getProcessorMemoryManager(),this,
                            callStatement,variable, initialValue,
                            this.setInitialValue);
                } else {
                    // handle an internal type list call
                    LsListCallStackEntry entry = new LsListCallStackEntry(
                        this.getProcessorMemoryManager(),this,
                        callStatement, initialValue, this.setInitialValue);
                }
            } else {
                if (!this.containsVariable(var)) {
                    throw new NoSuchVariable("Fatal error no such variable [" +
                            var + "]");
                }
                if (callStatement.getEntries().size() > 1) {
                    Object variable = this.getVariable(var);
                    VarCallHandlerStackEntry entry = new VarCallHandlerStackEntry(
                            this.getProcessorMemoryManager(),this,
                            callStatement,variable, initialValue, 
                            this.setInitialValue);
                } else {
                    if (this.setInitialValue) {
                        this.setVariable(var, this.initialValue);
                    }
                    Object variable = this.getVariable(var);
                    this.setResult(variable);
                }
            }
            
            // set the current comparison = null
            callStatement = null;
        } else {
            value = result;
            result = null;
            if (!(this.getParent() instanceof BlockStackEntry) &&
                    !(this.getParent() instanceof ForStatementStackEntry)) {
                ProcessStackEntry assignment =
                        (ProcessStackEntry) this.getParent();
                assignment.setResult(value);
            }
            pop();
        }
    }
    
    
    /**
     * This method returns true if this is a method call.
     * 
     * @return TRUE if method call FALSE if not.
     * @exception EngineException
     */
    private boolean isMethodCall() throws EngineException {
        CallStatement.CallStatementEntry entry = 
                this.callStatement.getEntries().get(
                this.callStatement.getEntries().size() - 1);
        if (entry.getArgument() != null && 
                entry.getArgument() instanceof ParameterArgument) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method returns true if this is a list call
     * 
     * @return TRUE if a list call.
     * @throws EngineException 
     */
    private boolean isListCall() throws EngineException {
        CallStatement.CallStatementEntry entry = 
                this.callStatement.getEntries().get(
                this.callStatement.getEntries().size() - 1);
        if (entry.getArgument() != null && 
                entry.getArgument() instanceof LsListArgument) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method returns the method definition.
     * 
     * @return The reference to the method definition
     */
    private MethodDefinition getMethod(String methodName) throws EngineException {
        if (this.callStatement.getEntries().size() > 1) {
            return null;
        }
        for (Statement statement: 
                this.getProcessorMemoryManager().getCodeSpace().getStatements()) {
            if (statement instanceof MethodDefinition) {
                MethodDefinition method = (MethodDefinition)statement;
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
        }
        return null;
    }
    
    
    /**
     * This method sets the result
     * 
     * @param result The reference to the result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (this.assignment != null && !setInitialValue) {
            setInitialValue = true;
            this.initialValue = result; 
        } else {
            this.getProcessorMemoryManager()
                    .setState(LeviathanConstants.Status.RUNNING);
            this.result = result;
        }
    }
    
}
