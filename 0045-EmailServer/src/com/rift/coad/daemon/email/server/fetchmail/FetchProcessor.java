/*
 * Email Server: The email server
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
 * Server.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Enumeration;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.Reader;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;



// log imports
import org.apache.log4j.Logger;

// java mail api
import javax.mail.*;
import javax.mail.internet.*;


// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.pool.Task;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;

// email server
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.config.ServerForward;
import com.rift.coad.daemon.email.server.config.ServerEmailbox;
import com.rift.coad.daemon.email.server.config.ServerForward;


/**
 * This object is responsible for processing a fetch request.
 *
 * @author brett chaldecott
 */
public class FetchProcessor implements Task {
    
    // class constants
    private final static String SMTP_SERVER_JNDI = "smtp_server_jndi";
    private final static String DEFAULT_SMTP_SERVER_JNDI =
            "java:comp/env/bean/email/SMTPServer";
    
    // private member variables
    private Logger log = Logger.getLogger(FetchProcessor.class);
    private Configuration config = null;
    private FetchEntry entry = null;
    
    
    /**
     * Creates a new instance of FetchProcessor
     *
     * @exception ServerException
     */
    public FetchProcessor() throws ServerException {
        try {
            config = ConfigurationFactory.getInstance().getConfig(
                    FetchProcessor.class);
            FetchMailManager.getInstance();
        } catch (Exception ex) {
            log.error("Failed to initialize fetch processor : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to initialize fetch processor : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The method that processes the request.
     */
    public void process(ThreadPoolManager pool) throws Exception {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            log.debug("Retrieve an entry");
            entry = FetchMailManager.getInstance().pop(transaction);
            log.debug("Retrieved an entry");
            transaction.commit();
            // the request manager has been terminated.
            if (entry == null) {
                return;
            }
            
        } catch (Exception ex) {
            log.error("Failed to retrieve a request : " + ex.getMessage(),ex);
            return;
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
        log.debug("Release another thread");
        pool.releaseThread();
        
        if (entry instanceof POPEntry) {
            log.debug("Process the request");
            processPop((POPEntry)entry);
            
        }
        
        // add the entries back in
        try {
            transaction.begin();
            log.debug("Add the entry back into the queue");
            FetchMailManager.getInstance().push(entry);
            log.debug("Finished with the entry commit the transaction");
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed to add the entry back : " + ex.getMessage(),ex);
        }
        log.debug("Release the transaction");
        transaction.release();
    }
    
    
    /**
     * This method process the prop requests
     */
    private void processPop(POPEntry entry) {
        Store store = null;
        try {
            Properties props = new Properties();
            Session session = Session.getInstance(props, null);
            store = session.getStore("pop3");
            store.connect(entry.getServer(), entry.getAccount(),
                    entry.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            int count = folder.getMessageCount();
            com.rift.coad.daemon.email.smtp.Server server = getServer();
            for (int index = 0; index < count; index++) {
                Message message = folder.getMessage(index + 1);
                Enumeration enumer = message.getAllHeaders();
                
                com.rift.coad.daemon.email.smtp.Message smtpMessage = 
                        new com.rift.coad.daemon.email.smtp.Message();
                while(enumer.hasMoreElements()) {
                    Header header = (Header)enumer.nextElement();
                    smtpMessage.addHeader(
                            new com.rift.coad.daemon.email.smtp.Header(
                            header.getName(),header.getValue()));
                }
                
                //maildirMessage.getRawInputStream().reset();
                InputStreamReader inStream = new InputStreamReader(
                        message.getInputStream());
                BufferedReader reader = new BufferedReader(inStream);
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while (((line = reader.readLine()) != null)  && 
                        (!line.equals("."))){
                    buffer.append(line).append("\r\n");
                }
                buffer.append(".\r\n");
                smtpMessage.setData(buffer.toString());
                processMessage(entry,smtpMessage,message);
            }
            folder.close(true);
        } catch (Exception ex) {
            log.error("Failed to pop mail : " + ex.getMessage(),ex);
            try {
                Server.getInstance().addError("[" + entry.getEmailAddress() + 
                    "] Failed to pop mail : " + ex.getMessage());
            } catch (Exception ex2) {
                log.error("Failed to add the error : " + ex2.getMessage(),ex2);
            }
        } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (Exception ex) {
                    log.error("Failed to close the pop mail store : " + 
                            ex.getMessage());
                }
            }
        }
    }
    
    
    /**
     * This method returns a reference to the server.
     *
     * @return The reference to the SMTP server.
     * @exception ServerException
     */
    private com.rift.coad.daemon.email.smtp.Server getServer() throws
            ServerException {
        try {
            Context context = new InitialContext();
            return (com.rift.coad.daemon.email.smtp.Server)context.lookup(
                    config.getString(SMTP_SERVER_JNDI,
                    DEFAULT_SMTP_SERVER_JNDI));
        } catch (Throwable ex) {
            log.error("Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to make a connection to the smtp server : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called process the delivery of an smtp messsage.
     *
     * @param smtpMessage A message to deliver.
     */
    private void processMessage(
            POPEntry entry,
            com.rift.coad.daemon.email.smtp.Message smtpMessage,
            Message message) throws
            ServerException {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            com.rift.coad.daemon.email.smtp.Server server = getServer();
            try {
                Address[] from = message.getFrom();
                List smtpFrom = new ArrayList();
                for (int index = 0; index < from.length; index++) {
                    smtpFrom.add(new com.rift.coad.daemon.email.types.Address(
                            from[index].toString()));
                }
                smtpMessage.setFrom(smtpFrom);
            } catch (Exception ex) {
                /// failed to process the from address
                log.warn("The from address in the header section of this " +
                        "message is invalid : " + ex.getMessage(),ex);
                List smtpFrom = new ArrayList();
                smtpFrom.add(new com.rift.coad.daemon.email.types.Address(
                            entry.getEmailAddress()));
                smtpMessage.setFrom(smtpFrom);
            }
            
            Address[] addresses = message.getAllRecipients();
            List targetList = new ArrayList();
            for (int index = 0; index < addresses.length; index++) {
                Address address = addresses[index];
                com.rift.coad.daemon.email.types.Address target = 
                        new com.rift.coad.daemon.email.types.Address(
                        address.toString());
                if (!ServerConfig.getInstance().getDomain().checkForLocalDomain(
                        target.getDomain())) {
                    if (entry.getDropBox() == null) {
                        log.error("The domain is not local and there is " +
                                "no drop box cannot deliver. [" + 
                                entry.getEmailAddress() + "]");
                        throw new ServerException(
                                "The domain is not local and there is " +
                                "no drop box cannot deliver.[" + 
                                entry.getEmailAddress() + "]");
                    }
                    target =  new com.rift.coad.daemon.email.types.Address(
                        entry.getDropBox());
                    if (!ServerConfig.getInstance().getDomain().checkForLocalDomain(
                        target.getDomain())) {
                        log.error("The domain for the drop box does not exist.[" + 
                                entry.getEmailAddress() + "][" + 
                                entry.getDropBox()+ "]");
                        throw new ServerException(
                                "The domain for the drop box does not exist.[" + 
                                entry.getEmailAddress() + "][" + 
                                entry.getDropBox()+ "]");
                    }
                }
                
                // check if the mailbox exists in some way
                // if it does not use the account drop box first and than
                // the domain drop box as a target.
                if (!checkForMailbox(target)) {
                    if (entry.getDropBox() != null) {
                        com.rift.coad.daemon.email.types.Address dropBox = 
                              new com.rift.coad.daemon.email.types.Address(
                              entry.getDropBox());
                        if (checkForMailbox(dropBox)) {
                            target = dropBox;
                        } else {
                            dropBox = new com.rift.coad.daemon.email.types.Address(
                                    ServerConfig.getInstance().getDomain().
                                    getDomainDropBox(target.getDomain()));
                            if (!checkForMailbox(dropBox)) {
                                continue;
                            }
                            target  = dropBox;
                        }
                    }
                    
                }
                
                // the target
                if (!targetList.contains(target)) {
                    targetList.add(target);
                }
            }
            smtpMessage.setRCPTs(targetList);
            server.sendMessage(smtpMessage);
            message.setFlag(Flags.Flag.DELETED,true);
            transaction.commit();
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.debug("Failed to process the message : " +
                    ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                try {
                    transaction.release();
                } catch (Exception ex) {
                    log.error("Failed to process the message : " +
                    ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method returns true if the mailbox exists locally.
     *
     * @return TRUE if found.
     * @param address The address to search for.
     * @exception ServerException
     */
    private boolean checkForMailbox(
            com.rift.coad.daemon.email.types.Address address) 
            throws ServerException {
        try {
            return ServerConfig.getInstance().getEmailbox().hasEmail(address) || 
                        ServerConfig.getInstance().getForward().
                        hasForward(address) || 
                        ServerConfig.getInstance().getAlias().
                        hasAlias(address);
        } catch (Exception ex) {
            log.error("Failed to check for a local mailbox : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to check for a local mailbox : " + 
                    ex.getMessage(),ex);
        }
    }
}
