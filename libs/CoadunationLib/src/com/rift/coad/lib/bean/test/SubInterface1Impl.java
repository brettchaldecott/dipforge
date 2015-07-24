/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * SubInterface1Impl.java
 *
 * This is a test object
 */

// package path
package com.rift.coad.lib.bean.test;

// coadunation imports
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
        
/**
 * This is a test object.
 *
 * @author Brett Chaldecott
 */
public class SubInterface1Impl implements SubInterface1, ResourceIndex, Resource {
    
    // the release count
    public static int releaseCount = 0;
    
    
    // private member variables
    private String key = null;
    
    /** Creates a new instance of SubInterface1Impl */
    public SubInterface1Impl(String key) {
        this.key = key;
    }
    
    /**
     * The name of this interface
     */
    public String getName() {
        return key;
    }
    
    
    /**
     * This method returns the primary key of this resource to enable indexing.
     *
     * @return The primary key of this object.
     */
    public Object getPrimaryKey() {
        return key;
    }
    
    
    /**
     * This method is called to retrieve the name of the resource.
     *
     * @return The name of the resource.
     */
    public String getResourceName() {
        return key;
    }
    
    
    /**
     * This method is called to release this resource.
     */
    public void releaseResource() {
        releaseCount++;
    }
}
