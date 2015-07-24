/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  2015 Burntjam
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
 * RoleHandler.java
 */

// the package path
package com.rift.coad.lib.security;

// java imports
import java.util.Map;

/**
 * This interface is responsible for supplying methods to get role information.
 *
 * @author brett
 */
public interface RoleHandler {
    /**
     * This method returns the roles referenced by this handler.
     */
    public Map getRoles() throws SecurityException;
    
    
    /**
     * This method returns the roles referenced by this handler.
     * 
     * @return A reference to the required role.
     * @param role The string containing the role value.
     * @exception SecurityException
     */
    public Role getRole(String role) throws SecurityException;
}
