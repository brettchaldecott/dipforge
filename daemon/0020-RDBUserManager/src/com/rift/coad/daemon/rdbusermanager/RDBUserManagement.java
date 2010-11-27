/*
 * 0020-RDBUserManager: The client of the RDB User Manager
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
 * RDBUserManagement.java
 */
package com.rift.coad.daemon.rdbusermanager;

// java imports
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;

// import database
import com.rift.coad.auth.db.*;
import com.rift.coad.auth.util.HashPassword;

// coadunation import
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.role.ConfigurationRoleHandler;
import com.rift.coad.util.transaction.UserTransactionWrapper;

// rdb auth imports
import com.rift.coad.auth.RDBUserStore;

/**
 * This object is responsible for implementing the rdb user management
 * funcationality.
 * 
 * @author brett chaldecott
 */
public class RDBUserManagement implements RDBUserManagementMBean {
    
    // private member variables
    private Logger log = Logger.getLogger(RDBUserManagement.class);

    /**
     * The default constructor of the rdb user managment object.
     */
    public RDBUserManagement() throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    RoleManager.class);
            StringTokenizer principalList = new StringTokenizer(
                    config.getString(ConfigurationRoleHandler.PRINCIPALS),",");
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            while(principalList.hasMoreTokens()) {
                String principalName = principalList.nextToken().trim();
                CoadunationPrincipal principal = (CoadunationPrincipal)session.get(
                        CoadunationPrincipal.class, principalName);
                if (principal == null) {
                    session.persist(new CoadunationPrincipal(principalName));
                }
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to setup the principal information : "
                    + ex.getMessage(),ex);
            throw new RDBUserManagementException(
                    "Failed to setup the principal information : "
                    + ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }

    /**
     * This method returns the version information.
     * 
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1";
    }

    /**
     * This method returns the name of this user management object.
     * 
     * @return The string containing the name of this user management object.
     */
    public String getName() {
        return this.getClass().getName();
    }
    
    /**
     * This method returns the description of the RDB user.
     * 
     * @return This method returns the description of the user management object.
     */
    public String getDescription() {
        return "The user management object.";
    }
    
    
    /**
     * This method returns the user.
     * 
     * @param username The user to retrieve.
     * @return This method return the user object.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     */
    public RDBUser getUser(String username) throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            CoadunationUser user = (CoadunationUser) session.get(
                    CoadunationUser.class, username);
            Set dbPrincipals = user.getPrincipals();
            List principals = new ArrayList();
            for (Iterator iter = dbPrincipals.iterator(); iter.hasNext();) {
                CoadunationUserPrincipal principal = 
                        (CoadunationUserPrincipal)iter.next();
                principals.add(principal.getPrincipal().getName());
            }
            return new RDBUser(user.getUsername(),principals);
        } catch (Throwable ex) {
            log.error("Failed to retrieve the user information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the user information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method returns a list of users registered with the rdb user 
     * management object.
     * 
     * @return This method returns a list of users managed by this object.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     */
    public List listUsers() throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            List users = session.createQuery(
                    "SELECT user.username FROM CoadunationUser as user").list();
            List userList = new ArrayList();
            userList.addAll(users);
            return userList;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of users : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the list of users : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method adds a new user to the rdb user management database.
     * 
     * @param username The name of the user to add.
     * @param password The password for the user.
     * @param principals The list of principals.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     */
    public void addUser(String username, String password, String[] principals)
            throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            // this convoluted logic is here to deal with the lack of roll back
            // when foreign keys are involved. I do not know if this is a bug
            // with hibernate or someithing with JOTM.
            for (String principalName: principals) {
                CoadunationPrincipal principal = 
                        (CoadunationPrincipal)session.get(
                        CoadunationPrincipal.class, principalName.trim());
                if (principal == null) {
                    log.warn("The principal [" + principalName 
                            + "] does not exist");
                    throw new RDBUserManagementException(
                            "The principal [" + principalName 
                            + "] does not exist");
                }
            }
            CoadunationUser user = new CoadunationUser(username,
                    HashPassword.generateSha(password));
            session.persist(user);
            for (String principalName: principals) {
                CoadunationPrincipal principal = 
                        (CoadunationPrincipal)session.get(
                        CoadunationPrincipal.class, principalName.trim());
                CoadunationUserPrincipal coadUserPrincipal = 
                        new CoadunationUserPrincipal(user,principal);
                session.persist(coadUserPrincipal);
            }
            transaction.commit();
        } catch (RDBUserManagementException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of users : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the list of users : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for updating the user password.
     * 
     * @param username The name of the user to update the password for.
     * @param password The new password for the user.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     */
    public void updateUserPassword(String username, String password) throws 
            RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            CoadunationUser user = (CoadunationUser) session.get(
                    CoadunationUser.class, username);
            user.setPassword(HashPassword.generateSha(password));
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to update the user information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to update the user information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for updating the user assigned principals.
     * 
     * @param username The name of the user to perform the update for.
     * @param principals The list of principals to update.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void updateUserPrincipal(String username, String[] principals)
            throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            // this convoluted logic is here to deal with the lack of roll back
            // when foreign keys are involved. I do not know if this is a bug
            // with hibernate or someithing with JOTM.
            for (String principalName: principals) {
                CoadunationPrincipal principal = 
                        (CoadunationPrincipal)session.get(
                        CoadunationPrincipal.class, principalName.trim());
                if (principal == null) {
                    log.warn("The principal [" + principalName 
                            + "] does not exist");
                    throw new RDBUserManagementException(
                            "The principal [" + principalName 
                            + "] does not exist");
                }
            }
            session.createQuery(
                    "DELETE FROM CoadunationUserPrincipal AS principal WHERE " +
                    "principal.user.username = ?").setString(0, username).
                    executeUpdate();
            CoadunationUser user = (CoadunationUser) session.get(
                    CoadunationUser.class, username);
            for (String principalName: principals) {
                CoadunationPrincipal principal = 
                        (CoadunationPrincipal)session.get(
                        CoadunationPrincipal.class, principalName.trim());
                CoadunationUserPrincipal coadUserPrincipal = 
                        new CoadunationUserPrincipal(user,principal);
                session.persist(coadUserPrincipal);
            }
            
            transaction.commit();
        } catch (RDBUserManagementException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to update the user information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to update the user information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for removing the user.
     * 
     * @param username The name of the user to remove.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     */
    public void removeUser(String username) throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            session.createQuery(
                    "DELETE FROM CoadunationUserPrincipal AS principal WHERE " +
                    "principal.user.username = ?").setString(0, username).
                    executeUpdate();
            session.createQuery(
                    "DELETE FROM CoadunationUser AS user WHERE " +
                    "user.username = ?").setString(0, username).
                    executeUpdate();
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to remove the user information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to remove the user information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method returns the role.
     * 
     * @param role The name of the role to retrieve
     * @return This method returns the role identifier
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public RDBRole getRole(String role) throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            CoadunationRole coadRole = (CoadunationRole) session.get(
                    CoadunationRole.class, role);
            Set dbPrincipals = coadRole.getPrincipals();
            List principals = new ArrayList();
            for (Iterator iter = dbPrincipals.iterator(); iter.hasNext();) {
                CoadunationRolePrincipal principal = 
                        (CoadunationRolePrincipal)iter.next();
                principals.add(principal.getPrincipal().getName());
            }
            return new RDBRole(coadRole.getRole(),principals);
        } catch (Throwable ex) {
            log.error("Failed to retrieve the role information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the role information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method returns a list of roles
     * 
     * @return This method returns a list of roles.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public List listRoles() throws RDBUserManagementException, RemoteException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            
            List dbRoles = session.createQuery(
                    "SELECT role.role FROM CoadunationRole as role").list();
            List roles = new ArrayList();
            roles.addAll(dbRoles);
            return roles;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the role information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the role information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for adding a new role.
     * 
     * @param role The role name to add.
     * @param principals The list of roles to associate with the user.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void addRole(String role, String[] principals) throws 
            RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            // this convoluted logic is here to deal with the lack of roll back
            // when foreign keys are involved. I do not know if this is a bug
            // with hibernate or someithing with JOTM.
            for (String principal : principals) {
                CoadunationPrincipal coadPrincipal = (CoadunationPrincipal)
                        session.get(CoadunationPrincipal.class, principal.trim());
                if (coadPrincipal == null) {
                    log.warn("The principal [" + principal 
                            + "] does not exist");
                    throw new RDBUserManagementException(
                            "The principal [" + principal 
                            + "] does not exist");
                }
            }
            CoadunationRole coadRole = new CoadunationRole(role);
            session.persist(coadRole);
            for (String principal : principals) {
                CoadunationPrincipal coadPrincipal = (CoadunationPrincipal)
                        session.get(CoadunationPrincipal.class, principal.trim());
                CoadunationRolePrincipal rolePrincipal = 
                        new CoadunationRolePrincipal(coadRole,coadPrincipal);
                session.persist(rolePrincipal);
            }
            transaction.commit();
        } catch (RDBUserManagementException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to add a role : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to add a role : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for updating the role.
     * 
     * @param role The name of the role to perform the update for.
     * @param principals The list of principals.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void updateRole(String role, String[] principals) 
            throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            // this convoluted logic is here to deal with the lack of roll back
            // when foreign keys are involved. I do not know if this is a bug
            // with hibernate or someithing with JOTM.
            for (String principal : principals) {
                CoadunationPrincipal coadPrincipal = (CoadunationPrincipal)
                        session.get(CoadunationPrincipal.class, principal.trim());
                if (coadPrincipal == null) {
                    log.warn("The principal [" + principal 
                            + "] does not exist");
                    throw new RDBUserManagementException(
                            "The principal [" + principal 
                            + "] does not exist");
                }
            }
            session.createQuery(
                    "DELETE FROM CoadunationRolePrincipal AS principal WHERE " +
                    "principal.role.role = ?").setString(0, role).
                    executeUpdate();
            CoadunationRole coadRole = (CoadunationRole) session.get(
                    CoadunationRole.class, role);
            for (String principal : principals) {
                CoadunationPrincipal coadPrincipal = (CoadunationPrincipal)
                        session.get(CoadunationPrincipal.class, principal.trim());
                CoadunationRolePrincipal rolePrincipal = 
                        new CoadunationRolePrincipal(coadRole,coadPrincipal);
                session.persist(rolePrincipal);
            }
            transaction.commit();
        } catch (RDBUserManagementException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to update the role information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to update the role information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for removing the specified realm.
     * @param role The role to remove.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void removeRole(String role) throws RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            
            session.createQuery(
                    "DELETE FROM CoadunationRolePrincipal AS principal WHERE " +
                    "principal.role.role = ?").setString(0, role).
                    executeUpdate();
            session.createQuery(
                    "DELETE FROM CoadunationRole AS role WHERE " +
                    "role.role = ?").setString(0, role).
                    executeUpdate();
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to remove the role information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to remove the role information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method returns a list of principals.
     * 
     * @return The list of principals.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public List listPrincipals() throws RDBUserManagementException, RemoteException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            
            List dbPrincipals = session.createQuery(
                    "SELECT principal.name FROM CoadunationPrincipal " +
                    "as principal").list();
            List principals = new ArrayList();
            principals.addAll(dbPrincipals);
            return principals;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of principals : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to retrieve the list of principals : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for adding new principals.
     * 
     * @param principal The string identifying the principal to add.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void addPrincipal(String principal) throws 
            RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            CoadunationPrincipal coadPrincipal = 
                    new CoadunationPrincipal(principal);
            session.persist(coadPrincipal);
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to add the principal : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to add the principal : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for removing the specified principal.
     * @param principal The name of the principal to remove.
     * @throws com.rift.coad.daemon.rdbusermanager.RDBUserManagementException
     * @throws java.rmi.RemoteException
     */
    public void removePrincipal(String principal) throws 
            RDBUserManagementException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            
            session.createQuery(
                    "DELETE FROM CoadunationPrincipal AS principal WHERE " +
                    "principal.name = ?").setString(0, principal).
                    executeUpdate();
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to remove the principal information : " +
                    ex.getMessage(), ex);
            throw new RDBUserManagementException(
                    "Failed to remove the principal information : " +
                    ex.getMessage(), ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
}
