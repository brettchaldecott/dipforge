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
 * TypeManagerLookup.java
 */

package com.rift.dipforge.ls.engine.internal;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.parser.obj.LsAnnotation;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a means to lookup the various type managers.
 * 
 * @author brett chaldecott
 */
public class TypeManagerLookup {
    
    // class singletons
    private static TypeManagerLookup singleton = null;
    
    // private member variables
    private List<TypeManager> managers = new ArrayList<TypeManager>();
    
    
    /**
     * The private constructor.
     */
    private TypeManagerLookup() {
    }
    
    
    /**
     * This method returns the reference to the type manager lookup instance.
     * 
     * @return The reference to the type manager lookup reference.
     * @throws EngineException 
     */
    public synchronized static TypeManagerLookup getInstance() 
            throws EngineException {
        if (singleton == null) {
            singleton = new TypeManagerLookup();
        }
        return singleton;
    }
    
    
    /**
     * This method adds a new type manager.
     * 
     * @param manager The reference to the type manager.
     */
    public synchronized void registerTypeManager(TypeManager manager) {
        managers.add(manager);
    }
    
    
    /**
     * This method returns a reference to the annotation responsible for the type.
     * 
     * @param annotation The reference to the annotation.
     * @return The link to the annotation
     * @throws EngineException 
     */
    public synchronized TypeManager getManager(LsAnnotation annotation) throws EngineException {
        for (TypeManager manager: managers) {
            if (manager.canHandleAnnotation(annotation)) {
                return manager;
            }
        }
        throw new EngineException("There is no manager for this annotation : " + 
                annotation.getName());
    }
    
    /**
     * This method returns a reference to the type manager responsible for a
     * particular object type.
     * 
     * @param type The reference to the type.
     * @return The manager type manager
     */
    public synchronized TypeManager getManager(Object type) throws EngineException {
        for (TypeManager manager: managers) {
            if (manager.manageType(type)) {
                return manager;
            }
        }
        throw new EngineException("There is no manager for the type : " + 
                type.getClass().getName());
    }
}
