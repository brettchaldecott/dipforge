/*
 * EMail: The email server
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
 * EMailServer.java
 */

// the package path
package com.rift.coad.daemon.email.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The email server bean interface.
 *
 * @author brett chaldecott
 */
public interface EMailServer extends Remote {
    /**
     * This method returns the version of the dns server.
     *
     * @return The string contain the version information.
     * @exception RemoteException
     */
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name ofe the dns server.
     *
     * @return The string containing the name of the dns server.
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
     * This method returns the string containing the status of the dns server.
     *
     * @return The string containing the status of the dns server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getStatus() throws EmailException, RemoteException;
    
    
    /**
     * This method lists the domains managed by the email server.
     *
     * @return The list of domains managed by this server.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listDomains() throws EmailException, RemoteException;
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, but does not supply a default drop box for it. If no box is found
     * for this domain the mail will be bounced.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addDomain(String domain) throws EmailException, RemoteException;
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, and supplies the default drop box for a domain.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addDomain(String domain, String dropBox) throws EmailException,
            RemoteException;
    
    
    /**
     * This method removes a domain so that it will no longer be seen as local
     * delivery for it.
     *
     * @param domain The domain to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeDomain(String domain) throws EmailException,
            RemoteException;
    
    
    /**
     * This method gets the drop box on a domain.
     *
     * @return The string containing the drop box on a domain.
     * @param domain The domain set the drop box on.
     * @exception EmailException
     * @exception RemoteException
     */
    public String getDomainDropBox(String domain) throws EmailException,
            RemoteException;
    
    
    /**
     * This method sets the drop box on a domain.
     *
     * @param domain The domain set the drop box on.
     * @param drop The drop box to send mail to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void setDomainDropBox(String domain, String dropBox) throws
            EmailException, RemoteException;
    
    
    /**
     * This method removes the default drop box from a domain. This will result
     * in mail bouncing if it is not correctly addressed.
     *
     * @param domain The domain to remove the drop box from.
     * @exception EmailException
     * @excetpion RemoteException
     */
    public void removeDomainDropBox(String domain) throws
            EmailException, RemoteException;
    
    
    /**
     * This method creates a new email box on a domain for the user. The user
     * name for authentication purposes will be user@domain.
     *
     * @param domain The domain to create.
     * @param user The username to create.
     * @param password The password to assign to the user.
     * @param quotaSize The size of the mail box.
     * @exception EmailException
     * @exception RemoteException
     */
    public void createMailBox(String domain, String user, String password, 
            long quotaSize) throws EmailException, RemoteException;
    
    
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
    throws EmailException, RemoteException;
    
    
    /**
     * This method returns the mailbox quota.
     *
     * @return The size of the mailbox.
     * @param domain The domain the box is attached to.
     * @param user The username to set the password for.
     * @exception EmailException
     * @exception RemoteException
     */
    public long getMailBoxQuota(String domain, String user)
    throws EmailException, RemoteException;
    
    
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
    throws EmailException, RemoteException;
    
    
    
    /**
     * This method removes a mailbox from a domain.
     *
     * @param domain The domain to remove the mailbox from. 
     * @param user The user to remove the mailbox for.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeMailBox(String domain, String user)
    throws EmailException, RemoteException;
    
    
    /**
     * This method lists the email boxes for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listMailBoxes(String domain) throws EmailException,
            RemoteException;
    
    
    /**
     * This method creates a forward to a target from the alias on the domain
     * specified.
     *
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @param targets The targets to deliver the messages to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void createForward(String domain, String alias, String[] targets)
    throws EmailException, RemoteException;
    
    
    /**
     * This method updates a forward to a target from the alias on the domain
     * specified.
     *
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @param target The targets to deliver the messages to.
     * @exception EmailException
     * @exception RemoteException
     */
    public void updateForward(String domain, String alias, String[] targets)
    throws EmailException, RemoteException;
    
    
    /**
     * This method returns the forward information for a given alias.
     *
     * @return The list of targets
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] getForward(String domain, String alias)
    throws EmailException, RemoteException;
    
    
    /**
     * This method removes the forward from the given address.
     *
     * @param domain The domain to remove the alias from.
     * @param alias The alias to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeForward(String domain, String alias)
    throws EmailException, RemoteException;
    
    
    /**
     * This method lists the forwards for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listForwards(String domain) throws EmailException,
            RemoteException;
    
    
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
    throws EmailException, RemoteException;
    
    
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
    throws EmailException, RemoteException;
    
    
    /**
     * This method lists the aliases for the given  for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @param user The user to which the aliases are attached.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listAliases(String domain, String user) throws EmailException,
            RemoteException;
    
    
    /**
     * This method adds a relay for the given address.
     *
     * @param address The address to add the relay for.
     * @exception EmailException
     * @exception RemoteException
     */
    public void addRelay(String address) throws EmailException,
            RemoteException;
    
    
    /**
     * This method is responsible for removing the specified relay.
     *
     * @param address The address to remove from the relay.
     * @exception EmailException
     * @exception RemoteException
     */
    public void removeRelay(String address) throws EmailException,
            RemoteException;
    
    
    /**
     * This method lists the replays.
     *
     * @return The list of replays.
     * @exception EmailException
     * @exception RemoteException
     */
    public String[] listRelays() throws EmailException, RemoteException;
}
