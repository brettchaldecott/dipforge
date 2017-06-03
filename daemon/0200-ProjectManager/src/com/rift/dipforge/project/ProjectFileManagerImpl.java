/*
 * ProjectManager: The project manager implentation.
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
 * ProjectManagerImpl.java
 */

// package
package com.rift.dipforge.project;

// import
import com.rift.dipforge.project.factory.ProjectBean;
import com.rift.dipforge.project.factory.ProjectFactory;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implementation of the project file manager.
 *
 * @author brett chaldecott
 */
public class ProjectFileManagerImpl implements ProjectFileManager {

    // class static variables
    private static Logger log = Logger.getLogger(ProjectFileManagerImpl.class);

    /**
     * The project file manager implementation.
     */
    public ProjectFileManagerImpl() {
    }


    /**
     * The create directory.
     *
     * @param project The project file.
     * @param directory The directory.
     * @throws ProjectException
     */
    public void createDirectory(String project, String directory)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.createDirectory(directory);
        } catch (Exception ex) {
            log.error("Failed to create the directory : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to create the directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * The remove directory,
     *
     * @param project The project that contains the directory to remove.
     * @param directory The directory to remove.
     * @throws ProjectException
     */
    public void removeDirectory(String project, String directory)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.removeDirectory(directory);
        } catch (Exception ex) {
            log.error("Failed to remove the directory : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to remove the directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists all the files within a project directory.
     *
     * @param name The name of the project.
     * @param directory The path to the directory. Starts with . for the beginning.
     * @return The list of files.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFiles(String project, String directory)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            return projectBean.listFiles(directory);
        } catch (Exception ex) {
            log.error("Failed to list the files for the directory : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to list the files for the directory : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method lists all the files within a project directory.
     *
     * @param project The name of the project.
     * @return The list of folders.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFolders(String project)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            return projectBean.listFolders();
        } catch (Exception ex) {
            log.error("Failed to list the files for the directory : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to list the files for the directory : " + ex.getMessage(),ex);
        }

    }


    /**
     * This method lists all the files within a project directory.
     *
     * @param project The name of the project.
     * @param directoryCommaList The list of directories to search below
     * @return The list of folders.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFolders(String project, String directoryCommaList)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            return projectBean.listFolders(directoryCommaList);
        } catch (Exception ex) {
            log.error("Failed to list the files for the directory : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to list the files for the directory : " + ex.getMessage(),ex);
        }

    }


    /**
     * This method creates the file.
     * @param project The project to create.
     * @param path The path.
     * @param type The type of file.
     * @throws ProjectException
     */
    public void createFile(String project, String path, String type)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.createFile(path,type);
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to create the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method creates the file.
     * @param project The project to create.
     * @param path The path.
     * @param type The type of file.
     * @param context The context of the file
     * @throws ProjectException
     */
    public void createFile(String project, String path, String type, String context)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.createFile(path,type,context);
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to create the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the file identified by the project and path.
     *
     * @param project The project containing the file.
     * @param path The path to the file within the project.
     * @return The contents of the file.
     * @throws ProjectException
     */
    public String getFile(String project, String path) throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            return projectBean.getFile(path);
        } catch (Exception ex) {
            log.error("Failed to get the file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to get the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the file.
     *
     * @param project The project that contains the file.
     * @param path The path to the file.
     * @param contents The contents of the file to update.
     * @throws ProjectException
     */
    public void updateFile(String project, String path, String contents)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.updateFile(path,contents);
        } catch (Exception ex) {
            log.error("Failed to update the file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to update the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method renames the file within the project.
     *
     * @param project The name of the project containing the file to rename.
     * @param source The source path to the file.
     * @param target The target path to the file.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void renameFile(String project, String source, String target)
             throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.renameFile(source,target);
        } catch (Exception ex) {
            log.error("Failed to rename the files : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to rename the files : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the file contained within the specified project.
     * @param project The
     * @param path
     * @throws ProjectException
     */
    public void removeFile(String project, String path)
            throws ProjectException {
        try {
            ProjectBean projectBean =
                    ProjectFactory.getInstance().getProject(project);
            projectBean.removeFile(path);
        } catch (Exception ex) {
            log.error("Failed to remove the files : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to remove the files : " + ex.getMessage(),ex);
        }
    }

}
