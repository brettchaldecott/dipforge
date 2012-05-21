/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  Rift IT Contracting
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
 * LsActionRDFData.java
 */
package com.rift.coad.change.request.action.leviathan.requestdata;

import java.io.Serializable;

/**
 * This is the RDF property that is stored in the configuration and 
 * environmental variables
 * 
 * @author brett chaldecott
 */
public class LsActionRDFProperty implements Serializable {
    
    // class constants
    public final static String RDF_GLOBAL_PROPERTY = "rdf_global_property";
    
    // private member variables
    private String name;
    private String uri;
    private String dataTypeUri;
    private LsActionRDFData data;

    /**
     * The default constructor for the action rdf property.
     */
    public LsActionRDFProperty() {
    }

    
    /**
     * This constructor sets up all the private member variables.
     * 
     * @param name The name of the property
     * @param uri The uri of the property within the RDF.
     * @param data The reference to the data.
     */
    public LsActionRDFProperty(String name, String uri, String dataTypeUri,
            LsActionRDFData data) {
        this.name = name;
        this.uri = uri;
        this.dataTypeUri = dataTypeUri;
        this.data = data;
    }

    
    /**
     * This method returns the reference to the action data.
     * 
     * @return The action data.
     */
    public LsActionRDFData getData() {
        return data;
    }

    
    /**
     * This method sets the data object reference.
     * 
     * @param data The data reference.
     */
    public void setData(LsActionRDFData data) {
        this.data = data;
    }

    
    /**
     * This method retrieves the name of the data
     * 
     * @return The name of the property data.
     */
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of the data.
     * 
     * @param name The name of the data parameter.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method retrieves the string URI of the property.
     * 
     * @return The unique string uri of the data.
     */
    public String getUri() {
        return uri;
    }

    
    /**
     * This method sets the URI of the property.
     * 
     * @param uri The string uri.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    
    /**
     * This method returns the id of the property extracted from the uri.
     * 
     * @return The id of the entry
     */
    public String getId() {
        return this.uri.substring(this.uri.lastIndexOf("/") + 1);
    }
    
    
    /**
     * This method retrieves the data type uri.
     * 
     * @return The reference to the data type uri.
     */
    public String getDataTypeUri() {
        return dataTypeUri;
    }

    
    /**
     * This method sets the data type uri
     * 
     * @param dataTypeUri The new data type uri.
     */
    public void setDataTypeUri(String dataTypeUri) {
        this.dataTypeUri = dataTypeUri;
    }

    
    
    
    /**
     * True if the objects are of equal value.
     * 
     * @param obj The reference to the object to perform the comparison on
     * @return TRUE if the objects are of equal value.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LsActionRDFProperty other = (LsActionRDFProperty) obj;
        if ((this.uri == null) ? (other.uri != null) : !this.uri.equals(other.uri)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code of the object.
     * 
     * @return The integer hash code of the object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.uri != null ? this.uri.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value of this object.
     * 
     * @return The string value of this object
     */
    @Override
    public String toString() {
        return "LsActionRDFProperty{" + "name=" + name + ", uri=" + uri + ", dataTypeUri=" + dataTypeUri + ", data=" + data + '}';
    }

    
    
    
    
    
    
}
