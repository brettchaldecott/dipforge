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
 * InterceptorFactory.java
 *
 * This class is responsible for returning interceptors constructed with
 * reference to the coadunation internals. So that incoming threads can be
 * boot strapped appropriatly.
 */

// package path
package com.rift.coad.lib.interceptor;

// coadunation imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;

/**
 * This class is responsible for returning interceptors constructed with
 * reference to the coadunation internals. So that incoming threads can be
 * boot strapped appropriatly.
 *
 * @author Brett Chaldecott
 */
public class InterceptorFactory {
    
    // private member variables
    private static InterceptorFactory singleton = null;
    private ClientInterceptor clientInterceptor = null;
    private ServerInterceptor serverInterceptor = null;
    
    /**
     * Creates a new instance of InterceptorFactory
     */
    private InterceptorFactory(ThreadsPermissionContainer permissionContainer,
            UserSessionManager userSessionManager, UserStoreManager 
            userStoreManger) {
        serverInterceptor = new ServerInterceptor(permissionContainer,
                userSessionManager, userStoreManger);
        clientInterceptor = new ClientInterceptor(permissionContainer);
    }
    
    
    /**
     * This method instanciates the rmi interceptor factory
     * 
     * 
     * @return The reference to the RMI interceptor factory.
     * @param permissionContainer The reference to the permission container.
     * @exception InterceptorException
     */
    public static synchronized InterceptorFactory init(
            ThreadsPermissionContainer permissionContainer, UserSessionManager 
            userSessionManager, UserStoreManager userStoreManger) throws 
            InterceptorException {
        if (singleton == null) {
            return singleton = new InterceptorFactory(permissionContainer,
                    userSessionManager,userStoreManger);
        }
        throw new InterceptorException("The interceptor factory has already " +
                "been initialized.");
    }
    
    
    /**
     * This method returns a reference to the rmi interceptor factory singleton.
     * 
     * 
     * @return The reference to the RMI interceptor factory.
     * @exception InterceptorException
     */
    protected static synchronized InterceptorFactory getInstance() throws 
            InterceptorException {
        if (singleton == null) {
            throw new InterceptorException("The interceptor factory has not " +
                "been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the client interceptor.
     *
     * @return A reference to the client intercpetor.
     * @exception InterceptorException
     */
    protected ClientInterceptor getClientInterceptor() throws 
            InterceptorException {
        return clientInterceptor;
    }
    
    
    /**
     * This method returns a reference to the server interceptor.
     *
     * @return A reference to the server interceptor.
     * @exception InterceptorException
     */
    protected ServerInterceptor getServerInterceptor() throws 
            InterceptorException {
        return serverInterceptor;
    }
}
