/*
 * Email Server: The email server interface
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
 * FetchMailServerMBean.java
 */

package com.rift.coad.daemon.email;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;



/**
 * This interface is responsible for providing a means to manage the fetch mail
 * server. It provides both an RMI and JMX interface.
 *
 * @author brett chaldecott
 */
public interface FetchMailServerMBean extends Remote {
    /**
     * This method returns the version of the fetch mail server.
     *
     * @return The containing the version of the fetch mail server.
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the version of the fetch mail server")
    @Version(number="1.0")
    @Result(description="The string containing the version of the fetch mail server")
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of the fetch mail server.
     *
     * @return The string containing the name of the fetch mail server.
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the name of the fetch mail server")
    @Version(number="1.0")
    @Result(description="The string containing the name of the fetch mail server")
    public String getName() throws RemoteException;
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the description of the server.")
    @Version(number="1.0")
    @Result(description="The string containing the description")
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns the string containing the status of the fetch mail server.
     *
     * @return The string containing the status of the fetch mail server.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the status of the server")
    @Version(number="1.0")
    @Result(description="A string containing the status of the fetch mail server")
    public String getStatus() throws EmailException, RemoteException;
    
    
    /**
     * This method retrieves a list of pop accounts.
     *
     * @return A list of pop accounts maintained by this server.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve a list of pop accounts.")
    @Version(number="1.0")
    @Result(description="A list of email addresses that this server retrieves mail for")
    public List listPOPAccounts()
            throws EmailException, RemoteException;
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     * @param targetAddress The target address to forward the mail to.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Add a new fetchmail account to the server.")
    @Version(number="1.0")
    public void addPOPAccount(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies this account [Wild cards are accepted *@domain.com.]")
            String emailAddress,
            @ParamInfo(name="account",
            description="The account to pop the mail for")String account,
            @ParamInfo(name="server",
            description="The server name to retrieve the mail from")String server,
            @ParamInfo(name="password",
            description="The password for the account")String password) 
            throws EmailException, RemoteException;
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     * @param dropBox The drop box for the domain over riding the domain drop box.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Add a new fetchmail account to the server.")
    @Version(number="1.0")
    public void addPOPAccount(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies this account [Wild cards are accepted *@domain.com.]")
            String emailAddress,
            @ParamInfo(name="account",
            description="The account to pop the mail for")String account,
            @ParamInfo(name="server",
            description="The server name to retrieve the mail from")String server,
            @ParamInfo(name="password",
            description="The password for the account")String password,
            @ParamInfo(name="dropBox",
            description="The drop box for the email address overriding the domain drop box.")
            String dropBox) throws EmailException, RemoteException;
    
    
    /**
     * This method updates the pop account password on the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Update a fetchmail pop account password on the server.")
    @Version(number="1.0")
    public void updatePOPAccountPassword(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies the accout.")String emailAddress,
            @ParamInfo(name="password",
            description="The password for the account")String password)
            throws EmailException, RemoteException;
    
    
    /**
     * This method updates the drop box for a pop account.
     *
     * @param emailAddress The email address that identifies this account.
     * @param dropBox The drop box for the email address that overrides the domain drop box.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Update the pop account for the given target address.")
    @Version(number="1.0")
    public void updatePOPAccountDropBox(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies this account")String emailAddress,
            @ParamInfo(name="dropBox",
            description="The drop box for the domain that overrides the domain drop box.")String dropBox)
            throws EmailException, RemoteException;
    
    
    
    /**
     * This method retrieves the POP account details.
     *
     * @return The fetch mail pop account details.
     * @param emailAddress The email address that identifies this account.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve a POP account.")
    @Version(number="1.0")
    @Result(description="The object detailing the pop account information.")
    public FetchMailPOPAccount getPOPAccount(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies this account.")String emailAddress)
            throws EmailException, RemoteException;
    
    
    /**
     * This method deletes the pop account from the server.
     *
     * @param emailAddress The email address that identifies this account
     *
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Delete a pop fetch mail acccount.")
    @Version(number="1.0")
    public void deletePOPAccount(
            @ParamInfo(name="emailAddress",
            description="The email address that identifies this account.")
            String emailAddress)
            throws EmailException, RemoteException;
    
}
