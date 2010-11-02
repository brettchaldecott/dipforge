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
package com.rift.coad.change.rdf.objmapping.client.change;

// jena bean imports

// the implementation of the data type base object.
import com.rift.coad.rdf.objmapping.client.base.DataType;


/**
 * A type action binding.
 *
 * @author brett chaldecott
 */
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
     * This method returns the action information.
     *
     * @return The action information.
     */
    public ActionInfo getActionInfo() {
        return actionInfo;
    }


    /**
     * This method sets the action information.
     *
     * @param actionInfo This method sets the action information.
     */
    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }


    /**
     * The getter for the data type id.
     *
     * @return The string containing the data type id.
     */
    public String getDataTypeId() {
        return dataTypeId;
    }


    /**
     * The setter for the data type id.
     *
     * @param dataTypeId The data type.
     */
    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }


    /**
     * This method gets the parent id information.
     *
     * @return The parent reference.
     */
    public ActionTaskDefinition getParent() {
        return parent;
    }

    
    /**
     * This sets the parent task reference.
     * 
     * @param parent The parent task reference.
     */
    public void setParent(ActionTaskDefinition parent) {
        this.parent = parent;
    }

    
    /**
     * This method returns the dependancy data.
     * 
     * @return The dependancy data.
     */
    public String[] getDependancyData() {
        return dependancyDataId;
    }


    /**
     * The setter for the dependancy data.
     *
     * @param dependancyData The new set of data dependancies required to complete this task.
     */
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


    /**
     * This method creates an instance of the action definition.
     *
     * @return The reference to the newly created action definition.
     */
    public static ActionDefinition createInstance() {
        ActionDefinition definition = new ActionDefinition();
        definition.setIdForDataType(Constants.ACTION_TASK_DEFINITION);
        definition.setBasicType(Constants.ACTION_TASK_DEFINITION);
        return definition;
    }


    /**
     * This constructor sets all internal values except for parent task.
     *
     * @param dataTypeId The data type id.
     * @param actionInfo The action information.
     */
    public static ActionDefinition createInstance(String dataTypeId, ActionInfo actionInfo) {
        ActionDefinition definition = createInstance();
        definition.setDataTypeId(dataTypeId);
        definition.setActionInfo(actionInfo);
        return definition;
    }


    /**
     * A constructor that sets all internal values.
     *
     * @param dataTypeId The data type id.
     * @param actionInfo The action information for this task.
     * @param parent The parent task.
     */
    public static ActionDefinition createInstance(String dataTypeId,
            ActionInfo actionInfo, ActionTaskDefinition parent) {
        ActionDefinition definition = createInstance();
        definition.setDataTypeId(dataTypeId);
        definition.setActionInfo(actionInfo);
        definition.setParent(parent);
        return definition;
    }
}
