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
 * ProjectFileManager.java
 */

// package path.
package com.rift.dipforge.project;

// imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * The project file manager.
 *
 * @author brett chaldecott
 */
public interface ProjectFileManager extends Remote {

    // TODO: File Meta Data System
    // Currently the java libraries provide very basic meta data about files.
    // This needs to be extended in future versions.

    /**
     * This method creates a new directory.
     *
     * @param name The name of the project
     * @param directory The directory.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void createDirectory(String project, String directory) throws ProjectException,
            RemoteException;

    
    /**
     * The project name.
     * 
     * @param project The name of the project to remove the directory for.
     * @param directory The name of the project.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void removeDirectory(String project, String directory) throws ProjectException,
            RemoteException;


    /**
     * This method lists the files in a directory.
     *
     * @param name The name of the project
     * @param directory The directory list.
     * @return The list of files.
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFiles(String name, String directory)
            throws ProjectException, RemoteException;


    /**
     * This method lists the files in a directory.
     *
     * @param name The name of the project
     * @return The list of folders in a project
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFolders(String name)
            throws ProjectException, RemoteException;


    /**
     * This method lists the files in a directory.
     *
     * @param name The name of the project
     * @param directoryCommaList The list of directories to perform the search under
     * @return The list of folders in a project
     * @throws ProjectException
     * @throws RemoteException
     */
    public List<FileDTO> listFolders(String name, String directoryCommaList)
            throws ProjectException, RemoteException;


    /**
     * This method is called to create the file identified by the path
     * 
     * @param project The name of the project.
     * @param path The path to the file.
     * @param type The type of file.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void createFile(String project, String path, String type) throws ProjectException,
            RemoteException;


    /**
     * This method is called to create the file identified by the path
     * 
     * @param project The name of the project.
     * @param path The path to the file.
     * @param type The type of file.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void createFile(String project, String path, String type, String context) throws ProjectException,
            RemoteException;


    /**
     * This method gets the file.
     *
     * @param project The project path.
     * @param path The path to the file.
     * @return The string containing the file contents.
     * @throws ProjectException
     * @throws RemoteException
     */
    public String getFile(String project, String path) throws ProjectException,
            RemoteException;


    /**
     * This method updates the file identified by the project and the path.
     *
     * @param project The project to update the file for.
     * @param path The path to the file. within the project.
     * @param contents The contents of the file.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void updateFile(String project, String path, String contents)
            throws ProjectException, RemoteException;


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
             throws ProjectException, RemoteException;

    
    /**
     * This method removes the file.
     *
     * @param project The project path.
     * @param path The path to the file.
     * @throws ProjectException
     * @throws RemoteException
     */
    public void removeFile(String project, String path) throws ProjectException,
            RemoteException;
}
