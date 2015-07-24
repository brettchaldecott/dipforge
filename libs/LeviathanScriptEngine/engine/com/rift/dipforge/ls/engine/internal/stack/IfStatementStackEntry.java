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
 * IfStatementStackEntry.java
 */
// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.IfStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The object that is responsible for managing the if statement.
 *
 * @author brett chaldecott
 */
public class IfStatementStackEntry extends StatementStackEntry {

    // private member variables
    private IfStatement statement;
    private List<IfStatement.IfBlock> statements = new ArrayList<IfStatement.IfBlock>();
    private Boolean expressionResult = null;

    /**
     * This constructor sets up all the internal values.
     *
     * @param processorMemoryManager The reference to the process memory
     * manager.
     * @param parent The parent reference.
     * @param variables The list of variables.
     * @param statement The statement.
     */
    public IfStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, IfStatement statement) {
        super(processorMemoryManager, parent, variables);
        this.statement = statement;
        this.statements.addAll(statement.getBlocks());
    }

    /**
     * This method executes the if statement
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (statements.size() > 0) {
            IfStatement.IfBlock block = statements.get(0);
            if (expressionResult == null
                    && block.getType() != IfStatement.IfStatementType.ELSE) {
                ExpressionStatementComponent expression = new ExpressionStatementComponent(
                        this.getProcessorMemoryManager(), this,
                        block.getExpression());

            } else if (block.getType() != IfStatement.IfStatementType.ELSE
                    && expressionResult!= null && 
                    expressionResult.booleanValue()) {
                Map parameters = new HashMap();
                parameters.put(Constants.BLOCK, block.getBlock());
                BlockStackEntry stackEntry = new BlockStackEntry(
                        this.getProcessorMemoryManager(),this,parameters);
                statements.clear();
            } else if (block.getType() == IfStatement.IfStatementType.ELSE) {
                Map parameters = new HashMap();
                parameters.put(Constants.BLOCK, block.getBlock());
                BlockStackEntry stackEntry = new BlockStackEntry(
                        this.getProcessorMemoryManager(),this,parameters);
                statements.clear();
            } else {
                statements.remove(0);
            }
            expressionResult = null;
        } else {
            // simply pop the stack
            pop();
        }
    }

    /**
     * This method sets the results
     *
     * @param result
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (!(result instanceof Boolean)) {
            throw new InvalidTypeException("Expected a boolean got a : " + 
                    result.getClass().getName());
        }
        this.expressionResult = (Boolean)result;
    }
}
