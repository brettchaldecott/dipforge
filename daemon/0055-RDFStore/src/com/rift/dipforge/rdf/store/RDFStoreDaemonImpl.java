/*
 * RDFStoreClient: The rdf store daemon client.
 * Copyright (C) 2011  Rift IT Contracting
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
 * RDFStoreDaemonImpl.java
 */

package com.rift.dipforge.rdf.store;

// private member
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeException;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.util.connection.ConnectionManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implementation of the rdf store daemon
 *
 * @author brett chaldecott
 */
public class RDFStoreDaemonImpl implements RDFStoreDaemon, BeanRunnable  {

    // class singletons.
    public static Logger log = Logger.getLogger(RDFStoreDaemonImpl.class);

    // private member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    
    public static class RDFStoreChange implements Change {
        
        private String action;
        private String rdfXML;

        /**
         * The constructor that sets up the rdf store.
         * 
         * @param action The action to perform on the store.
         * @param rdfXML The xml to persist.
         */
        public RDFStoreChange(String action, String rdfXML) {
            this.action = action;
            this.rdfXML = rdfXML;
        }
        
        
        
        /**
         * 
         * @throws ChangeException 
         */
        public void applyChanges() throws ChangeException {
            try {
                Session session = SemanticUtil.getInstance(RDFConfig.class).getSession();
                if (StoreActions.PERSIST.equals(action)) {
                    session.persist(rdfXML);
                    RDFStoreStatsManager.getInstance().incrementUpdate();
                } else {
                    session.remove(rdfXML);
                    RDFStoreStatsManager.getInstance().incrementDelete();
                }
            } catch (Exception ex) {
                log.error(
                        "Failed to persist the RDF store change : " + 
                        ex.getMessage(),ex);
                throw new ChangeException
                        ("Failed to persist the RDF store change : " + 
                        ex.getMessage(),ex);
            }
        }
        
    }
    

    /**
     * The default constructor for the store daemon.
     */
    public RDFStoreDaemonImpl() throws RDFStoreException {
    }


    /**
     * This method persists the given xml to the store.
     *
     * @param action The action to perform using the xml.
     * @param rdfXML The rdf xml.
     * @throws RDFStoreException
     * @throws RemoteException
     */
    public void persist(String action, String rdfXML) throws
            RDFStoreException, RemoteException {
        try {
            ChangeLog.getInstance().addChange(new RDFStoreChange(action,rdfXML));
        } catch (Exception ex) {
            log.error("Failed to add the change : " + ex.getMessage(),ex);
            throw new RemoteException(
                    "Failed to add the change : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method processes the rdf store daemon.
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        while (!monitor.isTerminated()) {
            try {
                log.info("Load the semantic util for [" + RDFConfig.class.getName() + "]");
                SemanticUtil.getInstance(RDFConfig.class);
                break;
            } catch (Exception ex) {
                log.info("Failed to instanciate the audit trail logger : " + ex.getMessage(),ex);
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex1) {
                    // ignore
                }
            }
        }
        
        try {
            ChangeLog.init(RDFStoreDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to start the request factory : " + ex.getMessage(),ex);
        }

        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            broker.registerService("rdf/RDFStoreDaemon", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }

        while(!monitor.isTerminated()) {
            try {
                ChangeLog.getInstance().start();
            } catch (Exception ex) {
                log.error("Failed to process : " + ex.getMessage(),ex);
            }
            monitor.monitor();
        }
        try {
            SemanticUtil.closeInstance(RDFConfig.class);
        } catch (Exception ex) {
            log.error("Failed to close the RDF store daemon because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for terminating the processing of the store.
     */
    public void terminate() {
        monitor.terminate(true);
        try {
            ChangeLog.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the change log : " + ex.getMessage(),
                    ex);
        }
    }

}
