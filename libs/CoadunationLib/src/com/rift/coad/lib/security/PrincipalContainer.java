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
 * PrincipalContainer.java
 *
 * The generic principal container interface.
 */

// package
package com.rift.coad.lib.security;

// class imports
import java.util.Set;

/**
 * The generic principal container interface.
 *
 * @author Brett Chaldecott
 */
public interface PrincipalContainer {
    
    /**
     * The name of the entity for which the principals are being stored.
     *
     * @return The string containing the name of the principal grouping.
     */
    public String getName();
    
    
    /**
     * Retrieve the list of principals for the container.
     *
     * @return The set of container information.
     */
    public Set getPrincipals();
}
