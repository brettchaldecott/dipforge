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
 * ProjectTypeManager.java
 */

package com.rift.dipforge.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface provides methods to access the project type management information.
 *
 * @author brett chalecott
 */
public interface ProjectTypeManager extends Remote {


    /**
     * This method retrieves the project types encapsulated in a string.
     *
     * @param project The name of the project to retrieve the types for.
     * @return The string containing the types.
     * @throws ProjectException
     * @throws RemoteException
     */
    public String getProjectTypes(String project) throws ProjectException, RemoteException;


    /**
     * This method sets the project types information.
     *
     * @param project The project information.
     * @param xml The xml containing the project information.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void setProjectTypes(String project, String xml) throws ProjectException, RemoteException;


    /**
     * This method is called to publishes the types to the type manager.
     *
     * @throws ProjectException
     * @throws RemoteException
     */
    public void publishTypes(String project) throws ProjectException, RemoteException;
}
