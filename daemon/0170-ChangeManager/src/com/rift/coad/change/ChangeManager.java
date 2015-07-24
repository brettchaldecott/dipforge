/*
 * ChangeControlClient: The client library for the change control client.
 * Copyright (C) 2009  2015 Burntjam
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
 * ChangeManagerDaemonImpl.java
 */

// package path
package com.rift.coad.change;

// java imports
import com.rift.coad.audit.client.AuditLogger;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * This object is the implementation of the change manager.
 *
 * @author brett chaldecott
 */
public class ChangeManager implements ChangeManagerMBean {

    // private member variables
    private Logger log = Logger.getLogger(ChangeManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            ChangeManager.class);
    /**
     * The default constructor
     */
    public ChangeManager() {
    }


    /**
     * This method returns the string containing the version information.\
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the change manager.
     *
     * @return The string containing the name of the change manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the change manager.
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return "Change Manager";
    }

    
    /**
     * This method is called to add an action to the change manager.
     * 
     * @param action The action to add
     * @param project The project to add the action for.
     * @param type The type of action.
     * @param file The file
     * @param role The role
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void addAction(
            String action,
            String project,
            String type,
            String file,
            String role)
            throws ChangeException, RemoteException {
        try {
            ChangeManagerDaemon daemon = 
                    (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            List<ActionInfo> actions = new ArrayList<ActionInfo>();
            actions.add(new ActionInfo(action, project, type, file,role));
            daemon.addAction(actions);
        } catch (Exception ex) {
            throw new ChangeException("Failed to add the action : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method updates the action.
     * 
     * @param action The action to update.
     * @param project The project the action belongs to.
     * @param type The type of action.
     * @param file The file that the action is implemented in.
     * @param role The role that the caller must have access to.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void updateAction(
            String action, String project, 
            String type, String file,String role)
            throws ChangeException, RemoteException {
        try {
            ChangeManagerDaemon daemon = 
                    (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            List<ActionInfo> actions = new ArrayList<ActionInfo>();
            actions.add(new ActionInfo(action, project, type, file,role));
            daemon.updateAction(actions);
        } catch (Exception ex) {
            throw new ChangeException("Failed to update the action : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method lists the actions.
     * 
     * @param project The project information.
     * @param type The type information.
     * @return The list of actions
     * @throws ChangeException
     * @throws RemoteException 
     */
    public List<String> listActions(
            String project, String type)
            throws ChangeException, RemoteException {
        try {
            ChangeManagerDaemon daemon = 
                    (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            List<ActionInfo> actionInfo = daemon.listActions(project, type);
            List<String> actionList = new ArrayList<String>();
            for (ActionInfo action : actionInfo) {
                actionList.add(action.getAction());
            }
            return actionList;
        } catch (Exception ex) {
            throw new ChangeException("Failed to list the actions : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns the action for the specified information.
     * 
     * @param project The project the action is a part of.
     * @param type The type of action.
     * @param action The action.
     * @return The action information.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public ActionInfo getAction(String project, String type, String action)
            throws ChangeException, RemoteException {
        try {
            ChangeManagerDaemon daemon = 
                    (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            return daemon.getAction(project, type, action);
        } catch (Exception ex) {
            throw new ChangeException("Failed to get the action : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method removes the action.
     * 
     * @param project The project information.
     * @param type The type of project.
     * @param action The action.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void removeAction(
            String project, String type, String action)
            throws ChangeException, RemoteException {
        try {
            ChangeManagerDaemon daemon = 
                    (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            daemon.removeAction(project, type, action);
        } catch (Exception ex) {
            throw new ChangeException("Failed to remove the action : " + 
                    ex.getMessage(),ex);
        }
    }



}
