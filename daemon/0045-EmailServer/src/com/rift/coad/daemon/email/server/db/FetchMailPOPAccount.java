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
 * EmailAlias.java
 */

// the package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Set;
import java.util.HashSet;



/**
 * The email alias.
 *
 * @author brett chaldecott
 */
public class FetchMailPOPAccount {
    
    // private member variables
    private String emailAddress;
    private String account;
    private String server;
    private String password;
    private String dropBox;
    
    /**
     * Creates a new instance of EmailAlias
     */
    public FetchMailPOPAccount() {
    }
    
    
    /**
     * Creates a new instance of EmailAlias
     *
     * @param emailAddress The email address that identfies this account.
     * @param account The account used to authenticate this entry.
     * @param server The server that the request must be made to.
     * @param password The password used to authenticate this user.
     * @param dropBox The drop box that overrides the domain drop box.
     */
    public FetchMailPOPAccount(String emailAddress, String account, 
            String server, String password, String dropBox) {
        this.emailAddress = emailAddress;
        this.account = account;
        this.server = server;
        this.password = password;
        this.dropBox = dropBox;
    }
    
    
    /**
     * This method returns the email address for this account
     *
     * @return The string containing the email address for this account.
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    
    
    /**
     * This method sets the email address for this account
     *
     * @param emailAddress The string containing the email address for this account.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
    /**
     * This method returns the account.
     *
     * @return The string containing the account information.
     */
    public String getAccount() {
        return account;
    }
    
    
    /**
     * This method sets the account.
     *
     * @param account The string containing the account information.
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    
    /**
     * This method returns the server name.
     *
     * @return The string containing the server name.
     */
    public String getServer() {
        return server;
    }
    
    
    /**
     * This method sets the server name.
     *
     * @param server The string containing the new server name.
     */
    public void setServer(String server) {
        this.server = server;
    }
    
    
    /**
     * This method retrieves the password for the account.
     *
     * @return The string containing the password information.
     */
    public String getPassword() {
        return password;
    }
    
    
    /**
     * This method sets the password for the account.
     *
     * @param password The password for the account.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * This method retrieves the drop box information.
     *
     * @return The string containing the drop box information.
     */
    public String getDropBox() {
        return dropBox;
    }
    
    
    /**
     * This method is responsible for setting the drop box for a domain.
     *
     * @param dropBox The drop box.
     */
    public void setDropBox(String dropBox) {
        this.dropBox = dropBox;
    }
    
}
