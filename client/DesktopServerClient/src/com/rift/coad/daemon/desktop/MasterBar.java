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
 * MasterBar.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface provides access to the master bar
 * information.
 * 
 * @author brett chaldecott
 */
public interface MasterBar extends Remote {
    /**
     * This method returns a list of menu items.
     * 
     * @return The list of menu items.
     * @param RemoteException
     */
    public List<Menu> getMenus() throws DesktopException, RemoteException;
    
    
    /**
     * This method sets the menue values.
     * 
     * @param menus The list of menu values to set.
     * @exception RemoteException;
     */
    public void setMenus(List<Menu> menus) throws DesktopException, RemoteException;
    
    
    /**
     * This method returns the list of master bar gadgets.
     * 
     * @return The list of gadgets.
     * @throws DesktopException
     * @throws RemoteException
     */
    public List<GadgetInfo> getGadgets() throws DesktopException, RemoteException;
    
    
    /**
     * This method adds a gadget to the list of gadgets used by the current user.
     * 
     * @param gadget The gadget to add to the users current list.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void addGadget(GadgetInfo gadget) throws DesktopException, RemoteException;
    
    
    /**
     * This method removes the specified gadget.
     * 
     * @param identifier The identifier of the gadget to remove.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void removeGadget(String identifier) throws DesktopException, RemoteException;
}
