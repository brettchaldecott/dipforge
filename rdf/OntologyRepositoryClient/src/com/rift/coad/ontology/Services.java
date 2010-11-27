/*
 * OntologyRepositoryClient: The client of the ontology repository.
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
 * Services.java
 */

package com.rift.coad.ontology;

/**
 * The services that can be used by the service broker.
 * 
 * @author brett chaldecott
 */
public class Services {
    
    /**
     * This service handles the adding of ontology events.
     */
    public final static String ADD_ONTOLOGY = "add_ontology";
    
    
    /**
     * This method handles the updating of the ontology events.
     */
    public final static String UPDATE_ONTOLOGY = "update_ontology";
    
    
    /**
     * An ontology has been deleted from the repository.
     */
    public final static String DELETE_ONTOLOGY = "delete_ontology";
}
