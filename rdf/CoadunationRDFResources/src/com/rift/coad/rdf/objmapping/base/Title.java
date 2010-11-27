/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  Rift IT Contracting
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
 * Title.java
 */

// the package path
package com.rift.coad.rdf.objmapping.base;

// java imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

/**
 * The new title object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("Title")
public class Title extends DataType {
    private String value;

    /**
     * The new title.
     */
    public Title() {
    }


    /**
     * The title for this object.
     *
     * @param value The new title value.
     */
    public Title(String value) {
        this.value = value;
    }


    /**
     * This method returns the value of this object.
     *
     * @return The value of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#TitleValue")
    @Identifier()
    public String getValue() {
        return value;
    }


    /**
     * The new value for this object.
     *
     * @param value The value for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#TitleValue")
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns the unique id for this object.
     * 
     * @return The string containing the object identifier.
     */
    @Override
    public String getObjId() {
        return value;
    }
    
    
    /**
     * This method returns true if the object being compared are equal.
     * 
     * @param obj The object to perform the comparison on.
     * @return True if equals false if not.
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
        final Title other = (Title) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * This method generates the hash code value.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    
    /**
     * This operator returns the string value of this object.
     * 
     * @return The string value of this object.
     */
    @Override
    public String toString() {
        return value;
    }
    
}
