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
 * BroadcastClientImpl.java
 */

package com.rift.coad.daemon.messageservice.broadcast.test;

// java imports
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// coadunation imports
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

/**
 * This client is responsible for testing the broad cast funationality
 *
 * @author Brett Chaldecott
 */
public class BroadcastClientImpl implements BroadcastClient {
    
    // number of results
    int results = 0;
    
    /** 
     * Creates a new instance of BroadcastClientImpl
     */
    public BroadcastClientImpl() {
    }
    
    
    /**
     * The definition of the run test method
     *
     * @param num The number of message to send
     * @exception RemoteException
     * @exception BroadcastException
     */
    public void runTest(int num) throws RemoteException, BroadcastException {
        System.out.println("The runtime class");
        try {
            List services = new ArrayList();
            services.add("test2");
            BroadcastServerAsync async1 = (BroadcastServerAsync)
                    RPCMessageClient.create(
                    BroadcastClient.JNDI_URL,BroadcastServer.class,
                    BroadcastServerAsync.class,services,true);
            
            results = 0;
            for (int count = 0; count < num; count++) {
                System.out.println("Make call");
                async1.testMethod("Message number : " + count);
            }
            
            synchronized (this) {
                Date startTime = new Date();
                while(results < (num * 2)) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 180000)) {
                        throw new BroadcastException(
                                "The test timed out \n" + 
                                "results " + results + "\n");
                    }
                    wait(1000);
                }
            }
        } catch (BroadcastException ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw new BroadcastException(
                    "Failed to test the message service : " + ex.getMessage());
        }
    }

    
    /**
     * This method is responsible for dealing with a successfull result.
     *
     * @param messageId The id of the message the result is from.
     * @param correllationId The correllation id.
     * @param result The result of this method.
     * @exception RemoteException
     */
    public void onSuccess(String messageId, String correllationId, 
            Object result) throws RemoteException {
        synchronized (this) {
            if (result instanceof String) {
                System.out.println("Result is : " + result);
                results++;
                notify();
            }
        }
    }
    
    
    /**
     * This method is called to deal with a failure.
     *
     * @param messageId The id of the message that caused this problem.
     * @param correllationId The correllation id for the message.
     * @param caught The exception that has been caught.
     * @exception RemoteException
     */
    public void onFailure(String messageId, String correllationId, 
            Throwable caught) throws RemoteException {
        
    }
}
