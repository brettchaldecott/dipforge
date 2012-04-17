/*
 * ChangeControl: The change control implementation
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
 * ActionInstanceInfoRDF.java
 */

// package path
package com.rift.coad.change.rdf;

import com.rift.coad.change.ActionConstants;
import com.rift.coad.change.request.Request;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;

/**
 * This object represents the action instance information.
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/change.actioninstance")
@LocalName("ActionInstanceInfoRDF")
public class ActionInstanceInfoRDF {
    
    // private member variable
    private String id;
    private ActionInfoRDF actionInfo;
    private RequestRDF request;
    private String masterRequestId;
    private String status;
    private String flowId;

    
    /**
     * The default constructor of the action instance RDF
     */
    public ActionInstanceInfoRDF() {
    }

    
    /**
     * The constructor of the action instance info RDF object.
     * 
     * @param id The id of the action.
     * @param actionInfo The action info.
     * @param request The reference to the request.
     * @param masterRequestId The master request id.
     * @param flowId The flow id.
     */
    public ActionInstanceInfoRDF(String id, ActionInfoRDF actionInfo,
            RequestRDF request, String masterRequestId,String flowId) {
        this.id = id;
        this.actionInfo = actionInfo;
        this.request = request;
        this.masterRequestId = masterRequestId;
        this.flowId = flowId;
        this.status = ActionConstants.INIT;
    }
    
    
    /**
     * This constructor sets up the action instance information RDF
     * 
     * @param info The reference to the action information RDF.
     */
    public ActionInstanceInfoRDF(ActionInfoRDF info, RequestRDF request, 
            String masterRequestID) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        this.actionInfo = info;
    }

    
    /**
     * This method retrieves the action information RDF.
     * 
     * @return The reference to the action information.
     */
    @PropertyLocalName("ActionFlow")
    public ActionInfoRDF getActionInfo() {
        return actionInfo;
    }

    
    /**
     * This method sets the the action information.
     * 
     * @param actionInfo The action information.
     */
    @PropertyLocalName("ActionFlow")
    public void setActionInfo(ActionInfoRDF actionInfo) {
        this.actionInfo = actionInfo;
    }

    
    /**
     * This method gets the master request id.
     * 
     * @return The string containing the master request id.
     */
    @PropertyLocalName("MasterRequestId")
    public String getMasterRequestId() {
        return masterRequestId;
    }

    
    /**
     * This method sets the master request id.
     * 
     * @param masterRequestId The new master request id.
     */
    @PropertyLocalName("MasterRequestId")
    public void setMasterRequestId(String masterRequestId) {
        this.masterRequestId = masterRequestId;
    }

    
    /**
     * This method gets the request information.
     * 
     * @return The reference to the request.
     */
    @PropertyLocalName("Request")
    public RequestRDF getRequest() {
        return request;
    }

    
    /**
     * This method sets the request.
     * 
     * @param request The request information.
     */
    @PropertyLocalName("Request")
    public void setRequest(RequestRDF request) {
        this.request = request;
    }

    
    
    
    /**
     * This method gets the flow ID.
     * 
     * @return The string containing the flow ID.
     */
    @PropertyLocalName("FlowId")
    public String getFlowId() {
        return flowId;
    }

    
    /**
     * This method sets the flow id.
     * 
     * @param flowId The string containing the flow id.
     */
    @PropertyLocalName("FlowId")
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    
    /**
     * This method returns the id of the action.
     * 
     * @return The string containing the id for this object.
     */
    @com.rift.coad.rdf.semantic.annotation.Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }

    
    /**
     * This method sets the id of this object.
     * 
     * @param id The string containing the id of this object.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method gets the status.
     * 
     * @return This method retrieves the status of the action instance.
     */
    @PropertyLocalName("Status")
    public String getStatus() {
        return status;
    }
    
    
    /**
     * This method sets the status of the action instance.
     * 
     * @param status The status of the action instance.
     */
    @PropertyLocalName("Status")
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
    /**
     * Returns true if the object are equal.
     * 
     * @param obj The object to perform the comparison on.
     * @return True if equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionInstanceInfoRDF other = (ActionInstanceInfoRDF) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code for this object.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.actionInfo != null ? this.actionInfo.hashCode() : 0);
        hash = 23 * hash + (this.flowId != null ? this.flowId.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value of the action.
     * 
     * @return The string containing the action instance value.
     */
    @Override
    public String toString() {
        return "ActionInstanceInfoRDF{" + "id=" + id + 
                ", actionInfo=" + actionInfo + ", request=" + request + 
                ", masterRequestId=" + masterRequestId + ", flowId=" +
                flowId + '}';
    }

    
    
    
    
    
    
}
