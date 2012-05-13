/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * OntologyProperty.java
 */

// packge path
package com.rift.coad.rdf.semantic.ontology;

// property import
import com.rift.coad.rdf.semantic.common.Property;
import com.rift.coad.rdf.semantic.types.DataType;

/**
 * This method represents an ontolog property.
 *
 * @author brett chaldecott
 */
public interface OntologyProperty extends Property {

    /**
     * This method returns the name space.
     *
     * @return The string containing the name space.
     * @throws OntologyException
     */
    public String getNamespace() throws OntologyException;


    /**
     * This method returns the local name.
     *
     * @return The string containing the local name.
     * @throws OntologyException
     */
    public String getLocalname() throws OntologyException;


    /**
     * This method adds a sub property.
     *
     * @param property The property.
     * @throws OntologyException
     */
    public void addSubProperty(OntologyProperty property) throws OntologyException;


    /**
     * This method adds a sub property.
     *
     * @param property The property.
     * @throws OntologyException
     */
    public void removeSubProperty(OntologyProperty property) throws OntologyException;


    /**
     * This method sets the type label.
     *
     * @param type The type label for this property.
     * @throws OntologyException
     */
    public void setType(DataType type) throws OntologyException;


    /**
     * This method returns the type label.
     *
     * @return The reference to the type label
     * @throws OntologyException
     */
    public DataType getType() throws OntologyException;
    
    
    /**
     * This method returns true if the ontology property has a range.
     * 
     * @return True if this object has a range
     * @throws OntologyException 
     */
    public boolean hasRange() throws OntologyException;
    
    
    /**
     * This method gets the range information.
     * 
     * @return The reference to the range
     * @throws OntologyException 
     */
    public DataType getRange() throws OntologyException;
    
    
    /**
     * This method sets the range information for the object.
     * 
     * @param range The range reference.
     * @throws OntologyException 
     */
    public void setRange(DataType range) throws OntologyException;
}
