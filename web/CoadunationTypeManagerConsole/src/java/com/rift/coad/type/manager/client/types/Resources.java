/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2009  2015 Burntjam
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
 * Resources.java
 */

// package path
package com.rift.coad.type.manager.client.types;

// java imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The definition of the resources.
 *
 * @author brett chaldecott
 */
public class Resources {

    // class singleton
    private static Resources singleton = null;

    // private member variables
    private Map<String, Resource> resourcesByName = new HashMap<String, Resource>();
    private Map<String, List<Resource>> resourcesByType = new HashMap<String, List<Resource>>();

    /**
     * This constructor sets up the default list of resources.
     */
    private Resources() {

    }


    /**
     * This method is responsible for returning the resource object.
     *
     * @return The type of resource object to retrieve.
     */
    public static synchronized Resources getInstance() {
        if (singleton == null) {
            singleton = new Resources();
        }
        return singleton;
    }


    /**
     * This method loads the resources for a given group
     * @param group THe resource group.
     */
    public void loadResources(String group) {
        
    }


    
}
