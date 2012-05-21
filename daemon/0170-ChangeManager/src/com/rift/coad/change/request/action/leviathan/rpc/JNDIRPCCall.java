/*
 * ChangeControlManager: The manager for the change events.
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
 * JNDIRPCCall.java
 */

// package path
package com.rift.coad.change.request.action.leviathan.rpc;

// java imports
import java.io.Serializable;


/**
 * The reference to the JNDI rpc call.
 * 
 * @author brett chaldecott
 */
public class JNDIRPCCall implements Serializable {
    
    // private member variables
    private String jndi;
    private String project;
    private String className;

    
    /**
     * This constructor sets up all the private member variables.
     * 
     * @param jndi The jndi reference
     * @param project The project reference
     * @param className The class name
     */
    public JNDIRPCCall(String jndi, String project, String className) {
        this.jndi = jndi;
        this.project = project;
        this.className = className;
    }
    
    
    /**
     * This method returns the class name
     * 
     * @return The string containing the class name
     */
    public String getClassName() {
        return className;
    }

    
    /**
     * This method sets the class name
     * 
     * @param className The string containing the class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    
    /**
     * The string containing the jndi value.
     * 
     * @return The jndi value.
     */
    public String getJndi() {
        return jndi;
    }
    
    
    /**
     * This method sets the jndi value.
     * 
     * @param jndi The string containing the jndi value.
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    
    /**
     * This method retrieves the project value.
     * 
     * @return The string containing the project value.
     */
    public String getProject() {
        return project;
    }

    
    /**
     * This method sets the project value.
     * 
     * @param project The string containing the project value.
     */
    public void setProject(String project) {
        this.project = project;
    }

    
    /**
     * This method returns true if the object are equal.
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
        final JNDIRPCCall other = (JNDIRPCCall) obj;
        if ((this.jndi == null) ? (other.jndi != null) : !this.jndi.equals(other.jndi)) {
            return false;
        }
        if ((this.project == null) ? (other.project != null) : !this.project.equals(other.project)) {
            return false;
        }
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method performs the comparison on the hash code value.
     * 
     * @return This method returns the hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.jndi != null ? this.jndi.hashCode() : 0);
        hash = 41 * hash + (this.project != null ? this.project.hashCode() : 0);
        hash = 41 * hash + (this.className != null ? this.className.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value.
     * 
     * @return The string value.
     */
    @Override
    public String toString() {
        return "JNDIRPCCall{" + "jndi=" + jndi + ", project=" + project + ", className=" + className + '}';
    }
    
    
    
}
