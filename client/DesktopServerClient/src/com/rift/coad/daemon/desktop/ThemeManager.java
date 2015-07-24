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
 * ThemeManager.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * This object is responsible for managing the themes.
 * 
 * @author brett chaldecott
 */
public interface ThemeManager extends Remote {
    /**
     * This method returns the string containing the name of the current set theme for this user.
     * 
     * @return The string containing the theme name.
     * @throws DesktopException
     * @throws RemoteException
     */
    public String getTheme() throws DesktopException, RemoteException;
    
    
    /**
     * This method sets the theme name for the current user.
     * 
     * @param theme The string containing the theme name.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void setTheme(String theme) throws DesktopException, RemoteException;
    
    
    /**
     *  This method lists the current available themes.
     * 
     * @return The list of themes.
     * @throws DesktopException
     * @throws RemoteException
     */
    public List<String> listThemes() throws DesktopException, RemoteException;
    
    
    /**
     *  This method lists the css files.
     * 
     * @return The list of css files.
     * @throws DesktopException
     * @throws RemoteException
     */
    public List<String> listCSS() throws DesktopException, RemoteException;
    
    
    /**
     * This method adds a new theme.
     *  
     * @param theme 
     * @param cssFile
     * @throws DesktopException
     * @throws RemoteException
     */
    public void addTheme(String theme, String cssFile) throws DesktopException, RemoteException;
    
    
    /**
     * This method removes the specified theme.
     * 
     * @param theme The name of the theme to remove.
     * @throws DesktopException
     * @throws RemoteException
     */
    public void removeTheme(String theme) throws DesktopException, RemoteException;
}
