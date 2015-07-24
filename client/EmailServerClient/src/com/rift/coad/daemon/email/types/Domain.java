/*
 * Email Server: The email server interface
 * Copyright (C) 2008  2015 Burntjam
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
 * Domain.java
 */

// package path
package com.rift.coad.daemon.email.types;

// imports
import com.rift.coad.daemon.email.EmailException;

/**
 * This object is responsible for acting as a domain.
 *
 * @author brett chaldecott
 */
public class Domain {
    
    // class constants
    private final static String VALIDATION_MASK = "[a-zA-Z0-9.-]+";
    
    // private member variables
    private String value = null;
    
    /**
     * Creates a new instance of Domain
     */
    public Domain() {
    }
    
    
    /**
     * This constructor is responsible for creating a new domain and setting 
     * the value.
     *
     * @param value The value to set in the object.
     */
    public Domain(String value) throws EmailException {
        validate(value);
        this.value = value;
    }
    
    
    /**
     * This method is responsible for validating the domain.
     *
     * @param value This method is used to validate an address.
     * @exception EmailException
     */
    public static void validate(String value) throws EmailException {
        if ((value != null) && !value.matches(VALIDATION_MASK)) {
                throw new EmailException("The domain is invalid [" + 
                        value + "]");
        }
    }
    
    
    /**
     * This method sets the value for the object.
     *
     * @return The value for this object.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value for the object.
     *
     * @param value The new value.
     * @exception EmailException
     */
    public void setValue(String value) throws EmailException {
        validate(value);
        this.value = value;
    }
    
    
    /**
     * This method returns the string value.
     *
     * @return The string value.
     */
    public String toString() {
        return value;
    }
}
