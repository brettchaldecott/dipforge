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
 * JavaReflectionTypeManager.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.type;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the Java Reflection stack
 * 
 * @author brett chaldecott
 */
public class JavaReflectionListStackEntry extends ProcessStackEntry {

    private CallStatement callStatement;
    private Object target;
    private Object argument;
    private Object assignmentValue;
    private boolean hasAssignment;
    
    /**
     * The constructor of the java reflection list stack entry
     * 
     * @param parent The reference to the parent.
     * @param variables The list of variables.
     * @param callStatement The call statement reference.
     * @param target The target to interact with.
     * @param argument The argument.
     * @param assignmentValue The assignment of the value.
     * @param hasAssignment has an assignment.
     */
    public JavaReflectionListStackEntry(
            ProcessStackEntry parent, Map variables, 
            CallStatement callStatement, Object target,
            Object argument, Object assignmentValue, boolean hasAssignment) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = target;
        this.argument = argument;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
    }

    
    /**
     * This method executes the method.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        Object variable = JavaReflectionUtil.getObject(target, 
                callStatement.getEntries().subList(1, callStatement.getEntries().size()));
        if (variable instanceof List) {
            List list = (List)variable;
            if (argument instanceof Number) {
                this.getParent().setResult(list.get(((Number)argument).intValue()));
            } else {
                this.getParent().setResult(list.get(Integer.parseInt(argument.toString())));
            }
            pop();
            return;
        } else if (variable instanceof Map) {
            Map map = (Map)variable;
            this.getParent().setResult(map.get(argument));
            pop();
            return;
        }
        throw new EngineException("Invalid type [" + 
                variable.getClass().getName() + 
                "] not supported must be either Map or List");
    }

    
    /**
     * This method sets the result;
     * 
     * @param result The result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
}
