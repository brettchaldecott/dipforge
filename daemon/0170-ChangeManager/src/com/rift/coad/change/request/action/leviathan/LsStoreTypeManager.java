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
 * LsRDFTypeManager.java
 */


package com.rift.coad.change.request.action.leviathan;

import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFProperty;
import com.rift.coad.change.request.action.leviathan.store.LsStoreMethodStackEntry;
import com.rift.coad.change.request.action.leviathan.store.LsStoreProperty;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the LsStoreTypeManager
 * 
 * @author brett chaldecott
 */
public class LsStoreTypeManager implements TypeManager {

    /**
     * The default constructor of the ls store type manager
     */
    public LsStoreTypeManager() {
    }
    
    
    /**
     * This method returns true if the annotation can be managed by this object.
     * 
     * @param annotation The reference to the annotation to process.
     * @return returns TRUE if the annotation can be handled
     * @throws EngineException 
     */
    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        boolean result = annotation.getName().equalsIgnoreCase("store");
        if (result && annotation.getList().size() != 1) {
            throw new EngineException(
                    "The annotation is corrupt and must provide one parameters [name] got [" +
                    annotation.getList().size() + "]");
        }
        return result;
    }

    
    /**
     * This method is called to process the annotation.
     * 
     * @param flow The reference to the flow.
     * @param annotation The annotation
     * @param configParameters The config parameters.
     * @param envParameters The environment.
     * @throws EngineException 
     */
    public void processAnnotation(Workflow flow, LsAnnotation annotation, Map configParameters, Map envParameters) throws EngineException {
        String name = annotation.getList().get(0);
        envParameters.put(name, new LsStoreProperty(name));
    }

    
    /**
     * This method is called to determine if this type manager manages the given type.
     * 
     * @param type The type to perform the check on.
     * @return TRUE if the type is managed by this object.
     * @throws EngineException 
     */
    public boolean manageType(Object type) throws EngineException {
        if (type instanceof LsStoreProperty) {
            return true;
        }
        return false;
    }

    
    /**
     * This method creates a new stack entry for method
     * @param parent
     * @param variables
     * @param callStatement
     * @param variable
     * @param parameters
     * @return
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForMethod(
            ProcessStackEntry parent, Map variables, 
            CallStatement callStatement, Object variable, List parameters)
            throws EngineException {
        return new LsStoreMethodStackEntry(parent,variables, callStatement, variable,
            parameters);
    }

    
    /**
     * This method is not supported by this type.
     * 
     * @param parent
     * @param variables
     * @param callStatement
     * @param target
     * @param argument
     * @param assignmentValue
     * @param hasAssignment
     * @return
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForList(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            Object argument, Object assignmentValue, boolean hasAssignment)
            throws EngineException {
        throw new UnsupportedOperationException("Not supported.");
    }

    
    /**
     * THis method is not supported by this type.
     * @param parent
     * @param variables
     * @param callStatement
     * @param target
     * @param assignmentValue
     * @param hasAssignment
     * @return
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForVariable(
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object target,
            Object assignmentValue, boolean hasAssignment) throws EngineException {
        throw new UnsupportedOperationException("Not supported.");
    }
    
}
