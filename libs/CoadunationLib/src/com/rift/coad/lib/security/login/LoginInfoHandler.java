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
 * LoginInfoHandler.java
 *
 * This interface will be responsible for suppling the login handler with the
 * information required to perform the login.
 */

package com.rift.coad.lib.security.login;

// the java imports
import java.util.Map;

/**
 * This interface will be responsible for suppling the login handler with the
 * information required to perform the login.
 *
 * @author Brett Chaldecott
 */
public interface LoginInfoHandler {
    
    /**
     * This method returns the authentication type required.
     *
     * @return The string containing the authentication type.
     */
    public String getAuthType();
    
    
    /**
     * This method return the information required to perform the login.
     *
     * @return The map containing the authentication information.
     * @exception LoginException If the info cannot be retrieved due to unforceen reasons
     */
    public Map getInfo() throws LoginException;
    
}
