/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  2015 Burntjam
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
 * Host.java
 */

package com.rift.coad.rdf.objmapping.inventory;

import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents a host on the network.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Host")
public class Host extends ResourceBase {
    private String hostname;

    /**
     * The default constructor of the
     */
    public Host() {
    }


    /**
     * This constructor sets the hostname for this object.
     * 
     * @param hostname The hostname for this object.
     */
    public Host(String hostname) {
        this.hostname = hostname;
    }



    /**
     * This method returns the host name of this object.
     *
     * @return The string containing the host name for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#Hostname")
    @Identifier()
    public String getHostname() {
        return hostname;
    }


    /**
     * This method sets the hostname for this object.
     * 
     * @param hostname The hostname for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#Hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }


    /**
     * This method returns the id for this object.
     *
     * @return The string containing the object id.
     */
    @Override
    public String getObjId() {
        return hostname;
    }

    /**
     * The equals operator for the host object.
     * 
     * @param obj The object.
     * @return TRUE if they are equal, FALSE if they are not.
     */
    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Host other = (Host) obj;
        if ((this.hostname == null) ? (other.hostname != null) : !this.hostname.equals(other.hostname)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.hostname != null ? this.hostname.hashCode() : 0);
        return hash;
    }

    /**
     * This method returns the string value for this object.
     *
     * @return This method returns the string value for this host
     */
    @Override
    public String toString() {
        return this.hostname;
    }




}
