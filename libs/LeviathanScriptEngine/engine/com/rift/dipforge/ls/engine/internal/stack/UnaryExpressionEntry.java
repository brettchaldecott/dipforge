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
 * UnaryExpressionEntry.java
 */
// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidOperationException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.NegationStatement;
import com.rift.dipforge.ls.parser.obj.UnaryStatement;
import java.util.Map;

/**
 * This object manages a unary statement.
 *
 * @author brett chaldecott
 */
public class UnaryExpressionEntry extends ExpressionEntryStatementComponent {

    // private member variables
    private UnaryStatement unaryStatement;
    private Object value;
    private Object result;
    private String operation;
    private NegationStatement currentNegation;

    /**
     * The constructor that sets up the unary expression
     *
     * @param processorMemoryManager The
     * @param parent The parent reference
     * @param unaryStatement The unary statement.
     */
    public UnaryExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, UnaryStatement unaryStatement) {
        super(processorMemoryManager, parent);
        this.unaryStatement = unaryStatement;
        this.currentNegation = unaryStatement.getNegation();
        this.operation = unaryStatement.getOperation();
    }

    /**
     * This constructor sets up the unary expression entry.
     *
     * @param processorMemoryManager
     * @param parent
     * @param variables
     */
    public UnaryExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            UnaryStatement unaryStatement) {
        super(processorMemoryManager, parent, variables);
        this.unaryStatement = unaryStatement;
        this.currentNegation = unaryStatement.getNegation();
        this.operation = unaryStatement.getOperation();
    }

    /**
     * This is the execute statement
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (currentNegation != null) {
            NegationExpressionEntry entry = new NegationExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentNegation);
            // set the current comparison = null
            currentNegation = null;
        } else {
            if (result != null) {
                value = result;
                if (operation != null) {
                    if ((value instanceof Number)) {
                        throw new InvalidOperationException(
                                "Cannot perform a unary operation on a none numeric value");
                    }
                    Number lhs = (Number) value;
                    if (operation.equals("-")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte) (lhs.byteValue() * -1));
                        } else if (value instanceof Double) {
                            value = new Double((double) (lhs.doubleValue() * -1));
                        } else if (value instanceof Float) {
                            value = new Float((float) (lhs.floatValue() * -1));
                        } else if (value instanceof Integer) {
                            value = new Integer((int) (lhs.intValue() * -1));
                        } else if (value instanceof Long) {
                            value = new Long((long) (lhs.longValue() * -1));
                        } else if (value instanceof Short) {
                            value = new Short((short) (lhs.shortValue() * -1));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte) (lhs.byteValue() * +1));
                        } else if (value instanceof Double) {
                            value = new Double((double) (lhs.doubleValue() * +1));
                        } else if (value instanceof Float) {
                            value = new Float((float) (lhs.floatValue() * +1));
                        } else if (value instanceof Integer) {
                            value = new Integer((int) (lhs.intValue() * +1));
                        } else if (value instanceof Long) {
                            value = new Long((long) (lhs.longValue() * +1));
                        } else if (value instanceof Short) {
                            value = new Short((short) (lhs.shortValue() * +1));
                        }
                    }
                }
            }
            currentNegation = null;
            result = null;
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(value);
            pop();
        }
    }

    /**
     * This method sets the result value.
     *
     * @param result The result
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
}
