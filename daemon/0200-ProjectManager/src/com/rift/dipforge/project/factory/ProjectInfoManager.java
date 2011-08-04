/*
 * 0200-ProjectManager: The project manager implentation.
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
 * ProjectInfoManager.java
 */

package com.rift.dipforge.project.factory;

import com.rift.dipforge.project.Constants;
import com.rift.dipforge.project.ProjectInfoDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;

/**
 * The object that is responsible for the persistance management of the project
 * information.
 *
 * @author brett chaldecott
 */
public class ProjectInfoManager {

    /**
     * The constants for the project info object.
     */
    public static class ProjectInfoConstants {
        /**
         * The author variable within the properties file.
         */
        public static final String AUTHOR = "author";


        /**
         * The created stamp
         */
        public static final String CREATED = "created";


        /**
         * This method sets the description
         */
        public static final String DESCRIPTION = "description";


        /**
         * This method sets the modified by date.
         */
        public static final String MODIFIED = "modified";


        /**
         * This method sets the modified by date.
         */
        public static final String MODIFIED_BY = "modified_by";
        

        /**
         * The string name identifier,
         */
        public static final String NAME = "name";


        /**
         * The date it was formatted for
         */
        public static SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
    }

    // private logger
    private static Logger log = Logger.getLogger(ProjectInfoManager.class);


    // project information
    private File projectFile;

    /**
     * This constructor sets up the project info manager.
     *
     * @param projectDir The project directory.
     * @throws ProjectFactoryException
     */
    public ProjectInfoManager(File projectDir) throws ProjectFactoryException {
        try {
            this.projectFile = new File(projectDir,Constants.PROJECT_INFO_FILE);
            if (!projectFile.exists()) {
                log.error(
                    "The project information file [" + projectFile.getPath() +
                    "] does not exist.");
                throw new ProjectFactoryException(
                    "The project information file [" + projectFile.getPath() +
                    "] does not exist.");
            }
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("The project information file [" + projectFile.getPath() +
                    "] does not exist.");
            throw new ProjectFactoryException(
                    "Failed to retrieve the project information : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method gets the information from a project.
     *
     * @return The project information.
     * @throws ProjectFactoryException
     */
    public ProjectInfoDTO getInfo() throws ProjectFactoryException {
        try {
            ProjectInfoDTO info = new ProjectInfoDTO();
            Properties projectPropertiesFile = new Properties();
            FileInputStream in = new FileInputStream(projectFile);
            projectPropertiesFile.load(in);
            in.close();
            info.setAuthor(projectPropertiesFile.getProperty(ProjectInfoConstants.AUTHOR));
            info.setCreated(ProjectInfoConstants.DateFormatter.parse(
                    projectPropertiesFile.getProperty(ProjectInfoConstants.CREATED)));
            info.setDescription(projectPropertiesFile.getProperty(ProjectInfoConstants.DESCRIPTION));
            info.setModified(ProjectInfoConstants.DateFormatter.parse(
                    projectPropertiesFile.getProperty(ProjectInfoConstants.MODIFIED)));
            info.setModifiedBy(projectPropertiesFile.getProperty(ProjectInfoConstants.MODIFIED_BY));
            info.setName(projectPropertiesFile.getProperty(ProjectInfoConstants.NAME));
            return info;
        } catch (Exception ex) {
            log.error("Failed to retrieve the project information : " +
                    ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to retrieve the project information : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the information.
     *
     * @param info The information to set.
     */
    public void setInfo(ProjectInfoDTO info) throws ProjectFactoryException {
        try {
            Properties projectPropertiesFile = new Properties();
            projectPropertiesFile.setProperty(ProjectInfoConstants.AUTHOR, info.getAuthor());
            projectPropertiesFile.setProperty(ProjectInfoConstants.CREATED,
                    ProjectInfoConstants.DateFormatter.format(info.getCreated()));
            projectPropertiesFile.setProperty(ProjectInfoConstants.DESCRIPTION, info.getDescription());
            projectPropertiesFile.setProperty(ProjectInfoConstants.MODIFIED,
                    ProjectInfoConstants.DateFormatter.format(info.getModified()));
            projectPropertiesFile.setProperty(ProjectInfoConstants.MODIFIED_BY, info.getModifiedBy());
            projectPropertiesFile.setProperty(ProjectInfoConstants.NAME, info.getName());
            FileOutputStream out = new FileOutputStream(projectFile);
            projectPropertiesFile.store(out, "Project Properties maintained Dipforge");
            out.close();
        } catch (Exception ex) {
            log.error("Failed to retrieve the project information : " +
                    ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to retrieve the project information : " +
                    ex.getMessage(),ex);
        }
    }
}
