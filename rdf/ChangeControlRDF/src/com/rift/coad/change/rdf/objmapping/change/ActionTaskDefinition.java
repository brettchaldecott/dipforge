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
package com.rift.coad.change.rdf.objmapping.change;

// web semantics
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;


// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.lib.common.RandomGuid;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;




/**
 * The definition of this action.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("ActionTaskDefinition")
public class ActionTaskDefinition extends DataType {

    private String id;
    private String name;
    private String description;
    private ActionTaskDefinition next;

    
    /**
     * The default constructor of the action task definition.
     */
    public ActionTaskDefinition() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }


    public ActionTaskDefinition(String name, String description) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
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
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
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
     * This method returns the
     *
     * @return
     */
    @Override
    public String getObjId() {
        return id;
    }

    
    /**
     * This method returns the description of the action task definition.
     * 
     * @return The string containing the action task definition.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskDescription")
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description of the action task definition
     * 
     * @param description The string containing the new description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskDescription")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the id of the action task definition
     *
     * @return The string containing the id of the task definition.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskDefinitionId")
    @Identifier()
    public String getTaskDefinitionId() {
        return id;
    }


    /**
     * This method sets the id of the task defintion.
     *
     * @param id The id of the task definition.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskDefinitionId")
    public void setTaskDefinitionId(String id) {
        this.id = id;
    }


    /**
     * This method returns the name of the action task definition.
     *
     * @return The name of the task.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the task.
     *
     * @param name The name of the task.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskName")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method returns the action task definition.
     * 
     * @return The next action task to execute.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskNext")
    public ActionTaskDefinition getNext() {
        return next;
    }


    /**
     * This method sets the next task.
     *
     * @param next The next task.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionTaskNext")
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


    @Override
    public Object clone() throws CloneNotSupportedException {
        ActionTaskDefinition definition = (ActionTaskDefinition)super.clone();
        try {
            definition.id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        return definition;
    }





    
}
