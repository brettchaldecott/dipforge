/*
 * GroovyDaemon: The groovy daemon.
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
 * GroovyTimerImpl.java
 */

package com.rift.coad.groovy;

import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperException;
import java.rmi.RemoteException;

/**
 * This object implements the groovy data mapper functionality.
 * 
 * @author brett chaldecott
 */
public class GroovyDataMapperImpl implements DataMapper {

    
    /**
     * This method is called to execute the groovy data mapper.
     * 
     * @param methodId The method to execute.
     * @param rdfXML The xml containing the parameters to execute the query.
     * @return The result of the execution
     * @throws DataMapperException
     * @throws RemoteException 
     */
    public String execute(String methodId, String rdfXML) throws DataMapperException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
