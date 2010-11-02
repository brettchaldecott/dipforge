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
 * ActionsCache.java
 */

package com.rift.coad.change.client.action;

// java imports
import java.util.ArrayList;
import java.util.List;

// gwt and smart gwt imports
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

/**
 * This cache manages the list of actions.
 *
 * @author brett chaldecott
 */
public class ActionsCache implements AsyncCallback {

    // private singleton information.
    private static ActionsCache singleton = null;

    // private member variables
    private List<String> actions = new ArrayList<String>();


    /**
     * The default constructor
     */
    private ActionsCache() {
        ActionManagerConnector.getService().listActions(this);
    }


    /**
     * This method returns a reference to the cache instance.
     *
     * @return The reference to the actions cache.
     */
    public static synchronized ActionsCache getInstance() {
        if (singleton == null) {
            singleton = new ActionsCache();
        }
        return singleton;
    }


    /**
     * This method deals with a failure to retrieve action information.
     *
     * @param caught The exception that cause the issue.
     */
    public void onFailure(Throwable caught) {
        SC.say("Action Error", "Failed to retrieve action information " +
                caught.getMessage());
    }


    /**
     * This method deals with the result.
     *
     * @param result The result of the action query.
     */
    public void onSuccess(Object result) {
        if (result instanceof List) {
            actions = (List<String>)result;
        }
    }

    /**
     * This method returns the list of actions.
     *
     * @return The list of actions
     */
    public List<String> getActions() {
        return actions;
    }

    /**
     * This method is responsible for adding a new action.
     * @param action The string containing the action name.
     */
    public void addAction(String action) {
        if (this.actions.contains(action)) {
            // ignore this
            return;
        }
        this.actions.add(action);
        ActionManagerConnector.getService().addAction(action,this);
    }


    /**
     * This method is responsible for removing the action.
     *
     * @param action The string containing the action to remove.
     */
    public void removeAction(String action) {
        if (!this.actions.contains(action)) {
            // ignore this
            return;
        }
        this.actions.remove(action);
        ActionManagerConnector.getService().removeAction(action,this);
    }
}
