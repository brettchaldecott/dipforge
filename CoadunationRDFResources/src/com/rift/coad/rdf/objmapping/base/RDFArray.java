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
 * RDFArray.java
 */


// package path
package com.rift.coad.rdf.objmapping.base;

//
import com.rift.coad.lib.common.RandomGuid;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents an array of a particular type. The type information
 *
 * @author brett
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("RDFArray")
public class RDFArray extends DataType implements RDFCollections {

    private String id;
    private DataType dataType;
    private DataType[] entries;


    /**
     * The default constructor
     */
    public RDFArray() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }


    /**
     * This constructor sets all the array information excluding the id.
     * @param dataTyep The data type associated with this array.
     * @param entries The list of entries.
     */
    public RDFArray(DataType dataType, DataType[] entries) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        this.dataType = dataType;
        this.entries = entries;
    }




    /**
     * This method returns the object id for this object.
     * @return The string containing the object id.
     */
    @Override
    public String getObjId() {
        return id;
    }

    /**
     * This method gets the id for this object.
     * 
     * @return The string containing the id for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id for this object.
     *
     * @param id This method sets the id for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The list of entries managed by this object.
     *
     * @return The list of entries managed by this object.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayEntries")
    public DataType[] getEntries() throws RDFException {
        return entries;
    }

    /**
     * This method sets the entries.
     *
     * @param entries The entris to store.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayEntries")
    public void setEntries(DataType[] entries) throws RDFException {
        this.entries = entries;
    }


    /**
     * This method returns the data type managed by this object.
     *
     * @return The object containing the data type.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayDataType")
    public DataType getDataType() throws RDFException {
        return dataType;
    }


    /**
     * This method sets the data type for this object.
     *
     * @param type The type information for this object.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFArrayDataType")
    public void setDataType(DataType type) throws RDFException {
        if (type != null) {
            try {
                this.dataType = (DataType)type.clone();
                this.dataType.setAssociatedObject(this.getIdForDataType());
            } catch (Exception ex) {
                // ignore
            }
        } else {
            this.dataType = null;
        }
    }
    

    /**
     * This method returns a new instance of the data type this array is associated with.
     *
     * @return The reference to the newly cloned data type object.
     */
    public DataType createTypeInstance() {
        if (dataType == null) {
            return dataType;
        }
        try {
            return (DataType)dataType.clone();
        } catch (Exception ex) {
            return null;
        }
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


    /**
     * The clone method.
     *
     * @return This method returns the clone information.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        RDFArray result = (RDFArray)super.clone();
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        return result;
    }


}
