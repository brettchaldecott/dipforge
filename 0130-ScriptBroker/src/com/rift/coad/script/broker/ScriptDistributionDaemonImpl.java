/*
 * ScriptBroker: The script broker daemon.
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
 * ScriptDistributionDaemonImpl.java
 */


// package path
package com.rift.coad.script.broker;

// java imports
import com.rift.coad.util.change.ChangeException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.script.broker.rdf.RDFScriptChangeInfo;
import com.rift.coad.script.broker.rdf.RDFScriptChangeSet;
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeLog;


/**
 * This object is responsible managing the distribution of scripts.
 *
 * @author brett chaldecott
 */
public class ScriptDistributionDaemonImpl implements ScriptDistributionDaemon, BeanRunnable {

    public static class ScriptChange implements Change {

        private RDFScriptInfo info;

        /**
         * The script change.
         *
         * @param info The change
         */
        public ScriptChange(RDFScriptInfo info) {
            this.info = info;
        }


        /**
         * The apply method
         * @throws com.rift.coad.util.change.ChangeException
         */
        public void applyChanges() throws ChangeException {
            // TODO: implement apply
        }

    }

    // class constants
    private final static String HOSTNAME = "host";

    // class singletons
    private static Logger log = Logger.getLogger(ScriptDistributionDaemonImpl.class);

    // class private member variables
    private String hostname;
    private ThreadStateMonitor monitor = new ThreadStateMonitor();


    /**
     * The constructor for the script ditribution
     */
    public ScriptDistributionDaemonImpl() throws ScriptBrokerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ScriptDistributionDaemonImpl.class);
            hostname = config.getString(HOSTNAME);
            ChangeLog.init(ScriptDistributionDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed init the distribution daemon : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed init the distribution daemon : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the change sets.
     *
     * @return The list of revisions.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptChangeSet> listChangeSets() throws ScriptBrokerException {
        try {
            ScriptRevisionManagerDaemonImpl server = (ScriptRevisionManagerDaemonImpl)ConnectionManager.getInstance().
                    getConnection(ScriptRevisionManagerDaemonImpl.class, "java:comp/env/bean/script/RevisionManagementDaemon");
            return server.listChangeSets();
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of changes : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                ("Failed to retrieve the list of changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to commit the changes associated with a given change set.
     *
     * @param changeSet The change set.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void distributeLocalChanges(RDFScriptChangeSet changeSet) throws ScriptBrokerException {
        try {
            Session session = SemanticUtil.getInstance(ScriptManagerDaemonImpl.class).getSession();
            Resource resource = session.getResource(RDFScriptChangeSet.class, changeSet, changeSet.getId());
            List<Resource> properties = resource.listProperties(Constants.CHANGE_REF);
            List<RDFScriptInfo> changes = new ArrayList<RDFScriptInfo>();
            for (Resource change : properties) {
                changes.add(change.get(RDFScriptChangeInfo.class).getScript());
            }
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            ScriptDistributionDaemonAsync helper = (ScriptDistributionDaemonAsync)RPCMessageClient.
                    createOneWay("script/DistributionDaemon", ScriptDistributionDaemon.class, 
                    ScriptDistributionDaemonAsync.class, services, true);
            helper.acceptChanges(hostname, changes);

        } catch (Exception ex) {
            log.error("Failed to distribute the local changes : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                ("Failed to distribute the local changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to retrieve the changes and apply them to the local directory.
     * @param hostname
     * @param changes
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void acceptChanges(String hostname, List<RDFScriptInfo> changes) 
            throws ScriptBrokerException, RemoteException {
        if (this.hostname.equals(hostname)) {
            // ignore these changes
            return;
        }
        for (RDFScriptInfo change : changes) {
            try {
                ChangeLog.getInstance().addChange(new ScriptChange(change));
            } catch (Exception ex) {
                log.error("Failed to add the change to the change log : " + ex.getMessage(),ex);
                throw new RemoteException
                        ("Failed to add the change to the change log : " + ex.getMessage(),ex);
            }
        }
    }


    /**
     * This method processes
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        try {
            ChangeLog.getInstance().start();
        } catch (Exception ex) {
            log.error("Failed to start the change log processing : "
                    + ex.getMessage(),ex);
        }
        // register the audit trail logger with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            broker.registerService("script/DistributionDaemon", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }

        while(!monitor.isTerminated()) {

            // wait indefinitly
            monitor.monitor();
        }

        try {
            log.info("Waiting for all uncommited changes to be dumped");
            ChangeLog.terminate();
            log.info("Changes have been dumped.");
        } catch (Exception ex) {
            log.error("Failed to shut down the change log : "
                    + ex.getMessage(),ex);
        }

    }

    /**
     * The terminate method
     */
    public void terminate() {
        monitor.terminate(true);
    }

}
