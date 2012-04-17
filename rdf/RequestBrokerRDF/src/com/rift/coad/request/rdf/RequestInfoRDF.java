/*
 * RevisionManager: The revision manager client library
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
 * RequestInfo.java
 */


package com.rift.coad.request.rdf;

// coadunation imports
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import java.io.Serializable;


/**
 * The request information object.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/request")
@LocalName("RequestInfoRDF")
public class RequestInfoRDF implements Serializable {

    // private member variable
    private String id;
    private String requestId;
    private String jndi;


    /**
     * The default constructor
     */
    public RequestInfoRDF() {
    }
    
    
    /**
     * This constructor sets the id, the request id, and the jndi reference.
     *
     * @param id The 
     * @param requestId
     * @param jndi
     */
    public RequestInfoRDF(String id, String requestId, String jndi) {
        this.id = id;
        this.requestId = requestId;
        this.jndi = jndi;
    }


    /**
     * This method returns the id of the request info.
     *
     * @return The id of the request info
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }


    /**
     * This method sets the id for the request info.
     *
     * @param id The string containing the id for the request info.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the string containing the jndi reference.
     *
     * @return The string containing the jndi reference.
     */
    @PropertyLocalName("jndi")
    public String getJndi() {
        return jndi;
    }


    /**
     * The setter for the jndi reference.
     *
     * @param jndi The string containing the name for the jndi reference.
     */
    @PropertyLocalName("jndi")
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }


    /**
     * This method retrieves the request id.
     * 
     * @return This method returns the request id.
     */
    @PropertyLocalName("requestId")
    public String getRequestId() {
        return requestId;
    }


    /**
     * This method sets the request if for the request mapping.
     * @param requestId
     */
    @PropertyLocalName("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    /**
     * The equals method
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
        final RequestInfoRDF other = (RequestInfoRDF) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code for this object.
     *
     * @return The integer containing the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value of this object.
     * 
     * @return The string containing the request information.
     */
    @Override
    public String toString() {
        return "RequestInfo{" + "id=" + id + ", requestId=" + requestId + 
                ", jndi=" + jndi + '}';
    }


}
