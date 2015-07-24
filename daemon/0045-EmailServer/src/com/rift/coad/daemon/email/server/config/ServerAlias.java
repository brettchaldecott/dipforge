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
 * ServerAlias.java
 */


// package path
package com.rift.coad.daemon.email.server.config;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.net.InetAddress;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;

// email imports
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.types.Domain;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.db.*;


/**
 * This object is responsible for managing server aliases.
 *
 * @author brett chaldecott
 */
public class ServerAlias {
    
    // private member variables
    private Logger log = Logger.getLogger(ServerAlias.class);
    
    
    /**
     * Creates a new instance of ServerAlias
     */
    public ServerAlias() {
    }
    
    
    /**
     * This method creates an alias to a users mail box.
     *
     * @param domain The domain to create the alias on.
     * @param user The user to create the alias for.
     * @param alias The alias to provide.
     * @exception ConfigException
     */
    public void createAlias(String domain, String username,String alias)
    throws ConfigException {
        try {
            Domain.validate(domain);
            String emailAddress = username + "@" + domain;
            String emailAliasString = alias + "@" + domain;
            Address.validate(emailAddress);
            Address.validate(emailAliasString);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // check for a class on the address
            ServerConfigValidator.checkForAddressClash(emailAliasString, session);
            
            
            // retrieve the domain id
            List list = session.createQuery(
                    "FROM Emailbox WHERE address = ?").
                    setString(0,emailAddress).list();
            if (list.size() != 1) {
                log.info("The address [" + emailAddress + "] does not exist");
                throw new ConfigException("The address [" + emailAddress + 
                        "] does not exist");
            }
            Emailbox emailbox = (Emailbox)list.get(0);
            
            
            // add the email box
            EmailAlias emailAlias = new EmailAlias(emailbox, emailAliasString);
            session.persist(emailAlias);
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the alias : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to persist the alias : " + ex.getMessage(),ex);
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
    public void removeAlias(String domain,String user, String alias)
    throws ConfigException {
        try {
            Domain.validate(domain);
            String emailAlias = alias + "@" + domain;
            Address.validate(emailAlias);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // check for clash on alias tables
            session.createSQLQuery(
                    "DELETE FROM EmailAlias WHERE address = ?").
                    setString(0,emailAlias).executeUpdate();
            
        } catch (Exception ex) {
            log.error("Failed to remove the alias : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to remove the alias : " + ex.getMessage(),ex);
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
    public List listAliases(String domain,String user)
            throws ConfigException {
        try {
            Domain.validate(domain);
            String emailbox = user + "@" + domain;
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List result = session.createQuery(
                    "SELECT alias.address FROM" +
                    " Emailbox as box, " +
                    " EmailAlias as alias " +
                    "WHERE" +
                    " alias.emailbox.id = box.id " +
                    "AND" +
                    " box.address = ?" +
                    " ORDER BY alias.address")
                    .setString(0,emailbox)
                    .list();
            List copy = new ArrayList();
            copy.addAll(result);
            return copy;
        } catch (Exception ex) {
            log.error("Failed to get the list of mail boxes : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the list of mail boxes : " +
                    ex.getMessage(),ex);
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
    public boolean hasAlias(Address address)
            throws ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List result = session.createQuery(
                    "SELECT alias.address FROM" +
                    " Emailbox as box, " +
                    " EmailAlias as alias " +
                    "WHERE" +
                    " alias.emailbox.id = box.id " +
                    "AND" +
                    " box.address = ?" +
                    " ORDER BY alias.address")
                    .setString(0,address.toString())
                    .list();
            return (result.size() > 0);
        } catch (Exception ex) {
            log.error("Failed to check for a alias : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to check for a alias : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the email box associated with an address or null.
     *
     * @return The address associated with the alias or null.
     * @param address The address to perform the lookup for.
     */
    public String getEmailbox(String address) throws ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "SELECT box.address FROM" +
                    " Emailbox as box, " +
                    " EmailAlias as alias " +
                    "WHERE" +
                    " alias.emailbox.id = box.id " +
                    "AND" +
                    " alias.address = ?" +
                    " ORDER BY address")
                    .setString(0,address)
                    .list();
            if (list.size() != 1) {
                return null;
            }
            return (String)list.get(0);
        } catch (Exception ex) {
            log.error("Failed to get the list of mail boxes : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the list of mail boxes : " +
                    ex.getMessage(),ex);
        }
    }
    
}
