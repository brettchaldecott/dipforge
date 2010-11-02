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

package com.rift.coad.rdf.semantic;

// java imports
import java.io.Serializable;
import java.util.List;

/**
 * This is the resource interface for the coadunation semantic library.
 *
 * @author brett chaldecott
 */
public interface Resource {
    /**
     * This object is responsible for updating
     * 
     * @param reference The reference to the object to update.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void update(Object reference) throws ResourceException;

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
     * @param obj The object to add to the property.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public Resource addProperty(String url, Object obj) throws ResourceException;

    /**
     * This method adds the resource to the property.
     *
     * @param url The url for the resource.
     * @param resource The resource to add as a property.
     * @return The Resource value.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public Resource addProperty(String url, Resource resource) throws ResourceException;


    /**
     * This method removes the property.
     *
     * @param url The url of the propety.
     * @param objectType The object type.
     * @param identifier The identifier.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void removeProperty(String url, Object objectType, Serializable identifier) throws ResourceException;


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
     * @param url The url of the property to retrieve.
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List<Resource> listProperties(String url) throws ResourceException;
}
