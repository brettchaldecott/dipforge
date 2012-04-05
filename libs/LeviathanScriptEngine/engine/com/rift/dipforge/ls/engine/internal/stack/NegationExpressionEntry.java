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
 * NegationExpressionEntry.java
 */


// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidOperationException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.IncrementStatement;
import com.rift.dipforge.ls.parser.obj.NegationStatement;
import java.util.Map;

/**
 * The negation expression entry
 *
 * @author brett chaldecott
 */
public class NegationExpressionEntry extends ExpressionEntryStatementComponent {

    // private memeber variables
    private NegationStatement negationStatement;
    private Object value;
    private Object result;
    private Boolean negation;
    private Object currentTerm;

    /**
     * This constructor sets up all internal values besides the variable map.
     *
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     * @param negationStatement The negation statement.
     */
    public NegationExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, NegationStatement negationStatement) {
        super(processorMemoryManager, parent);
        this.negationStatement = negationStatement;
        this.negation = negationStatement.isNegation();
        this.currentTerm = negationStatement.getTerm();
    }
    

    /**
     * This constructor sets up the negation expression entry.
     *
     * @param processorMemoryManager
     * @param parent
     * @param variables
     * @param negationStatement
     */
    public NegationExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            NegationStatement negationStatement) {
        super(processorMemoryManager, parent, variables);
        this.negationStatement = negationStatement;
        this.negation = negationStatement.isNegation();
        this.currentTerm = negationStatement.getTerm();
    }

    
    /**
     * This method is used to execute the negation expression.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (currentTerm != null) {
            TermExpressionEntry entry = new TermExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentTerm);
            // set the current comparison = null
            currentTerm = null;
        } else {
            if (result != null) {
                value = result;
                if (negation) {
                    if (value instanceof Boolean) {
                        value = !((Boolean) value).booleanValue();
                    } else {
                        throw new EngineException(
                                "Cannot perform negation on a none boolean type");
                    }
                }
            }
            currentTerm = null;
            result = null;
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(value);
            pop();
        }
    }

    /**
     * This method sets the result.
     *
     * @param result The result of this operation.
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
}
