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
 * InterceptorAuthenticator.java
 *
 * This interface is responsible for supplying a method to authenticate a
 * credential object an return the appropriate user session object.
 */

// the package path
package com.rift.coad.lib.interceptor;

// coadunation imports
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;

/**
 * This interface is responsible for supplying a method to authenticate a
 * credential object an return the appropriate user session object.
 *
 * @author Brett Chaldecott
 */
public interface InterceptorAuthenticator {
    
    /**
     * This method returns a valid user session object for the supplied 
     * credentials
     * 
     * @return UserSession The new user session for the given credentials.
     * @param credentials The credentials for the user session.
     * @exception InterceptorException
     * @exception AuthorizationException
     */
    public UserSession authenticate(Credential credential) throws 
            InterceptorException, AuthorizationException;
}
