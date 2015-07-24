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
 * RDFArray.java
 */


// package path
package com.rift.coad.rdf.objmapping.client.base;

//

/**
 * This object represents an array of a particular type. The type information
 *
 * @author brett
 */
public class RDFArray extends DataType implements RDFCollections {

    private String id;
    private DataType dataType;
    private DataType[] entries;


    /**
     * The default constructor
     */
    public RDFArray() {
    }


    /**
     * This constructor sets all the array information excluding the id.
     * @param dataTyep The data type associated with this array.
     * @param entries The list of entries.
     */
    public RDFArray(DataType dataType, DataType[] entries) {
        this.dataType = dataType;
        this.entries = entries;
    }




    /**
     * This method gets the id for this object.
     * 
     * @return The string containing the id for this object.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id for this object.
     *
     * @param id This method sets the id for this object.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The list of entries managed by this object.
     *
     * @return The list of entries managed by this object.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public DataType[] getEntries() throws RDFException {
        return entries;
    }

    /**
     * This method sets the entries.
     *
     * @param entries The entris to store.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public void setEntries(DataType[] entries) throws RDFException {
        this.entries = entries;
    }


    /**
     * This method returns the data type managed by this object.
     *
     * @return The object containing the data type.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public DataType getDataType() throws RDFException {
        return dataType;
    }


    /**
     * This method sets the data type for this object.
     *
     * @param type The type information for this object.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public void setDataType(DataType type) throws RDFException {
        this.dataType = dataType;
    }


    /**
     * This method checks the equals object value.
     *
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFArray other = (RDFArray) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code operation.
     *
     * @return The result of the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


}
