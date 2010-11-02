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
 * TextClient1Impl.java
 */

// package path
package com.rift.coad.daemon.messageservice.text.test;

// java imports
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// coadunation imports
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.TextMessage;

/**
 * The text client implementation
 *
 * @author Brett Chaldecott
 */
public class TextClient1Impl implements TextClient1 {
    // private member variables
    private Context context = null;
    private int results = 0;
    
    /** Creates a new instance of TextClient1Impl */
    public TextClient1Impl() throws TextTestException {
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            throw new TextTestException("Failed to instanciate the text test " +
                    "client : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to init the tests.
     *
     * @param numTests The number of tests.
     * @exception TextTestException
     */
    public void startTests(long numTests) throws TextTestException {
        try {
            System.out.println("The beginning of the start test method");
            results = 0;
            MessageProducer messageProducer =
                    (MessageProducer)PortableRemoteObject.narrow(
                    context.lookup(MessageProducer.JNDI_URL),
                    MessageProducer.class);
            Producer producer = messageProducer.createProducer(JNDI_NAME);
            for (int count = 0; count < numTests; count++) {
                TextMessage textMessage = producer.createTextMessage(
                        Message.POINT_TO_POINT);
                textMessage.setTarget(TextServer1Impl.JNDI_NAME);
                textMessage.setReply(true);
                textMessage.setTextBody("This is the text body : " + count);
                producer.submit(textMessage);
            }
            
            System.out.println("Wait on result");
            
            synchronized (this) {
                Date startTime = new Date();
                while(results < numTests) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 1800000)) {
                        throw new TextTestException(
                                "Only received [" + results 
                                + "] was expecting [" + numTests + "]");
                    }
                    wait(1000);
                }
            }
            System.out.println("After result");
        } catch (Exception ex) {
            System.out.println("Failed to run the test : " + ex.getMessage());
            throw new TextTestException("Failed to run the test : " +
                    ex.getMessage(),ex);
        }
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
        textMessage.setTextBody("This is a new message : " +
                new Date().getTime());
        textMessage.acknowledge();
        synchronized (this) {
            results++;
            notify();
        }
        return msg;
    }
}
