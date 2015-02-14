/*
 * ProjectClient: The project client interface..
 * Copyright (C) 2015  Rift IT Contracting
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
 * LogViewerImpl.java
 */

package com.rift.dipforge.project.logs;

// java imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * The implementation of the log viewer
 * 
 * @author brett chaldecott
 */
public class LogViewerImpl implements LogViewer, BeanRunnable {
    
    // class constants
    private final static String LOGS_DIRECTORY = "logs_directory";
    
    private static Logger log = Logger.getLogger(LogViewerImpl.class);
    
    // private member variables
    private File logsDirectory = null;
    private ThreadStateMonitor state = null;
    
    /**
     * The default constructor
     */
    public LogViewerImpl() throws LogsException {
        try {
            state = new ThreadStateMonitor(60000);
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(LogViewerImpl.class);
            logsDirectory = new File(config.getString(LOGS_DIRECTORY));
            if (!logsDirectory.isDirectory()) {
                log.error("The path [" + logsDirectory.toPath() + 
                        "] is invalid");
                throw new LogsException("The path [" + logsDirectory.toPath() + 
                        "] is invalid");
            }
            LogTailerManager.createInstance(logsDirectory);
        } catch (LogsException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to init the log viewer daemon : " + 
                    ex.getMessage(),ex);
            throw new LogsException
                ("Failed to init the log viewer daemon : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * This method returns a list of log files.
     * 
     * @return The list of log files.
     * @throws RemoteException 
     */
    public List<String> getLogList() throws LogsException {
        return Arrays.asList(logsDirectory.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith("log");
                }
            }
        ));
    }
    
    
    /**
     * This method returns the current tail information from the last line pos
     * and the new end line pos.
     * 
     * @param log The log to retrieve the lines for.
     * @param linePos The line pos.
     * @return The log tail.
     * @throws RemoteException 
     */
    public LogTail tailLog(String log, int linePos) throws LogsException {
        return LogTailerManager.getInstance().getLogTailer(log).tailLog(linePos);
    }

    
    /**
     * This method is called to process the log requests.
     */
    @Override
    public void process() {
        while (!state.isTerminated()) {
            // this method is called to monitor the state
            state.monitor();
        }
        LogTailerManager.getInstance().shutdown();
    }

    
    /**
     * This method is called by the container to shut down the waiting thread.
     */
    @Override
    public void terminate() {
        state.terminate(true);
    }

}
