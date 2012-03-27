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
 * BlockStackEntry.java
 */

// package statement component.
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import java.util.Map;

/**
 * This object represents a component of an expression
 * 
 * @author brett chaldecott
 */
public abstract class ExpressionEntryStatementComponent extends StatementComponentStackEntry {

    /**
     * The definition of an expression statement entry component.
     * 
     * @param processorMemoryManager The process memory manager.
     * @param parent The parent reference for this object.
     * @param variables The list of variables.
     */
    public ExpressionEntryStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables) {
        super(processorMemoryManager, parent, variables);
    }

    
    /**
     * The constructor that sets all parent variables.
     * 
     * @param processorMemoryManager The reference to the processor memory manager
     * @param parent The parent object reference.
     */
    public ExpressionEntryStatementComponent(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent) {
        super(processorMemoryManager, parent);
    }
    
    
}
