/*
 * MessageService: The message service daemon
 * Copyright (C) 2006  2015 Burntjam
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
 * MessageServiceImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice;


// java imports
import com.rift.coad.lib.thread.pool.PoolException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.transaction.UserTransaction;
import javax.transaction.TransactionManager;
import javax.transaction.Transaction;

// logging import
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;


// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.message.MessageManagerImpl;
import com.rift.coad.daemon.messageservice.named.NamedMemoryQueue;
/**
 * The implementation of the Message Service management interface.
 *
 * @author Brett Chaldecott
 */
public class MessageServiceManager implements MessageServiceManagerMBean, BeanRunnable {
    
    // class constants
    private final static String THREAD_POOL_SIZE = "thread_pool_size";
    private final static int DEFAULT_THREAD_POOL_SIZE = 10;
    private final static String THREAD_POOL_USER = "thread_pool_user";
    
    // the logger reference
    protected Logger log =
        Logger.getLogger(MessageServiceManager.class.getName());
    
    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private Configuration config = null;
    private Context context = null;
    private ThreadPoolManager threadPoolManager = null;
    private UserTransactionWrapper utw = null;
    private List initialEntries = null;
    
    /** 
     * Creates a new instance of MessageServiceImpl
     *
     * @exception MessageServiceException
     */
    public MessageServiceManager() throws MessageServiceException {
        try {
            config = ConfigurationFactory.getInstance().getConfig(
                    MessageServiceManager.class);
            context = new InitialContext();
            utw = new UserTransactionWrapper();
            log.info("Reading in and applying change log information, " +
                    "this may take some time");
            ChangeLog.init(MessageServiceManager.class);
            initialEntries = getDbMessageList();
            threadPoolManager = new ThreadPoolManager((int)
                    config.getLong(THREAD_POOL_SIZE,DEFAULT_THREAD_POOL_SIZE), 
                    MessageProcessor.class, config.getString(THREAD_POOL_USER));
        } catch (Exception ex) {
            log.error("Failed to instanciate the " +
                    "message service : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to instanciate the " +
                    "message service : " + ex.getMessage(),ex);
        }
        
    }
    
    
    /**
     * This method returns the thread pool size.
     *
     * @return The size of the thread pool.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public int getThreadPoolSize() throws RemoteException, 
            MessageServiceException {
        return threadPoolManager.getSize();
    }
    
    
    /**
     * This method sets the size of the thread pool.
     *
     * @param size The new size of the thread pool.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void setThreadPoolSize(int size) throws RemoteException, 
            MessageServiceException {
        try {
            threadPoolManager.setSize(size);
        } catch (Exception ex) {
            log.error("Failed to set the size : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to set the size : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists the named queues.
     *
     * @return The list of named queues.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public List listNamedQueues() throws RemoteException, 
            MessageServiceException {
        return NamedMemoryQueue.listQueues();
    }
    
    
    /**
     * This method returns the list of messages in the named queue.
     *
     * @return The list of messages for this queue.
     * @param queueName The name of the queue to list messages for.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public List listMessagesForNamedQueue(String queueName) throws 
            RemoteException, MessageServiceException {
        List namedQueues = NamedMemoryQueue.listQueues();
        if (!namedQueues.contains(queueName)) {
            throw new MessageServiceException("The queue [" + queueName + 
                    "] does not exist.");
        }
        return NamedMemoryQueue.getInstance(queueName).getMessages();
    }
    
    
    /**
     * This purges the messages from the named queue
     *
     * @param queueName The name of the queue to purge.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void purgeNamedQueue(String queueName) throws RemoteException,
            MessageServiceException {
        List namedQueues = NamedMemoryQueue.listQueues();
        if (!namedQueues.contains(queueName)) {
            throw new MessageServiceException("The queue [" + queueName + 
                    "] does not exist.");
        }
        NamedMemoryQueue.getInstance(queueName).purge();
    }
    
    
    /**
     * This method is responsible for performing the processing.
     */
    public void process() {
        try {
            for (Iterator iter = initialEntries.iterator(); iter.hasNext();) {
                String messageId = (String)iter.next();
                try {
                    log.info("Load message : " + messageId);
                    utw.begin();
                    Session session = HibernateUtil.getInstance(
                            MessageServiceManager.class).getSession();
                    com.rift.coad.daemon.messageservice.db.Message message =
                            (com.rift.coad.daemon.messageservice.db.Message)
                            session.get(com.rift.coad.daemon.messageservice.
                            db.Message.class,messageId);
                    MessageManager messageManager = 
                            MessageManagerFactory.getInstance().
                            getMessageManager(messageId);
                    MessageQueue messageQueue = MessageQueueManager.getInstance().
                            getQueue(MessageQueueManager.UNSORTED);
                    messageQueue.addMessage(messageManager);
                    utw.commit();
                    ProcessMonitor.getInstance().notifyProcessor();
                } catch (Exception ex) {
                    log.error("Failed to retrieve the message : " + 
                            ex.getMessage(),ex);
                } finally {
                    utw.release();
                }
            }
            try {
                ChangeLog.getInstance().start();
            } catch (Exception ex) {
                log.error("Failed to start the change log processing : "
                        + ex.getMessage(),ex);
            }
            while(!state.isTerminated()) {
                
                // wait indefinitly
                state.monitor();
            }
        } catch (Exception ex) {
            log.error("The processing failed in the message service because : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to terminate the processing of this object.
     */
    public void terminate() {
        try {
            threadPoolManager.terminate();
        } catch (PoolException ex) {
            log.error("Failed to terminate the thread pool : " + ex.getMessage(),
                    ex);
        }
        state.terminate(true);
        try {
            ProcessMonitor.getInstance().terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the processor: " + ex.getMessage(),
                    ex);
        }
        try {
            log.info("Waiting for all changes to be dumped");
            ChangeLog.terminate();
            log.info("Changes have been dumped.");
        } catch (Exception ex) {
            log.error("Failed to shut down the change log : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list db entries.
     */
    private List getDbMessageList() {
        boolean startedTransaction = false;
        List dbEntries = new ArrayList();
        try {
            utw.begin();
            startedTransaction = true;
            Session session = HibernateUtil.getInstance(
                    MessageServiceManager.class).getSession();
            List messages = session.createQuery(
                    "FROM Message as message").list();
            for (Iterator iter = messages.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.messageservice.db.Message msg = 
                        (com.rift.coad.daemon.messageservice.db.Message)
                        iter.next();
                dbEntries.add(msg.getId());
            }
            
            utw.commit();
            startedTransaction = false;
        } catch (Exception ex) {
            log.error("Failed to load the list of messages from the db : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
        return dbEntries;
    }
}
