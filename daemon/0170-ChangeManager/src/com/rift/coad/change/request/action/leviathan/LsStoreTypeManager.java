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

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import com.rift.dipforge.ls.parser.obj.Workflow;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author brett chaldecott
 */
public class LsStoreTypeManager implements TypeManager {

    public boolean canHandleAnnotation(LsAnnotation annotation) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void processAnnotation(Workflow flow, LsAnnotation annotation, Map configParameters, Map envParameters) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean manageType(Object type) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ProcessStackEntry createStackEntryForMethod(ProcessStackEntry parent, Map variables, CallStatement callStatement, Object variable, List parameters) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ProcessStackEntry createStackEntryForList(ProcessStackEntry parent, Map variables, CallStatement callStatement, Object target, Object argument, Object assignmentValue, boolean hasAssignment) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ProcessStackEntry createStackEntryForVariable(ProcessStackEntry parent, Map variables, CallStatement callStatement, Object target, Object assignmentValue, boolean hasAssignment) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
