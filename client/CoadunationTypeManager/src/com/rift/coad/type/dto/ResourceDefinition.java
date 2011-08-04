/*
 * CoadunationTypeManage: The resource type manager.
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
 * ResourceDefinition.java
 */

package com.rift.coad.type.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This contains the resource information.
 *
 * @author brett chaldecott
 */
public class ResourceDefinition implements Serializable {

    // private member variables
    private String namespace;
    private String localname;
    private Map<String,RDFDataType> properties;

    /**
     * The default constructor.
     */
    public ResourceDefinition() {
        properties = new HashMap<String,RDFDataType>();
    }


    /**
     * The resource definition.
     *
     * @param namespace The name space information.
     * @param localname The local name.
     */
    public ResourceDefinition(String namespace, String localname) {
        this.namespace = namespace;
        this.localname = localname;
        this.properties = new HashMap<String,RDFDataType>();
    }


    /**
     * The resource definition.
     *
     * @param namespace The name space information.
     * @param localname The local name.
     * @param properties The properties.
     */
    public ResourceDefinition(String namespace, String localname, Map<String, RDFDataType> properties) {
        this.namespace = namespace;
        this.localname = localname;
        this.properties = properties;
    }


    /**
     * The localname.
     *
     * @return The localname.
     */
    public String getLocalname() {
        return localname;
    }


    /**
     * This method sets the local name.
     *
     * @param localname The local name
     */
    public void setLocalname(String localname) {
        this.localname = localname;
    }


    /**
     * This method returns the name space.
     *
     * @return The name space string.
     */
    public String getNamespace() {
        return namespace;
    }


    /**
     * This method sets the name space string.
     *
     * @param namespace The name space.
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    /**
     * The properties attached to this resource.
     * 
     * @return The properties.
     */
    public Map<String, RDFDataType> getProperties() {
        return properties;
    }


    /**
     * This method returns the url of this object.
     *
     * @return The string containing the URL of this object.
     */
    public String toURL() {
        return namespace + "#" + localname;
    }


    /**
     *
     * @param properties
     */
    public void setProperties(Map<String, RDFDataType> properties) {
        this.properties = properties;
    }


    /**
     * This method adds the property.
     *
     * @param name The name of the property.
     * @param type The property.
     */
    public void addProperty(String name, RDFDataType type) {
        properties.put(name, type);
    }

    
    /**
     * This method sets the equal sign.
     *
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceDefinition other = (ResourceDefinition) obj;
        if ((this.namespace == null) ? (other.namespace != null) : !this.namespace.equals(other.namespace)) {
            return false;
        }
        if ((this.localname == null) ? (other.localname != null) : !this.localname.equals(other.localname)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.namespace != null ? this.namespace.hashCode() : 0);
        hash = 41 * hash + (this.localname != null ? this.localname.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ResourceDefinition{" + "namespace=" + namespace + "localname=" + localname + "properties=" + properties + '}';
    }



    
    

}
