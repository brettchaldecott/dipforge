/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * ResourceBase.java
 */

package com.rift.coad.rdf.objmapping.resource;

// java imports
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

// rdf imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;


// coadunation imports
import com.rift.coad.rdf.objmapping.base.DataType;


/**
 * This object is responsible for representing a resource base object.
 *
 * @author Brett Chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/resource#")
@RdfType("ResourceBase")
public abstract class ResourceBase extends DataType {
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#ResourceAttributes")
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
     * This method sets the id for the data type.
     *
     * @param idForDataType The id for the new data type.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType")
    public void setIdForDataType(String idForDataType) {
        super.setIdForDataType(idForDataType);
        if (attributes != null) {
            for (DataType attribute : attributes) {
                attribute.setAssociatedObject(idForDataType);
            }
        }
    }


    
     /**
     * This method returns the array of attributes.
     *
     * @return The array of attributes.
     */
    public <T> T getAttribute(Class<T> c, String name) {
        for (DataType attribute : attributes) {
            if (attribute.getDataName().equals(name)) {
                return c.cast(attribute);
            }
        }
        return null;
    }


     /**
     * This method returns the array of attributes.
     *
     * @return The array of attributes.
     */
    public <T> Collection<T> getAttributes(Class<T> c, String name) {
        Collection<T> entries = new ArrayList<T>();
        for (DataType attribute : attributes) {
            if (attribute.getIdForDataType().equals(name)) {
                entries.add(c.cast(attribute));
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
            attributes = Arrays.copyOf(attributes, attributes.length +1);
        }
        attribute.setAssociatedObject(this.getIdForDataType());
        attributes[attributes.length - 1] = attribute;
    }

}
