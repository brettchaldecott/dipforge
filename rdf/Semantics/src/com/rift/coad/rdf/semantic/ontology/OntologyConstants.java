/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  2015 Burntjam
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
 * OntologyConstants.java
 */

package com.rift.coad.rdf.semantic.ontology;

/**
 * The ontology constants used by the ontology class.
 *
 * @author brett chaldecott
 */
public class OntologyConstants {
    /**
     * The property name that manages the persistance layer
     */
    public final static String ONTOLOGY_MANAGER_CLASS = "ontology_manager";


    /**
     * The contents of the ontology to be managed.
     */
    public final static String ONTOLOGY_CONTENTS = "ontology_contents";


    /**
     * The ontology location uri.
     */
    public final static String ONTOLOGY_LOCATION_URIS = "ontology_location_uris";

    /**
     * The data type property uri
     */
    public final static String DATA_TYPE_PROPERTY_URI  = "http://www.w3.org/2002/07/owl#DatatypeProperty";


    /**
     * property ns
     */
    public final static String PROPERTY_NS_URI  = "http://www.w3.org/1999/02/22-rdf-syntax-ns#Property";

}
