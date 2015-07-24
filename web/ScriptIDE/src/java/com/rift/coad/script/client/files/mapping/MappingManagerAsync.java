/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  2015 Burntjam
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
 * MappingManagerAsync.java
 */

// package path
package com.rift.coad.script.client.files.mapping;

// async call back.
import com.google.gwt.user.client.rpc.AsyncCallback;

// coadunation imports
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;


/**
 * This interface is the asynchronous version of the mapping manager interface.
 *
 * @author brett chaldecott
 */
public interface MappingManagerAsync {


    /**
     * This method is the asynchronous version of the synchronous get mapping method.
     *
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @param asyncCallback The call back to handle the result.
     */
    public void getMapping(String scope, String name, AsyncCallback asyncCallback);


    /**
     * This method is responsible for updating the data mapping.
     *
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @param mapping The data mapping.
     * @param asyncCallback The result of the call.
     */
    public void updateMapping(String scope, String name, DataMapperMethod mapping, AsyncCallback asyncCallback);
    
}
