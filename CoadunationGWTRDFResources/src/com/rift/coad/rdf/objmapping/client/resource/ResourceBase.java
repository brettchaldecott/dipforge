/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * ResourceBase.java
 */

package com.rift.coad.rdf.objmapping.client.resource;

// java imports
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.DataType;


/**
 * This object is responsible for representing a resource base object.
 *
 * @author Brett Chaldecott
 */
public abstract class ResourceBase extends DataType {
    private DataType[] attributes = null;
    
    /**
     * The default constructor
     */
    public ResourceBase() {
    }


    /**
     * This object represents a resource base.
     *
     * @param id The id for the resource base.
     * @param name The name of the resource base.
     * @param attributes The list of attributes.
     */
    public ResourceBase(DataType[] attributes) {
        for (DataType type : attributes) {
            this.addAttribute(type);
        }
    }

    /**
     * This method sets the data type id for this object.
     *
     * @param dataTypeId The data type id for this object.
     */
    public void setIdForDataType(String dataTypeId) {
        super.setIdForDataType(dataTypeId);
        if (attributes != null) {
            for (DataType attribute : attributes) {
                attribute.setAssociatedObject(dataTypeId);
            }
        }
    }


    
     /**
     * This method returns the array of attributes.
     *
     * @return The array of attributes.
     */
    public DataType getAttribute(String name) {
        for (DataType attribute : attributes) {
            if (attribute.getDataName().equals(name)) {
                return attribute;
            }
        }
        return null;
    }


     /**
     * This method returns the array of attributes.
     *
     * @return The array of attributes.
     */
    public Collection<DataType> getAttributes(String name) {
        Collection<DataType> entries = new ArrayList<DataType>();
        for (DataType attribute : attributes) {
            if (attribute.getIdForDataType().equals(name)) {
                entries.add(attribute);
            }
        }
        return entries;
    }
    

    /**
     * This method returns the array of attributes.
     *
     * @return The array of attributes.
     */
    public DataType[] getAttributes() {
        return attributes;
    }


    /**
     * This method sets the array of attributes.
     * @param attributes
     */
    public void setAttributes(DataType[] attributes) {
        this.attributes = attributes;
        for (DataType dataType : this.attributes) {
            dataType.setAssociatedObject(this.getIdForDataType());
        }
    }


    /**
     * This method adds the specified attribute.
     * 
     * @param attribute The attribute to add to the list of attributes
     */
    public void addAttribute(DataType attribute) {
        if (attributes == null) {
            attributes = new DataType[1];
        } else {
            DataType[] attributes = new DataType[this.attributes.length + 1];
            for (int index = 0; index < this.attributes.length;index++) {
                attributes[index] = this.attributes[index];
            }
            this.attributes = attributes;
        }
        attribute.setAssociatedObject(this.getIdForDataType());
        this.attributes[attributes.length - 1] = attribute;
    }

}
