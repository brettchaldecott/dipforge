/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2007  Rift IT Contracting
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
 * CoadunationRealm.java
 */

// package path
package com.rift.coad.daemon.tomcat.security;

// java imports
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

// coadunation imports
import com.rift.coad.security.BasicPrincipal;
import com.rift.coad.lib.security.login.SessionLogin;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Role;

// logging import
import org.apache.log4j.Logger;

// tomcat imports
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Realm;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.Constants;
import org.apache.catalina.realm.GenericPrincipal;

/**
 * This object is responsible acting as a Tomcat Realm but authenticating the
 * calls off of Coadunation.
 *
 * @author brett
 */
public class CoadunationRealm extends RealmBase implements Realm {
    
    // private member variables
    protected Logger log = Logger.getLogger(CoadunationRealm.class.getName());
    private boolean trace = false;
    
    
    /**
     * Creates a new instance of CoadunationRealm
     */
    public CoadunationRealm() {
        trace = log.isTraceEnabled();
    }
    
    
    /**
     * This method returns the name of this realm.
     */
    protected String getName() {
        return this.getClass().getName();
    }
    
    
    /**
     * This method returns the password associated with the given username.
     */
    protected String getPassword(String username) {
        String password = null;
        return password;
    }
    
    
    /**
     * This method returns the principal for the requested user
     */
    protected Principal getPrincipal(String username) {
        return new BasicPrincipal(username);
    }
    
    
    /**
     * This method is here for debug purposes
     */
    public boolean hasResourcePermission(Request request, Response response,
            SecurityConstraint[] constraints, org.apache.catalina.Context context)
            throws IOException {
        if (constraints == null || constraints.length == 0) {
            return (true);
        }
        
        boolean hasPermission = false;
        // Specifically allow access to the form login and form error pages
        // and the "j_security_check" action
        LoginConfig config = context.getLoginConfig();
        if ((config != null) &&
                (Constants.FORM_METHOD.equals(config.getAuthMethod()))) {
            String requestURI = request.getRequestPathMB().toString();
            String loginPage = config.getLoginPage();
            if (loginPage.equals(requestURI)) {
                if( trace )
                    log.trace("Allow access to login page " + loginPage);
                return (true);
            }
            String errorPage = config.getErrorPage();
            if (errorPage.equals(requestURI)) {
                if( trace )
                    log.trace("Allow access to error page " + errorPage);
                return (true);
            }
            if (requestURI.endsWith(Constants.FORM_ACTION)) {
                if( trace )
                    log.trace("Allow access to username/password submission");
                return (true);
            }
        }
        
        // Which user principal have we already authenticated?
        Principal principal = request.getPrincipal();
        boolean denyfromall = false;
        for (int i = 0; i < constraints.length; i++) {
            SecurityConstraint constraint = constraints[i];
            
            String roles[];
            if (constraint.getAllRoles()) {
                // * means all roles defined in web.xml
                roles = request.getContext().findSecurityRoles();
            } else {
                roles = constraint.findAuthRoles();
            }
            
            if (roles == null) {
                roles = new String[0];
            }
            
            if( trace )
                log.trace("Checking roles " + principal);
            
            if (roles.length == 0 && !constraint.getAllRoles()) {
                if (constraint.getAuthConstraint()) {
                    if( trace )
                        log.trace("No roles");
                    hasPermission = false; // No listed roles means no access at all
                    denyfromall = true;
                } else {
                    if( trace )
                        log.trace("Passing all access");
                    return (true);
                }
            } else if (principal == null) {
                if( trace )
                    log.trace("No user authenticated, cannot grant access");
                hasPermission = false;
            } else if (!denyfromall) {
                for (int j = 0; j < roles.length; j++) {
                    if (hasRole(principal, roles[j])) {
                        hasPermission = true;
                    }
                    if( trace )
                        log.trace("No role found:  " + roles[j]);
                }
            }
        }
        
        if (allRolesMode != AllRolesMode.STRICT_MODE
                && hasPermission == false
                && principal != null) {
            if (trace) {
                log.trace("Checking for all roles mode: " + allRolesMode);
            }
            // Check for an all roles(role-name="*")
            for (int i = 0; i < constraints.length; i++) {
                SecurityConstraint constraint = constraints[i];
                String roles[];
                // If the all roles mode exists, sets
                if (constraint.getAllRoles()) {
                    if (allRolesMode == AllRolesMode.AUTH_ONLY_MODE) {
                        if (trace) {
                            log.trace("Granting access for role-name=*, auth-only");
                        }
                        hasPermission = true;
                        break;
                    }
                    
                    // For AllRolesMode.STRICT_AUTH_ONLY_MODE there must be zero roles
                    roles = request.getContext().findSecurityRoles();
                    if (roles.length == 0 && allRolesMode == AllRolesMode.STRICT_AUTH_ONLY_MODE) {
                        if (trace) {
                            log.trace("Granting access for role-name=*, strict auth-only");
                        }
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        
        // Return a "Forbidden" message denying access to this resource
        if (!hasPermission) {
            response.sendError
                    (HttpServletResponse.SC_FORBIDDEN,
                    sm.getString("realmBase.forbidden"));
        }
        return hasPermission;
    }
    
    
    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username    Username of the Principal to look up
     * @param credentials Password or other credentials to use in authenticating
     *                    this username
     */
    public Principal authenticate(String username, String credentials) {
        try {
            SessionLogin sessionLogin = new SessionLogin(
                    new PasswordInfoHandler(username,credentials));
            sessionLogin.login();
            UserSession userSession = sessionLogin.getUser();
            
            // retrieve the list of roles
            List roles = new ArrayList();
            Set systemRoles = RoleManager.getInstance().getRoles();
            for (Iterator iter = systemRoles.iterator(); iter.hasNext();) {
                Role role = RoleManager.getInstance().getRole(
                        (String)iter.next());
                Set rolePrincipals = role.getPrincipals();
                for (Iterator roleIter = rolePrincipals.iterator();
                    roleIter.hasNext();) {
                    if (userSession.getPrincipals().contains(roleIter.next())) {
                        roles.add(role.getName());
                        break;
                    }
                }
                    
            }
            
            // construct the gneric principal object
            CoadunationGenericPrincipal genericPrincipal = 
                    new CoadunationGenericPrincipal(this, username, credentials,
                    roles, userSession);
            
            // setup the session
            log.info("Setup a users session");
            CoadunationInterceptor.getInstance().pushUser(userSession);
            
            // return generic principals
            return genericPrincipal;
        } catch (Exception ex) {
           log.error("Failed to authenticate the user [" + username 
                   + "] because :" + ex.getMessage(),ex); 
        }
        
        return null;
    }
    
    
    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username    Username of the Principal to look up
     * @param credentials Password or other credentials to use in authenticating
     *                    this username
     */
    public Principal authenticate(String username, byte[] credentials) {
        
        return authenticate(username, new String(credentials));
    }
}
