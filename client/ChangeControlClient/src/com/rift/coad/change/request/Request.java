/*
 * ChangeControl: The request data
 * Copyright (C) 2012  2015 Burntjam
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
import java.util.*;

/**
 * The request information.
 *
 * @author brett chaldecott
 */
public class Request implements Serializable {

    // private member variables
    private String id;
    private String project;
    private RequestData data;
    private String action;
    private List<RequestData> dependencies = new ArrayList<RequestData>();
    private List<Request> children = new ArrayList<Request>();
    private String status = RequestConstants.UNPROCESSED;
    private Date start;
    private Date complete;
    private List<RequestEvent> events = new ArrayList<RequestEvent>();
    
    
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
     * This method sets all internal member variables.
     * 
     * @param id
     * @param project
     * @param data
     * @param action
     * @param start
     * @param complete
     * @param events 
     */
    public Request(String id, String project, RequestData data, String action, 
            Date start, Date complete, List<RequestEvent> events) {
        this.id = id;
        this.project = project;
        this.data = data;
        this.action = action;
        this.start = start;
        this.complete = complete;
        this.events = events;
    }
    
    
    public Request(String id, String project, RequestData data, String action, 
            List<RequestData> dependencies, List<Request> children, 
            String status, Date start, Date complete, List<RequestEvent> events) {
        this.id = id;
        this.project = project;
        this.data = data;
        this.action = action;
        this.dependencies = dependencies;
        this.children = children;
        this.status = status;
        this.start = start;
        this.complete = complete;
        this.events = events;
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
    public RequestData getData() {
        return data;
    }

    
    /**
     * This method sets the data.
     * @param data 
     */
    public void setData(RequestData data) {
        this.data = data;
    }
    

    /**
     * This method gets the dependancies.
     * 
     * @return The list of dependancies
     */
    public List<RequestData> getDependencies() {
        return dependencies;
    }

    
    /**
     * This method sets the dependencies.
     * 
     * @param dependancies The list of dependencies.
     */
    public void setDependencies(List<RequestData> dependencies) {
        this.dependencies = dependencies;
    }

    
    /**
     * This method retrieves the child information.
     *
     * @return The request information.
     */
    public List<Request> getChildren() {
        return children;
    }


    /**
     * This method sets the child request information.
     *
     * @param children The list of children.
     */
    public void setChildren(List<Request> children) {
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
    public List<RequestEvent> getEvents() {
        return events;
    }


    /**
     * This method sets the events attached to a request.
     *
     * @param events The array of events.
     */
    public void setEvents(List<RequestEvent> events) {
        this.events = events;
    }


    /**
     * Add an event to the request.
     *
     * @param event The event to add to the list.
     */
    public void addEvent(RequestEvent event) {
        this.events.add(event);
    }

    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Request{" + "id=" + id + ", project=" + project + ", data=" + data + ", action=" + action + ", dependencies=" + dependencies + ", children=" + children + ", status=" + status + ", start=" + start + ", complete=" + complete + ", events=" + events + '}';
    }

    
    
    
}
