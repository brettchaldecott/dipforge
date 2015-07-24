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
 * JavaReflectionTypeManager.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.type;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.Map;

/**
 * The implementation of the Java Reflection stack
 * 
 * @author brett chaldecott
 */
public class JavaReflectionVariableStackEntry extends ProcessStackEntry {
    
    // private member variables
    private CallStatement callStatement;
    private Object target;
    private Object assignmentValue;
    private boolean hasAssignment;
    
    /**
     * The constructor of the java reflection variable stack object
     * 
     * @param processorMemoryManager
     * @param parent
     * @param variables
     * @param callStatement
     * @param target
     * @param assignmentValue
     * @param hasAssignment 
     */
    public JavaReflectionVariableStackEntry(
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object target,
            Object assignmentValue, boolean hasAssignment) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = target;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
    }

    
    /**
     * This method executes the reflection call onto a variable
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        this.getParent().setResult(
                JavaReflectionUtil.getObject(target, 
                callStatement.getEntries().subList(1, callStatement.getEntries().size())));
        pop();
    }
    
    
    /**
     * This method sets the result
     * @param result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
}
