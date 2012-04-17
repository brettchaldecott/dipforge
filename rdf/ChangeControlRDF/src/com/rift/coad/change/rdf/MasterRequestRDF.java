/*
 * ChangeControlManager: The manager for the change events.
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
 * MasterRequest.java
 */

// package import
package com.rift.coad.change.rdf;

import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import java.io.Serializable;


/**
 * This object represents a request.
 *
 * @author brett
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/change.master")
@LocalName("MasterRequest")
public class MasterRequestRDF implements Serializable {

    // private member variables
    private String id;
    private RequestRDF request;

    
    /**
     * This method
     */
    public MasterRequestRDF() {
    }


    /**
     * This constructor sets the request link.
     *
     * @param request The request that identifies this object.
     */
    public MasterRequestRDF(RequestRDF request) {
        this.id = request.getId();
        this.request = request;
    }


    /**
     * This method returns the id of the master request object.
     *
     * @return The string containing the master request id.
     */
    @com.rift.coad.rdf.semantic.annotation.Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the master request.
     *
     * @param id The new id of the master request.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This object represents a request.
     *
     * @return The request id.
     */
    @PropertyLocalName("Request")
    public RequestRDF getRequest() {
        return request;
    }


    /**
     * This method sets the request information.
     *
     * @param request The request reference.
     */
    @PropertyLocalName("Request")
    public void setRequest(RequestRDF request) {
        this.request = request;
    }

    /**
     * The equals operator.
     *
     * @param obj The object to perform the comparison on.
     * @return The return result.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MasterRequestRDF other = (MasterRequestRDF) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code of this object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    
    
}
