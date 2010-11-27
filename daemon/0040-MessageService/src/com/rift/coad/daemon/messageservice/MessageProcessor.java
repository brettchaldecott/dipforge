/*
 * MessageService: The message service daemon
 * Copyright (C) 2007  Rift IT Contracting
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
 * MessageProcessor.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import com.rift.coad.daemon.messageservice.named.NamedQueueManagerImpl;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.message.MessageImpl;
import com.rift.coad.daemon.messageservice.message.RPCMessageImpl;
import com.rift.coad.daemon.messageservice.message.MessageManagerImpl;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.named.NamedMemoryQueue;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.NamingConstants;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.interceptor.credentials.Session;
import com.rift.coad.lib.thread.pool.Task;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;
import com.rift.coad.lib.thread.pool.PoolException;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.deployment.bean.BeanConnector;
import com.rift.coad.lib.deployment.bean.BeanManager;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanConnector;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanManager;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;


/**
 * This object is responsible for processing the message that are sent to the
 * message service.
 *
 * @author Brett Chaldecott
 */
public class MessageProcessor extends InterceptorWrapper implements Task  {
    
    // class constants
    private final static long BACK_OFF_PERIOD = 1000;
    private final static String RETRY_DELAY = "retry_delay";
    private final static long DEFAULT_RETRY_DELAY = 60000;
    private final static String PARENT_INSTANCE = "../";
    private final static String MAX_RETRIES = "max_retries";
    private final static long DEFAULT_MAX_RETRIES = 100;
    private final static String TRANSACTION_TIMEOUT = "transaction_timeout";
    
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(MessageProcessor.class.getName());
    
    // private member variables
    private Context context = null;
    private UserTransactionWrapper utw = null;
    private MessageProcessInfo messageProcessInfo = null;
    private NamingDirector namingDirector = null;
    private long delay = 0;
    private long maxRetries = 0;
    
    /**
     * Creates a new instance of MessageProcessor
     *
     * @exception Exception
     */
    public MessageProcessor() throws Exception {
        try {
            context = new InitialContext();
            namingDirector = NamingDirector.getInstance();
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(MessageProcessor.class);
            delay = config.getLong(RETRY_DELAY,DEFAULT_RETRY_DELAY);
            maxRetries = config.getLong(MAX_RETRIES,DEFAULT_MAX_RETRIES);
            utw = new UserTransactionWrapper();
            
        } catch (Exception ex) {
            log.error("Failed init the message processor : " + ex.getMessage(),
                    ex);
            throw new Exception("Failed init the message processor : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The implementation of the process method used by the coadunation
     * threading pool.
     *
     * @param poolManager The reference to the thread pool manager.
     * @exception Exception
     */
    public void process(ThreadPoolManager poolManager) throws Exception {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        if (DeploymentMonitor.getInstance().isTerminated()) {
            return;
        }
        boolean foundMessage = getMessageManager();
        poolManager.releaseThread();
        if (foundMessage) {
            processMessage();
        }
    }
    
    
    /**
     * This method retrieves the next message from the message queue manager
     * for processing.
     *
     * @return True if a message was found false if not.
     */
    private boolean getMessageManager() {
        try {
            Date currentTime = new Date();
            Date delayTime = new Date();
            messageProcessInfo = MessageQueueManager.
                    getInstance().getNextMessage(currentTime);
            if (messageProcessInfo != null) {
                return true;
            }
            long difference = delayTime.getTime() - currentTime.getTime();
            if ((delayTime.getTime() == currentTime.getTime()) ||
                    (difference > BACK_OFF_PERIOD) || (difference < 0)) {
                ProcessMonitor.getInstance().monitor(BACK_OFF_PERIOD);
            } else {
                ProcessMonitor.getInstance().monitor(difference);
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve a message : " + ex.getMessage(),ex);
        }
        return false;
    }
    
    
    /**
     * This method is called to process a message.
     */
    private void processMessage() {
        Message message = null;
        try {
            message = getMessage();
            if (message.getState() == Message.UNDELIVERED) {
                processUndelivered(message);
            } else if (message.getState() == Message.DELIVERED) {
                processDelivered(message);
            } else if (message.getState() == Message.UNDELIVERABLE) {
                processUndeliverable(message);
            }
        } catch (Exception ex) {
            log.error("Failed to process the message : " + ex.getMessage(),ex);
            if (message == null) {
                pushMessage(messageProcessInfo);
            } else {
                pushMessage(message,messageProcessInfo);
            }
        }
    }
    
    
    /**
     * This method returns the message contained within.
     *
     * @return Message The message.
     * @exception MessageServiceException
     */
    private Message getMessage() throws MessageServiceException {
        Message message = null;
        try {
            utw.begin();
            MessageManager messageManager = messageProcessInfo.
                    getMessageManager();
            message = messageManager.getMessage();
            utw.commit();
        } catch (Exception ex) {
            log.error("Failed to retrieve the message : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to retrieve the message : " + ex.getMessage(),ex);
        } finally {
            utw.release();
        }
        
        return message;
    }
    
    
    /**
     * This method will process the message.
     *
     * @param message The message to process.
     * @exception MessageServiceException
     */
    private void processUndelivered(Message message) throws
            MessageServiceException {
        try {
            if (message.getMessageType() == Message.POINT_TO_POINT) {
                if (checkIfTargetLocal(message) &&
                        checkIfMessageInQueue(message)) {
                    deliverMessage(message);
                }
            } else if (message.getMessageType() == Message.POINT_TO_SERVICE) {
                if (checkIfServiceLocal(message) &&
                        checkIfMessageInQueue(message)) {
                    deliverMessage(message);
                }
            } else if (message.getMessageType() ==
                    Message.POINT_TO_MULTI_SERVICE) {
                if (!namingDirector.isPrimary()) {
                    deliverToParent(message);
                } else {
                    cloneMessageForServices(message);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to process the undelivered message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to process the undelivered message : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will process the message.
     *
     * @param message The message to process.
     * @exception MessageServiceException
     */
    private void processDelivered(Message message) throws
            MessageServiceException {
        try {
            if (checkIfReplyLocal(message) &&
                    checkIfReplyMessageInQueue(message)) {
                deliverReplyMessage(message);
            }
        } catch (Exception ex) {
            log.error("Failed to process the undelivered message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to process the undelivered message : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will process the undeliverable message.
     *
     * @param message The message to process.
     * @exception MessageServiceException
     */
    private void processUndeliverable(Message message) throws
            MessageServiceException {
        try {
            if (checkIfReplyLocal(message)) {
                utw.begin();
                if (NamedQueueManagerImpl.getInstance().checkForNamedQueue(
                        MessageQueueManager.DEAD_LETTER,true)) {
                    log.info("Assign message to dead letter queue.");
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                    ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                            assignToQueue(MessageQueueManager.DEAD_LETTER);
                    NamedMemoryQueue.getInstance(
                            MessageQueueManager.DEAD_LETTER).
                            addMessage(messageProcessInfo.getMessageManager());
                    log.info("Added the value to the dead letter queue");
                } else {
                    log.error("Failed to add to the dead letter queue.");
                }
                utw.commit();
            }
        } catch (Exception ex) {
            log.error("Failed to process the Undeliverable message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to process the Undeliverable message : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method pushes the message back onto the queue from which it was
     * retrieved.
     *
     * @param messageProcessInfo The processing information for this thread.
     */
    private void pushMessage(MessageProcessInfo messageProcessInfo) {
        try {
            messageProcessInfo.getMessageQueue().pushBackMessage(
                    messageProcessInfo.getMessageManager());
            ProcessMonitor.getInstance().notifyProcessor();
        } catch (Exception ex) {
            log.error("Failed to push the message manager onto a queue : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method pushes the message back onto the queue from which it was
     * retrieved.
     *
     * @param message The message to push back.
     * @param messageProcessInfo The processing information for this thread.
     */
    private void pushMessage(Message message,
            MessageProcessInfo messageProcessInfo) {
        try {
            try {
                utw.begin();
                Date nextDate = new Date();
                nextDate.setTime(nextDate.getTime() + delay);
                ((MessageImpl)message).setNextProcessDate(nextDate);
                messageProcessInfo.getMessageManager().updateMessage(message);
                utw.commit();
            } catch (Exception ex) {
                log.error("Failed to process the message : " +
                        ex.getMessage(),ex);
            } finally {
                utw.release();
            }
            messageProcessInfo.getMessageQueue().pushBackMessage(
                    messageProcessInfo.getMessageManager());
            ProcessMonitor.getInstance().notifyProcessor();
        } catch (Exception ex) {
            log.error("Failed to push the message manager onto a queue : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method checks if the target this message is going to is local to
     * this Coadunation Instance.
     *
     * @return TRUE if local, FALSE if not.
     * @param message The message to perform the test on.
     */
    private boolean checkIfTargetLocal(Message message) throws
            MessageServiceException {
        try {
            String target = message.getTarget();
            if (target == null) {
                message.addError(Message.ERROR,
                        "There is no target for this message");
                initUndeliverableProcess(message);
                return false;
            }
            
            // check if this fall withing this part of the tree or below
            String jndiBase = NamingDirector.getInstance().getJNDIBase() + "/";
            String parentUrl = NamingDirector.getInstance().getPrimaryJNDIUrl();
            String instanceURL = NamingDirector.getInstance().
                    getInstanceId() + "/";
            int pos = target.indexOf(jndiBase);
            int instancePos = target.indexOf(instanceURL);
            if ((((target.indexOf(parentUrl)) != -1) &&
                    (target.indexOf(jndiBase) == -1)) ||
                    (target.indexOf(PARENT_INSTANCE) == 0) ||
                    ((target.indexOf(NamingConstants.JNDI_NETWORK_PREFIX) == 0) &&
                    !NamingDirector.getInstance().isPrimary())) {
                deliverToParent(message);
                return false;
            } else if (((instancePos != -1) && (
                    target.indexOf(NamingConstants.SUBCONTEXT,(instancePos +
                    instanceURL.length())) != -1)) ||
                    (target.indexOf(NamingConstants.SUBCONTEXT) == 0)) {
                deliverToChild(message.getTarget(),message);
                return false;
            } else if (instancePos != -1) {
                utw.begin();
                target = target.substring(instancePos + instanceURL.length());
                message.setTarget(target);
                messageProcessInfo.getMessageManager().updateMessage(message);
                utw.commit();
                messageProcessInfo.getMessageQueue().pushBackMessage(
                        messageProcessInfo.getMessageManager());
                ProcessMonitor.getInstance().notifyProcessor();
                return false;
            }
            return true;
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to check the message is local : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to check the message is local : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method checks if the target this message is going to is local to
     * this Coadunation Instance.
     *
     * @return TRUE if local, FALSE if not.
     * @param message The message to perform the test on.
     */
    private boolean checkIfReplyLocal(Message message) throws
            MessageServiceException {
        try {
            String reply = message.getReplyTo();
            if (reply == null) {
                reply = message.getFrom();
                if (reply == null) {
                    message.addError(Message.ERROR,
                            "There is no reply for this message");
                    initUndeliverableProcess(message);
                    return false;
                }
            }
            
            // check if this fall withing this part of the tree or below
            String jndiBase = NamingDirector.getInstance().getJNDIBase() + "/";
            String parentUrl = NamingDirector.getInstance().getPrimaryJNDIUrl();
            String instanceURL = NamingDirector.getInstance().
                    getInstanceId() + "/";
            int pos = reply.indexOf(jndiBase);
            int instancePos = reply.indexOf(instanceURL);
            if ((((reply.indexOf(parentUrl)) != -1) &&
                    (reply.indexOf(jndiBase) == -1)) ||
                    (reply.indexOf(PARENT_INSTANCE) == 0) ||
                    ((reply.indexOf(NamingConstants.JNDI_NETWORK_PREFIX) == 0) &&
                    !NamingDirector.getInstance().isPrimary())) {
                deliverToParent(message);
                return false;
            } else if (((instancePos != -1) && (
                    reply.indexOf(NamingConstants.SUBCONTEXT,(instancePos +
                    instanceURL.length())) != -1)) ||
                    (reply.indexOf(NamingConstants.SUBCONTEXT) == 0)) {
                deliverToChild(reply,message);
                return false;
            } else if (instancePos != -1) {
                utw.begin();
                reply = reply.substring(instancePos + instanceURL.length());
                if (message.getReplyTo() != null) {
                    message.setReplyTo(reply);
                } else {
                    message.setFrom(reply);
                }
                messageProcessInfo.getMessageManager().updateMessage(message);
                utw.commit();
                messageProcessInfo.getMessageQueue().pushBackMessage(
                        messageProcessInfo.getMessageManager());
                ProcessMonitor.getInstance().notifyProcessor();
                return false;
            }
            return true;
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to check the message is local : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to check the message is local : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method checks if the target this message is going to is local to
     * this Coadunation Instance.
     *
     * @return TRUE if local, FALSE if not.
     * @param message The message to perform the test on.
     */
    private boolean checkIfServiceLocal(Message message) throws
            MessageServiceException {
        try {
            String target = message.getTarget();
            if (target != null) {
                return checkIfTargetLocal(message);
            }
            
            String[] services = message.getServices();
            if (services == null) {
                message.addError(Message.ERROR,
                        "There are no services for this message");
                initUndeliverableProcess(message);
                return false;
            }
            List serviceList = new ArrayList();
            for (int index = 0; index < services.length; index++) {
                serviceList.add(services[index]);
            }
            ServiceBroker serviceBroker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    ServiceBroker.JNDI_URL);
            String service = serviceBroker.getServiceProvider(serviceList);
            if (service.length() != 0) {
                message.setTarget(service);
                utw.begin();
                messageProcessInfo.getMessageManager().updateMessage(message);
                utw.commit();
                messageProcessInfo.getMessageQueue().pushBackMessage(
                        messageProcessInfo.getMessageManager());
            } else {
                deliverToParent(message);
            }
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to check if the services are local : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to check if the services are local : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
        return false;
    }
    
    
    /**
     * This method checks if the target this message is going to is local to
     * this Coadunation Instance.
     *
     * @return TRUE if local, FALSE if not.
     * @param message The message to perform the test on.
     */
    private boolean checkIfMessageInQueue(Message message) throws
            MessageServiceException {
        try {
            if (messageProcessInfo.getMessageQueue().getName().equals(
                    message.getTarget())) {
                return true;
            }
            utw.begin();
            if (message.getTarget().equals(MessageServiceManagerMBean.JNDI_URL) &&
                    (message.getTargetNamedQueue() != null)) {
                if (false == NamedQueueManagerImpl.getInstance().
                        checkForNamedQueue(message.getTargetNamedQueue(),false)){
                    utw.release();
                    message.addError(Message.ERROR,"The named queue [" +
                            message.getTargetNamedQueue() + "] does not exist.");
                    initUndeliverableProcess(message);
                    return false;
                }
                messageProcessInfo.getMessageQueue().removeMessage(
                        message.getMessageId());
                ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                        assignToQueue(message.getTargetNamedQueue());
                NamedMemoryQueue.getInstance(
                        message.getTargetNamedQueue()).
                        addMessage(messageProcessInfo.getMessageManager());
            } else {
                MessageQueue messageQueue = MessageQueueManager.getInstance().
                        getQueue(message.getTarget());
                messageProcessInfo.getMessageQueue().removeMessage(
                        message.getMessageId());
                ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                        assignToQueue(message.getTarget());
                messageQueue.addMessage(messageProcessInfo.getMessageManager());
            }
            utw.commit();
            return false;
        } catch (Exception ex) {
            log.error("Failed to check the target : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to check the target : " + ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * Check if this message is in the correct queue.
     *
     * @return TRUE if local, FALSE if not.
     * @param message The message to perform the test on.
     * @exception MessageServiceException
     */
    private boolean checkIfReplyMessageInQueue(Message message) throws
            MessageServiceException {
        try {
            String reply = message.getReplyTo();
            if (reply == null) {
                reply = message.getFrom();
                if (reply == null) {
                    message.addError(Message.ERROR,
                            "There is no reply for this message");
                    initUndeliverableProcess(message);
                    return false;
                }
            }
            
            if (messageProcessInfo.getMessageQueue().getName().equals(
                    reply)) {
                return true;
            }
            utw.begin();
            if (reply.equals(MessageServiceManagerMBean.JNDI_URL) &&
                    (message.getTargetNamedQueue() != null)) {
                if (false == NamedQueueManagerImpl.getInstance().
                        checkForNamedQueue(message.getReplyNamedQueue(),false)){
                    utw.release();
                    message.addError(Message.ERROR,"The named queue [" +
                            message.getReplyNamedQueue() + "] does not exist.");
                    initUndeliverableProcess(message);
                    return false;
                }
                messageProcessInfo.getMessageQueue().removeMessage(
                        message.getMessageId());
                ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                        assignToQueue(message.getReplyNamedQueue());
                NamedMemoryQueue.getInstance(
                        message.getTargetNamedQueue()).
                        addMessage(messageProcessInfo.getMessageManager());
            } else {
                MessageQueue messageQueue = MessageQueueManager.getInstance().
                        getQueue(reply);
                messageProcessInfo.getMessageQueue().removeMessage(
                        message.getMessageId());
                ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                        assignToQueue(reply);
                messageQueue.addMessage(messageProcessInfo.getMessageManager());
            }
            utw.commit();
            return false;
        } catch (Exception ex) {
            log.error("Failed to check the reply queue : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to check the reply queue : " + ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method clones the original message so that it can be sent to all
     * the daemons suppliung the services.
     *
     * @param message The message to clone.
     * @exception MessageServiceException
     */
    private void cloneMessageForServices(Message message) throws
            MessageServiceException {
        try {
            String[] services = message.getServices();
            List serviceList = new ArrayList();
            for (int index = 0; index < services.length; index++) {
                serviceList.add(services[index]);
            }
            ServiceBroker serviceBroker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    ServiceBroker.JNDI_URL);
            List daemonList = serviceBroker.getServiceProviders(serviceList);
            if (daemonList.size() == 0) {
                message.addError(Message.ERROR,
                        "There are no daemon providing these services.");
                initUndeliverableProcess(message);
                return;
            }
            
            utw.begin();
            for (int index = 0; index < daemonList.size(); index++) {
                MessageImpl newMessage =
                        (MessageImpl)((MessageImpl)message).clone();
                newMessage.setMessageId(RandomGuid.getInstance().getGuid());
                newMessage.setTarget((String)daemonList.get(index));
                newMessage.setMessageType(Message.POINT_TO_POINT);
                newMessage.setNextProcessDate(new Date());
                MessageManager messageManager = MessageManagerFactory.getInstance().
                        getMessageManager(newMessage);
                MessageQueue messageQueue = MessageQueueManager.getInstance().
                        getQueue(MessageQueueManager.UNSORTED);
                ((MessageManagerImpl)messageManager).assignToQueue(
                        MessageQueueManager.UNSORTED);
                messageQueue.addMessage(messageManager);
            }
            messageProcessInfo.getMessageManager().remove();
            messageProcessInfo.getMessageQueue().removeMessage(
                    message.getMessageId());
            utw.commit();
        } catch (Exception ex) {
            log.error("Failed to clone the messages : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to clone the messages : " + ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method delivers the message to another coadunation instance.
     *
     * @param message The reference to the message object.
     * @exception MessageServiceException
     */
    private void deliverToParent(Message message) throws
            MessageServiceException {
        try {
            if (namingDirector.isPrimary()) {
                message.addError(Message.ERROR,
                        "The primary has no parent cannot go further.");
                initUndeliverableProcess(message);
                return;
            }
            Message messageCopy = (Message)((MessageImpl)message).clone();
            if (message.getTarget() != null) {
                messageCopy.setFrom(downJNDIUrl(message.getTarget()));
            }
            if (message.getReplyTo() != null) {
                messageCopy.setReplyTo(downJNDIUrl(message.getReplyTo()));
            }
            if (message.getFrom() != null) {
                messageCopy.setFrom(downJNDIUrl(message.getFrom()));
            }
            
            log.debug("Deliver message to parent : " + message.getMessageId());
            utw.begin();
            MessageStore messageStore = (MessageStore)ConnectionManager.
                    getInstance().getConnection(MessageStore.class,
                    namingDirector.getPrimaryJNDIUrl() + "/" +
                    MessageStore.JNDI_URL);
            messageProcessInfo.getMessageManager().remove();
            messageProcessInfo.getMessageQueue().removeMessage(
                    message.getMessageId());
            log.debug("The message has been deliverd to parent committing : " +
                    message.getMessageId());
            IDLock.getInstance().lock(message.getMessageId());
            messageStore.addMessage(messageCopy);
            utw.commit();
            log.debug("Delivered message to parent : " + message.getMessageId());
        } catch (Exception ex) {
            log.error("Failed to deliver to a the parent : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver to a the parent : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method delivers the message to another coadunation instance.
     *
     * @param target The target of the message.
     * @param message The reference to the message object.
     */
    private void deliverToChild(String target, Message message) throws
            MessageServiceException {
        try {
            Message messageCopy = (Message)((MessageImpl)message).clone();
            if (message.getTarget() != null) {
                messageCopy.setTarget(upJNDIUrl(target,message.getTarget()));
            }
            if (message.getReplyTo() != null) {
                messageCopy.setReplyTo(upJNDIUrl(target,message.getReplyTo()));
            }
            if (message.getFrom() != null) {
                messageCopy.setFrom(upJNDIUrl(target,message.getFrom()));
            }
            
            String subContextUrl = NamingConstants.SUBCONTEXT + "/";
            if (target.contains(namingDirector.getInstanceId())) {
                subContextUrl = namingDirector.getInstanceId() + "/" +
                        NamingConstants.SUBCONTEXT + "/";
            }
            int pos = target.indexOf(subContextUrl);
            if (pos == -1) {
                message.addError(Message.ERROR,
                        "Cannot find the sub reference information : " + target);
                initUndeliverableProcess(message);
                return;
            }
            String subContext = target.substring(pos + subContextUrl.length());
            subContext = NamingConstants.SUBCONTEXT + "/" +
                    subContext.substring(0,subContext.indexOf('/')) + "/" +
                    MessageStore.JNDI_URL;
            log.debug("Deliver message to child : " + message.getMessageId());
            utw.begin();
            MessageStore messageStore = (MessageStore)ConnectionManager.
                    getInstance().getConnection(MessageStore.class,
                    subContext);
            messageProcessInfo.getMessageManager().remove();
            messageProcessInfo.getMessageQueue().removeMessage(
                    message.getMessageId());
            IDLock.getInstance().lock(message.getMessageId());
            messageStore.addMessage(messageCopy);
            log.debug("The message has been deliverd to child committing : " +
                    message.getMessageId());
            utw.commit();
            log.debug("Delivered message to child : " + message.getMessageId());
        } catch (Exception ex) {
            log.error("Failed to deliver to a the child : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver to a the child : " +
                    ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method delivers the message.
     *
     * @param message The message to deliver
     * @exception MessageServiceException
     */
    private void deliverMessage(Message message) throws
            MessageServiceException {
        initUserSession(message);
        try {
            if (message instanceof RPCMessage) {
                deliverRPCMessage(message.getTarget(),message);
            } else if (message instanceof TextMessage) {
                deliverTextMessage(message.getTarget(),message);
            }
        } finally {
            releaseUserSession();
        }
    }
    
    
    /**
     * This method delivers the reply message.
     *
     * @param message The message to deliver
     * @exception MessageServiceException
     */
    private void deliverReplyMessage(Message message) throws
            MessageServiceException {
        initUserSession(message);
        try {
            String reply = message.getReplyTo();
            if (reply == null) {
                reply = message.getFrom();
                if (reply == null) {
                    message.addError(Message.ERROR,
                            "There is no reply for this message");
                    initUndeliverableProcess(message);
                    return;
                }
            }
            if (message instanceof RPCMessage) {
                deliverReplyRPCMessage(reply,message);
            } else if (message instanceof TextMessage) {
                deliverReplyTextMessage(reply,message);
            }
        } finally {
            releaseUserSession();
        }
    }
    
    
    /**
     * This method delivers the rpc message to its target.
     *
     * @param message The message to deliver.
     * @exception MessageServiceException
     */
    private void deliverRPCMessage(String target, Message message) throws
            MessageServiceException {
        ClassLoader original = null;
        try {
            Object ref = null;
            
            try {
                if (((ref = BeanConnector.getInstance().getBean(target)) == null) &&
                        ((ref = JMXBeanConnector.getInstance().getJMXBean(target))
                        == null)) {
                    message.addError(Message.ERROR,"The target [" + target
                            + "] does not exist.");
                    initUndeliverableProcess(message);
                    return;
                }
                original = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(
                        ref.getClass().getClassLoader());
                
                
                RPCMessageImpl rpcMessageImpl =
                        (RPCMessageImpl)((RPCMessageImpl)message).clone();
                Method method = ref.getClass().getMethod(rpcMessageImpl.getMethodName(),
                        rpcMessageImpl.getArgumentTypes());
                Object result = method.invoke(ref,rpcMessageImpl.getArguments());
                ((RPCMessage)message).setResult(result);
                
                // reset the class loader
                if (original != null) {
                    Thread.currentThread().setContextClassLoader(original);
                    original = null;
                }
            } catch (Throwable ex) {
                log.error("Caught an exception : "
                        + ex.getMessage(),ex);
                // reset the class loader
                if (original != null) {
                    Thread.currentThread().setContextClassLoader(original);
                    original = null;
                }
                // deal with invocation exception
                if (ex instanceof java.lang.reflect.InvocationTargetException) {
                    ex = ex.getCause();
                }
                
                // if this is a remote exception
                message.addError(Message.ERROR,
                                "Failed to deliver the message because :" +
                                ex.getMessage());
                if ((ex instanceof java.rmi.RemoteException) ||
                    (ex instanceof java.lang.RuntimeException)) {
                    log.info("This is a remote exception and results " +
                            "in a retry");
                    message.incrementRetries();
                    if (message.getRetries() >= maxRetries) {
                        message.addError(Message.ERROR,
                                "Reached the max retries [" +
                                maxRetries + "]");
                        initUndeliverableProcess(message);
                    } else {
                        try {
                            // start a new transaction
                            utw.begin();
                            
                            Date nextDate = new Date();
                            nextDate.setTime(nextDate.getTime() + delay);
                            ((MessageImpl)message).setNextProcessDate(nextDate);
                            messageProcessInfo.getMessageManager().updateMessage(
                                    message);
                            utw.commit();
                            messageProcessInfo.getMessageQueue().pushBackMessage(
                                    messageProcessInfo.getMessageManager());
                        } finally {
                            utw.release();
                        }
                    }
                    return;
                }
                ((RPCMessage)message).setThrowable(ex);
            }
            try {
                // update the reply information
                // In theory the this should be setup before the invoke call but
                // because that call could result in a transaction timeout and
                // rollback it is done here.
                utw.begin();
                
                // deal with reply
                if (message.getReply()) {
                    log.info("Init the process to deliver to the sender : " +
                            message.getMessageId());
                    ((RPCMessageImpl)message).setState(Message.DELIVERED);
                    messageProcessInfo.getMessageManager().updateMessage(message);
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                    MessageQueue messageQueue = MessageQueueManager.getInstance().
                            getQueue(MessageQueueManager.UNSORTED);
                    ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                            assignToQueue(MessageQueueManager.UNSORTED);
                    messageQueue.addMessage(messageProcessInfo.getMessageManager());
                } else {
                    log.info("Removing the completed rpc message : " +
                            message.getMessageId());
                    messageProcessInfo.getMessageManager().remove();
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                }
                utw.commit();
            } finally {
                utw.release();
            }
            
        } catch (Exception ex) {
            log.error("Failed to deliver the RPC Message : "
                    + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver the RPC Message : "
                    + ex.getMessage(),ex);
        } finally {
            if (original != null) {
                Thread.currentThread().setContextClassLoader(original);
            }
        }
    }
    
    
    /**
     * This method delivers the rpc reply message.
     *
     * @param reply The reply address for the message.
     * @param message The message to deliver.
     * @exception MessageServiceException
     */
    private void deliverReplyRPCMessage(String reply, Message message) throws
            MessageServiceException {
        ClassLoader original = null;
        RPCMessage rpcMessage = null;
        try {
            try {
                Object ref = null;
                if (((ref = BeanConnector.getInstance().getBean(reply)) == null) &&
                        ((ref = JMXBeanConnector.getInstance().getJMXBean(reply))
                        == null)) {
                    message.addError(Message.ERROR,"The reply [" + reply
                            + "] does not exist.");
                    initUndeliverableProcess(message);
                    return;
                }
                original = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(
                        ref.getClass().getClassLoader());
                
                // cast the rpc message
                rpcMessage = (RPCMessage)message;
                
                if (rpcMessage.generatedException()) {
                    Method method = ref.getClass().getMethod("onFailure",
                            new Class[] {String.class,String.class,
                            Throwable.class});
                    Throwable ex = rpcMessage.getThrowable();
                    if (ex instanceof
                            java.lang.reflect.InvocationTargetException) {
                        ex = ((java.lang.reflect.InvocationTargetException)ex).
                                getCause();
                    }
                    method.invoke(ref,new Object[] {rpcMessage.getMessageId(),
                    rpcMessage.getCorrelationId(),ex});
                } else {
                    Method method = ref.getClass().getMethod("onSuccess",
                            new Class[] {String.class,String.class,
                            Object.class});
                    method.invoke(ref,new Object[] {rpcMessage.getMessageId(),
                    rpcMessage.getCorrelationId(),
                    rpcMessage.getResult()});
                }
            } catch (Throwable ex) {
                log.error("Caught an exception : "
                        + ex.getMessage(),ex);
                // reset the class loader
                if (original != null) {
                    Thread.currentThread().setContextClassLoader(original);
                    original = null;
                }
                // deal with invocation exception
                if (ex instanceof java.lang.reflect.InvocationTargetException) {
                    ex = ex.getCause();
                }
                
                // add an error message
                message.addError(Message.ERROR,"Failed to deliver the message ["
                        + reply + "] to the AsyncCallbackHandler method : " +
                        ex.getMessage());
                // if this is a remote exception
                if ((ex instanceof java.rmi.RemoteException) ||
                        (ex instanceof java.lang.RuntimeException)) {
                    log.info("This is a remote exception and results " +
                            "in a retry");
                    message.incrementRetries();
                    if (message.getRetries() >= maxRetries) {
                        message.addError(Message.ERROR,
                                "Reached the max retries [" + maxRetries + "]");
                        initUndeliverableProcess(message);
                    } else {
                        try {
                            // start a new transaction
                            utw.begin();
                            
                            Date nextDate = new Date();
                            nextDate.setTime(nextDate.getTime() + delay);
                            ((MessageImpl)message).setNextProcessDate(nextDate);
                            messageProcessInfo.getMessageManager().updateMessage(
                                    message);
                            utw.commit();
                            messageProcessInfo.getMessageQueue().pushBackMessage(
                                    messageProcessInfo.getMessageManager());
                        } finally {
                            utw.release();
                        }
                    }
                    return;
                }
                log.error("Failed to deliver the message ["
                        + reply + "] to the AsyncCallbackHandler method : " +
                        ex.getMessage(),ex);
                initUndeliverableProcess(message);
                return;
            }
            
            if (original != null) {
                Thread.currentThread().setContextClassLoader(original);
                original = null;
            }
            try {
                // In theory the this should be setup before the invoke call but
                // because that call could result in a transaction timeout and
                // rollback it is done here.
                utw.begin();
                
                log.info("Removing the completed rpc message : " +
                        rpcMessage.getMessageId());
                messageProcessInfo.getMessageManager().remove();
                messageProcessInfo.getMessageQueue().removeMessage(
                        rpcMessage.getMessageId());
                
                utw.commit();
            } finally {
                utw.release();
            }
            
        } catch (Throwable ex) {
            log.error("Failed to deliver the reply RPC Message : "
                    + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver the reply RPC Message : "
                    + ex.getMessage(),ex);
        } finally {
            if (original != null) {
                Thread.currentThread().setContextClassLoader(original);
            }
        }
    }
    
    
    /**
     * This method delivers the text message to its target.
     *
     * @param target The target to deliver the message to.
     * @param message The message to deliver.
     * @exception MessageServiceException
     */
    private void deliverTextMessage(String target, Message message) throws
            MessageServiceException {
        try {
            Message result = message;
            try {
                MessageHandler messageHandler =
                        (MessageHandler)ConnectionManager.getInstance().
                        getConnection(MessageHandler.class,target);
                result = messageHandler.processMessage(message);
            } catch (java.lang.ClassCastException ex) {
                log.error("Failed to deliver the text message ["+
                        message.getMessageId()+ "], " +
                        "init the undeliverable process, as the target cannot be " +
                        "spoken to correctly : " + ex.getMessage(),ex);
                message.addError(Message.ERROR,"Failed to deliver the text message : "
                        + ex.getMessage());
                initUndeliverableProcess(message);
                return;
            } catch (com.rift.coad.util.connection.NameNotFound ex) {
                log.error("Failed to deliver the text message ["+
                        message.getMessageId()+ "], " +
                        "init the undeliverable process, " +
                        "as the target name cannot be found : "
                        + ex.getMessage(),ex);
                message.addError(Message.ERROR,"Failed to deliver the text message : "
                        + ex.getMessage());
                initUndeliverableProcess(message);
                return;
            } catch (Throwable ex) {
                log.error("Failed to deliver the text message ["+
                        message.getMessageId()+ "] : " + ex.getMessage(),ex);
                result.addError(Message.ERROR,
                        "Failed to deliver the text message : "
                        + ex.getMessage());
            }
            
            result.incrementRetries();
            if (result.isAcknowledged() && result.getReply() &&
                    (result.getState() == Message.UNDELIVERED)) {
                try {
                    // update the reply information
                    // In theory the this should be setup before the invoke call but
                    // because that call could result in a transaction timeout and
                    // rollback it is done here.
                    utw.begin();
                    
                    log.info("Init the process to deliver to the sender : " +
                            message.getMessageId());
                    ((MessageImpl)result).setState(Message.DELIVERED);
                    messageProcessInfo.getMessageManager().updateMessage(result);
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                    MessageQueue messageQueue = MessageQueueManager.getInstance().
                            getQueue(MessageQueueManager.UNSORTED);
                    ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                            assignToQueue(MessageQueueManager.UNSORTED);
                    messageQueue.addMessage(messageProcessInfo.getMessageManager());
                    utw.commit();
                } finally {
                    utw.release();
                }
            } else if ((result.isAcknowledged() && !result.getReply()) ||
                    (result.isAcknowledged() &&
                    (result.getState() == Message.DELIVERED))){
                try {
                    // update the reply information
                    // In theory the this should be setup before the invoke call but
                    // because that call could result in a transaction timeout and
                    // rollback it is done here.
                    utw.begin();
                    log.info("Removing the completed text message : " +
                            message.getMessageId());
                    messageProcessInfo.getMessageManager().remove();
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                    utw.commit();
                } finally {
                    utw.release();
                }
            } else if (result.getRetries() >= maxRetries) {
                result.addError(Message.ERROR,
                        "Reached the max retries [" + maxRetries + "]");
                initUndeliverableProcess(result);
            } else {
                try {
                    // update the reply information
                    // In theory the this should be setup before the invoke call but
                    // because that call could result in a transaction timeout and
                    // rollback it is done here.
                    utw.begin();
                    
                    Date nextDate = new Date();
                    nextDate.setTime(nextDate.getTime() + delay);
                    ((MessageImpl)result).setNextProcessDate(nextDate);
                    messageProcessInfo.getMessageManager().updateMessage(result);
                    utw.commit();
                    messageProcessInfo.getMessageQueue().pushBackMessage(
                            messageProcessInfo.getMessageManager());
                } finally {
                    utw.release();
                }
            }
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to deliver the text message ["+
                    message.getMessageId()+ "] : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver the text message : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method delivers the reply text message to its target.
     *
     * @param reply The reply address for the message.
     * @param message The message to deliver.
     * @exception MessageServiceException
     */
    private void deliverReplyTextMessage(String reply, Message message) throws
            MessageServiceException {
        try {
            Message result = message;
            try {
                MessageHandler messageHandler =
                        (MessageHandler)ConnectionManager.getInstance().
                        getConnection(MessageHandler.class,reply);
                result = messageHandler.processMessage(message);
            }  catch (java.lang.ClassCastException ex) {
                log.error("Failed to deliver the text message : "
                        + ex.getMessage(),ex);
                message.addError(Message.ERROR,"Failed to deliver the text message : "
                        + ex.getMessage());
                initUndeliverableProcess(message);
            } catch (com.rift.coad.util.connection.NameNotFound ex) {
                log.error("Failed to deliver the text message : "
                        + ex.getMessage(),ex);
                message.addError(Message.ERROR,"Failed to deliver the text message : "
                        + ex.getMessage());
                initUndeliverableProcess(message);
            } catch (Throwable ex) {
                log.error("Failed to deliver the text message : "
                        + ex.getMessage(),ex);
                result.addError(Message.ERROR,
                        "Failed to deliver the text message : "
                        + ex.getMessage());
                
            }
            result.incrementRetries();
            if (result.isAcknowledged()){
                try{
                    // update the reply information
                    // In theory the this should be setup before the invoke call but
                    // because that call could result in a transaction timeout and
                    // rollback it is done here.
                    utw.begin();
                    
                    log.info("Removing the completed text message : " +
                            message.getMessageId());
                    messageProcessInfo.getMessageManager().remove();
                    messageProcessInfo.getMessageQueue().removeMessage(
                            message.getMessageId());
                    utw.commit();
                } finally {
                    utw.release();
                }
            } else if (result.getRetries() >= maxRetries) {
                result.addError(Message.ERROR,
                        "Reached the max retries [" + maxRetries + "]");
                initUndeliverableProcess(result);
            } else {
                try{
                    // update the reply information
                    // In theory the this should be setup before the invoke call but
                    // because that call could result in a transaction timeout and
                    // rollback it is done here.
                    utw.begin();
                    Date nextDate = new Date();
                    nextDate.setTime(nextDate.getTime() + delay);
                    ((MessageImpl)result).setNextProcessDate(nextDate);
                    messageProcessInfo.getMessageManager().updateMessage(result);
                    utw.commit();
                    messageProcessInfo.getMessageQueue().pushBackMessage(
                            messageProcessInfo.getMessageManager());
                } finally {
                    utw.release();
                }
            }
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to deliver the text message : "
                    + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to deliver the text message : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method prepends the JNDI URL base.
     *
     * @return The modified url.
     * @param url The url to modify.
     */
    private String downJNDIUrl(String url) throws
            MessageServiceException {
        try {
            String instanceBase = NamingConstants.SUBCONTEXT + "/"
                    + namingDirector.getInstanceId() + "/";
            if (url.indexOf(PARENT_INSTANCE) == 0) {
                return url.substring(PARENT_INSTANCE.length());
            } else if (url.contains(namingDirector.getPrimaryJNDIUrl()) ||
                    url.contains(namingDirector.getJNDIBase()) ||
                    url.contains(instanceBase) ||
                    (url.indexOf(NamingConstants.JNDI_NETWORK_PREFIX) == 0)) {
                return url;
            } else if (!url.contains(namingDirector.getInstanceId())) {
                return instanceBase + url;
            } else {
                return NamingConstants.SUBCONTEXT + "/" + url;
            }
        } catch (Exception ex) {
            log.error("Failed to move down the url : "
                    + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to move down the url : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method prepends the JNDI URL base.
     *
     * @return The modified url.
     * @param url The url to modify.
     */
    private String upJNDIUrl(String target,String url) throws
            MessageServiceException {
        try {
            String updatedURL = url;
            String instanceBase = namingDirector.getInstanceId()  + "/" +
                    NamingConstants.SUBCONTEXT + "/";
            String subContextUrl = NamingConstants.SUBCONTEXT + "/";
            int pos = url.indexOf(subContextUrl);
            if (url.contains(instanceBase)) {
                updatedURL = url.substring(url.indexOf(instanceBase) +
                        instanceBase.length());
                pos = updatedURL.indexOf(subContextUrl);
                if (url.equals(target) && (pos == 0)) {
                    updatedURL = updatedURL.substring(updatedURL.indexOf("/",
                            pos + subContextUrl.length()) + 1);
                }
            } else if (url.equals(target) && (pos == 0)) {
                updatedURL = url.substring(url.indexOf("/",
                        pos + subContextUrl.length()) + 1);
            } else {
                updatedURL = PARENT_INSTANCE + url;
            }
            return updatedURL;
        } catch (Exception ex) {
            log.error("Failed to modify the url to set it relative to the next " +
                    "coadunation intance : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to modify the url to " +
                    "set it relative to the next coadunation intance : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The message has been deemed undeliverable for some reason.
     *
     * @param message The message set as undeliverable
     * @excption MessageServiceException
     */
    private void initUndeliverableProcess(Message message) throws
            MessageServiceException {
        try {
            int currentState = message.getState();
            utw.begin();
            ((MessageImpl)message).setState(Message.UNDELIVERABLE);
            messageProcessInfo.getMessageManager().updateMessage(message);
            utw.commit();
            utw.release();
            if (currentState == Message.DELIVERED) {
                processUndeliverable(message);
            } else if (messageProcessInfo.getMessageQueue().getName().equals(
                    MessageQueueManager.UNSORTED)) {
                messageProcessInfo.getMessageQueue().pushBackMessage(
                        messageProcessInfo.getMessageManager());
            } else {
                utw.begin();
                messageProcessInfo.getMessageQueue().removeMessage(
                        message.getMessageId());
                MessageQueue messageQueue = MessageQueueManager.getInstance().
                        getQueue(MessageQueueManager.UNSORTED);
                ((MessageManagerImpl)messageProcessInfo.getMessageManager()).
                        assignToQueue(MessageQueueManager.UNSORTED);
                messageQueue.addMessage(messageProcessInfo.getMessageManager());
                utw.commit();
            }
            
        } catch (Exception ex) {
            log.error("Failed to init the undeliverable process :" +
                    ex.getMessage(),ex);
            throw new MessageServiceException("Failed to init the " +
                    "undeliverable process :" + ex.getMessage(),ex);
        } finally {
            utw.release();
        }
    }
    
    
    /**
     * This method is responsible for initializing the user session.
     *
     * @param message The message containing the user session information.
     * @exception MessageServiceException
     */
    private void initUserSession(Message message) throws MessageServiceException {
        try {
            Session session = new Session(message.getMessageCreater(),
                    message.getSessionId(),
                    new HashSet(message.getMessagePrincipals()));
            getServerInterceptor().createSession(session);
        } catch (Exception ex) {
            log.error("Failed to setup the user session : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to setup the user session :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for initializing the user session.
     */
    private void releaseUserSession() {
        try {
            getServerInterceptor().release();
        } catch (Exception ex) {
            log.error("Failed to release the user session : " +
                    ex.getMessage(),ex);
        }
    }
}
