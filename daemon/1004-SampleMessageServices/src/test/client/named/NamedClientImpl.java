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

package test.client.named;

// java imports
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

// coadunation imports
import com.rift.coad.daemon.messageservice.MessageService;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.TextMessage;

/**
 * This object is responsible for implementing named client interface.
 *
 * @author Brett Chaldecott
 */
public class NamedClientImpl implements NamedClient {
    
    // private member variables
    private Context context = null;
    private int results = 0;
    
    /** Creates a new instance of NamedClient1Impl */
    public NamedClientImpl() throws NamedTestException{
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
    public void runBasicTest(String text) throws NamedTestException {
        System.out.println("The beginning of the start test method");
        MessageProducer messageProducer;
        try {
            messageProducer = (MessageProducer) PortableRemoteObject.
                    narrow(context.lookup(MessageProducer.JNDI_URL),
                    MessageProducer.class);
            
            Producer producer = messageProducer.createProducer(JNDI_URL);
            TextMessage textMessage = producer.createTextMessage(
                    Message.POINT_TO_POINT);
            textMessage.setTarget(MessageService.JNDI_URL);
            textMessage.setTargetNamedQueue("test");
            textMessage.setReply(false);
            textMessage.setTextBody(text);
            producer.submit(textMessage);
            
            System.out.println("After result");
        } catch (Exception ex) {
            throw new NamedTestException("The test failed:", ex);
        }
    }
    
}
