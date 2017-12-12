/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  2015 Burntjam
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
 * LeviathanLog.java
 */
package com.rift.coad.change.request.action.leviathan;

// java imports
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 * This object is the leviathan log object.
 * 
 * @author brett chaldecott
 */
public class LeviathanLog implements Serializable {
    
    // private member variable
    private String project;
    private String file;
    
    /**
     * The constructor of the leviathan log object.
     * 
     * @param project The project that this log object belongs to.
     * @param file The file that is used by this log object.
     */
    public LeviathanLog(String project, String file) {
        this.project = project;
        this.file = file.substring(0,file.lastIndexOf("."));
        this.file = this.file.replaceAll("/", ".");
    }
    
    
    /**
     * This method wraps the debug call.
     * 
     * @param message The message to call
     */
    public void debug(String message) {
        getLog().debug(message);
    }
    
    
    /**
     * This method wraps the info call
     * 
     * @param message The message
     */
    public void info(String message) {
        getLog().info(message);
    }
    
    
    /**
     * This method wrapps the error call.
     * 
     * @param message The message
     */
    public void error(String message) {
        getLog().error(message);
    }
    
    
    /**
     * This method wrapps the fatal error call.
     * 
     * @param message The message
     */
    public void fatal(String message) {
        getLog().fatal(message);
    }
    
    
    /**
     * This method returns a reference to the log object.
     * 
     * @return The reference to the log object
     */
    private Logger getLog() {
        return Logger.getLogger(LeviathanLog.class.getName() + "." + project + "." + file);
    }
}
