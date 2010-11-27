/*
 * Email Server: The email server interface
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
 * ServerImpl.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Set;
import java.util.Date;

// log4j imports
import org.apache.log4j.Logger;

// james imports
import org.apache.mailet.RFC2822Headers;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.CoadunationHashMap;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.deployment.DeploymentMonitor;

// message imports
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

// email imports
import com.rift.coad.daemon.email.smtp.Handler;
import com.rift.coad.daemon.email.smtp.Message;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.SMTPException;
import com.rift.coad.daemon.email.smtp.Server;
import com.rift.coad.daemon.email.types.Address;
import com.rift.coad.daemon.email.smtp.MessageInfo;
import com.rift.coad.daemon.email.server.ServerConfig;
import com.rift.coad.daemon.email.server.smtp.route.RouteManager;
import com.rift.coad.daemon.email.server.smtp.route.RouteEntry;
import com.rift.coad.daemon.email.server.db.SpoolAddress;
import com.rift.coad.daemon.email.server.db.SpoolHeader;
import com.rift.coad.daemon.email.server.db.SpoolMessage;

/**
 * The implementation of the SMTP server.
 *
 * @author brett chaldecott
 */
public class ServerImpl implements Server, BeanRunnable {
    
    // class constants
    private final static String SERVER_HOST = "host";
    private final static String SHUTDOWN_TIMEOUT = "shutdown_timeout";
    private final static String SERVER_BASE = "email_server_base";
    private final static String SERVER_ROUTING_INFO = "server_routing_info";
    private final static String DEFAULT_SERVER_ROUTING_INFO = File.separator 
            + "route.xml";
    private final static String SERVER_PERSISTANCE_DIR = 
            "server_persistance_dir";
    private final static String DEFAULT_SERVER_PERSISTANCE_DIR = File.separator 
            + "spool" + File.separator;
    private final static String SERVER_SMTP_JNDI = "email_server_jndi";
    private final static String DEFAULT_DEFAULT_SMTP_JNDI = "email/SMTPServer";
    private final static long DEFAULT_SHUTDOWN_TIMEOUT = 30000;
    private final static String MAX_RETRIES = "email_max_retries";
    private final static long DEFAULT_MAX_RETRIES = 100;
    private final static String MAX_RECEIVED = "email_max_received";
    private final static long DEFAULT_MAX_RECEIVED = 50;
    private final static String REVERSE_MESSAGE = "reverse_message";
    
    
    
    // private static variables
    private static Logger log = Logger.getLogger(ServerImpl.class);
    
    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private String hostname = null;
    private SMTPMessageManager manager = null;
    private SMTPMessageQueue queue = null;
    private SMTPProcessingList smtpProcessingList = new SMTPProcessingList();
    private long shutdownTimeout = DEFAULT_SHUTDOWN_TIMEOUT;
    private String serverBase = null;
    private RouteManager routeManager = null;
    private String returnJNDI = null;
    private long maxRetries = 0;
    private long maxReceived = 0;
    private String reverseMessage = null;
    private String persistancyDir = null;
    
    /**
     * Creates a new instance of ServerImpl
     */
    public ServerImpl() throws SMTPException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(com.rift.coad.daemon.email.server.Server.class);
            hostname = config.getString(SERVER_HOST);
            manager = new SMTPMessageManager();
            queue = new SMTPMessageQueue();
            shutdownTimeout = config.getLong(SHUTDOWN_TIMEOUT,
                    DEFAULT_SHUTDOWN_TIMEOUT);
            serverBase = config.getString(SERVER_BASE);
            routeManager = new RouteManager(serverBase +
                    config.getString(SERVER_ROUTING_INFO,
                    DEFAULT_SERVER_ROUTING_INFO));
            returnJNDI = config.getString(SERVER_SMTP_JNDI,
                    DEFAULT_DEFAULT_SMTP_JNDI);
            maxRetries = config.getLong(MAX_RETRIES,DEFAULT_MAX_RETRIES);
            maxReceived = config.getLong(MAX_RECEIVED,DEFAULT_MAX_RECEIVED);
            reverseMessage = config.getString(REVERSE_MESSAGE);
            persistancyDir = config.getString(SERVER_PERSISTANCE_DIR,
                    DEFAULT_SERVER_PERSISTANCE_DIR);
        } catch (Exception ex) {
            log.error("Failed to instanciate the server : " +
                    ex.getMessage(),ex);
            throw new SMTPException("Failed to instanciate the server : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sends messages.
     *
     * @param message The message to get processed.
     * @exception SMTPException
     */
    public void sendMessage(Message message) throws SMTPException {
        
        if (state.isTerminated()) {
            throw new SMTPException(
                    "Server shutting down will not accept any more messages");
        }
        
        // count the headers
        List headers = message.getHeaders();
        int count = 0;
        for (int index = 0; index < headers.size(); index++) {
            Header current = (Header)headers.get(index);
            if (current.getKey().equalsIgnoreCase(RFC2822Headers.RECEIVED)) {
                count++;
            }
        }
        // check if the count has exceeded max received.
        // if it has drop the message.
        if (count >= maxReceived) {
            // drop message
            log.warn("A message is being drop for exceeding its max " +
                    "received count");
            return;
        }
        
        // set the necessary headers
        message.addHeader(new Header(RFC2822Headers.RECEIVED,
                String.format(
                "Request received for [%s] Coadunation SMTP Service [%s]",
                hostname, SMTPUtils.getDateString())));
        
        // If headers do not contains minimum REQUIRED headers fields,
        // add them
        if (!message.hasHeader(RFC2822Headers.DATE)) {
            message.addHeader(new Header(RFC2822Headers.DATE,
                    SMTPUtils.getDateString()));
        }
        if (!message.hasHeader(RFC2822Headers.FROM) &&
                (message.getFrom().size() > 0)) {
            message.addHeader(new Header(RFC2822Headers.FROM,
                    message.getFrom().get(0).toString()));
        }
        
        // log the message
        log.debug("Message received : " + message.toString());
        
        
        // send the message to all the recipients
        try {
            log.debug("Dispatch the message to all recipients : " +
                    message.toString());
            RouteEntry entry = routeManager.getBase();
            HandlerAsync handler = (HandlerAsync)RPCMessageClient.create(
                    returnJNDI,Handler.class,
                    HandlerAsync.class,entry.getJNDI());
            for (int index = 0; index < message.getRCPTs().size(); index++) {
                List rcpt = new ArrayList();
                rcpt.add(message.getRCPTs().get(index));
                SMTPServerMessage smtpMessage = new SMTPServerMessage(
                        new Message(
                        RandomGuid.getInstance().getGuid(),
                        Message.UNKNOWN, message.getFrom(), rcpt,
                        message.getHeaders(),message.getData()));
                manager.addMessage(smtpMessage);
                String messageServiceId = handler.process(
                        smtpMessage.getMessage().getInfo());
                smtpMessage.setMessageServiceId(messageServiceId);
                smtpMessage.setRouteName(entry.getName());
                smtpProcessingList.addMessage(smtpMessage);
                log.debug("Added message [" + smtpMessage.getId() 
                + "] message service id [" + messageServiceId + "]");
            }
        } catch (Throwable ex) {
            log.error("Failed to send the message : " + ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to send the message : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the message based on the id passed in.
     *
     * @return The message to process.
     * @param id The
     */
    public Message getMessage(String id) throws SMTPException {
        try {
            return manager.getMessage(id).getMessage();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the message : " + ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed to retrieve the message : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for processing requests
     */
    public void process() {
        log.info("Restore the persisted messages");
        restoreMessages();
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
        } catch (Exception ex) {
            log.fatal("Failed to start a transaction : " + ex.getMessage(),ex);
            return;
        }
        try {
            DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        } catch (Throwable ex) {
            log.error("Failed to wait for initial deployment : " + 
                    ex.getMessage(),ex);
        }
        log.info("Start processing messages after waiting for initial " +
                "deployment to complete");
        while(!state.isTerminated()) {
            try {
                SMTPServerMessage smtpMessage = queue.pop(transaction);
                if (smtpMessage == null) {
                    log.info("Retrieve a null pointer from the queue");
                    continue;
                }
                RouteEntry entry = routeManager.getEntry(
                        smtpMessage.getRouteName());
                HandlerAsync handler = (HandlerAsync)RPCMessageClient.create(
                        returnJNDI,Handler.class,
                        HandlerAsync.class,entry.getJNDI());
                String messageServiceId = handler.process(
                        smtpMessage.getMessage().getInfo());
                log.info("Added message [" + smtpMessage.getId() 
                + "] message service id [" + messageServiceId + "]");
                smtpMessage.setMessageServiceId(messageServiceId);
                smtpProcessingList.addMessage(smtpMessage);
                transaction.commit();
            } catch (Throwable ex) {
                log.error("Failed to process the message : " + ex.getMessage(),
                        ex);
            } finally {
                try {
                    transaction.release();
                } catch (Throwable ex) {
                    log.error("Failed to release the transaction : " +
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method is called to terminate this thread.
     */
    public void terminate() {
        
        log.info("Terminate the SMTP server");
        state.terminate(true);
        try {
            this.queue.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the queue.");
        }
        try {
            log.info("Check the processing list");
            smtpProcessingList.checkList(shutdownTimeout);
        } catch (Exception ex) {
            log.error("Failed to wait for processing message : " +
                    ex.getMessage(),ex);
        }
        // this method is called to persist the message
        log.info("Persist the messages to the database");
        persistMessages();
    }
    
    
    /**
     * This method is called to process successful results.
     *
     * @param messageId The id of the message.
     * @param correllationId The id to corrolate this message internally.
     * @param result The result of the call.
     * @exception RemoteException
     */
    public void onSuccess(String messageId,
            String correllationId, Object result) throws RemoteException {
        try {
            MessageInfo info = (MessageInfo)result;
            SMTPServerMessage smtpMessage = 
                    smtpProcessingList.getMessage(messageId);
            if (smtpMessage == null) {
                log.error("The message " + messageId + "does not exist");
                return;
            }
            smtpProcessingList.removeMessage(messageId);
            smtpMessage.getMessage().updateInfo(info);
            RouteEntry entry = routeManager.getEntry(
                        smtpMessage.getRouteName());
            entry = entry.getEntry(info);
            if (entry == null) {
                log.info("Message delivered : " + smtpMessage.getId());
                this.manager.removeMessage(smtpMessage.getId());
                return;
            }
            smtpMessage.setRouteName(entry.getName());
            smtpMessage.resetRetries();
            if (!state.isTerminated()) {
                HandlerAsync handler = (HandlerAsync)RPCMessageClient.create(
                        returnJNDI,Handler.class,
                        HandlerAsync.class,entry.getJNDI());
                String messageServiceId = handler.process(
                        smtpMessage.getMessage().getInfo());
                smtpMessage.setMessageServiceId(messageServiceId);
                log.info("Added message [" + smtpMessage.getId() 
                + "] message service id [" + messageServiceId + "]");
                smtpProcessingList.addMessage(smtpMessage);
            } else {
                smtpMessage.setMessageServiceId(null);
                queue.add(smtpMessage);
            }
        } catch (Throwable ex) {
            log.error("Failed to process the message [" + messageId + "] :" +
                    ex.getMessage(),ex);
            throw new RemoteException(
                    "Failed to process the message [" + messageId + "] :" +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called when there is an error will processing the message.
     *
     * @param messageId The unque id of the message.
     * @param correllationId The id of the message.
     * @param caught The exception that got caught.
     * @exception RemoteException
     */
    public void onFailure(String messageId,
            String correllationId, Throwable caught) throws RemoteException {
        try {
            SMTPServerMessage smtpMessage = 
                    smtpProcessingList.getMessage(messageId);
            if (smtpMessage == null) {
                log.error("The message does not exist [" + messageId + "]");
                return;
            }
            smtpProcessingList.removeMessage(messageId);
            long retries = smtpMessage.incrementRetries();
            if (retries > maxRetries) {
                reverseMessage(smtpMessage,
                        "Max Number of internal server retries reached. " +
                        "Message undeliverable");
                return;
            }
            log.error("Failed to process the message [" + 
                    smtpMessage.getId() + "] because : " + caught.getMessage(),
                    caught);
            this.queue.add(smtpMessage);
        } catch (Throwable ex) {
            log.error("Failed to process the on failure message [" 
                    + messageId + "] :" + ex.getMessage(),ex);
            throw new RemoteException(
                    "Failed to process the on failure message [" 
                    + messageId + "] :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to persist all the active message to the database.
     */
    private void persistMessages() {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session = HibernateUtil.getInstance(ServerImpl.class).
                    getSession();
            session.createSQLQuery("DELETE FROM SpoolHeader").executeUpdate();
            session.createSQLQuery("DELETE FROM SpoolAddress").executeUpdate();
            session.createSQLQuery("DELETE FROM SpoolMessage").executeUpdate();
            purgeDir();
            Map list = this.manager.getList();
            for (Iterator iter = list.keySet().iterator(); iter.hasNext();) {
                Object key = iter.next();
                SMTPServerMessage serverMessage = (SMTPServerMessage)list.get(key);
                String id = serverMessage.getId();
                String dataPath = this.serverBase + this.persistancyDir + 
                        File.separator + id;
                Message message = serverMessage.getMessage();
                
                // write the file to the directory
                File dataFile = new File(dataPath);
                FileOutputStream out = new FileOutputStream(dataFile);
                out.write(message.getData().getBytes());
                out.flush();
                out.close();
                
                // persist the message to the database
                log.info("Persist message [" + id + "]");
                SpoolMessage spoolMessage = new SpoolMessage(id, 
                        new Integer(message.getType()), 
                        ((Address)message.getRCPTs().get(0)).getAddress(),
                        dataPath,new java.sql.Timestamp(
                        serverMessage.getRetryDate().getTime()), 
                        new Integer(serverMessage.getRetries()),
                        serverMessage.getRouteName());
                session.persist(spoolMessage);
                List from = message.getFrom();
                for (int index = 0; index < from.size(); index++) {
                    Address address = (Address)from.get(index);
                    SpoolAddress spoolAddress = new SpoolAddress(
                            spoolMessage, address.getAddress());
                    session.persist(spoolAddress);
                }
                List headers = message.getHeaders();
                for (int index = 0; index < headers.size(); index++) {
                    Header header = (Header)headers.get(index);
                    SpoolHeader spoolHeader = new SpoolHeader(
                            spoolMessage, header.getKey(), header.getValue());
                    session.persist(spoolHeader);
                }
            }
            
            // commit the databse changes
            transaction.commit();
            transaction.release();
            
            // free up the memory this has to be done speratly because the clear
            // call places a write lock on the object.
            transaction.begin();
            list.clear();
            queue.clear();
            transaction.commit();
            transaction.release();
            
            
        } catch (Throwable ex) {
            log.fatal("Failed to persist the server SMTP information because : "
                    + ex.getMessage(),ex);
        } finally {
            try {
                if (transaction != null) {
                    transaction.release();
                }
            } catch (Exception ex) {
                log.error("Failed to release the database changes : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method is responsible for restoring all the messages that will be
     * processed by this server.
     */
    private void restoreMessages() {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session = HibernateUtil.getInstance(ServerImpl.class).
                    getSession();
            List list = session.createQuery(
                    "FROM SpoolMessage as message ORDER BY message.retryDate").
                    list();
            log.info("There are [" + list.size() + "] messages to load");
            Date retryTime = new Date(new Date().getTime() - 
                    this.queue.getRetryTime());
            for (int index = 0; index < list.size(); index++) {
                SpoolMessage spoolMessage = (SpoolMessage)list.get(index);
                log.info("Load message [" + spoolMessage.getId() + "]");
                
                // write the file to the directory
                File dataFile = new File(spoolMessage.getDataPath());
                FileInputStream in = new FileInputStream(dataFile);
                byte[] bytes = new byte[(int)dataFile.length()];
                in.read(bytes);
                in.read();
                
                // from
                List from = new ArrayList();
                Set fromSet = spoolMessage.getFrom();
                for (Iterator iter = fromSet.iterator(); iter.hasNext();) {
                    SpoolAddress spoolAddress = (SpoolAddress)iter.next();
                    from.add(new Address(spoolAddress.getValue()));
                }
                
                // rcpt
                List rcpt = new ArrayList();
                rcpt.add(new Address(spoolMessage.getRcpt()));
                
                // headers
                List headers = new ArrayList();
                Set headersSet = spoolMessage.getHeaders();
                for (Iterator iter = headersSet.iterator(); iter.hasNext();) {
                    SpoolHeader spoolHeader = (SpoolHeader)iter.next();
                    headers.add(new Header(spoolHeader.getKey(),
                            spoolHeader.getValue()));
                }
                
                Message message = new Message(spoolMessage.getId(), 
                        spoolMessage.getType(), from, rcpt, headers,
                        new String(bytes));
                SMTPServerMessage serverMessage = new SMTPServerMessage(
                        message, null, retryTime, 
                        spoolMessage.getRetries(), spoolMessage.getRouteName());
                manager.addMessage(serverMessage);
                queue.add(serverMessage);
            }
            
            
            transaction.commit();
        } catch (Exception ex) {
            log.fatal("Failed to restore the server SMTP information because : "
                    + ex.getMessage(),ex);
        } finally {
            try {
                if (transaction != null) {
                    transaction.release();
                }
            } catch (Exception ex) {
                log.error("Failed to release the database changes : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method purges the dir
     */
    private void purgeDir() {
        try {
            File[] files = new File(this.serverBase + this.persistancyDir).
                    listFiles();
            for (int index = 0;(files != null) && 
                    (index < files.length); index++) {
                if (!files[index].isDirectory()) {
                    files[index].delete();
                }
            }
        } catch (Exception ex) {
            log.error("Failed to purge the perstancy directory :" 
                    + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method reverses a messages path.
     *
     * @param smtpMessage The smtp message.
     * @param cause The cause of the cause reversal.
     */
    private void reverseMessage(SMTPServerMessage smtpMessage, String cause) 
    throws SMTPException {
        try {
            Message message = smtpMessage.getMessage();
            List from = message.getFrom();
            List headers = message.getHeaders();
            message.setData(String.format(reverseMessage,cause));
            message.setRCPTs(from);
            this.sendMessage(message);
        } catch (Throwable ex) {
            log.error("Failed reverse the message : " + ex.getMessage(),ex);
            throw new SMTPException(
                    "Failed reverse the message : " + ex.getMessage(),ex);
        }
    }
}
