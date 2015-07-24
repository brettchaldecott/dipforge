/*
 * Email Server: The email server interface
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
 * FetchMailServer.java
 */

package com.rift.coad.daemon.email.webservice;

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
public interface FetchMailServer extends Remote {
    /**
     * This method returns the version of the fetch mail server.
     *
     * @return The containing the version of the fetch mail server.
     * @exception RemoteException
     */
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of the fetch mail server.
     *
     * @return The string containing the name of the fetch mail server.
     * @exception RemoteException
     */
    public String getName() throws RemoteException;
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     * @exception RemoteException
     */
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns the string containing the status of the fetch mail server.
     *
     * @return The string containing the status of the fetch mail server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getStatus() throws EmailException, RemoteException;
    
    
    /**
     * This method retrieves a list of pop accounts.
     *
     * @return A list of pop accounts maintained by this server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listPOPAccounts()
            throws EmailException, RemoteException;
    
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address associated with this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void addPOPAccount(String emailAddress, String account,String server,
            String password) throws EmailException,
            RemoteException;
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address associated with this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     * @param dropBox The drop box for the email address overriding the domain
     * drop box.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void addPOPAccountWithDropBox(String emailAddress, String account,
            String server,String password, String dropBox) throws EmailException,
            RemoteException;
    
    
    /**
     * This method updates the pop account password on the server.
     *
     * @param account The account to add.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void updatePOPAccountPassword(String emailAddress, String password)
            throws EmailException, RemoteException;
    
    
    /**
     * This method updates the target address for a pop account.
     *
     * @param account The account to add.
     * @param targetAddress The updated target address for the account.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void updatePOPAccountDropBox(String emailAddress,
            String dropBox) throws EmailException, RemoteException;
    
    
    
    /**
     * This method retrieves the POP account details.
     *
     * @return The fetch mail pop account details.
     * @param emailAddress The email address identifiying this object.
     * @exception EmailException
     * @exception RemoteException
     */
    public FetchMailPOPAccount getPOPAccount(String emailAddress)
            throws EmailException, RemoteException;
    
    
    /**
     * This method deletes the pop account from the server.
     *
     * @param emailAddress The email address identifying this account.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void deletePOPAccount(String emailAddress) throws EmailException, 
            RemoteException;
    
}
