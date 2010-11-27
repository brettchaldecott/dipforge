/*
 * MessageService: The message service daemon
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
 * RoutingClientImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.routing.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


// coadunation imports
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;




/**
 * This object provides the ability to test the routing in coadunation.
 *
 * @author Brett Chaldecott
 */
public class RoutingClientImpl implements RoutingClient {
    
    // private member variables
    private int testResult = 0;
    
    /** 
     * Creates a new instance of RoutingClientImpl
     */
    public RoutingClientImpl() {
    }
    
    
    /**
     * This method is called to test the routing.
     *
     * @param target The target the tests will be routed to.
     * @param number The number of messages to send.
     * @exception RoutingTestException
     * @exception RemoteException
     */
    public void testRouting(String target, int number) throws 
            RoutingTestException, RemoteException {
        System.out.println("The runtime class");
        try {
            RoutingServerAsync async1 = (RoutingServerAsync)RPCMessageClient.
                    create(RoutingClient.JNDI_URL,RoutingServer.class,
                    RoutingServerAsync.class,target);
            
            for (int count = 0; count < number; count++) {
                async1.testRoutingMethod("Routing message : " + count);
            }
            
            synchronized (this) {
                Date startTime = new Date();
                while(testResult < number) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 180000)) {
                        throw new RoutingTestException(
                                "The test timed out \n" + 
                                "Number results [" + testResult + "].");
                    }
                    wait(1000);
                }
            }
        } catch (RoutingTestException ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw new RoutingTestException(
                    "Failed to test the message service : " + ex.getMessage());
        }
    }
    
    
    /**
     * The result result of the call.
     *
     * @param messageId The id of the message making the call.
     * @param correllationId The correllation id for this message.
     * @param result The result object for this call.
     */
    public synchronized void onSuccess(String messageId, String correllationId, 
            Object result) throws RemoteException {
        testResult++;
        notify();
    }
    
    
    /**
     * This method is responsible for processing the results of a failure.
     *
     * @param messageId The id of the message.
     * @param correllationId The id of the correlation.
     * @exception caught The caught message.
     */
    public void onFailure(String messageId, String correllationId, 
            Throwable caught) throws RemoteException {
        
    }
    
    
}
