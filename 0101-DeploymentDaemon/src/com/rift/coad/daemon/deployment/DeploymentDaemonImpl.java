/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  Rift IT Contracting
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
 * DeploymentDaemonImpl.java
 */

package com.rift.coad.daemon.deployment;

import com.rift.coad.lib.configuration.ConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 * This Daemon allows users to remotely upload either Daemons or any other file
 * to the Coadunation server.
 *
 * @author Glynn Chaldecott
 */
public class DeploymentDaemonImpl implements DeploymentDaemon {
    
    protected Logger log =
            Logger.getLogger(DeploymentDaemonImpl.class.getName());
    
    String coadLocal = "";
    String coadunationTmp = "";
    
    /** Creates a new instance of DeploymentDaemonImpl */
    public DeploymentDaemonImpl() throws Exception {
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance().getConfig(com.rift.coad.daemon.deployment.
                    DeploymentDaemonImpl.class);
            coadLocal = coadConfig.getString("coadunation_deploy");
            coadunationTmp = coadConfig.getString("coadunation_temp");
        } catch (ConfigurationException ex) {
            log.error("Failed to set jython properties :" + ex.getMessage(),
                    ex);
            throw new Exception("Failed to set jython properties :" + ex);
        }
    }
    
    /**
     * This method is used when a user wishes to remotely deploy a Daemon to
     * Coadunation.
     *
     * @param file This is a byte[] containing the contents of the jar file.
     * @param name This is the name of the Daemon.
     * @param extension This is the file extension. It has to be .jar otherwise
     *          the method will throw an error.
     */
    public void daemonDeployer(byte[] file, String name)
            throws RemoteException, DeploymentDaemonException {
        try {
            File temp = File.createTempFile(name, null);
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(file);
            fos.close();
            File supFile = new File(coadLocal + File.separator + name);
            temp.renameTo(supFile);
        } catch (IOException ex) {
            log.error("Failed to copy file:" + ex, ex);
        }
    }
    
    /**
     * This method is used when a user needs to remotely upload a file to the
     * Coadunation server.
     *
     * @param file This is a byte[] containing the contents of the file.
     * @param name This is the name of the file.
     * @param location This is the location that the file will be stored.
     * @param extension This is the file's extension.
     */
    public void copyFile(byte[] file, String name, String location) 
            throws RemoteException {
        try {
            File temp = File.createTempFile(name, null);
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(file);
            fos.close();
            File supFile = new File(location + File.separator + name);
            temp.renameTo(supFile);
        } catch (IOException ex) {
            log.error("Failed to copy file:" + ex, ex);
        }
    }
    
}
