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
 * RDFCollections.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base;


/**
 * This interface defines the RDF collection information.
 *
 * @author brett chaldecott
 */
public interface RDFCollections {


    /**
     * This method retrieves the data type held in this collection.
     *
     * @return The reference to this array.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public DataType getDataType() throws RDFException;


    /**
     * This method sets the data type managed by this object.
     * 
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public void setDataType(DataType type) throws RDFException;



    /**
     * This method returns the entries managed by this object.
     *
     * @return The array of entries.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public DataType[] getEntries() throws RDFException;


    /**
     * This method sets the entries managed by this object.
     *
     * @param entries The entries to be managed by this object.
     * @throws com.rift.coad.rdf.objmapping.base.RDFException
     */
    public void setEntries(DataType[] entries) throws RDFException;
    
}
