/*
 * MessageTest: This is a test message service library.
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
 * NamedClient1Impl.java
 */

package com.rift.coad.daemon.messageservice.named.test;

// java imports
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// coadunation imports
import com.rift.coad.daemon.messageservice.MessageService;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.TextMessage;
import com.rift.coad.daemon.messageservice.rpc.test.MessageTestException;

/**
 * This object is responsible for implementing named client interface.
*
 * @author Brett Chaldecott
 */
public class NamedClient1Impl implements NamedClient1 {
    
    // private member variables
    private Context context = null;
    private int results = 0;
    
    /** Creates a new instance of NamedClient1Impl */
    public NamedClient1Impl() throws NamedTestException{
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            throw new NamedTestException("Failed to instanciate the named test " +
                    "client : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to run a basic named message test.
     *
     * @param numMessages The number of messages to test the queues with.
     * @exception MessageTestException
     */
    public void runBasicTest(int numMessages) throws NamedTestException {
        try {
            System.out.println("The beginning of the start test method");
            NamedSingleton.getInstance().resetCount();
            MessageProducer messageProducer =
                    (MessageProducer)PortableRemoteObject.narrow(
                    context.lookup(MessageProducer.JNDI_URL),
                    MessageProducer.class);
            Producer producer = messageProducer.createProducer(JNDI_URL);
            for (int count = 0; count < numMessages; count++) {
                TextMessage textMessage = producer.createTextMessage(
                        Message.POINT_TO_POINT);
                textMessage.setTarget(MessageService.JNDI_URL);
                textMessage.setTargetNamedQueue("test");
                textMessage.setReply(false);
                textMessage.setTextBody("This is the text body : " + count);
                producer.submit(textMessage);
            }
            
            System.out.println("Wait on result");
            
            if (false == 
                    NamedSingleton.getInstance().checkCount(numMessages,180000)) {
                throw new NamedTestException(
                        "Named test failed did not receive enough messages");
            }
            System.out.println("After result");
        } catch (NamedTestException ex){
            throw ex;
        } catch (Exception ex) {
            System.out.println("Failed to run the test : " + ex.getMessage());
            throw new NamedTestException("Failed to run the test : " +
                    ex.getMessage(),ex);
        }
    }
    
}
