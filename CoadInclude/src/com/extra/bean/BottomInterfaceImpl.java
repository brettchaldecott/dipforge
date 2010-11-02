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
 * BottomInterfaceImpl.java
 *
 * This class is here for test purposes.
 *
 * $Revision: 1.1.1.1.2.1 $
 */

package com.extra.bean;

/**
 *
 * @author Brett Chaldecott
 */
public class BottomInterfaceImpl implements MiddleInterface {
    
    // private member variables
    private String name = null;
    private String description = null;
    
    /** Creates a new instance of BottomInterfaceImpl */
    public BottomInterfaceImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    
    /**
     * The name of this instance.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method retrieve the description of this object.
     *
     * @return The remote exception
     */
    public String getDescription() {
        return description;
    }
}
