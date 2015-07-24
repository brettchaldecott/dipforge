/*
 * EmailServer: The email server implementation.
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
 * ImapServer.java
 */

// package path
package com.rift.coad.daemon.email.server;

// java imports
import java.io.File;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// email server imports
import com.rift.coad.daemon.email.EMailServerConstants;
import com.rift.coad.daemon.email.server.config.ServerRelay;
import com.rift.coad.daemon.email.server.config.ServerDomain;
import com.rift.coad.daemon.email.server.config.ServerEmailbox;
import com.rift.coad.daemon.email.server.config.ServerAlias;
import com.rift.coad.daemon.email.server.config.ServerForward;
import com.rift.coad.daemon.email.server.db.*;

/**
 * This object contains the server configuration information.
 *
 * @author brett chaldecott
 */
public class ServerConfig {
    
    // class constants
    private final static String SERVER_BASE = "email_server_base";
    
    // class singleton.
    private static Logger log = Logger.getLogger(ServerConfig.class);
    private static ServerConfig singleton = null;
    
    // class member variables
    private ServerRelay relay = new ServerRelay();
    private ServerDomain domain = new ServerDomain();
    private ServerEmailbox emailbox = new ServerEmailbox();
    private ServerAlias alias = new ServerAlias();
    private ServerForward forward = new ServerForward();
    private String maildirBase = null;
    
    /**
     * Creates a new instance of ServerConfig
     */
    private ServerConfig() throws ServerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ServerConfig.class);
            maildirBase = config.getString(SERVER_BASE) + File.separator + 
                    EMailServerConstants.MAIL_DIR;
    
            HibernateUtil.getInstance(ServerConfig.class);
        } catch (Exception ex) {
            log.error("Failed to instanciate the server configuration : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to instanciate the server configuration : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the reference to the server configuration object.
     *
     * @return The reference to the server config.
     * @exception ServerException
     */
    public synchronized static ServerConfig getInstance() throws 
            ServerException {
        if (singleton == null) {
            singleton = new ServerConfig();
        }
        return singleton;
    }
    
    
    /**
     * This method checks if the relay is allowed for the given address
     *
     * @return A reference to the server relay object.
     */
    public ServerRelay getRelay() throws ServerException {
        
        return relay;
    }
    
    
    /**
     * This method returns the domain configuration object.
     *
     * @return A reference to the domain configuration object.
     */
    public ServerDomain getDomain() throws ServerException {
        
        return domain;
    }
    
    
    /**
     * This method returns the email box management object.
     *
     * @return A reference to the email box configuration object.
     */
    public ServerEmailbox getEmailbox() throws ServerException {
        
        return emailbox;
    }
    
    
    /**
     * This method returns the alias management object.
     *
     * @return A reference to the alias configuration object.
     */
    public ServerAlias getAlias() throws ServerException {
        
        return alias;
    }
    
    
    /**
     * This method returns the server forwards management object.
     *
     * @return A reference to the server forwards object.
     */
    public ServerForward getForward() throws ServerException {
        
        return forward;
    }
    
    
    /**
     * This method returns a new Hibernate session object. Using the 
     * configuration for this server to set it up.
     *
     * @return A new session object.
     * @exception ServerException
     */
    public Session getSession() throws ServerException {
        try {
            return HibernateUtil.getInstance(ServerConfig.class).getSession();
        } catch (Exception ex) {
            log.error("Failed to retrieve a session : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve a session : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the mail dir for the server.
     *
     * @return The string containing the mail dir.
     */
    public String getMailDir() {
        return maildirBase;
    }
}
