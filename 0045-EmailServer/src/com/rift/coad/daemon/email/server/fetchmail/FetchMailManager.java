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
 * FetchMailManager.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.CoadunationHashMap;

// email imports
import com.rift.coad.daemon.email.server.db.FetchMailPOPAccount;

/**
 * This object is responsible for managing the fetch mail entries.
 *
 * @author brett chaldecott
 */
public class FetchMailManager {
    
    
    
    // class singletons
    private static FetchMailManager singleton = null;
    private static Logger log = Logger.getLogger(FetchMailManager.class);
    
    // private member variables
    private Map accounts = new CoadunationHashMap();
    private FetchMailQueue queue = new FetchMailQueue();
    private Map deletedAccounts = new CoadunationHashMap();
    /**
     * Creates a new instance of FetchMailQueue
     *
     * @exception ServerException
     */
    private FetchMailManager() throws ServerException {
        UserTransactionWrapper transaction = null;
        try {
            HibernateUtil.getInstance(FetchMailManager.class);
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            List list = session.createQuery("FROM FetchMailPOPAccount as account").list();
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                POPEntry entry = new POPEntry((FetchMailPOPAccount)iter.next());
                log.debug("Load entry : " + entry.getEmailAddress());
                accounts.put(entry.getEmailAddress(),entry);
                queue.add(entry);
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to load the fetch mail configuration " +
                    "information : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to load the fetch mail configuration " +
                    "information : " + ex.getMessage(),ex);
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
     * This method returns an instance of the fetch mail queue object.
     */
    public static synchronized FetchMailManager getInstance() throws 
            ServerException {
        if (singleton == null) {
            singleton = new FetchMailManager();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the size of the fetch mail manager, this is the
     * number of accounts managed by it.
     */
    public int size() {
        return this.accounts.size();
    }
    
    
    /**
     * This method return the next entry
     */
    public FetchEntry pop(UserTransactionWrapper transaction) throws
            ServerException {
        return queue.pop(transaction);
    }
    
    
    /**
     * This method pushes an entry back onto the queue.
     *
     * @param entry The entry to add back.
     */
    public void push(FetchEntry entry) throws
            ServerException {
        if (this.deletedAccounts.containsKey(entry.getEmailAddress())) {
            this.deletedAccounts.remove(entry.getEmailAddress());
            return;
        }
        queue.add(entry);
    }
    
    
    /**
     * This method retrieves a list of pop accounts.
     *
     * @return A list of pop accounts maintained by this server.
     * @exception ServerException
     */
    public List listPOPAccounts() throws ServerException {
        List result = new ArrayList();
        for (Iterator iter = this.accounts.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            Object entry = accounts.get(key);
            if (entry instanceof POPEntry) {
                result.add(key);
            }
        }
        return result;
    }
    
    
    /**
     * This method add a new pop account to the server.
     *
     * @param emailAddress The email address that identifies this account.
     * @param account The account to add.
     * @param server The server to retrieve the mail from.
     * @param password The password to authenticate the account with.
     *
     * @exception ServerException
     */
    public void addPOPAccount(String emailAddress, String account, String server,
            String password) throws ServerException {
        try {
            Session session =
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            FetchMailPOPAccount dbAccount = (FetchMailPOPAccount)session.get(
                    FetchMailPOPAccount.class,emailAddress);
            if (dbAccount != null) {
                log.error("Attempting to add a duplicate account : " +
                        emailAddress);
                throw new ServerException(
                        "Attempting to add a duplicate account : " +
                        emailAddress);
            }
            dbAccount = new FetchMailPOPAccount(emailAddress,account, server,
                    password, null);
            session.persist(dbAccount);
            POPEntry popEntry = new POPEntry(dbAccount);
            this.accounts.put(emailAddress,popEntry);
            this.queue.add(popEntry);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a pop entry : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add a pop entry : " + ex.getMessage(),ex);
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
     * @exception ServerException
     */
    public void addPOPAccount(String emailAddress, String account, String server,
            String password, String dropBox) throws ServerException {
        try {
            Session session =
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            FetchMailPOPAccount dbAccount = (FetchMailPOPAccount)session.get(
                    FetchMailPOPAccount.class,emailAddress);
            if (dbAccount != null) {
                log.error("Attempting to add a duplicate account : " +
                        emailAddress);
                throw new ServerException(
                        "Attempting to add a duplicate account : " +
                        emailAddress);
            }
            dbAccount = new FetchMailPOPAccount(emailAddress,account, server,
                    password, dropBox);
            session.persist(dbAccount);
            POPEntry popEntry = new POPEntry(dbAccount);
            this.accounts.put(emailAddress,popEntry);
            this.queue.add(popEntry);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a pop entry : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add a pop entry : " + ex.getMessage(),ex);
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
    throws ServerException {
        Object entry = this.accounts.get(emailAddress);
        if ((entry == null) || !(entry instanceof POPEntry)) {
            log.error("Failed to update the pop entry [" + emailAddress
                    + "] as it does not exist.");
            throw new ServerException(
                    "Failed to update the pop entry [" + emailAddress
                    + "] as it does not exist.");
        }
        POPEntry popEntry = (POPEntry)entry;
        popEntry.setPassword(password);
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
            String dropBox) throws ServerException {
        Object entry = this.accounts.get(emailAddress);
        if ((entry == null) || !(entry instanceof POPEntry)) {
            log.error("Failed to update the drop box [" + emailAddress
                    + "] as it does not exist.");
            throw new ServerException(
                    "Failed to update the drop box [" + emailAddress
                    + "] as it does not exist.");
        }
        POPEntry popEntry = (POPEntry)entry;
        popEntry.setDropBox(dropBox);
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
    public POPEntry getPOPAccount(String emailAddress)
    throws ServerException {
        Object entry = this.accounts.get(emailAddress);
        if ((entry == null) || !(entry instanceof POPEntry)) {
            log.error("The entry [" + emailAddress
                    + "] does not exist.");
            throw new ServerException(
                    "The entry [" + emailAddress
                    + "] does not exist.");
        }
        return (POPEntry)entry;
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
    throws ServerException {
        try {
            Object entry = this.accounts.get(emailAddress);
            if ((entry == null) || !(entry instanceof POPEntry)) {
                log.error("The entry [" + emailAddress
                        + "] does not exist.");
                throw new ServerException(
                        "The entry [" + emailAddress
                        + "] does not exist.");
            }
            POPEntry popEntry = (POPEntry)entry;
            if (!this.queue.contains(popEntry)) {
                this.deletedAccounts.put(popEntry.getEmailAddress(),popEntry);
            }
            Session session =
                    HibernateUtil.getInstance(FetchMailManager.class).getSession();
            session.createSQLQuery(
                    "DELETE FROM FetchMailPOPAccount as account " +
                    "WHERE " +
                    " account.emailAddress = ?").setString(0,emailAddress)
                    .executeUpdate();
            this.accounts.remove(popEntry.getEmailAddress());
            this.queue.remove(popEntry);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete the pop account : " + 
                    ex.getMessage(),ex);
            throw new ServerException("Failed to delete the pop account : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to terminate the various queues.
     */
    public void terminate() {
        this.queue.terminate();
    }
}
