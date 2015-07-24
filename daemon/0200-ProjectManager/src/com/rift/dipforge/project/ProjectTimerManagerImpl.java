/*
 * ProjectClient: The project client interface..
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
 * ProjectTypeManager.java
 */
package com.rift.dipforge.project;

import com.rift.coad.daemon.timer.Timer;
import com.rift.coad.daemon.timer.TimerEvent;
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
            TimerEvent[] existingTimer = daemon.listEvents();
            
            // add new enties
            for (XMLTimerInfoParser.Action action: actions) {
                boolean found = false;
                String script = parser.getProject() + ":" + action.getScript();
                for (TimerEvent timer : existingTimer) {
                    String event = (timer.getEvent() != null? timer.getEvent().toString() : null);
                    if (script.equals(event)
                            && timer.getMonth() == action.getMonth()
                            && timer.getDay() == action.getDay()
                            && timer.getHour() == action.getHour()
                            && timer.getMinute() == action.getMinute()
                            && timer.getRecure() == action.isRecure()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    daemon.register(action.getJndi(), action.getMonth(), 
                            action.getDay(), action.getHour(), action.getMinute(), 
                            script,action.isRecure());
                }
            }
            
            // remove
            for (TimerEvent timer : existingTimer) {
                String event = (timer.getEvent() != null? timer.getEvent().toString() : null);
                
                // check if this event has any thing to do with this project
                if (event == null || !event.startsWith(parser.getProject() + ":")) {
                    continue;
                }
                
                // check the new action list
                boolean found = false;
                for (XMLTimerInfoParser.Action action: actions) {
                    String script = parser.getProject() + ":" + action.getScript();
                    if (script.equals(event)
                            && timer.getMonth() == action.getMonth()
                            && timer.getDay() == action.getDay()
                            && timer.getHour() == action.getHour()
                            && timer.getMinute() == action.getMinute()
                            && timer.getRecure() == action.isRecure()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    daemon.deleteEvent(timer.getId());
                }
            }
            
        } catch (Exception ex) {
            log.error("Failed to publish the types file : " + ex.getMessage(),ex);
            throw new ProjectException
                    ("Failed to publish the types file : " + ex.getMessage(),ex);
        }
    }
    
}
