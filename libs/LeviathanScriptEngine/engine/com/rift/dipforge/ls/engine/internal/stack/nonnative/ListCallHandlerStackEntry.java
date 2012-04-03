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
 * ListCallHandlerStackEntry.java
 */

package com.rift.dipforge.ls.engine.internal.stack.nonnative;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.TypeManagerLookup;
import com.rift.dipforge.ls.engine.internal.stack.ExpressionStatementComponent;
import com.rift.dipforge.ls.engine.internal.stack.StatementStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsListArgument;
import java.util.Map;

/**
 * The list call handler stack entry
 * 
 * @author brett chaldecott
 */
public class ListCallHandlerStackEntry extends StatementStackEntry {
    
    // execute the expression
    private CallStatement callStatement;
    private Object variable;
    private Object assignmentValue;
    private boolean hasAssignment;
    private LsListArgument lsArgument;
    private Object callArgument;
    private boolean executed;
    private Object result;
    
    /**
     * This constructor sets up the call handler stack entry
     * 
     * @param processorMemoryManager The process memory manager.
     * @param parent The parent reference.
     */
    public ListCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, CallStatement callStatement,
            Object variable, Object assignmentValue, boolean hasAssignment) {
        super(processorMemoryManager, parent);
        this.callStatement = callStatement;
        this.variable = variable;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
        this.lsArgument = this.getListArgument(callStatement);
        
    }

    
    /**
     * This constructor is responsible for setting up the list call handler
     * 
     * @param processorMemoryManager The reference to the processor memory manager.
     * @param parent The reference to the parent.
     * @param variables The variables
     */
    public ListCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, 
            CallStatement callStatement, Object variable,
            Object assignmentValue, boolean hasAssignment) {
        super(processorMemoryManager, parent, variables);
        this.callStatement = callStatement;
        this.variable = variable;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
        this.lsArgument = this.getListArgument(callStatement);
        
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
     * The execute method.
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
        } else if (!executed) {
            // set the parent to null so the method will not recurse
            // into it variables for a look up.
            TypeManager manager = TypeManagerLookup.getInstance().
                    getManager(variable);
            ProcessStackEntry entry = manager.
                    createStackEntryForList(this,null, callStatement, 
                    variable, this.callArgument, this.assignmentValue,
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
     * @param result The reference othe result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (callArgument == null) {
            callArgument = result;
        } else {
            this.result = result;
        }
    }
    
}
