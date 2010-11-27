/*
 * ActionManagerImpl.java
 *
 * Created on 17 September 2009, 6:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

// package path
package com.rift.coad.change.server.action;

// java path
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;
import java.util.ArrayList;
import java.util.List;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.change.ChangeManagerDaemon;
import com.rift.coad.change.client.action.ActionException;
import com.rift.coad.change.client.action.ActionManager;
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This method returns a list of the actions.
 *
 * @author brett chaldecott
 */
public class ActionManagerImpl extends RemoteServiceServlet implements
        ActionManager {

    private Logger log = Logger.getLogger(ActionManagerImpl.class);

    /**
     * The default constructor
     */
    public ActionManagerImpl() {
    }


    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public List<String> listActions() throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");
            List<ActionInfo> actionInfoList = daemon.listActions();
            List<String> result = new ArrayList<String>();
            for (int index = 0; index <  actionInfoList.size(); index++) {
                ActionInfo action = actionInfoList.get(index);
                result.add(action.getName());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the actions : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to list the action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds the specified action.
     * 
     * @param action The string containing the action to add.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void addAction(String action) throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");
            daemon.addAction(new ActionInfo(action,action));
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to add the action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the specified action.
     *
     * @param action The action to remove.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void removeAction(String action) throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");
            daemon.removeAction(action);
        } catch (Exception ex) {
            log.error("Failed to remove the action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to remove the action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method gets the action definition.
     *
     * @param objectId The object id.
     * @param action The action.
     * @return The reference to the action definition.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public ActionDefinition getActionDefinition(String objectId, String action) throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");
            return (ActionDefinition)RDFCopy.copyToClient(daemon.getActionDefinition(objectId,action));
        } catch (Exception ex) {
            log.error("Failed to get the action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to get the action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds the action definition.
     *
     * @param definition The definition to add.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void saveActionDefinition(ActionDefinition definition) throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "change/ChangeManagerDaemon");
            if (daemon.getActionDefinition(definition.getDataTypeId(),
                    definition.getActionInfo().getName()) != null) {
                daemon.updateActionDefinition((com.rift.coad.change.rdf.objmapping.change.ActionDefinition)
                    RDFCopy.copyFromClient(definition));
            } else {
                daemon.addActionDefinition((com.rift.coad.change.rdf.objmapping.change.ActionDefinition)
                    RDFCopy.copyFromClient(definition));
            }
        } catch (Exception ex) {
            log.error("Failed to save the action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to save the action : " + ex.getMessage(),ex);
        }
    }


    
}
