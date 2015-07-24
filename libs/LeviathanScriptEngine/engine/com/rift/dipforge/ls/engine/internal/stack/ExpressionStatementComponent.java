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
 * ExpressionStatementComponent.java
 */
// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Comparison;
import com.rift.dipforge.ls.parser.obj.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The expression statement component
 *
 * @author brett chaldecott
 */
public class ExpressionStatementComponent extends StatementComponentStackEntry {

    // private member variables
    private Expression expression;
    private Object value;
    private Object result;
    private String operation;
    private Comparison currentComparison;
    private List<Expression.ExpressionBlock> blocks =
            new ArrayList<Expression.ExpressionBlock>();

    /**
     * This constructor sets up the expression statement object.
     *
     * @param processorMemoryManager The processor memory manager.
     * @param parent
     * @param variables
     */
    public ExpressionStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, Expression expression) {
        super(processorMemoryManager, parent, variables);
        this.expression = expression;
        this.currentComparison = expression.getInitialValue();
        this.blocks.addAll(expression.getBlocks());
    }

    /**
     * This constructor sets up the expression statement component information.
     *
     * @param processorMemoryManager The process memory manager.
     * @param parent The reference to the parent.
     */
    public ExpressionStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Expression expression) {
        super(processorMemoryManager, parent);
        this.expression = expression;
        this.currentComparison = expression.getInitialValue();
        this.blocks.addAll(expression.getBlocks());
    }

    
    /**
     * This method sets up the execute method
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (currentComparison != null) {
            ComparisonExpressionEntry entry = new ComparisonExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentComparison);
            // set the current comparison = null
            currentComparison = null;
        } else {
            if (value == null && operation == null) {
                value = result;
            } else {
                if (!(value instanceof Boolean)) {
                    throw new EngineException(
                            "Performing boolean operation on none bool types");
                }
                Boolean lhs = (Boolean) value;
                if (!(operation.equals("&&") && !lhs.booleanValue())) {

                    if (!(result instanceof Boolean)) {
                        throw new EngineException(
                                "Performing boolean operation on none bool types");
                    }
                    Boolean rhs = (Boolean) result;
                    if (operation.equals("&&")) {
                        value = new Boolean(lhs.booleanValue() && rhs.booleanValue());
                    } else {
                        value = new Boolean(lhs.booleanValue() || rhs.booleanValue());
                    }
                }
            }
            result = null;
            if (blocks.size() == 0) {
                ProcessStackEntry assignment = 
                        (ProcessStackEntry)this.getParent();
                assignment.setResult(value);
                pop();
            } else {
                Expression.ExpressionBlock expression = this.blocks.remove(0);
                operation = expression.getOperation();
                currentComparison = expression.getStatement();
            }
        }
    }

    /**
     * This method is called to set the result.
     *
     * @param result The result
     * @throws EngineException
     */
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
}
