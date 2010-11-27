/*
 * Email Server: The email server interface
 * Copyright (C) 2008  Rift IT Contracting
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
 * Address.java
 */

// package path
package com.rift.coad.daemon.email.types;

// imports
import com.rift.coad.daemon.email.EmailException;

/**
 * This object represents an address for a message.
 *
 * @author brett chaldecott
 */
public class Address implements java.io.Serializable {
    
    // class constants
    private final static String VALIDATION_MASK = "[a-zA-Z0-9.-]+";
    
    // private member variables
    private String value = null;
    
    /**
     * Creates a new instance of Address
     */
    public Address() {
    }
    
    
    /**
     * This constructor that sets the address value if it if valid.
     *
     * @param 
     */
    public Address(String value) throws EmailException {
        value =stripAddress(value);
        validate(value);
        this.value = value;
    }
    
    
    /**
     * This method is responsible for validating the address passed in.
     *
     * @param value This method is used to validate an address.
     * @exception EmailException
     */
    public static void validate(String value) throws EmailException {
        String[] entries = value.split("[@]");
        if (entries.length != 2) {
            throw new EmailException("The email address is invalid [" + 
                        value + "]");
        } else if (entries.length == 2 && 
                !entries[1].matches(VALIDATION_MASK)) {
                throw new EmailException("The email address is invalid [" + 
                        value + "]");
        }
    }
    
    
    /**
     * This method returns the address contained within.
     *
     * @return The string containing the address.
     */
    public String getAddress() {
        return value;
    }
    
    
    /**
     * This method sets the address requirements.
     *
     * @param value The value of this address.
     */
    public void setAddress(String value) throws EmailException {
        validate(value);
        this.value = value;
    }
    
    /**
     * This method returns the local part of the email address.
     *
     * @return The string containing the local part
     */
    public String getLocalPart(){
        if (value == null) {
            return null;
        }
        return value.split("[@]")[0];
    }
    
    
    /**
     * This method returns the domain part of the address
     *
     * @return The string containing the domain part of the address
     */
    public String getDomain() {
        if (value == null) {
            return null;
        }
        return value.split("[@]")[1];
    }
    
    
    /**
     * This method prints out the value of the address.
     */
    public String toString() {
        return value;
    }
    
    
    /**
     * This method return true if the value is the same as the one held by this
     * object.
     *
     * @return TRUE if the value is the same FALSE if not.
     * @param value The value of the string to store.
     */
    public boolean equals(Object value) {
        if (!(value instanceof Address)) {
            return false;
        } else if (value == this) {
            return true;
        }
        Address entry = (Address)value;
        return entry.toString().equals(value);
    }
    
    
    /**
     * This method is used to strip the address.
     */
    private String stripAddress(String value) {
        if (value.contains("<")) {
            value = value.substring(value.indexOf("<") + 1, value.length());
        }
        if (value.contains(">")) {
            value = value.substring(0, value.indexOf(">"));
        }
        return value;
    }
}
