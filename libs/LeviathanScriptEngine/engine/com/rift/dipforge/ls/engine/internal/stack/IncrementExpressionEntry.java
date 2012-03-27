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
 * IncrementExpressionEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.IncrementStatement;
import java.util.Map;

/**
 * This method is used to increment the expression information.
 *
 * @author brett chaldecott
 */
public class IncrementExpressionEntry extends ExpressionEntryStatementComponent {

    // private member variables
    private IncrementStatement incrementStatement;
    private Object value;
    private Object result;
    private String operation;
    private Object currentTerm;

    /**
     * This method increments the expression entry information.
     *
     * @param processorMemoryManager The processor memory manager
     * @param parent
     * @param currentIncrement The current increment method.
     */
    public IncrementExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, IncrementStatement incrementStatement) {
        super(processorMemoryManager, parent);
        this.incrementStatement = incrementStatement;
        this.operation = incrementStatement.getOperation();
        this.currentTerm = incrementStatement.getValue();
    }

    /**
     * This method increments the expression entry information.
     *
     * @param processorMemoryManager The processor memory manager
     * @param parent
     * @param currentIncrement The current increment method.
     */
    public IncrementExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            IncrementStatement incrementStatement) {
        super(processorMemoryManager, parent, variables);
        this.incrementStatement = incrementStatement;
        this.operation = incrementStatement.getOperation();
        this.currentTerm = incrementStatement.getValue();
    }
    

    /**
     * This method is called to execute the increment expression.
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
        } else if (result != null) {
            if (result != null) {
                value = result;
                if (operation != null && operation.length() > 0) {
                    Number lhs = (Number) value;
                    if (operation.equals("++")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte) (lhs.byteValue() + 1));
                        } else if (value instanceof Double) {
                            value = new Double((double) (lhs.doubleValue() + 1));
                        } else if (value instanceof Float) {
                            value = new Float((float) (lhs.floatValue() + 1));
                        } else if (value instanceof Integer) {
                            value = new Integer((int) (lhs.intValue() + 1));
                        } else if (value instanceof Long) {
                            value = new Long((long) (lhs.longValue() + 1));
                        } else if (value instanceof Short) {
                            value = new Short((short) (lhs.shortValue() + 1));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte) (lhs.byteValue() - 1));
                        } else if (value instanceof Double) {
                            value = new Double((double) (lhs.doubleValue() - 1));
                        } else if (value instanceof Float) {
                            value = new Float((float) (lhs.floatValue() - 1));
                        } else if (value instanceof Integer) {
                            value = new Integer((int) (lhs.intValue() - 1));
                        } else if (value instanceof Long) {
                            value = new Long((long) (lhs.longValue() - 1));
                        } else if (value instanceof Short) {
                            value = new Short((short) (lhs.shortValue() - 1));
                        }
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
     * @param result The result to set
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
}
