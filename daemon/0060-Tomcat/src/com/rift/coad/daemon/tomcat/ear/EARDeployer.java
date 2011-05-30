/*
 * Tomcat: The deployer for the tomcat daemon
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
 * EARDeployer.java
 */

// package
package com.rift.coad.daemon.tomcat.ear;

import com.rift.coad.daemon.tomcat.TomcatWrapper;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.BasicRunnable;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The ear deployer
 *
 * @author brett chaldecott
 */
public class EARDeployer extends BasicThread {

    

    // the logger reference
    protected Logger log =
            Logger.getLogger(EARDeployer.class.getName());

    // private member variables
    private ThreadStateMonitor state = null;
    private File deploymentDir = null;
    


    /**
     * The default constructor
     *
     * @throws Exception
     */
    public EARDeployer() throws Exception {
        try {
            Configuration configuration = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            state = new ThreadStateMonitor(configuration.getLong(
                    "DeployCheckInterval",1000));
            Configuration deployConfig = ConfigurationFactory.getInstance().
                    getConfig(com.rift.coad.lib.deployment.
                    DeploymentManager.class);
            deploymentDir = new File(deployConfig.getString("directory"));

            

            // load the deployed entries
            EARDeploymentManager.getInstance();

        } catch (Exception ex) {
            log.error("Failed to initialize the configuration because : " +
                    ex.getMessage(),ex);
            throw new EARException(
                    "Failed to initialize the configuration because : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to
     *
     * @throws Exception
     */
    @Override
    public void process() throws Exception {
        while (!state.isTerminated()) {
            try {
                // the reference to the manager
                EARDeploymentManager manager = EARDeploymentManager.getInstance();

                List<EARDeployEntry> entries = manager.listEntries();
                for (EARDeployEntry entry : entries) {
                    if (entry.getFile().exists() && entry.isValid()) {
                        continue;
                    }
                    manager.undeployEntry(entry.getFile());
                }

                log.debug("Process the directory : " + deploymentDir);
                File[] earFiles = FileUtil.filter(deploymentDir.listFiles(),".ear");
                for (File earFile: earFiles) {
                    if (manager.contains(earFile)) {
                        continue;
                    }
                    log.info("Attempt to deploy the file : " + earFile);
                    manager.deployEntry(earFile);
                }
            } catch (Exception ex) {
                log.error("Failed to process the ear files bcause : " +
                        ex.getMessage(),ex);
            }
            // check the state
            state.monitor();
        }
    }


    /**
     * This method is called to terminate the processing.
     */
    @Override
    public void terminate() {
        state.terminate(true);
        try{
            this.join();
        } catch (Exception ex) {
            log.error("Failed to wait for tomcat :" + ex.getMessage(),ex);
        }
        log.info("Finished waiting for tomcat to shut down.");
    }



}
