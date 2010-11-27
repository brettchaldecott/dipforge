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
 * MemoryNamingEnumeration.java
 *
 * This object implements the Naming enumeration for the memory context.
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.util.Enumeration;
import javax.naming.NamingEnumeration;

/**
 * This object implements the Naming enumeration for the memory context.
 *
 * @author Brett Chaldecott
 */
public class MemoryNamingEnumeration implements NamingEnumeration {
    
    // private member variables
    private Enumeration enumer = null;
    
    /** 
     * Creates a new instance of MemoryNamingEnumeration 
     *
     * @param enumer The enumeration reference being wrapped
     */
    public MemoryNamingEnumeration(Enumeration enumer) {
        this.enumer = enumer;
    }
    
    
    /**
     * Closes this enumeration.
     */
    public void close() {
        
    }
    
    /**
     * Determines whether there are any more elements in the enumeration.
     *
     * @return True if there are more elements.
     */
    public boolean hasMore() {
        return enumer.hasMoreElements();
    }
    
    
    /**
     * Retrieves the next element in the enumeration.
     *
     * @return The next object
     */
    public Object next() {
        return enumer.nextElement();
    }
    
    
    /**
     * Tests if this enumeration contains more elements.
     *
     * @return boolean True if has element false if not.
     */
    public boolean hasMoreElements() {
        return enumer.hasMoreElements();
    }
    
    
    /**
     * Returns the next element of this enumeration if this enumeration object 
     * has at least one more element to provide.
     *
     * @return The next element;
     */
    public Object nextElement() {
        return enumer.nextElement();
    }
}
