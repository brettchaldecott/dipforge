/*
 * ProjectClient: The project client interface..
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
 * ProjectActionManager.java
 */
package com.rift.dipforge.project;

// imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface defines the methods to interact with the project action manager
 * 
 * @author brett chaldecott
 */
public interface ProjectActionManager extends Remote {
    
    /**
     * This method is called to publish the action.
     * 
     * @param content The content of the action.
     * @throws ProjectException
     * @throws RemoteException 
     */
    public void publishActions(String content) throws ProjectException, 
            RemoteException;
}
