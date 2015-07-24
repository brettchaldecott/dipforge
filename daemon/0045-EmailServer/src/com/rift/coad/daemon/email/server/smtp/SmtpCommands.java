/*
 * EMailServer: The email server
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
 * EMailServerImpl.java
 */

package com.rift.coad.daemon.email.server.smtp;

/**
 * The definition of the smtp constants
 * @author brett chaldecott
 */
public class SmtpCommands {
    
    // supported commands
    public final static String HELO = "HELO";
    public final static String EHLO = "EHLO";
    public final static String MAIL = "MAIL";
    public final static String RCPT = "RCPT";
    public final static String DATA = "DATA";
    public final static String RSET = "RSET";
    public final static String NOOP = "NOOP";
    public final static String QUIT = "QUIT";
    
    // unsupported commands
    public final static String TURN = "TURN";
    public final static String SEND = "SEND";
    public final static String SOML = "SOML";
    public final static String SAML = "SAML";
    public final static String VRFY = "VRFY";
    public final static String EXPN = "EXPN";
    public final static String HELP = "HELP";
    
    // reply codes and messages
    public final static String SYSTEM_STATUS = "211";
    public final static String HELP_REPLY = "214";
    public final static String SERVICE_READY = "220";
    public final static String SERVICE_READY_MESSAGE = 
            "Coadunation SMTP Service ready %s";
    public final static String CLOSING_TRANSACTION_CHANNEL = "221";
    public final static String CLOSING_TRANSACTION_CHANNEL_MESSAGE = 
        "Coadunation SMTP Service closing transmission channel";
    public final static String REQUEST_OKAY = "250";
    public final static String REQUEST_OKAY_MESSAGE = "%s, completed";
    public final static String USER_NOT_LOCAL = "251";
    public final static String USER_NOT_LOCAL_MESSAGE = 
            "User not local; will forward to %s";
    public final static String START_MAIL_INPUT = "354";
    public final static String START_MAIL_INPUT_MESSAGE = 
            "Start mail input; end with <CRLF>.<CRLF>";
    public final static String SERVICE_NOT_AVAILABLE = "421";
    public final static String SERVICE_NOT_AVAILABLE_MESSAGE = 
            "Service not available, closing transmission channel; %s";
    public final static String MAILBOX_UNAVALABLE = "450";
    public final static String MAILBOX_UNAVALABLE_MESSAGE = 
            "Requested mail action not taken: mailbox unavailable";
    public final static String REQUEST_ABORTED = "451";
    public final static String REQUEST_ABORTED_MESSAGE = 
            "Requested action aborted: %s";
    public final static String REQUEST_NOT_TAKEN = "452";
    public final static String REQUEST_NOT_TAKEN_MESSAGE = 
            "Requested action not taken: %s";
    public final static String SYNTAX_ERROR = "500";
    public final static String SYNTAX_ERROR_MESSAGE = 
            "Syntax error, command unrecognized";
    public final static String SYNTAX_ERROR_PARAMETERS = "501";
    public final static String SYNTAX_ERROR_PARAMETERS_MESSAGE = 
            "Invalid argument: %s";
    public final static String COMMANDS_NOT_IMPLEMENTED = "502";
    public final static String COMMANDS_NOT_IMPLEMENTED_MESAGE = 
            "Command not implemented";
    public final static String BAD_SEQUENCE_OF_COMMANDS = "503";
    public final static String BAD_SEQUENCE_OF_COMMANDS_MESSAGE = 
            "Bad sequence of commands: %s";
    public final static String COMMAND_PARAMETER_NOT_IMPLEMENTED = "504";
    public final static String COMMAND_PARAMETER_NOT_IMPLEMENTED_MESSAGE = 
            "Command parameter not implemented";
    public final static String REQUEST_ACTION_NOT_TAKEN = "550";
    public final static String REQUEST_ACTION_NOT_TAKEN_MESSAGE = 
            "Requested action not taken: %s";
    public final static String USER_NOT_LOCAL2 = "551";
    public final static String USER_NOT_LOCAL2_MESSAGE = 
            "User not local; please try %s";
    public final static String REQUEST_MAIL_ACTION_ABORTED = "552";
    public final static String REQUEST_MAIL_ACTION_ABORTED_MESSAGE = 
            "Requested mail action aborted: %s";
    public final static String REQUEST_ACTION_NOT_TAKEN2 = "553";
    public final static String REQUEST_ACTION_NOT_TAKEN2_MESSAGE = 
            "Requested action not taken: %s";
    public final static String TRANSACTION_FAILED = "554";
    public final static String TRANSACTION_FAILED_MESSAGE = 
            "Transaction failed";
    
    
    
    /**
     * Creates a new instance of SmtpCommands
     */
    private SmtpCommands() {
    }
    
}
