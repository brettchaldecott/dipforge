/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  2015 Burntjam
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
 * ClearTextPassword.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.password;

// semantic imports
import com.rift.coad.rdf.objmapping.base.Password;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

// log4j import
import org.apache.log4j.Logger;

/**
 * This object represents a password object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("ClearTextPassword")
public class ClearTextPassword extends Password {

    // class singletons
    private static Logger log = Logger.getLogger(ClearTextPassword.class);

    // private values
    private String id;
    private String value;
    
    
    /**
     * The password value.
     */
    public ClearTextPassword() {
    }

    
    /**
     * The constructor that sets the value.
     * 
     * @param value The default value
     */
    public ClearTextPassword(String id, String value) {
        this.id = id;
        this.value = value;
    }
    
    
    /**
     * The id of the password
     * 
     * @return The id of the password.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * Set the id of the clear text value.
     *
     * @param id The id of the clear text password.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordId")
    public void setId(String id) {
        this.id = id;
    }
    

    /**
     * This method returns the object identifier.
     *
     * @return The string containing the object identifier.
     */
    @Override
    public String getObjId() {
        return this.id;
    }

    
    /**
     * This method returns the value of the password.
     * 
     * @return The string containing the value of the password.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    public String getValue() {
        return value;
    }

    
    /**
     * This method sets the value for the password.
     * 
     * @param value The new password value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This operator checks the password value.
     * 
     * @param obj The object containing the password value.
     * @return TRUE if the values are equal, FALSE if they are not.
     */
    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClearTextPassword other = (ClearTextPassword) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This operator returns the hash code.
     * 
     * @return The integer containing the hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value of this object.
     * 
     * @return This method returns the string value.
     */
    @Override
    public String toString() {
        return this.value;
    }
    
    
    
    
}
