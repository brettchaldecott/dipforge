/*
 * DesktopServerClient: The client interface to the desktop server.
 * Copyright (C) 2008  2015 Burntjam
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

// package pat
package com.rift.coad.daemon.desktop;


// java import
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface provides a means to manage all the desktops used by a user.
 * 
 * @author brett chaldecott
 */
public interface DesktopManager extends Remote {
    /**
     * This method lists the desktops in place for a user.
     * 
     * @return The list of desktop information objects.
     * @throws DesktopException
     * @throws RemoteException
     */
    public List<DesktopInfo> listDesktops() throws DesktopException, RemoteException;
    
    
    /**
     * This method adds the specified desktop to the list of available desktops.
     * 
     * @param name The name of ths desktop.
     * @throws DuplicateEntryException
     * @throws DesktopException
     * @throws RemoteException
     */
    public void addDesktop(String name) throws DuplicateEntryException, DesktopException, RemoteException;
    
    
    /**
     * This method returns the information for a specified desktop.
     * 
     * @param name The name of the desktop to retrieve the information for.
     * @return The desktop information to retrieve.
     * @throws com.rift.coad.daemon.desktop.EntryNotFoundException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     * @throws java.rmi.RemoteException
     */
    public DesktopInfo getDesktopInfo(String name) throws EntryNotFoundException, DesktopException, RemoteException;
    
    /**
     * This method renames a desktop.
     * 
     * @param oldName The old name of the desktop.
     * @param newName The new name of the desktop.
     * @throws com.rift.coad.daemon.desktop.DuplicateEntryException
     * @throws com.rift.coad.daemon.desktop.EntryNotFoundException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     * @throws java.rmi.RemoteException
     */
    public void renameDesktop(String oldName, String newName) throws 
            DuplicateEntryException, EntryNotFoundException, DesktopException, RemoteException;
    
    
    /**
     * This method removes the specified desktop from the list.
     * 
     * @param name The name of the desktop to remove.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void removeDesktop(String name) throws DesktopException, RemoteException;
    
    
    /**
     * This method returns a list of the desktop applications.
     * 
     * @return The list of desktop applications.
     * @throws com.rift.coad.desktop.client.desk.DesktopRPCException
     */
    public List<MimeType> listDesktopApplications(String desktop) throws DesktopException, RemoteException;
}
