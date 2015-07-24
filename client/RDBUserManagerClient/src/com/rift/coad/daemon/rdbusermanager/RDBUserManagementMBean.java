/*
 * RDBUserManagerClient: The client of the RDB User Manager
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
 * RDBUserManagementMBean.java
 */

// package path
package com.rift.coad.daemon.rdbusermanager;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;

/**
 * This interface defines the rdb user management bean.
 * 
 * @author brett chaldecott
 */
public interface RDBUserManagementMBean extends Remote {
    /**
     * This method returns the version of this object.
     * 
     * @return The version information for this management object.
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the version information.")
    @Version(number="1.0")
    @Result(description="This method returns the version information.")
    public String getVersion() throws RemoteException;
    
    
    /**
     * This object returns the name of this object.
     * 
     * @return The string containing the name of this object.
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the name of this object.")
    @Version(number="1.0")
    @Result(description="This name of this object.")
    public String getName() throws RemoteException;
    
    
    /**
     * This object returns the name of this object.
     * 
     * @return The string containing the name of this object.
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the description of this object.")
    @Version(number="1.0")
    @Result(description="This method returns the description of this object.")
    public String getDescription() throws RemoteException;
    
    
    
    /**
     * This method returns the definition of the requested user.
     * 
     * @return The RDBUser object containing the definition of the user.
     * @param username The name of the user to add.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the definition on the requested user.")
    @Version(number="1.0")
    @Result(description="The definition of the requested user.")
    public RDBUser getUser(@ParamInfo(name="username",
            description="The name of the user to retrieve.")String username)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method returns the list of users.
     * 
     * @return The list of users.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns a list of users.")
    @Version(number="1.0")
    @Result(description="The list of users stored in the database.")
    public List listUsers() throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method adds a new user.
     *
     * @param username The name of the user to retrieve.
     * @param password The password to authenticate the user.
     * @param principals The list of principals.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to add a new user.")
    @Version(number="1.0")
    public void addUser(@ParamInfo(name="username",
            description="The name of the user to create")String username,
            @ParamInfo(name="password",
            description="The password to authenticate the user")String password,
            @ParamInfo(name="principals",
            description="An array of principals. When using the admin console this is a comma deliminated list.")String[] principals)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method updates the users password.
     *
     * @param username The name of the user to retrieve.
     * @param password The new password to authenticate the user.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to add a new user.")
    @Version(number="1.0")
    public void updateUserPassword(@ParamInfo(name="username",
            description="The name of the user to update")String username,
            @ParamInfo(name="password",
            description="The new password to authenticate the user")String password)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method adds a new user.
     *
     * @param username The name of the user to retrieve.
     * @param password The password to authenticate the user.
     * @param principals The list of principals.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to update a users principals.")
    @Version(number="1.0")
    public void updateUserPrincipal(@ParamInfo(name="username",
            description="The name of the user to update")String username,
            @ParamInfo(name="principals",
            description="An array of principals. When using the admin console this is a comma deliminated list.")String[] principals)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method is used to remove a new user.
     *
     * @param username The name of the user to retrieve.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is used to remove a user.")
    @Version(number="1.0")
    public void removeUser(@ParamInfo(name="username",
            description="The name of the user to remove")String username)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method returns the role information.
     * 
     * @return The RDBRole object containing the definition of the role.
     * @param role The name of the role to retrieve.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method retrieves the information for the specified role.")
    @Version(number="1.0")
    @Result(description="The object containing the role information.")
    public RDBRole getRole(@ParamInfo(name="role",
            description="The name of the role to retrieve.")String role)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method returns the list of roles.
     * 
     * @return The list of roles.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the list of roles.")
    @Version(number="1.0")
    @Result(description="The list of roles stored in the database.")
    public List listRoles() throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method adds a new role.
     *
     * @param role The name of the role to add
     * @param principals The list of principals.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to add a new role.")
    @Version(number="1.0")
    public void addRole(@ParamInfo(name="role",
            description="The name of the role to create")String role,
            @ParamInfo(name="principals",
            description="An array of principals. When using the admin console this is a comma deliminated list.")String[] principals)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method updates a role.
     *
     * @param role The name of the role to update
     * @param principals The list of principals.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to update a role.")
    @Version(number="1.0")
    public void updateRole(@ParamInfo(name="update",
            description="The name of the role to update")String role,
            @ParamInfo(name="principals",
            description="An array of principals. When using the admin console this is a comma deliminated list.")String[] principals)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method is used to remove a new user.
     *
     * @param role The name of the role to retrieve.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is used to remove a role.")
    @Version(number="1.0")
    public void removeRole(@ParamInfo(name="role",
            description="The name of the role to remove")String role)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method returns the list of principals.
     * 
     * @return The list of principals.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the list of principals.")
    @Version(number="1.0")
    @Result(description="The list of principals.")
    public List listPrincipals() throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method adds a new principal.
     *
     * @param principal The name of the principal to add
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is invoked to add a new principal.")
    @Version(number="1.0")
    public void addPrincipal(@ParamInfo(name="principal",
            description="The name of the principal to create")String principal)
            throws RDBUserManagementException, RemoteException;
    
    
    /**
     * This method is used to remove a principal.
     *
     * @param principal The name of the role to retrieve.
     * @throws RDBUserManagementException
     * @throws RemoteException
     */
    @MethodInfo(description="This method is used to remove a principal.")
    @Version(number="1.0")
    public void removePrincipal(@ParamInfo(name="principal",
            description="The name of the principal to remove")String principal)
            throws RDBUserManagementException, RemoteException;
    
}
