/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * DataMapperManagerImpl.java
 */


// package path
package com.rift.coad.change.server.action.workflow.piece.call;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.change.client.action.workflow.piece.call.DataMapperManager;
import com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemon;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the data mapper manager.
 *
 * @author brett chaldecott
 */
public class DataMapperManagerImpl extends RemoteServiceServlet implements
        DataMapperManager {

    // class singletons
    private static Logger log = Logger.getLogger(DataMapperManagerImpl.class);

    /**
     * The default constructor for the data mapper.
     */
    public DataMapperManagerImpl() {
    }


    /**
     * This method lists the services.
     *
     * @return The list of services.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public String[] listServices() throws DataMapperManagerException {
        try {
            DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(DataMapperBrokerDaemon.class, "datamapper/BrokerDaemon");
            return daemon.listDataMappers().toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to list the services : " + ex.getMessage(),ex);
            throw new DataMapperManagerException
                ("Failed to list the services : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of all the jndi references for this service.
     *
     * @param service The service to return the list for.
     * @return The array of jndi services.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public String[] listJNDIForService(String service) throws DataMapperManagerException {
        try {
            ServiceBroker daemon = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List services = new ArrayList();
            services.add(service);
            return (String[])daemon.getServiceProviders(services).toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to list the jndi references for the service : " + ex.getMessage(),ex);
            throw new DataMapperManagerException
                ("Failed to list the jndi references for the service : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the methods for the service.
     *
     * @param service The name of the service.
     * @return The data mapper methods.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public DataMapperMethod[] listMethods(String service) throws DataMapperManagerException {
        try {
            DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(DataMapperBrokerDaemon.class, "datamapper/BrokerDaemon");
            return (DataMapperMethod[])RDFCopy.copyToClientArray(daemon.listMethods(service));
        } catch (Exception ex) {
            log.error("Failed to list the methods : " + ex.getMessage(),ex);
            throw new DataMapperManagerException
                ("Failed to list the methods : " + ex.getMessage(),ex);
        }
    }
}
