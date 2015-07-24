/*
 * <Add library description here>
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
 * BroadcastServerImpl2.java
 */

// package path
package com.rift.coad.daemon.messageservice.broadcast.test;

// java imports
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// coadunation imports
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.util.connection.ConnectionManager;

/**
 *
 * @author Brett Chaldecott
 */
public class BroadcastServerImpl2 implements BroadcastServer {
    
    /** 
     * Creates a new instance of BroadcastServerImpl2 
     */
    public BroadcastServerImpl2() throws BroadcastException {
        try {
            ServiceBroker serviceBroker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    ServiceBroker.JNDI_URL);
            List services = new ArrayList();
            services.add("test2");
            serviceBroker.registerService("messagetest/BroadcastServer2",
                    services);
        } catch (Exception ex) {
            throw new BroadcastException(
                    "Failed to connect to the service broker : " + 
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * The definition of the test method.
     *
     * @return The string result.
     * @param message The message result.
     * @exception RemoteException
     */
    public String testMethod(String message) throws RemoteException {
        System.out.println("[BroadcastServerImpl2]Message is : " + message);
        return "[BroadcastServerImpl2]" + message;
    }
}
