/*
 * ChangeControlManager: The manager for the change events.
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
 * ServiceRPCCall.java
 */
package com.rift.coad.change.request.action.leviathan.rpc;

// imports
import java.io.Serializable;

/**
 * This object represents a service call.
 * 
 * @author brett chaldecott
 */
public class ServiceRPCCall implements Serializable {
    
    // private member variables
    private String service;
    private String project;
    private String className;

    /**
     * This constructor sets up all the private member variables.
     */
    public ServiceRPCCall(String service, String project, String className) {
        this.service = service;
        this.project = project;
        this.className = className;
    }
    
    /**
     * This method returns the class name.
     * 
     * @return The reference to the class name.
     */
    public String getClassName() {
        return className;
    }

    
    /**
     * This method sets the class name.
     * 
     * @param className The class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    
    /**
     * This method returns the project name.
     * 
     * @return The string containing the project name.
     */
    public String getProject() {
        return project;
    }

    
    /**
     * This method sets the project name.
     * 
     * @param project The project name.
     */
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * The getter for the service information.
     * 
     * @return The string containing the service information.
     */
    public String getService() {
        return service;
    }

    
    /**
     * This method sets the service information.
     * 
     * @param service The string that identifies the service name.
     */
    public void setService(String service) {
        this.service = service;
    }

    
    /**
     * This method returns true if the object is equal.
     * 
     * @param obj The object to perform the comparison on now.
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
        final ServiceRPCCall other = (ServiceRPCCall) obj;
        if ((this.service == null) ? (other.service != null) : 
                !this.service.equals(other.service)) {
            return false;
        }
        if ((this.project == null) ? (other.project != null) : 
                !this.project.equals(other.project)) {
            return false;
        }
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code.
     * 
     * @return The integer hash code.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.service != null ? this.service.hashCode() : 0);
        hash = 73 * hash + (this.project != null ? this.project.hashCode() : 0);
        hash = 73 * hash + (this.className != null ? this.className.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns a string value of the object.
     * 
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return "ServiceRPCCall{" + "service=" + service + 
                ", project=" + project + ", className=" + className + '}';
    }
    
    
    
}
