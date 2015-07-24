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
 * EMailServerMBean.java
 */

// the package path
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
 * The email server bean interface.
 *
 * @author brett chaldecott
 */
public interface EMailServerMBean extends Remote {
    /**
     * This method returns the version of the email server
     *
     * @return The string contain the version information.
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the version of the email server")
    @Version(number="1.0")
    @Result(description="The string containing the version of the email server")
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of the email server.
     *
     * @return The string containing the name of the dns server.
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the name of the email server")
    @Version(number="1.0")
    @Result(description="The string containing the name of the email server")
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
     * This method returns the string containing the status of the email server.
     *
     * @return The string containing the status of the email server.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Returns the status of the server")
    @Version(number="1.0")
    @Result(description="A string containing the status of the email server")
    public String getStatus() throws EmailException, RemoteException;
    
    
    /**
     * This method lists the domains managed by the email server.
     *
     * @return The list of domains managed by this server.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="List the domains that are managed by this server.")
    @Version(number="1.0")
    @Result(description="The list of domains.")
    public List listDomains() throws EmailException, RemoteException;
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, but does not supply a default drop box for it. If no box is found
     * for this domain the mail will be bounced.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Add a new domain to the server.")
    @Version(number="1.0")
    public void addDomain(@ParamInfo(name="domain",
            description="A string containing the domain to add")String domain) 
            throws EmailException, RemoteException;
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, and supplies the default drop box for a domain.
     *
     * @param domain The domain to add.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Add a new domain to the server.")
    @Version(number="1.0")
    public void addDomain(@ParamInfo(name="domain",
            description="A string containing the domain to add")String domain, 
            @ParamInfo(name="dropBox",
            description="The email drop box for incorrectly addressed mail attached to this domain")
            String dropBox) throws EmailException,
            RemoteException;
    
    
    /**
     * This method removes a domain so that it will no longer be seen as local
     * delivery for it.
     *
     * @param domain The domain to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Removes the specified domain.")
    @Version(number="1.0")
    public void removeDomain(@ParamInfo(name="domain",
            description="A string containing the domain to remove")
            String domain) throws EmailException,
            RemoteException;
    
    
    /**
     * This method gets the drop box on a domain.
     *
     * @return The string containing the drop box on a domain.
     * @param domain The domain set the drop box on.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Get the drop box on a domain.")
    @Version(number="1.0")
    @Result(description="The domain drop box.")
    public String getDomainDropBox(@ParamInfo(name="domain",
            description="A string containing the domain for the drop box")String domain) throws
            EmailException, RemoteException;
    
    
    /**
     * This method sets the drop box on a domain.
     *
     * @param domain The domain set the drop box on.
     * @param drop The drop box to send mail to.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Set the drop box on a domain.")
    @Version(number="1.0")
    public void setDomainDropBox(@ParamInfo(name="domain",
            description="A string containing the domain for the drop box")String domain, 
            @ParamInfo(name="dropBox",
            description="The drop box for the domain")String dropBox) throws
            EmailException, RemoteException;
    
    
    /**
     * This method removes the default drop box from a domain. This will result
     * in mail bouncing if it is not correctly addressed.
     *
     * @param domain The domain to remove the drop box from.
     * @exception EmailException
     * @excetpion RemoteException
     */
    @MethodInfo(description="Remove the drop box from a domain")
    @Version(number="1.0")
    public void removeDomainDropBox(@ParamInfo(name="domain",
            description="A string containing the domain name")String domain) throws
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
    @MethodInfo(description="Create a new mailbox on a given domain for a user")
    @Version(number="1.0")
    public void createMailBox(@ParamInfo(name="domain",
            description="The domain to create the mailbox on.")String domain, 
            @ParamInfo(name="user",
            description="The user to create the mailbox for.")String user,
            @ParamInfo(name="password",
            description="The password value for the mailbox.")String password, 
            @ParamInfo(name="quotaSize",
            description="The size of the quota. (If set to Zero than infinite)")long quotaSize)
            throws EmailException, RemoteException;
    
    
    /**
     * This method sets the password on the mailbox.
     *
     * @param domain The domain the box is attached to.
     * @param user The username to set the password for.
     * @param password The password to set for the user.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Set the password on a mail box.")
    @Version(number="1.0")
    public void setMailBoxPassword(@ParamInfo(name="domain",
            description="The domain the mail box belongs to.")String domain,
            @ParamInfo(name="user",
            description="The user the mail box belongs to.")String user,
            @ParamInfo(name="password",
            description="The password for the account.")String password)
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
    @MethodInfo(description="Retrieve the size of the quota.")
    @Version(number="1.0")
    @Result(description="The size of the quota on a mailbox. (If Zero than infinite)")
    public long getMailBoxQuota(@ParamInfo(name="domain",
            description="The domain the mailbox is attached to.")String domain,
            @ParamInfo(name="user",
            description="The user the mailbox is attached to.")String user)
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
    @MethodInfo(description="Set the quota size for a mailbox.")
    @Version(number="1.0")
    public void setMailBoxQuota(@ParamInfo(name="domain",
            description="The domain the mailbox is attached to.")String domain,
            @ParamInfo(name="user",
            description="The user the mailbox is attached to.")String user,
            @ParamInfo(name="size",
            description="The new size of the quota on a mailbox.")long size)
    throws EmailException, RemoteException;
    
    
    
    /**
     * This method removes a mailbox from a domain.
     *
     * @param domain The domain to remove the mailbox from. 
     * @param user The user to remove the mailbox for.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Remove a mailbox identified by the domain and user.")
    @Version(number="1.0")
    public void removeMailBox(@ParamInfo(name="domain",
            description="The domain the mailbox is attached to.")String domain,
            @ParamInfo(name="user",
            description="The user the mailbox is attached to.")String user)
    throws EmailException, RemoteException;
    
    
    /**
     * This method lists the email boxes for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve the list of mailboxes attached to a domain.")
    @Version(number="1.0")
    @Result(description="The list of mailbox on a domain.")
    public List listMailBoxes(@ParamInfo(name="domain",
            description="The domain to retrieve the mailboxes for.")String domain) 
            throws EmailException, RemoteException;
    
    
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
    @MethodInfo(description="Create a new forward on a domain")
    @Version(number="1.0")
    public void createForward(@ParamInfo(name="domain",
            description="The domain to attach the forward to")String domain, 
            @ParamInfo(name="alias",
            description="The alias to attach the forward to")String alias,
            @ParamInfo(name="target",
            description="The list of target addresses")String[] targets)
    throws EmailException, RemoteException;
    
    
    /**
     * This method updates a forward to a target from the alias on the domain
     * specified.
     *
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @param targets The targets to deliver the messages to.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Create a new forward on a domain")
    @Version(number="1.0")
    public void updateForward(@ParamInfo(name="domain",
            description="The domain to update the forward for")String domain, 
            @ParamInfo(name="alias",
            description="The alias to attach the forward for")String alias,
            @ParamInfo(name="target",
            description="The list of targets")String[] targets)
    throws EmailException, RemoteException;
    
    
    /**
     * This method returns the forward information for a given alias.
     *
     * @return The list of forwards for the given alias.
     * @param domain The domain to create the alias on.
     * @param alias The alias to provide.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve a list of forwards on a domain.")
    @Version(number="1.0")
    @Result(description="The list of forwards on a domain and alias.")
    public List getForward(@ParamInfo(name="domain",
            description="The domain to retrieve the forward for")String domain, 
            @ParamInfo(name="alias",
            description="The alias to retrieve the forward for")String alias)
    throws EmailException, RemoteException;
    
    
    /**
     * This method removes the forward from the given address.
     *
     * @param domain The domain to remove the alias from.
     * @param alias The alias to remove.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Remove a forward from a domain.")
    @Version(number="1.0")
    public void removeForward(@ParamInfo(name="domain",
            description="The domain to remove the forward form")String domain, 
            @ParamInfo(name="alias",
            description="The alias to remove the forward form")String alias)
    throws EmailException, RemoteException;
    
    
    /**
     * This method lists the forwards for the given domain.
     *
     * @return The list of email boxes on a domain.
     * @param domain The domain to retrieve the list for.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve the list of aliases that are forward addresses on a domain.")
    @Version(number="1.0")
    @Result(description="The list forward addresses attached to a domain.")
    public List listForwards(@ParamInfo(name="domain",
            description="The domain to retrieve the forward alias list for")
            String domain) throws EmailException,
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
    @MethodInfo(description="Create a new alias for a user on a domain.")
    @Version(number="1.0")
    public void createAlias(@ParamInfo(name="domain",
            description="The domain to create the alias on.")String domain, 
            @ParamInfo(name="user",
            description="The user to attach the alias to.")String user,
            @ParamInfo(name="alias",
            description="The new alias attached to the domain for the user.")
            String alias)
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
    @MethodInfo(description="Remove an alias from a user on a domain.")
    @Version(number="1.0")
    public void removeAlias(@ParamInfo(name="domain",
            description="The domain to remove the alias from.")String domain, 
            @ParamInfo(name="user",
            description="The user to remove the alias from.")String user, 
            @ParamInfo(name="alias",
            description="The alias to remove from the user.")String alias)
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
    @MethodInfo(description="Retrieve the list of aliases for a user on a domain")
    @Version(number="1.0")
    @Result(description="The list alias on a user at a domain.")
    public List listAliases(@ParamInfo(name="domain",
            description="The domain to retrieve the aliases for.")String domain,
            @ParamInfo(name="user",
            description="The user to retrieve the aliases for.")String user)
            throws EmailException,RemoteException;
    
    
    /**
     * This method adds a relay for the given address.
     *
     * @param address The address to add the relay for.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Add a relay to the email server. This will allow anyone coming from the provided address to send mail")
    @Version(number="1.0")
    public void addRelay(@ParamInfo(name="address",
            description="The address the sender has to come from in order to send mail.")String address)
            throws EmailException, RemoteException;
    
    
    /**
     * This method is responsible for removing the specified relay.
     *
     * @param address The address to remove from the relay.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Remove a relay from the email server.")
    @Version(number="1.0")
    public void removeRelay(@ParamInfo(name="address",
            description="The address to remove as a relay.")String address) throws EmailException,
            RemoteException;
    
    
    /**
     * This method lists the replays.
     *
     * @return The list of replays.
     * @exception EmailException
     * @exception RemoteException
     */
    @MethodInfo(description="Retrieve the list of relays")
    @Version(number="1.0")
    @Result(description="The list of relay addresses that can send mail through an email server instance.")
    public List listRelays() throws EmailException, RemoteException;
    
}
