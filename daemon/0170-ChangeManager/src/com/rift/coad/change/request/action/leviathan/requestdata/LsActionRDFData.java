/*
 * ChangeControlManager: The manager for the change events.
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
 * LsActionRDFData.java
 */
package com.rift.coad.change.request.action.leviathan.requestdata;

import java.io.Serializable;

/**
 * This object contains all the RDF data for an action.
 * 
 * @author brett chaldecott
 */
public class LsActionRDFData implements Serializable {
    
    // all the data
    private String data;

    /**
     * The default constructor.
     */
    public LsActionRDFData() {
    }
    
    
    /**
     * 
     * @param data 
     */
    public LsActionRDFData(String data) {
        this.data = data;
    }
    
    /**
     * This method retrieves the data
     * 
     * @return 
     */
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LsActionRDFData{" + "data=" + data + '}';
    }
    
    
    
}
