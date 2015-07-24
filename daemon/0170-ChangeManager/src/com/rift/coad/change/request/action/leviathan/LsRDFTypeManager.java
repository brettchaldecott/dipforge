/*
 * ChangeControlManager: The manager for the change events.
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
 * LsRDFTypeManager.java
 */

// package path
package com.rift.coad.change.request.action.leviathan;

import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFProperty;
import com.rift.coad.change.request.action.leviathan.requestdata.LsRDFTypeListStackEntry;
import com.rift.coad.change.request.action.leviathan.requestdata.LsRDFTypeMethodStackEntry;
import com.rift.coad.change.request.action.leviathan.requestdata.LsRDFTypeVariableStackEntry;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 * The RDF Type manager object.
 * 
 * @author brett chaldecott
 */
public class LsRDFTypeManager implements TypeManager {

    /**
     * The default constructor
     */
    public LsRDFTypeManager() {
    }

    
    /**
     * This method is called to process the given annotation.
     * 
     * @param annotation The annotation to process.
     * @return TRUE if the annotation is handled by this object.
     * @throws EngineException 
     */
    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        boolean result = annotation.getName().equalsIgnoreCase("rdf");
        if (result && annotation.getList().size() != 2) {
            throw new EngineException(
                    "The annotation is corrupt and must provide two parameters [type,name] got [" +
                    annotation.getList().size() + "]");
        }
        return result;
    }
    
    
    /**
     * This method creates a new stack entry.
     * 
     * @param parent The parent.
     * @param variables The variables.
     * @param callStatement The call statement.
     * @param target The target.
     * @param argument The argument.
     * @param assignmentValue The assignment value.
     * @param hasAssignment Has assignment.
     * @return The reference to the process stack entry
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForList(
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object target, Object argument, Object assignmentValue,
            boolean hasAssignment) throws EngineException {
        return new LsRDFTypeListStackEntry(parent, variables, callStatement,
                target, argument, assignmentValue, hasAssignment);
    }

    
    /**
     * This method creates the stack entry.
     * 
     * @param parent The parent reference.
     * @param variables The list of variables
     * @param callStatement The call statement.
     * @param variable The variable reference.
     * @param parameters The list of parameters
     * @return The reference.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForMethod(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object variable,
            List parameters) throws EngineException {
        return new LsRDFTypeMethodStackEntry(parent,variables, callStatement, 
                variable,parameters);
    }
    
    
    /**
     * This method creates a new stack entry.
     * 
     * @param parent The parent stack entry
     * @param variables The variables.
     * @param callStatement The call statement.
     * @param target The target object.
     * @param assignmentValue The assignment value.
     * @param hasAssignment If this method is an assignment
     * @return The reference to the process stack entry.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForVariable(
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object target, Object assignmentValue, boolean hasAssignment) throws EngineException {
        return new LsRDFTypeVariableStackEntry(parent, variables, callStatement,
            target, assignmentValue, hasAssignment);
    }

    
    /**
     * THis method returns true if the type can be handled by this type manager.
     * 
     * @param type The type to process.
     * @return TRUE if can be managed, FALSE if not.
     * @throws EngineException 
     */
    public boolean manageType(Object type) throws EngineException {
        if (type instanceof LsActionRDFProperty) {
            return true;
        }
        return false;
    }

    
    /**
     * This method is called to process the annotation
     * 
     * @param flow The flow to process
     * @param annotation The annotation to extract the information from.
     * @param configParameters The configuration parameters for this flow.
     * @param envParameters The environment parameters that will be used by this flow.
     * @throws EngineException 
     */
    public void processAnnotation(Workflow flow, LsAnnotation annotation, 
            Map configParameters, Map envParameters) throws EngineException {
        String type = annotation.getList().get(0);
        String name = annotation.getList().get(1);
        if (!configParameters.containsKey(name)) {
            throw new EngineException("The configuration parameter to match the"
                    + " annotation name [" + name + "][" + 
                    configParameters.keySet().toString() + "] does not exist.");
        }
        LsActionRDFProperty data = (LsActionRDFProperty)configParameters.get(name);
        if (!data.getDataTypeUri().equals(type)) {
            throw new EngineException("The configuration parameter to match the"
                    + " annotation name [" + name + "] does not match type expected [" +
                    type + "] got [" + data.getDataTypeUri() + "]");
        }
        envParameters.put(name, data);
        
        // setup the global rdf property information.
        if (!envParameters.containsKey(LsActionRDFProperty.RDF_GLOBAL_PROPERTY)) {
            envParameters.put(LsActionRDFProperty.RDF_GLOBAL_PROPERTY, 
                    data.getData());
        }
    }
    
}
