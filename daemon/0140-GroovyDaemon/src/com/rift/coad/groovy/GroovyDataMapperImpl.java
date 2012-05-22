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
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.rdf.types.mapping.ParameterMapping;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentManager;
import com.rift.dipforge.groovy.lib.GroovyExecuter;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This object implements the groovy data mapper functionality.
 * 
 * @author brett chaldecott
 */
public class GroovyDataMapperImpl implements DataMapper {
    
    // class constants
    private final static String EXECUTE_SCRIPT = "execute_script";
    private final static String DEFAULT_EXECUTE_SCRIPT = "ExecuteScript.groovy";
    
    
    // singleton member variables
    private static Logger log = Logger.getLogger(GroovyDaemonImpl.class);

    // private member variables
    private String executeScript;
    
    /**
     * The default constructor of the data mapper.
     * 
     * @exception DataMapperException
     */
    public GroovyDataMapperImpl() throws DataMapperException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(GroovyDataMapperImpl.class);
            executeScript = config.getString(EXECUTE_SCRIPT, 
                    DEFAULT_EXECUTE_SCRIPT);
        } catch (Exception ex) {
            log.error("Failed to initialize the groovy data mapper : " +
                    ex.getMessage(),ex);
            throw new DataMapperException(
                    "Failed to initialize the groovy data mapper : " +
                    ex.getMessage(),ex);
        }
    }

    
    
    
    /**
     * This method is called to execute the groovy data mapper.
     * 
     * @param methodId The method to execute.
     * @param rdfXML The xml containing the parameters to execute the query.
     * @return The result of the execution
     * @throws DataMapperException
     * @throws RemoteException 
     */
    public Object execute(MethodMapping method,  List<Object> parameters) 
            throws DataMapperException, RemoteException {
        try {
            GroovyExecuter executer = GroovyEnvironmentManager.getInstance().getExecuter(
                    new ContextInfo(method.getProject()));
            String[] parameterNames =  {"method","parameters"};
            Object[] values = {method,parameters};
            Object result = executer.executeScript(this.executeScript,
                    parameterNames, values);
            return result;
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage() 
                    + " forcing a retry" ,ex);
            throw new RemoteException
                    ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }
    
}
