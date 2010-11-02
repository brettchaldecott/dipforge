/*
 * Tomcat: The deployer for the tomcat daemon
 * Created on July 10, 2007, 6:52 AM
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
 * CoadunationInterceptor.java
 */

// package paths
package com.rift.coad.daemon.tomcat.security;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.interceptor.ServerInterceptor;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.interceptor.credentials.Session;


/**
 * The coadunation interceptor.
 *
 * @author brett
 */
public class CoadunationInterceptor extends InterceptorWrapper {
    
    // static member variables
    protected static Logger log = 
            Logger.getLogger(CoadunationInterceptor.class.getName());
    private static CoadunationInterceptor singleton = null;
    
    // private member variables
    private ThreadLocal threadSession = new ThreadLocal();
    
    /**
     * Creates a new instance of CoadunationInterceptor
     */
    public CoadunationInterceptor() {
    }
    
    
    /**
     * This method is called to sudo a user
     */
    public void pushUser(UserSession session) {
        threadSession.set(session);
        if (session == null) {
            return;
        }
        try {
            this.getServerInterceptor().createSession(new Session(
                    session.getName(),session.getSessionId(),
                    session.getPrincipals()));
        } catch (Exception ex) {
            log.error("Failed to create a session : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will be called to pop a user after the valve call has 
     * terminated.
     */
    public void popUser() {
        if (threadSession.get() == null) {
            return;
        }
        try {
            this.getServerInterceptor().release();
            threadSession.set(null);
        } catch (Exception ex) {
            log.error("Failed to release a session : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This  method returns the singleton instance.
     */
    public synchronized static CoadunationInterceptor getInstance() {
        if (singleton == null) {
            singleton = new CoadunationInterceptor();
        }
        return singleton;
    }
}
