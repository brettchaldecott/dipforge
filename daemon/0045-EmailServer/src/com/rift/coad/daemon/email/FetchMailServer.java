/*
 * Email Server: The email server
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
 * FetchMailServer.java
 */

// package path
package com.rift.coad.daemon.email;

// log4j imports
import org.apache.log4j.Logger;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.util.transaction.UserTransactionWrapper;

// email imports
import com.rift.coad.daemon.email.server.fetchmail.FetchMailManager;
import com.rift.coad.daemon.email.server.fetchmail.Server;
import com.rift.coad.daemon.email.server.fetchmail.POPEntry;

/**
 * This interface is responsible for providing a means to manage the fetch mail
 * server. It provides both an RMI and JMX interface.
 *
 * @author brett chaldecott
 */
public class FetchMailServer implements FetchMailServerMBean, BeanRunnable {
    
    // private member variables
    private Logger log = Logger.getLogger(FetchMailServer.class);
    private ThreadStateMonitor state = new ThreadStateMonitor();
    
    
    /**
     * The class constructor
     */
    public FetchMailServer() throws EmailException {
        try {
            Server.getInstance();
            
        } catch (Exception ex) {
            log.error("Failed to initialize the fetch mail server : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to initialize the fetch mail server : " + 
                    ex.getMessage(),ex);
        }
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
     * @exception RemoteException
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
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            return Server.getInstance().getStatus();
        } catch (Exception ex) {
            log.error("Failed to retrieve the status : " + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the status : " + ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method retrieves a list of pop accounts.
     *
     * @return A list of pop accounts maintained by this server.
     * @exception EmailException
     */
    public List listPOPAccounts() throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            return FetchMailManager.getInstance().listPOPAccounts();
        } catch (Exception ex) {
            log.error("Failed to retrieve the accounts list : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the accounts list : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     */
    public void addPOPAccount(String emailAddress, String account, String server,
            String password) throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            FetchMailManager.getInstance().addPOPAccount(emailAddress, account,
                    server, password);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to add the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to add the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     * @param dropBox The drop box that overrides the domain drop box.
     *
     * @exception EmailException
     */
    public void addPOPAccount(String emailAddress, String account, String server,
            String password, String dropBox) throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            FetchMailManager.getInstance().addPOPAccount(emailAddress, account,
                    server, password, dropBox);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to add the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to add the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method updates the pop account password on the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param password The password to authenticate the account with.
     *
     * @exception EmailException
     */
    public void updatePOPAccountPassword(String emailAddress, String password)
            throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            FetchMailManager.getInstance().updatePOPAccountPassword(
                    emailAddress, password);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to update the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to update the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method updates the target address for a pop account.
     *
     * @param emailAddress The email address that identifies this account.
     * @param dropBox The drop box that overrides the mail domain drop box.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void updatePOPAccountDropBox(String emailAddress, 
            String dropBox) throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            FetchMailManager.getInstance().updatePOPAccountDropBox(
                    emailAddress, dropBox);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to update the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to update the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    
    /**
     * This method retrieves the POP account details.
     *
     * @return The fetch mail pop account details.
     * @param emailAddress The email address that identifies this account.
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public FetchMailPOPAccount getPOPAccount(String emailAddress) 
    throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            POPEntry entry = FetchMailManager.getInstance().getPOPAccount(
                    emailAddress);
            FetchMailPOPAccount account = new FetchMailPOPAccount(
                    entry.getEmailAddress(),entry.getAccount(),
                    entry.getServer(), entry.getDropBox());
            return account;
        } catch (Exception ex) {
            log.error("Failed to retrieve the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method deletes the pop account from the server.
     *
     * @param emailAddress The email address that identifies this account
     *
     * @exception EmailException
     * @exception RemoteException
     */
    public void deletePOPAccount(String emailAddress)
            throws EmailException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            FetchMailManager.getInstance().deletePOPAccount(emailAddress);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to delete the account : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to delete the account : " + 
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to release the transaction : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method will be called to begin the processing required by this
     * object.
     */
    public void process() {
        try {
            Server.initialize();
        } catch (Exception ex) {
            // ignore the error
        }
        // wait until the thread is terminate
        while(!state.isTerminated()) {
            state.monitor();
        }

    }
    
    
    /**
     * This method terminates the processing
     */
    public void terminate() {
        state.terminate(true);
        
        try {
            FetchMailManager.getInstance().terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the fetch mail manager: " + 
                    ex.getMessage(),ex);
        }
        
        try {
            Server.getInstance().terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the fetch mail server : " + 
                    ex.getMessage(),ex);
        }
        
        
    }
    
}
