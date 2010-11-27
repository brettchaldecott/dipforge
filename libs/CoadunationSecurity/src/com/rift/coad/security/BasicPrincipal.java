/*
 * CoadunationSecurity: This library contains extra security related classes.
 * Copyright (C) 2007  Rift IT Contracting
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
 * BasicPrincipal.java
 */

// package path
package com.rift.coad.security;

// imports
import java.security.Principal;
import java.io.Serializable;

/**
 * This object is responsible for storing the principal information.
 *
 * @author brett
 */
public class BasicPrincipal implements Principal,Serializable {
    
    // private member variables
    private String name = null;
    
    /**
     * Creates a new instance of BasicPrincipal
     *
     * @param name The name of this principal
     */
    public BasicPrincipal(String name) {
        this.name = name;
    }
    
    /**
     * This method returns the name of the principal
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns true if the objects are the same type and contain the
     * same value.
     *
     * @return TRUE if equal.
     * @param value The value to perform the comparison on.
     */
    public boolean equals(Object value) {
        if ((value instanceof BasicPrincipal) &&
                ((BasicPrincipal)value).getName().equals(name)) {
            return true;
        }
        return false;
        
    }
    
    
    /**
     * This method returns the hash code of this item.
     */
    public int hashCode() {
        if (name == null) {
            return 0;
        }
        return name.hashCode();
    }
    
    
    /**
     * This method returns the string value of this object.
     */
    public String toString() {
        return name;
    }
}
