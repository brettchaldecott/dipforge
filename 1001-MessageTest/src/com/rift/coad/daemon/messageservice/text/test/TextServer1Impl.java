/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 Rift IT Contracting
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
 * TextServer1Impl.java
 */

// package path
package com.rift.coad.daemon.messageservice.text.test;

// java imports
import java.util.Date;

// coaduantion imports
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageHandler;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.TextMessage;

/**
 * This object is responsible for representing a TextServer handler.
 *
 * @author Brett Chaldecott
 */
public class TextServer1Impl implements MessageHandler {
    
    public final static String JNDI_NAME = "messagetest/TextServer1";
    
    /** Creates a new instance of TextServer1Impl */
    public TextServer1Impl() {
    }
    
    
    /**
     * This method is called to process a message.
     *
     * @return The processed message. Cannot use IN/OUT as RMI does not support 
     *          it.
     * @param msg The message to perform the processing on.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public Message processMessage(Message msg) throws MessageServiceException {
        TextMessage textMessage = (TextMessage)msg;
        textMessage.setTextBody("This is the response from the server : " + 
                new Date().getTime());
        textMessage.acknowledge();
        return msg;
    }
}
