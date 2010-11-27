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
 * Role.java
 *
 * The role object that contains the list principals. If a user or object has
 * a principal that matches, than access will be granted to run as that role.
 */

// the package name
package com.rift.coad.lib.security;


// java imports
import java.util.Set;
import java.util.Iterator;

// log 4 j imports
import org.apache.log4j.Logger;


/**
 * The role object that contains the list principals. If a user or object has
 * a principal that matches, than access will be granted to run as that role.
 *
 * @author Brett Chaldecott
 */
public class Role implements PrincipalContainer {
    // log
    private static Logger log =
        Logger.getLogger(Role.class.getName());
    
    // the classes private member variables
    private String name = null;
    private Set principals = null;
    
    
    /** 
     * Creates a new instance of a role using the supplied principal list
     *
     * @param name The name of the role.
     * @param principals The list of principals assigned to this role.
     */
    public Role(String name, Set principals) {
        this.name = name;
        this.principals = principals;
    }
    
    
    /**
     * The getter method for the name of this role.
     *
     * @return The string containing the name of this role.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns the list of principals.
     *
     * @return The list of principals.
     */
    public Set getPrincipals() {
        return principals;
    }
    
    
    /**
     * This method will return true if one of the principals match
     *
     * @return TRUE if one of the principals in sets match.
     * @param queryPrincipals The set of principals that will be used to query
     *      this role.
     */
    public boolean canAccessRole(Set queryPrincipals) {
        for (Iterator iter = queryPrincipals.iterator(); iter.hasNext();){
            String principal = (String)iter.next();
            if (principals.contains(principal)) {
                return true;
            }
        }
        return false;
    }
}
