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
package com.rift.coad.change.request.rdf;


// java imports
// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

// coadunation imports
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.rdf.objmapping.base.DataType;


/**
 * This object represents a request.
 *
 * @author brett
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/requestmisc#")
@RdfType("MasterRequest")
public class MasterRequest extends DataType {

    // private member variables
    private String id;
    private Request request;

    
    /**
     * This method
     */
    public MasterRequest() {
    }


    /**
     * This constructor sets the request link.
     *
     * @param request The request that identifies this object.
     */
    public MasterRequest(Request request) {
        this.id = request.getId();
        this.request = request;
    }


    /**
     * This method returns the id of the request object.
     *
     * @return The id of the request object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the id of the master request object.
     *
     * @return The string containing the master request id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/requestmisc#MasterRequestId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the master request.
     *
     * @param id The new id of the master request.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/requestmisc#MasterRequestId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This object represents a request.
     *
     * @return The request id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/requestmisc#MasterRequestRequest")
    public Request getRequest() {
        return request;
    }


    /**
     * This method sets the request information.
     *
     * @param request The request reference.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/requestmisc#MasterRequestRequest")
    public void setRequest(Request request) {
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
        final MasterRequest other = (MasterRequest) obj;
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
