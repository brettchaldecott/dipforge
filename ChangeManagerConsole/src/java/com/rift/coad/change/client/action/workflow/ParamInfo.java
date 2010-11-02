/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * BinWhileBlock.java
 */


// package path
package com.rift.coad.change.client.action.workflow;



/**
 * The parameter information
 *
 * @author brett chaldecott
 */
public class ParamInfo {
    // private member variables
    private String type;
    private String name;

    /**
     * The default constructor.
     */
    public ParamInfo() {
    }


    /**
     * This constructor sets all the internal information.
     *
     * @param type The type of the parameter.
     * @param name The name of the parameter.
     */
    public ParamInfo(String type, String name) {
        this.type = type;
        this.name = name;
    }


    /**
     * This method returns the name of the parameter.
     *
     * @return The name of the parameter.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the parameter.
     *
     * @param name The name of the parameter.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method sets the type of parameter information.
     *
     * @return The type of the parameter.
     */
    public String getType() {
        return type;
    }


    /**
     * This method sets the type of information.
     * 
     * @param type The string containing the type of information.
     */
    public void setType(String type) {
        this.type = type;
    }





}
