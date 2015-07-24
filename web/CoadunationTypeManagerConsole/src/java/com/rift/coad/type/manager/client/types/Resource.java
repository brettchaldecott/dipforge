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
 * Resource.java
 */

// package path
package com.rift.coad.type.manager.client.types;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.rift.coad.type.manager.client.dto.ResourceDefinition;

/**
 * The definition of a resource.
 *
 * @author brett chaldecott
 */
public class Resource {
    // private member variables
    private String name;
    private String type;
    private String uri;
    private String className;
    private ResourceDefinition dataType;

    
    /**
     * This constructor setups up the resource definition.
     *
     * @param name The name of the resource to construct.
     * @param type The type of resource.
     * @param uri The uri of the resource.
     */
    public Resource(String name, String type, String uri, String className, boolean load) throws TypeException {
        this.name = name;
        this.type = type;
        this.uri = uri;
        this.className = className;
        try {
            //this.dataType = TypeManager.getType(className);
        } catch (Exception ex) {
            throw new TypeException("Failed to retrieve the type : " + ex.getMessage(),ex);
        }
        // load the data type
        if (load) {

        }
    }


    /**
     * This method returns the name of this resource.
     * @return The string containing the
     */
    public String getName() {
        return name;
    }


    /**
     * This method returns the type information.
     *
     * @return The string containing the type information.
     */
    public String getType() {
        return type;
    }

    
    /**
     * The uri that this type will be scoped under in the type management store.
     *
     * @return The string containing the uri value.
     */
    public String getUri() {
        return uri;
    }
    
    
    /**
     * This method returns the name of the class.
     * 
     * @return The string containing the class name.
     */
    public String getClassName() {
        return className;
    }


    /**
     * This method sets the class name.
     *
     * @param className The string containing the class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }


    /**
     * This method retrieves the data type.
     *
     * @return The reference to specified data type.
     */
    public ResourceDefinition getDataType() {
        return dataType;
    }


    /**
     * Set the data type.
     *
     * @param dataType The data type.
     */
    public void setDataType(ResourceDefinition dataType) {
        this.dataType = dataType;
    }


    /**
     * This object returns a resource object instance.
     *
     * @return The resource object to retrieve.
     */
    public ResourceDefinition getResource() {
        try {
            //return TypeManager.getType(this.className);
            return null;
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * This object returns a resource object instance.
     *
     * @return The resource object to retrieve.
     */
    public Composite getEditor() {
        try {
            //return TypeManager.getTypeEditor(dataType);
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
