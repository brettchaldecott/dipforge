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
 * ClientInterceptor.java
 *
 * This object is responsible for supplying the authentication information on
 * the client side of a connection for the request onto a server.
 */

// package path
package com.rift.coad.lib.interceptor;

// the thread permission container
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Session;

/**
 * This object is responsible for supplying the authentication information on
 * the client side of a connection for the request onto a server.
 *
 * @author Brett Chaldecott
 */
public class ClientInterceptor {
    
    // the classes private member variables
    private ThreadsPermissionContainer permissionContainer = null;
    
    /**
     * Creates a new instance of ClientInterceptor
     */
    protected ClientInterceptor(ThreadsPermissionContainer permissionContainer) {
        this.permissionContainer = permissionContainer;
    }
    
    
    /**
     * This method retrieves the threads session credentials.
     *
     * @return The credentials for this session.
     * @exception InterceptorException
     */
    public Credential getSessionCredential() throws InterceptorException {
        try {
            ThreadPermissionSession permission = permissionContainer.
                    getSession();
            Session session = new Session(permission.getUser().getName(), 
                    permission.getUser().getSessionId(), 
                    permission.getPrincipals());
            return session;
        } catch (Exception ex) {
            throw new InterceptorException(
                    "Failed to retrieve the session credentials : " + 
                    ex.getMessage(),ex);
        }
    }
    
}
