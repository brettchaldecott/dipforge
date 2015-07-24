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
 * LsStoreProperty.java
 */
package com.rift.coad.change.request.action.leviathan.store;

import java.io.Serializable;

/**
 * The ls store property
 * 
 * @author brett chaldecott
 */
public class LsStoreProperty implements Serializable {
    
    // private member variables
    private String name;

    /**
     * This constructor sets up all the 
     * 
     * @param name The name of the parameter.
     */
    public LsStoreProperty(String name) {
        this.name = name;
    }

    /**
     * This method returns the name of the store property.
     * 
     * @return The string containing the name of the store property.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method sets the name of the property.
     * 
     * @param name The new name of the property
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method returns TRUE if the object is equal.
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LsStoreProperty other = (LsStoreProperty) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code value.
     * 
     * @return The reference to the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of the store.
     * 
     * @return The string value
     */
    @Override
    public String toString() {
        return "LsStoreProperty{" + "name=" + name + '}';
    }
    
}
