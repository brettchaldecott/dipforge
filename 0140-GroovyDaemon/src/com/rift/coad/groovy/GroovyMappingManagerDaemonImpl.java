/*
 * GroovyDaemon: The groovy daemon
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
 * GroovyMappingManagerDaemonImpl.java
 */


// package path
package com.rift.coad.groovy;

// java imports
import java.util.ArrayList;
import java.util.List;


// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import com.rift.coad.datamapperbroker.util.DataMapperBrokerUtil;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;


/**
 * This object represents a groovy mapping daemon.
 *
 * @author brett chaldecott
 */
public class GroovyMappingManagerDaemonImpl implements GroovyMappingManagerDaemon, BeanRunnable {



    // class singletons.
    private static Logger log = Logger.getLogger(GroovyMappingManagerDaemonImpl.class);

    // private member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();

    /**
     * The default constructor for the groovy mapping daemon.
     *
     * @throws GroovyDaemonException
     */
    public GroovyMappingManagerDaemonImpl() throws GroovyDaemonException {
        // Remove this because of startup order problem. Will use on demand
        // funcationality provided by the semantic util.
//        try {
//            SemanticUtil.getInstance(GroovyMappingManagerDaemonImpl.class);
//        } catch (Exception ex) {
//            log.error("Failed to instanciate the groovy mapping manager : " + ex.getMessage(),ex);
//            throw new GroovyDaemonException
//                    ("Failed to instanciate the groovy mapping manager : " + ex.getMessage(),ex);
//        }
    }


    /**
     * This method returns a list of methods registered with the data mapper.
     *
     * @return The list of methods registered with this data mapper.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public List<DataMapperMethod> listMethods() throws GroovyDaemonException {
        try {
            return listMethods(SemanticUtil.getInstance(GroovyMappingManagerDaemonImpl.class).getSession());
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of all the registered methods : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to retrieve a list of all the registered methods : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds a new method to the list of methods
     * @param method This method adds the specified method.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public void addMethod(DataMapperMethod newMethod) throws GroovyDaemonException {
        try {
            Session session = SemanticUtil.getInstance(GroovyMappingManagerDaemonImpl.class).getSession();
            List<DataMapperMethod> methods = listMethods(session);
            for (DataMapperMethod method : methods) {
                if (method.equals(newMethod)) {
                    log.error("Duplicate method : " + newMethod.getName());
                    throw new GroovyDaemonException
                            ("Duplicate method : " + newMethod.getName());
                }
            }
            newMethod.setService(Constants.SERVICE_ID);
            methods.add(newMethod);
            session.persist(newMethod);
            
            DataMapperBrokerUtil util1 = new DataMapperBrokerUtil(Constants.SERVICE_ID,
                    Constants.DATA_MAPPER);
            util1.register(methods.toArray(new DataMapperMethod[0]));
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add method : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to add method : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the identified method.
     *
     * @param method The method to remove.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public void updateMethod(DataMapperMethod method) throws GroovyDaemonException {
        try {
            Session session = SemanticUtil.getInstance(GroovyMappingManagerDaemonImpl.class).getSession();
            List<DataMapperMethod> methods = listMethods(session);
            method.setService(Constants.SERVICE_ID);
            for (DataMapperMethod meth : methods) {
                if (meth.equals(method)) {
                    session.persist(method);
                    methods.remove(meth);
                    methods.add(method);
                    DataMapperBrokerUtil util1 = new DataMapperBrokerUtil(Constants.SERVICE_ID,
                            Constants.DATA_MAPPER);
                    util1.register(methods.toArray(new DataMapperMethod[0]));
                    return;
                }
            }
            log.debug("The entry was not found to update.");
            throw new GroovyDaemonException("The entry was not found to update.");
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the method : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to add method : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method removes the identified method.
     *
     * @param method The method to remove.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public void removeMethod(DataMapperMethod method) throws GroovyDaemonException {
        try {
            Session session = SemanticUtil.getInstance(GroovyMappingManagerDaemonImpl.class).getSession();
            List<DataMapperMethod> methods = listMethods(session);
            for (DataMapperMethod meth : methods) {
                if (meth.equals(method)) {
                    session.remove(meth);
                    methods.remove(meth);
                    DataMapperBrokerUtil util1 = new DataMapperBrokerUtil(Constants.SERVICE_ID,
                            Constants.DATA_MAPPER);
                    util1.register(methods.toArray(new DataMapperMethod[0]));
                    return;
                }
            }
            log.debug("The entry was not found to remove.");
            throw new GroovyDaemonException("The entry was not found to remove.");
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add method : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to add method : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of the methods.
     *
     * @param session The session to retrieve the list of methods from.
     * @return The list of methods.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    private List<DataMapperMethod> listMethods(Session session) throws GroovyDaemonException {
        try {
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethod> ." +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodServiceId> ?ServiceId ." +
                    "FILTER (?ServiceId = ${ServiceId})}").setString("ServiceId", Constants.SERVICE_ID)
                    .execute();
            List<DataMapperMethod> methods = new ArrayList<DataMapperMethod>();
            for (SPARQLResultRow row : entries) {
                methods.add(row.get(0).cast(DataMapperMethod.class));
            }

            return methods;
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of all the registered methods : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to retrieve a list of all the registered methods : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to process events.
     */
    public void process() {
        while (!monitor.isTerminated()) {
            monitor.monitor();
        }
        try {
            SemanticUtil.closeInstance(GroovyMappingManagerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to close the semantic session manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to terminate.
     */
    public void terminate() {
        monitor.terminate(true);
    }
}
