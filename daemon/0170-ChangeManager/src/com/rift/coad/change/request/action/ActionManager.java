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
 * ActionManager.java
 */


// package path
package com.rift.coad.change.request.action;

// java imports
import com.rift.coad.change.rdf.RequestRDF;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import java.rmi.RemoteException;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// package path
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The implementation of the Action Manager MBean
 *
 * @author brett chaldecott
 */
public class ActionManager implements ActionManagerMBean {

    // class singletons
    private static Logger log = Logger.getLogger(ActionManager.class);

    /**
     * The default constructor
     */
    public ActionManager() {
    }


    /**
     * This method returns the version information.
     *
     * @return The version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the action manager.
     *
     * @return The string containing the name of the action manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the action manager.
     *
     * @return The string containing the description of the action manager.
     */
    public String getDescription() {
        return "The action manager MBean.";
    }


    /**
     * This method is responsible for extracting from the XML the action to init.
     * @param id The id of the action to initialize.
     * @param xml The xml to extract the action from.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void initActionFromXML(String masterRequestId, String id, String xml) throws ActionException {
        try {
            Session session = XMLSemanticUtil.getSession();
            session.persist(xml);
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            daemon.createActionInstance(masterRequestId, 
                    session.get(RequestRDF.class, id).toRequest());
        } catch (Exception ex) {
            log.error("Failed to get an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to get an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the action identified by the id.
     *
     * @param id The id of the action to remove.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void removeAction(String id) throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            daemon.removeActionInstance(id);
        } catch (Exception ex) {
            log.error("Failed to remove an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to remove an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the actions instances that are currently running.
     *
     * @return The id of the action.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listActions() throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            return daemon.listActionInstances();
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of actions : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to retrieve a list of actions : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the request id.
     * 
     * @return The list of requests that are currently executing.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listRequestId() throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            return daemon.listRequestId();
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
        }
    }

}
