/*
 * CoadunationCRMClient: The CRM client library
 * Copyright (C) 2008  2015 Burntjam
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
 * CRMRecordEntry.java
 */

package com.rift.coad.crm.result;

/**
 * This object represents an individual column entry.
 * 
 * @author brett chaldecott
 */
public class CRMRecordEntry implements java.io.Serializable {
    // private member variables
    private String name;
    private String type;
    private Object value;

    public CRMRecordEntry() {
    }

    public CRMRecordEntry(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * This method returns a string value of the internal values
     * @return
     */
    @Override
    public String toString() {
        return "{name = " + name + ", type = " + type + ", value = " + 
                value.toString() + "}";
    }
    
    
    
    
}
