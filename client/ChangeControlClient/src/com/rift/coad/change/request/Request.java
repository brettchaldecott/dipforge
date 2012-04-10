/*
 * ChangeControl: The request data
 * Copyright (C) 2012  Rift IT Contracting
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
 * Request.java
 */

package com.rift.coad.change.request;

// java import
import java.util.Date;
import java.util.Arrays;

// random guid
import com.rift.coad.lib.common.RandomGuid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The request information.
 *
 * @author brett chaldecott
 */
public class Request implements Serializable {

    // private member variables
    private String id;
    private String project;
    private String dataType;
    private String action;
    private String data;
    private Map<String,String> dependencies = new HashMap<String,String>();
    private Request[] children;
    private String status = RequestConstants.UNPROCESSED;
    private Date start;
    private Date complete;
    private RequestEvent[] events;
    
    
    /**
     * The default constructor
     */
    public Request() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }

    
    /**
     * This constructor sets the action information.
     *
     * @param action The action name that is being performed by this request.
     */
    public Request(String action) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        this.action = action;
    }

    
    /**
     * This id identifies the request id.
     *
     * @return The id of this object.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the request id for this object.
     *
     * @param id The id of this request.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This is the data type information.
     * 
     * @return The type information
     */
    public String getDataType() {
        return dataType;
    }

    
    /**
     * This method sets the type information
     * 
     * @param dataType The data type information.
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    
    /**
     * This method retrieves the project information.
     * 
     * @return The string containing the project information.
     */
    public String getProject() {
        return project;
    }

    
    /**
     * This method set the project information.
     * 
     * @param project The project information.
     */
    public void setProject(String project) {
        this.project = project;
    }
    
    
    /**
     * This method returns the action string.
     *
     * @return The string containing the action.
     */
    public String getAction() {
        return action;
    }


    /**
     * This method sets the action definition.
     *
     * @param action The string containing the action information.
     */
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * This method retrieves the data
     * 
     * @return The data used for this request.
     */
    public String getData() {
        return data;
    }

    
    /**
     * This method sets the data.
     * @param data 
     */
    public void setData(String data) {
        this.data = data;
    }
    

    /**
     * This method gets the dependancies.
     * 
     * @return The list of dependancies
     */
    public Map<String, String> getDependencies() {
        return dependencies;
    }

    
    /**
     * This method sets the dependencies.
     * 
     * @param dependancies The list of dependencies.
     */
    public void setDependencies(Map<String, String> dependencies) {
        this.dependencies = dependencies;
    }

    
    /**
     * This method retrieves the child information.
     *
     * @return The request information.
     */
    public Request[] getChildren() {
        return children;
    }


    /**
     * This method sets the child request information.
     *
     * @param children The list of children.
     */
    public void setChildren(Request[] children) {
        this.children = children;
    }


    
    /**
     * The completed time for this object.
     *
     * @return The date the request was completed on.
     */
    public Date getComplete() {
        return complete;
    }


    /**
     * Set the date the request was completed on.
     *
     * @param complete The date the request was completed on.
     */
    public void setComplete(Date complete) {
        this.complete = complete;
    }


    /**
     * This method returns  the start date.
     *
     * @return The object containing the start date.
     */
    public Date getStart() {
        return start;
    }


    /**
     * This method sets the start date.
     *
     * @param start The object containing the start date.
     */
    public void setStart(Date start) {
        this.start = start;
    }


    /**
     * This method returns the status of the request.
     *
     * @return The current status of the request.
     */
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the status of the request.
     *
     * @param status The status of the request.
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * This method returns the events for the request object.
     *
     * @return The list of events bound to a request.
     */
    public RequestEvent[] getEvents() {
        return events;
    }


    /**
     * This method sets the events attached to a request.
     *
     * @param events The array of events.
     */
    public void setEvents(RequestEvent[] events) {
        this.events = events;
    }


    /**
     * Add an event to the request.
     *
     * @param event The event to add to the list.
     */
    public void addEvent(RequestEvent event) {
        if (this.events == null) {
            this.events = new RequestEvent[1];
            this.events[0] = event;
            return;
        }
        RequestEvent[] events = new RequestEvent[this.events.length + 1];
        for (int index = 0; index < this.events.length; index++) {
            events[index] = this.events[index];
        }
        events[this.events.length] = event;
        this.events = events;
    }

    
    
    
}
