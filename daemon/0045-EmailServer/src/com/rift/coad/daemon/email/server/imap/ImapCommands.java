/*
 * EMailServer: The email server implementation.
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
 * ImapRequest.java
 */
package com.rift.coad.daemon.email.server.imap;

/**
 * The imap commands
 *
 * @author brett chaldecott
 */
public class ImapCommands {
    
    /**
     * The list of commands
     */
    public final static String CAPABILITY = "CAPABILITY";
    public final static String NOOP = "NOOP";
    public final static String LOGOUT = "LOGOUT";
    public final static String AUTHENTICATE = "AUTHENTICATE";
    public final static String LOGIN = "LOGIN";
    public final static String SELECT = "SELECT";
    public final static String EXAMINE = "EXAMINE";
    public final static String CREATE = "CREATE";
    public final static String DELETE = "DELETE";
    public final static String RENAME = "RENAME";
    public final static String SUBSCRIBE = "SUBSCRIBE";
    public final static String UNSUBSCRIBE = "UNSUBSCRIBE";
    public final static String LIST = "LIST";
    public final static String LSUB = "LSUB";
    public final static String STATUS = "STATUS";
    public final static String APPEND = "APPEND";
    public final static String CHECK = "CHECK";
    public final static String CLOSE = "CLOSE";
    public final static String EXPUNGE = "EXPUNGE";
    public final static String SEARCH = "SEARCH";
    public final static String FETCH = "FETCH";
    public final static String STORE = "STORE";
    public final static String COPY = "COPY";
    public final static String UID = "UID";
    
    /**
     * The responses
     */
    public final static String OK = "OK";
    public final static String NO = "NO";
    public final static String BAD = "BAD";
    public final static String PREAUTH = "PREAUTH";
    public final static String BYE = "BYE";
    public final static String COMPLETED = "completed";
    
    /**
     * The reponse messages
     */
    public final static String WELCOME_MESSAGE = "Coadunation IMAP Server";
    
    /**
     * The invalid command message
     */
    public final static String INVALID_REQUEST = "Invalid Request [%s]";
    
    /**
     * The invalid command message
     */
    public final static String SERVICE_UNAVAILABLE = "Service Unavailable [%s]";
    
    
    /**
     * The autentication failed
     */
    public final static String AUTHENTICATION_FAILURE = 
            "authenticate failure: %s";
    
    /**
     * Authentication successfull
     */
    public final static String AUTHENTICATION_SUCCESSFULL = 
            "authenticate completed, now in authenticated state";
    
    /**
     * CAPABILITY response
     */
    public final static String CAPABILITY_RESPONSE = 
            "IMAP4rev1 AUTH=PLAIN";
    
    /**
     * The reponse messages
     */
    public final static String LOGOUT_MESSAGE = 
            "Coadunation IMAP Server Logging Out";
    
    
    /**
     * Creates a new instance of ImapCommands
     */
    private ImapCommands() {
    }
    
}
