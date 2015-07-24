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
 * AssignmentStatementComponent.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Assignment;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.LsList;
import java.util.Map;

/**
 * This method sets up the assignment statement.
 * 
 * @author brett chaldecott
 */
public class AssignmentStatementComponent extends StatementComponentStackEntry {
    
    // the private member variables
    private Assignment assignment;
    private Object result;
    private boolean set;
    
    
    /**
     * This constructor sets up the statement component information.
     * 
     * @param processorMemoryManager The reference to the processor memory manager
     * @param parent The parent variable.
     * @param variables The variables.
     * @param assignment The reference to the assignment
     */
    public AssignmentStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            Assignment assignment) {
        super(processorMemoryManager, parent, variables);
        this.assignment = assignment;
    }
    
    
    /**
     * This method sets the assignment statement component.
     * 
     * @param processorMemoryManager The process memory manager.
     * @param parent The reference to this stack entries parent.
     * @param assignment The reference to the assignment
     */
    public AssignmentStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent,
            Assignment assignment) {
        super(processorMemoryManager, parent);
        this.assignment = assignment;
    }

    
    /**
     * This method executes the assignment statement.
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (set) {
            ProcessStackEntry entry = (ProcessStackEntry)this.getParent();
            entry.setResult(result);
            this.pop();
        } else {
            if (this.assignment.getValue() instanceof Expression) {
                ExpressionStatementComponent expression = new 
                        ExpressionStatementComponent(
                        this.getProcessorMemoryManager(),this, 
                        (Expression)this.assignment.getValue());
            } else if (this.assignment.getValue() instanceof LsList) {
                LsListStackEntry listStack = new 
                        LsListStackEntry(this.getProcessorMemoryManager(),
                        this, (LsList)this.assignment.getValue());
            }
        }
    }
    
    
    /**
     * This method is called to set the result.
     * 
     * @param result The reference to the result
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.set = true;
        this.result = result;
    }
}
