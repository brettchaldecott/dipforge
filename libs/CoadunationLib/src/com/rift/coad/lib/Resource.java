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
 * Resource.java
 *
 * This resource interface. Defines methods to query and manipulate a resource
 * within Coadunation.
 */

package com.rift.coad.lib;

/**
 * This resource interface. Defines methods to query and manipulate a resource
 * within Coadunation.
 *
 * @author Brett Chaldecott
 */
public interface Resource {
    
    /**
     * This method is called to retrieve the name of the resource.
     *
     * @return The name of the resource
     */
    public String getResourceName();
    
    
    /**
     * This method is called to release this resource.
     */
    public void releaseResource();
}
