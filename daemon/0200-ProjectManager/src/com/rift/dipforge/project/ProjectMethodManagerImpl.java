/*
 * ProjectDaemon: The project daemon implementation
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
 * ProjectTypeManagerImpl.java
 */

package com.rift.dipforge.project;

import com.rift.dipforge.project.factory.ProjectBean;
import com.rift.dipforge.project.factory.ProjectFactory;
import com.rift.dipforge.project.method.XMLMethodMappingParser;
import org.apache.log4j.Logger;



/**
 * This class
 * 
 * @author brett chaldecott
 */
public class ProjectMethodManagerImpl implements ProjectMethodManager {

    // class static variables
    private static Logger log = Logger.getLogger(ProjectMethodManagerImpl.class);

    
    /**
     * The default constructor of the project method manager.
     */
    public ProjectMethodManagerImpl() {
    }

    
    /**
     * This method returns the xml defintion for the methods.
     * 
     * @param project The name of the project to retrieve the method definition for.
     * @return The string containing the xml definition for the method mapping.
     * @throws ProjectException
     */
    public String getProjectMethods(String project) throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            return projectBean.getFile(Constants.CONFIG_DIRECTORY + 
                    Constants.PROJECT_METHODS);
        } catch (Exception ex) {
            log.error("Failed to retrieve the methods file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to retrieve the methods file : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the project method mappings.
     * 
     * @param project The string containing the project name.
     * @param xml The xml for the project.
     * @throws ProjectException 
     */
    public void setProjectMethods(String project, String xml) throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.updateFile(Constants.CONFIG_DIRECTORY + 
                    Constants.PROJECT_METHODS,xml);
        } catch (Exception ex) {
            log.error("Failed to update the methods file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to update the methods file : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is responsible for publishing the methods.
     * 
     * @param project The string containing name of the project to publish the methods for.
     * @throws ProjectException
     */
    public void publishMethods(String project) throws ProjectException {
        try {
            XMLMethodMappingParser parser = new XMLMethodMappingParser(
                    this.getProjectMethods(project));
            
        } catch (ProjectException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to publish the methods : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to publish the methods : " + ex.getMessage(),ex);
        }
    }
    
    
}
