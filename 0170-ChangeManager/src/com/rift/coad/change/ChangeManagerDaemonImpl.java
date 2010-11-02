/*
 * ChangeControlManager: The manager for the change events.
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
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// change manager imports
import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.audit.client.rdf.AuditLogger;


/**
 * This is the implementation of the change manager daemon.
 *
 * @author brett chaldecott
 */
public class ChangeManagerDaemonImpl implements ChangeManagerDaemon {

    // private member variables
    private static Logger log = Logger.getLogger(ChangeManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            ChangeManagerDaemonImpl.class);

    /**
     * The default constructor
     */
    public ChangeManagerDaemonImpl() {
    }


    /**
     * This method is responsible for adding an action.
     *
     * @param action The action to add.
     * @throws com.rift.coad.change.ChangeException
     */
    public void addAction(ActionInfo action) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(action);
            auditLog.create("Added a new action [%s]",action.toString()).addData(action).complete();
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the action: " + ex.getMessage(), ex);
        }
    }


    /**
     * This method updates the specified action.
     *
     * @param action The update action.
     * @throws com.rift.coad.change.ChangeException
     */
    public void updateAction(ActionInfo action) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(action);
            auditLog.create("Update an action [%s]",action.toString()).addData(action).complete();
        } catch (Exception ex) {
            log.error("Failed to update the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to update the action: " + ex.getMessage(), ex);
        }
    }
    

    /**
     * This method returns a list of actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.ChangeException
     */
    public List<ActionInfo> listActions() throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/change#ActionInfo> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#Name> ?Name . } " +
                    "ORDER BY ?Name").execute();
            List<ActionInfo> result = new ArrayList<ActionInfo>();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                result.add(entry.get(0).cast(ActionInfo.class));
            }
            return result;
            
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the action: " + ex.getMessage(), ex);
        }
    }


    /**
     * This method removes the action information.
     *
     * @param name The name of the action to remove.
     * @throws com.rift.coad.change.ChangeException
     */
    public void removeAction(String name) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/change#ActionInfo> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#Name> ?Name . " +
                    "FILTER (?Name = ${Name}) } " +
                    "ORDER BY ?Name").setString("Name", name).execute();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                session.remove(entry.get(0).cast(ActionInfo.class));
            }
            auditLog.create("Failed to remove the action [%s]",name).complete();
        } catch (Exception ex) {
            log.error("Failed to remove the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to remove the action: " +
                    ex.getMessage(), ex);
        }
    }


    /**
     * This method lists the action definitions.
     *
     * @param objectId The id of the object to list action definitions actions.
     * @return The list of strings defining the actions bound to the action definitions.
     * @throws com.rift.coad.change.ChangeException
     */
    public List<String> listActionDefinitions(String objectId) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s ?ActionInfo WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/change#ActionDefinition> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#DefinitionActionInfo> ?ActionInfo . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#DefinitionDataTypeId> ?ObjectId . " +
                    "FILTER (?ObjectId = ${ObjectId}) } ").setString("ObjectId", objectId).execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                result.add(entry.get(1).getResource().get(ActionInfo.class).getName());
            }
            return result;

        } catch (Exception ex) {
            log.error("Failed to get the action definition list for object id : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to get the action definition list for object id : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method adds an action definition.
     *
     * @param definition The action definition.
     * @throws com.rift.coad.change.ChangeException
     */
    public void addActionDefinition(ActionDefinition definition) throws ChangeException {
        System.err.println("The beginning of the action definition");
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(definition);
            auditLog.create("Add an action definition [%s]",definition.toString()).
                    addData(definition).complete();
        } catch (Exception ex) {
            log.error("Failed to update the action definition : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to update the action definition : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method updates the action definition
     * @param definition
     * @throws com.rift.coad.change.ChangeException
     */
    public void updateActionDefinition(ActionDefinition definition) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            session.persist(definition);
            auditLog.create("Update an action definition [%s].",definition.toString()).
                    addData(definition).complete();
        } catch (Exception ex) {
            log.error("Failed to update the action definition : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to update the action definition : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the action definition identified by the object id and the action.
     *
     * @param objectId The object id.
     * @param action The action.
     * @return The reference to the retrieved action definition or null.
     * @throws com.rift.coad.change.ChangeException
     */
    public ActionDefinition getActionDefinition(String objectId, String action) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/change#ActionDefinition> . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#DefinitionActionInfo> ?ActionInfo . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/change#DefinitionDataTypeId> ?ObjectId . " +
                    "FILTER (?ObjectId = ${ObjectId}) } ")
                    .setString("ObjectId", objectId).execute();
            // this is a very crude way of handling the finding of objects, and should be
            // handled by a join.
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                ActionDefinition actionDefinition = entry.get(0).cast(ActionDefinition.class);
                if (actionDefinition.getActionInfo().getName().equals(action)) {
                    return actionDefinition;
                }
            }
            return null;

        } catch (Exception ex) {
            log.error("Failed to get the action definition list for object id : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to get the action definition list for object id : " + ex.getMessage(), ex);
        }
    }


    /**
     * Failed to remove the action definition.
     * @param objectId The id of the object
     * @param action
     * @throws com.rift.coad.change.ChangeException
     */
    public void removeActionDefinition(String objectId, String action) throws ChangeException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            ActionDefinition definition = getActionDefinition(objectId, action);
            if (definition != null) {
                session.remove(definition);
            }
            auditLog.create("Remove an attached action objectId [%s] acton [%s].",objectId,action).complete();
        } catch (Exception ex) {
            log.error("Failed to remove the action definition list for object id : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to remove the action definition list for object id : " + ex.getMessage(), ex);
        }
    }

}
