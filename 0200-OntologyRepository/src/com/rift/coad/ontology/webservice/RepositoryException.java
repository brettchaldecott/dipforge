/*
 * OntologyRepository: The ontology repository.
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
 * RepositoryException.java
 */

// package path
package com.rift.coad.ontology.webservice;


// java imports
import java.io.Serializable;


/**
 * This exception is thrown when there is an error occurs with the repository.
 * 
 * @author brett chaldecott
 */
public class RepositoryException extends Exception implements Serializable {

    /**
     * The message for the caller.
     */
    public String message = null;
    
    /**
     * The cause of the exception.
     */
    public String cause = null;
}
