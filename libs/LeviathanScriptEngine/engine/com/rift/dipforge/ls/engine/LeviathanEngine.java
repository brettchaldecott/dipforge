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
 * LeviathanEngine.java
 */

// package path
package com.rift.dipforge.ls.engine;

import java.util.Properties;

/**
 * The implementation of the leviathan script engine.
 *
 * @author brett chaldecott
 */
public class LeviathanEngine {
    
    // private member variables
    private static LeviathanEngine engine = null;
    
    
    /**
     * The constructor that sets up the leviathan engine.
     * 
     * @param property The property list.
     */
    private LeviathanEngine(Properties property) {
        
    }
    
    
    /**
     * This method returns a reference to a new leviathan engine.
     *
     * @return The reference to the new leviathan engine.
     */
    public synchronized static LeviathanEngine buildEngine(Properties property) 
        throws EngineException {
        if (engine != null) {
            throw new EngineException("The ");
        }
        return engine;
    }
    
    
    /**
     * This method returns the reference to the leviathan engine.
     * 
     * @return The reference to the leviathan engine.
     */
    public static LeviathanEngine getInstance() {
        return engine;
    }
}
