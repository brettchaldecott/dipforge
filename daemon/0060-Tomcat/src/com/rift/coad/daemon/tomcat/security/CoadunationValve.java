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
 * CoadunationValve.java
 */

// package path
package com.rift.coad.daemon.tomcat.security;

// logging import
import com.rift.coad.daemon.tomcat.Tomcat;
import com.rift.coad.daemon.tomcat.TomcatDeployer;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.ServletException;

// log 4 j imports
import org.apache.log4j.Logger;

// tomcat imports
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import javax.servlet.http.HttpSession;

// coadunation imports
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.SessionManager;

/**
 * This valve is responsible for associating an active session with a user. In
 * coadunation.
 *
 * @author brett chaldecott
 */
public class CoadunationValve extends ValveBase {
    
    // instanciate the logger
    protected static Logger log =
            Logger.getLogger(CoadunationValve.class.getName());
    
    // private member variables
    private CoadunationInterceptor interceptor =
            CoadunationInterceptor.getInstance();
    
    /**
     * Creates a new instance of CoadunationValve
     */
    public CoadunationValve() {
        
    }
    
    
    /**
     * This method is responsible for dealing with the invoke request on the
     * servlet container.
     *
     * @param request The data used for the request.
     * @param response The data resulting from the request response.
     * @exception IOException
     * @exception ServletException
     */
    public void invoke(Request request, Response response) throws IOException,
            ServletException {
        Principal caller = request.getSessionInternal().getPrincipal();
        HttpSession hsession = request.getSession(false);
        UserSession userSession = null;
        try {
            if (caller instanceof CoadunationGenericPrincipal) {
                userSession = ((CoadunationGenericPrincipal)caller).getSession();
            // this section is to deal with form based authentication.
            } else if (hsession != null) {
                userSession = (UserSession)hsession.
                        getAttribute("CoadunationUserSession");
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve the user session information : " +
                    ex.getMessage(),ex);
        }
        if (userSession != null) {
            log.debug("The user session is for : " + userSession.getName());
        } else {
            // retrieve the default backend session information
            userSession = TomcatDeployer.getSession();
            log.debug("Using the default backend session : " + 
                    (userSession != null ? userSession.getName() : "N\\A"));
        }
        interceptor.pushUser(userSession);
        try {
            getNext().invoke(request, response);
            // this section is to deal with form based authentication.
            if (hsession != null) {
               try {
                    ThreadPermissionSession session = 
                            SessionManager.getInstance().getSession();
                    
                    hsession.setAttribute("CoadunationUserSession",
                            session.getUser());
                } catch (Exception ex) {
                    // ignore this as we will not have a session on a an normal
                    // thread
                    log.debug("No session has been setup for current thread :" 
                            + ex.getMessage());
                }
            }
        } finally {
            interceptor.popUser();
        }
    }
    
}
