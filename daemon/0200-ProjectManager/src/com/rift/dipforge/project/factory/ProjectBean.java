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
 * ProjectBean.java
 */

// package information
package com.rift.dipforge.project.factory;

import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.project.Constants;
import com.rift.dipforge.project.FileDTO;
import com.rift.dipforge.project.FileTypes;
import com.rift.dipforge.project.ProjectInfoDTO;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;

/**
 * The project bean.
 *
 * @author brett chaldecott
 */
public class ProjectBean {

    // TODO: File Meta Data System
    // Currently the java libraries provide very basic meta data about files.
    // This needs to be extended in future versions.


    // class log
    private static Logger log = Logger.getLogger(ProjectBean.class);

    // private member variables
    private String name;
    private File projectDir;
    private ProjectInfoManager manager;
    private String templateDir;

    /**
     * The constructor of the project bean.
     */
    public ProjectBean(File projectDir) throws ProjectFactoryException {
        try {
            this.projectDir = projectDir;
            manager = new ProjectInfoManager(projectDir);
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProjectBean.class);
            templateDir = config.getString(Constants.TEMPLATE_DIR);
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to init the project because : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                ("Failed to init the project because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This constructor is responsible for creating a new project bean instance.
     *
     * @param baseDir The base directory.
     * @param name The name of the project.
     */
    public ProjectBean(File baseDir, String name, File templateDirectory) throws ProjectFactoryException {
        try {
            this.name = name;
            projectDir = createProject(baseDir, name, templateDirectory);
            manager = new ProjectInfoManager(projectDir);
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProjectBean.class);

        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to init the project because : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                ("Failed to init the project because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the information about a project.
     *
     * @return The information about a project.
     * @throws ProjectFactoryException
     */
    public ProjectInfoDTO getInfo() throws ProjectFactoryException {
        return manager.getInfo();
    }


    /**
     * This method sets the project info.
     *
     * @param info
     * @throws ProjectFactoryException
     */
    public void setInfo(ProjectInfoDTO info) throws ProjectFactoryException {
        manager.setInfo(info);
    }


    /**
     * This method creates a new directory.
     *
     * @param directory The directory.
     * @throws ProjectException
     */
    public void createDirectory(String directory) throws ProjectFactoryException {
        try {
            File  dir = new File(projectDir,directory);
            if (dir.exists()) {
                throw new ProjectFactoryException("The directory [" + directory + "] already exists");
            }
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the directory : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * The project name.
     *
     * @param directory The name of the project.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void removeDirectory(String directory) throws ProjectFactoryException {
        try {
            File  dir = new File(projectDir,directory);
            if (!dir.exists()) {
                throw new ProjectFactoryException("The directory [" + directory + "] already exists");
            } else if (!dir.isDirectory()) {
                throw new ProjectFactoryException("The directory [" + directory + "] already exists");
            }
            removeDirectory(dir);
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the directory : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to recursively delete a directory structure.
     *
     * @param directory The directory to delete.
     * @throws ProjectFactoryException
     */
    private void removeDirectory(File directory) throws ProjectFactoryException {
        try {
            for (File file: directory.listFiles()) {
                if (file.isDirectory()) {
                    removeDirectory(file);
                }
                file.delete();
            }
        } catch (Exception ex) {
            log.error("Failed to remove the directory : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the files in a directory.
     *
     * @param name The name of the project
     * @param directory The directory list.
     * @return The list of files.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFiles(String directory)
            throws ProjectFactoryException {
        try {
            File  dir = new File(projectDir,directory);
            if (!dir.exists()) {
                throw new ProjectFactoryException("The directory [" + directory + "] does not exist");
            }
            List<FileDTO> files = new ArrayList<FileDTO>();
            for (File file: dir.listFiles()) {
                FileDTO fileDto = new FileDTO();
                // at this point the java libraries to not provide
                // a means to get at the created date of a file.
                fileDto.setCreated(new Date(file.lastModified()));
                fileDto.setCreator(null);
                fileDto.setModified(new Date(file.lastModified()));
                fileDto.setModifier(null);
                fileDto.setName(file.getName());
                // construct a relative path based on the directory passed in.
                fileDto.setPath(directory + "/" + file.getName());
                if (file.isDirectory()) {
                    fileDto.setType(FileTypes.DIRECTORY);
                } else {
                    fileDto.setType(FileTypes.FILE);
                }
                files.add(fileDto);
            }
            return files;
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to list the files : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to list the files : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to create the file identified by the path
     *
     * @param path The path to the file.
     * @param type The type of file.
     * @throws ProjectException
     */
    public void createFile(String path, String type) throws ProjectFactoryException {
        try {
            FileUtil.copyFile(new File(templateDir,
                    type + Constants.TEMPLATE_SUFFIX), new File(projectDir,path));
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method gets the file.
     *
     * @param path The path to the file.
     * @return The string containing the file contents.
     * @throws ProjectException
     */
    public String getFile(String path) throws ProjectFactoryException {
        try {
            File  file = new File(projectDir,path);
            if (file.isDirectory()) {
                log.error("Trying to access directory [" + path + "] as a file");
                throw new ProjectFactoryException
                        ("Trying to access directory [" + path + "] as a file");
            }
            FileInputStream in = new FileInputStream(file);
            StringBuffer result = new StringBuffer();
            byte[] buffer = new byte[1024];
            int bits = 0;
            while ( (bits = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bits));
            }
            return result.toString();
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to get the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the file identified by the project and the path.
     *
     * @param path The path to the file. within the project.
     * @param contents The contents of the file.
     * @throws ProjectException
     */
    public void updateFile(String path, String contents)
            throws ProjectFactoryException {
        try {
            File  file = new File(projectDir,path);
            if (file.isDirectory()) {
                log.error("Trying to access directory [" + path + "] as a file");
                throw new ProjectFactoryException
                        ("Trying to access directory [" + path + "] as a file");
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(contents.getBytes());
            out.flush();
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to update the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method renames the file within the project.
     *
     * @param source The source path to the file.
     * @param target The target path to the file.
     * @throws ProjectException
     */
    public void renameFile(String source, String target)
             throws ProjectFactoryException {
        try {
            FileUtil.copyFile(new File(projectDir,source),
                    new File(projectDir,target));
        } catch (Exception ex) {
            log.error("Failed to rename the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to rename the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the file.
     *
     * @param path The path to the file.
     * @throws ProjectException
     */
    public void removeFile(String path) throws ProjectFactoryException {
        try {
            File  file = new File(projectDir,path);
            if (!file.exists()) {
                throw new ProjectFactoryException("The file [" + path + 
                        "] does not exist");
            } else if(file.isDirectory()) {
                throw new ProjectFactoryException("The file [" + path +
                        "] is a directory and not a file");
            }
            file.delete();
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to update the file : " + ex.getMessage(),ex);
        }
    }

    /**
     * The method to create the project.
     */
    private File createProject(File baseDir, String name, File templateDirectory) throws ProjectFactoryException {
        try {
            File projectDir = new File(baseDir,name);
            if (projectDir.exists()) {
                throw new ProjectFactoryException("The project [" + name + "] already exists.");
            }
            
            projectDir.mkdirs();
            copyRecursive(templateDirectory, projectDir);
            return projectDir;
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the project directory : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the project directory : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method copies the entries recursively.
     *
     * @param sourceDir The source directory to copy from
     * @param targetDir The target directory to copy to.
     * @throws ProjectFactoryException
     */
    private void copyRecursive(File sourceDir, File targetDir) throws ProjectFactoryException {
        try {
            for (File file: sourceDir.listFiles()) {
                File targetSub = new File(targetDir,file.getName());
                if (!targetSub.exists()) {
                    targetSub.mkdirs();
                }
                if (file.isDirectory()) {
                    copyRecursive(file, targetSub);
                    continue;
                }
                FileUtil.copyFile(file, targetSub);
            }
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to copy the directory recursively : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to copy the directory recursively : " + ex.getMessage(),ex);
        }
    }


    // types
    /**
     * This method returns the project types information.
     *
     * @param project The project to get the information for
     * @return
     * @throws ProjectException
     * @throws RemoteException
     */
    public String getProjectTypes() throws ProjectFactoryException {
        try {
            File  file = new File(projectDir,Constants.PROJECT_TYPES);
            if (!file.exists()) {
                return "";
            }
            
        } catch (Exception ex) {
            
        }
        return null;
    }


    /**
     * This method sets the project types.
     *
     * @param project The string containing the project name.
     * @param xml The string containing the xml.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void setProjectTypes(String project, String xml) throws ProjectFactoryException {

    }


    /**
     * This method publishes types.
     *
     * @param project The name of the project
     * @throws ProjectException
     */
    public void publishTypes(String project) throws ProjectFactoryException {
        
    }


}
