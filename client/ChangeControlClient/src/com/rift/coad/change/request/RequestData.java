/*
 * ChangeControl: The request data
 * Copyright (C) 2012  2015 Burntjam
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
 * RequestData.java
 */
package com.rift.coad.change.request;

import com.rift.coad.lib.common.RandomGuid;
import java.io.Serializable;

/**
 * The request data
 * 
 * @author brett chaldecott
 */
public class RequestData implements Serializable {
    
    // private member variables
    private String id;
    private String dataType;
    private String data;
    private String name;

    /**
     * The default constructor for the request data
     */
    public RequestData() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }

    
    /**
     * The constructor that sets all the internal values.
     * 
     * @param dataType The type of data.
     * @param data The data supplied.
     * @param name The values.
     */
    public RequestData(String dataType, String data, String name) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        this.dataType = dataType;
        this.data = data;
        this.name = name;
    }

    
    /**
     * This constructor sets up all the internal private member variables.
     * 
     * @param id The id that this request data will use.
     * @param dataType The data type for this request data.
     * @param data The data type.
     * @param name The name of the variable.
     */
    public RequestData(String id, String dataType, String data, String name) {
        this.id = id;
        this.dataType = dataType;
        this.data = data;
        this.name = name;
    }
    
    
    /**
     * This method returns the data information.
     * 
     * @return The data.
     */
    public String getData() {
        return data;
    }

    
    /**
     * This method sets up the data.
     * 
     * @param data This method sets the data information.
     */
    public void setData(String data) {
        this.data = data;
    }

    
    /**
     * This method sets the data type information.
     * 
     * @return The string containing the data type information.
     */
    public String getDataType() {
        return dataType;
    }
    
    
    /**
     * This method sets the data type information.
     * 
     * @param dataType The string containing the id type
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    
    /**
     * This method sets the ID
     * 
     * @return The string containing the ID of the type
     */
    public String getId() {
        return id;
    }

    
    /**
     * This method gets the ID for the type.
     * 
     * @param id The id for the type.
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method retrieves the name of the request data.
     * 
     * @return The name of tge type.
     */
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name
     * 
     * @param name The string containing the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method returns TRUE if the values are equal
     * @param obj The reference to the object.
     * @return TRUE if equal FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestData other = (RequestData) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns the hash code
     * 
     * @return The int for the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 13 * hash + (this.dataType != null ? this.dataType.hashCode() : 0);
        hash = 13 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string value for this object.
     * 
     * @return The string value for this object.
     */
    @Override
    public String toString() {
        return "RequestData{" + "id=" + id + ", dataType=" + dataType + 
                ", data=" + data + ", name=" + name + '}';
    }
    
    
    
    
}
