/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * DataMapperManagerAsync.java
 */


// package path
package com.rift.coad.change.client.action.workflow.piece.call;

// imports
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;


/**
 * This is the async version of the data mapper manager interface.
 *
 * @author brett chaldecott
 */
public interface DataMapperManagerAsync {

    /**
     * The async version of the services interface.
     *
     * @param asyncCallback The list of services.
     */
    public void listServices(AsyncCallback asyncCallback);


    /**
     * This method is the async version of the list jndi for service method. It returns a
     * a list of jndi references.
     *
     * @param service The service to list the jndi references for.
     * @param asyncCallback The call back value.
     */
    public void listJNDIForService(java.lang.String service, AsyncCallback asyncCallback);


    /**
     * The service to retrieve the list of data mapped methods for.
     * @param service The string containing the service name.
     * @param asyncCallback The call back method.
     */
    public void listMethods(java.lang.String service, AsyncCallback asyncCallback);
}
