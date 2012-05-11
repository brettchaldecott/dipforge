/*
 * MasterRDFStoreClient: The master RDF store interface
 * Copyright (C) 2012  Rift IT Contracting
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
 * MasterRDFStoreDaemon.java
 */
package com.rift.dipforge.rdf.store.master;

import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
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
import com.rift.dipforge.rdf.store.RDFStoreDaemon;
import com.rift.dipforge.rdf.store.RDFStoreDaemonAsync;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author brett chaldecott
 */
public class MasterRDFStoreDaemonImpl implements MasterRDFStoreDaemon, BeanRunnable {
    
    // class singletons.
    public static Logger log = Logger.getLogger(MasterRDFStoreDaemonImpl.class);
    
    // private member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    
    public static class MasterRDFStoreChange implements Change {
        
        private String action;
        private String rdfXML;

        /**
         * The constructor that sets up the rdf store.
         * 
         * @param action The action to perform on the store.
         * @param rdfXML The xml to persist.
         */
        public MasterRDFStoreChange(String action, String rdfXML) {
            this.action = action;
            this.rdfXML = rdfXML;
        }
        
        
        
        /**
         * 
         * @throws ChangeException 
         */
        public void applyChanges() throws ChangeException {
            try {
                Session session = SemanticUtil.getInstance(
                        MasterRDFStoreDaemonImpl.class).getSession();
                if (StoreActions.PERSIST.equals(action)) {
                    session.persist(rdfXML);
                    MasterRDFStoreStatsManager.getInstance().incrementUpdate();
                } else {
                    session.remove(rdfXML);
                    MasterRDFStoreStatsManager.getInstance().incrementDelete();
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
     * The default constructor of the master rdf store.
     */
    public MasterRDFStoreDaemonImpl() {
        MasterRDFStoreStatsManager.getInstance();
    }
    
    
    /**
     * This method is called to persist the changes.
     * 
     * @param action The action to perform on the store.
     * @param rdfXML The xml containing the information.
     * @throws MasterRDFStoreException
     * @throws RemoteException 
     */
    @Override
    public void persist(String action, String rdfXML) throws MasterRDFStoreException, RemoteException {
        try {
            ChangeLog.getInstance().addChange(new MasterRDFStoreChange(action,rdfXML));
            List<String> services = new ArrayList<String>();
            services.add("rdf_store");
            
            // push the changes to the read only stores.
            RDFStoreDaemonAsync daemon = (RDFStoreDaemonAsync)RPCMessageClient.createOneWay(
                    "rdf/MasterRDFStoreDaemon", RDFStoreDaemon.class,
                    RDFStoreDaemonAsync.class, services, true);
            daemon.persist(action, rdfXML);
        } catch (Exception ex) {
            log.error("Failed to add the change : " + ex.getMessage(),ex);
            throw new RemoteException(
                    "Failed to add the change : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to start processing.
     */
    @Override
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        while (!monitor.isTerminated()) {
            try {
                log.info("Load the semantic util for [" + MasterRDFStoreDaemonImpl.class.getName() + "]");
                SemanticUtil.getInstance(MasterRDFStoreDaemonImpl.class);
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
            ChangeLog.init(MasterRDFStoreDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to start the request factory : " + ex.getMessage(),ex);
        }

        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            broker.registerService("rdf/MasterRDFStoreDaemon", services);
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
            SemanticUtil.closeInstance(MasterRDFStoreDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to close the RDF store daemon because : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to terminate the 
     */
    @Override
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
