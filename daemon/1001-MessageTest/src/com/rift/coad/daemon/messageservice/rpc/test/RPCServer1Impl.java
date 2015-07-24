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
 * RPCServer1Impl.java
 */

package com.rift.coad.daemon.messageservice.rpc.test;

// java imports
import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

// log imports
import org.apache.log4j.Logger;


/**
 * This class implements the RPC
 *
 * @author Brett Chaldecott
 */
public class RPCServer1Impl implements RPCServer1 {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(RPCServer1Impl.class.getName());
    
    /** 
     * Creates a new instance of RPCClient1Impl
     */
    public RPCServer1Impl() {
    }
    
    
    /**
     * This method is used to test the rpc client
     *
     * @param msg The message to print.
     * @exception RemoteException
     */
    public void voidMethod(String msg) throws RemoteException {
        log.info("The void method has been called : " + msg);
    }
    
    
    /**
     * This method is used to test the rpc client
     *
     * @return A string result.
     * @param msg The message to print.
     * @exception RemoteException
     */
    public String stringMethod(String msg) throws RemoteException {
        log.info("The string method has been called : " + msg);
        return "The string method.";
    }
    
    
    /**
     * This method returns an integer result.
     *
     * @return Int an integer result
     * @param msg The message to print.
     * @exception RemoteException
     */
    public int intMethod(String msg) throws RemoteException {
        log.info("The int method : " + msg);
        return 1;
    }
    
    
    /**
     * This method returns an integer result.
     *
     * @return Int an integer result
     * @param msg The message to print.
     * @exception RemoteException
     * @exception MessageTestException
     */
    public void exceptionMethod(String msg) throws RemoteException,
            MessageTestException {
        log.info("The exception method : " + msg);
        throw new MessageTestException("This is a test exception");
    }
    
    
    /**
     * This method returns an integer result.
     *
     * @return The data test object.
     * @param value The data test object.
     * @exception RemoteException
     */
    public RPCDataTest objectMethod(RPCDataTest value) throws RemoteException {
        value.setValue("The new value : "  + new Date().getTime());
        return value;
    }
}
