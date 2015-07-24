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
 * RPCClient1Impl.java
 */

// package path
package com.rift.coad.daemon.messageservice.rpc.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


// coadunation imports
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

/**
 * This object implements the RPC client implementation.
 *
 * @author Brett Chaldecott
 */
public class RPCClient2Impl implements RPCClient2 {
    
    // private member variables
    int onewayResult = 0;
    
    /** Creates a new instance of RPCClient1Impl */
    public RPCClient2Impl() {
    }
    
    
    /**
     * This object runs the test.
     *
     * @param numMessage The number of message.
     * @exception MessageTestException
     */
    public void runOneWayTest(int numMessages) throws RemoteException,
            MessageTestException {
        System.out.println("The runtime class");
        try {
            RPCClient2Async async2 = (RPCClient2Async)RPCMessageClient.createOneWay(
                    "messagetest/RPCClient2",RPCClient2.class,
                    RPCClient2Async.class,"messagetest/RPCClient2");
            onewayResult = 0;
            
            for (int count = 0; count < numMessages; count++) {
                async2.onewayMethod("test method :" + count);
            }
            
            synchronized (this) {
                Date startTime = new Date();
                while(onewayResult < numMessages) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 1800000)) {
                        throw new MessageTestException(
                                "The test timed out \n" + 
                                "Oneway " + onewayResult + "\n.");
                    }
                    wait(1000);
                }
            }
        } catch (MessageTestException ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.out.println("Failed to test the message service : " + 
                    ex.getMessage());
            throw new MessageTestException(
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
    public void onewayMethod(String message) throws RemoteException {
        synchronized(this) {
            onewayResult++;
            notify();
        }
    }

    
}
