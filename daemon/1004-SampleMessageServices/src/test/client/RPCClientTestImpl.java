/*
 * Timer: The timer class
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
 * RPCClientTestImpl.java
 */

package test.client;

import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.TextMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import test.server.RPCServerTest;

public class RPCClientTestImpl implements RPCClientTest {
    
    public String testID = "";
    
    public RPCClientTestImpl() {
    }
    
    public void runBasicTest(String testString) throws RemoteException,
            MessageTestException {
        try {
            System.out.println("The runtime class");
            RPCServerTestAsync async = (RPCServerTestAsync)RPCMessageClient.create(
                    "RPCClientTest",RPCServerTest .class,
                    RPCServerTestAsync.class,"RPCServerTest");
            
            testID = async.testMethod(testString);
            System.out.println("End of method : " + testID);
        } catch (Throwable ex) {
            System.out.println("The test run failed : " +
                    ex.getMessage());
            ex.printStackTrace(System.out);
            throw new MessageTestException("The test run failed : " +
                    ex.getMessage(),ex);
        }
    }
    
    public synchronized void onSuccess(String messageId, String correllationId,
            Object result) throws RemoteException {
        
        if (messageId == testID) {
            System.out.println("ID: " + result.toString());
        }
        
    }
    
    public synchronized void onFailure(String messageId, String correllationId,
            Throwable caught) throws RemoteException {
        
        System.out.println("The exception is not a message test " +
                "exception : " + caught.getClass().getName());
        
    }
    
    public void runBasicMessageTest(String testString) throws RemoteException,
            MessageTestException {
        System.out.println("The beginning of the start test method");
        Context context;
        try {
            context = new InitialContext();
            MessageProducer messageProducer =
                    (MessageProducer)PortableRemoteObject.narrow(
                    context.lookup(MessageProducer.JNDI_URL),
                    MessageProducer.class);
            Producer producer = messageProducer.createProducer("RPCClientTest");
            TextMessage textMessage = producer.createTextMessage(
                    Message.POINT_TO_POINT);
            textMessage.setTarget("TextServerTest");
            textMessage.setReply(true);
            textMessage.setTextBody(testString);
            producer.submit(textMessage);
        } catch (Exception ex) {
            throw new MessageTestException("Text message failure:",ex);
        }
    }
    
    public Message processMessage(Message message) throws RemoteException,
            MessageServiceException {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("Message is : " + textMessage.getTextBody());
        textMessage.acknowledge();
        return textMessage;
    }
}