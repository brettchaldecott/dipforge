/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2012  2015 Burntjam
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
 * Property.java
 */
package com.rift.coad.rdf.semantic;

// the property information
import java.net.URI;


/**
 * The basic property
 * 
 * @author brett chaldecott
 */
public interface Property {
    
    /**
     * This method returns the type uri
     * 
     * @return The reference to the URI
     * @exception ResourceException
     */
    public URI getTypeURI() throws ResourceException;
    
    
    /**
     * This method returns TRUE for the basic type and FALSE if it is not.
     * 
     * @return True if this is a basic Type
     * @throws ResourceException 
     */
    public boolean isBasicType() throws ResourceException;
    
    
    /**
     * This method returns the object type identified by the class
     * 
     * @param <T> The class type.
     * @param c The reference to the class.
     * @return The property value as the requested type.
     * @throws ResourceException 
     */
    public <T> T get(Class<T> c) throws ResourceException;
}
