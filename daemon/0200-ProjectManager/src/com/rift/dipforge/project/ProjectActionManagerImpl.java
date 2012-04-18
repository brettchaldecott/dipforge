/*
 * ProjectDaemon: The project daemon implementation
 * Copyright (C) 2012  Rift IT Contracting
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
 * ProjectTypeManagerImpl.java
 */

// package path
package com.rift.dipforge.project;

import com.rift.coad.change.ActionInfo;
import com.rift.coad.change.ChangeManagerDaemon;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.project.action.XMLActionInfoParser;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This object is responsible for managing the project actions.
 * 
 * @author brett chaldecott
 */
public class ProjectActionManagerImpl implements ProjectActionManager {

    // class static variables
    private static Logger log = Logger.getLogger(ProjectActionManagerImpl.class);
    
    /**
     * The default constructor of the project action manager.
     */
    public ProjectActionManagerImpl() {
    }
    
    
    /**
     * The publish actions project manager.
     * 
     * @param content The content to publish.
     * @throws ProjectException
     * @throws RemoteException 
     */
    public void publishActions(String content) throws ProjectException, RemoteException {
        try {
            XMLActionInfoParser parser = new XMLActionInfoParser(
                    content);
            List<ActionInfo> actions = parser.getActions();
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ChangeManagerDaemon.class,
                    "change/ChangeManagerDaemon");
            daemon.addAction(actions);
        } catch (Exception ex) {
            log.error("Failed to publish the actions : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to publish the actions : " + ex.getMessage(),ex);
        }
    }
    
}
