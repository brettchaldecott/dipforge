/*
 * DataMapperBrokerMBean: The data mapper broker client interface.
 * Copyright (C) 2009  Rift IT Contracting
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
 * DataMapperBrokerUtil.java
 */


// package path
package com.rift.coad.datamapperbroker.util;

import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.datamapperbroker.DataMapperBrokerConstants;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemon;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemonAsync;
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;



/**
 * This utility wrapps the complexity of adding entries to the service broker and
 * data mapper broker.
 *
 * @author brett chaldecott
 */
public class DataMapperBrokerUtil {
    
    // private member variables
    private String serviceId;
    private String jndi;

    /**
     * This constructor sets up the
     *
     * @param serviceId The id of the service to register.
     * @param jndi The jndi binding for the service.
     */
    public DataMapperBrokerUtil(String serviceId, String jndi) {
        this.serviceId = serviceId;
        this.jndi = jndi;
    }


    /**
     * This method is called to register the methods with the data mapper broker.
     *
     * @param methods The methods to register.
     * @throws com.rift.coad.datamapperbroker.util.DataMapperBrokerUtilException
     */
    public void register(DataMapperMethod[] methods) throws DataMapperBrokerUtilException {
        if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
            throw new DataMapperBrokerUtilException("The initial deployment has not been " +
                    "completed, cannot register");
        }
        try {
            List<String> services = new ArrayList<String>();
            services.add(DataMapperBrokerConstants.SERVICE);
            DataMapperBrokerDaemonAsync dataMapperBroker = (DataMapperBrokerDaemonAsync)RPCMessageClient.createOneWay(
                    jndi, DataMapperBrokerDaemon.class, DataMapperBrokerDaemonAsync.class, services, false);
            dataMapperBroker.register(serviceId, methods);
            
        } catch (Throwable ex) {
            throw new DataMapperBrokerUtilException("Failed to register : " + ex.getMessage(),ex);
        }
        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(serviceId);
            broker.registerService(jndi, services);
        } catch (Exception ex) {
            throw new DataMapperBrokerUtilException("Failed to register entry with the service broker: "
                    + ex.getMessage(),ex);
        }
    }
}
