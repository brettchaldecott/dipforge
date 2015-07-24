/*
 * CoadunationUtil: The coadunation util library.
 * Copyright (C) 2007  2015 Burntjam
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
 * ConnectionHandler.java
 */

// package path
package com.rift.coad.util.connection;

// java imports
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * This object is responsible for controlling the Proxy wrapping of any RMI
 * classes in the connection manager.
 *
 * @author Brett Chaldecott
 */
public class ConnectionHandler implements InvocationHandler {
    
    // private member variables
    private RMIConnection rmiConnection = null;
    private Object rmiRef = null;
    
    
    /** 
     * Creates a new instance of ConnectionHandler.
     *
     * @param rmiConnection The reference to the connection object.
     * @param rmiRef The reference to the sub object to make the call onto.
     */
    public ConnectionHandler(RMIConnection rmiConnection, Object rmiRef) {
        this.rmiConnection = rmiConnection;
        this.rmiRef = rmiRef;
    }
    
    
    /**
     * This method performs the call on the rmi reference.
     *
     * @param proxy The proxy that is making the call on this handler.
     * @param method The method that is being called.
     * @param args The arguments that are being called.
     * @exception Throwable.
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws 
            Throwable {
        try {
            // retrieve the method information
            Method rmiMethod = rmiRef.getClass().getMethod(method.getName(),
                    method.getParameterTypes());
            
            // make the call
            return rmiMethod.invoke(rmiRef,args);
        } catch (InvocationTargetException ex) {
            Throwable ex2 = ex.getTargetException();
            if ((ex2 instanceof java.rmi.RemoteException) || 
                    (ex2 instanceof java.lang.RuntimeException))
            {
                rmiConnection.invalidateConnection();
            }
            throw ex2;
        } catch (Throwable ex) {
            if ((ex instanceof java.rmi.RemoteException) || 
                    (ex instanceof java.lang.RuntimeException))
            {
                rmiConnection.invalidateConnection();
            }
            throw ex;
        }
    }
    
}
