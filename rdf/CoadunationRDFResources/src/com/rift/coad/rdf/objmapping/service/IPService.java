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
 * IPService.java
 */

// package path
package com.rift.coad.rdf.objmapping.service;

// imports
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This service represents an ip bound service, such as http, mail etc. It is
 * identified by an id value which can be initialy generated by combining both
 * the hostname and service name. Alternatively an id can be set for this object.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/service#")
@RdfType("IPService")
public class IPService extends ResourceBase {
    
    // private member variables
    private String id;
    private String hostname;
    private String name;

    /**
     * The default constructor for the ip service
     */
    public IPService() {
    }


    /**
     * This constructor of the ip service
     *
     * @param hostname The hostname for the ip service.
     * @param name The name of this service.
     */
    public IPService(String hostname, String name) {
        // this sets the initial id.
        this.id = hostname + name;
        this.hostname = hostname;
        this.name = name;
    }

    /**
     * This constructor sets all the id information.
     *
     * @param id The id of the
     * @param hostname The hostname for this service.
     * @param name The name for this service.
     */
    public IPService(String id, String hostname, String name) {
        this.id = id;
        this.hostname = hostname;
        this.name = name;
    }


    /**
     * This method returns the object id.
     *
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the id of this object.
     *
     * @return The string containing the id of his object
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPID")
    @Identifier()
    public String getId() {
        return id;
    }

    /**
     * This method sets the id of the ip service.
     *
     * @param id The id of the ip service.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPID")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method gets the host name for this service
     *
     * @return The string containing the host name for this service.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPHostname")
    public String getHostname() {
        return hostname;
    }

    /**
     * This method sets the host name for this service and the id for this service if it is not set already.
     * 
     * @param hostname The host name for this service
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPHostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
        setIdValue();
    }


    /**
     * This method returns the name of the ip service.
     *
     * @return The string containing the name of the ip service
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPName")
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of the ip service.
     *
     * @param name The string containing the name of the ip service.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/service#IPName")
    public void setName(String name) {
        this.name = name;
        setIdValue();
    }


    /**
     * This method sets the id value of the ip service if it is not set.
     */
    private void setIdValue() {
        if (id == null && name != null && hostname != null) {
            id = hostname + name;
        }
    }


    /**
     * This method checks if the current ip service equals another one.
     *
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
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
        final IPService other = (IPService) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the IP service.
     *
     * @return The string containing the ip service hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string representation of this object.
     *
     * @return The string representation for the ip service.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", this.id, this.hostname, this.name);
    }

}
