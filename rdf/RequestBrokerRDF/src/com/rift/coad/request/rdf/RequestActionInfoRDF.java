/*
 * RevisionManager: The revision manager client library
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
 * RequestActionInfo.java
 */
// package path
package com.rift.coad.request.rdf;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.request.RequestActionInfo;
import java.io.Serializable;


/**
 * This object contains the action information
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/requestactioninfo")
@LocalName("RequestActionInfoRDF")
public class RequestActionInfoRDF implements Serializable {
    
    
    // private member variables
    public String id;
    public String targetUri;
    public String project;
    public String type;
    public String action;

    
    /**
     * The default constructor of the action information object.
     */
    public RequestActionInfoRDF() {
    }
    
    /**
     * The constructor that sets up the action information.
     * 
     * @param action The reference to the action
     */
    public RequestActionInfoRDF(RequestActionInfo action) {
        this.id = action.getId();
        this.targetUri = action.getTargetUri();
        this.project = action.getProject();
        this.type = action.getType();
        this.action = action.getAction();
    }
    
    /**
     * This constructor sets all request action information.
     * 
     * @param id The id of the request.
     * @param targetUri The target uri.
     * @param project The project
     * @param type The type
     * @param action The action
     */
    public RequestActionInfoRDF(String id, String targetUri, String project, 
            String type, String action) {
        this.id = id;
        this.targetUri = targetUri;
        this.project = project;
        this.type = type;
        this.action = action;
    }

    
    /**
     * The action that is being performed on the target.
     * 
     * @return The action
     */
    @PropertyLocalName("action")
    public String getAction() {
        return action;
    }

    
    /**
     * The setter for the action.
     * 
     * @param action The action information setter.
     */
    @PropertyLocalName("action")
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * The getter for the action request id.
     * 
     * @return The id of the action.
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }

    
    /**
     * The setter for the action request id.
     * 
     * @param id The id of the request.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * The getter for the project name.
     * 
     * @return The reference to the project.
     */
    @PropertyLocalName("project")
    public String getProject() {
        return project;
    }

    /**
     * The setter for project name.
     * 
     * @param project The string containing the project name.
     */
    @PropertyLocalName("project")
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method returns the target uri information
     * 
     * @return The string containing the target uri information.
     */
    @PropertyLocalName("targetUri")
    public String getTargetUri() {
        return targetUri;
    }

    /**
     * The setter for the target uri.
     * 
     * @param targetUri The string containing the target uri information.
     */
    @PropertyLocalName("targetUri")
    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    
    /**
     * The getter for the type information.
     * 
     * @return The string containing the type information.
     */
    @PropertyLocalName("type")
    public String getType() {
        return type;
    }
    
    
    /**
     * This method sets the type information.
     * 
     * @param type The string containing the type information.
     */
    @PropertyLocalName("type")
    public void setType(String type) {
        this.type = type;
    }
    
    
    /**
     * This method returns the action information
     * 
     * @return The action information for this 
     */
    public RequestActionInfo toRequestActionInfo() {
        return new RequestActionInfo(id, targetUri, project, type, action);
    }
    
    
    /**
     * The equals method.
     * 
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestActionInfoRDF other = (RequestActionInfoRDF) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    
    /**
     * The hash code.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    /**
     * The string reference.
     * @return 
     */
    @Override
    public String toString() {
        return "RequestActionInfo{" + "id=" + id + ", targetUri=" + 
                targetUri + ", project=" + project + ", type=" + type + 
                ", action=" + action + '}';
    }
    
    
    
    
    
    
}
