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
 * TypeManager.java
 */
package com.rift.dipforge.ls.engine;

import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 * This is the manager of a particular type
 * 
 * @author brett chaldecott
 */
public interface TypeManager {
    
    /**
     * This method is called to determine if the type manager can handle the
     * specified annotation.
     * 
     * @param annotation The reference to the annotation.
     * @return The annotations to determine.
     * @throws EngineException 
     */
    public boolean canHandleAnnotation(LsAnnotation annotation)
            throws EngineException;
    
    
    /**
     * This method is called to process the annotation.
     * 
     * @param flow The workflow annotation.
     * @param annotation The annotation to process.
     * @param configParameters The source configuration parameters
     * @param envParameters The environmental parameters.
     * @throws EngineException 
     */
    public void processAnnotation(Workflow flow, 
            LsAnnotation annotation,
            Map configParameters, 
            Map envParameters) throws EngineException;
    
    
    /**
     * This method returns true if the type is managed by this object.
     * 
     * @param type The type to manage.
     * @return TRUE This method returns true if the manage type return true.
     * @throws EngineException 
     */
    public boolean manageType(Object type) throws EngineException;
    
    
    /**
     * This method creates a new stack entry on behalf of the call to handle a
     * call onto a non native method
     * 
     * @param parent The parent variable
     * @param variables The variables
     * @param callStatement The call statement
     * @param parameters The parameters
     * @return The process stack to execute the task.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForMethod(
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object variable, List parameters)
            throws EngineException;
    
    
    /**
     * This method creates a stack entry 
     * 
     * @param parent The reference to the parent
     * @param variables The variables
     * @param callStatement The call statement.
     * @param target The target
     * @param argument The argument.
     * @param assignmentValue The assignment value
     * @param hasAssignment If this has an assignment value.
     * @return The reference to the process stack entry.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForList(
            ProcessStackEntry parent, Map variables, CallStatement callStatement, 
            Object target, Object argument, Object assignmentValue,
            boolean hasAssignment)
            throws EngineException;
    
    
    /**
     * This method creates a stack entry to make the call onto the given variable
     * 
     * @param parent The parent
     * @param variables The variables.
     * @param target The target
     * @param assignmentValue The assignment value
     * @param hasAssignment if this argument has assignment 
     * @return The reference to the process stack
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForVariable(
            ProcessStackEntry parent, Map variables, CallStatement callStatement, 
            Object target, Object assignmentValue, boolean hasAssignment)
            throws EngineException;
}
