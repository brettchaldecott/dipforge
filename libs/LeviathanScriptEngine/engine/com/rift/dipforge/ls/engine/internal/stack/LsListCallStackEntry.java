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
 * LsListCallStackEntry.java
 */
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.NoSuchVariable;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.LsListArgument;
import java.util.List;
import java.util.Map;

/**
 * This object is the ls list stack entry
 *
 * @author brett chaldecott
 */
public class LsListCallStackEntry extends StatementStackEntry {

    // the private member variables
    private CallStatement callStatement;
    private LsListArgument lsArgument;
    private List listVar;
    private Map mapVar;
    private Object result;
    private Object assignmentValue;
    private boolean hasAssignment;

    /**
     * This constructor lists stack entry information.
     *
     * @param processorMemoryManager The process memory manager.
     * @param parent The reference to parent.
     * @param callStatement The call statement.
     */
    public LsListCallStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, CallStatement callStatement,
            Object assignmentValue, boolean hasAssignment)
            throws EngineException {
        super(processorMemoryManager, parent);
        this.callStatement = callStatement;
        this.lsArgument = this.getListArgument(callStatement);
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
        getVariable(parent,callStatement);
    }

    /**
     * This is the constructor that sets up the list information.
     *
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     * @param variables The list of variables.
     */
    public LsListCallStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement,
            Object assignmentValue, boolean hasAssignment) throws EngineException {
        super(processorMemoryManager, parent, variables);
        this.callStatement = callStatement;
        this.lsArgument = this.getListArgument(callStatement);
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
        getVariable(parent,callStatement);
    }
    
    
    /**
     * This method returns the list argument for the current call.
     *
     * @param callStatement
     * @return The reference to the ls list argument.
     */
    private LsListArgument getListArgument(CallStatement callStatement) {
        return (LsListArgument) callStatement.getEntries().
                get(callStatement.getEntries().size() - 1).getArgument();
    }

    /**
     * This method gets the appropriate variable reference.
     *
     * @param parent The parent to retrieve the variable from
     * @param callStatement Call containing the variable name
     */
    private void getVariable(ProcessStackEntry parent,
            CallStatement callStatement) throws EngineException {
        String name = null;
        for (CallStatement.CallStatementEntry entry : callStatement.getEntries()) {
            if (name == null) {
                name = entry.getName();
            } else {
                name += "." + entry.getName();
            }
        }
        Object variable = parent.getVariable(name);
        if (variable == null) {
            throw new NoSuchVariable("No such variable [" + name + "]");
        }
        if (variable instanceof Map) {
            this.mapVar = (Map) variable;
        } else if (variable instanceof List) {
            this.listVar = (List) variable;
        } else {
            throw new InvalidTypeException("The variable [" + name + "]["
                    + variable.getClass().getName() + "]");
        }
    }

    
    /**
     * This method executes the list stack entry object.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (this.lsArgument != null) {
            ExpressionStatementComponent expression = new ExpressionStatementComponent(
                    this.getProcessorMemoryManager(), this,
                    lsArgument.getExpression());
            this.lsArgument = null;
        } else {
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(result);
            pop();
        }
    }

    /**
     * This method is called by the stacks child to set the result.
     *
     * @param result The result key
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (mapVar != null) {
            if (this.hasAssignment) {
                mapVar.put(result,this.assignmentValue);
            }
            this.result = mapVar.get(result);
        } else if (listVar != null && result instanceof String) {
            Integer key = Integer.parseInt((String)result);
            if (this.hasAssignment) {
                listVar.set(key, this.assignmentValue);
            }
            this.result = listVar.get(key);
        } else if (listVar != null && result instanceof Number) {
            Integer key = ((Number) result).intValue();
            if (this.hasAssignment) {
                listVar.set(key, this.assignmentValue);
            }
            this.result = listVar.get(key);
        } else if (listVar == null ) {
            throw new InvalidTypeException("Stack not instanciated properly ["
                    + result.getClass().getName() + "]");
        } else {
            throw new InvalidTypeException("Cannot access list with type of ["
                    + result.getClass().getName() + "]");
        }
    }
}
