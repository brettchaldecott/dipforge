/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
 * Copyright (C) 2008  2015 Burntjam
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
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.security.Role;
import com.rift.coad.lib.security.RoleHandler;
import com.rift.coad.lib.security.SecurityException;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;



/**
 * This object is reponsible for handling the role information.
 * 
 * @author brett chaldecott
 */
public class RDBRoleHandler implements RoleHandler {
    
    // private member
    private static Logger log = Logger.getLogger(RDBRoleHandler.class);
    private static DataSource ds = null;
    
    
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
        Connection connection = null;
        try {
            if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
                log.info("The deployment has not completed");
                throw new SecurityException(
                    "The deployment has not been completed");
            }
            transaction = new UserTransactionWrapper();
            transaction.begin();
            connection = getConnection();
            ResultSet result = 
                    connection.prepareStatement("SELECT * FROM CoadunationRole")
                            .executeQuery();
            Map results = new HashMap();
            while(result.next()) {
                String role = result.getString("role");
                results.put(role,getRole(connection,role));
            }
            return results;
        } catch (SecurityException ex) {
            log.warn("Failed to retrieve the list of roles : " +
                    ex.getMessage(), ex);
            return new HashMap();
        } catch (Exception ex) {
            log.warn("Failed to retrieve the list of roles : " +
                    ex.getMessage(), ex);
            return new HashMap();
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception ex) {
                    //ignore
                }
                try {
                    connection.close();
                } catch(Exception ex) {
                    // ignore
                }
            }
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
        Connection connection = null;
        try {
            if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
               return null;
            } 
            transaction = new UserTransactionWrapper();
            transaction.begin();
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT ROLE FROM CoadunationRole WHERE ROLE = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
               return null; 
            }
            return getRole(connection,role);
        } catch (Exception ex) {
            log.warn("Failed to retrieve the role: " +
                    ex.getMessage(), ex);
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception ex) {
                    //ignore
                }
                try {
                    connection.close();
                } catch(Exception ex) {
                    // ignore
                }
            }
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
    private Role getRole(Connection connection, String role) throws RDBException {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM CoadunationRolePrincipal WHERE ROLE = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            Set principals = new HashSet();
            while(rs.next()) {
                String principal = rs.getString("name");
                principals.add(principal);
            }
            return new Role(role,principals);
        } catch (Exception ex) {
            log.warn("Failed to retrieve the ROLE information : " + ex.getMessage(),ex);
            throw new RDBException
                    ("Failed to retrieve the ROLE information : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is used to retrieve a connection to the database.
     * 
     * @return The connection to the database.
     * @throws RDBException 
     */
    private synchronized static Connection getConnection() throws RDBException {
        try {
            if (ds == null) {
                Context context = new InitialContext();
                Configuration config = ConfigurationFactory.getInstance().
                        getConfig(RDBUserStore.class);
                ds = (DataSource)context.lookup(
                        config.getString("DATA_SOURCE","java:comp/env/jdbc/hsqldb"));
            }
            Connection connection = ds.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the connection : " + ex.getMessage(),ex);
            throw new RDBException(
                "Failed to retrieve the connection : " + ex.getMessage(),ex);
        }
    }
}
