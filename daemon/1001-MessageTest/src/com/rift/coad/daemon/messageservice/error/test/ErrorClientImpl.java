/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2006  Rift IT Contracting
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
 * ErrorClient.java
 */

// package path
package com.rift.coad.daemon.messageservice.error.test;


// java imports
import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

// log imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.daemon.messageservice.QueueManager;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.TextMessage;
import com.rift.coad.daemon.messageservice.named.NamedQueueClient;


/**
 * The definition of the error client interface interface.
 *
 * @author Brett Chaldecott
 */
public class ErrorClientImpl implements ErrorClient, BeanRunnable {
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(ErrorClientImpl.class.getName());
    
    
    // private member variable
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private Context context = null;
    private int count = 0;
    
    /**
     * The constructor of the error clietn
     *
     * @exception ErrorTestException
     */
    public ErrorClientImpl() throws ErrorTestException{
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            throw new ErrorTestException("Failed to instanciate the error " +
                    "client : " + ex.getMessage());
        }
    }
    
    
    /**
     * This method is called to test the error routing.
     *
     * @param number The number of error messages.
     * @exception ErrorTestException
     * @exception RemoteException
     */
    public void testErrorRouting(int number) throws ErrorTestException,
            RemoteException {
        try {
            System.out.println("The beginning of the start test method");
            count = 0;
            MessageProducer messageProducer =
                    (MessageProducer)PortableRemoteObject.narrow(
                    context.lookup(MessageProducer.JNDI_URL),
                    MessageProducer.class);
            Producer producer = messageProducer.createProducer(JNDI_URL);
            for (int count = 0; count < number; count++) {
                TextMessage textMessage = producer.createTextMessage(
                        Message.POINT_TO_POINT);
                textMessage.setTarget("failure");
                textMessage.setReply(false);
                textMessage.setTextBody("This is the text body : " + count);
                producer.submit(textMessage);
            }
            
            System.out.println("Wait on result");
            
            synchronized (this) {
                Date startTime = new Date();
                while(count < number) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 180000)) {
                        throw new ErrorTestException(
                                "The test timed out \n" + 
                                "Number results [" + count + "].");
                    }
                    this.wait(1000);
                }
            }
            System.out.println("After result");
        } catch (ErrorTestException ex){
            throw ex;
        } catch (Exception ex) {
            System.out.println("Failed to run the test : " + ex.getMessage());
            throw new ErrorTestException("Failed to run the test : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform the processing.
     */
    public void process() {
        NamedQueueClient client = null;
        try {
            client = NamedQueueClient.create(QueueManager.DEAD_LETTER);
        } catch (Exception ex) {
            log.error("Failed to create the named client : " + ex.getMessage(),
                    ex);
            return;
        }
        while(!state.isTerminated()) {
            try {
                log.info("Wait for a message.");
                Message message = client.receive(1000);
                if (message == null) {
                    continue;
                }
                log.info("Receive a dead letter : " + message.getMessageId());
                count++;
                synchronized(this) {
                    this.notify();
                }
            } catch (Exception ex) {
                log.error("Failed to retrieve text message : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method is called to terminate the error client
     */
    public void terminate() {
        state.terminate(true);
    }
}
