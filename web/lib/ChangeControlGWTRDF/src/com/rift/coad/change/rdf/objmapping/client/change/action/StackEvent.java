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
 * StackEvent.java
 */

// the package path
package com.rift.coad.change.rdf.objmapping.client.change.action;

// the data type information
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.client.base.DataType;

// the semantic information
import java.util.Date;

/**
 * This object represents the log information on a stack entry.
 *
 * @author brett chaldecott
 */
public class StackEvent extends DataType {

    // private member variables
    private String id;
    private Date start;
    private Date complete;
    private ActionTaskDefinition task;
    private String status;
    private String username;
    private String message;

    
    /**
     * The default constructor fofr the stack event.
     */
    public StackEvent() {
    }

    
    /**
     * This constructor sets all the information for the stack information using a generated stack id.
     * 
     * @param start The time the stack event began.
     * @param complete The time the stack event ended.
     * @param task The task the stack event was tied to.
     * @param status The status of a stack event.
     * @param username The username.
     */
    public StackEvent(Date start, Date complete, ActionTaskDefinition task, String status, String username) {
        this.start = start;
        this.complete = complete;
        this.task = task;
        this.status = status;
        this.username = username;
    }





    /**
     * The constructor that sets all the internal value.
     * @param id The id of the
     * @param start The start of the task event.
     * @param complete The time the task event completed.
     * @param task The task information.
     * @param status The status of the task.
     * @param username The user name associated with the task.
     */
    public StackEvent(String id, Date start, Date complete,
            ActionTaskDefinition task, String status, String username) {
        this.id = id;
        this.start = start;
        this.complete = complete;
        this.task = task;
        this.status = status;
        this.username = username;
    }


    /**
     * This method returns the completed time.
     * 
     * @return The object containing the completed time.
     */
    public Date getComplete() {
        return complete;
    }


    /**
     * This method sets the completed time.
     *
     * @param complete The date the event was completed.
     */
    public void setComplete(Date complete) {
        this.complete = complete;
    }


    /**
     * This method returns the event id for the stack event.
     *
     * @return The string containing the id of the stack event.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the stack event.
     *
     * @param id The id of the stack event.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the start of the stack event.
     *
     * @return This method returns the start date.
     */
    public Date getStart() {
        return start;
    }



    /**
     * This method sets the start event.
     *
     * @return This method returns the start date.
     */
    public void setStart(Date start) {
        this.start = start;
    }


    /**
     * The status of this event.
     * 
     * @return The string containing the status of the stack.
     */
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the status of the
     *
     * @param status The string status of this event
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * This method returns the task information.
     *
     * @param The task information.
     */
    public ActionTaskDefinition getTask() {
        return task;
    }


    /**
     * This method sets the task information.
     *
     * @param task The task information.
     */
    public void setTask(ActionTaskDefinition task) {
        this.task = task;
    }


    /**
     * This method returns the username.
     *
     * @return This method returns the username.
     */
    public String getUsername() {
        return username;
    }


    /**
     * This method sets the username.
     *
     * @param username The string containing the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the message.
     * 
     * @return The string containing the message for this stack event.
     */
    public String getMessage() {
        return message;
    }


    /**
     * This method sets the message.
     *
     * @param message The string containing the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * This method returns true if the object that the comparison is on equal to this object.
     *
     * @param obj The object to perform the equals on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StackEvent other = (StackEvent) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash value of this object.
     *
     * @return The integer hash value.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    

}
