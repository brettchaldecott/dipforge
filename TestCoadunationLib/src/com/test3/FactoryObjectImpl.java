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
 * FactoryObjectImpl.java
 *
 * A test factory object.
 */

// package path
package com.test3;

// java imports
import java.util.List;
import java.util.ArrayList;

// coadunation imports
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.lib.bean.test.CalledObjects;

/**
 * A test factory object.
 *
 * @author Brett Chaldecott
 */
public class FactoryObjectImpl implements FactoryInterface, 
        Resource, ResourceIndex {
    
    // private member variables
    private String name = null;
    
    /**
     * The factory object
     */
    public FactoryObjectImpl(String name) {
        this.name = name;
    }
    
    
    /**
     *
     */
    public int getInt(String bob) {
        return 1;
    }
    
    /**
     * An integer array
     */
    public int[] getIntArray(String[] bob) throws 
            java.rmi.NoSuchObjectException {
        return new int[] {1,2,3};
    }
    
    /**
     * The get list method
     */
    public List getList(String fred) {
        ArrayList list = new  ArrayList();
        list.add("test");
        list.add("test2");
        return list;
    }
    
    /**
     * This is a void method
     */
    public void parentMethod(String bob) {
        
    }
    
    
    /**
     * This method is called to retrieve the name of the resource.
     *
     * @return The name of the resource
     */
    public String getResourceName() {
        return name;
    }
    
    
    /**
     * This method is called to release this resource.
     */
    public void releaseResource() {
        CalledObjects.releasedCount++;
    }
    
    
    /**
     * This method returns the primary key of this resource to enable indexing.
     *
     * @return The primary key of this object.
     */
    public Object getPrimaryKey() {
        return name;
    }
}
