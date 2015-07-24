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
 * FetchMailServerImpl.java
 */

package com.rift.coad.daemon.email.webservice;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// log4j imports
import org.apache.log4j.Logger;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;

// email imports
import com.rift.coad.daemon.email.server.fetchmail.FetchMailManager;
import com.rift.coad.daemon.email.server.fetchmail.Server;
import com.rift.coad.daemon.email.server.fetchmail.POPEntry;


/**
 * This class is responsible for providing a means to manage the fetch mail
 * server. It provides both an RMI and JMX interface.
 *
 * @author brett chaldecott
 */
public class FetchMailServerImpl implements FetchMailServer {
    
    // class log object
    private Logger log = Logger.getLogger(FetchMailServerImpl.class);
    
    /**
     * The constructor of the fetch mail server.
     */
    public FetchMailServerImpl() {
        
    }
    
    
    /**
     * This method returns the version of the fetch mail server.
     *
     * @return The containing the version of the fetch mail server.
     */
    public String getVersion() {
        return "1";
    }
    
    
    /**
     * This method returns the name of the fetch mail server.
     *
     * @return The string containing the name of the fetch mail server.
     */
    public String getName() {
        return "FetchMailServer";
    }
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     */
    public String getDescription() {
        return "The Fetch Mail Server";
    }
    
    
    /**
     * This method returns the string containing the status of the fetch mail server.
     *
     * @return The string containing the status of the fetch mail server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getStatus() throws EmailException {
        try {
            String result = Server.getInstance().getStatus();
            result += "\nAccounts : " + FetchMailManager.getInstance().size();
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the status : " + ex.getMessage(),ex);
            throwEmailException(
                    "Failed to retrieve the status : " + ex.getMessage(),ex);
            // this is here for the compiler
            return null;
        }
    }
    
    
    /**
     * This method retrieves a list of pop accounts.
     *
     * @return A list of pop accounts maintained by this server.
     * @exception EmailException
     */
    public String[] listPOPAccounts() throws EmailException {
        try {
            return (String[])FetchMailManager.getInstance().listPOPAccounts().
                    toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to retrieve list of accounts : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to retrieve list of accounts : " + 
                    ex.getMessage(),ex);
            // this is here for the compiler
            return null;
        }
    }
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies the account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     */
    public void addPOPAccount(String emailAddress, String account,String server,
            String password) throws EmailException {
        try {
            FetchMailManager.getInstance().addPOPAccount(emailAddress, account,
                    server, password);
        } catch (Exception ex) {
            log.error("Failed to add the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to add the account : " + 
                    ex.getMessage(),ex);
        }        
    }
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies the account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     * @param dropBox The new drop box for an account.
     *
     * @exception EmailException
     */
    public void addPOPAccountWithDropBox(String emailAddress, String account,
            String server, String password,String dropBox) throws EmailException {
        try {
            FetchMailManager.getInstance().addPOPAccount(emailAddress, account,
                    server, password, dropBox);
        } catch (Exception ex) {
            log.error("Failed to add the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to add the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the pop account password on the server.
     *
     * @param emailAddress The email address that identifies the account.
     * @param account The account to add.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     */
    public void updatePOPAccountPassword(String emailAddress, String password) 
    throws EmailException {
        try {
            FetchMailManager.getInstance().updatePOPAccountPassword(
                    emailAddress, password);
        } catch (Exception ex) {
            log.error("Failed to update the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to update the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the target address for a pop account.
     *
     * @param emailAddress The email address that identifies the account.
     * @param dropBox The drop box for the account.
     *
     * @exception EmailException
     */
    public void updatePOPAccountDropBox(String emailAddress,
            String dropBox) throws EmailException {
        try {
            FetchMailManager.getInstance().updatePOPAccountDropBox(
                    emailAddress, dropBox);
        } catch (Exception ex) {
            log.error("Failed to update the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to update the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * This method retrieves the POP account details.
     *
     * @return The fetch mail pop account details.
     * @param emailAddress The email address that identifies this account.
     * @exception EmailException
     */
    public FetchMailPOPAccount getPOPAccount(String emailAddress)
            throws EmailException {
        try {
            POPEntry entry = FetchMailManager.getInstance().getPOPAccount(
                    emailAddress);
            FetchMailPOPAccount account = new FetchMailPOPAccount();
            account.emailAddress = entry.getEmailAddress();
            account.account = entry.getAccount();
            account.server = entry.getServer();
            account.dropBox = entry.getDropBox();
            return account;
        } catch (Exception ex) {
            log.error("Failed to retrieve the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to retrieve the account : " + 
                    ex.getMessage(),ex);
            // this is here for the compiler
            throw null;
        }
    }
    
    
    /**
     * This method deletes the pop account from the server.
     *
     * @param emailAddress The email address that identifies this account.
     *
     * @exception EmailException
     */
    public void deletePOPAccount(String emailAddress) throws EmailException {
        try {
            FetchMailManager.getInstance().deletePOPAccount(emailAddress);
        } catch (Exception ex) {
            log.error("Failed to delete the account : " + 
                    ex.getMessage(),ex);
            throwEmailException(
                    "Failed to delete the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @exception DNSException
     */
    private void throwEmailException(String message, Throwable ex) throws
            EmailException {
        EmailException exception = new EmailException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        throw exception;
    }

}
