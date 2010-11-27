/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
 * Copyright (C) 2008  Rift IT Contracting
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
 * CoadunationRole.java
 */

package com.rift.coad.auth;

// java imports
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;

// coadunation imports
import com.rift.coad.lib.security.Role;
import com.rift.coad.lib.security.RoleHandler;
import com.rift.coad.lib.security.SecurityException;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.lib.deployment.DeploymentMonitor;

// import database
import com.rift.coad.auth.db.*;



/**
 * This object is reponsible for handling the role information.
 * 
 * @author brett chaldecott
 */
public class RDBRoleHandler implements RoleHandler {
    
    // private member
    private Logger log = Logger.getLogger(RDBRoleHandler.class);
    
    /**
     * The constructor of the rdb role handler.
     */
    public RDBRoleHandler() {
        
    }
    
    /**
     * This method returns a map of all the roles available.
     * 
     * @return The roles managed by this handler
     * @throws com.rift.coad.lib.security.SecurityException
     */
    public Map getRoles() throws SecurityException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        UserTransactionWrapper transaction = null;
        try {
            if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
                log.info("The deployment has not completed");
                throw new SecurityException(
                    "The deployment has not been completed");
            }
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            List roles = session.createQuery("FROM CoadunationRole").list();
            Map results = new HashMap();
            for (Iterator iter = roles.iterator(); iter.hasNext();) {
                CoadunationRole role = (CoadunationRole)iter.next();
                results.put(role.getRole(), getRole(role));
            }
            return results;
        } catch (SecurityException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of roles : " +
                    ex.getMessage(), ex);
            throw new SecurityException(
                    "Failed to retrieve the list of roles : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
            // reset the loader
            Thread.currentThread().setContextClassLoader(loader);
        }
    }
    
    
    /**
     * The role matching the request.
     * 
     * @param role The role matching the request string.
     * @return The role identified by the string.
     * @throws com.rift.coad.lib.security.SecurityException
     */
    public Role getRole(String role) throws SecurityException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        UserTransactionWrapper transaction = null;
        try {
            if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
               return null;
            } 
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            return getRole( 
                    (CoadunationRole)session.get(CoadunationRole.class, role));
        } catch (Exception ex) {
            log.error("Failed to retrieve the role: " +
                    ex.getMessage(), ex);
            throw new SecurityException(
                    "Failed to retrieve the role : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
            // reset the loader
            Thread.currentThread().setContextClassLoader(loader);
        }
    }
    
    
    /**
     * This method returns the Coadunation role value for the RDB object.
     * 
     * @return The role from the coadunation rdb object.
     * @param role The rdb object  to convert.
     * @throws RDBException
     */
    private Role getRole(CoadunationRole role) throws RDBException {
        Set principals = new HashSet();
        Set rolePrincipals = role.getPrincipals();
        for (Iterator iter = rolePrincipals.iterator(); iter.hasNext();) {
            CoadunationRolePrincipal principal = 
                    (CoadunationRolePrincipal)iter.next();
            principals.add(principal.getPrincipal().getName());
        }
        return new Role(role.getRole(), principals);
    }
}
