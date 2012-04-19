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
import com.rift.coad.audit.client.AuditLogger;
import com.rift.coad.change.rdf.ActionInfoRDF;
import com.rift.coad.rdf.semantic.Resource;
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// change manager imports
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import java.rmi.RemoteException;


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
     * This method adds the action
     * @param action
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void addAction(List<ActionInfo> actions)
            throws ChangeException, RemoteException {
        try {
            String project = "";
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            for (ActionInfo action : actions) {
                session.persist(new ActionInfoRDF(action));
                project = action.getProject();
            }
            auditLog.complete("Added [%d] actions to project [%s]",
                    actions.size(),project);
        } catch (Exception ex) {
            log.error("Failed to add the actions : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the actions : " + 
                    ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method is responsible for updating the existing action.
     * 
     * @param action The action list to add.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void updateAction(List<ActionInfo> actions)
            throws ChangeException, RemoteException {
        try {
            String project = "";
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            for (ActionInfo action : actions) {
                ActionInfoRDF actionInfo = new ActionInfoRDF(action);
                if (session.contains(ActionInfoRDF.class, actionInfo.getId())) {
                        session.remove(
                                session.get(ActionInfoRDF.class, actionInfo.getId()));
                }
                session.persist(new ActionInfoRDF(action));
                project = action.getProject();
            }
            auditLog.complete("Updated [%d] actions to project [%s]",
                    actions.size(),project);
        } catch (Exception ex) {
            log.error("Failed to add the actions : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the actions : " + 
                    ex.getMessage(), ex);
        }
    }

    
    /**
     * This method lists the actions.
     * 
     * @param project The project to which the action list is attached.
     * @param type The type of action.
     * @return The list of actions
     * @throws ChangeException
     * @throws RemoteException 
     */
    public List<ActionInfo> listActions(String project, String type)
            throws ChangeException, RemoteException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            StringBuilder sparqlFilter = new StringBuilder();
            String sep = "";
            if (project != null && project.length() > 0) {
                sparqlFilter.append(" ?Project = \"").
                        append(project).append("\" ");
                sep = "&&";
            }
            if (type != null && type.length() > 0) {
                sparqlFilter.append(sep).append(" ?Type = \"").
                        append(project).append("\" ");
                sep = "&&";
            }
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#ActionInfoRDF> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Project> ?Project . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Type> ?Type . } " +
                    "FILTER (" + sparqlFilter.toString() + ") ORDER BY ?Type").execute();
            List<ActionInfo> result = new ArrayList<ActionInfo>();
            for (SPARQLResultRow entry : entries) {
                System.out.println("Looping through the results");
                result.add(entry.get(ActionInfoRDF.class,0).toAction());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the action: " + ex.getMessage(), ex);
        }
    }

    
    /**
     * The action information.
     *  
     * @param project The project information.
     * @param type The type information.
     * @param action The action information.
     * @return This method retrieves the action information
     * @throws ChangeException
     * @throws RemoteException 
     */
    public ActionInfo getAction(String project, String type, String action)
            throws ChangeException, RemoteException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            StringBuilder sparqlFilter = new StringBuilder();
            String sep = "";
            if (project != null && project.length() > 0) {
                sparqlFilter.append(" ?Project = \"").
                        append(project).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide a project name.");
            }
            if (action != null && action.length() > 0) {
                sparqlFilter.append(sep).append(" ?Type = \"").
                        append(type).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide a type name.");
            }
            if (type != null && type.length() > 0) {
                sparqlFilter.append(sep).append(" ?Action = \"").
                        append(action).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide an action name.");
            }
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#ActionInfoRDF> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Project> ?Project . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Type> ?Type .  " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Action> ?Action . } " +
                    "FILTER (" + sparqlFilter.toString() + ") ORDER BY ?Type").execute();
            if (entries.size() == 0) {
                throw new ChangeException("No entries found matching [" + project +
                        "][" + type + "][" + action + "].");
            }
            return entries.get(0).get(ActionInfoRDF.class,0).toAction();
        } catch (ChangeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to add the action: " + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method removes the action identified by the information.
     * 
     * @param project The project information.
     * @param type The type information.
     * @param action The action information.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void removeAction(String project, String type, String action)
            throws ChangeException, RemoteException {
        try {
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            StringBuilder sparqlFilter = new StringBuilder();
            String sep = "";
            if (project != null && project.length() > 0) {
                sparqlFilter.append(" ?Project = \"").
                        append(project).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide a project name.");
            }
            if (action != null && action.length() > 0) {
                sparqlFilter.append(sep).append(" ?Type = \"").
                        append(type).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide a type name.");
            }
            if (type != null && type.length() > 0) {
                sparqlFilter.append(sep).append(" ?Action = \"").
                        append(action).append("\" ");
                sep = "&&";
            } else {
                throw new ChangeException("Must provide an action name.");
            }
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#ActionInfoRDF> . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Project> ?Project . " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Type> ?Type .  " +
                    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/change.action#Action> ?Action . } " +
                    "FILTER (" + sparqlFilter.toString() + ") ORDER BY ?Type").execute();
            if (entries.size() == 0) {
                throw new ChangeException("No entries found matching [" + project +
                        "][" + type + "][" + action + "].");
            }
            session.remove(entries.get(0).get(ActionInfoRDF.class,0).toAction());
        } catch (ChangeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the action : " + ex.getMessage(), ex);
            throw new ChangeException("Failed to remove the action: " + 
                    ex.getMessage(), ex);
        }
    }

}
