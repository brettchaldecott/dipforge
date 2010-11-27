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
 * MappingManager.java
 */

// package path
package com.rift.coad.script.client.files.mapping;

import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;

/**
 * This interface defines the mapping management methods.
 *
 * @author brett chaldecott
 */
public interface MappingManager extends RemoteService{

    /**
     * This method returns the mapping information for the given scope and name.
     *
     * @param scope The scope of the mapping.
     * @param name The name of the file for the mapping.
     * @return The reference to the mapping.
     * @throws com.rift.coad.script.client.files.mapping.NoMappingException
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    public DataMapperMethod getMapping(String scope, String name) throws 
            NoMappingException, MappingException;



    /**
     * This method is used to update the mapping.
     *
     * @param scope The scope of the mapping.
     * @param name The name of the file for the mapping.
     * @param mapping The mapping method.
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    public void updateMapping(String scope, String name, DataMapperMethod mapping) throws MappingException;
}
