/*
 * DataMapperBroker: The implementation of the data mapper broker
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
 * DataMapperBroker.java
 */

package com.rift.coad.datamapperbroker;

// java imports
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.List;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * The management bean for the data mapper broker.
 *
 * @author brett chaldecott
 */
public class DataMapperBroker implements DataMapperBrokerMBean {

    // private member variables
    private Logger log = Logger.getLogger(DataMapperBroker.class);
    

    /**
     * The default constructor
     */
    public DataMapperBroker() {
    }

    
    /**
     * This method returns the version information for the data mapper broker.
     *
     * @return This method returns the version information for this object.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the data mapper broker.
     *
     * @return The string containing the name of this object.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the data mapper broker.
     *
     * @return This method returns the description of the data mapper broker.
     */
    public String getDescription() {
        return "The implementation of the data mapper broker";
    }


    /**
     * This method returns the list of services.
     *
     * @return The string list of services.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public List<String> listServices() throws DataMapperBrokerException {
        try {
            DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(DataMapperBrokerDaemon.class, "java:comp/env/bean/datamapper/BrokerDaemon");
            return daemon.listDataMappers();
        } catch (Exception ex) {
            log.error("Failed to list the services : " + ex.getMessage(),ex);
            throw new DataMapperBrokerException
                ("Failed to list the services : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of methods.
     *
     * @param serviceId The service id to retrieve the list of methods for.
     * @return The list of methods.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public List<String> listMethods(String serviceId) throws DataMapperBrokerException {
        try {
            DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(DataMapperBrokerDaemon.class, "java:comp/env/bean/datamapper/BrokerDaemon");
            DataMapperMethod[] methods = daemon.listMethods(serviceId);
            List<String> result = new ArrayList<String>();
            for (DataMapperMethod method : methods) {
                result.add(method.getName());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the services : " + ex.getMessage(),ex);
            throw new DataMapperBrokerException
                ("Failed to list the services : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a description of the method.
     * 
     * @param serviceId
     * @param method
     * @return
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public String describeMethodAsXML(String serviceId, String method) throws DataMapperBrokerException {
        try {
            DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(DataMapperBrokerDaemon.class, "java:comp/env/bean/datamapper/BrokerDaemon");
            DataMapperMethod definition = daemon.getMethod(serviceId, method);
            Session result = Basic.initSessionManager().getSession();
            result.persist(definition);
            return result.dump(RDFFormats.XML_ABBREV);
        } catch (Exception ex) {
            log.error("Failed to get the XML definition for the method : " + ex.getMessage(),ex);
            throw new DataMapperBrokerException
                ("Failed to get the XML definition for the method : " + ex.getMessage(),ex);
        }
    }

}
