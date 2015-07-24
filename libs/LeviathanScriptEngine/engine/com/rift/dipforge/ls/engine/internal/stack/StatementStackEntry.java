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
 * StatementStackEntry.java
 */

// package
package com.rift.dipforge.ls.engine.internal.stack;

// 
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import java.util.Map;

/**
 * This is the statement stack entry
 * 
 * @author brett chaldecott
 */
public abstract class StatementStackEntry extends ProcessStackEntry {

    
    /**
     * The statement stack entry
     * 
     * @param processorStack The stack reference.
     * @param parent The parent of this stack entry.
     * @param variables The list of variables.
     */
    public StatementStackEntry(ProcessorMemoryManager processorMemoryManager, 
            ProcessStackEntry parent, Map variables) {
        super(processorMemoryManager, parent, variables);
    }

    
    /**
     * The statement stack entry
     * 
     * @param processorMemoryManager
     * @param parent 
     */
    public StatementStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent) {
        super(processorMemoryManager, parent);
    }

    
    
}
