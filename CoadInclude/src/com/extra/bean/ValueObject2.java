/*
 * CoadunationInclude: A test library.
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
 * ValueObject2.java
 */

package com.extra.bean;

/**
 * This is a test object.
 *
 * @author Brett Chaldecott
 */
public class ValueObject2 implements java.io.Serializable {
    
    // private member variables
    private String value = "testing";
    
    /** 
     * Creates a new instance of ValueObject2 
     */
    public ValueObject2() {
    }
    
    
    /**
     * This method returns the value.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * This method is responsible for setting the new value.
     *
     * @param value The value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
