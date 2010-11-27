/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * ActionStack.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.client.change.action;

// java imports
import java.util.Arrays;

// type import
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;

// java imports
import com.rift.coad.rdf.objmapping.client.base.DataType;


/**
 * This object represents the stack of
 *
 * @author brett chaldecott
 */
public class ActionStack extends DataType {

    // private member variables
    private String id;
    private String masterRequestId;
    private ActionDefinition action;
    private String requestId;
    private StackEntry stack;
    private StackEvent[] events;

    
    /**
     * The default constructor of the action stack.
     */
    public ActionStack() {
    }


    /**
     * This constructor sets the action reference and the request id.
     *
     * @param action The action that this stack is tied to.
     * @param requestId The id of the request.
     */
    public ActionStack(String masterRequestId,ActionDefinition action, String requestId) {
        this.masterRequestId = masterRequestId;
        this.action = action;
        this.requestId = requestId;
    }
    
    
    /**
     * This constructor 
     *
     * @param action The action definition that this stack is attached to.
     * @param requestId The request id.
     * @param stack The stack
     */
    public ActionStack(String masterRequestId, ActionDefinition action, String requestId, StackEntry stack) {
        this.masterRequestId = masterRequestId;
        this.action = action;
        this.requestId = requestId;
        this.stack = stack;
    }




    /**
     * This constructor sets all the membervariables.
     *
     * @param id The id of the request.
     * @param action The action information.
     * @param requestId The request id.
     * @param stack The stack exception.
     */
    public ActionStack(String id, String masterRequestId,
            ActionDefinition action, String requestId, StackEntry stack) {
        this.id = id;
        this.masterRequestId = masterRequestId;
        this.action = action;
        this.requestId = requestId;
        this.stack = stack;
    }


    /**
     * This method gets the action that this object is bound to.
     *
     * @return The reference to the action this object is bound to.
     */
    public ActionDefinition getAction() {
        return action;
    }


    /**
     * This method sets the action this stack is tied to.
     *
     * @param action The action definition this object is tied to.
     */
    public void setAction(ActionDefinition action) {
        this.action = action;
    }
    

    /**
     * The id that identifies this stack.
     *
     * @return This method returns the string id for this action.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the action information.
     *
     * @param id The string that contains the id information.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the master request information for the action stack.
     *
     * @return The master request id.
     */
    public String getMasterRequestId() {
        return masterRequestId;
    }
    

    /**
     * This method sets the master request for an action stack entry.
     *
     * @param masterRequestId The master request.
     */
    public void setMasterRequestId(String masterRequestId) {
        this.masterRequestId = masterRequestId;
    }




    /**
     * This method retrieves the request id that this action stack is bound to.
     *
     * @return The id of the request this action stack is bound to.
     */
    public String getRequestId() {
        return requestId;
    }


    /**
     * This method retrieves the request id that this action stack is bound to.
     *
     * @return The id of the request this action stack is bound to.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    /**
     * This method returns the stack information.
     *
     * @return The reference to the stack object.
     */
    public StackEntry getStack() {
        return stack;
    }


    /**
     * This method sets the stack information.
     * 
     * @param stack The stack informaton.
     */
    public void setStack(StackEntry stack) {
        this.stack = stack;
    }

    /**
     * This method returns the list of events attached to this stack.
     * 
     * @return The list of events attached to the stack.
     */
    public StackEvent[] getEvents() {
        return events;
    }


    /**
     * This method sets the events for this stack.
     *
     * @param events The events attached to the stack.
     */
    public void setEvents(StackEvent[] events) {
        this.events = events;
    }


    /**
     * This method is responsible for adding a new event.
     *
     * @param event The event to add.
     */
    public void addEvent(StackEvent event) {
        if (this.events == null) {
            this.events = new StackEvent[]{event};
            return;
        }
        StackEvent[] events = new StackEvent[this.events.length + 1];
        for (int index = 0; index < this.events.length; index++) {
            events[index] = this.events[index];
        }
        events[this.events.length] = event;
        this.events = events;
    }

    
    
    /**
     * This method determines if objects have an equal value.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionStack other = (ActionStack) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for this object.
     *
     * @return The integer containing the hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


}
