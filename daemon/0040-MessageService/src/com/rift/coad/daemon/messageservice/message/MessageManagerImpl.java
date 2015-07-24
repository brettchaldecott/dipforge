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
 * MessageManagerImpl.java
 */

// the package path
package com.rift.coad.daemon.messageservice.message;

// java imports
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageServiceManager;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.TextMessage;
import com.rift.coad.daemon.messageservice.MessageManager;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.InvalidProperty;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.daemon.messageservice.named.NamedQueueManagerImpl;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeException;
import com.rift.coad.util.change.ChangeLog;


/**
 * This object implements the message manager interface.
 *
 * @author Brett Chaldecott
 */
public class MessageManagerImpl implements MessageManager {
    
    
    /**
     * This object represents an add message change
     */
    public static class AddMessageChange implements Change {
        // private member variables
        private MessageImpl newMessage = null;
        
        /**
         * The constructor of the add message change object.
         *
         * @param message The message that represents this change.
         * @exception MessageServiceException
         */
        public AddMessageChange(MessageImpl newMessage) throws
                MessageServiceException {
            try {
                this.newMessage = (MessageImpl)newMessage.clone();
            } catch (Exception ex) {
                log.error("Failed to create a new add message change : " +
                        ex.getMessage(),ex);
                throw new MessageServiceException(
                        "Failed to create a new add message change : " +
                        ex.getMessage(),ex);
            }
        }
        
        /**
         * The definition of the apply method.
         */
        public void applyChanges() throws ChangeException {
            try {
                //MessageTransactionLock.getInstance().lock();
                Session session = HibernateUtil.getInstance(
                        MessageServiceManager.class).getSession();
                
                
                // set the reply flag
                int reply = 0;
                if (newMessage.getReply()) {
                    reply = 1;
                }
                int acknowledged = 0;
                if (newMessage.isAcknowledged()) {
                    acknowledged = 1;
                }
                
                // check the type of messge
                int messageType = TEXT_MESSAGE;
                if (newMessage instanceof RPCMessage) {
                    messageType = RPC_MESSAGE;
                }
                
                // instanciate the basic message
                com.rift.coad.daemon.messageservice.db.Message message =
                        new com.rift.coad.daemon.messageservice.db.Message(
                        newMessage.getMessageId(),newMessage.getMessageCreater(),
                        newMessage.getSessionId(),messageType,
                        newMessage.getMessageType(),newMessage.getPriority(),
                        reply, newMessage.getFrom(), acknowledged, 0,
                        newMessage.getRetries());
                if ((newMessage.getTarget() != null) &&
                        (newMessage.getTarget().length() != 0 )) {
                    message.setTarget(newMessage.getTarget());
                }
                if (newMessage.getFrom() == null) {
                    throw new InvalidProperty("The from address must be set");
                }
                message.setFromUrl(newMessage.getFrom());
                if ((newMessage.getReplyTo() != null) &&
                        (newMessage.getReplyTo().length() != 0)) {
                    message.setReplyUrl(newMessage.getReplyTo());
                }
                if ((newMessage.getTargetNamedQueue() != null) &&
                        (newMessage.getTargetNamedQueue().length() != 0)) {
                    message.setTargetNamedQueue(newMessage.getTargetNamedQueue());
                }
                if ((newMessage.getReplyNamedQueue() != null) &&
                        (newMessage.getReplyNamedQueue().length() != 0)) {
                    message.setReplyNamedQueue(newMessage.getReplyNamedQueue());
                }
                if ((newMessage.getCorrelationId() != null) &&
                        (newMessage.getCorrelationId().length() != 0)) {
                    message.setCorrelationId(newMessage.getCorrelationId());
                }
                message.setCreated(new Timestamp(newMessage.getCreated().
                        getTime()));
                message.setProcessed(new Timestamp(newMessage.getProcessedDate().
                        getTime()));
                message.setNextProcess(new Timestamp(
                        ((MessageImpl)newMessage).getNextProcessDate().getTime()));
                message.setMessageState(newMessage.getState());
                message = (com.rift.coad.daemon.messageservice.db.Message)session.get(com.rift.coad.daemon.messageservice.db.Message.class,session.save(message));
                
                if (newMessage instanceof RPCMessage) {
                    RPCMessage rpcMessage = (RPCMessage)newMessage;
                    MessageRpcBody rpcBody = new MessageRpcBody();
                    rpcBody.setMessage(message);
                    rpcBody.setMessageId(message.getId());
                    rpcBody.setXml(rpcMessage.getMethodBodyXML());
                    if (rpcMessage.generatedException()) {
                        rpcBody.setExceptionValue(
                                ((RPCMessageImpl)rpcMessage).getThrowableBytes().
                                clone());
                    }
                    if (rpcMessage.getResult() != null) {
                        rpcBody.setResultValue(
                                ((RPCMessageImpl)rpcMessage).getResultBytes().
                                clone());
                    }
                    session.persist(rpcBody);
                    
                } else if (newMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage)newMessage;
                    MessageTxtBody txtBody = new MessageTxtBody();
                    txtBody.setMessage(message);
                    txtBody.setMessageId(message.getId());
                    txtBody.setBody(textMessage.getTextBody());
                    session.persist(txtBody);
                } else {
                    log.error("The message type [" + newMessage.getClass().getName()
                    + "] is not recognised.");
                    throw new MessageServiceException("The message type [" +
                            newMessage.getClass().getName() +
                            "] is not recognised.");
                }
                
                // the message information
                if (newMessage.getServices() != null) {
                    String[] services = newMessage.getServices();
                    for (int index = 0; index < services.length; index++) {
                        MessageService messageServices = new MessageService();
                        messageServices.setMessage(message);
                        messageServices.setService(services[index]);
                        session.persist(messageServices);
                    }
                }
                
                // the message properties
                for (Enumeration enumerat = newMessage.getPropertyNames();
                enumerat.hasMoreElements();) {
                    String key = (String)enumerat.nextElement();
                    Object value = newMessage.getPropertyValue(key);
                    MessageProperty property = new MessageProperty();
                    property.setMessage(message);
                    property.setName(key);
                    if (value instanceof Boolean) {
                        if (((Boolean)value).booleanValue()) {
                            property.setBoolValue(new Integer(1));
                        } else {
                            property.setBoolValue(new Integer(0));
                        }
                    } else if (value instanceof Byte) {
                        property.setByteValue(new Integer(((Byte)value).intValue()));
                    } else if (value instanceof Integer) {
                        property.setIntValue((Integer)value);
                    } else if (value instanceof Long) {
                        property.setLongValue((Long)value);
                    } else if (value instanceof Double) {
                        property.setDoubleValue((Double)value);
                    } else if (value instanceof Float) {
                        property.setFloatValue((Float)value);
                    } else if (value instanceof String) {
                        property.setStringValue((String)value);
                    } else if (value instanceof byte[]) {
                        property.setObjectValue((byte[])value);
                    }
                    try {
                        session.persist(property);
                    } catch (Exception ex) {
                        log.info("Failed to save the property [" + 
                                property.getName().length() 
                                + ":" +
                                (property.getStringValue() != null ? property.getStringValue().length() : 0 )  
                                + ":" +
                                (property.getObjectValue() != null ? property.getObjectValue().length : 0 ) 
                                + "] : " + 
                                ex.getMessage(),ex);
                        throw ex;
                    }
                }
                
                // the message information
                if (newMessage.getMessagePrincipals() == null) {
                    throw new InvalidProperty("Must supply principals");
                }
                List principals = newMessage.getMessagePrincipals();
                for (Iterator iter = principals.iterator(); iter.hasNext();) {
                    MessagePrincipal principal = new MessagePrincipal();
                    principal.setMessage(message);
                    String principalStr = (String)iter.next();
                    //log.info("Add principal : " + principalStr);
                    principal.setPrincipalValue(principalStr);
                    session.persist(principal);
                }
                List errors = newMessage.getErrors();
                for (Iterator iter = errors.iterator(); iter.hasNext();) {
                    com.rift.coad.daemon.messageservice.MessageError messageError =
                            (com.rift.coad.daemon.messageservice.MessageError)
                            iter.next();
                    com.rift.coad.daemon.messageservice.db.MessageError
                            dbMessageError = new
                            com.rift.coad.daemon.messageservice.db.MessageError();
                    dbMessageError.setMessage(message);
                    dbMessageError.setErrorDate(new java.sql.Timestamp(
                            messageError.getErrorDate().getTime()));
                    dbMessageError.setErrorLevel(messageError.getLevel());
                    dbMessageError.setMsg(messageError.getMSG().length() > 1024 ? 
                            messageError.getMSG().substring(0,1024) : messageError.getMSG());
                    
                    // If the apply fails exit
                    session.persist(dbMessageError);
                
                }
                
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(),ex);
                throw new ChangeException(
                        "Failed to apply the changes : " + ex.getMessage(),ex);
            }
            
        }
    }
    
    /**
     * This object represents queue assignment
     */
    public static class AssignMessageToQueueChange implements Change {
        // private member variables
        private String messageId = null;
        private String queueName = null;
        
        /**
         * The constructor of the assign message to queue object.
         */
        public AssignMessageToQueueChange(String messageId,String queueName) {
            this.messageId = new String(messageId);
            this.queueName = new String(queueName);
        }
        
        
        /**
         * The definition of the apply method.
         */
        public void applyChanges() throws ChangeException {
            try {
                //MessageTransactionLock.getInstance().lock();
                Session session = HibernateUtil.getInstance(
                        MessageServiceManager.class).getSession();
                List entries = session.createQuery(
                        "FROM MessageQueue as queue WHERE queue.messageQueueName = ?").
                        setString(0,queueName).list();
                
                if (entries.size() != 1) {
                    log.error("There is no queue by the name of : " +
                            queueName);
                    throw new MessageServiceException(
                            "There is no queue by the name of : " +
                            queueName);
                }
                com.rift.coad.daemon.messageservice.db.MessageQueue messageQueue =
                        (com.rift.coad.daemon.messageservice.db.MessageQueue)
                        entries.get(0);
                com.rift.coad.daemon.messageservice.db.Message message =
                        (com.rift.coad.daemon.messageservice.db.Message)session.
                        get(com.rift.coad.daemon.messageservice.db.Message.class,
                        messageId);
                message.setMessageQueue(messageQueue);
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(),ex);
                // ignore failed application
                //throw new ChangeException(
                //        "Failed to apply the changes : " + ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This object represents an update message change
     */
    public static class UpdateMessageChange implements Change {
        // private member variables
        private MessageImpl updatedMessage = null;
        
        /**
         * The constructor of the update message change object.
         *
         * @param updatedMessage The updated message object.
         * @exception MessageServiceException
         */
        public UpdateMessageChange(MessageImpl updatedMessage) throws
                MessageServiceException {
            try {
                this.updatedMessage = (MessageImpl)updatedMessage.clone();
            } catch (Exception ex) {
                log.error("Failed to clone the updated message : " +
                        ex.getMessage(),ex);
                //throw new MessageServiceException(
                //        "Failed to clone the updated message : " +
                //        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * The definition of the apply method.
         */
        public void applyChanges() throws ChangeException {
            try {
                //MessageTransactionLock.getInstance().lock();
                Session session = HibernateUtil.getInstance(
                        MessageServiceManager.class).getSession();
                
                com.rift.coad.daemon.messageservice.db.Message message =
                        (com.rift.coad.daemon.messageservice.db.Message)session.
                        get(com.rift.coad.daemon.messageservice.db.Message.class,
                        updatedMessage.getMessageId());
                
                if (!(updatedMessage instanceof MessageImpl)) {
                    throw new MessageServiceException(
                            "The incorrect message object has been passed " +
                            "into update");
                }
                MessageImpl messageImpl = (MessageImpl)updatedMessage;
                if ((updatedMessage.getTarget() != null) &&
                        (updatedMessage.getTarget().length() != 0 )) {
                    message.setTarget(updatedMessage.getTarget());
                }
                if (updatedMessage.getFrom() == null) {
                    throw new InvalidProperty("The from address must be set");
                }
                message.setFromUrl(updatedMessage.getFrom());
                if ((updatedMessage.getReplyTo() != null) &&
                        (updatedMessage.getReplyTo().length() != 0)) {
                    message.setReplyUrl(updatedMessage.getReplyTo());
                }
                if ((updatedMessage.getTargetNamedQueue() != null) &&
                        (updatedMessage.getTargetNamedQueue().length() != 0)) {
                    message.setTargetNamedQueue(updatedMessage.
                            getTargetNamedQueue());
                }
                if ((updatedMessage.getReplyNamedQueue() != null) &&
                        (updatedMessage.getReplyNamedQueue().length() != 0)) {
                    message.setReplyNamedQueue(updatedMessage.
                            getReplyNamedQueue());
                }
                if ((updatedMessage.getCorrelationId() != null) &&
                        (updatedMessage.getCorrelationId().length() != 0)) {
                    message.setCorrelationId(updatedMessage.getCorrelationId());
                }
                message.setCreated(new Timestamp(updatedMessage.getCreated().
                        getTime()));
                message.setProcessed(new Timestamp(updatedMessage.getProcessedDate().
                        getTime()));
                message.setNextProcess(new Timestamp(
                        ((MessageImpl)updatedMessage).getNextProcessDate().getTime()));
                message.setMessageState(updatedMessage.getState());
                message.setRetries(updatedMessage.getRetries());
                message.setMessageRoutingType(updatedMessage.getMessageType());
                message.setPriority(updatedMessage.getPriority());
                if (messageImpl.isAcknowledged()) {
                    message.setAcknowledged(1);
                } else {
                    message.setAcknowledged(0);
                }
                
                
                // the message properties
                session.createQuery(
                        "DELETE FROM MessageProperty as property WHERE " +
                        "property.message.id = ?").
                        setString(0,updatedMessage.getMessageId()).
                        executeUpdate();
                //message.getMessageProperties().clear();
                for (Enumeration enumerat = updatedMessage.getPropertyNames();
                enumerat.hasMoreElements();) {
                    String key = (String)enumerat.nextElement();
                    Object value = updatedMessage.getPropertyValue(key);
                    MessageProperty property = new MessageProperty();
                    property.setMessage(message);
                    property.setName(key);
                    if (value instanceof Boolean) {
                        if (((Boolean)value).booleanValue()) {
                            property.setBoolValue(new Integer(1));
                        } else {
                            property.setBoolValue(new Integer(0));
                        }
                    } else if (value instanceof Byte) {
                        property.setByteValue(new Integer(((Byte)value).intValue()));
                    } else if (value instanceof Integer) {
                        property.setIntValue((Integer)value);
                    } else if (value instanceof Long) {
                        property.setLongValue((Long)value);
                    } else if (value instanceof Double) {
                        property.setDoubleValue((Double)value);
                    } else if (value instanceof Float) {
                        property.setFloatValue((Float)value);
                    } else if (value instanceof String) {
                        property.setStringValue((String)value);
                    } else if (value instanceof byte[]) {
                        property.setObjectValue((byte[])value);
                    }
                    session.persist(property);
                }
                
                // the message properties
                session.createQuery(
                        "DELETE FROM MessageError as error WHERE " +
                        "error.message.id = ?").
                        setString(0,updatedMessage.getMessageId()).
                        executeUpdate();
                List errors = updatedMessage.getErrors();
                for (Iterator iter = errors.iterator(); iter.hasNext();) {
                    com.rift.coad.daemon.messageservice.MessageError messageError =
                            (com.rift.coad.daemon.messageservice.MessageError)
                            iter.next();
                    com.rift.coad.daemon.messageservice.db.MessageError
                            dbMessageError = new
                            com.rift.coad.daemon.messageservice.db.MessageError();
                    dbMessageError.setMessage(message);
                    dbMessageError.setErrorDate(new java.sql.Timestamp(
                            messageError.getErrorDate().getTime()));
                    dbMessageError.setErrorLevel(messageError.getLevel());
                    dbMessageError.setMsg(messageError.getMSG());
                    session.persist(dbMessageError);
                }
                
                
                if (updatedMessage instanceof RPCMessage) {
                    RPCMessage rpcMessage = (RPCMessage)updatedMessage;
                    MessageRpcBody rpcBody = (MessageRpcBody)session.get(
                            MessageRpcBody.class,message.getId());
                    if (rpcMessage.generatedException()) {
                        rpcBody.setExceptionValue(
                                ((RPCMessageImpl)rpcMessage).getThrowableBytes().
                                clone());
                    }
                    if (((RPCMessageImpl)rpcMessage).getResultBytes() != null) {
                        rpcBody.setResultValue(
                                ((RPCMessageImpl)rpcMessage).getResultBytes().
                                clone());
                    }
                } else if (updatedMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage)updatedMessage;
                    MessageTxtBody txtBody = (MessageTxtBody)session.get(
                            MessageTxtBody.class,message.getId());
                    txtBody.setBody(textMessage.getTextBody());
                } else {
                    log.error("The message type [" + updatedMessage.getClass().getName()
                    + "] is not recognised.");
                    throw new ChangeException("The message type [" +
                            updatedMessage.getClass().getName() +
                            "] is not recognised.");
                }
                
            } catch (Exception ex) {
                log.error("Failed to update the message because : " +
                        ex.getMessage(),ex);
                // ignore failed application
                //throw new ChangeException(
                //        "Failed to update the message because : " +
                //        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This object represents a remove message change
     */
    public static class RemoveMessageChange implements Change {
        // private member variables
        private String messageId = null;
        
        /**
         * The constructor of the remove message change object.
         *
         * @param messageId The message id for this object.
         */
        public RemoveMessageChange(String messageId) {
            this.messageId = new String(messageId);
        }
        
        
        /**
         * The definition of the apply method.
         */
        public void applyChanges() throws ChangeException {
            try {
                //MessageTransactionLock.getInstance().lock();
                Session session = HibernateUtil.getInstance(
                        MessageServiceManager.class).getSession();
                session.createQuery(
                        "DELETE FROM MessageRpcBody as body WHERE " +
                        "body.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery(
                        "DELETE FROM MessageTxtBody as body WHERE " +
                        "body.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery(
                        "DELETE FROM MessageService as service WHERE " +
                        "service.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery(
                        "DELETE FROM MessageProperty as property WHERE " +
                        "property.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery(
                        "DELETE FROM MessagePrincipal as principal WHERE " +
                        "principal.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery(
                        "DELETE FROM MessageError as error WHERE " +
                        "error.message.id = ?").
                        setString(0,messageId).executeUpdate();
                session.createQuery("DELETE FROM Message as msg WHERE msg.id = ?").
                        setString(0,messageId).executeUpdate();
            } catch (Exception ex) {
                log.error("Failed to failed to remove the message from the db : " +
                        ex.getMessage(),ex);
                // ignore failed application
                //throw new ChangeException(
                //        "Failed to failed to remove the message from the db : " +
                //        ex.getMessage(),ex);
            }
        }
    }
    
    // class constants
    public final static int TEXT_MESSAGE = 1;
    public final static int RPC_MESSAGE = 2;
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(MessageManagerImpl.class.getName());
    
    // private member variable
    private String id = null;
    private Date nextProcessTime = null;
    private String messageQueueName = null;
    private MessageImpl masterMessageImpl = null;
    
    // transaction variables
    private Date originalNextProcessTime = null;
    private String originalMessageQueueName = null;
    private MessageImpl originalMessageImpl = null;
    
    /**
     * Creates a new instance of MessageManagerImpl
     *
     * @param id The id of the message.
     */
    public MessageManagerImpl(String id) throws MessageServiceException {
        this.id = id;
        originalMessageImpl = masterMessageImpl = loadMessage();
    }
    
    
    /**
     * Creates a new instance of MessageManagerImpl
     *
     * @param message The new message to create.
     */
    public MessageManagerImpl(Message newMessage) throws MessageServiceException {
        try {
            this.id = newMessage.getMessageId();
            nextProcessTime = ((MessageImpl)newMessage).
                    getNextProcessDate();
            originalNextProcessTime = ((MessageImpl)newMessage).
                    getNextProcessDate();
            originalMessageImpl = (MessageImpl)newMessage;
            masterMessageImpl = (MessageImpl)newMessage;
            ChangeLog.getInstance().addChange(new AddMessageChange(
                    (MessageImpl)newMessage));
            TransactionManager.getInstance().bindResource(this,true);
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the message " +
                    "from the database : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to create the message " +
                    "in the database : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the id of this messsage.
     *
     * @return The id of the message this object is managing.
     */
    public String getID() {
        return id;
    }
    
    
    /**
     * This method returns the message object.
     *
     * @return The message object.
     * @exception MessageServiceException
     */
    public Message getMessage() throws MessageServiceException {
        return masterMessageImpl;
    }
    
    
    /**
     * This method returns the message object.
     *
     * @return The message object.
     * @exception MessageServiceException
     */
    public void assignToQueue(String queueName) throws MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,true);
            ChangeLog.getInstance().addChange(new AssignMessageToQueueChange(
                    this.id,queueName));
            this.messageQueueName = queueName;
        } catch (Exception ex) {
            log.error("Failed to assign this object to a queue because : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to assign this object to a queue because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the message object.
     *
     * @param updatedMessage The updated message object.
     * @exception MessageServiceException
     */
    public void updateMessage(Message updatedMessage) throws
            MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,true);
            ChangeLog.getInstance().addChange(new UpdateMessageChange(
                    (MessageImpl)updatedMessage));
            nextProcessTime = ((MessageImpl)updatedMessage).
                    getNextProcessDate();
            masterMessageImpl = (MessageImpl)updatedMessage;
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the message because : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to update the message because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible from removing this message from the db.
     *
     * @exception MessageServiceException
     */
    public void remove() throws MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,true);
            ChangeLog.getInstance().addChange(new RemoveMessageChange(
                    this.id));
        } catch (Exception ex) {
            log.error("Failed to failed to remove the message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to failed to remove the message : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the next process time for this message.
     *
     * @return The date message.
     * @exception MessageServiceException
     */
    public Date nextProcessTime() {
        return nextProcessTime;
    }
    
    
    /**
     * This message returns the priority.
     */
    public int getPriority() {
        return this.masterMessageImpl.getPriority();
    }
    
    
    /**
     * This method returns the name of the messaqe queue to which this message
     * is assigned.
     *
     * @return The name of the message queue that this message is assigned to.
     */
    public String getMessageQueueName() {
        return this.messageQueueName;
    }
    
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public synchronized void commit(Xid xid, boolean onePhase) throws
            XAException {
        if (nextProcessTime != null) {
            this.originalNextProcessTime = nextProcessTime;
        }
        if (this.messageQueueName != null) {
            this.originalMessageQueueName = messageQueueName;
        }
        if (this.masterMessageImpl != null) {
            this.originalMessageImpl = masterMessageImpl;
        }
    }
    
    
    /**
     * The resource manager has dissociated this object from the transaction.
     *
     * @param xid The id of the transaction that is getting ended.
     * @param flags The flags associated with this operation.
     * @exception XAException
     */
    public void end(Xid xid, int flags) throws XAException {
    }
    
    
    /**
     * The transaction has been completed and must be forgotten.
     *
     * @param xid The id of the transaction to forget.
     * @exception XAException
     */
    public void forget(Xid xid) throws XAException {
        if (nextProcessTime != null) {
            this.originalNextProcessTime = nextProcessTime;
        }
        if (this.messageQueueName != null) {
            this.originalMessageQueueName = messageQueueName;
        }
        if (this.masterMessageImpl != null) {
            this.originalMessageImpl = masterMessageImpl;
        }
    }
    
    
    /**
     * This method returns the transaction timeout for this object.
     *
     * @return The int containing the transaction timeout.
     * @exception XAException
     */
    public int getTransactionTimeout() throws XAException {
        return -1;
    }
    
    
    /**
     * This method returns true if this object is the resource manager getting
     * queried.
     *
     * @return TRUE if this is the resource manager, FALSE if not.
     * @param xaResource The resource to perform the check against.
     * @exception XAException
     */
    public boolean isSameRM(XAResource xAResource) throws XAException {
        return this == xAResource;
    }
    
    
    /**
     * This is called before a transaction is committed.
     *
     * @return The results of the transaction.
     * @param xid The id of the transaction to check against.
     * @exception XAException
     */
    public int prepare(Xid xid) throws XAException {
        return XAResource.XA_OK;
    }
    
    
    /**
     * This method returns the list of transaction branches for this resource
     * manager.
     *
     * @return The list of resource branches.
     * @param flags The flags
     * @exception XAException
     */
    public Xid[] recover(int flags) throws XAException {
        return null;
    }
    
    
    /**
     * This method is called to roll back the specified transaction.
     *
     * @param xid The id of the transaction to roll back.
     * @exception XAException
     */
    public void rollback(Xid xid) throws XAException {
        nextProcessTime = originalNextProcessTime;
        messageQueueName = originalMessageQueueName;
        masterMessageImpl = originalMessageImpl;
    }
    
    
    /**
     * This method sets the transaction timeout for this resource manager.
     *
     * @return TRUE if the transaction timeout can be set successfully.
     * @param transactionTimeout The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int transactionTimeout) throws
            XAException {
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param flags The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int flags) throws XAException {
        
    }
    
    
    /**
     * The compare to interface used to order this object in the queues.
     *
     * @return -1,0,1 depending on the order of the object.
     * @param o The object to perform the comparison on.
     */
    public int compareTo(Object o) {
        MessageManagerImpl msg =(MessageManagerImpl)o;
        if (msg.nextProcessTime().getTime() > nextProcessTime().getTime()) {
            return -1;
        } else if (nextProcessTime().getTime() > msg.nextProcessTime().getTime()) {
            return 1;
        } else if (msg.getPriority() > getPriority()) {
            return -1;
        } else if (getPriority() > msg.getPriority()) {
            return 1;
        }
        return 0;
    }
    
    
    /**
     * This method returns the message object.
     *
     * @return The message object.
     * @exception MessageServiceException
     */
    private MessageImpl loadMessage() throws MessageServiceException {
        try {
            //MessageTransactionLock.getInstance().lock();
            Session session = HibernateUtil.getInstance(
                    MessageServiceManager.class).getSession();
            
            com.rift.coad.daemon.messageservice.db.Message message =
                    (com.rift.coad.daemon.messageservice.db.Message)session.
                    get(com.rift.coad.daemon.messageservice.db.Message.class,id);
            
            MessageImpl result = null;
            if (message.getMessageType() == MessageManagerImpl.RPC_MESSAGE) {
                RPCMessageImpl rpcMessage = new RPCMessageImpl(message.getId(),
                        new Date(message.getCreated().getTime()),
                        message.getRetries(), new Date(message.getProcessed().
                        getTime()),message.getMessageCreator(),
                        message.getSessionId(), null,message.getFromUrl(),
                        message.getMessageRoutingType(),
                        message.getMessageState());
                MessageRpcBody rpcBody = (MessageRpcBody)session.get(
                        MessageRpcBody.class,message.getId());
                rpcMessage.setMethodBodyXML(rpcBody.getXml());
                if (rpcBody.getExceptionValue() != null) {
                    rpcMessage.setThrowableBytes(rpcBody.
                            getExceptionValue().clone());
                }
                if (rpcBody.getResultValue() != null) {
                    rpcMessage.setResultBytes(
                            rpcBody.getResultValue().clone());
                }
                result = rpcMessage;
            } else {
                
                TextMessageImpl txtMessage = new TextMessageImpl(message.getId(),
                        new Date(message.getCreated().getTime()),
                        message.getRetries(), new Date(message.getProcessed().
                        getTime()),message.getMessageCreator(),
                        message.getSessionId(),null,message.getFromUrl(),
                        message.getMessageRoutingType(),
                        message.getMessageState());
                MessageTxtBody txtBody = (MessageTxtBody)session.get(
                        MessageTxtBody.class,message.getId());
                txtMessage.setTextBody(txtBody.getBody());
                result = txtMessage;
            }
            
            // set from
            result.setFrom(message.getFromUrl());
            result.setPriority(message.getPriority());
            result.setNextProcessDate(new Date(
                    message.getNextProcess().getTime()));
            result.setProcessedDate(new Date(
                    message.getProcessed().getTime()));
            
            // set the target
            if (message.getTarget() != null) {
                result.setTarget(message.getTarget());
            }
            // set the reply flag
            if (message.getReply() == 1) {
                result.setReply(true);
            } else {
                result.setReply(false);
            }
            // set the reply to address
            if (message.getReplyUrl() != null) {
                result.setReplyTo(message.getReplyUrl());
            }
            // set the named queue
            if (message.getTargetNamedQueue() != null) {
                result.setTargetNamedQueue(message.getTargetNamedQueue());
            }
            if (message.getReplyNamedQueue() != null) {
                result.setReplyNamedQueue(message.getReplyNamedQueue());
            }
            // the correlation id
            if (message.getCorrelationId() != null) {
                result.setCorrelationId(message.getCorrelationId());
            }
            
            // set the services
            List dbServices = session.createQuery(
                    "FROM MessageService as service WHERE " +
                    "service.message.id = ?").
                    setString(0,message.getId()).list();
            String[] services = new String[dbServices.size()];
            int index = 0;
            for (Iterator iter = dbServices.iterator();
            iter.hasNext(); index++) {
                services[index] = ((MessageService)iter.next()).getService();
            }
            result.setServices(services);
            
            // set properties
            List dbProperties = session.createQuery(
                    "FROM MessageProperty as property WHERE " +
                    "property.message.id = ?").
                    setString(0,message.getId()).list();
            for (Iterator iter = dbProperties.iterator();
            iter.hasNext();) {
                MessageProperty property = (MessageProperty)iter.next();
                if (property.getBoolValue() != null) {
                    result.setBooleanProperty(property.getName(),
                            ((Integer)property.getBoolValue()).intValue() == 1 ?
                                true:false);
                } else if (property.getByteValue() != null) {
                    result.setByteProperty(property.getName(),
                            ((Integer)property.getByteValue()).byteValue());
                } else if (property.getIntValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getIntValue());
                } else if (property.getLongValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getLongValue());
                } else if (property.getDoubleValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getDoubleValue());
                } else if (property.getFloatValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getFloatValue());
                } else if (property.getObjectValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getObjectValue());
                } else if (property.getStringValue() != null) {
                    result.setPropertyValue(property.getName(),
                            property.getStringValue());
                }
                
            }
            
            List principals = new ArrayList();
            List dbPrincipals = session.createQuery(
                    "FROM MessagePrincipal as principal WHERE " +
                    "principal.message.id = ?").
                    setString(0,message.getId()).list();
            for (Iterator iter = dbPrincipals.iterator(); iter.hasNext();) {
                MessagePrincipal principal = (MessagePrincipal)iter.next();
                principals.add(principal.getPrincipalValue());
            }
            result.setMessagePrincipals(principals);
            
            List dbErrors = session.createQuery(
                    "FROM MessageError as error WHERE " +
                    "error.message.id = ?").
                    setString(0,message.getId()).list();
            for (Iterator iter = dbErrors.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.messageservice.db.MessageError
                        dbMessageError =
                        (com.rift.coad.daemon.messageservice.db.MessageError)
                        iter.next();
                com.rift.coad.daemon.messageservice.MessageError messageError =
                        new com.rift.coad.daemon.messageservice.MessageError(
                        new Date(dbMessageError.getErrorDate().getTime()),
                        dbMessageError.getErrorLevel(),dbMessageError.getMsg());
                ((MessageImpl)result).addError(messageError);
            }
            
            if (message.getMessageQueue() != null) {
                originalMessageQueueName = messageQueueName =
                        message.getMessageQueue().getMessageQueueName();
            }
            if (message.getNextProcess() != null) {
                this.nextProcessTime = this.originalNextProcessTime =
                        new Date(message.getNextProcess().getTime());
            }
            
            return result;
        } catch (Exception ex) {
            log.error("Failed to load the message because : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to load the message because : " +
                    ex.getMessage(),ex);
        }
    }
    
}
