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
 * POP3Request.java
 */

// package path
package com.rift.coad.daemon.email.server.pop;

// java imports
import com.sun.mail.imap.protocol.FLAGS;
import java.net.Socket;
import java.net.InetAddress;
import java.net.Socket;
import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;

// log4j imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;

// mail dir
import net.ukrpost.storage.maildir.MaildirStore;
import net.ukrpost.storage.maildir.MaildirQuota;

// coadunation imports
import com.rift.coad.util.transaction.UserTransactionWrapper;

// email server imports
import com.rift.coad.daemon.email.server.ServerRequest;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.ServerRequest;
import com.rift.coad.daemon.email.server.ServerException;
import com.rift.coad.daemon.email.server.db.Emailbox;

/**
 * This object is responsible for answering a pop3 request.
 *
 * @author brett chaldecott
 */
public class POP3Request implements ServerRequest {
    
    // class singletons
    private static Logger log = Logger.getLogger(POP3Request.class);
    
    // private member variables
    private Socket socket = null;
    private boolean process = true;
    private Emailbox emailbox = null;
    private Session session = null;
    private MaildirStore store = null;
    private Folder inbox = null;
    private List deletedMessages = new ArrayList();
    
    
    /**
     * Creates a new instance of POP3Request
     */
    public POP3Request(Socket socket) {
        this.socket = socket;
    }
    
    
    /**
     * This method processes the POP3 request
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
            write(out,POPCommands.OK,POPCommands.SERVICE_READY_MESSAGE);
            
            
            // authenticate the transaction
            while (process) {
                String line = read(in);
                
                // parse the input
                String command = parseCommand(line);
                String argument = this.parseArgument(line);
                
                // handle the authentication
                if (handleAuthentication(out, in, command, argument)) {
                    initSession();
                    break;
                }
            }
            
            // process the transaction
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
                    write(out,POPCommands.ERR,
                            String.format(POPCommands.
                            SERVICE_NOT_AVAILABLE_MESSAGE,
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
     * Reads a line from the input stream and returns it.
     */
    private String read(BufferedReader in) throws ServerException {
        try {
            String inputLine = in.readLine().trim();
            log.debug( "Read Input: " + inputLine );
            return inputLine;
        } catch(java.net.SocketTimeoutException ex) {
            try {
                if (this.inbox != null) {
                    this.inbox.close(true);
                }
            } catch (Exception ex2) {
                // ignore
            }
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
     * Writes the specified output message to the client.
     */
    private void write(Writer out, String message)
    throws ServerException {
        
        try {
            log.debug( "Writing: " + message );
            out.write(String.format("%s\r\n",message));
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
     * This method is responsible for handling the command that needs
     * procedssing.
     */
    private boolean handleAuthentication(Writer out, BufferedReader in,
            String command, String argument) throws ServerException {
        try {
            // supported commands
            if (command.equalsIgnoreCase(POPCommands.QUIT)) {
                write(out,POPCommands.OK,
                        POPCommands.QUIT_RESPONSE);
                process = false;
            }
            // supported commands
            else if (command.equalsIgnoreCase(POPCommands.USER)) {
                processUser(out,in,command,argument);
            }
            // supported commands
            else if (command.equalsIgnoreCase(POPCommands.PASS)) {
                return processPass(out,in,command,argument);
            }
            // unsupported commands
            else if (command.equalsIgnoreCase(POPCommands.DELE) ||
                    command.equalsIgnoreCase(POPCommands.LIST) ||
                    command.equalsIgnoreCase(POPCommands.NOOP) ||
                    command.equalsIgnoreCase(POPCommands.RETR) ||
                    command.equalsIgnoreCase(POPCommands.RSET) ||
                    command.equalsIgnoreCase(POPCommands.STAT) ||
                    command.equalsIgnoreCase(POPCommands.TOP) ||
                    command.equalsIgnoreCase(POPCommands.UIDL)) {
                write(out,POPCommands.ERR,
                        POPCommands.INVALID_COMMAND_IN_AUTHENTICATION);
            } else {
                write(out,POPCommands.ERR,
                        String.format(POPCommands.UNKNOWN_COMMAND,command));
            }
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to process the POP request : " + ex.getMessage(),
                    ex);
            write(out,POPCommands.ERR,
                    String.format(POPCommands.
                    INVALID_REQUEST,
                    ex.getMessage()));
        }
        return false;
    }
    
    
    /**
     * This method is responsible for handling the command that needs
     * procedssing.
     */
    private void handleCommand(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            // supported commands
            if (command.equalsIgnoreCase(POPCommands.QUIT)) {
                write(out,POPCommands.OK,
                        POPCommands.QUIT_RESPONSE);
                inbox.close(true);
                process = false;
                deletedMessages.clear();
            }
            // delete command
            else if (command.equalsIgnoreCase(POPCommands.DELE)) {
                processDelete(out,in,command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.LIST)) {
                processList(out,in,command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.NOOP)) {
                // ping method
                write(out,POPCommands.OK);
            } else if (command.equalsIgnoreCase(POPCommands.RETR)) {
                processRetrieve(out,in,command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.RSET)) {
                processReset(out, in, command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.STAT)) {
                processStat(out, in, command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.UIDL)) {
                processUidl(out, in, command,argument);
            } else if (command.equalsIgnoreCase(POPCommands.TOP)) {
                processTop(out, in, command,argument);
            }
            // unsupported commands
            else if (command.equalsIgnoreCase(POPCommands.USER) ||
                    command.equalsIgnoreCase(POPCommands.PASS)) {
                write(out,POPCommands.ERR,
                        POPCommands.INVALID_COMMAND_IN_TRANSACTION);
            } else {
                write(out,POPCommands.ERR,
                        String.format(POPCommands.UNKNOWN_COMMAND,command));
            }
        } catch (Exception ex) {
            log.error("Failed to process the POP request : " + ex.getMessage(),
                    ex);
            write(out,POPCommands.ERR,
                    String.format(POPCommands.
                    INVALID_REQUEST,
                    ex.getMessage()));
        }
    }
    
    
    /**
     * This method is called to process the user call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processUser(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Emailbox user = ServerConfig.getInstance().getEmailbox().getEmail(
                    argument);
            if (user == null) {
                write(out,POPCommands.ERR,POPCommands.USER_UNKNOWN);
            } else {
                write(out,POPCommands.OK,POPCommands.USER_OK);
                this.emailbox = user;
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to retrieve the user information because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the user information because : " +
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
    }
    
    
    /**
     * Process the password
     *
     * @return TRUE if the password is valid FALSE if not.
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private boolean processPass(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            boolean result = ServerConfig.getInstance().getEmailbox().
                    validatePassword(argument,this.emailbox);
            if (result == false) {
                write(out,POPCommands.ERR,POPCommands.PASS_INVALID);
            } else {
                write(out,POPCommands.OK,POPCommands.MAILDROP_READY);
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to validate the password : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to validate the password : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Init the mail store.
     */
    public void initSession() throws ServerException {
        try {
            // setup the path
            File path = new File(ServerConfig.getInstance().getMailDir() +
                    File.separator + emailbox.getPath());
            URLName url = new URLName(
                    path.toURL().toString().replace("file:","maildir:"));
            
            // write the message to the mail store
            java.util.Properties p = new java.util.Properties();
            p.put("mail.store.maildir.autocreatedir", "true");
            session = Session.getDefaultInstance(p);
            store = (MaildirStore)session.getStore(url);
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
        } catch (Exception ex) {
            log.error("Failed to init the mailstore : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to init the mailstore : " + ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * Process the delete request
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processDelete(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            Message message = inbox.getMessage(Integer.parseInt(argument));
            message.setFlag(Flags.Flag.DELETED,true);
            deletedMessages.add(message);
            write(out,POPCommands.OK,POPCommands.DELE_MESSAGE_OK);
        } catch (java.lang.IndexOutOfBoundsException ex) {
            write(out,POPCommands.ERR,POPCommands.DELE_NO_SUCH_MESSAGE);
        } catch (Exception ex) {
            log.error("Failed to delete the message : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to delete the message : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the delete request
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processList(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            Message[] messages = inbox.getMessages();
            if (argument.equals("")) {
                write(out,POPCommands.OK,String.format(
                        POPCommands.LIST_FOLLOWS,messages.length));
                for (int index = 0; index < messages.length; index++) {
                    Message message = messages[index];
                    out.write(String.format("%d %o\r\n",message.getMessageNumber(),
                            message.getSize()));
                }
                out.write(".\r\n");
                out.flush();
            } else {
                Message message = inbox.getMessage(Integer.parseInt(argument));
                net.ukrpost.storage.maildir.MaildirMessage maildirMessage =
                        (net.ukrpost.storage.maildir.MaildirMessage)message;
                maildirMessage.getMessageID();
                write(out,POPCommands.OK,String.format("%d %o",
                        message.getMessageNumber(),message.getSize()));
            }
        } catch (java.lang.IndexOutOfBoundsException ex) {
            write(out,POPCommands.ERR,POPCommands.RETR_NO_SUCH_MESSAGE);
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the retrieve command
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processRetrieve(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            Message message = inbox.getMessage(Integer.parseInt(argument));
            write(out,POPCommands.OK,String.format(POPCommands.RETR_MESSAGE,
                    message.getSize()));
            net.ukrpost.storage.maildir.MaildirMessage maildirMessage =
                    (net.ukrpost.storage.maildir.MaildirMessage)message;
            maildirMessage.getMessageID();
            Enumeration lines = maildirMessage.getAllHeaderLines();
            while (lines.hasMoreElements()) {
                write(out,lines.nextElement().toString());
            }
            int count = 0;
            //maildirMessage.getRawInputStream().reset();
            write(out,"");
            InputStreamReader inStream = new InputStreamReader(
                    maildirMessage.getRawInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            String line = null;
            while ((line = reader.readLine()) != null){
                write(out,line);
                count++;
            }
            //write(out,".");
            out.flush();
            inStream.close();
        } catch (java.lang.IndexOutOfBoundsException ex) {
            write(out,POPCommands.ERR,POPCommands.RETR_NO_SUCH_MESSAGE);
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the reset command
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processReset(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            for (int index = 0; index < this.deletedMessages.size(); index++) {
                Message message = (Message)deletedMessages.get(index);
                message.setFlag(Flags.Flag.DELETED,false);
            }
            write(out,POPCommands.OK);
            
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the stat request
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processStat(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            Message[] messages = inbox.getMessages();
            int size = 0;
            for (int index = 0; index < messages.length; index++) {
                size += messages[index].getSize();
            }
            write(out,POPCommands.OK,String.format(
                    POPCommands.STAT_MESSAGE,messages.length,size));
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the uidl command
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processUidl(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            if (!argument.equals("")) {
                Message message = inbox.getMessage(Integer.parseInt(argument));
                net.ukrpost.storage.maildir.MaildirMessage maildirMessage =
                        (net.ukrpost.storage.maildir.MaildirMessage)message;
                write(out,POPCommands.OK,String.format(POPCommands.UIDL_RESPONSE,
                        message.getMessageNumber(),maildirMessage.getMessageID()));
            } else {
                Message[] messages = inbox.getMessages();
                write(out,POPCommands.OK);
                for (int index = 0; index < messages.length; index++) {
                    Message message = messages[index];
                    net.ukrpost.storage.maildir.MaildirMessage maildirMessage =
                        (net.ukrpost.storage.maildir.MaildirMessage)message;
                    write(out,String.format(POPCommands.UIDL_RESPONSE,
                            message.getMessageNumber(),
                            maildirMessage.getMessageID()));
                }
                write(out,".");
                out.flush();
            }
            
        } catch (java.lang.IndexOutOfBoundsException ex) {
            write(out,POPCommands.ERR,POPCommands.RETR_NO_SUCH_MESSAGE);
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Process the top command
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processTop(Writer out, BufferedReader in, String command,
            String argument) throws ServerException {
        try {
            String[] topParam = argument.split("[ ]");
            if (topParam.length != 2) {
                write(out,POPCommands.ERR,"Invalid arguments");
                return;
            }
            Message message = inbox.getMessage(Integer.parseInt(topParam[0]));
            write(out,POPCommands.OK);
            net.ukrpost.storage.maildir.MaildirMessage maildirMessage =
                    (net.ukrpost.storage.maildir.MaildirMessage)message;
            maildirMessage.getMessageID();
            Enumeration lines = maildirMessage.getAllHeaderLines();
            while (lines.hasMoreElements()) {
                write(out,lines.nextElement().toString());
            }
            write(out,"");
            int count = 0;
            //maildirMessage.getRawInputStream().reset();
            InputStreamReader inStream = new InputStreamReader(
                    maildirMessage.getRawInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            String line = null;
            while (((line = reader.readLine()) != null) && 
                    (count < Integer.parseInt(topParam[1])) && 
                    (!line.equals("."))){
                write(out,line);
                count++;
            }
            
            write(out,".");
            out.flush();
            inStream.close();
            
        } catch (java.lang.IndexOutOfBoundsException ex) {
            write(out,POPCommands.ERR,POPCommands.RETR_NO_SUCH_MESSAGE);
        } catch (Exception ex) {
            log.error("Failed to list the messages : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to list the messages : " +
                    ex.getMessage(),ex);
        }
    }
}
