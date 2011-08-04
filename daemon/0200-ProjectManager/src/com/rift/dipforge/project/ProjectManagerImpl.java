/*
 * ProjectManager: The project manager implentation.
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
 * ProjectManagerImpl.java
 */

package com.rift.dipforge.project;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.project.factory.ProjectBean;
import com.rift.dipforge.project.factory.ProjectFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implementation of the project manager.
 *
 * @author brett chaldecott
 */
public class ProjectManagerImpl implements ProjectManager {

    // class singletons.
    public static Logger log = Logger.getLogger(ProjectManagerImpl.class);

    // private member variables
    private String projectDir;

    /**
     * The constructor of the project manager implementation.
     */
    public ProjectManagerImpl() throws ProjectException {

        try {
            Configuration configuration = ConfigurationFactory.getInstance().getConfig(
                    ProjectManagerImpl.class);
            projectDir = configuration.getString(
                    Constants.PROJECT_BASE);

        } catch (Exception ex) {
            log.error("Failed to setup the project manager :" + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to setup the project manager :" + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method lists the projects.
     * 
     * @return The list of projects
     * @throws ProjectException
     */
    public List<ProjectInfoDTO> listProjects() throws ProjectException {
        try {
            File dir = new File(projectDir);
            File[] files = dir.listFiles();
            List<ProjectInfoDTO> projectList = new ArrayList<ProjectInfoDTO>();
            for (File file : files) {
                if (!file.isDirectory()) {
                    continue;
                }
                ProjectBean project = ProjectFactory.getInstance().getProject(
                        file.getName());
                projectList.add(project.getInfo());
            }
            return projectList;
        } catch (Exception ex) {
            log.error("Failed to list the projects : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to list the projects : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method creates the project.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @throws ProjectException
     */
    public void createProject(String name, String description) throws ProjectException {
        try {
            ProjectBean project = ProjectFactory.getInstance().createProject(name);
            ProjectInfoDTO info = project.getInfo();
            info.setDescription(description);
            project.setInfo(info);
        } catch (Exception ex) {
            log.error("Failed to create the project : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to create the project : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method deletes the project identified by the name.
     *
     * @param name The name of the project
     * @throws ProjectException
     */
    public void deletetProject(String name) throws ProjectException {
        try {
            ProjectFactory.getInstance().removeProject(name);
        } catch (Exception ex) {
            log.error("Failed to delete the project : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to delete the project : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the project information.
     *
     * @param name The name of the project.
     * @return The project information
     * @throws ProjectException
     * @throws RemoteException
     */
    public ProjectInfoDTO getProject(String name) throws ProjectException {
        try {
            return ProjectFactory.getInstance().getProject(name).getInfo();
        } catch (Exception ex) {
            log.error("Failed to get the project : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to get the project : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method updates the project information.
     *
     * @param info The project information
     * @throws ProjectException
     * @throws RemoteException
     */
    public void updateProject(ProjectInfoDTO info) throws ProjectException {
        try {
            ProjectFactory.getInstance().getProject(info.getName()).setInfo(info);
        } catch (Exception ex) {
            log.error("Failed to set the project : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to set the project : " + ex.getMessage(),ex);
        }
    }
}
