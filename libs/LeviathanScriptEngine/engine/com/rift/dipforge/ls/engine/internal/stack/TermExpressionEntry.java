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
 * TermExpressionEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.IncrementStatement;
import java.util.Map;

/**
 * The definition of the term expression entry
 * 
 * @author brett chaldecott
 */
public class TermExpressionEntry extends ExpressionEntryStatementComponent {
    
    // private member variables
    private Object term;
    private Object value;
    private Object result;

    
    /**
     * The term expression entry
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     * @param term The term that is being executed.
     */
    public TermExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Object term) {
        super(processorMemoryManager, parent);
        this.term = term;
    }

    
    /**
     * The constructor for the term expression entry.
     * 
     * @param processorMemoryManager The reference for the processor memory manager
     * @param parent The parent reference.
     * @param variables The variables reference.
     * @param term The term
     */
    public TermExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, Object term) {
        super(processorMemoryManager, parent, variables);
        this.term = term;
    }

    
    /**
     * This method is used to execute the term.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (term != null) {
            if (term instanceof CallStatement) {
                CallStatementStackEntry entry = new CallStatementStackEntry(
                    this.getProcessorMemoryManager(), this, 
                        (CallStatement)term);
            } else if (term instanceof Expression) {
                System.out.println("Execute the expression");
                ExpressionStatementComponent expression = new 
                    ExpressionStatementComponent(
                    this.getProcessorMemoryManager(),this, 
                    (Expression)term);
            } else {
                result = term;
            }
            // set the current comparison = null
            term = null;
        } else {
            value = result;
            result = null;
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(value);
            pop();
        }
    }
    
    
    /**
     * This method is called to set the result.
     * 
     * @param result The result value
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
    
}
