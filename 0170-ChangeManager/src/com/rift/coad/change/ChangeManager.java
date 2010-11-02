/*
 * ChangeControlClient: The client library for the change control client.
 * Copyright (C) 2009  Rift IT Contracting
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
import com.rift.coad.audit.client.rdf.AuditLogger;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

// log4j import
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;
import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.semantic.config.Basic;
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
     * This method adds an action to the system.
     *
     * @param name The name of the action.
     * @param description The description of the action.
     * @throws com.rift.coad.change.ChangeException
     */
    public void addAction(String name, String description) throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            daemon.addAction(new ActionInfo(name,description));

        } catch (Exception ex) {
            log.error("Failed to add a new action : " + ex.getMessage(),ex);
            throw new ChangeException
                ("Failed to add a new action : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method is called to list actions.
     * 
     * @return
     * @throws com.rift.coad.change.ChangeException
     */
    public List<String> listActions() throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            List<ActionInfo> actionInfoList = daemon.listActions();
            List<String> result = new ArrayList<String>();
            for (int index = 0; index <  actionInfoList.size(); index++) {
                ActionInfo action = actionInfoList.get(index);
                result.add(action.getName());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the actions : " + ex.getMessage(),ex);
            throw new ChangeException
                ("Failed to list the actions : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the specified action entry.
     *
     * @param name The name of the action to remove.
     * @throws com.rift.coad.change.ChangeException
     */
    public void removeAction(String name) throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            daemon.removeAction(name);
        } catch (Exception ex) {
            log.error("Failed to remove the specified action : " + ex.getMessage(),ex);
            throw new ChangeException
                ("Failed to remove the specified action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of named action definition for the given object id.
     *
     * @param objectId The id of the object to return the action definition list for.
     * @return This method returns a list of action definitions.
     * @throws com.rift.coad.change.ChangeException
     */
    public List<String> listActionDefinitions(String objectId) throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            return daemon.listActionDefinitions(objectId);
        } catch (Exception ex) {
            log.error("List the action definitions : " + ex.getMessage(),ex);
            throw new ChangeException
                ("List the action definitions : " + ex.getMessage(),ex);
        }
    }


    /**
     * Failed to add the action definition in xml.
     *
     * @param definition The definition of the action.
     * @throws com.rift.coad.change.ChangeException
     */
    public void addActionDefinitionFromXML(String definition) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(definition);
            auditLog.create("Added an action from an XML definition").complete();
        } catch (Exception ex) {
            log.error("Failed to add the definition from xml : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the definition from xml : " + ex.getMessage(), ex);
        }
    }
    

    /**
     * This method is used to update the action definition.
     *
     * @param definition The XML containing the action definition.
     * @throws com.rift.coad.change.ChangeException
     */
    public void updateActionDefinitionFromXML(String definition) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(definition);
            auditLog.create("Update an action from an XML definition").complete();
        } catch (Exception ex) {
            log.error("Failed to update the definition from xml : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to update the definition from xml : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns an xml definition of the objects.
     * @param objectId The id of the object.
     * @param action The action name.
     * @return The string containing the XML definition.
     * @throws com.rift.coad.change.ChangeException
     */
    public String getActionDefinitionAsXML(String objectId, String action) throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            ActionDefinition definition = daemon.getActionDefinition(objectId,action);
            Session result = Basic.initSessionManager().getSession();
            result.persist(definition);
            return result.dump(RDFFormats.XML_ABBREV);
        } catch (Exception ex) {
            log.error("Failed to get the XML definition for the : " + ex.getMessage(),ex);
            throw new ChangeException
                ("Failed to get the XML definition for the : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the specified action definition.
     *
     * @param objectId The id of the object.
     * @param action The action to remove.
     * @throws com.rift.coad.change.ChangeException
     */
    public void removeActionDefinition(String objectId, String action) throws ChangeException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            daemon.removeActionDefinition(objectId, action);
        } catch (Exception ex) {
            log.error("Failed to remove the XML definition : " + ex.getMessage(),ex);
            throw new ChangeException
                ("Failed to remove the XML definition : " + ex.getMessage(),ex);
        }
    }

}
