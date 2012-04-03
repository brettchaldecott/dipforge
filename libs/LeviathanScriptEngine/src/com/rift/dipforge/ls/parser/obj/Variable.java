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
 * Variable.java
 */
package com.rift.dipforge.ls.parser.obj;

import java.io.Serializable;

public class Variable extends Statement implements Serializable {

    /**
     * The serial number for this version
     */
    private static final long serialVersionUID = 1L;
    private String type;
    private String name;
    private Assignment initialValue;

    /**
     * The variable
     */
    public Variable() {
        // TODO Auto-generated constructor stub
    }

    
    /**
     * The constructor that sets all internal variables
     * 
     * @param type The type of variable
     * @param name The name of the variable
     * @param initialValue The initial value
     */
    public Variable(String type, String name, Assignment initialValue) {
        this.type = type;
        this.name = name;
        this.initialValue = initialValue;
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
     * This method sets the type information.
     *
     * @param type The string containing the type name.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method retrieves the type name.
     *
     * @return The name of the type to retrieve.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of the variable.
     *
     * @param name The string containing the name of the variable.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the intial value for this type.
     *
     * @return The reference to the initial type.
     */
    public Assignment getInitialValue() {
        return initialValue;
    }

    /**
     * This method returns the intial value for this type.
     *
     * @return The reference to the initial type.
     */
    public void setInitialValue(Assignment initialValue) {
        this.initialValue = initialValue;
    }


    /*
     * (non-Javadoc) @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((initialValue == null) ? 0 : initialValue.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }


    /*
     * (non-Javadoc) @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Variable other = (Variable) obj;
        if (initialValue == null) {
            if (other.initialValue != null) {
                return false;
            }
        } else if (!initialValue.equals(other.initialValue)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }


    /*
     * (non-Javadoc) @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Variable [type=" + type + ", name=" + name + ", initialValue="
                + initialValue + "]";
    }
}
