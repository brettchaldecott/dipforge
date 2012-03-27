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

// the package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.Expression;
import java.util.Map;

/**
 *
 * @author brett chaldecott
 */
public class CallStatementStackEntry extends StatementStackEntry {

    // private member variables
    private CallStatement callStatement;
    private Object value;
    private Object result;
    
    /**
     * The constructor of the call statement
     * 
     * @param processorMemoryManager The reference to the processor memory manager.
     * @param parent The parent reference.
     * @param callStatement The call statement reference.
     */
    public CallStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, CallStatement callStatement) {
        super(processorMemoryManager, parent);
        this.callStatement = callStatement;
    }
    
    
    /**
     * The call statement stack entry.
     * 
     * @param processorMemoryManager The reference to the processor
     * @param parent
     * @param variables
     * @param callStatement 
     */
    public CallStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement) {
        super(processorMemoryManager, parent, variables);
        this.callStatement = callStatement;
    }
    
    
    /**
     * This method executes the call statement.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (callStatement != null) {
            // TODO: implement the call statement
            
            // set the current comparison = null
            callStatement = null;
        } else if (result != null) {
            value = result;
            result = null;
            ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
            assignment.setResult(value);
            pop();
        }
    }
    
    
    /**
     * This method sets the result
     * 
     * @param result The reference to the result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.getProcessorMemoryManager()
                .setState(LeviathanConstants.Status.RUNNING);
        this.result = result;
    }
    
}
