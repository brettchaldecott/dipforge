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
 * ServerForward.java
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
 * The server forward management object.
 *
 * @author brett chaldecott
 */
public class ServerForward {
    
    // private member variables
    private Logger log = Logger.getLogger(ServerForward.class);
    
    
    /**
     * Creates a new instance of ServerForward
     */
    public ServerForward() {
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
    public void createForward(String domain, String alias, String[] targets)
    throws ConfigException {
        try {
            Domain.validate(domain);
            String forwardString = alias + "@" + domain;
            Address.validate(forwardString);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // check for a class on the address
            ServerConfigValidator.checkForAddressClash(forwardString, session);
            
            // retrieve the email domain
            List list = session.createQuery(
                    "FROM EmailDomain WHERE domain = ?").
                    setString(0,domain).list();
            if (list.size() != 1) {
                log.info("The domain [" + domain + "] does not exist");
                throw new ConfigException("The domain [" + domain
                        + "] does not exist");
            }
            EmailDomain emailDomain = (EmailDomain)list.get(0);
            
            
            // create the forward entry
            EmailForward forward = new EmailForward(emailDomain, forwardString);
            session.persist(forward);
            
            // add the targets
            for (int index = 0; index < targets.length; index++) {
                String targetAddress = targets[index];
                Address.validate(targetAddress);
                EmailForwardEntry entry = new EmailForwardEntry(forward, 
                        targetAddress);
                session.persist(entry);
            }
        
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the forwards : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to create the forwards: " + ex.getMessage(),ex);
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
    public void updateForward(String domain, String alias, String[] targets)
    throws ConfigException {
        try {
            Domain.validate(domain);
            String forwardString = alias + "@" + domain;
            Address.validate(forwardString);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // retrieve the email domain
            List list = session.createQuery(
                    "FROM EmailForward WHERE address = ?").
                    setString(0,forwardString).list();
            if (list.size() != 1) {
                log.info("There is no forward for [" + forwardString 
                        + "] does not exist");
                throw new ConfigException("There is no forward for [" 
                        + forwardString + "] does not exist");
            }
            EmailForward emailForward = (EmailForward)list.get(0);
            
            session.createQuery(
                    "DELETE FROM EmailForwardEntry WHERE emailForwardId = ?").
                    setLong(0,emailForward.getId()).executeUpdate();
            
            
            // add the targets
            for (int index = 0; index < targets.length; index++) {
                String targetAddress = targets[index];
                Address.validate(targetAddress);
                EmailForwardEntry entry = new EmailForwardEntry(emailForward, 
                        targetAddress);
                session.persist(entry);
            }
        
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the forwards : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to update the forwards: " + ex.getMessage(),ex);
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
    throws ConfigException {
        try {
            Domain.validate(domain);
            String forwardString = alias + "@" + domain;
            Address.validate(forwardString);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            List result = session.createQuery(
                    "SELECT entry.address FROM" +
                    " EmailForward as forward, " +
                    " EmailForwardEntry as entry " +
                    "WHERE" +
                    " entry.forward.id = forward.id " +
                    "AND" +
                    " forward.address = ?" +
                    " ORDER BY entry.address")
                    .setString(0,forwardString)
                    .list();
            List copy = new ArrayList();
            copy.addAll(result);
            return copy;
        
        } catch (Exception ex) {
            log.error("Failed to retrieve the forwards : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to retrieve the forwards: " + ex.getMessage(),ex);
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
    throws ConfigException {
        try {
            Domain.validate(domain);
            String forwardString = alias + "@" + domain;
            Address.validate(forwardString);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // retrieve the email domain
            List list = session.createQuery(
                    "FROM EmailForward WHERE address = ?").
                    setString(0,forwardString).list();
            if (list.size() != 1) {
                log.info("There is no forward for [" + forwardString 
                        + "] does not exist");
                throw new ConfigException("There is no forward for [" 
                        + forwardString + "] does not exist");
            }
            EmailForward emailForward = (EmailForward)list.get(0);
            
            session.createQuery(
                    "DELETE FROM EmailForwardEntry WHERE emailForwardId = ?").
                    setLong(0,emailForward.getId()).executeUpdate();
            
            session.delete(emailForward);
            
            
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete the forwards : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to delete the forwards: " + ex.getMessage(),ex);
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
    public List listForwards(String domain) throws ConfigException {
        try {
            Domain.validate(domain);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            List result = session.createQuery(
                    "SELECT forward.address FROM" +
                    " EmailDomain as domain, " +
                    " EmailForward as forward " +
                    "WHERE" +
                    " forward.domain.id = domain.id " +
                    "AND" +
                    " domain.domain = ?" +
                    " ORDER BY forward.address")
                    .setString(0,domain)
                    .list();
            List copy = new ArrayList();
            copy.addAll(result);
            return copy;
        
        } catch (Exception ex) {
            log.error("Failed to retrieve the forwards on a domain : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to retrieve the forwards on a domain : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns true if the forward exists.
     */
    public boolean hasForward(Address address) throws ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            List result = session.createQuery(
                    "SELECT entry.address FROM" +
                    " EmailForward as forward, " +
                    " EmailForwardEntry as entry " +
                    "WHERE" +
                    " entry.forward.id = forward.id " +
                    "AND" +
                    " forward.address = ?" +
                    " ORDER BY entry.address")
                    .setString(0,address.toString())
                    .list();
            return (result.size() > 0);
        } catch (Exception ex) {
            log.error("Failed to retrieve the forwards : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to retrieve the forwards: " + ex.getMessage(),ex);
        }
    }
}
