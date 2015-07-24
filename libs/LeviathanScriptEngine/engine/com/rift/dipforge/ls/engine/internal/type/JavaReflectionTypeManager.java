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
package com.rift.dipforge.ls.engine.internal.type;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.InvalidParameterException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.util.VariableUtil;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brett
 */
public class JavaReflectionTypeManager implements TypeManager {

    /**
     * The default constructor
     */
    public JavaReflectionTypeManager() {
    }

    /**
     * The manage type information.
     *
     * @param type type to perform the check on.
     * @return TRUE if the type can be managed,
     * @throws EngineException
     */
    @Override
    public boolean manageType(Object type) throws EngineException {
        return true;
    }

    
    /**
     * This method returns true if the object can process the annotation.
     * 
     * @param annotation The annotation to process.
     * @return
     * @throws EngineException 
     */
    @Override
    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        boolean result = annotation.getName().equalsIgnoreCase("java");
        if (result && annotation.getList().size() < 1) {
            throw new EngineException(
                    "The annotation is corrupt and must provide atleast one parameter");
        }
        return result;
    }

    
    /**
     * This method is called to process the annotation
     * 
     * @param annotation
     * @param configParameters
     * @param envParameters
     * @throws EngineException 
     */
    @Override
    public void processAnnotation(
            Workflow flow,
            LsAnnotation annotation, 
            Map configParameters,
            Map envParameters) throws EngineException {
        for (String key : annotation.getList()) {
            if (!configParameters.containsKey(key)) {
                throw new InvalidParameterException(
                        "The configuration parameter [" + key + "] was not found");
            }
            if (!VariableUtil.containsHeapVariable(flow, key)) {
                throw new InvalidParameterException(
                        "A variable in the flow by the name of [" + key + "] was not found");
            }
            envParameters.put(key, configParameters.get(key));
        }
    }
    
    
    
    
    
    
    /**
     * This method creates a stack entry
     * 
     * @param parent The reference to the parent
     * @param variables The variables
     * @param callStatement The call statement
     * @param parameters The parameters for the call statement
     * @return The reference to the stack to handle the call.
     * @throws EngineException 
     */
    @Override
    public ProcessStackEntry createStackEntryForMethod(
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object variable, List parameters)
            throws EngineException {
        return new JavaReflectionMethodStackEntry(parent,variables,
                callStatement, variable, parameters);
    }

    
    /**
     * This method creates a stack entry for the list.
     * @param parent The parent reference.
     * @param variables The list of variables
     * @param callStatement The call statement
     * @param target The target
     * @param argument The argument
     * @param assignmentValue The assignment values
     * @param hasAssignment has the assignment
     * @return The reference to the stack
     * @throws EngineException 
     */
    @Override
    public ProcessStackEntry createStackEntryForList(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            Object argument, Object assignmentValue, boolean hasAssignment)
            throws EngineException {
        return new JavaReflectionListStackEntry(parent, variables,
                callStatement, target,argument, assignmentValue, hasAssignment);
    }
    
    
    /**
     * This method creates a stack entry
     * 
     * @param parent The reference to the parent
     * @param variables The list of variables
     * @param callStatement The call statement
     * @param target The target of the call
     * @param assignmentValue The assignment value
     * @param hasAssignment Hash the assignment
     * @return Reference to the stack
     * @throws EngineException 
     */
    @Override
    public ProcessStackEntry createStackEntryForVariable(
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object target, Object assignmentValue,
            boolean hasAssignment) throws EngineException {
        return new JavaReflectionVariableStackEntry(parent, variables,
                callStatement, target,assignmentValue, hasAssignment);
    }
}
