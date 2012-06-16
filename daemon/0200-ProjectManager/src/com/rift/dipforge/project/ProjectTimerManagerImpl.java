/*
 * ProjectClient: The project client interface..
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
 * ProjectTypeManager.java
 */
package com.rift.dipforge.project;

import com.rift.coad.daemon.timer.Timer;
import com.rift.coad.type.TypeManagerDaemon;
import com.rift.coad.type.dto.ResourceDefinition;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.project.timer.XMLTimerInfoParser;
import com.rift.dipforge.project.type.XMLTypeInfoParser;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implement of the project timer.
 * 
 * @author brett chaldecott
 */
public class ProjectTimerManagerImpl implements ProjectTimerManager {
    
    // private member variables
    private static Logger log = Logger.getLogger(ProjectTimerManagerImpl.class);
    
    /**
     * The default constructor
     */
    public ProjectTimerManagerImpl() {
    }
    
    
    
    
    /**
     * The publish times method. Used to publish times for a project.
     * 
     * @param content The content containing the time information
     * @throws ProjectException
     * @throws RemoteException 
     */
    public void publishTimes(String content) throws ProjectException, RemoteException {
        try {
            XMLTimerInfoParser parser = new XMLTimerInfoParser(
                    content);
            List<XMLTimerInfoParser.Action> actions = parser.getActions();
            Timer daemon = (Timer)
                    ConnectionManager.getInstance().getConnection(Timer.class,
                    "timer/Daemon");
            for (XMLTimerInfoParser.Action action: actions) {
                daemon.register(action.getJndi(), action.getMonth(), 
                        action.getDay(), action.getHour(), action.getMinute(), 
                        parser.getProject() + ":" + action.getScript(), 
                        action.isRecure());
            }
        } catch (Exception ex) {
            log.error("Failed to publish the types file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to publish the types file : " + ex.getMessage(),ex);
        }
    }
    
}
