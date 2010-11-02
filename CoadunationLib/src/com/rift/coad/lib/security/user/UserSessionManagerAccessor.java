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
 * UserSessionManagerAccessor.java
 *
 * This class supplies access to the user session manager object. The calling
 * thread has to have access to the appropriate roles before being granted
 * access.
 */

// package path
package com.rift.coad.lib.security.user;

// imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.SecurityException;


/**
 * This class supplies access to the user session manager object. The calling
 * thread has to have access to the appropriate roles before being granted
 * access.
 *
 * @author Brett Chaldecott
 */
public class UserSessionManagerAccessor {
    
    // class constants
    private final static String ROLE = "role";
    
    // singleton veriables
    private static UserSessionManagerAccessor singleton = null;
    
    // private member variables
    private UserSessionManager userSessionManager = null;
    private String role = null;
    
    /** 
     * Creates a new instance of UserSessionManagerAccessor
     *
     * @param userSessionManager The reference to the class to supply access to.
     * @exception UserException
     */
    private UserSessionManagerAccessor(UserSessionManager userSessionManager) 
            throws UserException {
        try {
            Configuration config  = 
                    ConfigurationFactory.getInstance().getConfig(this.getClass());
            this.userSessionManager = userSessionManager;
            role = config.getString(ROLE);
        } catch (Exception ex) {
            throw new UserException(
                    "Failed to init the user session manager accessor : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method instanciates the user session manager accessor.
     *
     * @return A reference to the user session manager accessor
     * @exception UserException
     */
    public synchronized static UserSessionManagerAccessor init(
            UserSessionManager userSessionManager) 
            throws UserException {
        if (singleton == null) {
            singleton = new UserSessionManagerAccessor(userSessionManager);
        }
        return singleton;
    }
    
    
    /**
     * This method retrieves a reference to the user session manager instance.
     *
     * @return A reference to the user session manager accessor.
     * @exception UserException;
     */
    public synchronized static UserSessionManagerAccessor getInstance() 
            throws UserException {
        if (singleton == null) {
            throw new UserException(
                    "The user session manager accessor has not been instanciated");
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the user session manager.
     *
     * @return A reference to the user session manager object.
     * @exception AuthorizationException
     * @exception SecurityException
     */
    public UserSessionManager getUserSessionManager() 
            throws AuthorizationException, SecurityException {
        Validator.validate(this.getClass(),role);
        return userSessionManager;
    }
}
