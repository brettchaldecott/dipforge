/*
 * CoadunationLib: The coaduntion implementation library.
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
 * ConfigurationRoleHandler.java
 */

// package path
package com.rift.coad.lib.security.role;

// java import
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Role;
import com.rift.coad.lib.security.RoleHandler;
import com.rift.coad.lib.security.SecurityException;


/**
 * This class is responsible for implementing the traditional configuration
 * based role handler.
 *
 * @author brett
 */
public class ConfigurationRoleHandler implements RoleHandler {
    
    // the classes constant static variables
    public static final String PRINCIPALS = "principals";
    public static final String ROLES = "roles";
    
    // private member variables
    private Set principals = new HashSet();
            
    /** 
     * Creates a new instance of ConfigurationRoleHandler
     */
    public ConfigurationRoleHandler() {
    }
    
    
    /**
     * This method returns the roles managed by this class.
     *
     * @return The map containing the roles
     *
     * @exception SecurityException
     */
    public Map getRoles() throws SecurityException {
        try {
            Map roles = new HashMap();
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    RoleManager.class);
            StringTokenizer principalList = new StringTokenizer(
                    config.getString(PRINCIPALS),",");
            while(principalList.hasMoreTokens()) {
                principals.add(principalList.nextToken().trim());
            }
            StringTokenizer roleList = new StringTokenizer(
                    config.getString(ROLES),",");
            while(roleList.hasMoreTokens()) {
                String role = (String)roleList.nextToken().trim();
                roles.put(role,loadRole(config,role));
            }
            return roles;
        } catch (Exception ex) {
            throw new SecurityException("Failed to load the role information : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will load the required role into memory.
     *
     * @return The role to load into memory.
     * @param config The configuration class.
     * @param role The string identifying the role.
     * @exception SecurityException
     */
    private Role loadRole(Configuration config,String role) 
            throws SecurityException {
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(
                    config.getString(role),",");
            Set roleSet = new HashSet();
            while(stringTokenizer.hasMoreTokens()) {
                String principal = (String)stringTokenizer.nextToken();
                if (principals.contains(principal) == false) {
                    throw new SecurityException("Invalid principal : " + 
                            principal);
                }
                roleSet.add(principal);
            }
            return new Role(role,roleSet);
        } catch (Exception ex) {
            throw new SecurityException("The list of roles : " + ex.getMessage(),
                    ex);
        }
    }

    
    /**
     * This method returns the configuration information.
     * 
     * @param role The string identifying the role name.
     * @return The object representing the role.
     * @throws com.rift.coad.lib.security.SecurityException
     */
    public Role getRole(String role) throws SecurityException {
        return (Role)getRoles().get(role);
    }
}
