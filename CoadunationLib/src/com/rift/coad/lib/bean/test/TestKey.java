/*
 * CoadunationLib: The coaduntion implementation library.
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
 * TestKey.java
 *
 * The test key for the proxy test.
 */

package com.rift.coad.lib.bean.test;

/**
 * The test key for the proxy test.
 *
 * @author Brett Chaldecott
 */
public class TestKey implements java.io.Serializable {
    
    // private member variables
    private String key1 = null;
    private String key2 = null;
    
    /** Creates a new instance of TestKey */
    public TestKey() {
    }
    
    
    /** Creates a new instance of TestKey */
    public TestKey(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
    
    
    /**
     * Retrieve the key 1 value.
     */
    public String getKey1() {
        return key1;
    }
    
    
    /**
     * This method sets key 1
     */
    public void setKey1(String key1) {
        this.key1 = key1;
    }
    
    
    /**
     * Retrieve the key 2 value.
     */
    public String getKey2() {
        return key1;
    }
    
    
    /**
     * This method sets key 2
     */
    public void setKey2(String key2) {
        this.key2 = key2;
    }
    
    
    /**
     * This method returns the hash code of the test key.
     */
    public int hashCode() {
        return (key1 + key1).hashCode();
    }
    
    
    /**
     * This method returns the hash code of the test key.
     *
     * @return TRUE if equals, FALSE if not.
     * @param value The value to perform the test on.
     */
    public boolean equals(Object value) {
        if (!(value instanceof TestKey)) {
            return false;
        }
        TestKey testKey = (TestKey)value;
        return (key1.equals(testKey.getKey1()) && 
                key2.equals(testKey.getKey2()));
    }
}
