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

import com.rift.coad.change.request.action.leviathan.rpc.JNDIRPCCall;
import com.rift.coad.change.request.action.leviathan.rpc.LsJNDIStackEntry;
import com.rift.coad.change.request.action.leviathan.rpc.LsServiceStackEntry;
import com.rift.coad.change.request.action.leviathan.rpc.ServiceRPCCall;
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
 * This object manages the rpc types.
 * 
 * @author brett chaldecott
 */
public class LsRPCTypeManager implements TypeManager {

    /**
     * The default constructor.
     */
    public LsRPCTypeManager() {
    }
    
    
    /**
     * This method is called to determine if this object can handle the given annotation.
     * 
     * @param annotation The annotation to perform the check on.
     * @return The reference to the annotation to process.
     * @throws EngineException 
     */
    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        try {
            if (annotation.getName().equalsIgnoreCase("service")) {
                if (annotation.getList().size() != 4) {
                    throw new EngineException(
                            "The annotation is corrupt and must provide four "
                            + "parameters [service,project,class,name] got [" +
                            annotation.getList().size() + "]");
                }
                return true;
            }
            if (annotation.getName().equalsIgnoreCase("jndi")) {
                if (annotation.getList().size() != 4) {
                    throw new EngineException(
                            "The annotation is corrupt and must provide four "
                            + "parameters [jndi,project,class,name] got [" +
                            annotation.getList().size() + "]");
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
     * This method is called to process the annotation.
     * 
     * @param flow The flow that definies the annotation.
     * @param annotation The annotation to process.
     * @param configParameters The parameters to configure.
     * @param envParameters The environmental variables.
     * @throws EngineException 
     */
    public void processAnnotation(Workflow flow, LsAnnotation annotation,
            Map configParameters, Map envParameters) throws EngineException {
        try {
            String identifier = annotation.getList().get(0);
            String project = annotation.getList().get(1);
            String className = annotation.getList().get(2);
            String name = annotation.getList().get(3);
            if (annotation.getName().equalsIgnoreCase("jndi")) {
                envParameters.put(name, new JNDIRPCCall(identifier, project, className));
            } else if (annotation.getName().equalsIgnoreCase("service")) {
                envParameters.put(name, new ServiceRPCCall(identifier, project, className));
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
     * This method is called to determine if this object manages the given type.
     * 
     * @param type The type manager.
     * @return TRUE if found, FALSE if not.
     * @throws EngineException 
     */
    public boolean manageType(Object type) throws EngineException {
        if (type instanceof JNDIRPCCall ||
                type instanceof ServiceRPCCall) {
            return true;
        }
        return false;
    }

    
    /**
     * This method creates a stack entry.
     * 
     * @param parent The reference to the parent stack entry.
     * @param variables The variables.
     * @param callStatement The call statement.
     * @param variable The variable.
     * @param parameters The parameters.
     * @return The reference to the process stack entry.
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForMethod(
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object variable, List parameters) throws EngineException {
        if (variable instanceof JNDIRPCCall) {
            return new LsJNDIStackEntry(parent,variables, callStatement, variable,
                    parameters);
        } else {
            return new LsServiceStackEntry(parent,variables, callStatement, 
                    variable, parameters);
        }
    }

    
    /**
     * This method is not implemented.
     * 
     * @param parent The reference to the 
     * @param variables The variables.
     * @param callStatement The call statement.
     * @param target The target object.
     * @param argument The argument.
     * @param assignmentValue The assignment value.
     * @param hasAssignment TRUE if an asignment value has been set.
     * @return
     * @throws EngineException 
     */
    public ProcessStackEntry createStackEntryForList(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            Object argument, Object assignmentValue, boolean hasAssignment)
            throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method is not implemented.
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
    public ProcessStackEntry createStackEntryForVariable(
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object target,
            Object assignmentValue, boolean hasAssignment)
            throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
