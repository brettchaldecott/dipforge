/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * ParameterDef.java
 */

// package defintion
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * This object represents a parameter definition.
 *
 * @author brett chaldecott
 */
public class VariableDef implements IsSerializable {
    
    // private member variables
    private String name = null;
    private String type = null;
    private String description = null;
    private String value = null;
    
    /**
     * Creates a new instance of ParameterDef
     */
    public VariableDef() {
    }
    
    
    /**
     * Creates a new instance of ParameterDef
     *
     * @param name The name of the parameter.
     * @param type The type of the parameter.
     * @param description The description of the parameter.
     */
    public VariableDef(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
    
    
    /**
     * This method returns the name of the parameter.
     *
     * @return The name of this parameter.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns the name of the parameter.
     *
     * @param name The name of this parameter.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * This method returns the type.
     */
    public String getType() {
        return type;
    }
    
    
    /**
     * This method returns the type.
     *
     * @param type The type of parameter.
     */
    public void getType(String type) {
        this.type = type;
    }
    
    
    /**
     * This method returns the description.
     *
     * @return The description of this variable.
     */
    public String getDescription() {
        return description;
    }
    
    
    /**
     * This method sets the description of the variable.
     *
     * @param description The description of the variable.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * This method returns the value of the variable.
     *
     * @return The value of the variable.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value of the variable.
     *
     *
     * @param value The new value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
