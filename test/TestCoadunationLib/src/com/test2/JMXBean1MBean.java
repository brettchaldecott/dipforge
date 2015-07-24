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
 * JMXBean1MBean.java
 *
 * This is a test JMX Bean interface
 */

package com.test2;

// imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is a test JMX Bean interface
 *
 * @author Brett Chaldecott
 */
public interface JMXBean1MBean  extends Remote {
    
    /**
     * The test JMX Bean.
     *
     * @param msg The message for the server.
     * @return The string message for the client
     */
    public String helloWorld(String msg) throws RemoteException;
}
