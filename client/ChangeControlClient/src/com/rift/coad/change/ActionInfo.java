/*
 * ChangeControlClient: The client library for the change control client.
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
 * ChangeManagerMBean.java
 */

// package path
package com.rift.coad.change;

import java.io.Serializable;

/**
 * The action information object.
 * 
 * @author brett chaldecott
 */
public class ActionInfo implements Serializable {
   
    // private member variable
    private String action;
    private String project;
    private String type;
    private String file;
    private String role;

    
    /**
     * The default constructor for the action information object.
     */
    public ActionInfo() {
    }

    
    /**
     * This constructor sets up all the action information.
     * 
     * @param action The action information.
     * @param project The project name
     * @param type The project type.
     * @param file The file name.
     */
    public ActionInfo(String action, String project, String type, 
            String file, String role) throws ChangeException {
        if (action == null || project == null || type == null || file == null 
                || role == null) {
            throw new ChangeException("Must provide all the propertys to the action information object");
        }
        this.action = action;
        this.project = project;
        this.type = type;
        this.file = file;
        this.role = role;
    }

    
    
    
    /**
     * This method returns the name of the action.
     * @return The string containing the name of the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * This method sets the action name.
     * 
     * @param action The string containing the action name.
     */
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * This method gets the path to the file containing the action flow.
     * 
     * @return The string containing the action information path
     */
    public String getFile() {
        return file;
    }

    
    /**
     * This method sets the file path.
     * 
     * @param file The string containing the file.
     */
    public void setFile(String file) {
        this.file = file;
    }

    
    /**
     * This method retrieves the project information.
     * 
     * @return The string containing the project path.
     */
    public String getProject() {
        return project;
    }

    
    /**
     * This method sets the project name.
     * 
     * @param project The string containing the project name
     */
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method retrieves the type information.
     * 
     * @return The string containing the type information.
     */
    public String getType() {
        return type;
    }

    
    /**
     * This method sets the type information for the object.
     * 
     * @param type The string containing the type information
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /**
     * This method retrieves the role information.
     * 
     * @return The string containing the role information.
     */
    public String getRole() {
        return role;
    }

    
    /**
     * The string containing the role information.
     * 
     * @param role The string containing the role
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
    /**
     * This method returns the string value.
     * 
     * @return The string value.
     */
    @Override
    public String toString() {
        return "ActionInfo{" + "action=" + action + ", project=" + 
                project + ", type=" + type + ", file=" + file + 
                ", role=" + role + '}';
    }

    
    /**
     * This method performs the equals operation.
     * 
     * @param obj The object to perform the comparison on.
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
        final ActionInfo other = (ActionInfo) obj;
        if ((this.action == null) ? (other.action != null) : !this.action.equals(other.action)) {
            return false;
        }
        if ((this.project == null) ? (other.project != null) : !this.project.equals(other.project)) {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if ((this.file == null) ? (other.file != null) : !this.file.equals(other.file)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * The hash code.
     * 
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.action != null ? this.action.hashCode() : 0);
        hash = 59 * hash + (this.project != null ? this.project.hashCode() : 0);
        hash = 59 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 59 * hash + (this.file != null ? this.file.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
