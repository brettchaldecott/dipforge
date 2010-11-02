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
 * RPCServer1.java
 */

// package path
package com.rift.coad.daemon.messageservice.rpc.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface describes the methods that can be called for the rpc tests.
 *
 * @author Brett Chaldecott
 */
public interface RPCServer1Async extends Remote {
    
    /**
     * This method is used to test the rpc client
     *
     * @param msg The message to print.
     * @exception RemoteException
     */
    public String voidMethod(String msg) throws RemoteException;
    
    
    /**
     * This method is used to test the rpc client
     *
     * @return A string result.
     * @param msg The message to print.
     * @exception RemoteException
     */
    public String stringMethod(String msg) throws RemoteException;
    
    
    /**
     * This method returns an integer result.
     *
     * @return Int an integer result
     * @param msg The message to print.
     * @exception RemoteException
     */
    public String intMethod(String msg) throws RemoteException;
    
    
    /**
     * This method returns an integer result.
     *
     * @return Int an integer result
     * @param msg The message to print.
     * @exception RemoteException
     * @exception MessageTestException
     */
    public String exceptionMethod(String msg) throws RemoteException,
            MessageTestException;
    
    
    /**
     * This method returns an integer result.
     *
     * @return The data test object.
     * @param value The data test object.
     * @exception RemoteException
     */
    public String objectMethod(RPCDataTest value) throws RemoteException;
}
