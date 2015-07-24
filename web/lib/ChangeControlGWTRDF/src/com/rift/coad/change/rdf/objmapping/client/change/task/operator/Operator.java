/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * Operator.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task.operator;

// data type imports
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * An operator to be perform on an expresion.
 *
 * @author brett chaldecott
 */
public class Operator extends DataType {

    // private member variables
    private String name;
    private String description;

    
    /**
     * The default constructor.
     */
    public Operator() {

    }
    

    /**
     * This constructor is responsible for setting the name and description.
     *
     * @param name The name of the operator.
     * @param description The description.
     */
    public Operator(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * This method returns the description of the operator.
     *
     * @return The string containing the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method sets the name of the operator.
     * @return
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the operator.
     *
     * @param name The name of the operator.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The equals operator.
     *
     * @param obj The object result.
     * @return The return result.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)){
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Operator other = (Operator) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }



    
}
