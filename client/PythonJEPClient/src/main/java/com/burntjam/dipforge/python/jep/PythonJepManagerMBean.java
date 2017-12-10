/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ubuntu
 */
public interface PythonJepManagerMBean extends Remote {
    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;



    /**
     * This method is called to execute the given script path.
     *
     * @param scriptPath The path to the script
     * @return The results of executing the script.
     * @throws PythonJepException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method executes the script identified by the name.")
    @Version(number="1.0")
    @Result(description="The string containing the result of the execution.")
    public String execute(
            @ParamInfo(name="project",description="The project the script is in.")String project,
            @ParamInfo(name="scriptPath",description="The path to the script.")String scriptPath)
            throws PythonJepException, RemoteException;

    
    /**
     * This method is called to execute the script identified by the path supplying the xml
     * parameters.
     *
     * @param scriptPath The path to the script to excute.
     * @param xmlParameters The xml parameters to execute.
     * @return The xml result.
     * @throws PythonJepException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method executes the script identified by the name.")
    @Version(number="1.0")
    @Result(description="The string containing the result of the execution.")
    public String executeMethod(
            @ParamInfo(name="methodId",description="The id of the method to execute.")String methodId,
            @ParamInfo(name="xmlParameters",description="Parameters in xml.")String xmlParameters)
            throws PythonJepException, RemoteException;
    
    
    /**
     * This method returns the stats information for the store.
     *
     * @return The string containing the stats information for this store.
     * @throws PythonJepException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the version of this daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the version")
    public String getStats() throws PythonJepException, RemoteException;
}
