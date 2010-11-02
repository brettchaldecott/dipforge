/*
 * EMail: The email server
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
 * EMailServer.java
 */

package com.rift.coad.daemon.email;

// java imports
import java.rmi.RemoteException;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.daemon.email.server.Server;
import com.rift.coad.util.transaction.UserTransactionWrapper;

// email server imports
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.config.ServerRelay;
import com.rift.coad.daemon.email.server.config.ServerDomain;

/**
 * The implementation of the EMailServerMBean interface.
 *
 * @author brett chaldecott
 */
public class EMailServer implements EMailServerMBean, BeanRunnable {
    
    // private member variables
    private Logger log = Logger.getLogger(EMailServer.class);
    private ThreadStateMonitor state = new ThreadStateMonitor();
    
    /**
     * Creates a new instance of EMailServer
     */
    public EMailServer() throws EmailException {
        try {
            Server.instantiate();
        } catch (Exception ex) {
            log.error("Failed to instantiate the email server : " + 
                    ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to instantiate the email server : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the version of the dns server.
     *
     * @return The string contain the version information.
     * @exception RemoteException
     */
    public String getVersion() {
        return EMailServerConstants.VERSION;
    }
    
    
    /**
     * This method returns the name ofe the dns server.
     *
     * @return The string containing the name of the dns server.
     * @exception RemoteException
     */
    public String getName() {
        return EMailServerConstants.NAME;
    }
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     * @exception RemoteException
     */
    public String getDescription() {
        return EMailServerConstants.DESCRIPTION;
    }
    
    
    /**
     * This method returns the string containing the status of the dns server.
     *
     * @return The string containing the status of the dns server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getStatus() throws EmailException {
        try {
            return Server.getInstance().getStatus();
        } catch (Exception ex) {
            log.error("Failed to retrieve the status : " + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the status : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the domains managed by the email server.
     *
     * @return The list of domains managed by this server.
     * @exception EmailException
     */
    public List listDomains() throws EmailException {
        try {
            return ServerConfig.getInstance().getDomain().listDomains();
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of domains : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the list of domains : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, but does not supply a default drop box for it. If no box is found
     * for this domain the mail will be bounced.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addDomain(String domain) throws EmailException {
        try {
            ServerConfig.getInstance().getDomain().addDomain(domain);
        } catch (Exception ex) {
            log.error("Failed to add a domain to the the list of domains : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to add a domain to the the list of domains : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, and supplies the default drop box for a domain.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addDomain(String domain, String dropBox) throws EmailException {
        try {
            ServerConfig.getInstance().getDomain().addDomain(domain,dropBox);
        } catch (Exception ex) {
            log.error("Failed to add a domain to the the list of domains : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to add a domain to the the list of domains : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes a domain so that it will no longer be seen as local
     * delivery for it.
     *
     * @param domain The domain to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeDomain(String domain) throws EmailException {
        try {
            ServerConfig.getInstance().getDomain().removeDomain(domain);
        } catch (Exception ex) {
            log.error("Failed to remove the domain : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the domain : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method gets the drop box on a domain.
     *
     * @return The string containing the drop box on a domain.
     * @param domain The domain set the drop box on.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getDomainDropBox(String domain) throws EmailException,
            RemoteException {
        try {
            return ServerConfig.getInstance().getDomain().
                    getDomainDropBox(domain);
        } catch (Exception ex) {
            log.error("Failed to get the drop box : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to get the drop box : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the drop box on a domain.
     *
     * @param domain The domain set the drop box on.
     * @param drop The drop box to send mail to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void setDomainDropBox(String domain, String dropBox) throws
            EmailException {
        try {
            ServerConfig.getInstance().getDomain().
                    setDomainDropBox(domain, dropBox);
        } catch (Exception ex) {
            log.error("Failed to set the drop box : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to set the drop box : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the default drop box from a domain. This will result
     * in mail bouncing if it is not correctly addressed.
     *
     * @param domain The domain to remove the drop box from.
     * @exception EmailException
     * @excetpion RemoteException
     */
    public void removeDomainDropBox(String domain) throws
            EmailException {
        try {
            ServerConfig.getInstance().getDomain().removeDomainDropBox(domain);
        } catch (Exception ex) {
            log.error("Failed to remove the drop box : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the drop box : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a new email box on a domain for the user. The user
     * name for authentication purposes will be user@domain.
     *
     * @param domain The domain to create.
     * @param user The username to create.
     * @param password The password to assign to the user.
     * @param quotaSize The quota size of the mail box.
     * @exception EmailException
     * @exception RemoteException
     */
    public void createMailBox(String domain, String user, String password, 
            long quotaSize) throws EmailException {
        try {
            ServerConfig.getInstance().getEmailbox().addMailbox(domain,
                    user, password, quotaSize);
        } catch (Exception ex) {
            log.error("Failed to create the email box : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the drop box : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the password on the mailbox.
     *
     * @param domain The domain the box is attached to.
     * @param user The username to set the password for.
     * @param password The password to set for the user.
     * @exception EmailException
     * @exception RemoteException
     */
    public void setMailBoxPassword(String domain, String user, String password)
    throws EmailException {
        try {
            ServerConfig.getInstance().getEmailbox().setMailboxPassword(domain,
                    user, password);
        } catch (Exception ex) {
            log.error("Failed to set the password for the mail box : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to set the password for the mail box : "
                    + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns the mailbox quota.
     *
     * @return The size of the mailbox.
     * @param domain The domain the box is attached to.
     * @param user The username to set the password for.
     * @exception EmailException
     */
    public long getMailBoxQuota(String domain, String user)
    throws EmailException {
        try {
            return ServerConfig.getInstance().getEmailbox().getMailboxQuota(
                    domain,user);
        } catch (Exception ex) {
            log.error("Failed to retrieve the emailbox quota : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to retrieve the email box quota : "
                    + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method sets the mailbox quota.
     *
     * @param domain The domain the box is attached to.
     * @param user The username to set the password for.
     * @param int The size of the mailbox quota.
     * @exception EmailException
     * @exception RemoteException
     */
    public void setMailBoxQuota(String domain, String user, long size)
    throws EmailException {
        try {
            ServerConfig.getInstance().getEmailbox().setMailboxQuota(
                    domain,user,size);
        } catch (Exception ex) {
            log.error("Failed to set the emailbox quota : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to set the email box quota : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes a mailbox from a domain.
     *
     * @param domain The domain to remove the mailbox from. 
     * @param user The user to remove the mailbox for.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeMailBox(String domain, String user)
    throws EmailException {
        try {
            ServerConfig.getInstance().getEmailbox().removeMailbox(
                    domain,user);
        } catch (Exception ex) {
            log.error("Failed to remove the emailbox quota : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the email box quota : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the email boxes for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    public List listMailBoxes(String domain) throws EmailException {
        try {
            return ServerConfig.getInstance().getEmailbox().listBoxes(domain);
        } catch (Exception ex) {
            log.error("Failed to list the email boxes : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to list the email boxes : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a forward to a target from the alias on the domain
     * specified.
     *
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @param target The target to deliver the messages to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void createForward(String domain, String alias, String[] target)
    throws EmailException {
        try {
            ServerConfig.getInstance().getForward().createForward(domain,alias,
                    target);
        } catch (Exception ex) {
            log.error("Failed to create the forward : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to create the forward : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates a forward to a target from the alias on the domain
     * specified.
     *
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @param target The target to deliver the messages to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void updateForward(String domain, String alias, String[] target)
    throws EmailException {
        try {
            ServerConfig.getInstance().getForward().updateForward(domain,alias,
                    target);
        } catch (Exception ex) {
            log.error("Failed to update the forward : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to update the forward : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the forward information for a given alias.
     *
     * @return The forwards for the alias
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @exception EmailException
     * @exception RemoteException
     */
    public List getForward(String domain, String alias)
    throws EmailException {
        try {
            return ServerConfig.getInstance().getForward().getForward(
                    domain,alias);
        } catch (Exception ex) {
            log.error("Failed to get the forward list : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to get the forward list : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the forward from the given address.
     *
     * @param domain The domain to remove the alias from.
     * @param alias The alias to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeForward(String domain, String alias)
    throws EmailException {
        try {
            ServerConfig.getInstance().getForward().removeForward(
                    domain,alias);
        } catch (Exception ex) {
            log.error("Failed to remove the forward : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the forward : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the forwards for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    public List listForwards(String domain) throws EmailException {
        try {
            return ServerConfig.getInstance().getForward().listForwards(
                    domain);
        } catch (Exception ex) {
            log.error("Failed to list the forwards : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to list the forwards : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates an alias to a users mail box.
     *
     * @param domain The domain to create the alias on.
     * @param user The user to create the alias for.
     * @param alias The alias to provide.
     * @exception EmailException
     * @exception RemoteException
     */
    public void createAlias(String domain, String user, String alias)
    throws EmailException {
        try {
            ServerConfig.getInstance().getAlias().createAlias(
                    domain,user,alias);
        } catch (Exception ex) {
            log.error("Failed to create the alias : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to create the alias : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the alias from the given address.
     *
     * @param domain The domain to remove the alias from.
     * @param user The name of the user to remove the alias from.
     * @param alias The alias to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeAlias(String domain, String user, String alias)
    throws EmailException {
        try {
            ServerConfig.getInstance().getAlias().removeAlias(
                    domain,user,alias);
        } catch (Exception ex) {
            log.error("Failed to remove the alias : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the alias : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the aliases for the given  for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @param user The user to which the aliases are attached.
     * @exception EmailException
     * @exception RemoteException
     */
    public List listAliases(String domain, String user) throws EmailException {
        try {
            return ServerConfig.getInstance().getAlias().listAliases(
                    domain,user);
        } catch (Exception ex) {
            log.error("Failed to list the alias : " 
                    + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to list the alias : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a relay for the given address.
     *
     * @param address The address to add the relay for.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addRelay(String address) throws EmailException {
        // this code instantiates a new transaction because standard MX Bean
        // clients such as jconsole do not support JTA container transactions.
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            ServerConfig.getInstance().getRelay().addRelay(address);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to add the relay : " + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to add the relay : " + ex.getMessage(),ex);
        } finally { 
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is responsible for removing the specified relay.
     *
     * @param address The address to remove from the relay.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeRelay(String address) throws EmailException {
        // this code instantiates a new transaction because standard MX Bean
        // clients such as jconsole do not support JTA container transactions.
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            ServerConfig.getInstance().getRelay().removeRelay(address);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to remove the relay : " + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to remove the relay : " + ex.getMessage(),ex);
        } finally { 
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method lists the replays.
     *
     * @return The list of replays.
     * @exception EmailException
     * @exception RemoteException
     */
    public List listRelays() throws EmailException {
        // this code instantiates a new transaction because standard MX Bean
        // clients such as jconsole do not support JTA container transactions.
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            List relays = 
                    ServerConfig.getInstance().getRelay().getRelays();
            transaction.commit();
            return relays;
        } catch (Exception ex) {
            log.error("Failed to list the relays : " + ex.getMessage(),ex);
            throw new EmailException(
                    "Failed to list the relays : " + ex.getMessage(),ex);
        } finally { 
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is called to process
     */
    public void process() {
        
        // start the dns server
        try {
            Server.getInstance().initialize();
        } catch (Exception ex) {
            log.error("Failed to initialie the Email Server : " + 
                    ex.getMessage(),ex);
            return;
        }
        log.info("DNS Server Processing Requests");
        // wait until the thread is terminate
        while(!state.isTerminated()) {
            state.monitor();
        }
        
        // do anything else that might be required
    }
    
    
    /**
     * This method terminates the processing of the dns server.
     */
    public void terminate() {
        // shut down the
        try {
            Server.getInstance().terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the server : " + 
                    ex.getMessage(),ex);
        }
        state.terminate(true);    
    }
    
    
}
