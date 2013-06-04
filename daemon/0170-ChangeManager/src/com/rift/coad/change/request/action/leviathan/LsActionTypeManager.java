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
 * LsActionTypeManager.java
 */
package com.rift.coad.change.request.action.leviathan;

import com.rift.coad.change.request.action.leviathan.action.ActionCall;
import com.rift.coad.change.request.action.leviathan.action.LsActionStackEntry;
import com.rift.coad.change.request.action.leviathan.rpc.JNDIRPCCall;
import com.rift.coad.change.request.action.leviathan.rpc.LsJNDIStackEntry;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 * This object is responsible for providing access to the underlying action
 * information.
 * 
 * @author brett chaldecott
 */
public class LsActionTypeManager implements TypeManager {

    // class constants
    private final static String ACTION_NAME = "action";
    
    /**
     * The default constructor for this 
     */
    public LsActionTypeManager() {
    }

    
    /**
     * This method is used to check if the annotation is 
     * 
     * @param annotation The annotation to process.
     * @return TRUE if this annotation is handled by this object.
     * @throws EngineException 
     */
    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        try {
            if (annotation.getName().equalsIgnoreCase("action")) {
                if (annotation.getList().size() != 0) {
                    throw new EngineException(
                            "The annotation is corrupt as it does not take any "
                            + "parameters.");
                }
                return true;
            }
            return false;
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to check if the annotation can be "
                    + "handled by this manager because : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is used to process the annotation at run time.
     * @param flow
     * @param annotation
     * @param configParameters
     * @param envParameters
     * @throws EngineException 
     */
    public void processAnnotation(Workflow flow, LsAnnotation annotation, Map configParameters, Map envParameters) throws EngineException {
        try {
            if (annotation.getName().equalsIgnoreCase("action")) {
                envParameters.put(ACTION_NAME, new ActionCall());
            } else {
                throw new EngineException("The annotation [" + 
                        annotation.getName() + "] cannot be managed by this object");
            }
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to process the annotations : " +
                    ex.getMessage());
        }
    }

    
    /**
     * This method is used by the leviathan engine to determine if a variable
     * is handled by it.
     * 
     * @param type The type to process.
     * @return TRUE if this object manages it.
     * @throws EngineException 
     */
    public boolean manageType(Object type) throws EngineException {
        if (type instanceof ActionCall) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method is called to create a stack entry for the method.
     * 
     * @param parent The parent of this stack entry.
     * @param variables The variables used in this stack.
     * @param callStatement The call back statement.
     * @param variable The variables to add.
     * @param parameters The list of parameters.
     * @return The newly created stack entry.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForMethod(ProcessStackEntry parent, Map variables, 
            CallStatement callStatement, Object variable, List parameters) 
            throws EngineException {
        if (variable instanceof ActionCall) {
            return new LsActionStackEntry(parent,variables, callStatement, variable,
                    parameters);
        }
        throw new EngineException("Incorrect type passed in to create stack entry.");
    }

    
    /**
     * This method is not supported.
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
    public ProcessStackEntry createStackEntryForList(ProcessStackEntry parent, Map variables, CallStatement callStatement, Object target, Object argument, Object assignmentValue, boolean hasAssignment) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method is not supported.
     * 
     * @param parent
     * @param variables
     * @param callStatement
     * @param target
     * @param assignmentValue
     * @param hasAssignment
     * @return
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForVariable(ProcessStackEntry parent, Map variables, CallStatement callStatement, Object target, Object assignmentValue, boolean hasAssignment) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
