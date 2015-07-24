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
 * ProducerImpl.java
 */

// the package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.transaction.Status;

// logging import
import org.apache.log4j.Logger;

// message service imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.daemon.messageservice.message.MessageImpl;
import com.rift.coad.daemon.messageservice.message.RPCMessageImpl;
import com.rift.coad.daemon.messageservice.message.TextMessageImpl;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.message.MessageManagerImpl;

/**
 * This object is responsible for producing new message and submitting them to
 * the message service for processing.
 *
 * @author Brett Chaldecott
 */
public class ProducerImpl implements Producer {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(ProducerImpl.class.getName());
    
    
    
    // the private member variables
    private Context context = null;
    private String from = null;
    private UserTransaction ut = null;
    
    /** 
     * Creates a new instance of ProducerImpl 
     *
     * @param from The from address for the messages.
     * @exception MessageServiceException
     */
    public ProducerImpl(String from) throws MessageServiceException {
        try {
            this.from = from;
            context = new InitialContext();
            ut = (UserTransaction)context.lookup("java:comp/UserTransaction");
        } catch (Exception ex) {
            log.error("Failed to instanciate the producer : " + ex.getMessage(),
                    ex);
            throw new MessageServiceException(
                    "Failed to instanciate the producer : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method is responsible for creating a new text message for the
     * message service.
     *
     * @return A newly created text message.
     * @param type The type of message.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public TextMessage createTextMessage(int type) throws RemoteException, 
            MessageServiceException {
        try {
            ThreadPermissionSession session = SessionManager.getInstance().
                    getSession();
            Set principals = new HashSet();
            for (Iterator iter = session.getPrincipals().iterator(); 
            iter.hasNext();) {
                principals.add(iter.next());
            }
            
            TextMessageImpl textMessage = new TextMessageImpl(
                    RandomGuid.getInstance().getGuid(),new Date(),
                    0,new Date(),session.getUser().getName(),
                    session.getUser().getSessionId(),
                    new ArrayList(principals),from,type,
                    Message.UNDELIVERED);
            textMessage.setNextProcessDate(new Date());
            return textMessage;
        } catch (Exception ex) {
            log.error("Failed to create the text message : " + ex.getMessage(),
                    ex);
            throw new MessageServiceException(
                    "Failed to create the text message : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method is responsible for creating a new RPC message for the
     * message service.
     *
     * @return A newly created text message.
     * @param type The type of message.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public RPCMessage createRPCMessage(int type) throws RemoteException, 
            MessageServiceException {
        try {
            ThreadPermissionSession session = SessionManager.getInstance().
                    getSession();
            Set principals = new HashSet();
            for (Iterator iter = session.getPrincipals().iterator(); 
            iter.hasNext();) {
                principals.add(iter.next());
            }
            RPCMessageImpl rpcMessage = new RPCMessageImpl(
                    RandomGuid.getInstance().getGuid(),new Date(),
                    0,new Date(),session.getUser().getName(),
                    session.getUser().getSessionId(),
                    new ArrayList(principals),from,type,
                    Message.UNDELIVERED);
            rpcMessage.setNextProcessDate(new Date());
            return rpcMessage;
        } catch (Exception ex) {
            log.error("Failed to create the rpc message : " + ex.getMessage(),
                    ex);
            throw new MessageServiceException(
                    "Failed to create the rpc message : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method is responsible for submitting a new message for processing by
     * the message service.
     *
     * @param newMessage The new message to be processed.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void submit(Message newMessage) throws RemoteException, 
            MessageServiceException {
        if ((newMessage instanceof RPCMessage) && 
                (((RPCMessage)newMessage).getMethodBodyXML() == null)) {
            throw new MessageServiceException("The xml body is not set");
        } else if ((newMessage instanceof TextMessage) && 
                (((TextMessage)newMessage).getTextBody() == null)) {
            throw new MessageServiceException("The text body is not set");
        }
        try {
            ((MessageImpl)newMessage).setNextProcessDate(new Date());
            MessageManager messageManager = MessageManagerFactory.getInstance().
                    getMessageManager(newMessage);
            MessageQueue messageQueue = MessageQueueManager.getInstance().
                    getQueue(MessageQueueManager.UNSORTED);
            ((MessageManagerImpl)messageManager).assignToQueue(
                    MessageQueueManager.UNSORTED);
            messageQueue.addMessage(messageManager);
        } catch (Throwable ex) {
            log.error("Failed to add the message : " + 
                    ex.getMessage(),ex);
            throw new MessageServiceException("Failed to add the message : " + 
                    ex.getMessage(),ex);
        }
    }

}
