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
 * LogViewer.java
 */

// package path
package com.rift.dipforge.project.logs;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the methods to retrieve the log information.
 * 
 * @author brett chaldecott
 */
public interface LogViewer extends Remote {
   
    /**
     * This method returns a list of log files.
     * 
     * @return The list of log files.
     * @throws RemoteException 
     * @throws LogsException
     */
    public List<String> getLogList() throws RemoteException, LogsException;
    
    
    /**
     * This method returns the current tail information from the last line pos
     * and the new end line pos.
     * 
     * @param log The log to retrieve the lines for.
     * @param linePos The line pos.
     * @return The log tail.
     * @throws RemoteException 
     * @throws LogsException
     */
    public LogTail tailLog(String log, int linePos) throws RemoteException, LogsException;
    
}
