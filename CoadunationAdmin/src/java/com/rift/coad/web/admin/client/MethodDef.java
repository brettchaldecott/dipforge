/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * MethodDef.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This object represents a method description
 *
 * @author brett chaldecott
 */
public class MethodDef implements IsSerializable {
    
    // private member variables
    private String name = null;
    private String description = null;
    private VariableDef result = null;
    private VariableDef[] parameters = null;
    
    
    /**
     * Creates a new instance of MethodDef
     */
    public MethodDef() {
    }
    
    
    /**
     * Creates a new instance of MethodDef
     *
     * @param name The name of the method.
     * @param description The description of the method.
     * @param result The result definition.
     * @param parameters The parameters.
     */
    public MethodDef(String name, String description, VariableDef result,
            VariableDef[] parameters) {
        this.name = name;
        this.description = description;
        this.result = result;
        this.parameters = parameters;
    }
    
    
    /**
     * This method returns the name of the method.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method sets the name of the method.
     *
     * @param name The name of the method.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * This method returns the description of this method.
     *
     * @return The string containing the description of this method.
     */
    public String getDescription() {
        return description;
    }
    
    
    /**
     * This method sets the description of the method.
     *
     * @param description The description of this method.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * This method returns the result of the variable definition.
     *
     * @return The definition of the result.
     */
    public VariableDef getResult() {
        return result;
    }
    
    
    /**
     * This method sets the result of method.
     *
     * @param result The result of the method.
     */
    public void setResult(VariableDef result) {
        this.result = result;
    }
    
    
    /**
     * This method returns the parameter definition information.
     *
     * @return The list of parameters.
     */
    public VariableDef[] getParameters() {
        return parameters;
    }
    
    
    /**
     * This method sets the parameters of the method.
     *
     * @param parameters The list of parameters.
     */
    public void setParameters(VariableDef[] parameters) {
        this.parameters = parameters;
    }
 }
