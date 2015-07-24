/*
 * RevisionManager: The revision manager client library
 * Copyright (C) 2012  2015 Burntjam
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
package com.rift.coad.request;

import java.io.Serializable;


/**
 * This object contains the action information
 * 
 * @author brett chaldecott
 */
public class RequestActionInfo implements Serializable {
    
    
    // private member variables
    private String id;
    private String targetUri;
    private String project;
    private String type;
    private String action;

    
    /**
     * The default constructor of the action information object.
     */
    public RequestActionInfo() {
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
    public RequestActionInfo(String id, String targetUri, String project, 
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
    public String getAction() {
        return action;
    }

    
    /**
     * The setter for the action.
     * 
     * @param action The action information setter.
     */
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * The getter for the action request id.
     * 
     * @return The id of the action.
     */
    public String getId() {
        return id;
    }

    
    /**
     * The setter for the action request id.
     * 
     * @param id The id of the request.
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * The getter for the project name.
     * 
     * @return The reference to the project.
     */
    public String getProject() {
        return project;
    }

    /**
     * The setter for project name.
     * 
     * @param project The string containing the project name.
     */
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method returns the target uri information
     * 
     * @return The string containing the target uri information.
     */
    public String getTargetUri() {
        return targetUri;
    }

    /**
     * The setter for the target uri.
     * 
     * @param targetUri The string containing the target uri information.
     */
    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    
    /**
     * The getter for the type information.
     * 
     * @return The string containing the type information.
     */
    public String getType() {
        return type;
    }
    
    
    /**
     * This method sets the type information.
     * 
     * @param type The string containing the type information.
     */
    public void setType(String type) {
        this.type = type;
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
        final RequestActionInfo other = (RequestActionInfo) obj;
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
