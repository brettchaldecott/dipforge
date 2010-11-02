/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * ActionTaskDefinition.java
 */


// package information
package com.rift.coad.change.rdf.objmapping.client.change;

// web semantics


// java imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;




/**
 * The definition of this action.
 *
 * @author brett chaldecott
 */
public class ActionTaskDefinition extends DataType {

    private String id;
    private String name;
    private String description;
    private ActionTaskDefinition next;

    
    /**
     * The default constructor of the action task definition.
     */
    public ActionTaskDefinition() {
        id = IDGenerator.getId();
    }


    public ActionTaskDefinition(String name, String description) {
        id = IDGenerator.getId();
        this.name = name;
        this.description = description;
    }



    /**
     * This constructor sets all internal parameters except for the child task.
     * 
     * @param id The id of the task.
     * @param name The name of the task.
     * @param description The description of the task.
     */
    public ActionTaskDefinition(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    /**
     * This constructor sets up all the internal values.
     *
     * @param name The name of the action.
     * @param description The description of this task.
     * @param next The follow ontask.
     */
    public ActionTaskDefinition(String name, String description, ActionTaskDefinition next) {
        id = IDGenerator.getId();
        this.name = name;
        this.description = description;
        this.next = next;
    }



    /**
     * This constructor sets all the internal parameters.
     *
     * @param id The id of this task.
     * @param name The name of the next task.
     * @param description The description of this task.
     * @param next The next task to execute after this one.
     */
    public ActionTaskDefinition(String id, String name, String description, ActionTaskDefinition next) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.next = next;
    }


    

    /**
     * This method returns the description of the action task definition.
     * 
     * @return The string containing the action task definition.
     */
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description of the action task definition
     * 
     * @param description The string containing the new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the id of the action task definition
     *
     * @return The string containing the id of the task definition.
     */
    public String getTaskDefinitionId() {
        return id;
    }


    /**
     * This method sets the id of the task defintion.
     *
     * @param id The id of the task definition.
     */
    public void setTaskDefinitionId(String id) {
        this.id = id;
    }


    /**
     * This method returns the name of the action task definition.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the task.
     *
     * @param name The name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method returns the action task definition.
     * 
     * @return The next action task to execute.
     */
    public ActionTaskDefinition getNext() {
        return next;
    }


    /**
     * This method sets the next task.
     *
     * @param next The next task.
     */
    public void setNext(ActionTaskDefinition next) {
        this.next = next;
    }
    

    /**
     * This method checks to see if entries are equal
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionTaskDefinition other = (ActionTaskDefinition) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    
}
