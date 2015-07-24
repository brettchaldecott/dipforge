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
 * Surname.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base.name;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.Name;
        

/**
 * The surname of a user.
 * 
 * @author brett chaldecott
 */
public class FirstNames extends Name {

    /**
     * The default constructor
     */
    public FirstNames() {
    }

    /**
     * The surname to perform the search on.
     * 
     * @param value
     */
    public FirstNames(String value) {
        super(value);
    }
    
    
    
}
