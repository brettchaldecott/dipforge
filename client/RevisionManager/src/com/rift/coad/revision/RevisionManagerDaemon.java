/*
 * RevisionManager: The revision manager client library
 * Copyright (C) 2009  Rift IT Contracting
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
 * RevisionManagerDaemon.java
 */


// the package path
package com.rift.coad.revision;

// java imports
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import com.rift.coad.revision.rdf.RDFTag;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface discribes the methods to manage the distribution
 *
 * @author brett chaldecott
 */
public interface RevisionManagerDaemon extends Remote {

    /**
     * This method creates a repository.
     *
     * @param name The name of the repository to create.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public void createRepository(String name) throws RevisionManagerException, RemoteException;


    /**
     * This method lists the revisions identified by the repository, branch and path.
     *
     * @param repository The repository.
     * @param branch The branch.
     * @param path The path.
     * @return The list of revision information objects.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFRevisionInfo> listRevisions(String repository, String branch, String path)
            throws RevisionManagerException, RemoteException;


    /**
     * This method returns the contents of the entry identified by the info object.
     *
     * @param info The information object.
     * @return The contents of the entry identified by the revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public String getEntry(RDFRevisionInfo info) throws RevisionManagerException, RemoteException;


    /**
     * This method is responsible for adding the entry.
     *
     * @param repository The repository the directory must be added to.
     * @param branch The branch.
     * @param path The path
     * @return The reference to the newly created version.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public RDFRevisionInfo addDirectory(String repository, String branch, String path)
            throws RevisionManagerException, RemoteException;

    /**
     * This method is called to add the given change.
     *
     * @param repository The repository change.
     * @param branch The branch change.
     * @param path The path.
     * @param contents The contents of the file.
     * @return The revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public RDFRevisionInfo addEntry(String repository, String branch, String path, String contents)
            throws RevisionManagerException, RemoteException;


    /**
     * This method is called to add the given change.
     *
     * @param repository The repository change.
     * @param branch The branch change.
     * @param path The path.
     * @param contents The contents of the file.
     * @return The revision information.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public RDFRevisionInfo updateEntry(String repository, String branch, String path, String contents)
            throws RevisionManagerException, RemoteException;


    /**
     * This method is called to delete an entry.
     *
     * @param repository The repository the entry is in.
     * @param branch The branch.
     * @param path The path.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public void deleteEntry(String repository, String branch, String path)
            throws RevisionManagerException, RemoteException;


    /**
     * This method is responsible for creating a tag.
     * @param tag The tag to create.
     * @param revisions The revision list to create the tag against.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public void createTag(String tag, String description, List<RDFRevisionInfo> revisions)
            throws RevisionManagerException, RemoteException;


    /**
     * This method returns a list of the tags.
     *
     * @return The list of tags.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFTag> listTags() throws RevisionManagerException, RemoteException;


    /**
     * This method is responsible for retrieving the tag information.
     *
     * @param tag The tag
     * @return The revision list that the tag is attached to.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFRevisionInfo> getTagList(String tag)
            throws RevisionManagerException, RemoteException;


    /**
     * This method updates the tag.
     * @param tag The tag information.
     * @param revisions The list of revisions.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public void updateTag(String tag, List<RDFRevisionInfo> revisions)
            throws RevisionManagerException, RemoteException;


    /**
     * This method deletes the specified tag.
     * @param tag The tag to delete.
     * @throws com.rift.coad.revision.RevisionManagerException
     * @throws java.rmi.RemoteException
     */
    public void deleteTag(String tag)
            throws RevisionManagerException, RemoteException;
}
