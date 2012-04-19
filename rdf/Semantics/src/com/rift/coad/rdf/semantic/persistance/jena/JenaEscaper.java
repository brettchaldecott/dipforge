/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * JenaEscaper.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

/**
 * The definition of the jena escaper
 * 
 * @author brett chaldecott
 */
public interface JenaEscaper {
    
    /**
     * This method is used to escape the given string.
     * 
     * @param value The value to escape
     * @return The result of the escaping.
     */
    public String escape(String value);
}
