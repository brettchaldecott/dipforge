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
 * IfStatementStackEntry.java
 */
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.ForStatement;
import com.rift.dipforge.ls.parser.obj.IncrementStatement;
import com.rift.dipforge.ls.parser.obj.Variable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author brett chaldecott
 */
public class ForStatementStackEntry extends StatementStackEntry {

    enum ForOp {

        INIT,
        COMP,
        INC,
        BLOCK
    };
    // private member variables.
    private ForStatement statement;
    private ForOp status;
    private Boolean result;
    private IncrementStatement increment;
    private CallStatement callStatement;

    /**
     * The constructor that initializes all values.
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The parent reference.
     * @param statement The statement reference.
     */
    public ForStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent,
            ForStatement statement) throws EngineException {
        super(processorMemoryManager, parent);
        this.statement = statement;
        status = ForOp.INIT;
        this.addVariable(Constants.CONTINUE, null);
        this.addVariable(Constants.BREAK, null);
        this.increment = statement.getIncrement();
        this.callStatement = statement.getCall();
    }
    

    /**
     * This method is called to execute
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (StackUtil.loopPopStack(this)) {
            pop();
        } else if (status == ForOp.INIT) {
            if (this.statement.getInitialValue() != null) {
                VariableStackEntry stackEntry = new VariableStackEntry(
                        this.getProcessorMemoryManager(), this,
                        statement.getInitialValue());
            }
            status = ForOp.COMP;
        } else if (status == ForOp.COMP) {
            ExpressionStatementComponent expression = new ExpressionStatementComponent(
                    this.getProcessorMemoryManager(), this,
                    statement.getComparison());
            status = ForOp.BLOCK;
        } else if (status == ForOp.INC) {
            if (this.increment != null) {
                IncrementStatementStackEntry stackEntry =
                        new IncrementStatementStackEntry(
                        this.getProcessorMemoryManager(),this,
                        increment);
            } else {
                CallStatementStackEntry stackEntry = new CallStatementStackEntry(
                        this.getProcessorMemoryManager(),this, null,
                        callStatement);
            }
            status = ForOp.COMP;
        } else if (status == ForOp.BLOCK) {
            if (result.booleanValue()) {
                this.setVariable(Constants.CONTINUE, null);
                Map parameters = new HashMap();
                parameters.put(Constants.BLOCK, this.statement.getChild());
                BlockStackEntry stackEntry = new BlockStackEntry(
                        this.getProcessorMemoryManager(), this, parameters);
                status = ForOp.INC;
            } else {
                pop();
            }
        }
    }

    /**
     * This method sets the result.
     *
     * @param result The reference to the result.
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (status == ForOp.BLOCK) {
            if (result instanceof Boolean) {
                this.result = (Boolean) result;
            } else if (result != null) {
                throw new InvalidTypeException("Expected a boolean type, receieved : "
                        + result.getClass().getName());
            } else {
                this.result = new Boolean(false);
            }
        }
    }
}
