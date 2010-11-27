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
 * ServerEmailbox.java
 */

// package path
package com.rift.coad.daemon.email.server.config;

// java imports
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.net.InetAddress;
import java.security.MessageDigest;
import javax.mail.URLName;

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
 * This object is responsible for managing the addresses.
 *
 * @author brett
 */
public class ServerEmailbox {
    
    // private member variables
    private Logger log = Logger.getLogger(ServerEmailbox.class);
    
    
    
    /**
     * Creates a new instance of ServerAddress
     */
    public ServerEmailbox() {
    }
    
    
    /**
     * This method adds a new address to the list of addresses.
     *
     * @param address The address to add.
     * @param password The password to add.
     * @exception ConfigException
     */
    public List listBoxes(String domain) throws
            ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List result = session.createQuery(
                    "SELECT box.address FROM" +
                    " Emailbox as box, " +
                    " EmailDomain as domain " +
                    "WHERE" +
                    " box.domain.id = domain.id " +
                    "AND" +
                    " domain.domain = ?" +
                    " ORDER BY address")
                    .setString(0,domain)
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
     * This method adds a new address to the list of addresses.
     *
     * @param domain The domain for the quota.
     * @param username The username.
     * @param password The password to add.
     * @param quotaSize The size of the quota.
     * @exception ConfigException
     */
    public void addMailbox(String domain, String username, String password,
            long quotaSize) throws ConfigException {
        try {
            Domain.validate(domain);
            String address = username + "@" + domain;
            Address.validate(address);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // check for a class on the address
            ServerConfigValidator.checkForAddressClash(address, session);
            
            // retrieve the domain id
            List list = session.createQuery(
                    "FROM EmailDomain WHERE domain = ?").
                    setString(0,domain).list();
            if (list.size() != 1) {
                log.info("The domain [" + domain + "] does not exist");
                throw new ConfigException("The domain [" + domain
                        + "] does not exist");
            }
            EmailDomain emailDomain = (EmailDomain)list.get(0);
            
            
            // add the email box
            Emailbox emailbox = new Emailbox(address, hashPassword(password),
                    emailDomain,stripAddress(address), quotaSize);
            session.persist(emailbox);
            
            // setup the path
            File path = new File(ServerConfig.getInstance().getMailDir() + 
                    File.separator + emailbox.getPath());
            URLName url = new URLName(path.toURL().toString().replace("file:","maildir:"));
            
            // write the message to the mail store
            java.util.Properties p = new java.util.Properties();
            p.put("mail.store.maildir.autocreatedir", "true");
            javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(p);
            net.ukrpost.storage.maildir.MaildirStore store =
                    (net.ukrpost.storage.maildir.MaildirStore)
                    mailSession.getStore(url);
            javax.mail.Folder inbox = store.getFolder("INBOX");
            inbox.open(javax.mail.Folder.READ_WRITE);
            inbox.close(false);
            store.close();
            
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the mail box : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to persist the mail box : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the mailbox password.
     *
     * @param domain The domain for the quota.
     * @param username The username.
     * @param password The password to add.
     * @exception ConfigException
     */
    public void setMailboxPassword(String domain, String username,
            String password) throws ConfigException {
        try {
            Domain.validate(domain);
            String address = username + "@" + domain;
            Address.validate(address);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "FROM Emailbox as box WHERE box.address = ?").
                    setString(0,address).list();
            if (list.size() != 1) {
                log.error("The email box [" + address + "] does not exist");
                throw new ConfigException("The email box [" + address
                        + "] does not exist");
            }
            Emailbox box = (Emailbox)list.get(0);
            box.setPassword(hashPassword(password));
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to set the mail box password : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to set the mail box password : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the mail box quota.
     *
     * @return The long containing the mailbox quota.
     * @param domain The domain the mailbox is attached to.
     * @param username The username for the mailbox.
     * @exception ConfigException
     */
    public long getMailboxQuota(String domain, String username) throws
            ConfigException {
        try {
            Domain.validate(domain);
            String address = username + "@" + domain;
            Address.validate(address);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "FROM Emailbox as box WHERE box.address = ?").
                    setString(0,address).list();
            if (list.size() != 1) {
                log.error("The email box [" + address + "] does not exist");
                throw new ConfigException("The email box [" + address
                        + "] does not exist");
            }
            Emailbox box = (Emailbox)list.get(0);
            return box.getQuota();
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the mailbox quota.
     *
     * @return The long containing the mailbox quota.
     * @param domain The domain the mailbox is attached to.
     * @param username The username for the mailbox.
     * @exception ConfigException
     */
    public void setMailboxQuota(String domain, String username,
            long quotaSize) throws ConfigException {
        try {
            Domain.validate(domain);
            String address = username + "@" + domain;
            Address.validate(address);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "FROM Emailbox as box WHERE box.address = ?").
                    setString(0,address).list();
            if (list.size() != 1) {
                log.error("The email box [" + address + "] does not exist");
                throw new ConfigException("The email box [" + address
                        + "] does not exist");
            }
            Emailbox box = (Emailbox)list.get(0);
            box.setQuota(quotaSize);
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the mailbox account.
     *
     * @param domain The domain the user is attached to.
     * @param username The username to remove.
     */
    public void removeMailbox(String domain, String username) throws
            ConfigException {
        try {
            Domain.validate(domain);
            String address = username + "@" + domain;
            Address.validate(address);
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            session.createSQLQuery(
                    "DELETE FROM Emailbox WHERE address = ?").
                    setString(0,address).executeUpdate();
        } catch (Exception ex) {
            log.error("Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get the mail box quota : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the email box or null.
     *
     * @return The object containing the email box information.
     * @param address The address to return.
     * @exception ConfigException
     */
    public Emailbox getEmail(String address) throws ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "FROM Emailbox as box WHERE box.address = ?").
                    setString(0,address).list();
            if (list.size() != 1) {
                return null;
            }
            return (Emailbox)list.get(0);
        } catch (Exception ex) {
            log.error("Failed to retrieve the email : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to retrieve the email : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns true if the email address exists.
     *
     * @return TRUE if the method exists, FALSE if not.
     * @param address The address to return.
     * @exception ConfigException
     */
    public boolean hasEmail(Address address) throws ConfigException {
        try {
            Session session =
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List list = session.createQuery(
                    "FROM Emailbox as box WHERE box.address = ?").
                    setString(0,address.toString()).list();
            return (list.size() > 0);
        } catch (Exception ex) {
            log.error("Failed to check for an email address : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to check for an email address : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method validates the password againts the email box.
     *
     * @return TRUE if a valid password, FALSE if not.
     * @param password The password to perform the check for.
     * @param box The box to retrieve.
     * @exception ConfigException
     */
    public boolean validatePassword(String password, Emailbox box) throws
            ConfigException {
        return box.getPassword().equals(hashPassword(password));
    }
    
    
    /**
     * This method is responsible for generating a hash of the password.
     *
     * @return The string containing a hash of the password.
     * @param password The password to be hashed.
     */
    private String hashPassword(String password) throws ConfigException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            //Convert the byte array into a String
            byte[] array = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception ex) {
            log.error("Failed to generate a hash of the password : " +
                    ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to generate a hash of the password : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method strips the email address so that it can be used as the path
     * for the user account.
     *
     * @return The stipped address
     * @param address The address to strip.
     */
    private String stripAddress(String address) {
        StringBuffer result = new StringBuffer();
        for (int index = 0; index < address.length(); index++) {
            char entry = address.charAt(index);
            if (Character.isLetterOrDigit(entry)) {
                result.append(entry);
            }
        }
        return result.toString();
    }
}
