/*
 * Tomcat: The deployer for the tomcat daemon
 * Created on July 10, 2007, 6:23 AM
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
 * CoadunationGenericPrincipal.java
 */

// package path
package com.rift.coad.daemon.tomcat.security;

// java imports
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

// tomcat import
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.Realm;

// coadunation imports
import com.rift.coad.lib.security.UserSession;

/**
 * This object extends the Tomcat Generic Principal object.
 *
 * @author brett
 */
public class CoadunationGenericPrincipal extends GenericPrincipal {
    
    // private member variables
    private UserSession session = null;
    
    /**
     * Creates a new instance of CoadunationGenericPrincipal
     */
    public CoadunationGenericPrincipal(Realm realm, String name, String password,
            List roles, UserSession session) {
        super(realm,name,password,roles);
        this.session = session;
    }
    
    
    /**
     * This method returns the user session.
     *
     * @return The user session contained by t his object.
     */
    public UserSession getSession() {
        return session;
    }
    
}
