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
public class RPCClient1Impl implements RPCClient1 {
    
    // private member variables
    Set intSet = null;
    int intResult = 0;
    Set strSet = null;
    int strResult = 0;
    Set voidSet = null;
    int voidResult = 0;
    Set exceptionSet = null;
    int exceptionResult = 0;
    Set objectSet = null;
    int objectResult = 0;
    
    /** Creates a new instance of RPCClient1Impl */
    public RPCClient1Impl() {
    }
    
    
    /**
     * This object runs the test.
     *
     * @param numMessage The number of message.
     * @exception MessageTestException
     */
    public void runBasicTest(int numMessages) throws RemoteException,
            MessageTestException {
        System.out.println("The runtime class");
        try {
            RPCServer1Async async1 = (RPCServer1Async)RPCMessageClient.create(
                    "messagetest/RPCClient1",RPCServer1.class,
                    RPCServer1Async.class,"messagetest/RPCServer1");
            intSet = new HashSet();
            intResult = 0;
            strSet = new HashSet();
            strResult = 0;
            voidSet = new HashSet();
            voidResult = 0;
            exceptionSet = new HashSet();
            exceptionResult = 0;
            objectSet = new HashSet();
            objectResult = 0;
            
            for (int count = 0; count < numMessages; count++) {
                intSet.add(async1.intMethod("int method"));
                strSet.add(async1.stringMethod("String method"));
                voidSet.add(async1.voidMethod("void method"));
                exceptionSet.add(async1.exceptionMethod("exception method"));
                objectSet.add(async1.objectMethod(
                        new RPCDataTest("exception method : " + count)));
            }
            
            synchronized (this) {
                Date startTime = new Date();
                while((intResult < intSet.size()) || 
                        (strResult < strSet.size()) ||
                        (voidResult < voidSet.size()) ||
                        (exceptionResult < exceptionSet.size()) || 
                        (objectResult < objectSet.size())) {
                    Date currentTime = new Date();
                    if (currentTime.getTime() > (startTime.getTime() + 1800000)) {
                        throw new MessageTestException(
                                "The test timed out \n" + 
                                "Int " + intResult + "\n" +
                                "String " + strResult + "\n" +
                                "Void " + voidResult + "\n" +
                                "Exception " + exceptionResult + "\n.");
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
    public synchronized void onSuccess(String messageId, String correllationId, 
            Object result) throws RemoteException {
        if (result != null) {
            System.out.println("Result is : " + result.getClass().getName() + 
                    " [" + result.toString() + "]");
        } else {
            System.out.println("Result is null");
        }
        if (intSet.contains(messageId)) {
            intResult++;
        } else if (strSet.contains(messageId)) {
            strResult++;
        } else if (voidSet.contains(messageId)) {
            voidResult++;
        } else if (objectSet.contains(messageId)) {
            objectResult++;
        }
        notify();
    }
    
    
    /**
     * This method is responsible for processing the results of a failure.
     *
     * @param messageId The id of the message.
     * @param correllationId The id of the correlation.
     * @exception caught The caught message.
     */
    public synchronized void onFailure(String messageId, String correllationId, 
            Throwable caught) throws RemoteException {
        if (exceptionSet.contains(messageId)) {
            if (!(caught instanceof MessageTestException)) {
                System.out.println("The exception is not a message test " +
                        "exception : " + caught.getClass().getName());
            }
            exceptionResult++;
        }
        notify();
    }
    
}
