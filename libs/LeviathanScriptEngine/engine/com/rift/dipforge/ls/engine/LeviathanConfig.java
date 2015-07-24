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
 * LeviathanEngine.java
 */

// package path
package com.rift.dipforge.ls.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The leviathan configuration information
 *
 * @author brett chaldecott
 */
public class LeviathanConfig {
    
    // private member variables
    private List<TypeManager> typeManagers = new ArrayList<TypeManager>();
    private Properties properties = new Properties();
    
    /**
     * The private constructor of the leviathan config
     */
    private LeviathanConfig() {
        
    }
    
    /**
     * This method creates a new configuration object to be consumed by the
     * leviathan engine.
     * 
     * @return A reference to the new leviathan configuration.
     */
    public static LeviathanConfig createConfig() {
        return new LeviathanConfig();
    }
    
    
    /**
     * This method adds a new type manager.
     * 
     * @param manager The list of type managers.
     */
    public void addTypeManager(TypeManager manager) {
        typeManagers.add(manager);
    }

    
    /**
     * This method returns the list of types.
     * 
     * @return This method returns a list of type managers.
     */
    public List<TypeManager> getTypeManagers() {
        return typeManagers;
    }

    /**
     * This method returns the properties information.
     * 
     * @return 
     */
    public Properties getProperties() {
        return properties;
    }
    
    
    
    
}
