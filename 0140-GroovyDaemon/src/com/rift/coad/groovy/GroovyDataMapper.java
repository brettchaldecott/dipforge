/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
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
 * GroovyMappingManagerDaemonImpl.java
 */

// package path
package com.rift.coad.groovy;

// java imports
import java.rmi.RemoteException;

// log4j imports
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperException;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object handles the mapping of calls onto the daemon.
 *
 * @author brett chaldecott
 */
public class GroovyDataMapper implements DataMapper {


    // private import
    private static Logger log = Logger.getLogger(GroovyDataMapper.class);


    /**
     * The default constructor
     */
    public GroovyDataMapper() {
        
    }


    /**
     * This method executes the given method providing the parameters.
     *
     * @param serviceId The service id for the call
     * @param method The method that is being invoked.
     * @param parameters The parameters containing the data to execute this call.
     * @return The result of the call.
     * @throws com.rift.coad.datamapper.DataMapperException
     */
    public DataType execute(String serviceId, String method, DataType[] parameters) throws DataMapperException {
        try {
            GroovyDaemon server = (GroovyDaemon)ConnectionManager.getInstance().
                    getConnection(GroovyDaemon.class, "java:comp/env/bean/groovy/Daemon");
            return server.execute(method, parameters);
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new DataMapperException
                ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }

}
