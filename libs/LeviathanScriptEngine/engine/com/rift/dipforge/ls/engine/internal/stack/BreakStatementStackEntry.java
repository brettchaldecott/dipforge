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
 * ContinueStatementStackEntry.java
 */

// pakage path
package com.rift.dipforge.ls.engine.internal.stack;


// imports
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import java.util.Map;

/**
 * This is the break statement
 * 
 * @author brett chaldecott
 */
public class BreakStatementStackEntry extends StatementStackEntry {

    /**
     * The constructor of the break statement.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     */
    public BreakStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent) {
        super(processorMemoryManager, parent);
    }

    
    /**
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        this.setVariable(Constants.BREAK, new Boolean(true));
        pop();
    }

    
    /**
     * This method sets up the result.
     * 
     * @param result The results of the call.
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
}
