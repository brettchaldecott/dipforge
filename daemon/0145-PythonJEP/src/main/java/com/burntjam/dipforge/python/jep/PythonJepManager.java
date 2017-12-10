/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import com.rift.coad.util.connection.ConnectionManager;
import static java.lang.StrictMath.log;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class PythonJepManager implements PythonJepManagerMBean {
    
    // private member variables
    private static Logger log = Logger.getLogger(PythonJepManager.class);

    
    /**
     * The default constructor
     */
    public PythonJepManager() {
    }
    
    
    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() {
        return "1.0";
    } 


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    public String getName() throws RemoteException {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    public String getDescription() {
        return "The java embedded python manager";
    }



    /**
     * This method is called to execute the given script path.
     *
     * @param scriptPath The path to the script
     * @return The results of executing the script.
     * @throws PythonJepException
     */
    public String execute(
            String project,
            String scriptPath)
            throws PythonJepException {
        try {
            PythonJepDaemon server = (PythonJepDaemon)ConnectionManager.getInstance().
                    getConnection(PythonJepDaemon.class, "java:comp/env/bean/python/Daemon");
            return server.execute(project,scriptPath);
        } catch (PythonJepException ex) {
            throw new PythonJepException(ex.getMessage(),ex);
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new PythonJepException
                ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to execute the script identified by the path supplying the xml
     * parameters.
     *
     * @param scriptPath The path to the script to excute.
     * @param xmlParameters The xml parameters to execute.
     * @return The xml result.
     * @throws PythonJepException
     */
    public String executeMethod(
            String methodId,
            String xmlParameters)
            throws PythonJepException {
        return null;
    }
    
    
    /**
     * This method returns the stats information for the store.
     *
     * @return The string containing the stats information for this store.
     * @throws PythonJepException
     */
    public String getStats() throws PythonJepException {
        return "NA";
    }

}
