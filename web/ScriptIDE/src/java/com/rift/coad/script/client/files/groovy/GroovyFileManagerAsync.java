/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * GroovyFileManager.java
 */

// package path
package com.rift.coad.script.client.files.groovy;

// async import
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The interface definition for the groovy file manager async version.
 * @author brett chaldecott
 */
public interface GroovyFileManagerAsync {
    /**
     * This method is called to retrieve the list of scopes from
     * script broker.
     *
     * @param callback The object that will handle the result.
     */
    public void listScopes(AsyncCallback callback);


    /**
     * This method returns list of files.
     *
     * @param scope The scope to perform the search on.
     * @param callback The object that will handle the result.
     */
    public void listFiles(String scope, AsyncCallback callback);


    /**
     * This method is called to create a file.
     *
     * @param type The type of file to create.
     * @param scope The scope for the file.
     * @param name The name of the file.
     * @param callback The callback.
     */
    public void createFile(String type, String scope, String name,
            AsyncCallback callback);


    /**
     * This method is called to delete a file.
     *
     * @param scope The name space of the file.
     * @param name The name of the file.
     * @param callback The callback.
     */
    public void deleteFile(String scope, String name,
            AsyncCallback callback);
}
