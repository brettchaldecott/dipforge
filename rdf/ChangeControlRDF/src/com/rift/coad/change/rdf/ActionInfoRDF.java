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
package com.rift.coad.change.rdf;

import com.rift.coad.change.ActionInfo;
import com.rift.coad.change.ChangeException;
import com.rift.coad.lib.common.IDGenerator;
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import java.io.Serializable;

/**
 * The action information object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/change.action")
@LocalName("ActionInfoRDF")
public class ActionInfoRDF implements Serializable {
   
    // private member variable
    private String id;
    private String action;
    private String project;
    private String type;
    private String file;
    private String role;

    
    /**
     * The default constructor for the action information object.
     */
    public ActionInfoRDF() {
    }
    
    /**
     * The default constructor for the action information object.
     * 
     * @action The action information
     */
    public ActionInfoRDF(ActionInfo action) {
        this.action = action.getAction();
        this.file = action.getFile();
        this.project = action.getProject();
        this.type = action.getType();
        this.role = action.getRole();
    }

    
    /**
     * This constructor sets up all the action information.
     * 
     * @param action The action information.
     * @param project The project name
     * @param type The project type.
     * @param file The file name.
     */
    public ActionInfoRDF(String action, String project, String type, String file, String role) {
        this.action = action;
        this.project = project;
        this.type = type;
        this.file = file;
        this.role = role;
    }

    
    /**
     * This method returns the id of the action info RDF
     * 
     * @return The id
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() throws ChangeControlExceptionRDF {
        try {
            IDGenerator id = IDGenerator.init();
            id.addKey(this.project);
            id.addKey(this.action);
            id.addKey(this.type);
            return this.id = id.id();
        } catch (Exception ex) {
            throw new ChangeControlExceptionRDF(
                    "Failed to generate the exception : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the id
     * 
     * @param id The id of the action information object
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }

    
    
    
    /**
     * This method returns the name of the action.
     * @return The string containing the name of the action.
     */
    @PropertyLocalName("Action")
    public String getAction() {
        return action;
    }

    /**
     * This method sets the action name.
     * 
     * @param action The string containing the action name.
     */
    @PropertyLocalName("Action")
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * This method gets the path to the file containing the action flow.
     * 
     * @return The string containing the action information path
     */
    @PropertyLocalName("File")
    public String getFile() {
        return file;
    }

    
    /**
     * This method sets the file path.
     * 
     * @param file The string containing the file.
     */
    @PropertyLocalName("File")
    public void setFile(String file) {
        this.file = file;
    }

    
    /**
     * This method retrieves the project information.
     * 
     * @return The string containing the project path.
     */
    @PropertyLocalName("Project")
    public String getProject() {
        return project;
    }

    
    /**
     * This method sets the project name.
     * 
     * @param project The string containing the project name
     */
    @PropertyLocalName("Project")
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method retrieves the type information.
     * 
     * @return The string containing the type information.
     */
    @PropertyLocalName("Type")
    public String getType() {
        return type;
    }

    
    /**
     * This method sets the type information for the object.
     * 
     * @param type The string containing the type information
     */
    @PropertyLocalName("Type")
    public void setType(String type) {
        this.type = type;
    }

    
    /**
     * This method is called to retrieve the role information.
     * 
     * @return The reference to the role.
     */
    @PropertyLocalName("Role")
    public String getRole() {
        return role;
    }

    
    /**
     * This method sets the role information.
     * 
     * @param role The role.
     */
    @PropertyLocalName("Role")
    public void setRole(String role) {
        this.role = role;
    }
    
    
    /**
     * This method returns the action information.
     * 
     * @return The action information.
     */
    public ActionInfo toAction() throws ChangeException {
        return new ActionInfo(action, project, type, file,role);
    }
    
    
    /**
     * The equals operation
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
        final ActionInfoRDF other = (ActionInfoRDF) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * The hash code for this object.
     * 
     * @return The integer hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    /**
     * The string reference.
     * 
     * @return The reference to the string.
     */
    @Override
    public String toString() {
        return "ActionInfoRDF{" + "id=" + id + ", action=" + 
                action + ", project=" + project + ", type=" + 
                type + ", file=" + file + '}';
    }

    
    
}
