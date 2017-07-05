/*
 * ProjectClient: The project client interface..
 * Copyright (C) 2011  2015 Burntjam
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
import java.util.List;

/**
 * The project manager.
 *
 * @author brett chaldecott
 */
public interface ProjectManager extends Remote {


    /**
     * This method lists the projects.
     *
     * @return
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<ProjectInfoDTO> listProjects() throws ProjectException, RemoteException;

    /**
     * This method is called to create the project.
     *
     * @param name The name of the project to create.
     * @param description The description of the project.
     * @throws ProjectException
     */
    public void createProject(String name, String description) throws ProjectException, RemoteException;


    /**
     * This method is called to create the project.
     *
     * @param name The name of the project to create.
     * @param description The description of the project.
     * @param type The type of of project.
     * @throws ProjectException
     */
    public void createProject(String name, String description, String projectType) throws ProjectException, RemoteException;


    /**
     * This method deletes the project identified by name.
     *
     * @param name The name of the project to delete.
     * @throws ProjectException
     */
    public void deleteProject(String name) throws ProjectException, RemoteException;


    /**
     * This method retrieves the project information.
     *
     * @param name The name of the project.
     * @return The project information
     * @throws ProjectException
     * @throws RemoteException
     */
    public ProjectInfoDTO getProject(String name) throws ProjectException, RemoteException;


    /**
     * This method updates the project information.
     *
     * @param info The project information
     * @throws ProjectException
     * @throws RemoteException
     */
    public void updateProject(ProjectInfoDTO info) throws ProjectException, RemoteException;

    
}
