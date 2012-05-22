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
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.common.CopyObject;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.rdf.types.mapping.ParameterMapping;
import com.rift.coad.util.connection.ConnectionManager;
import java.rmi.RemoteException;
import java.util.*;

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
    public void register(List<MethodMapping> methods) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.
                    getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            // this code makes the assumption that methods are attached to projects
            // and that these project methods will be removed
            Set<String> projects = new HashSet<String>();
            for (MethodMapping method: methods) {
                projects.add(method.getProject());
            }
            
            // remove the resources attached to the projects
            for (String project : projects) {
                List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#MethodMapping> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#Project> ?Project . " +
                    "FILTER (?Project = ${project}) }").setString("project", project).execute();
                for (SPARQLResultRow entry : entries) {
                    Resource resource = entry.get(Resource.class, 0);
                    session.remove(resource);
                }
            }
            // add the methods
            Map<String,List<String>> serviceMap = new HashMap<String,List<String>>();
            for (MethodMapping method : methods) {
                session.persist(method);
                if (method.getService() != null) {
                    List<String> services = serviceMap.get(method.getJndi());
                    if (services == null) {
                        services = new ArrayList<String>();
                        serviceMap.put(method.getJndi(), services);
                    }
                    if (!services.contains(method.getService())) {
                        services.add(method.getService());
                    }
                }
            }
            // register the services
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            for (String key: serviceMap.keySet()) {
                broker.registerService(key, serviceMap.get(key));
            }
        } catch (Exception ex) {
            log.error("Failed to register the methods : " + ex.getMessage(),ex);
            throw new DataMapperBrokerException(
                    "Failed to register the methods : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of all the JNDI bindings that have methods registered
     * for them.
     *
     * @return The list of data mappers
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public List<String> listJNDIBindings() throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#MethodMapping> . }").execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                MethodMapping method = entry.get(MethodMapping.class, 0);
                if (!result.contains(method.getJndi())) {
                    result.add(method.getJndi());
                }
            }
            return result;
        } catch (Exception ex) {
            log.error("This method lists the data mappers : " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "This method lists the data mappers : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns a list of methods identified by the service id.
     *
     * @param serviceId The id to the service.
     * @return The method attached to the given service id.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public List<MethodMapping> listMethods(String jndi) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#MethodMapping> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#JNDI> ?JNDI . " +
                    "FILTER (?JNDI = ${jndi}) }").setString("jndi", jndi).execute();
            List<MethodMapping> result = new ArrayList<MethodMapping>();
            for (SPARQLResultRow entry : entries) {
                result.add(copyMethodMapping(
                        entry.get(MethodMapping.class,0)));
            }
            return result;
        } catch (Exception ex) {
            log.error("This method lists the methods identified by jndi reference ["
                    + jndi + "]: " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "This method lists the methods identified by jndi reference ["
                    + jndi + "]: " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the list of methods.
     *
     * @param methodId The id of the method.
     * @return The reference to the data mapper.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     */
    public MethodMapping getMethod(String methodId) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            MethodMapping method = session.get(MethodMapping.class, methodId);
            
            // copy the method to a DTO version.
            return copyMethodMapping(method);
        } catch (Exception ex) {
            log.error("Failed to retrieve the method ["
                    + methodId + "] because : " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "Failed to retrieve the method ["
                    + methodId + "] because : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method returns a list of methods.
     * 
     * @param jndi The jndi to search against.
     * @param project The project name to search for.
     * @param className The class name within the project to execute.
     * @return The list of methods that match the parameters
     * @throws DataMapperBrokerException
     * @throws RemoteException 
     */
    public List<MethodMapping> listMethods(String jndi, String project, 
            String className) throws DataMapperBrokerException, RemoteException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#MethodMapping> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#JNDI> ?JNDI . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#Project> ?Project . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#ClassName> ?ClassName . " +
                    "FILTER (?JNDI = ${jndi} && ?Project = ${project} && ?ClassName = ${className}) }").
                    setString("jndi", jndi).setString("project",project).
                    setString("className",className).execute();
            List<MethodMapping> result = new ArrayList<MethodMapping>();
            for (SPARQLResultRow entry : entries) {
                result.add(copyMethodMapping(
                        entry.get(MethodMapping.class,0)));
            }
            return result;
        } catch (Exception ex) {
            log.error("This method lists the methods identified by jndi reference ["
                    + jndi + "]: " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "This method lists the methods identified by jndi reference ["
                    + jndi + "]: " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method returns the list of services.
     * 
     * @param service The service to perform the search against.
     * @param project The project name.
     * @param className The class name.
     * @return The list of method mappings matching the criteria
     * @throws DataMapperBrokerException
     */
    public List<MethodMapping> listMethodsByService(String service,
            String project, String className) throws DataMapperBrokerException {
        try {
            Session session = SemanticUtil.getInstance(DataMapperBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#MethodMapping> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#Service> ?Service . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#Project> ?Project . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/common/MappingMethod#ClassName> ?ClassName . " +
                    "FILTER (?Service = ${service} && ?Project = ${project} && ?ClassName = ${className}) }").
                    setString("service", service).setString("project",project).
                    setString("className",className).execute();
            List<MethodMapping> result = new ArrayList<MethodMapping>();
            for (SPARQLResultRow entry : entries) {
                result.add(copyMethodMapping(
                        entry.get(MethodMapping.class,0)));
            }
            return result;
        } catch (Exception ex) {
            log.error("This method lists the methods identified by jndi reference ["
                    + service + "]: " + ex.getMessage(), ex);
            throw new DataMapperBrokerException(
                    "This method lists the methods identified by jndi reference ["
                    + service + "]: " + ex.getMessage(), ex);
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
    
    
    /**
     * This method is used to copy the method mapping object.
     * @param methodMapping
     * @return 
     */
    private MethodMapping copyMethodMapping(MethodMapping method)
        throws DataMapperBrokerException {
        try {
            MethodMapping result = CopyObject.copy(MethodMapping.class,method);
            result.setParameters(new ArrayList<ParameterMapping>());
            
            List<ParameterMapping> parameters = method.getParameters();
            for (ParameterMapping parameter : parameters) {
                result.getParameters().add(
                        CopyObject.copy(ParameterMapping.class,parameter));
            }
            
            return result;
        } catch (Exception ex) {
            throw new DataMapperBrokerException(
                    "Failed to copy the method because : " + ex.getMessage(),ex);
        }
    }

}
