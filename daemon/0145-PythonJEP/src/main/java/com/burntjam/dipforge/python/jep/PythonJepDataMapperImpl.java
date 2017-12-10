/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import com.burntjam.dipforge.python.jep.engine.PythonEngineManager;
import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperException;
import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentManager;
import com.rift.dipforge.groovy.lib.GroovyExecuter;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class PythonJepDataMapperImpl implements DataMapper {

    // private member variables
    private static Logger log = Logger.getLogger(PythonJepDataMapperImpl.class);

    
    public PythonJepDataMapperImpl() {
    }

    
    /**
     * Execute the method.
     * @param method
     * @param parameters
     * @return
     * @throws DataMapperException
     * @throws RemoteException 
     */
    @Override
    public Object execute(MethodMapping method, List<Object> parameters) throws DataMapperException, RemoteException {
        try {
            return PythonEngineManager.getInstance().getWrapper(method.getProject()).execute(method, parameters);
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage() 
                    + " forcing a retry" ,ex);
            throw new RemoteException
                    ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }
    
}
