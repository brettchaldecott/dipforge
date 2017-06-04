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
 * ProjectBean.java
 */

// package information
package com.rift.dipforge.project.factory;

import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.dipforge.project.Constants;
import com.rift.dipforge.project.FileDTO;
import com.rift.dipforge.project.FileTypes;
import com.rift.dipforge.project.ProjectInfoDTO;
import com.rift.dipforge.project.util.TemplateHelper;
import com.rift.dipforge.project.util.TemplateVariables;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
            ProjectInfoDTO info = getInfo();
            this.name = info.getName();
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
    public ProjectBean(File baseDir, String name, String description, 
            File templateDirectory) throws ProjectFactoryException {
        try {
            this.name = name;
            projectDir = createProject(baseDir, name, templateDirectory);
            manager = new ProjectInfoManager(projectDir);
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProjectBean.class);
            templateDir = config.getString(Constants.TEMPLATE_DIR);
            String username = ThreadsPermissionContainerAccessor.getInstance().
                    getThreadsPermissionContainer().getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setName(name);
            info.setDescription(description);
            info.setAuthor(username);
            info.setCreated(new Date());
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
            dir.mkdirs();
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
                    continue;
                }
                file.delete();
            }
            directory.delete();
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
            File[] filesArray = dir.listFiles();
            Arrays.sort(filesArray);
            for (File file: filesArray) {
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
     * This method lists the files in a directory.
     *
     * @param name The name of the project
     * @param directory The directory list.
     * @return The list of files.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFolders()
            throws ProjectFactoryException {
        try {
            File  dir = projectDir;
            List<FileDTO> files = new ArrayList<FileDTO>();
            File[] filesArray = dir.listFiles();
            Arrays.sort(filesArray);
            for (File file: filesArray) {
                if (!file.isDirectory()) {
                    continue;
                }
                FileDTO fileDto = new FileDTO();
                // at this point the java libraries to not provide
                // a means to get at the created date of a file.
                fileDto.setCreated(new Date(file.lastModified()));
                fileDto.setCreator(null);
                fileDto.setModified(new Date(file.lastModified()));
                fileDto.setModifier(null);
                fileDto.setName(file.getName());
                // construct a relative path based on the directory passed in.
                fileDto.setPath(stripProjectBase(file));
                fileDto.setType(FileTypes.DIRECTORY);
                files.add(fileDto);
                // start recursion on the folder structure
                files.addAll(listFolders(file));
            }
            return files;
        } catch (Exception ex) {
            log.error("Failed to list the files : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to list the files : " + ex.getMessage(),ex);
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
    public List<FileDTO> listFolders(String directoryCommaList)
            throws ProjectFactoryException {
        try {
            List<FileDTO> files = new ArrayList<FileDTO>();
            String[] directories = directoryCommaList.split(",");
            for (String directoryName: directories) {

                File  dir = new File(projectDir,directoryName);
                File[] filesArray = dir.listFiles();
                if (filesArray == null) {
                    continue;
                }
                Arrays.sort(filesArray);
                for (File file: filesArray) {
                    if (!file.isDirectory()) {
                        continue;
                    }
                    FileDTO fileDto = new FileDTO();
                    // at this point the java libraries to not provide
                    // a means to get at the created date of a file.
                    fileDto.setCreated(new Date(file.lastModified()));
                    fileDto.setCreator(null);
                    fileDto.setModified(new Date(file.lastModified()));
                    fileDto.setModifier(null);
                    fileDto.setName(file.getName());
                    // construct a relative path based on the directory passed in.
                    fileDto.setPath(stripProjectBase(file));
                    fileDto.setType(FileTypes.DIRECTORY);
                    files.add(fileDto);
                    // start recursion on the folder structure
                    files.addAll(listFolders(file));
                }
            }
            return files;
        } catch (Exception ex) {
            log.error("Failed to list the files : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to list the files : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the files in a directory.
     *
     * @param folder The parent folder that we are recursing through
     * @return The list of files.
     */
    private List<FileDTO> listFolders(File folder) {
        List<FileDTO> files = new ArrayList<FileDTO>();
        File[] filesArray = folder.listFiles();
        Arrays.sort(filesArray);
        for (File file: filesArray) {
            if (!file.isDirectory()) {
                continue;
            }
            FileDTO fileDto = new FileDTO();
            // at this point the java libraries to not provide
            // a means to get at the created date of a file.
            fileDto.setCreated(new Date(file.lastModified()));
            fileDto.setCreator(null);
            fileDto.setModified(new Date(file.lastModified()));
            fileDto.setModifier(null);
            fileDto.setName(file.getName());
            // construct a relative path based on the directory passed in.
            fileDto.setPath(stripProjectBase(file));
            fileDto.setType(FileTypes.DIRECTORY);
            files.add(fileDto);
            files.addAll(listFolders(file));
        }

        return files;
    }

    private String stripProjectBase(File path) {
        String targetPath = path.getAbsolutePath();
        String projectPath = projectDir.getAbsolutePath();
        return targetPath.substring(projectPath.length());
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
            File targetFile = new File(projectDir,path + "." + type);
            if (targetFile.exists()) {
                log.error("Attempting to create duplicate file [" + path + 
                        "." + type + "]");
                throw new ProjectFactoryException(
                        "Attempting to create duplicate file [" + path + 
                        "." + type + "]");
            }
            
            // process path information
            String directory = path.substring(0,path.lastIndexOf("/"));
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            
            String contents = getTemplate(directory,fileName,type);
            java.io.FileOutputStream out = new java.io.FileOutputStream(targetFile);
            out.write(contents.getBytes());
            out.close();
            
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to create the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to create the file identified by the path
     *
     * @param path The path to the file, this has to be fully formed within a project
     * @param type The type of file.
     * @param context The context for the template file
     * @throws ProjectException
     */
    public void createFile(String path, String type, String context) throws ProjectFactoryException {
        try {

            File targetFile = new File(projectDir,path);
            if (targetFile.exists()) {
                log.error("Attempting to create duplicate file [" + path + "]");
                throw new ProjectFactoryException(
                        "Attempting to create duplicate file [" + path + "]");
            }
            
            // process path information
            String directory = path.substring(0,path.lastIndexOf("/"));

            File dir = new File(projectDir,directory);
            if (!dir.exists()) {
                createDirectory(directory);
            }
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            
            log.info("Attempt to retrieve the file template [" + type + "][" + context + "]");
            String contents = getTemplate(directory,fileName,type,context);
            java.io.FileOutputStream out = new java.io.FileOutputStream(targetFile);
            out.write(contents.getBytes());
            out.close();
            
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
        } catch (ProjectFactoryException ex) {
            throw ex;
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
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
            File sourceFile = new File(projectDir,source);
            sourceFile.renameTo(new File(projectDir,target));
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
            String username = SessionManager.getInstance().
                    getSession().getUser().getName();
            ProjectInfoDTO info = getInfo();
            info.setModifiedBy(username);
            info.setModified(new Date());
            setInfo(info);
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
            copyRecursive(name,templateDirectory, projectDir);
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
    private void copyRecursive(String project, File sourceDir,
            File targetDir) throws ProjectFactoryException {
        try {
            for (File file: sourceDir.listFiles()) {
                File targetSub = new File(targetDir,file.getName());
                if (file.isDirectory()) {
                    targetSub.mkdirs();
                    copyRecursive(project, file, targetSub);
                    continue;
                }
                if (isParsable(file)) {
                    TemplateHelper template = new TemplateHelper(file.getPath());
                    Map<String,String> values = new HashMap<String,String>();

                    // setup the variables
                    values.put(TemplateVariables.AUTHOR, SessionManager.getInstance().
                            getSession().getUser().getName());
                    values.put(TemplateVariables.DATE, new Date().toString());
                    values.put(TemplateVariables.PROJECT, project);
                    
                    template.setParameters(values);
                    String contents = template.parse();
                    java.io.FileOutputStream out = 
                            new java.io.FileOutputStream(targetSub);
                    out.write(contents.getBytes());
                    out.close();
                } else {
                    FileUtil.copyFile(file, targetSub);
                }
            }
        } catch (ProjectFactoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to copy the directory recursively : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to copy the directory recursively : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the template file
     */
    private String getTemplate(String directory, String fileName, String type)
            throws ProjectFactoryException {
            return getTemplate(directory,fileName,type,null);
    }

    /**
     * This method returns the contents of the specified template file.
     *
     * @return The string containing the template file information.
     * @throws ProjectFactoryException
     */
    private String getTemplate(String directory, String fileName, String type, String context)
            throws ProjectFactoryException {
        try {
            File templateFile = null;
            log.info("The context path [" + fileName + "][" + type + "][" + context + "]");
            if (context != null) {
                templateFile = new File(templateDir,context + "/" + type + Constants.TEMPLATE_SUFFIX);
                log.info("The template file : " + templateFile.getPath());
                if (!templateFile.exists()) {
                    templateFile = new File(templateDir,type + Constants.TEMPLATE_SUFFIX);
                    log.info("Use the short path : " + templateFile.getPath());
                }
            } else {
                templateFile = new File(templateDir, type + Constants.TEMPLATE_SUFFIX);
            }
            TemplateHelper template = new TemplateHelper(templateFile.getPath());
            Map<String,String> values = new HashMap<String,String>();
            
            String fullFilename = fileName;
            if (!fullFilename.contains(".")) {
                fullFilename = fileName + "." + type;
            }
            if (fileName.contains(".")) {
                fileName = fileName.substring(0,fileName.lastIndexOf("."));
            }
            
            String scope = ".";
            int index = 0;
            if ( (index = directory.indexOf("/",1)) != -1) {
                scope = directory.substring(index + 1).
                    replaceAll("/",".");
            }

            // setup the variables
            values.put(TemplateVariables.AUTHOR, SessionManager.getInstance().
                    getSession().getUser().getName());
            values.put(TemplateVariables.DATE, new Date().toString());
            values.put(TemplateVariables.FILE_NAME,fullFilename);
            values.put(TemplateVariables.SCOPE,scope);
            values.put(TemplateVariables.PATH,
                    directory + File.separator + fullFilename);
            values.put(TemplateVariables.NAME,fileName);
            values.put(TemplateVariables.PROJECT, this.name);
            template.setParameters(values);
            return template.parse();
        } catch (Exception ex) {
            log.error("Failed to read in the template : " + ex.getMessage(),ex);
            throw new ProjectFactoryException
                    ("Failed to read in the template : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method determines if the file is parsable
     * 
     * @param file The file to perform the check on.
     * @return TRUE if parsable
     */
    private boolean isParsable(File file) {
        for (String suffix : Constants.FILE_SUFFIXES) {
            if (file.getName().endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
}
