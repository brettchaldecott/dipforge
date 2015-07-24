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
 * ServiceServcerImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.service.test;

// java imports
import java.util.List;
import java.util.ArrayList;

// coadunation imports
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object implements the service server.
 *
 * @author Brett Chaldecott
 */
public class ServiceServerImpl implements ServiceServer {
    
    
    /**
     * Creates a new instance of ServiceServcerImpl
     */
    public ServiceServerImpl() throws ServiceException {
        try {
            ServiceBroker serviceBroker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    ServiceBroker.JNDI_URL);
            List services = new ArrayList();
            services.add("test");
            serviceBroker.registerService(ServiceServer.JNDI_URL,services);
        } catch (Exception ex) {
            throw new ServiceException(
                    "Failed to connect to the service broker : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to test the service for this server.
     *
     * @param value The test value for this method
     * @exception RemoteException
     */
    public void callMethod(String value) {
        System.out.println("The service server has been called.");
    }
}
