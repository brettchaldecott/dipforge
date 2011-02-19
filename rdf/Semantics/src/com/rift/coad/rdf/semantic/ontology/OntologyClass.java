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
 * OntologyClass.java
 */

package com.rift.coad.rdf.semantic.ontology;

import com.rift.coad.rdf.semantic.types.DataType;
import java.net.URI;
import java.util.List;

/**
 * This interface defines the methods to manipulate the ontology.
 *
 * @author brett chaldecott
 */
public interface OntologyClass extends DataType {

    /**
     * This method adds a new property.
     *
     * @param property This method adds a new property to the ontology.
     * @throws OntologyException
     */
    public void addProperty(OntologyProperty property) throws OntologyException;


    /**
     * This method returns a list of properties.
     *
     * @return The list of ontology properties associated with this object.
     * @throws OntologyException
     */
    public List<OntologyProperty> listProperties() throws OntologyException;


    /**
     * This method removes the property identified by the URI.
     *
     * @param uri The URI to remove.
     * @throws OntologyException
     */
    public void removeProperty(URI uri) throws OntologyException;
}
