/*
 * DesktopServerClient: The client interface to the desktop server.
 * Copyright (C) 2008  Rift IT Contracting
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
 * DesktopManager.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface provide a means to manage an individual desktop.
 * 
 * @author brett chaldecott
 */
public interface Desktop extends Remote {
    /**
     * This method returns a list of applications.
     * 
     * @return A list of all the applications.
     * @param name The name of the application to add.
     * @throws DesktopException
     * @throws RemoteException
     */
    public List<AppInfo> getApps(String name) throws DesktopException, RemoteException;
    
    
    /**
     * This method adds a running destkop app.
     * 
     * @param name The name of the desktop app to add.
     * @param info The information about the desktop app.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void addApp(String name, AppInfo info) throws DesktopException, RemoteException;
    
    
    /**
     * This method updates the application information.
     * 
     * @param name The name of the dektop to update the app for.
     * @param info The updated application information
     * @throws DesktopException
     * @throws RemoteException
     */
    public void updateApp(String name, AppInfo info) throws DesktopException, RemoteException;
    
    
    /**
     * This method removes an app from the desktop.
     * 
     * @param name The name of the desktop to perform the operation on.
     * @param identifier The identifier for the application
     * @throws DesktopException
     * @throws RemoteException
     */
    public void removeApp(String name, String identifier) throws DesktopException, RemoteException;
}
