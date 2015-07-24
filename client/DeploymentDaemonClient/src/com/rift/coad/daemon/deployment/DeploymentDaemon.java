/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * DeploymentDaemon.java
 */

package com.rift.coad.daemon.deployment;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This Daemon allows users to remotely upload either Daemons or any other file 
 * to the Coadunation server.
 *
 * @author Glynn Chaldecott
 */
public interface DeploymentDaemon extends Remote {
    
    /**
     * This method is used when a user wishes to remotely deploy a Daemon to 
     * Coadunation.
     *
     * @param file This is a byte[] containing the contents of the jar file.
     * @param name This is the name of the Daemon. Please note that for a 
     * windows installation it will require that the extension be included 
     * in the name.
     */
    public void daemonDeployer(byte[] file, String name) 
            throws RemoteException, DeploymentDaemonException;
    
    /**
     * This method is used when a user needs to remotely upload a file to the 
     * Coadunation server.
     *
     * @param file This is a byte[] containing the contents of the file.
     * @param name This is the name of the file.
     * @param location This is the location that the file will be stored. Please
     * note the for a windows installation the name will have to include the 
     * extension.
     */
    public void copyFile(byte[] file, String name, String location) 
            throws RemoteException;
}
