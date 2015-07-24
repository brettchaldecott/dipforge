/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * PersistanceIdentifier.java
 */


package com.rift.coad.rdf.semantic.persistance;

import java.net.URI;

/**
 * This object represents the identification information for a resource or property.
 *
 * @author brett chaldecott
 */
public class PersistanceIdentifier {
    // private member variables.
    private String namespace;
    private String localname;

    
    /**
     * The private constructor of the persistance identifier.
     *
     * @param namespace The namespace for the persistance identifier.
     * @param localname The local name.
     */
    private PersistanceIdentifier(String namespace, String localname) {
        this.namespace = namespace;
        if (this.namespace.endsWith("#")) {
            this.namespace = 
                    this.namespace.substring(0,this.namespace.length() -1);
        }
        this.localname = localname;
    }

    
    /**
     * This method returns an instance of the persistance identifier.
     *
     * @param namespace The name space.
     * @param localname The localname for the entry.
     * @return The reference to the identifier.
     */
    public static PersistanceIdentifier getInstance(String namespace, String localname) {
        return new PersistanceIdentifier(namespace,localname);
    }


    /**
     * The local name for the persistance identifier.
     *
     * @return The reference to the local name.
     */
    public String getLocalname() {
        return localname;
    }


    /**
     * The local name.
     *
     * @param localname The string 
     */
    public void setLocalname(String localname) {
        this.localname = localname;
    }


    /**
     * This method returns the namespace for this identifier
     *
     * @return The namespace value.
     */
    public String getNamespace() {
        return namespace;
    }


    /**
     * Set the namespace value.
     *
     * @param namespace The string containing the namespace value.
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * This method returns the URI value of this persistance identifier.
     *
     * @return The URI that identifies the object.
     */
    public URI toURI() throws PersistanceException {
        try {
            // generate the URI
            return new URI(namespace + "#" + localname);
        } catch (Exception ex) {
            throw new PersistanceException(
                    "Failed to construct the URI for the identifier : " +
                    ex.getMessage());
        }
    }


    /**
     * This method returns TRUE if the objects are equal FALSE if not.
     * 
     * @param obj This method returns true if the object is equal.
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
        final PersistanceIdentifier other = (PersistanceIdentifier) obj;
        if ((this.namespace == null) ? (other.namespace != null) : !this.namespace.equals(other.namespace)) {
            return false;
        }
        if ((this.localname == null) ? (other.localname != null) : !this.localname.equals(other.localname)) {
            return false;
        }
        return true;
    }
    

    /**
     * This method returns the hash code for this object.
     *
     * @return The list of hash codes.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.namespace != null ? this.namespace.hashCode() : 0);
        hash = 47 * hash + (this.localname != null ? this.localname.hashCode() : 0);
        return hash;
    }


    /**
     * The to string method.
     * 
     * @return The string containing the persistance information.
     */
    @Override
    public String toString() {
        return "PersistanceIdentifier{" + "namespace=" + namespace + "localname=" + localname + '}';
    }




}
