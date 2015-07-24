/*
 * Jython: The jython daemon
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * JythonDaemonImpl.java
 */

package com.rift.coad.daemon.jython;

// java imports
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;

// log4j imports
import org.apache.log4j.Logger;

// python imports
import org.python.util.PythonInterpreter;


// coadunation imports
import com.rift.coad.lib.configuration.*;

/**
 * This Daemon integrates Jython into coadunation.
 *
 * @author Glynn Chaldecott
 */
public class JythonDaemonImpl implements JythonDaemon {
    
    // the log classes
    private static Logger log =
            Logger.getLogger(JythonDaemonImpl.class.getName());
    
    // the script location
    private String scriptLocal = "";
    private String tmpLocation = "";
    
    
    /**
     * Creates a new instance of JythonEmbedImpl and configures Jython for use.
     */
    public JythonDaemonImpl() throws Exception {
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance().getConfig(com.rift.coad.daemon.jython.
                    JythonDaemonImpl.class);
            System.setProperty("python.home",
                    coadConfig.getString("python_home"));
            scriptLocal = coadConfig.getString("script_location");
            tmpLocation = coadConfig.getString("coadunation_temp");
        } catch (ConfigurationException ex) {
            log.error("Failed to set jython properties :" +ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to set jython properties :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value.
     *
     * @param name This is the name of the script that a user wishes to run.
     * @param returnValue This is the name of the value a user wishes to have 
     *          returned.
     * @param javaclass This is the type of object a user wants the returned 
     *          value to returned as.
     * @return Returns a value from the script.
     */
    public Object runScript(String name, String returnValue, Class javaclass) 
            throws JythonDaemonException {
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "] does not exist.");
                throw new JythonDaemonException(
                        "The script file [" + name + "] does not exist.");
            }
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            inter.execfile(fis);
            return inter.get(returnValue,javaclass);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve and run script :"+ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to retrieve and run script :" + ex,ex);
        }
    }

    /**
     * This script is called in order to register a new script within 
     * Coadunation.
     *
     * @param script This is a string containing the script that will be 
     *          inserted in a python file.
     * @param name This is what the script will be called as well as what the 
     *          python file will be named.
     */
    public void registerScript(byte[] file, String name) 
            throws JythonDaemonException {
        try {
            File temp = File.createTempFile(name, null,new File(tmpLocation));
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(file);
            fos.close();
            File supFile = new File(scriptLocal,name);
            temp.renameTo(supFile);
        } catch (Exception ex) {
            log.error("Failed to register script :"+ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to register script :" + ex,ex);
        }
    }
    
    
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value. A user can also specify value for 
     * variables within the script.
     *
     * @param name This is the name of the script that a user wishes to run.
     * @param returnValue This is the name of the value a user wishes to have 
     *          returned.
     * @param javaclass This is the type of object a user wants the returned 
     *          value to returned as.
     * @param arguments This is a Map object containing as the key the name of 
     *          the variable and the value for that variable.
     * @return Returns a value from the script.
     */
    public Object runScript(String name, String returnValue, Class javaclass, 
            Map arguments) throws JythonDaemonException {
        
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "] does not exist.");
                throw new JythonDaemonException(
                        "The script file [" + name + "] does not exist.");
            }
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            Iterator key = arguments.keySet().iterator();
            while (key.hasNext()) {
                String temp = (String) key.next();
                Object tempValue = arguments.get(temp);
                inter.set(temp,tempValue);
            }
            inter.execfile(fis);
            return inter.get(returnValue,javaclass);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve and run script :"+ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to retrieve and run script :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list of scripts.
     *
     * @return An array of script names.
     * @throws JythonDaemonException
     */
    public String[] listScripts() throws JythonDaemonException {
        try {
            File scriptDir = new File(scriptLocal);
            File[] files = scriptDir.listFiles();
            List entries = new ArrayList();
            for (int index = 0; index < files.length; index++) {
                File file = files[index];
                if (!file.isFile()) {
                    continue;
                }
                entries.add(file.getName());
            }
            return (String[])entries.toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to list the scripts : " + ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to retrieve and run script :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves the specified script
     *
     * @return A string containing the script value
     * @param name The name of the script to retrieve
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public String getScript(String name) throws JythonDaemonException {
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "] does not exist.");
                throw new JythonDaemonException(
                        "The script file [" + name + "] does not exist.");
            }
            FileInputStream input = new FileInputStream(scriptFile);
            byte[] bytes = new byte[(int)scriptFile.length()];
            input.read(bytes);
            return new String(bytes);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the script :"+ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to retrieve the script :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will remove the script with the given name.
     *
     * @param name The name of the script to remove
     * @throws JythonDaemonException
     */
    public void removeScript(String name) throws JythonDaemonException {
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "]does not exist.");
                throw new JythonDaemonException(
                        "The script file [" + name + "]does not exist.");
            }
            scriptFile.delete();
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve and run script :"+ex.getMessage(),ex);
            throw new JythonDaemonException(
                    "Failed to retrieve and run script :" + ex.getMessage(),ex);
        }
    }
}
