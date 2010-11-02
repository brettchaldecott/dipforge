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
 * FetchMailPOPAccount.java
 */

// package path
package com.rift.coad.daemon.email;

/**
 * This object represents a fetch mail pop account.
 *
 * @author brett chaldecott
 */
public class FetchMailPOPAccount implements java.io.Serializable {
    
    // private member variables
    private String emailAddress;
    private String account;
    private String server;
    private String dropBox;
    
    /**
     * Creates a new instance of FetchMailPOPAccount
     */
    public FetchMailPOPAccount() {
    }
    
    
    /**
     * Creates a new instance of FetchMailPOPAccount
     *
     * @param emailAddress The email address identifying this account.
     * @param account The account.
     * @param server The server
     * @param targetAddress The address to create.
     */
    public FetchMailPOPAccount(String emailAddress, String account, String server, 
            String dropBox) {
        this.emailAddress = emailAddress;
        this.account = account;
        this.server = server;
        this.dropBox = dropBox;
    }
    
    
    /**
     * This method returns the email address.
     *
     * @return The string email address.
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    
    /**
     * This method sets the new email address
     *
     * @param account The account.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
    /**
     * This method returns the new account.
     *
     * @return The string containing the account.
     */
    public String getAccount() {
        return this.account;
    }
    
    
    /**
     * This method sets the new account.
     *
     * @param account The account.
     */
    public void setAccount(String account) {
        this.account = account;
    }
    
    
    /**
     * This method returns the server name.
     *
     * @return The string containing the server.
     */
    public String getServer() {
        return server;
    }
    
    
    /**
     * This method sets the server name.
     *
     * @param server The new server value.
     */
    public void setServer(String server) {
        this.server = server;
    }
    
    
    /**
     * This method retrieves the target drop box.
     *
     * @return The string containing the drop box.
     */
    public String getDropBox() {
        return dropBox;
    }
    
    
    /**
     * This method retrieves the drop box.
     *
     * @return The string containing the drop box.
     */
    public void setDropBox(String dropBox) {
        this.dropBox = dropBox;
    }
    
    
    /**
     * This method returns the string value of the fetch mail account.
     */
    public String toString() {
        return this.emailAddress + ":" + this.account + ":" + this.server + ":" 
                + this.dropBox;
    }
}
