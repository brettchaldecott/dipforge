/*
 * MessageQueueClient: The message queue client library
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
 * RPCMessageClient.java
 */

// package path
package com.rift.coad.daemon.messageservice.rpc;

// java imports
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.naming.InitialContext;

/**
 * This object is responsible for establishing a connection to the Message
 * Service and for setting the appropriate RPC proxy and handler so that
 * messages can be constructed appropriatly.
 *
 * @author Brett Chaldecott
 */
public class RPCMessageClient {
    
    /**
     * Creates a new instance of RPCMessageClient
     */
    private RPCMessageClient() {
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param targetURL The target url for the message.
     * @exception RPCMessageClientException
     */
    public static Object create(String from, Class targetInterface,
            Class asyncInterface, String targetURL) throws
            RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from,targetInterface,
                targetURL,true);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param services The list of services for this rpc message
     * @param broadcast If true this message will be sent to all daemons suppling
     *      the given service.
     * @exception RPCMessageClientException
     */
    public static Object create(String from, Class targetInterface,
            Class asyncInterface, List services, boolean broadcast) throws
            RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from, targetInterface,
                services, broadcast,true);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly. The message will only go one way no result will be
     * received.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param targetURL The target url for the message.
     * @exception RPCMessageClientException
     */
    public static Object createOneWay(String from, Class targetInterface,
            Class asyncInterface, String targetURL) throws
            RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from,targetInterface,
                targetURL,false);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly. The message will only go one way no result will be
     * received.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param services The list of services for this rpc message
     * @param broadcast If true this message will be sent to all daemons suppling
     *      the given service.
     * @exception RPCMessageClientException
     */
    public static Object createOneWay(String from, Class targetInterface,
            Class asyncInterface, List services, boolean broadcast) throws
            RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from, targetInterface,
                services, broadcast,false);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param targetURL The target url for the message.
     * @param correlationId The correlation id for this message.
     * @exception RPCMessageClientException
     */
    public static Object create(String from, Class targetInterface,
            Class asyncInterface, String targetURL, String correlationId) throws
            RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from, targetInterface,
                targetURL, correlationId);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param services The list of services for this rpc message
     * @param broadcast If true this message will be sent to all daemons suppling
     *      the given service.
     * @param correlationId The correlation id for this message.
     * @exception RPCMessageClientException
     */
    public static Object create(String from, Class targetInterface,
            Class asyncInterface, List services, boolean broadcast,
            String correlationId) throws RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(from, targetInterface,
                services, broadcast, correlationId);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param jndiURL The url of the message producer.
     * @param context The context of the message service.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param targetURL The target url for the message.
     * @exception RPCMessageClientException
     */
    public static Object create(InitialContext context, String jndiURL,
            String from, Class targetInterface, Class asyncInterface,
            String targetURL) throws RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(context, jndiURL,
            from, targetInterface, targetURL);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is called to setup a async interface so that messages can
     * be sent properly.
     *
     * @return The reference that was created.
     * @param context The context of the message service.
     * @param jndiURL The url of the message producer.
     * @param from The address all the requests will come from.
     * @param targetInterface The target interface.
     * @param asyncInterface The asynchronis interface.
     * @param services The list of services for this rpc message
     * @param broadcast If true this message will be sent to all daemons suppling
     *      the given service.
     * @exception RPCMessageClientException
     */
    public static Object create(InitialContext context, String jndiURL,
            String from, Class targetInterface, Class asyncInterface, List services,
            boolean broadcast) throws RPCMessageClientException {
        verify(targetInterface,asyncInterface);
        RPCMessageHandler handler = new RPCMessageHandler(context, jndiURL,
            from, targetInterface, services, broadcast);
        return Proxy.newProxyInstance(
                asyncInterface.getClassLoader(),
                new Class[] {asyncInterface},handler);
    }
    
    
    /**
     * This method is responsible for verifying the target interface and async
     * interface in sync.
     *
     * @param targetInterface The interface the call are made onto.
     * @param asyncInterface The async interface the calls are made with.
     * @exception RPCMessageClientException
     */
    private static void verify(Class targetInterface, Class asyncInterface)
    throws RPCMessageClientException {
        Method[] methods = asyncInterface.getMethods();
        for (int index = 0; index < methods.length; index++) {
            Method method = methods[index];
            try {
                targetInterface.getMethod(method.getName(),
                        method.getParameterTypes());
            } catch (Exception ex) {
                throw new RPCMessageClientException(
                        "Could not validate the methods : " + ex.getMessage(),
                        ex);
            }
            if (method.getReturnType() != java.lang.String.class) {
                throw new RPCMessageClientException(
                        "The return type of the async interface must be a " +
                        "String. As it will contain the new Message Id");
            }
        }
    }
}
