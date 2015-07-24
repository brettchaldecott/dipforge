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
 * Server.java
 */

// package path
package com.rift.coad.daemon.email.server;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;

// email server imports
import com.rift.coad.daemon.email.server.dns.NSTool;
import com.rift.coad.daemon.email.server.pop.POP3Request;
import com.rift.coad.daemon.email.server.imap.ImapRequest;
import com.rift.coad.daemon.email.server.smtp.SmtpRequest;


/**
 * The server that manages the mail server.
 *
 * @author brett chaldecott
 */
public class Server {
    
    // class constants
    private final static String POOL_USER = "email_pool_user";
    private final static String POOL_SIZE = "email_pool_size";
    private final static long DEFAULT_POOL_SIZE = 10;
    private final static String POP_PORT = "email_pop3_port";
    private final static long DEFAULT_POP_PORT = 110;
    private final static String POP_ADDRESS = "pop_address";
    private final static String DEFAULT_POP_ADDRESS = "0.0.0.0";
    private final static String START_POP = "start_pop";
    private final static boolean DEFAULT_START_POP = true;
    private final static String IMAP_PORT = "email_imap_port";
    private final static long DEFAULT_IMAP_PORT = 143;
    private final static String IMAP_ADDRESS = "imap_address";
    private final static String DEFAULT_IMAP_ADDRESS = "0.0.0.0";
    private final static String START_IMAP = "start_imap";
    private final static boolean DEFAULT_START_IMAP = true;
    private final static String SMTP_PORT = "email_smtp_port";
    private final static long DEFAULT_SMTP_PORT = 25;
    private final static String SMTP_ADDRESS = "smtp_address";
    private final static String DEFAULT_SMTP_ADDRESS = "0.0.0.0";
    private final static String START_SMTP = "start_smtp";
    private final static boolean DEFAULT_START_SMTP = true;
    
    
    
    // static member variables
    private static Logger log = Logger.getLogger(Server.class);
    private static Server singleton = null;
    
    // class member variables
    private ThreadPoolManager threadPool = null;
    private TCPServer popServer = null;
    private TCPServer imapServer = null;
    private TCPServer smtpServer = null;
    
    
    /**
     * Creates a new instance of Server
     *
     * @exception ServerException
     */
    private Server() throws ServerException {
        try {
            // retrieve the general configuration.
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(Server.class);
            
            // instanciate the server configuration
            ServerConfig.getInstance();
            
            
            // instanciate the pop server.
            if (config.getBoolean(START_POP,DEFAULT_START_POP)) {
                popServer = new TCPServer(
                        (int)config.getLong(POP_PORT,DEFAULT_POP_PORT),
                        config.getString(POP_ADDRESS,DEFAULT_POP_ADDRESS),
                        POP3Request.class);
            }
            
            // instanciate the pop server.
            if (config.getBoolean(START_IMAP,DEFAULT_START_IMAP)) {
                imapServer = new TCPServer(
                            (int)config.getLong(IMAP_PORT,DEFAULT_IMAP_PORT),
                            config.getString(IMAP_ADDRESS,DEFAULT_IMAP_ADDRESS),
                            ImapRequest.class);
            }
            
            // instanciate the pop server.
            if (config.getBoolean(START_SMTP,DEFAULT_START_SMTP)) {
                smtpServer = new TCPServer(
                        (int)config.getLong(SMTP_PORT,DEFAULT_SMTP_PORT),
                        config.getString(SMTP_ADDRESS,DEFAULT_SMTP_ADDRESS),
                        SmtpRequest.class);
            }
            
            // instanciate the email server thread pool
            threadPool = new ThreadPoolManager(
                    (int)config.getLong(POOL_SIZE,DEFAULT_POOL_SIZE), 
                    ServerRequestProcessor.class, config.getString(POOL_USER));
            
            
        } catch (Exception ex) {
            log.error("Failed to initialize the EMail Server core : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to initialize the EMail Server core : " + 
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to initialize the server.
     *
     * @return A reference to the server.
     * @exception ServerException
     */
    public synchronized static Server instantiate() throws ServerException {
        if (singleton == null) {
            singleton = new Server();
        }
        return singleton;
    }
    
    
    /**
     * This method is called to retrieve an instance of the server.
     *
     * @return An instance of the server.
     * @exception ServerException
     */
    public synchronized static Server getInstance() throws ServerException {
        if (singleton == null) {
            throw new ServerException("The Server has not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method initializes the server.
     */
    public void initialize() {
        try {
            if (this.popServer != null) {
                log.info("Starting the POP3 server.");
                this.popServer.start();
            }
        } catch (Exception ex) {
            log.error("Failed to start the POP3 server : " + ex.getMessage(),ex);
        }
        try {
            if (this.imapServer != null) {
                log.info("Starting the IMAP server.");
                this.imapServer.start();
            }
        } catch (Exception ex) {
            log.error("Failed to start the IMAP server : " + ex.getMessage(),ex);
        }
        try {
            if (this.smtpServer != null) {
                log.info("Starting the SMTP server.");
                this.smtpServer.start();
            }
        } catch (Exception ex) {
            log.error("Failed to start the SMTP server : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method terminates the server.
     */
    public void terminate() {
        
        try {
            if (this.smtpServer != null) {
                log.info("Stopping the SMTP server.");
                this.smtpServer.terminate();
            }
        } catch (Exception ex) {
            log.error("Failed to stop the SMTP server : " + ex.getMessage(),ex);
        }
        try {
            if (this.imapServer != null) {
                log.info("Stopping the IMAP server.");
                this.imapServer.terminate();
            }
        } catch (Exception ex) {
            log.error("Failed to stop the IMAP server : " + ex.getMessage(),ex);
        }
        try {
            if (this.popServer != null) {
                log.info("Stopping the POP3 server.");
                this.popServer.terminate();
            }
        } catch (Exception ex) {
            log.error("Failed to stop the POP3 server : " + ex.getMessage(),ex);
        }
        
        // terminate the request manager
        log.info("Terminate the server request manager");
        ServerRequestManager.getInstance().terminate();
        
        // terminate the request manager
        log.info("terminate the thread pool");
        try {
            threadPool.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the thread pool : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the current status of this server.
     */
    public String getStatus() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Thread Pool Size : ").append(this.threadPool.getSize())
        .append("\n");
        if (popServer != null) {
            buffer.append("POP3 : ").append(popServer.getStatus()).append("\n");
        }
        if (imapServer != null) {
            buffer.append("IMAP : ").append(imapServer.getStatus()).append("\n");
        }
        if (smtpServer != null) {
            buffer.append("SMTP : ").append(smtpServer.getStatus()).append("\n");
        }
        try {
            buffer.append(NSTool.getInstance().getStatus());
        } catch (Exception ex) {
            log.error("Failed to retrieve the dns status : " + ex.getMessage(),
                    ex);
            buffer.append("NS Config: Not Available\n");
        }
        return buffer.toString();
    }
}
