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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the Java Reflection stack
 * 
 * @author brett chaldecott
 */
public class JavaReflectionMethodStackEntry extends ProcessStackEntry {

    // private memember variables
    private CallStatement callStatement;
    private Object target;
    private List parameters = new ArrayList();
    private List<CallStatement.CallStatementEntry> entries = new ArrayList<CallStatement.CallStatementEntry>();
    
    /**
     * The constructor for the java reflextion method stack entry
     * 
     * @param parent The reference to the stack pareent
     * @param variables The list of variables
     * @param callStatement The call statement.
     * @param parameters The list of parameters
     */
    public JavaReflectionMethodStackEntry(
            ProcessStackEntry parent,Map variables,
            CallStatement callStatement, Object target, List parameters) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = target;
        this.parameters.addAll(parameters);
        entries.addAll(callStatement.getEntries().subList(1, 
                callStatement.getEntries().size() -1));
    }
    
    
    /**
     * This method executes the method
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        Object target = JavaReflectionUtil.getObject(this.target, 
                callStatement.getEntries().subList(1,
                callStatement.getEntries().size() - 1));
        this.getParent().setResult(executeMethod(target, callStatement));
        pop();
    }

    
    /**
     * This method sets the reflection method stack entry.
     * 
     * @param result The result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
    
    /**
     * This method executes the method that matches the parameters.
     * 
     * @param target The target of the call.
     * @param callStatement The call statement.
     * @return The result of the call.
     * @throws EngineException 
     */
    private Object executeMethod(Object target, CallStatement callStatement)
            throws EngineException {
        try {
            CallStatement.CallStatementEntry entry = 
                    callStatement.getEntries().get(callStatement.getEntries().size() -1);
            Method[] methods = target.getClass().getMethods();
            for (Method method: methods) {
                if (methodsMatch(method,entry)) {
                    return method.invoke(target, parameters.toArray(new Object[0]));
                }
            }
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to execute the method because : " 
                    + ex.getMessage(),ex);
        }
        throw new java.lang.NoSuchMethodError(
                "No method matching the requirments was found");
    }
    
    
    /**
     * This method returns true if the methods match.
     * @param method TRUE if the methods match, FALSE if not.
     * @return
     * @throws EngineException 
     */
    private boolean methodsMatch(Method method,
            CallStatement.CallStatementEntry entry) throws EngineException {
        if (!method.getName().equals(entry.getName())) {
            return false;
        }
        if (method.getParameterTypes().length != parameters.size()) {
            return false;
        }
        Class[] parameterTypes = method.getParameterTypes();
        for (int index = 0; index < parameters.size(); index++) {
            Class parameterType = parameterTypes[index];
            Object parameterValue = parameters.get(index);
            if (!parameterType.isAssignableFrom(parameterValue.getClass())) {
                return false;
            }
        }
        
        for (int index = 0; index < parameters.size(); index++) {
            Class parameterType = parameterTypes[index];
            Object parameterValue = parameters.get(index);
            parameters.set(index, parameterType.cast(parameterValue));
        }
        return true;
    }
}
