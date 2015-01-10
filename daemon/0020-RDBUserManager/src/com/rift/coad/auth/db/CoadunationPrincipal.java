/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
 * Copyright (C) 2008  Rift IT Contracting
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
 * CoadunationPrincipal.java
 */

// the package path
package com.rift.coad.auth.db;

/**
 * This object represents a coadunation principal.
 * 
 * @author brett chaldecott
 */
public class CoadunationPrincipal {
    private String name = null;
    
    /**
     * This object represents a single principal in the database.
     */
    public CoadunationPrincipal() {
        
    }
    
    
    /**
     * This object represents a single principal in the database.
     * 
     * @param name The name of this principal.
     */
    public CoadunationPrincipal(String name) {
        this.name = name;
    }
    
    
    /**
     * This method returns the name of the principal
     * 
     * @return The string containing the name of this principal
     */
    public String getName() {
        return name;
    }
    
    /**
     * This method returns the name of the principal
     * 
     * @param name The string containing the name of this principal
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
