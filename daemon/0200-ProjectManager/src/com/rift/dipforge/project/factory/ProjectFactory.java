/*
 * 0200-ProjectManager: The project manager implentation.
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
 * ProjectFactory.java
 */


// package path
package com.rift.dipforge.project.factory;

// imports
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.project.Constants;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 * The project factory
 *
 * @author brett chaldecott
 */
public class ProjectFactory {

    // class singleton
    private static ProjectFactory singleton = null;
    private static Logger log = Logger.getLogger(ProjectFactory.class);

    // private member variables.
    private Map<String, ProjectBean> entries = new ConcurrentHashMap<String, ProjectBean>();
    private String basePath;
    private String templatePath;

    /**
     * The private constructor of the project factory.
     *
     * @throws ProjectFactoryException
     */
    private ProjectFactory() throws ProjectFactoryException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProjectFactory.class);
            basePath = config.getString(Constants.PROJECT_BASE);
            templatePath = config.getString(Constants.TEMPLATE_DIR);
        } catch (Exception ex) {
            log.error("Failed to load the configuration : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to load the configuration : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a reference to the project factory.
     *
     * @return The reference to the project factory.
     * @throws ProjectFactoryException
     */
    public synchronized static ProjectFactory getInstance() throws ProjectFactoryException {
        if (singleton == null) {
            singleton = new ProjectFactory();
        }
        return singleton;
    }


    /**
     * This method creates new project.
     *
     * @param project
     * @return
     * @throws ProjectFactoryException
     */
    public ProjectBean createProject(String project, String description) throws ProjectFactoryException {
        ProjectBean projectBean = new ProjectBean(new File(basePath), project,
                description, new File(templatePath));
        entries.put(project, projectBean);
        return projectBean;
    }


    /**
     * This method creates new project.
     *
     * @param project
     * @return
     * @throws ProjectFactoryException
     */
    public ProjectBean createProject(String project, String description, String projectType) throws ProjectFactoryException {

        String templatePath  = this.templatePath;
        if (!projectType.equals("simple")) {
            templatePath = String.format("%s_%s",this.templatePath,projectType);
        }
        ProjectBean projectBean = new ProjectBean(new File(basePath), project,
                description, new File(templatePath));
        entries.put(project, projectBean);
        return projectBean;
    }


    /**
     * This method returns the project information.
     *
     * @param project The reference to the project.
     * @return The project bean.
     * @throws ProjectFactoryException
     */
    public ProjectBean getProject(String project) throws ProjectFactoryException {
        File projectDir = new File(basePath,project);
        if (!projectDir.exists()) {
            throw new ProjectFactoryException("The project [" + project +
                    "] does not exist.");
        }
        if (!entries.containsKey(project)) {
            entries.put(project, new ProjectBean(projectDir));
        }
        return entries.get(project);
    }


    /**
     * This method is called to remove the project.
     *
     * @param project The project to remove
     * @throws ProjectFactoryException
     */
    public void removeProject(String project) throws ProjectFactoryException {
        try {
            File projectDir = new File(basePath,project);
            if (!projectDir.exists()) {
                throw new ProjectFactoryException("The project [" + project +
                        "] does not exist.");
            }
            ProjectBean projectBean = null;
            if (!entries.containsKey(project)) {
                projectBean = entries.remove(project);
            } else {
                projectBean = new ProjectBean(projectDir);
            }
            FileUtil.delTargetRecursive(projectDir);
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the project [" + project +
                    "] because : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to remove the project [" + project +
                    "] because : " + ex.getMessage(),ex);
        }
    }
}
