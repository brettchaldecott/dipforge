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
 * ServerInterceptor.java
 *
 * This object is responsible for authenticating the incoming thread using the
 * credential information supplied.
 */

// package path
package com.rift.coad.lib.interceptor;

// java imports
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Login;
import com.rift.coad.lib.interceptor.credentials.Session;
import com.rift.coad.lib.interceptor.authenticator.SessionAuthenticator;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;


/**
 * This object is responsible for authenticating the incoming thread using the
 * credential information supplied.
 *
 * @author Brett Chaldecott
 */
public class ServerInterceptor {
    
    // class constants
    public static String CREDENTIAL_AUTHENTICATOR = "credential_authenticator";
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(ServerInterceptor.class.getName());
    
    // the class private member variables
    private ConcurrentMap authenticators = new ConcurrentHashMap();
    private ThreadsPermissionContainer permissionContainer = null;
    private InterceptorPermissionStack permissionStack = null;
    private UserSessionManager userSessionManager = null;
    private UserStoreManager userStoreManger = null;
    
    /**
     * Creates a new instance of ServerInterceptor
     *
     * @param threadPermissionContainer The container responsible for 
     *          controlling the threading permissions.
     */
    protected ServerInterceptor(ThreadsPermissionContainer 
            threadPermissionContainer,UserSessionManager userSessionManager, 
            UserStoreManager userStoreManger) {
        permissionStack = new InterceptorPermissionStack(
                threadPermissionContainer);
        this.permissionContainer = threadPermissionContainer;
        this.userSessionManager = userSessionManager;
        this.userStoreManger = userStoreManger;
    }
    
    
    /**
     * This method overwrites the session attached to this thread.
     *
     * @return The user session of the authenticated user.
     * @param credential The credentials to create a new session for.
     * @exception AuthorizationException
     * @exception InterceptorException
     */
    public UserSession setSession(Credential credential) throws 
            AuthorizationException, InterceptorException {
        UserSession userSession = authenticate(credential);
        permissionContainer.putSession(Thread.currentThread().getId(),
                    new ThreadPermissionSession(
                    new Long(Thread.currentThread().getId()),userSession));
        return userSession;
    }
    
    
    /**
     * This method creates a new session using the credentials passed in.
     *
     * @return The user session of the authenticated user.
     * @param credential The credentials to create a new session for.
     * @exception AuthorizationException
     * @exception InterceptorException
     */
    public UserSession createSession(Credential credential) throws 
            AuthorizationException, InterceptorException {
        UserSession userSession = authenticate(credential);
        permissionStack.push(userSession);
        return userSession;
    }
    
    
    /**
     * This method releases this thread, removing all user information from it.
     *
     * @exception InterceptorException
     */
    public void release() throws InterceptorException {
        permissionStack.pop();
    }
    
    
    /**
     * This method authenticates a thread using the credential object passed in.
     *
     * @return The reference to the user session object.
     * @param credentials The credential to authenticate this call.
     * @exception AuthorizationException
     * @exception InterceptorException
     */
    private UserSession authenticate(Credential credential) throws 
            AuthorizationException, InterceptorException {
        try {
            InterceptorAuthenticator authenticator = null;
            if (!authenticators.containsKey(credential.getClass())) {
                Configuration config = ConfigurationFactory.getInstance().
                        getConfig(credential.getClass());
                String className = config.getString(CREDENTIAL_AUTHENTICATOR,
                        SessionAuthenticator.class.getName());
                if (className.equals(
                        SessionAuthenticator.class.getName()))
                {
                    authenticator = new SessionAuthenticator(
                            userSessionManager, userStoreManger);
                }
                else
                {
                    authenticator = (InterceptorAuthenticator)Class.
                            forName(className).newInstance();
                }
                authenticators.put(credential.getClass(),authenticator);
            } else {
                authenticator = (InterceptorAuthenticator)authenticators.get(
                        credential.getClass());
            }
            
            return authenticator.authenticate(credential);
        } catch (AuthorizationException ex) {
            throw ex;
        } catch (InterceptorException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to authenticate :" + ex.getMessage(),ex);
            throw new InterceptorException("Failed to authenticate : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    
}
