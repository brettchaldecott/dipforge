/*
 * EMailServer: The email server implementation.
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
 * POPCommands.java
 */

package com.rift.coad.daemon.email.server.pop;

/**
 * The POP commands.
 *
 * @author brett chaldecott
 */
public class POPCommands {
    
    // authentication commands
    /**
     * The user to authenticate the session with.
     */
    public final static String USER = "USER";
    
    /**
     * Unknown user
     */
    public final static String USER_UNKNOWN = 
            "User Unknown";
    
    /**
     * Unknown user
     */
    public final static String USER_OK = 
            "User Ok";
    
    
    /**
     * The password to use to authenticate
     */
    public final static String PASS = "PASS";
    
    /**
     * Maildrop ready
     */
    public final static String MAILDROP_READY = 
            "maildrop ready";
    
    /**
     * Unknown user
     */
    public final static String PASS_INVALID = 
            "Invalid password";
    
    /**
     * Unable to lock maildrop
     */
    public final static String UNABLE_TO_LOCK_MAILDROP = 
            "unable to lock maildrop";
    
    // transaction commands
    
    /**
     * The status of the mailbox
     */
    public final static String STAT = "STAT";
    
    /**
     * The status of the mailbox
     */
    public final static String STAT_MESSAGE = "%d %o";
    
    
    /**
     * a message-number (optional), which, if present, may NOT
     * refer to a message marked as deleted
     */
    public final static String LIST = "LIST";
    
    /**
     * List the messages
     */
    public final static String LIST_FOLLOWS = 
            "%d messages";
    
    /**
     * Retrieve the specified message number.
     */
    public final static String RETR = "RETR";
    
    /**
     * RETR no such message
     */
    public final static String RETR_MESSAGE = 
            "%o octets";
    
    
    /**
     * RETR no such message
     */
    public final static String RETR_NO_SUCH_MESSAGE = 
            "no such message";
    
    
    /**
     * Delete the specified message
     */
    public final static String DELE = "DELE";
    
    /**
     * DELETE message ok
     */
    public final static String DELE_MESSAGE_OK = 
            "message deleted";
    
    /**
     * DELETE  no such message
     */
    public final static String DELE_NO_SUCH_MESSAGE = 
            "no such message";
    
    /**
     * Ping call to keep transaction alive.
     */
    public final static String NOOP = "NOOP";
    
    /**
     * Reset an operations that have been made during the the transaction.
     */
    public final static String RSET = "RSET";
    
    /**
     * The quit command valid for both
     */
    public final static String QUIT = "QUIT";
    
    
    /**
     * The service ready message
     */
    public final static String QUIT_RESPONSE = 
            "Coadunation POP3 server signing off.";
    
    /**
     * The top of the message
     */
    public final static String TOP = "TOP";
    
    /**
     * The unique identifier for a message.
     */
    public final static String UIDL = "UIDL";
    
    /**
     * The service ready message
     */
    public final static String UIDL_RESPONSE = 
            "%d %s";
    
    
    /**
     * Okay response
     */
    public final static String OK = "+OK";
    
    /**
     * Error response
     */
    public final static String ERR = "-ERR";
    
    /**
     * The service ready message
     */
    public final static String SERVICE_READY_MESSAGE = 
            "Coadunation POP3 Service ready.";
    
    /**
     * The service ready message
     */
    public final static String SERVICE_NOT_AVAILABLE_MESSAGE = 
            "Coadunation POP3 Service Not Available [%s].";
    
    /**
     * The service ready message
     */
    public final static String INVALID_REQUEST = 
            "Invalid request [%s].";
    
    /**
     * The service ready message
     */
    public final static String INVALID_COMMAND_IN_AUTHENTICATION = 
            "Invalid request in authentication scope";
    
    /**
     * The service ready message
     */
    public final static String INVALID_COMMAND_IN_TRANSACTION = 
            "Invalid request in transaction scope";
    
    
    /**
     * The service ready message
     */
    public final static String UNKNOWN_COMMAND = 
            "Unknown command [%s]";
    
    
    /**
     * Creates a new instance of POPCommands
     */
    private POPCommands() {
    }
    
}
