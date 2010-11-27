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
 * ActionInfo.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.change;

// jena bean imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

// the implementation of the data type base object.
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;


/**
 * A type action binding.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("ActionDefinition")
public class ActionDefinition extends DataType {

    private String dataTypeId;
    private ActionInfo actionInfo;
    private ActionTaskDefinition parent;
    private String[] dependancyDataId;

    /**
     * The default constructor.
     */
    public ActionDefinition() {
    }


    /**
     * This constructor sets all internal values except for parent task.
     *
     * @param dataTypeId The data type id.
     * @param actionInfo The action information.
     */
    public ActionDefinition(String dataTypeId, ActionInfo actionInfo) {
        this.dataTypeId = dataTypeId;
        this.actionInfo = actionInfo;
    }
    
    

    /**
     * A constructor that sets all internal values.
     *
     * @param dataTypeId The data type id.
     * @param actionInfo The action information for this task.
     * @param parent The parent task.
     */
    public ActionDefinition(String dataTypeId, ActionInfo actionInfo, ActionTaskDefinition parent) {
        this.dataTypeId = dataTypeId;
        this.actionInfo = actionInfo;
        this.parent = parent;
    }

    
    /**
     * This method returns the composite id.
     *
     * @return The id that identifies this object.
     */
    @Override
    public String getObjId() {
        return dataTypeId + actionInfo.getObjId();
    }


    /**
     * This method returns the action information.
     *
     * @return The action information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#DefinitionActionInfo")
    @Identifier()
    public ActionInfo getActionInfo() {
        return actionInfo;
    }


    /**
     * This method sets the action information.
     *
     * @param actionInfo This method sets the action information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#DefinitionActionInfo")
    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }


    /**
     * The getter for the data type id.
     *
     * @return The string containing the data type id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#DefinitionDataTypeId")
    @Identifier()
    public String getDataTypeId() {
        return dataTypeId;
    }


    /**
     * The setter for the data type id.
     *
     * @param dataTypeId The data type.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#DefinitionDataTypeId")
    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }


    /**
     * This method gets the parent id information.
     *
     * @return The parent reference.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionDefinitionParent")
    public ActionTaskDefinition getParent() {
        return parent;
    }

    
    /**
     * This sets the parent task reference.
     * 
     * @param parent The parent task reference.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionDefinitionParent")
    public void setParent(ActionTaskDefinition parent) {
        this.parent = parent;
    }

    
    /**
     * This method returns the dependancy data.
     * 
     * @return The dependancy data.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionDependancyData")
    public String[] getDependancyData() {
        return dependancyDataId;
    }


    /**
     * The setter for the dependancy data.
     *
     * @param dependancyData The new set of data dependancies required to complete this task.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ActionDependancyData")
    public void setDependancyData(String[] dependancyDataId) {
        this.dependancyDataId = dependancyDataId;
    }

    
    /**
     * This method checks to see if the values are equal.
     *
     * @param obj The object to perform the comparison on.
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
        final ActionDefinition other = (ActionDefinition) obj;
        if ((this.dataTypeId == null) ? (other.dataTypeId != null) : !this.dataTypeId.equals(other.dataTypeId)) {
            return false;
        }
        if (this.actionInfo != other.actionInfo && (this.actionInfo == null || !this.actionInfo.equals(other.actionInfo))) {
            return false;
        }
        return true;
    }


    /**
     * The integer containing the hash code.
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.dataTypeId != null ? this.dataTypeId.hashCode() : 0);
        hash = 59 * hash + (this.actionInfo != null ? this.actionInfo.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string representation of this object.
     * 
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return "["+ this.dataTypeId + ":" + this.actionInfo.toString() +"]";
    }


    
}
