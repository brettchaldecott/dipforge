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
 * DataMapperBrokerDaemonImpl.java
 */

// package path
package com.rift.coad.datamapperbroker;

// java imports
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import java.util.List;

// log4j import
import org.apache.log4j.Logger;

// data mapper information
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.datamapperbroker.rdf.DataMapperService;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;

/**
 * This object is responsible for brokering the daemon requests.
 *
 * @author brett chaldecott
 */
public class DataMapperBrokerDaemonImpl implements DataMapperBrokerDaemon, BeanRunnable {

    // private member variables
    private static Logger log = Logger.getLogger(DataMapperBrokerDaemonImpl.class);
    private ThreadStateMonitor monitor = new ThreadStateMonitor();

    /**
     * The default constructor
     */
    public DataMapperBrokerDaemonImpl() {
    }


    /**
     * This method is responsible for registering a new service.
     *
     * @param serviceId The string identifying the service.
     * @param methods The methods to register with the data mapper broker.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public void register(String serviceId, DataMapperMethod[] methods) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            session.persist(new DataMapperService(serviceId,methods));
        } catch (Exception ex) {
            log.error("Failed to register the method : " + ex.getMessage(),ex);
            throw new DataMapperBrokerException(
                    "Failed to register the method : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of data mapper requests.
     *
     * @return The list of data mappers
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public List<String> listDataMappers() throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s ?ServiceId WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperService> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperServiceId> ?ServiceId . } " +
                    "ORDER BY ?ServiceId").execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                result.add(entry.get(1).cast(String.class));
            }
            return result;
        } catch (Exception ex) {
            log.error("This method lists the data mappers : " + ex.getMessage(), ex);
            throw new DataMapperBrokerException("This method lists the data mappers : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns a list of methods identified by the service id.
     *
     * @param serviceId The id to the service.
     * @return The method attached to the given service id.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public DataMapperMethod[] listMethods(String serviceId) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethod> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodServiceId> ?ServiceId . " +
                    "FILTER (?ServiceId = ${ServiceId}) } " +
                    "ORDER BY ?ServiceId").setString("ServiceId", serviceId).execute();
            List<DataMapperMethod> result = new ArrayList<DataMapperMethod>();
            for (SPARQLResultRow entry : entries) {
                result.add(entry.get(0).cast(DataMapperMethod.class));
            }
            return result.toArray(new DataMapperMethod[0]);
        } catch (Exception ex) {
            log.error("This method lists the methods identified by the service id ["
                    + serviceId + "]: " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "This method lists the methods identified by the service id ["
                    + serviceId + "]: " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the list of methods.
     *
     * @param serviceId The service id.
     * @param methodName The method name.
     * @return The reference to the data mapper.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public DataMapperMethod getMethod(String serviceId, String methodName) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethod> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodServiceId> ?ServiceId . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodName> ?MethodName . " +
                    "FILTER ((?ServiceId = ${ServiceId}) && (?MethodName = ${MethodName})) } " +
                    "ORDER BY ?ServiceId").setString("ServiceId", serviceId).
                    setString("MethodName", methodName).execute();
            if (entries.size() < 1) {
                throw new DataMapperBrokerException("The Service id [" + serviceId
                        + "] with method [" + methodName + "] was not found.");
            }
            return entries.get(0).get(0).cast(DataMapperMethod.class);
        } catch (DataMapperBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the service id ["
                    + serviceId + "] and method [" + methodName + "] because : " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "Failed to retrieve the service id ["
                    + serviceId + "] and method [" + methodName + "] because : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to process.
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        while (!monitor.isTerminated()) {
            try {
                SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class);
                break;
            } catch (Exception ex) {
                log.info("Failed to instanciate the data mapper broker : " + ex.getMessage(),ex);
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex1) {
                    // ignore
                }
            }
        }
        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(DataMapperBrokerConstants.SERVICE);
            broker.registerService("datamapper/BrokerDaemon", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }

        // wait for shut down
        while (!monitor.isTerminated()) {
            monitor.monitor();
        }
        // close down the semantic util
        try {
            SemanticUtil.closeInstance(DataMapperBrokerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to shut down the data mapper broker : " + ex.getMessage(),ex);
        }
    }


    /**
     * The method called to terminate the processing of the data mapper broker
     */
    public void terminate() {
        monitor.terminate(true);
    }

}
