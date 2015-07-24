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
 * SmtpRequest.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.net.InetAddress;
import java.net.Socket;
import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;

// log4j imports
import org.apache.log4j.Logger;

// james mail server imports
import org.apache.mailet.RFC2822Headers;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.transaction.UserTransactionWrapper;


// email server imports
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.ServerRequest;
import com.rift.coad.daemon.email.server.ServerException;
import com.rift.coad.daemon.email.server.config.ServerRelay;
import com.rift.coad.daemon.email.server.dns.NSTool;
import com.rift.coad.daemon.email.smtp.Server;
import com.rift.coad.daemon.email.smtp.Message;
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.smtp.Header;


/**
 * This object is responsible for answering a smtp request.
 *
 * @author brett chaldecott
 */
public class SmtpRequest implements ServerRequest {
    
    // class constants
    private final static String CHECK_NS = "validate_domain_argument";
    private final static String SMTP_SERVER_JNDI = "smtp_server_jndi";
    private final static String DEFAULT_SMTP_SERVER_JNDI =
            "java:comp/env/bean/email/SMTPServer";
    private final static String SOCKET_TIMEOUT = "smtp_socket_timeout";
    private final static long DEFAULT_SOCKET_TIMEOUT = 120000;
    
    // class singletons
    private static Logger log = Logger.getLogger(SmtpRequest.class);
    
    // private member variables
    private Socket socket = null;
    private boolean process = true;
    private InetAddress address = null;
    private boolean handshake = false;
    private boolean checkNs = false;
    private boolean relay = false;
    private Server smtpServer = null;
    private Message currentMessage = null;
    
    /**
     * Creates a new instance of SmtpRequest
     */
    public SmtpRequest(Socket socket) throws ServerException {
        this.socket = socket;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    SmtpRequest.class);
            checkNs = config.getBoolean(CHECK_NS);
            Context context = new InitialContext();
            smtpServer = (Server)context.lookup(
                    config.getString(SMTP_SERVER_JNDI,
                    DEFAULT_SMTP_SERVER_JNDI));
            
            // Set the socket to timeout after XX seconds, the default is 60.
            this.socket.setSoTimeout( (int)config.getLong(this.SOCKET_TIMEOUT,
                    DEFAULT_SOCKET_TIMEOUT) );
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the configuration for the SMTP request :"
                    + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the configuration for the SMTP request :"
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the SMTP request
     */
    public void processRequest() {
        
        Writer out = null;
        BufferedReader in = null;
        try {
            //Prepare the input and output streams.
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream() ));
            
            // hello message
            write(out,SmtpCommands.SERVICE_READY,
                    String.format(SmtpCommands.SERVICE_READY_MESSAGE,
                    SMTPUtils.getDateString()));
            
            
            // check if relay is allowed
            address = socket.getInetAddress();
            relay = checkRelay(address);
            if (!checkNs && !relay) {
                write(out,SmtpCommands.REQUEST_ABORTED,
                        String.format(SmtpCommands.REQUEST_ABORTED_MESSAGE,
                        ("Cannot relay for [" + address.getHostName() + "]")));
                log.info("Cannot relay for [" + address.getHostName() + "]");
                return;
            }
            
            while(process) {
                String line = read(in);
                
                // parse the input
                String command = parseCommand(line);
                String argument = this.parseArgument(line);
                
                // handle the command
                handleCommand(out, in, command, argument);
            }
            
            
        } catch (Exception ex) {
            log.error("Failed to process the request : " + ex.getMessage(),ex);
            if (out != null) {
                try {
                    write(out,SmtpCommands.SERVICE_NOT_AVAILABLE,
                            String.format(SmtpCommands.SERVICE_NOT_AVAILABLE_MESSAGE,
                            ex.getMessage()));
                } catch (Exception ex2) {
                    log.error("Failed to send error message : " +
                            ex2.getMessage(),ex2);
                }
            }
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
                
            }
        }
        
    }
    
    
    /**
     * This method is responsible for handling the command that needs
     * procedssing.
     */
    private void handleCommand(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            // supported commands
            if (command.equalsIgnoreCase(SmtpCommands.HELO) ||
                    command.equalsIgnoreCase(SmtpCommands.EHLO)) {
                processHelo(out, in, command,argument);
            } else if (handshake &&
                    command.equalsIgnoreCase(SmtpCommands.MAIL)) {
                processMail(out, in, command,argument);
            } else if (handshake &&
                    command.equalsIgnoreCase(SmtpCommands.RCPT)) {
                processRcpt(out, in, command,argument);
            } else if (handshake &&
                    command.equalsIgnoreCase(SmtpCommands.DATA)) {
                processData(out, in, command);
            } else if (handshake &&
                    command.equalsIgnoreCase(SmtpCommands.RSET)) {
                this.currentMessage = new Message();
                write(out,SmtpCommands.REQUEST_OKAY,
                        String.format(SmtpCommands.REQUEST_OKAY_MESSAGE,
                        SmtpCommands.RSET));
            } else if (command.equalsIgnoreCase(SmtpCommands.NOOP)) {
                // reply to ping command
                write(out,SmtpCommands.REQUEST_OKAY,
                        String.format(SmtpCommands.REQUEST_OKAY_MESSAGE,
                        SmtpCommands.NOOP));
            } else if (command.equalsIgnoreCase(SmtpCommands.QUIT)) {
                write(out,SmtpCommands.CLOSING_TRANSACTION_CHANNEL,
                        SmtpCommands.CLOSING_TRANSACTION_CHANNEL_MESSAGE);
                process = false;
            }
            // unsupported commands
            else if (command.equalsIgnoreCase(SmtpCommands.TURN) ||
                    command.equalsIgnoreCase(SmtpCommands.SEND) ||
                    command.equalsIgnoreCase(SmtpCommands.SOML) ||
                    command.equalsIgnoreCase(SmtpCommands.SAML) ||
                    command.equalsIgnoreCase(SmtpCommands.VRFY) ||
                    command.equalsIgnoreCase(SmtpCommands.EXPN) ||
                    command.equalsIgnoreCase(SmtpCommands.HELP)) {
                write(out,SmtpCommands.COMMANDS_NOT_IMPLEMENTED,
                        SmtpCommands.COMMAND_PARAMETER_NOT_IMPLEMENTED_MESSAGE);
            } else {
                write(out,SmtpCommands.SYNTAX_ERROR,
                        SmtpCommands.SYNTAX_ERROR_MESSAGE);
            }
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the SMTP request : " + ex.getMessage(),
                    ex);
            //throw new ServerException("Failed to process the SMTP request : "
            //        + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Reads a line from the input stream and returns it.
     */
    private String read(BufferedReader in) throws ServerException {
        try {
            String inputLine = in.readLine().trim();
            log.debug( "Read Input: " + inputLine );
            return inputLine;
        } catch(java.net.SocketTimeoutException ex) {
            log.debug(
                    "Socket timeout shutting down server connection : " 
                    + ex.getMessage(), ex);
            throw new ServerException(
                    "Socket timeout shutting down server connection : " 
                    + ex.getMessage(), ex);
        } catch( Exception ex ) {
            log.error(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
            throw new ServerException(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * Writes the specified output message to the client.
     */
    private void write(Writer out, String code, String message)
    throws ServerException {
        
        try {
            log.debug( "Writing: " + message );
            out.write(String.format("%s %s\r\n",code,message));
            out.flush();
        } catch (Exception ex) {
            log.error(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
            throw new ServerException(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * Parses the input stream for the command.  The command is the
     * begining of the input stream to the first space.  If there is
     * space found, the entire input string is returned.
     * <p>
     * This method converts the returned command to uppercase to allow
     * for easier comparison.
     * <p>
     * Additinally, this method checks to verify that the quit command
     * was not issued.  If it was, a SystemException is thrown to terminate
     * the connection.
     */
    private String parseCommand( String inputString ) {
        
        int index = inputString.indexOf( " " );
        
        if( index == -1 ) {
            String command = inputString.toUpperCase();
            return command;
        } else {
            String command = inputString.substring( 0, index ).toUpperCase();
            return command;
        }
    }
    
    
    /**
     * Parses the input stream for the argument.  The argument is the
     * text starting afer the first space until the end of the inputstring.
     * If there is no space found, an empty string is returned.
     * <p>
     * This method does not convert the case of the argument.
     */
    private String parseArgument( String inputString ) {
        
        int index = inputString.indexOf( " " );
        
        if( index == -1 ) {
            return "";
        } else {
            return inputString.substring( index + 1 );
        }
    }
    
    
    /**
     * This method is called to process the helo request.
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processHelo(Writer out, BufferedReader in, String command,
            String argument) {
        try {
            // check to see if a name lookup for the argument is required.
            if (!relay && checkNs) {
                if (NSTool.getInstance().getByName(argument) == null) {
                    write(out,SmtpCommands.SYNTAX_ERROR_PARAMETERS,
                            String.format(
                            SmtpCommands.SYNTAX_ERROR_PARAMETERS_MESSAGE,
                            argument));
                    process = false;
                    return;
                }
            }
            
            // This method responds to the helo request
            handshake = true;
            write(out,SmtpCommands.REQUEST_OKAY,
                    String.format("HELO [%s] [%s]",
                    address.getHostName(),
                    address.getHostAddress()));
            
            // create new message
            currentMessage = new Message();
            
        } catch (Exception ex) {
            log.error("Failed to process the HELO command : " + ex.getMessage(),
                    ex);
            
        }
    }
    
    
    /**
     * This method processes the relay check
     */
    private boolean checkRelay(InetAddress address) throws ServerException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            boolean result = ServerConfig.getInstance().getRelay().
                    checkRelayAllow(address);
            transaction.commit();
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve relay information : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve relay information : " +
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is called to process the mail request.
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processMail(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        // strip off the unnecessary arguments
        if (!argument.toLowerCase().trim().matches(
                "from[:][\\p{Space}]*[<]*[\\p{Alnum}\\p{Space}!" +
                "\"#$%&'()*+,-./:;=?@\\^_`{|}~]+[>]*")) {
            log.info("Invalid request format : " + argument);
            write(out,SmtpCommands.SYNTAX_ERROR_PARAMETERS,
                    String.format(
                    SmtpCommands.SYNTAX_ERROR_PARAMETERS_MESSAGE,
                    argument));
            return;
        }
        
        String addressField = argument.substring(argument.indexOf(":") + 1,
                argument.length());
        addressField = addressField.replaceAll("<","");
        addressField = addressField.replaceAll(">","");
        addressField = addressField.trim();
        log.debug("FROM : " + addressField);
        String[] addresses = addressField.split("[,]");
        List addressList = new ArrayList();
        for (int index = 0; index < addresses.length; index++) {
            try {
                log.debug("Process address : " + addresses[index]);
                addressList.add(new Address(addresses[index]));
            } catch (Exception ex) {
                log.info("Invalid address [" + addresses[index] + "] : " +
                        ex.getMessage(),ex);
                write(out,SmtpCommands.SYNTAX_ERROR_PARAMETERS,
                        String.format(
                        SmtpCommands.SYNTAX_ERROR_PARAMETERS_MESSAGE,
                        ex.getMessage()));
                return;
            }
        }
        
        this.currentMessage.setFrom(addressList);
        
        // reply to rcpt
        write(out,SmtpCommands.REQUEST_OKAY,
                String.format(SmtpCommands.REQUEST_OKAY_MESSAGE,
                SmtpCommands.MAIL));
    }
    
    
    /**
     * This method is called to process the rcpt request.
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processRcpt(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        // strip off the unnecessary arguments
        if (!argument.toLowerCase().trim().matches(
                "to[:][\\p{Space}]*[<]*[\\p{Alnum}\\p{Space}!" +
                "\"#$%&'()*+,-./:;=?@\\^_`{|}~]+[>]*")) {
            log.info("Invalid request format : " + argument);
            write(out,SmtpCommands.SYNTAX_ERROR_PARAMETERS,
                    String.format(
                    SmtpCommands.SYNTAX_ERROR_PARAMETERS_MESSAGE,
                    argument));
            return;
        }
        
        String addressField = argument.substring(argument.indexOf(":") + 1,
                argument.length());
        addressField = addressField.replaceAll("<","");
        addressField = addressField.replaceAll(">","");
        addressField = addressField.trim();
        log.info("To : " + addressField);
        String[] addresses = addressField.split("[,]");
        List addressList = new ArrayList();
        for (int index = 0; index < addresses.length; index++) {
            try {
                log.debug("Process address : " + addresses[index]);
                addressList.add(new Address(addresses[index]));
            } catch (Exception ex) {
                log.info("Invalid address [" + addresses[index] + "] : " +
                        ex.getMessage(),ex);
                write(out,SmtpCommands.SYNTAX_ERROR_PARAMETERS,
                        String.format(
                        SmtpCommands.SYNTAX_ERROR_PARAMETERS_MESSAGE,
                        ex.getMessage()));
                return;
            }
        }
        
        // add all recipients
        currentMessage.getRCPTs().addAll(addressList);
        
        // reply to rcpt
        write(out,SmtpCommands.REQUEST_OKAY,
                String.format(SmtpCommands.REQUEST_OKAY_MESSAGE,
                SmtpCommands.RCPT));
    }
    
    
    /**
     * This method is called to process the data request.
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     */
    private void processData(Writer out, BufferedReader in, String command)
    throws ServerException{
        
        try {
            // validate message
            if (currentMessage.getFrom().size() == 0) {
                write(out,SmtpCommands.BAD_SEQUENCE_OF_COMMANDS,
                        String.format(
                        SmtpCommands.BAD_SEQUENCE_OF_COMMANDS_MESSAGE,
                        "No from address supplied"));
                return;
            } else if (currentMessage.getRCPTs().size() == 0) {
                write(out,SmtpCommands.BAD_SEQUENCE_OF_COMMANDS,
                        String.format(
                        SmtpCommands.BAD_SEQUENCE_OF_COMMANDS_MESSAGE,
                        "No recipent supplied."));
                return;
            }
            write(out,SmtpCommands.START_MAIL_INPUT,
                    SmtpCommands.START_MAIL_INPUT_MESSAGE);
            String inputLine = in.readLine();
            Header currentHeader = null;
            while (!inputLine.equals(".")) {
                log.debug( "Read Input: " + inputLine );
                // check for end of header section
                if (inputLine.equals("")) {
                    break;
                } else if (inputLine.startsWith(" ") || 
                        inputLine.startsWith("\t")) {
                    // handle wrapped header lines
                    log.debug( "Wrapped header: " + inputLine );
                    if (currentHeader != null) {
                        currentHeader.setValue(currentHeader.getValue() + "\r\n" 
                                + inputLine);
                    }
                } else {
                    int pos = inputLine.indexOf(":");
                    String key = inputLine;
                    String value = "";
                    if (pos != -1) {
                        key = inputLine.substring(0,pos);
                        value = inputLine.substring(pos + 1, inputLine.length());
                    }
                    currentHeader = new Header(key,value);
                    currentMessage.addHeader(currentHeader);
                }
                inputLine = in.readLine();
            }
            
            StringBuffer buffer = new StringBuffer();
            inputLine = in.readLine();
            while (!inputLine.equals(".")) {
                log.debug( "Read Input: " + inputLine );
                buffer.append(inputLine).append("\r\n");
                inputLine = in.readLine();
            }
            currentMessage.setData(buffer.toString());
            
            this.smtpServer.sendMessage(currentMessage);
            
            write(out,SmtpCommands.REQUEST_OKAY,
                    String.format(SmtpCommands.REQUEST_OKAY_MESSAGE,
                    SmtpCommands.DATA));
            currentMessage = new Message();
        } catch( Exception ex ) {
            log.error(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
            throw new ServerException(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
        }
    }
    
}
