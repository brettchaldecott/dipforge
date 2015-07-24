/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * BeanInterface.java
 *
 * A test bean interface.
 */

package com.test;

// imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author Brett Chaldecott
 */
public interface BeanInterface extends Remote {
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public String helloWorld(String msg) throws RemoteException, TestException;
    
    
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public void exceptionMethod(String msg) throws RemoteException, TestException;
    
    
    /**
     * Retrieve the value
     */
    public ValueObject getValue() throws RemoteException;
    
    
    /**
     * Set the value
     */
    public void setValue(ValueObject value) throws RemoteException;
    
    /**
     * A void method.
     */
    public void voidMethod() throws RemoteException;
    
    
    /**
     * This method returns a factory object
     */
    public FactoryInterface connectToObject() throws RemoteException;
    
    
    /**
     * This method adds a test object.
     */
    public FactoryInterface addObject(String key) throws RemoteException;
    
    /**
     * This method adds a test object.
     */
    public FactoryParentInterface getObject(String key) throws RemoteException;
    
    
    /**
     * This method adds a test object.
     */
    public void removeObject(String key) throws RemoteException;
}
