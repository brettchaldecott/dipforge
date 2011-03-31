/*
 * AuditTrail: The audit trail server
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
 * AuditTrailLoggerImpl.java
 */

package com.rift.coad.audit;

// rmi imports
import com.rift.coad.audit.dao.LogEntryDAO;
import com.rift.coad.audit.dto.LogEntry;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import java.rmi.RemoteException;

// log4j imports
import java.util.logging.Level;
import org.apache.log4j.Logger;

// java imports
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.semantic.session.UnknownEntryException;
import com.rift.coad.rdf.types.network.Host;
import com.rift.coad.rdf.types.network.Service;
import com.rift.coad.rdf.types.operation.User;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;


/**
 * The object that represents the audit trail logger.
 *
 * @author brett chaldecott
 */
public class AuditTrailLoggerImpl implements AuditTrailLogger, BeanRunnable {

    // private member variables
    private static Logger log = Logger.getLogger(AuditTrailLoggerImpl.class);

    // member variables
    private ThreadStateMonitor monitor = new ThreadStateMonitor();

    
    /**
     * The default constructor of the audit trail logger.
     *
     * @throws AuditTrailException
     */
    public AuditTrailLoggerImpl() throws AuditTrailException {
        
    }


    /**
     * The method to log the event.
     *
     * @param entry The entry.
     * @throws java.rmi.RemoteException
     */
    public void logEvent(LogEntry entry) throws RemoteException {
        try {
            Session session = SemanticUtil.getInstance(AuditTrailLoggerImpl.class).getSession();
            session.persist(new LogEntryDAO(entry.getId(), new Host(entry.getHostname()),
                    new Service(entry.getSource()), new User(entry.getUser()),
                    entry.getTime(), entry.getStatus(), entry.getCorrelationId(), entry.getExternalId(),
                    entry.getRequest()));
        } catch (Exception ex) {
            log.error("Failed to persist the log event : " + ex.getMessage(),ex);
            throw new RemoteException(
                    "Failed to persist the log event : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to process the thread events.
     */
    public void process() {

        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        while (!monitor.isTerminated()) {
            try {
                SemanticUtil.getInstance(AuditTrailLoggerImpl.class);
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

        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            broker.registerService("audit/AuditTrailLogger", services);
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
            SemanticUtil.closeInstance(AuditTrailLoggerImpl.class);
        } catch (Exception ex) {
            log.error("Failed to shut down the audit trail logger.");
        }
    }


    /**
     * This method is called to terminiate the requests.
     */
    public void terminate() {
        monitor.terminate(true);
    }

}
