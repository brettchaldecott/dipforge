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

// package path
package com.rift.coad.daemon.email.server.imap;

// java imports
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
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;

// log4j imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.imap.protocol.FLAGS;

// mail dir
import net.ukrpost.storage.maildir.MaildirStore;
import net.ukrpost.storage.maildir.MaildirQuota;

// coadunation imports
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// email server imports
import com.rift.coad.daemon.email.server.ServerRequest;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.ServerRequest;
import com.rift.coad.daemon.email.server.ServerException;
import com.rift.coad.daemon.email.server.db.Emailbox;
import com.rift.coad.daemon.email.smtp.Header;



/**
 * This object is responsible for answering a imap request.
 *
 * @author brett chaldecott
 */
public class ImapRequest implements ServerRequest {
    
    // class singletons
    private static Logger log = Logger.getLogger(ImapRequest.class);
    
    // class constants
    private final static String SOCKET_TIMEOUT = "imap_socket_timeout";
    private final static long DEFAULT_SOCKET_TIMEOUT = 10 * 60 * 1000;
    
    // private member variables
    private Socket socket = null;
    private boolean process = true;
    private Emailbox emailbox = null;
    private Session session = null;
    private MaildirStore store = null;
    private Map folders = new HashMap();
    private Folder selected = null;
    private boolean selectedFolder = false;
    private List storeChanges = new ArrayList();
    
    /**
     * Creates a new instance of ImapRequest
     */
    public ImapRequest(Socket socket) throws ServerException {
        this.socket = socket;
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    ImapRequest.class);
            
            // Set the socket to timeout after XX seconds, the default is 60.
            this.socket.setSoTimeout( (int)config.getLong(this.SOCKET_TIMEOUT,
                    DEFAULT_SOCKET_TIMEOUT) );
        } catch (Exception ex) {
            log.error("Failed to initialize the request : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to initialize the request : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method processes the imap request
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
            write(out,"*",ImapCommands.OK,ImapCommands.WELCOME_MESSAGE);
            
            // handle the authentication
            while(process) {
                try {
                    String line = read(in);
                    StringTokenizer token = new StringTokenizer(line," ");
                    
                    // handle the command
                    if (this.handleAuthenticate(out, in, token.nextToken(),
                            token.nextToken(), token)) {
                        break;
                    }
                } catch (java.util.NoSuchElementException ex) {
                    write(out,"*",ImapCommands.BAD,
                            String.format(ImapCommands.
                            INVALID_REQUEST,
                            ex.getMessage()));
                }
            }
            
            // process the transaction
            while(process) {
                try {
                    String line = read(in);
                    if (line == null) {
                        log.debug("There is no input");
                        continue;
                    }
                    StringTokenizer token = new StringTokenizer(line," ");
                    
                    // handle the command
                    handleCommand(out, in, token.nextToken(),
                            token.nextToken(), token);
                } catch (java.util.NoSuchElementException ex) {
                    write(out,"*",ImapCommands.BAD,
                            String.format(ImapCommands.
                            INVALID_REQUEST,
                            ex.getMessage()));
                }
            }
            
            
        } catch (Exception ex) {
            log.error("Failed to process the request : " + ex.getMessage(),ex);
            if (out != null) {
                try {
                    write(out,"*",ImapCommands.BAD,
                            String.format(ImapCommands.
                            SERVICE_UNAVAILABLE,
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
            String inputLine = in.readLine();
            if (inputLine == null) {
                if (socket.isInputShutdown()) {
                    log.error("The input is shut down.");
                    throw new ServerException("The input is shut down");
                }
                log.debug("Assume the client has terminated the connection");
                this.process = false;
                return null;
            }
            log.debug( "Read Input: " + inputLine );
            return inputLine;
        } catch (ServerException ex) {
            throw ex;
        } catch (java.net.SocketTimeoutException ex) {
            this.process = false;
            log.debug(
                    "Socket timeout shutting down server connection : "
                    + ex.getMessage(), ex);
            throw new ServerException(
                    "Socket timeout shutting down server connection : "
                    + ex.getMessage(), ex);
        } catch ( Exception ex ) {
            log.error(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
            throw new ServerException(
                    "Failed to read from the socket : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * Writes the specified output message to the client.
     */
    private void write(Writer out, String number, String code, String message)
    throws ServerException {
        
        try {
            log.debug(String.format("%s %s %s\r\n",number,code,message));
            out.write(String.format("%s %s %s\r\n",number,code,message));
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
     * This method is responsible for handling the command that needs
     * procedssing.
     */
    private boolean handleAuthenticate(Writer out, BufferedReader in,
            String commandNumber ,String command,
            StringTokenizer token) throws ServerException {
        try {
            // handle login
            if (command.equalsIgnoreCase(ImapCommands.CAPABILITY)) {
                this.write(out,"*",ImapCommands.OK,command + " " +
                        ImapCommands.CAPABILITY_RESPONSE);
                this.write(out,commandNumber,ImapCommands.OK,command + " " +
                        ImapCommands.COMPLETED);
            } else if (command.equalsIgnoreCase(ImapCommands.LOGIN)) {
                return processLogin(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.AUTHENTICATE)) {
                return processAuthenticate(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.LOGOUT)) {
                this.write(out,"*",ImapCommands.BYE,
                        ImapCommands.LOGOUT_MESSAGE);
                this.write(out,commandNumber,ImapCommands.OK,command + " " +
                        ImapCommands.COMPLETED);
                process = false;
            } else if (command.equalsIgnoreCase(ImapCommands.CLOSE)) {
                processClose(out, in, commandNumber, command, token);
            } else {
                this.write(out,commandNumber,ImapCommands.BAD,String.format(
                        ImapCommands.INVALID_REQUEST,command));
            }
            return false;
        } catch (Exception ex) {
            log.error("Failed to process the IMAP request : " + ex.getMessage(),
                    ex);
            write(out,"\r\n" + commandNumber, ImapCommands.BAD,
                    String.format(ImapCommands.
                    INVALID_REQUEST,
                    ex.getMessage()));
            return false;
        }
    }
    
    
    /**
     * This method is responsible for handling the command that needs
     * procedssing.
     */
    private void handleCommand(Writer out, BufferedReader in,
            String commandNumber ,String command,
            StringTokenizer token) throws ServerException {
        try {
            // handle login
            if (command.equalsIgnoreCase(ImapCommands.CAPABILITY)) {
                this.write(out,"*",ImapCommands.OK,command + " " +
                        ImapCommands.CAPABILITY_RESPONSE);
                this.write(out,commandNumber,ImapCommands.OK,command + " " +
                        ImapCommands.COMPLETED);
            } else if (command.equalsIgnoreCase(ImapCommands.NOOP)) {
                processNoop(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.LOGOUT)) {
                // close all open folders.
                // This is required since some mail clients do not perform a
                // close or expunge before logout out
                closeFolders();
                this.write(out,"*",ImapCommands.BYE,
                        ImapCommands.LOGOUT_MESSAGE);
                this.write(out,commandNumber,ImapCommands.OK,command + " " +
                        ImapCommands.COMPLETED);
                process = false;
            } else if (command.equalsIgnoreCase(ImapCommands.SELECT)) {
                processSelect(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.EXAMINE)) {
                processExamine(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.CREATE)) {
                processCreate(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.DELETE)) {
                processDelete(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.RENAME)) {
                processRename(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.SUBSCRIBE)) {
                processSubscribe(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.UNSUBSCRIBE)) {
                processUnsubscribe(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.LIST)) {
                processList(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.LSUB)) {
                processLSub(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.STATUS)) {
                processStatus(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.APPEND)) {
                processAppend(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.CHECK)) {
                processCheck(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.CLOSE)) {
                processClose(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.EXPUNGE)) {
                processExpunge(out, in, commandNumber, command, token);
            } else if (command.equalsIgnoreCase(ImapCommands.SEARCH)) {
                processSearch(out, in, commandNumber, command, token, false);
            } else if (command.equalsIgnoreCase(ImapCommands.FETCH)) {
                processFetch(out, in, commandNumber, command, token, false);
            } else if (command.equalsIgnoreCase(ImapCommands.STORE)) {
                processStore(out, in, commandNumber, command, token, false);
            } else if (command.equalsIgnoreCase(ImapCommands.COPY)) {
                processCopy(out, in, commandNumber, command, token, false);
            } else if (command.equalsIgnoreCase(ImapCommands.UID)) {
                this.processUID(out, in, commandNumber, command, token);
            } else {
                this.write(out,commandNumber,ImapCommands.BAD,String.format(
                        ImapCommands.INVALID_REQUEST,command));
            }
        } catch (Exception ex) {
            log.error("Failed to process the IMAP request : " + ex.getMessage(),
                    ex);
            write(out,"\r\n" + commandNumber, ImapCommands.BAD,
                    String.format(ImapCommands.
                    INVALID_REQUEST,
                    ex.getMessage()));
        }
    }
    
    
    /**
     * Process the authenticate call
     *
     * @return TRUE if authenticated false if not.
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private boolean processAuthenticate(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        write(out,commandNumber, ImapCommands.BAD,String.format(
                ImapCommands.AUTHENTICATION_FAILURE,"Unsupported Request"));
        return false;
    }
    
    
    /**
     * This method is called to process the login call
     *
     * @return TRUE if authenticated false if not.
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private boolean processLogin(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Emailbox user = ServerConfig.getInstance().getEmailbox().getEmail(
                    token.nextToken());
            if (user == null) {
                write(out,commandNumber, ImapCommands.NO,String.format(
                        ImapCommands.AUTHENTICATION_FAILURE,"User unknown"));
                return false;
            }
            boolean result = ServerConfig.getInstance().getEmailbox().
                    validatePassword(token.nextToken(),user);
            if (result == false) {
                write(out,commandNumber, ImapCommands.NO,String.format(
                        ImapCommands.AUTHENTICATION_FAILURE,"Invalid Password"));
                return false;
            }
            write(out,commandNumber, ImapCommands.OK,
                    ImapCommands.AUTHENTICATION_SUCCESSFULL);
            this.emailbox = user;
            initSession();
            transaction.commit();
            return true;
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
        } catch (Exception ex) {
            log.error("Failed to init the mailstore : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to init the mailstore : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the close call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processNoop(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            // process the command on the selected folder
            if (this.selected != null) {
                net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                        (net.ukrpost.storage.maildir.MaildirFolder)this.selected;
                Message[] messages = maildirFolder.expunge();
                for (int index = 0; index < messages.length; index++) {
                    Message message = messages[index];
                    this.write(out,String.format(
                            "* %d EXPUNGE",message.getMessageNumber()));
                }
                this.write(out,"*", "" +
                        maildirFolder.getNewMessageCount(), "RECENT");
                this.write(out,"*", "" +
                        maildirFolder.getMessageCount(), "EXISTS");
                for (Iterator iter = this.storeChanges.iterator(); 
                iter.hasNext();) {
                    Message message = (Message)iter.next();
                    printMessageFlags(out,message.getMessageNumber(), message);
                }
            }
            // write out message
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the close command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the close command : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to process the select call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processSelect(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            selectedFolder = false;
            String folderName = ImapUtils.parseQuotedString(token);
            Folder folder = this.getFolder(out,commandNumber,folderName);
            if (folder == null) {
                return;
            }
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)folder;
            this.selected = folder;
            Message[] messages = folder.getMessages();
            int firstUnseen = 0;
            for (int index = 0; index < messages.length; index++) {
                Message message = messages[index];
                if (!message.isSet(Flags.Flag.SEEN)) {
                    if(firstUnseen == 0) {
                        firstUnseen = message.getMessageNumber();
                    }
                }
            }
            this.write(out,"*", "" + messages.length, "EXISTS");
            this.write(out,"*", "" + maildirFolder.getNewMessageCount(),
                    "RECENT");
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UNSEEN %d] Message %d is first unseen",
                    folder.getUnreadMessageCount(),firstUnseen));
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UIDVALIDITY %d] UIDs valid",
                    maildirFolder.getUIDValidity()));
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UIDNEXT %d] Predicted next UID",
                    maildirFolder.getUIDNext()));
            this.write(out,"* FLAGS (\\Answered \\Flagged \\Deleted \\Seen " +
                    "\\Draft  \\Recent)");
            Flags.Flag[] flags = folder.getPermanentFlags().getSystemFlags();
            String permittedFlags = "";
            String sep = "";
            for (int index = 0; index < flags.length; index++) {
                permittedFlags += sep + ImapUtils.getFlag(flags[index]);
                sep = " ";
            }
            this.write(out,String.format(
                    "* OK [PERMANENTFLAGS (%s)] Limited",permittedFlags));
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "[READ-WRITE] SELECT %s",ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the select command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the select command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the examine call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processExamine(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            selectedFolder = false;
            String folderName = ImapUtils.parseQuotedString(token);
            Folder folder = this.getFolder(out,commandNumber,folderName);
            if (folder == null) {
                return;
            }
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)folder;
            this.selected = folder;
            Message[] messages = folder.getMessages();
            int firstUnseen = 0;
            for (int index = 0; index < messages.length; index++) {
                Message message = messages[index];
                if (!message.isSet(Flags.Flag.SEEN)) {
                    if(firstUnseen == 0) {
                        firstUnseen = message.getMessageNumber();
                    }
                }
            }
            this.write(out,"*", "" + messages.length, "EXISTS");
            this.write(out,"*", "" + folder.getNewMessageCount(), "RECENT");
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UNSEEN %d] Message %d is first unseen",
                    folder.getUnreadMessageCount(),firstUnseen));
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UIDVALIDITY %d] UIDs valid",
                    maildirFolder.getUIDValidity()));
            this.write(out,"*", ImapCommands.OK , String.format(
                    "[UIDNEXT %d] Predicted next UID",
                    maildirFolder.getUIDNext()));
            this.write(out,"* FLAGS (\\Answered \\Flagged \\Deleted \\Seen " +
                    "\\Draft \\Recent)");
            Flags.Flag[] flags = folder.getPermanentFlags().getSystemFlags();
            String permittedFlags = "";
            String sep = "";
            for (int index = 0; index < flags.length; index++) {
                permittedFlags += sep + ImapUtils.getFlag(flags[index]);
                sep = " ";
            }
            this.write(out,String.format(
                    "* OK [PERMANENTFLAGS (%s)] Limited",permittedFlags));
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "[READ-ONLY] EXAMINE %s",ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the examine command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the examine command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the create call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processCreate(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            String folderName = parseFolderName(
                    ImapUtils.parseQuotedString(token));
            // remove prepended dots
            log.debug("Create the folder : " + folderName);
            Folder folder = store.getFolder(folderName);
            if (folder.exists()) {
                this.write(out,commandNumber,ImapCommands.NO,String.format(
                        ImapCommands.INVALID_REQUEST,"Folder exists"));
                return;
            }
            folder.create(Folder.HOLDS_MESSAGES);
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "CREATE %s",ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the create command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the create command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the delete call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processDelete(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            String folderName = parseFolderName(
                    ImapUtils.parseQuotedString(token));
            Folder folder = store.getFolder(folderName);
            if (!folder.exists()) {
                this.write(out,commandNumber,ImapCommands.NO,String.format(
                        ImapCommands.INVALID_REQUEST,"Folder Does Not Exist"));
                return;
            }
            folder.delete(true);
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "DELETE %s",ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the delete command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the delete command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the rename call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processRename(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            String folderName = parseFolderName(
                    ImapUtils.parseQuotedString(token));
            String newFolderName = parseFolderName(
                    ImapUtils.parseQuotedString(token));
            Folder folder = store.getFolder(folderName);
            if (!folder.exists()) {
                this.write(out,commandNumber,ImapCommands.NO,String.format(
                        ImapCommands.INVALID_REQUEST,"Folder Does Not Exist"));
                return;
            }
            folder.renameTo(store.getFolder(newFolderName));
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "RENAME %s",ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the rename command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the rename command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the subscribe call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processSubscribe(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            this.write(out,commandNumber,ImapCommands.NO,
                    "subscribe failure: not implemented");
        } catch (Exception ex) {
            log.error("Failed to process the subscribe request " +
                    "information because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the subscribe request " +
                    "information because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the unsubscribe call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processUnsubscribe(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            this.write(out,commandNumber,ImapCommands.NO,
                    "unsubscribe failure: not implemented");
        } catch (Exception ex) {
            log.error("Failed to process the unsubscribe request " +
                    "information because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the unsubscribe request " +
                    "information because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the list call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processList(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            String referenceName = ImapUtils.parseQuotedString(token);
            String mailboxName = ImapUtils.parseQuotedString(token);
            
            if (referenceName.equals("") && mailboxName.equals("")) {
                this.write(out,"*", command,
                        "(\\Noselect) \".\" \"\"");
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "[READ-WRITE] %s %s",command,ImapCommands.COMPLETED));
                return;
            } else if (referenceName.equals("")) {
                referenceName = ".";
            }
            Folder folderRef = store.getFolder(referenceName);
            Folder[] folders = folderRef.list(mailboxName);
            for (int index = 0; index < folders.length; index++) {
                Folder folder = folders[index];
                if (folder.getType() == Folder.HOLDS_FOLDERS) {
                    this.write(out,"*", command, String.format(
                            "(\\Noselect) \".\" \"%s\"",folder.getFullName()));
                } else {
                    this.write(out,"*", command, String.format(
                            "() \".\" \"%s\"",folder.getFullName()));
                }
            }
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "[READ-WRITE] %s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the list command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the list command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the status call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processLSub(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            this.write(out,commandNumber,ImapCommands.NO,
                    "subscribe failure: not implemented");
        } catch (Exception ex) {
            log.error("Failed to retrieve the user information because : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve the user information because : " +
                    ex.getMessage(),ex);
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
    private void processStatus(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            Folder folder =getFolder(out,commandNumber,
                    ImapUtils.parseQuotedString(token));
            if (folder == null) {
                return;
            }
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)folder;
            
            StringBuffer result = new StringBuffer();
            String sep = "";
            
            while(token.hasMoreTokens()) {
                String tokenValue = token.nextToken();
                if (tokenValue.equals("(") || tokenValue.equals(")")) {
                    continue;
                }
                
                if (tokenValue.indexOf("(") != -1) {
                    tokenValue = tokenValue.replace("(","");
                }
                if (tokenValue.indexOf(")") != -1) {
                    tokenValue = tokenValue.replace(")","");
                }
                if (tokenValue.equalsIgnoreCase("MESSAGES")) {
                    result.append(sep).append("MESSAGES ").
                            append("" + maildirFolder.getMessageCount());
                } else if (tokenValue.equalsIgnoreCase("RECENT")) {
                    result.append(sep).append("RECENT ").
                            append("" + maildirFolder.getNewMessageCount());
                } else if (tokenValue.equalsIgnoreCase("UIDNEXT")) {
                    result.append(sep).append("UIDNEXT ").
                            append("" + maildirFolder.getUIDNext());
                } else if (tokenValue.equalsIgnoreCase("UIDVALIDITY")) {
                    result.append(sep).append("UIDVALIDITY ").
                            append("" + maildirFolder.getUIDValidity());
                } else if (tokenValue.equalsIgnoreCase("UNSEEN")) {
                    result.append(sep).append("UNSEEN ").
                            append("" + maildirFolder.getUnreadMessageCount());
                }
                sep = " ";
            }
            this.write(out,"*", command , String.format(
                    "%s (%s)",maildirFolder.getFullName(),result.toString()));
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the status command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the status command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the append call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processAppend(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            Folder folder =getFolder(out,commandNumber,
                    ImapUtils.parseQuotedString(token));
            if (folder == null) {
                return;
            }
            
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)folder;
            
            Flags flags = getFlags(token);
            
            // check for date
            Date receivedDate = new Date();
            Integer size = null;
            while (token.hasMoreTokens()) {
                String tokenValue = ImapUtils.parseQuotedString(token);
                if (tokenValue.matches("[{][0-9]+[}]")) {
                    tokenValue = tokenValue.replace("{","");
                    tokenValue = tokenValue.replace("}","");
                    size = new Integer(Integer.parseInt(tokenValue));
                    break;
                }
                receivedDate = ImapUtils.parseDate(tokenValue);
            }
            
            if (size == null) {
                log.error("Size not provided with request");
                this.write(out,commandNumber,ImapCommands.NO,String.format(
                        ImapCommands.INVALID_REQUEST,"Size not provided with request"));
                return;
            }
            
            // read in the bytes
            log.debug("Read in [" + size.intValue() + "]");
            StringBuffer message = new StringBuffer();
            write(out,"+ Ready for literal data");
            char[] buffer = new char[1024];
            int buffSize = 0;
            while(message.toString().length() < size.intValue() &&
                    ((buffSize = in.read(buffer)) != -1)) {
                message.append(buffer,0,buffSize);
                log.debug("Size is : " + message.toString().length());
            }
            ByteArrayInputStream byteArray = new ByteArrayInputStream(
                    message.toString().getBytes());
            
            // setup the message
            MimeMessage mm = new MimeMessage((Session)null,byteArray);
            mm.setSentDate(receivedDate);
            mm.setFlags(flags,true);
            maildirFolder.appendMessages(new Message[] {mm});
            
            
            // write out message
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the append command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the append command : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to process the check call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processCheck(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            // write out message
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the check command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the check command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the close call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processClose(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            // loop through all the opened folders
            closeFolders();
            
            // write out message
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
            
        } catch (Exception ex) {
            log.error("Failed to process the close command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the close command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the expunge call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processExpunge(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            // loop through all the opened folders
            for (Iterator iter = this.folders.keySet().iterator();
            iter.hasNext();) {
                Folder folder = (Folder)folders.get(iter.next());
                Message[] messages = folder.expunge();
                for (int index = 0; index < messages.length; index++) {
                    Message message = messages[index];
                    this.write(out,String.format(
                            "* %d EXPUNGE",message.getMessageNumber()));
                }
                folder.close(true);
            }
            folders.clear();
            this.selected = null;
            
            // write out message
            this.write(out,commandNumber, ImapCommands.OK , String.format(
                    "%s %s",command,ImapCommands.COMPLETED));
        } catch (Exception ex) {
            log.error("Failed to process the expunge command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the expunge command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the search call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     * @param uid Must respond using uids rather than message numbers.
     */
    private void processSearch(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token, boolean uid) throws ServerException {
        try {
            if (this.selected == null) {
                this.write(out,commandNumber, ImapCommands.NO ,
                        "search error: no selected box");
                return;
            }
            
            ImapSearchFilter filter = new ImapSearchFilter(uid,token);
            
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)this.selected;
            out.write("* SEARCH");
            
            // loop through all the opened folders
            Message[] messages = maildirFolder.getMessages();
            for (int index = 0; index < messages.length; index++) {
                Message message = messages[index];
                log.debug("Message : " + message.getMessageNumber());
                if (!filter.filter(maildirFolder, message)) {
                    continue;
                }
                if (uid) {
                    out.write(" " + maildirFolder.getUID(message));
                } else {
                    out.write(" " + message.getMessageNumber());
                }
            }
            write(out,"");
            
            // write out message
            if (uid) {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "UID SEARCH %s",ImapCommands.COMPLETED));
            } else {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "%s %s",command,ImapCommands.COMPLETED));
            }
        } catch (Exception ex) {
            log.error("Failed to process the search command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the search command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the fetch call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     * @param uid This is a uid request.
     */
    private void processFetch(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token, boolean uid) throws ServerException {
        try {
            if (this.selected == null) {
                this.write(out,commandNumber, ImapCommands.NO ,
                        "fetch error: no selected box");
                return;
            }
            
            // message set.
            ImapSequenceSet sequenceSet = new ImapSequenceSet(token.nextToken());
            ImapFetchHandler handler = new ImapFetchHandler(this.selectedFolder,
                    out,token,uid);
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)this.selected;
            
            // reset the last pos in the sequence
            Message lastMessage =
                    maildirFolder.getMessage(maildirFolder.getMessageCount());
            if (uid) {
                sequenceSet.setEnd(maildirFolder.getUID(lastMessage));
            } else {
                sequenceSet.setEnd(lastMessage.getMessageNumber());
            }
            
            
            // loop through all the opened folders
            for (Iterator iter = sequenceSet.getSequenceSet().iterator();
            iter.hasNext(); ){
                long messageId = ((Long)iter.next()).longValue();
                Message message = null;
                if (uid) {
                    message = maildirFolder.getMessageByUID(messageId);
                } else {
                    message = maildirFolder.getMessage((int)messageId);
                }
                // ignore null messages
                if ((message == null) &&
                        (sequenceSet.getSequenceSet().size() == 1)) {
                    this.write(out,commandNumber, ImapCommands.NO ,
                            "fetch error: can't fetch that data");
                    return;
                } else if (message != null) {
                    log.debug("Message : " + message.getMessageNumber());
                    handler.processMessage(maildirFolder,message);
                }
            }
            
            // write out message
            if (uid) {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "UID FETCH %s",ImapCommands.COMPLETED));
            } else {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "%s %s",command,ImapCommands.COMPLETED));
            }
        } catch (Exception ex) {
            log.error("Failed to process the fetch command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the fetch command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the store call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     * @param uid This is a uid request.
     */
    private void processStore(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token, boolean uid) throws ServerException {
        try {
            if (this.selected == null) {
                this.write(out,commandNumber, ImapCommands.NO ,
                        "fetch error: no selected box");
                return;
            }
            
            // message set.
            ImapSequenceSet sequenceSet = new ImapSequenceSet(token.nextToken());
            String flagCommand = token.nextToken();
            Flags flags = getFlags(token);
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)this.selected;
            
            // reset the last pos in the sequence
            Message lastMessage =
                    maildirFolder.getMessage(maildirFolder.getMessageCount());
            if (uid) {
                sequenceSet.setEnd(maildirFolder.getUID(lastMessage));
            } else {
                sequenceSet.setEnd(lastMessage.getMessageNumber());
            }
            
            // loop through all the opened folders
            for (Iterator iter = sequenceSet.getSequenceSet().iterator();
            iter.hasNext(); ){
                long messageId = ((Long)iter.next()).longValue();
                Message message = null;
                if (uid) {
                    message = maildirFolder.getMessageByUID(messageId);
                } else {
                    message = maildirFolder.getMessage((int)messageId);
                }
                if (message == null) {
                    continue;
                }
                if (flagCommand.equals("FLAGS") ||
                        flagCommand.equals("FLAGS.SILENT")) {
                    Flags messFlags = message.getFlags();
                    Flags.Flag[] flagList = messFlags.getSystemFlags();
                    for (int index = 0; index < flagList.length; index++) {
                        if (flagList[index] == Flags.Flag.RECENT) {
                            continue;
                        }
                        message.setFlag(flagList[index],false);
                    }
                    message.setFlags(flags,true);
                } else if (flagCommand.equals("+FLAGS") ||
                        flagCommand.equals("+FLAGS.SILENT")) {
                    message.setFlags(flags,true);
                } else if (flagCommand.equals("-FLAGS") ||
                        flagCommand.equals("-FLAGS.SILENT")) {
                    message.setFlags(flags,true);
                }
                log.debug("Message : " + message.getMessageNumber());
                if (!storeChanges.contains(message)) {
                    storeChanges.add(message);
                }
                if (!flagCommand.contains("SILENT")) {
                    printMessageFlags(out,messageId, message);
                }
            }
            
            // write out message
            if (uid) {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "UID STORE %s",ImapCommands.COMPLETED));
            } else {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "%s %s",command,ImapCommands.COMPLETED));
            }
        } catch (Exception ex) {
            log.error("Failed to process the store command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the store command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the copy call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     * @param uid This is a uid request.
     */
    private void processCopy(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token, boolean uid) throws ServerException {
        try {
            if (this.selected == null) {
                this.write(out,commandNumber, ImapCommands.NO ,
                        "fetch error: no selected box");
                return;
            }
            
            // message set.
            ImapSequenceSet sequenceSet = new ImapSequenceSet(token.nextToken());
            Folder folder =getFolder(out,commandNumber,
                    ImapUtils.parseQuotedString(token));
            if (folder == null) {
                return;
            }
            net.ukrpost.storage.maildir.MaildirFolder maildirFolder =
                    (net.ukrpost.storage.maildir.MaildirFolder)this.selected;
            
            // reset the last pos in the sequence
            Message lastMessage =
                    maildirFolder.getMessage(maildirFolder.getMessageCount());
            if (uid) {
                sequenceSet.setEnd(maildirFolder.getUID(lastMessage));
            } else {
                sequenceSet.setEnd(lastMessage.getMessageNumber());
            }
            
            // loop through all the opened folders
            for (Iterator iter = sequenceSet.getSequenceSet().iterator();
            iter.hasNext(); ){
                long messageId = ((Long)iter.next()).longValue();
                Message message = null;
                if (uid) {
                    message = maildirFolder.getMessageByUID(messageId);
                } else {
                    message = maildirFolder.getMessage((int)messageId);
                }
                if (message != null) {
                    maildirFolder.copyMessages(new Message[]{message},folder);
                }
            }
            
            // write out message
            if (uid) {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "UID COPY %s",ImapCommands.COMPLETED));
            } else {
                this.write(out,commandNumber, ImapCommands.OK , String.format(
                        "%s %s",command,ImapCommands.COMPLETED));
            }
        } catch (Exception ex) {
            log.error("Failed to process the copy command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the copy command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to process the uid call
     *
     * @param out The output writer.
     * @param in The input reader.
     * @param command The command to process.
     * @param argument The argument to process.
     */
    private void processUID(Writer out, BufferedReader in,
            String commandNumber, String command,
            StringTokenizer token) throws ServerException {
        try {
            if (this.selected == null) {
                this.write(out,commandNumber, ImapCommands.NO ,
                        "fetch error: no selected box");
                return;
            }
            
            // message set.
            String subCommand = token.nextToken();
            if (subCommand.equalsIgnoreCase(ImapCommands.SEARCH)) {
                processSearch(out, in, commandNumber, command, token, true);
            } else if (subCommand.equalsIgnoreCase(ImapCommands.FETCH)) {
                processFetch(out, in, commandNumber, command, token, true);
            } else if (subCommand.equalsIgnoreCase(ImapCommands.STORE)) {
                processStore(out, in, commandNumber, command, token, true);
            } else if (subCommand.equalsIgnoreCase(ImapCommands.COPY)) {
                processCopy(out, in, commandNumber, command, token, true);
            }
            
        } catch (Exception ex) {
            log.error("Failed to process the uid command : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to process the uid command : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves the list of flags
     */
    public Flags getFlags(StringTokenizer token) {
        Flags flags = new Flags();
        boolean processFlags = true;
        while(token.hasMoreTokens() && processFlags) {
            String tokenValue = token.nextToken();
            if (tokenValue.equals("(") || tokenValue.equals("(")) {
                continue;
            }
            
            if (tokenValue.indexOf("(") != -1) {
                tokenValue = tokenValue.replace("(","");
            }
            if (tokenValue.indexOf(")") != -1) {
                processFlags = false;
                tokenValue = tokenValue.replace(")","");
            }
            
            // process the flags
            if (tokenValue.equalsIgnoreCase("\\Answered")) {
                flags.add(Flags.Flag.ANSWERED);
            } else if (tokenValue.equalsIgnoreCase("\\Flagged")) {
                flags.add(Flags.Flag.FLAGGED);
            } else if (tokenValue.equalsIgnoreCase("\\Deleted")) {
                flags.add(Flags.Flag.DELETED);
            } else if (tokenValue.equalsIgnoreCase("\\Seen")) {
                flags.add(Flags.Flag.SEEN);
            } else if (tokenValue.equalsIgnoreCase("\\Draft")) {
                flags.add(Flags.Flag.DRAFT);
            } else if (tokenValue.equalsIgnoreCase("\\Recent")) {
                flags.add(Flags.Flag.RECENT);
            }
        }
        return flags;
    }
    
    
    /**
     * This method gets the folder with the specified name.
     *
     * @return The folder object.
     * @param out The socket writer.
     * @param commandNumber The command number to write to the socket
     * @param name The name of the folder to retrieve.
     */
    private Folder getFolder(Writer out, String commandNumber,
            String folderName) throws ServerException {
        try {
            Folder folder = null;
            if (folderName.equals("")) {
                this.write(out,commandNumber,ImapCommands.NO,String.format(
                        ImapCommands.INVALID_REQUEST,"No such mailbox"));
                return null;
            } else if (this.folders.containsKey(folderName)) {
                folder = (Folder)folders.get(folderName);
            } else {
                folder = store.getFolder(folderName);
                if (!folder.exists()) {
                    this.write(out,commandNumber,ImapCommands.NO,String.format(
                            ImapCommands.INVALID_REQUEST,"[TRY-CREATE] No such mailbox"));
                    return null;
                }
                this.folders.put(folderName,folder);
                try {
                    folder.open(Folder.READ_WRITE);
                } catch (Exception ex) {
                    log.error("Failed to open : " + ex.getMessage(),ex);
                    folder.open(Folder.READ_ONLY);
                }
            }
            return folder;
        } catch (Exception ex) {
            log.error("Failed to retrieve a folder : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to retrieve a folder : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to close all the open folders.
     */
    private void closeFolders() throws ServerException {
        try {
            // loop through all the opened folders
            for (Iterator iter = this.folders.keySet().iterator();
            iter.hasNext();) {
                Folder folder = (Folder)folders.get(iter.next());
                folder.close(true);
            }
            storeChanges.clear();
            folders.clear();
            this.selected = null;
        } catch (Exception ex) {
            log.error("Failed to close the folders : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to close the folders : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method strips the folder name
     *
     * @return A string containing the folder name.
     * @param folder The folder to parse.
     */
    private String parseFolderName(String folderName) {
        while (folderName.indexOf(".") == 0) {
            folderName = folderName.substring(1,folderName.length());
        }
        return folderName;
    }
    
    
    /**
     * This method prints out the flags for a message,
     *
     * @param out The out stream.
     * @param messageId The message id,
     * @param message The message
     * @exception ServerException
     */
    private void printMessageFlags(Writer out,long messageId, Message message)
    throws ServerException {
        try {
            StringBuffer flagBuffer = new StringBuffer().append("(");
            Flags.Flag[] flagList = message.getFlags().getSystemFlags();
            String sep = "";
            for (int index = 0; index < flagList.length; index++) {
                flagBuffer.append(sep).
                        append(ImapUtils.getFlag(flagList[index]));
                sep = " ";
            }
            flagBuffer.append(")");
            this.write(out,String.format("* %d FETCH %s", messageId,
                    flagBuffer.toString()));
        } catch (Exception ex) {
            log.error("Failed to print the flags for a message : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to print the flags for a message : " +
                    ex.getMessage(),ex);
        }
    }
}
