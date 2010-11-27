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
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;


/**
 * This is the async version of the document management interface
 *
 * @author brett chaldecott
 */
public interface DocumentManagerAsync {

    /**
     * This is the async version of the get http base uri method.
     *
     * @param asyncCallback The call back interface.
     */
    public void getHttpBaseUri(AsyncCallback asyncCallback);

    /**
     * This method is the async version of the list scopes method
     *
     * @param asyncCallback The call back.
     */
    public void listScopes(AsyncCallback asyncCallback);


    /**
     * This method lists the files.
     *
     * @param scope The scope for the files.
     * @param asyncCallback
     */
    public void listFiles(java.lang.String scope, AsyncCallback asyncCallback);

    /**
     * The async create file method.
     *
     * @param type The create file.
     * @param scope The scope method.
     * @param name The name of the scope.
     * @param asyncCallback
     */
    public void createFile(java.lang.String type, java.lang.String scope,
            java.lang.String name, AsyncCallback asyncCallback);


    /**
     * This method is used to delete the specified file.
     *
     * @param scope The scope of the file.
     * @param name The name of the file to delete.
     * @param asyncCallback
     */
    public void deleteFile(java.lang.String scope, java.lang.String name,
            AsyncCallback asyncCallback);
    
}
