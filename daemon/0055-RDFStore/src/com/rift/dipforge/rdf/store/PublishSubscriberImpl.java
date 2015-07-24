/*
 * RDFStoreClient: The rdf store daemon client.
 * Copyright (C) 2011  2015 Burntjam
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
 * PublishSubscriberImpl.java
 */
package com.rift.dipforge.rdf.store;

// imports
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.type.subscriber.PublishSubscriber;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The publish subscriber implementation
 * 
 * @author brett chaldecott
 */
public class PublishSubscriberImpl implements PublishSubscriber, BeanRunnable  {

    // class singletons.
    private Logger log = Logger.getLogger(PublishSubscriberImpl.class);

    // private member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();

   
    /**
     * The default constructor
     */
    public PublishSubscriberImpl() {
    }
    
    
    /**
     * The process method. Required by the bean runnable
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        
        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(PublishSubscriber.SERVICE_NAME);
            broker.registerService("rdf/PublishSubscriber", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }
        
        while(!monitor.isTerminated()) {
            monitor.monitor();
        }
        
    }

    
    /**
     * This method is called to terminate the processing
     */
    public void terminate() {
        monitor.terminate(true);
    }
    
    
    /**
     * This method is called when RDF is published.
     */
    public void rdfPublished() throws RemoteException {
        try {
            SemanticUtil.getInstance(RDFConfig.class).reloadOntology();
        } catch (Exception ex) {
            log.error("Failed to reload the ontology because : " + 
                    ex.getMessage(),ex);
        }
    }

}
