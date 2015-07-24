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
 * Validator.java
 *
 * This security validator.
 */

package com.rift.coad.lib.security;

// log 4 j imports
import org.apache.log4j.Logger;


/**
 * This security validator.
 *
 * @author Brett Chaldecott
 */
public class Validator {
    
    // log
    private static Logger log =
        Logger.getLogger(Validator.class.getName());
    
    /**
     * The validator responsible for determining if the current thread can
     * access a role.
     *
     * @param ref The reference to the class on which the call is being made.
     * @param roleName The name of the role that access is being checked on.
     * @exception AuthorizationException
     * @exception SecurityException
     */
    public static void validate(Class ref,String roleName) throws 
            AuthorizationException, SecurityException {
        log.debug("Validate access to : " + roleName);
        ThreadPermissionSession session = 
                SessionManager.getInstance().getSession();
        Role role = RoleManager.getInstance().getRole(roleName);
        if (role.canAccessRole(session.getPrincipals()) == false) {
            throw new AuthorizationException("Access Denied to [" +
                    ref.getName() + "] and role [" + roleName + "] by [" +
                    session.getPrincipals().toString() + "]");
        }
    }
    
}
