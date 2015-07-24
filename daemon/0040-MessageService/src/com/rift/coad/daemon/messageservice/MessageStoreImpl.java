/*
 * MessageService: The message service daemon
 * Copyright (C) 2007 2015 Burntjam
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
 * MessageStoreImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.message.MessageImpl;
import com.rift.coad.daemon.messageservice.message.RPCMessageImpl;
import com.rift.coad.daemon.messageservice.message.TextMessageImpl;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.message.MessageManagerImpl;

/**
 * This class is responsible for implementing the message store interface.
 *
 * @author Brett Chaldecott
 */
public class MessageStoreImpl implements MessageStore {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(MessageStoreImpl.class.getName());
    
    /**
     * Creates a new instance of MessageStoreImpl 
     */
    public MessageStoreImpl() {
    }
    
    
    /**
     * This method adds a message to the message store of a message service.
     *
     * @param message The message to add to the store.
     * @exception MessageServiceException
     * @exception RemoteException
     */
    public void addMessage(Message newMessage) throws MessageServiceException, 
            RemoteException {
        if ((newMessage instanceof RPCMessage) && 
                (((RPCMessage)newMessage).getMethodBodyXML() == null)) {
            throw new MessageServiceException("The xml body is not set");
        } else if ((newMessage instanceof TextMessage) && 
                (((TextMessage)newMessage).getTextBody() == null)) {
            throw new MessageServiceException("The text body is not set");
        }
        try {
            log.debug("Receive message : " + newMessage.getMessageId());
            IDLock.getInstance().lock(newMessage.getMessageId());
            ((MessageImpl)newMessage).setNextProcessDate(new Date());
            MessageManager messageManager = MessageManagerFactory.getInstance().
                    getMessageManager(newMessage);
            MessageQueue messageQueue = MessageQueueManager.getInstance().
                    getQueue(MessageQueueManager.UNSORTED);
            ((MessageManagerImpl)messageManager).assignToQueue(
                    MessageQueueManager.UNSORTED);
            messageQueue.addMessage(messageManager);
            log.debug("Message added : " + newMessage.getMessageId());
        } catch (Exception ex) {
            log.error("Failed to add the message : " + 
                    ex.getMessage(),ex);
            throw new MessageServiceException("Failed to add the message : " + 
                    ex.getMessage(),ex);
        }
    }
}
