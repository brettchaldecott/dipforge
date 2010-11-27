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
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.db.*;

/**
 * This object managers the server relay information.
 *
 * @author brett chaldecott
 */
public class ServerRelay  {
    
    // class constants
    private final static String LOOP_BACK = "127.0.0.1";
    
    // private member variables
    private Logger log = Logger.getLogger(ServerRelay.class);
    
    
    
    /** 
     * Creates a new instance of ServerRelay
     */
    public ServerRelay() {
    }
    
    
    /**
     * This method returns the list of relays for this server.
     *
     * @return The list of relays.
     * @exception ConfigException
     */
    public List getRelays() throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            List result = session.createSQLQuery(
                    "SELECT address FROM EmailRelay").list();
            List copy = new ArrayList();
            copy.addAll(result);
            return copy;
        } catch (Exception ex) {
            log.error("Failed to get relays : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to get relays : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method add adds a new address.
     *
     * @param address The address to add.
     * @throws ConfigException
     */
    public void addRelay(String address) throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            Relay check = (Relay)session.get(Relay.class,address);
            if (check != null) {
                log.info("Attempting to add duplicate relay address [" + address 
                        + "]");
                throw new ConfigException("The relay [" + address 
                        + "] already exists");
            }
            Relay relay = new Relay(address);
            session.persist(relay);
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the relay : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to add the relay : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for removing a relay.
     *
     * @param address The address to add the relay to.
     * @throws ConfigException
     */
    public void removeRelay(String address) throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            Relay relay = (Relay)session.get(Relay.class,address);
            if (relay == null) {
                log.info("The relay [" + address 
                        + "] does not exist. Cannot be removed.");
                throw new ConfigException("The relay [" + address 
                        + "] does not exist. Cannot be removed.");
            }
            session.delete(relay);
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the relay : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to remove the relay : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to check if a relay is allowed for the specific 
     * address
     */
    public boolean checkRelayAllow(InetAddress address) throws ConfigException {
        try {
            Session session = 
                    HibernateUtil.getInstance(ServerConfig.class).getSession();
            
            // check for relay
            String ipAddress = address.getHostAddress();
            if ((ipAddress == InetAddress.getLocalHost().getHostAddress()) ||
                    ipAddress == LOOP_BACK) {
                return true;
            }
            
            // check the db for relay information based on ip address
            log.debug("Check the relay");
            while (true) {
                Relay relay = (Relay)session.get(Relay.class,ipAddress);
                if (relay != null) {
                    return true;
                }
                int pos = ipAddress.lastIndexOf(".");
                if (pos == -1) {
                    break;
                } else {
                    ipAddress = ipAddress.substring(0,pos);
                }
            }
            
            
            log.debug("Retrieve the remote host");
            String remoteHost = address.getHostName();
            log.debug("Walk the host name : " + remoteHost);
            while(true) {
                Relay relay = (Relay)session.get(Relay.class,remoteHost);
                if (relay != null) {
                    return true;
                }
                int pos = remoteHost.indexOf(".");
                if (pos == -1) {
                    break;
                } else {
                    remoteHost = remoteHost.substring(pos + 1,remoteHost.length());
                }
            }
            
            // there is no matching relay host
            log.debug("Finished");
            return false;
        } catch (Exception ex) {
            log.error("Failed to check for relay allowed : " + ex.getMessage(),
                    ex);
            throw new ConfigException(
                    "Failed to check for relay allowed : " + ex.getMessage(),
                    ex);
        }
    }
}
