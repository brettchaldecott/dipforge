/*
 * OfficeSuite: The Office Suite for Coadunation OS.
 * Copyright (C) 2010  Rift IT Contracting
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
 * DocumentManager.java
 */

package com.rift.coad.office.client.documents;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 * This object is responsible for managing the documents.
 *
 * @author brett chaldecott
 */
public interface DocumentManager extends RemoteService{

    /**
     * This method returns the base uri for the documents.
     *
     * @return The string containing the base uri for the documents.
     * @throws com.rift.coad.office.client.documents.DocumentManagerException
     */
    public String getHttpBaseUri() throws DocumentManagerException;

    
    /**
     * The file manager.
     *
     * @return The list of scopes.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public List<String> listScopes() throws DocumentManagerException;


    /**
     * This method returns list of files.
     *
     * @param scope The scope to perform the search on.
     * @return The list of files.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public List<String>
            listFiles(String scope) throws DocumentManagerException;


    /**
     * This method is called to create the file.
     *
     * @param type The type of file to create.
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public void createFile(String type, String scope, String name)
            throws DocumentManagerException;


    /**
     * This method is called to delete a file.
     *
     * @param scope The name space of the file.
     * @param name The name of the file.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public void deleteFile(String scope, String name)
            throws DocumentManagerException;

}
