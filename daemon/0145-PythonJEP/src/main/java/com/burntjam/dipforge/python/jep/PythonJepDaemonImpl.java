/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import com.burntjam.dipforge.python.jep.engine.PythonEngineManager;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class PythonJepDaemonImpl implements PythonJepDaemon {

    // private member variables
    private static Logger log = Logger.getLogger(PythonJepDaemonImpl.class);

    
    public PythonJepDaemonImpl() throws PythonJepException {
        try {
            PythonEngineManager.getInstance();
        } catch (Exception ex) {
            log.error("Failed to init the jep daemon : " + ex.getMessage(),ex);
            throw new PythonJepException("Failed to init the jep daemon : " + ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * This method is called to execute the given script path.
     * 
     * @param project The project the script is in.
     * @param scriptPath The path to the script
     * @return The results of executing the script.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     * @throws java.rmi.RemoteException
     */
    public String execute(String project, String scriptPath)
        throws PythonJepException {
        try {
            String result = PythonEngineManager.getInstance().getWrapper(project).execute(scriptPath).toString();
            log.info("The result string : " + result);
            return result;
        } catch (Throwable ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new PythonJepException
                    ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method executes the
     *
     * @param scriptPath The path to the script.
     * @param parameters The parameter for the request.
     * @return The return result.
     * @throws com.rift.coad.datamapper.DataMapperException
     * @throws java.rmi.RemoteException
     */
    public String executeMethod(String methodId, String input)
            throws PythonJepException {
        return null;
    }

}
