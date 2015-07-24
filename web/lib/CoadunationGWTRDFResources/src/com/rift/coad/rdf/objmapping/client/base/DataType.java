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
 * DataType.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base;

// java imports
import java.lang.annotation.Annotation;

/**
 * The data type information
 * 
 * @author brett chaldecott
 */
public abstract class DataType extends RDFBase {
    // member variables.
    private String basicType = null;
    private String idForDataType = null;
    private String associatedObject = null;
    private String dataName = null;


    /**
     * The default constructor
     */
    public DataType() {
        /*try {
            basicType = this.getClass().getName();
            dataTypeId = this.getClass().getName();
        } catch (Exception ex) {
            Logger.getLogger(DataType.class).fatal("Failed to generate a unique id: " +
                    ex.getMessage(),ex);
        }*/

        dataName = null;
    }


    /**
     * The name of the data type.
     *
     * @param dataName The name of the object.
     */
    public DataType(String dataName) {
        /*try {
            basicType = this.getClass().getName();
            idForDataType = this.getClass().getName();
        } catch (Exception ex) {
            Logger.getLogger(DataType.class).fatal("Failed to generate a unique id: " +
                    ex.getMessage(),ex);
        }*/
        this.dataName = dataName;
    }

    
    /**
     * The constructor that sets all the internal member variables.
     * 
     * @param idForDataType The id for this data type.
     * @param dataName The name of this object.
     */
    public DataType(String idForDataType, String dataName) {
        basicType = this.getClass().getName();
        this.idForDataType = idForDataType;
        this.dataName = dataName;
    }


    /**
     * This method returns the type information for this object.
     * 
     * @return The string containing the basic type information.
     */
    public String getBasicType() {
        return basicType;
    }


    /**
     * This method is responsible for setting the basic type information.
     *
     * @param basicType The type information
     */
    public void setBasicType(String basicType) {
        this.basicType = basicType;
    }

    /**
     * This method retrieves the id for the data type.
     *
     * @return The id that identifies this data type.
     */
    public String getIdForDataType() {
        return idForDataType;
    }


    /**
     * The setter of the id for the data type.
     *
     * @param idForDataType The string that contains the new id for the data type.
     */
    public void setIdForDataType(String idForDataType) {
        this.idForDataType = idForDataType;
    }
    
    
    /**
     * This method returns the associated object data type id.
     * 
     * @return The string containing the associated data type id.
     */
    public String getAssociatedObject() {
        return associatedObject;
    }


    /**
     * This method sets the assocated object data type information.
     *
     * @param associatedObject The string containing the associated object data type id.
     */
    public void setAssociatedObject(String associatedObject) {
        this.associatedObject = associatedObject;
    }


    /**
     * The name of the object.
     *
     * @return This method returns the data name.
     */
    public final String getDataName() {
        if (dataName == null) {
            return this.getClass().getName();
        }
        return dataName;
    }


    /**
     * This method sets the final name of the data type.
     *
     * @param dataName The name of the data type.
     */
    public final void setDataName(String dataName) {
        this.dataName = dataName;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataType other = (DataType) obj;
        if ((this.basicType == null) ? (other.basicType != null) : !this.basicType.equals(other.basicType)) {
            return false;
        }
        if ((this.idForDataType == null) ? (other.idForDataType != null) : !this.idForDataType.equals(other.idForDataType)) {
            return false;
        }
        if ((this.associatedObject == null) ? (other.associatedObject != null) : !this.associatedObject.equals(other.associatedObject)) {
            return false;
        }
        if ((this.dataName == null) ? (other.dataName != null) : !this.dataName.equals(other.dataName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.basicType != null ? this.basicType.hashCode() : 0);
        hash = 89 * hash + (this.idForDataType != null ? this.idForDataType.hashCode() : 0);
        return hash;
    }



}
