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
 * ContinueStatementStackEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.ReturnStatement;

/**
 * The returns statement stack entry
 * 
 * @author brett chaldecott
 */
public class ReturnStatementStackEntry extends StatementStackEntry {

    // private member variables
    private ReturnStatement statement;
    private boolean executed = false;
    
    /**
     * The constructor of the return statement
     * @param processorMemoryManager
     * @param parent 
     */
    public ReturnStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, ReturnStatement statement) {
        super(processorMemoryManager, parent);
        this.statement = statement;
    }

    
    /**
     * This method is called to execute
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (!executed) {
            ExpressionStatementComponent expression = new ExpressionStatementComponent(
                    this.getProcessorMemoryManager(), this,
                    statement.getExpression());
            executed = true;
        } else {
            pop();
        }
    }

    
    /**
     * This method sets the result.
     * 
     * @param result The result of the call.
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.setVariable(Constants.RETURN, new Boolean(true));
        this.setVariable(Constants.RESULT, result);
    }
    
}
