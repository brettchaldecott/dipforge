/*
 * <Add library description here>
 * Copyright (C) 2007 2015 Burntjam
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
 * RPCDataTest.java
 */

package com.rift.coad.daemon.messageservice.rpc.test;

/**
 * This object is used to test the serialization and deserialization.
 *
 * @author Brett Chaldecott
 */
public class RPCDataTest implements java.io.Serializable {
    
    // private member variables
    private String value = null;
    
    
    /** 
     * Creates a new instance of RPCDataTest 
     */
    public RPCDataTest(String value) {
        this.value = value;
    }
    
    
    /**
     * This method sets the value for this RPC data test object.
     *
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns the value of this object.
     *
     * @return value The value to return.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * The value from the rpc data test.
     */
    public String toString() {
        return value;
    }
}
