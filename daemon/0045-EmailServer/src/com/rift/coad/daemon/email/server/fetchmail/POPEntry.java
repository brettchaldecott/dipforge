/*
 * Email Server: The email server
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
 * FetchMailQueue.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.Date;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// email imports
import com.rift.coad.daemon.email.server.db.FetchMailPOPAccount;


/**
 * This object represents an entry in the fetch mail queue.
 *
 * @author brett
 */
public class POPEntry implements FetchEntry {
    
    // class singletons
    private static Logger log = Logger.getLogger(POPEntry.class);

    // private member variables
    private String emailAddress;
    private String account;
    private String server;
    private String password;
    private String dropBox;
    private Date retryTime = null;
    private boolean isDeleted = false;
    
    /**
     * This method constructs a new entry object.
     */
    public POPEntry(FetchMailPOPAccount popAccount) {
        this.emailAddress = popAccount.getEmailAddress();
        this.account = popAccount.getAccount();
        this.server = popAccount.getServer();
        this.password = popAccount.getPassword();
        this.dropBox = popAccount.getDropBox();
    }
    
    
    /**
     * This method constructs a new entry object.
     *
     * @param emailAddress The email address.
     * @param account The account.
     * @param server The server.
     * @param password The password.
     * @param dropBox The drop box.
     *
     * @exception ServerException
     */
    public POPEntry(String emailAddress, String account, String server, 
            String password, String dropBox) throws ServerException {
        this.emailAddress = emailAddress;
        this.account = account;
        this.server = server;
        this.password = password;
        this.dropBox = dropBox;
        
        try {
            Session session = 
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            FetchMailPOPAccount popAccount = new FetchMailPOPAccount(
                    emailAddress, account, server, password, dropBox);
            session.persist(popAccount);
        } catch (Exception ex) {
            log.error("Failed to store the pop account information : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to store the pop account information : " + 
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method retrieves the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }
    
    
    /**
     * This method returns the account.
     */
    public String getAccount() {
        return account;
    }
    
    
    /**
     * This method returns the server.
     */
    public String getServer() {
        return server;
    }
    
    
    /**
     * This method retrieves the password for the account.
     */
    public String getPassword() {
        return password;
    }
    
    
    /**
     * This method retrieves the password for the account.
     *
     * @param password The password value to set.
     * @exception ServerException
     */
    public void setPassword(String password) throws ServerException {
        this.password = password;
        try {
            Session session = 
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            FetchMailPOPAccount popAccount = (FetchMailPOPAccount)session.get(
                    FetchMailPOPAccount.class,this.emailAddress);
            popAccount.setPassword(password);
        } catch (Exception ex) {
            log.error("Failed to set the password for the account : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to set the password for the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the drop box
     */
    public String getDropBox() {
        return dropBox;
    }
    
    
    /**
     * This method returns the drop box
     *
     * @param dropBox The drop box to retrieve.
     * @exception ServerException
     */
    public void setDropBox(String dropBox) throws ServerException {
        this.dropBox = dropBox;
        try {
            Session session = 
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            FetchMailPOPAccount popAccount = (FetchMailPOPAccount)session.get(
                    FetchMailPOPAccount.class,this.emailAddress);
            popAccount.setPassword(password);
        } catch (Exception ex) {
            log.error("Failed to set the password for the account : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to set the password for the account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method gets the retrieve time.
     */
    public Date getRetryTime() {
        return retryTime;
    }
    
    
    /**
     * This method re-calculates the retrieve time.
     */
    public void recalculateRetryTime(long retryInterval) {
        if (retryTime != null) {
            retryTime.setTime(new Date().getTime() + retryInterval);
        } else {
            retryTime = new Date();
        }
    }

    /**
     * The equals method.
     *
     * @return TRUE if they are equal FALSE if not.
     * @param value The value to performm the comparison on.
     */
    public boolean equals(Object value) {
        if (value == this) {
            return true;
        } else if (!(value instanceof POPEntry)) {
            return false;
        }
        POPEntry entry = (POPEntry)value;
        return entry.getEmailAddress().equals(this.getEmailAddress());
    }
    
    
    /**
     * This method performs a comparison of this object.
     */
    public int compareTo(Object value) {
        if (value == this) {
            return 0;
        } else if (!(value instanceof POPEntry)) {
            return -1;
        }
        POPEntry entry = (POPEntry)value;
        if (entry.getRetryTime().getTime() == this.getRetryTime().getTime()) {
            return this.getEmailAddress().compareTo(
                    entry.getEmailAddress());
        }
        return (int)(this.getRetryTime().getTime() - entry.getRetryTime().getTime());
    }
    
}
