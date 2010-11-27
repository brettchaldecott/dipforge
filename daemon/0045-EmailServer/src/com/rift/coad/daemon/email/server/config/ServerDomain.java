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
 * ServerDomain.java
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
 * This object is responsible for managing the server domain information for the
 * email server.
 *
 * @author brett chaldecott
 */
public class ServerDomain {
    
    // private member variables
    private Logger log = Logger.getLogger(ServerDomain.class);
    
    
    /**
     * Creates a new instance of ServerDomain
     */
    public ServerDomain() {
        
    }
    
    
    /**
     * This method lists the domains managed by the email server.
     *
     * @return The list of domains managed by this server.
     * @exception EmailException
     */
    public List listDomains() throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List result = session.createSQLQuery(
                    "SELECT domain FROM EmailDomain ORDER BY domain").list();
            List copy = new ArrayList();
            copy.addAll(result);
            return copy;
        } catch (Exception ex) {
            log.error("Failed to get the list of domains : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the list of domains : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, but does not supply a default drop box for it. If no box is found
     * for this domain the mail will be bounced.
     *
     * @param domain The domain to add.
     * @exception ConfigException
     */
    public void addDomain(String domain) throws ConfigException {
        try {
            Domain.validate(domain);
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            EmailDomain emailDomain = new EmailDomain(domain,null);
            session.persist(emailDomain);
        } catch (Exception ex) {
            log.error("Failed to persist the domain : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to persist the domain : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a domain to the email server, so that it knows it as
     * local, and supplies the default drop box for a domain.
     *
     * @param domain The domain to add.
     * @exception ConfigException
     */
    public void addDomain(String domain, String dropBox) throws 
            ConfigException {
        try {
            Domain.validate(domain);
            Address.validate(dropBox);
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            EmailDomain emailDomain = new EmailDomain(domain,dropBox);
            session.persist(emailDomain);
        } catch (Exception ex) {
            log.error("Failed to persist the domain : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to persist the domain : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes a domain so that it will no longer be seen as local
     * delivery for it.
     *
     * @param domain The domain to remove.
     * @exception EmailException
     * @exception ConfigException
     */
    public void removeDomain(String domain) throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            session.createSQLQuery(
                    "DELETE FROM EmailDomain WHERE domain = ?").
                    setString(0,domain).executeUpdate();
        } catch (Exception ex) {
            log.error("Failed to delete the domain : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to delete the domain : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the drop box on a domain.
     *
     * @param domain The domain set the drop box on.
     * @param drop The drop box to send mail to.
     * @exception ConfigException
     */
    public String getDomainDropBox(String domain) throws
            ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createSQLQuery(
                    "SELECT dropBox FROM EmailDomain WHERE domain = ?")
                    .setString(0,domain).list();
            if (list.size() != 1) {
                throw new ConfigException("The is no domain [" + domain + "]");
            }
            return (String)list.get(0);
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to set the drop box for the domain : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to set the drop box for the domain : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the drop box on a domain.
     *
     * @param domain The domain set the drop box on.
     * @param drop The drop box to send mail to.
     * @exception ConfigException
     */
    public void setDomainDropBox(String domain, String dropBox) throws
            ConfigException {
        try {
            Address.validate(dropBox);
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            session.createSQLQuery(
                    "UPDATE EmailDomain SET dropBox = ? WHERE domain = ?").
                    setString(0,dropBox).setString(1,domain).executeUpdate();
        } catch (Exception ex) {
            log.error("Failed to set the drop box for the domain : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to set the drop box for the domain : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the default drop box from a domain. This will result
     * in mail bouncing if it is not correctly addressed.
     *
     * @param domain The domain to remove the drop box from.
     * @exception ConfigException
     */
    public void removeDomainDropBox(String domain) throws
            ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            session.createSQLQuery(
                    "UPDATE EmailDomain SET dropBox = NULL WHERE domain = ?").
                    setString(0,domain).executeUpdate();
        } catch (Exception ex) {
            log.error("Failed to remove the drop box from the domain : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to remove the drop box from the domain : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method checks if the domain is local and if so returns true.
     *
     * @return TRUE if the domain is local.
     * @param domain The domain to perform the check on.
     * @exception ConfigException
     */
    public boolean checkForLocalDomain(String domain) throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createSQLQuery(
                    "SELECT domain FROM EmailDomain WHERE domain = ?").
                    setString(0,domain).list();
            if (list.size() == 1) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("Failed to remove the drop box from the domain : " + 
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to remove the drop box from the domain : " + 
                    ex.getMessage(),ex);
        }
    }
}
