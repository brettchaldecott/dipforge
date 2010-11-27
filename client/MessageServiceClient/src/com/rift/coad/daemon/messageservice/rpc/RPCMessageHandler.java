/*
 * MessageQueueClient: The message queue client library
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
 * RPCMessageHandler.java
 */

// the package path
package com.rift.coad.daemon.messageservice.rpc;

// java imports
import java.util.List;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.naming.Context;
import javax.naming.InitialContext;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.Producer;
import com.rift.coad.daemon.messageservice.MessageProducer;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object handles the creation of a RPC message.
 *
 * @author Brett Chaldecott
 */
public class RPCMessageHandler implements InvocationHandler {
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(RPCMessageHandler.class.getName());
    
    // private member variables
    private Class targetInterface = null;
    private Producer producer = null;
    private String targetURL = null;
    private String[] services = null;
    private boolean reply = true;
    private boolean broadcast = false;
    private String correlationId = null;
    private boolean usedCorrelationId = false;
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param targetURL The target url for the request.
     * @param reply TRUE if the message should reply, FALSE if it a oneway call.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(String from, Class targetInterface, 
            String targetURL, boolean reply) throws RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance().getConnection(MessageProducer.class,
                    MessageProducer.JNDI_URL);
            producer = messageProducer.createProducer(from);
            this.targetURL = targetURL;
            this.reply = reply;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param services The list of services.
     * @param broadcast True if the messages must be sent all the systems that
     *      supply the listed services.
     * @param reply TRUE if the message should reply, FALSE if it a oneway call.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(String from, Class targetInterface, 
            List services, boolean broadcast, boolean reply) throws 
            RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance().getConnection(MessageProducer.class,
                    MessageProducer.JNDI_URL);
            producer = messageProducer.createProducer(from);
            copyServices(services);
            this.broadcast = broadcast;
            this.reply = reply;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param targetURL The target url for the request.
     * @param correlationId The correlation id for the message.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(String from, Class targetInterface, 
            String targetURL, String correlationId) throws 
            RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance().getConnection(MessageProducer.class,
                    MessageProducer.JNDI_URL);
            producer = messageProducer.createProducer(from);
            this.targetURL = targetURL;
            this.correlationId = correlationId;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param services The list of services.
     * @param broadcast True if the messages must be sent all the systems that
     *      supply the listed services.
     * @param correlationId The correlation id for the message.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(String from, Class targetInterface, 
            List services, boolean broadcast, String correlationId) throws 
            RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance().getConnection(MessageProducer.class,
                    MessageProducer.JNDI_URL);
            producer = messageProducer.createProducer(from);
            copyServices(services);
            this.broadcast = broadcast;
            this.correlationId = correlationId;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param context The context that will be used to retrieve the service.
     * @param jndiURL The url of the MessageProducer.
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param targetURL The target url for the request.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(InitialContext context, String jndiURL,
            String from, Class targetInterface, String targetURL) 
            throws RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance(context).getConnection(MessageProducer.class,
                    jndiURL);
            producer = messageProducer.createProducer(from);
            this.targetURL = targetURL;
            this.reply = false;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /** 
     * Creates a new instance of RPCMessageHandler 
     *
     * @param context The context that will be used to retrieve the service.
     * @param jndiURL The url of the MessageProducer.
     * @param from The address the message will come from.
     * @param targetInterface The interface the message will wrap to.
     * @param services The list of services the message will be sent to.
     * @param broadcast True if the messages must be sent all the systems that
     *      supply the listed services.
     * @exception RPCMessageClientException
     */
    public RPCMessageHandler(InitialContext context, String jndiURL,
            String from, Class targetInterface, List services, boolean broadcast) 
            throws RPCMessageClientException {
        try {
            this.targetInterface = targetInterface;
            MessageProducer messageProducer = (MessageProducer)ConnectionManager
                    .getInstance(context).getConnection(MessageProducer.class,
                    jndiURL);
            producer = messageProducer.createProducer(from);
            copyServices(services);
            this.reply = false;
            this.broadcast = broadcast;
        } catch (Exception ex) {
            throw new RPCMessageClientException(
                    "Failed to init the message handler : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method is responsible for handling the invocation request.
     *
     * @return The results.
     * @param proxy The proxy that the call is being made on.
     * @param method The method that the call has been made on.
     * @param args The arguments passed to that method.
     * @exception Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws 
            Throwable {
        if (correlationId != null && usedCorrelationId) {
            throw new RPCMessageClientException(
                    "The correlation Id has been used cannot re-use this " +
                    "object.");
        }
        usedCorrelationId = true;
        try {
            RPCMessage message = null;
            if (targetURL != null) {
                message = producer.createRPCMessage(Message.POINT_TO_POINT);
                message.setTarget(targetURL);
            } else if (broadcast == false) {
                message = producer.createRPCMessage(Message.POINT_TO_SERVICE);
                message.setServices(this.services);
            } else {
                message = producer.createRPCMessage(
                        Message.POINT_TO_MULTI_SERVICE);
                message.setServices(this.services);
            }
            message.setReply(reply);
            message.setCorrelationId(correlationId);
            
            Method targetMethod = this.targetInterface.getMethod(
                    method.getName(),method.getParameterTypes());
            message.defineMethod(targetMethod.getReturnType(),
                    method.getName(),method.getParameterTypes());
            message.setArguments(args);
            
            producer.submit(message);
            return message.getMessageId();
        } catch (Exception ex) {
            log.error("Failed to setup the RPC message because : " + 
                    ex.getMessage(),ex);
            throw new RPCMessageClientException(
                    "Failed to setup the RPC message because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method copyies the services to a string array.
     */
    private void copyServices(List serviceList) {
        services = new String[serviceList.size()];
        for (int index = 0; index < serviceList.size(); index++) {
            services[index] = (String)serviceList.get(index);
        }
    }
    
}
