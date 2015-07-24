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
 * SoftwareService.java
 */

package com.rift.coad.rdf.objmapping.client.service;

// coadunation rdf imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;

/**
 * This software service represents software service on a network.
 *
 * @author brett chaldecott
 */
public class SoftwareService extends ResourceBase {

    // private member variables
    private String id;
    private String hostname;
    private String name;


    /**
     * The default constructor
     */
    public SoftwareService() {
    }


    /**
     * This constructor sets the hostname and name and derives the id from those
     * two values.
     * 
     * @param hostname The name of the host this software service is attached to.
     * @param name
     */
    public SoftwareService(String hostname, String name) {
        this.hostname = hostname;
        this.name = name;
        this.setIdValue();
    }

    
    /**
     * This constructor sets all the software values.
     * 
     * @param id The id of the software service.
     * @param hostname The hostname of the software service.
     * @param name The name of the software service.
     */
    public SoftwareService(String id, String hostname, String name) {
        this.id = id;
        this.hostname = hostname;
        this.name = name;
    }


    /**
     * This method gets the id of the software service.
     * 
     * @return The string containing the id of software service.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the software service
     *
     * @param id The id of the software service.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the software service id.
     *
     * @return The string containing the software service host name.
     */
    public String getHostname() {
        return hostname;
    }


    /**
     * This method sets the hostname of the software service.
     *
     * @param hostname The string containing the hostname of the software service.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
        this.setIdValue();
    }


    /**
     * This method retrieves the name of the software service.
     *
     * @return The string containing the name of the software service.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the software service.
     *
     * @param name The name of the software service
     */
    public void setName(String name) {
        this.name = name;
        this.setIdValue();
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
     * This method determines if an object equal by comparing the id values.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoftwareService other = (SoftwareService) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method retrieves the hash code for the software service.
     *
     * @return The hash code for the object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value of this service.
     *
     * @return The string containing the string representation of this object.
     */
    @Override
    public String toString() {
        return this.id + " " + this.hostname + " " + this.name;
    }
    
}
