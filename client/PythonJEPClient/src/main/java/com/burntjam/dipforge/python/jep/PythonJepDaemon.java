/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ubuntu
 */
public interface PythonJepDaemon extends Remote {
    
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
        throws PythonJepException, RemoteException;
    

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
            throws PythonJepException, RemoteException;
    
}
