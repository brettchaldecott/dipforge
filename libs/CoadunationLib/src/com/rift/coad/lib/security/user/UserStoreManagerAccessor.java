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
 * UserStoreManagerAccessor.java
 *
 * This object is responsible for supplying access to the user store manager.
 */

// package path
package com.rift.coad.lib.security.user;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.SecurityException;


/**
 * This object is responsible for supplying access to the user store manager.
 *
 * @author Brett Chaldecott
 */
public class UserStoreManagerAccessor {
    
    // class constants
    private final static String ROLE = "role";
    
    // class member variables
    private static UserStoreManagerAccessor singleton = null;
    
    // private member variables
    private UserStoreManager userStoreManager = null;
    private String role = null;
    
    /** 
     * Creates a new instance of UserStoreManagerAccessor 
     *
     * @param userStoreManager The reference to the user store object.
     * @exception UserException
     */
    private UserStoreManagerAccessor(UserStoreManager userStoreManager) 
            throws UserException {
        try {
            this.userStoreManager = userStoreManager;
            Configuration configuration = 
                    ConfigurationFactory.getInstance().getConfig(this.getClass());
            role = configuration.getString(ROLE);
        } catch (Exception ex) {
            throw new UserException (
                    "Failed to instanciate the user store manager accessor : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method instanciates the user store manager accessor.
     *
     * @return A reference to the user store manager accessor.
     * @param userStoreManager The reference to the user store manager.
     * @exception UserException
     */
    public synchronized static UserStoreManagerAccessor init(
            UserStoreManager userStoreManager) 
            throws UserException {
        if (singleton == null) {
            singleton = new UserStoreManagerAccessor(userStoreManager);
        }
        return singleton;
    }
    
    
    /**
     * This method retrieves a reference to the user store manager instance.
     *
     * @return A reference to the user store manager accessor.
     * @exception UserException;
     */
    public synchronized static UserStoreManagerAccessor getInstance() 
            throws UserException {
        if (singleton == null) {
            throw new UserException(
                    "The user store manager accessor has not been instanciated");
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the user store manager.
     *
     * @return A reference to the user session manager object.
     * @exception AuthorizationException
     * @exception SecurityException
     */
    public UserStoreManager getUserStoreManager() 
            throws AuthorizationException, SecurityException {
        Validator.validate(this.getClass(),role);
        return userStoreManager;
    }
}
