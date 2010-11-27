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
 * URL.java
 */


package com.rift.coad.rdf.objmapping.base;

// java imports
import java.net.URI;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

// coadunation imports
import com.rift.coad.rdf.objmapping.utils.HashUtil;


/**
 * This object represents a url.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("URL")
public class URL extends DataType {
    
    private URI value;

    
    /**
     * The default constructor.
     */
    public URL() {
    }

    
    /**
     * This constructor sets the values appropriatly.
     * 
     * @param value The new value.
     */
    public URL(URI value) {
        this.value = value;
    }

    
    /**
     * The getter for the value.
     * 
     * @return The object containing the value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#URLValue")
    @Identifier()
    public URI getValue() {
        return value;
    }
    
    
    /**
     * The setter for the value.
     * 
     * @param value The new value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#URLValue")
    public void setValue(URI value) {
        this.value = value;
    }

    
    /**
     * This method returns the unique identifier for this object instance.
     * 
     * @return The string containing the unqueue identifier for this object.
     */
    @Override
    public String getObjId() {
        if (value == null) {
            return "";
        }
        return HashUtil.md5(value.toString());
    }
    
    
    /**
     * The equals operator.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal, FALSE if not.
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
        final URL other = (URL) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This operator returns the hash code value of this object.
     * 
     * @return The integer containing the hash code value of this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of this object.
     * 
     * @return The string value of the URI.
     */
    @Override
    public String toString() {
        return value.toString();
    }
    
    
    
    
    
    
}
