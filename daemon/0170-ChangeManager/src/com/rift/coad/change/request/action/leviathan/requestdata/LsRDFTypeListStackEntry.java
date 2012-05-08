/*
 * ChangeControlManager: The manager for the change events.
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
 * LsRDFTypeListStackEntry.java
 */

// package path
package com.rift.coad.change.request.action.leviathan.requestdata;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.Map;


/**
 * This object manages an request types information.
 * 
 * @author brett chaldecott
 */
public class LsRDFTypeListStackEntry extends ProcessStackEntry {

    /**
     * The constructor responsible for setting up the basic values.
     * 
     * @param processorMemoryManager The reference to the processor memory
     * @param parent The parent reference
     * @param variables The list of variables.
     */
    public LsRDFTypeListStackEntry(ProcessStackEntry parent, Map variables, 
            CallStatement callStatement, Object target, Object argument,
            Object assignmentValue, boolean hasAssignment) {
        super(parent.getProcessorMemoryManager(), parent, variables);
    }
    
    
    /**
     * This constructor sets up the list of stack entries.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent reference.
     */
    public LsRDFTypeListStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent) {
        super(processorMemoryManager, parent);
    }

    
    /**
     * This method is called to execute the on this object. [Not supported at this point]
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method sets the result.
     * 
     * @param result The result reference.
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
