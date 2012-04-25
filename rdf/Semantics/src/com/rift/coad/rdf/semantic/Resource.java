/*
 * Semantics: The semantic library for coadunation os
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
 * Resource.java
 */

// package path
package com.rift.coad.rdf.semantic;

// java imports
import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * This is the resource interface for the coadunation semantic library.
 *
 * @author brett chaldecott
 */
public interface Resource {

    /**
     * This method returns the URI of this object.
     *
     * @return The uri of this object.
     * @throws ResourceException
     */
    public URI getURI() throws ResourceException;

    /**
     * This method returns the object instance identifed by the type information.
     *
     * @param <T> The type information for this call.
     * @param t The type to perform the cast to.
     * @return This method contains the type information.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public <T> T get(Class<T> t) throws ResourceException;


    /**
     * The method adds properties to the resource.
     *
     * @param url The url for the property.
     * @param value The value to add.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public <T> T addProperty(String url, T value) throws ResourceException;
    
    
    /**
     * The method sets properties to the resource.
     *
     * @param url The url for the property.
     * @param value The value to set.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public <T> T setProperty(String url, T value) throws ResourceException;

    
    /**
     * The method adds properties to the resource.
     *
     * @param url The url for the property.
     * @param obj The object to add to the property.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public <T> T getProperty(String url, Class<T> c) throws ResourceException;


    
    /**
     * This method removes the property.
     *
     * @param url The url of the propety.
     * @param objectType The object type.
     * @param identifier The identifier.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void removeProperty(String url) throws ResourceException;


    /**
     * This method is called to remove the property from the resource.
     *
     * @param url The url of the property to remove.
     * @param resource The resource name
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void removeProperty(String url, Resource resource) throws ResourceException;


    /**
     * This method lists properties.
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List listProperties() throws ResourceException;


    /**
     * This method lists properties.
     * @param url The url of the property to retrieve.
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List listProperties(String url) throws ResourceException;
}
