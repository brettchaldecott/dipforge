/*
 * CoadunationInclude: A test library.
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
 * Bean2Interface.java
 *
 * An interface defining a test bean.
 */

package com.extra.bean;

// the remote interface
import java.rmi.Remote;

/**
 * An interface defining a test bean.
 *
 * @author Brett Chaldecott
 */
public interface Bean2Interface extends Remote {
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public String helloWorld(String msg) throws java.rmi.RemoteException;
    
    /**
     * This method returns a top interface.
     *
     * @return A reference to the top interface.
     * @param name The name of the insterface to retrieve.
     * @exception java.rmi.RemoteException
     */
    public TopInterface getTopInter(String name) throws java.rmi.RemoteException;
    
    
    /**
     * This method takes the value given to it.
     *
     * @param value The value to take.
     * @exception RemoteException
     */
    public void takeValue(ValueObject2 value) throws java.rmi.RemoteException;
}
