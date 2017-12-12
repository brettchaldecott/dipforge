/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class JepEnv {
    
    // private member variables
    private static Logger log = Logger.getLogger(JepEnv.class);

    // member variables
    private String jepPathJar;
    private String jar;
    private String location;

    public JepEnv() throws PythonWrapperException {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"pip", "show", "jep", "-f"};
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            
            String line = null;
            while ((line = stdInput.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Location:")) {
                    String[] locationElements = line.split(":");
                    location = locationElements[1].trim();
                } else if (line.endsWith("jar")) {
                    jar = line;
                }
            }
            
        } catch (Exception ex) {
           throw new PythonWrapperException("Failed to retrieve the pip and jep information : " + ex.getMessage(),ex);
        }
    }

    public String getJar() {
        return jar;
    }

    public String getLocation() {
        return location;
    }
    
    /**
     * This method return the jar path
     * @return 
     */
    public File getJarPath() {
        return new File(location + File.separator + jar);
    }
}
