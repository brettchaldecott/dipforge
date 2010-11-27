/*
 * <Add library description here>
 * Copyright (C) 2006  Rift IT Contracting
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
 * ValueObject.java
 *
 * <place file description here>
 *
 * Revision: $ID
 */

package com.test;

/**
 *
 * @author Brett Chaldecott
 */
public class ValueObject implements java.io.Serializable {
    
    private String value = null;
    
    /** Creates a new instance of ValueObject */
    public ValueObject() {
    }
    
    /**
     *
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * The value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
