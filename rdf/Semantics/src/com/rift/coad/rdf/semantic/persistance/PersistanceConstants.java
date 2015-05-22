/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * PersistanceConstants.java
 */

package com.rift.coad.rdf.semantic.persistance;

/**
 * The constants used by the persistance layer
 *
 * @author brett chaldecott
 */
public class PersistanceConstants {
    /**
     * The property name that manages the persistance layer
     */
    public final static String PERSISTANCE_MANAGER_CLASS = "persistance_manager";


    /**
     * The rdf contents to be managed by the store.
     */
    public final static String XML_RDF_CONTENTS = "xml_rdf_contents";


    /**
     * If the store being selected requires a configuration file.
     */
    public final static String STORE_CONFIGURATION_FILE = "store_configuration_file";
    
    
    /**
     * The store url
     */
    public final static String STORE_URL = "store_url";
    
}
