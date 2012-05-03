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

package com.rift.coad.change.rdf;

// java import
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.RequestConstants;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.RequestEvent;
import java.util.Date;
import java.util.Arrays;

// random guid
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import java.io.Serializable;
import java.util.*;

/**
 * The request information.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change.request")
@LocalName("RequestRDF")
public class RequestRDF implements Serializable {

    // private member variables
    private String id;
    private String project;
    private RequestDataRDF data;
    private String action;
    private List<RequestDataRDF> dependencies;
    private List<RequestRDF> children;
    private String status;
    private Date start;
    private Date complete;
    private List<RequestEventRDF> events;
    
    
    /**
     * The default constructor
     */
    public RequestRDF() {
        dependencies = new ArrayList<RequestDataRDF>();
        children = new ArrayList<RequestRDF>();
        events = new ArrayList<RequestEventRDF>();
    }

    /**
     * This constructor sets up all internal variables.
     * 
     * @param id The id of the request.
     * @param project The project the request is attached to.
     * @param data The data.
     * @param action The action on the data
     * @param dependencies The dependencies for the action
     * @param children The list of children
     * @param status The status
     * @param start The start
     * @param complete The complete task.
     * @param events The list of events.
     */
    public RequestRDF(String id, String project, RequestDataRDF data, 
            String action, List<RequestDataRDF> dependencies,
            List<RequestRDF> children, String status, Date start, Date complete,
            List<RequestEventRDF> events) {
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
     * This constructor sets up the internal information using the request object.
     * 
     * @param request The request object
     */
    public RequestRDF(Request request) {
        this.id = request.getId();
        this.project = request.getProject();
        this.data = new RequestDataRDF(request.getData());
        this.action = request.getAction();
        this.dependencies = new ArrayList<RequestDataRDF>();
        for (RequestData requestData : request.getDependencies()) {
            this.dependencies.add(new RequestDataRDF(requestData));
        } 
        this.children = new ArrayList<RequestRDF>();
        if (request.getChildren() != null) {
            for (Request child : request.getChildren()) {
                this.children.add(new RequestRDF(child));
            }
        }
        this.status = request.getStatus();
        this.start = request.getStart();
        this.complete = request.getComplete();
        this.events = new ArrayList<RequestEventRDF>();
        if (request.getEvents() != null) {
            for (RequestEvent event: request.getEvents()) {
                this.events.add(new RequestEventRDF(event));
            }
        }
    }
    
    
    
    /**
     * This id identifies the request id.
     *
     * @return The id of this object.
     */
    @com.rift.coad.rdf.semantic.annotation.Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }


    /**
     * This method sets the request id for this object.
     *
     * @param id The id of this request.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the project information.
     * 
     * @return The string containing the project information.
     */
    @PropertyLocalName("Project")
    public String getProject() {
        return project;
    }

    
    /**
     * This method set the project information.
     * 
     * @param project The project information.
     */
    @PropertyLocalName("Project")
    public void setProject(String project) {
        this.project = project;
    }
    
    
    /**
     * This method returns the action string.
     *
     * @return The string containing the action.
     */
    @PropertyLocalName("Action")
    public String getAction() {
        return action;
    }


    /**
     * This method sets the action definition.
     *
     * @param action The string containing the action information.
     */
    @PropertyLocalName("Action")
    public void setAction(String action) {
        this.action = action;
    }

    
    /**
     * This method retrieves the data
     * 
     * @return The data used for this request.
     */
    @PropertyLocalName("Data")
    public RequestDataRDF getData() {
        return data;
    }

    
    /**
     * This method sets the data.
     * @param data 
     */
    @PropertyLocalName("Data")
    public void setData(RequestDataRDF data) {
        this.data = data;
    }
    

    /**
     * This method gets the dependancies.
     * 
     * @return The list of dependancies
     */
    @PropertyLocalName("Dependencies")
    public List<RequestDataRDF> getDependencies() {
        return dependencies;
    }

    
    /**
     * This method sets the dependencies.
     * 
     * @param dependancies The list of dependencies.
     */
    @PropertyLocalName("Dependencies")
    public void setDependencies(List<RequestDataRDF> dependencies) {
        this.dependencies = dependencies;
    }

    
    /**
     * This method retrieves the child information.
     *
     * @return The request information.
     */
    @PropertyLocalName("Children")
    public List<RequestRDF> getChildren() {
        return children;
    }


    /**
     * This method sets the child request information.
     *
     * @param children The list of children.
     */
    @PropertyLocalName("Children")
    public void setChildren(List<RequestRDF> children) {
        this.children = children;
    }


    
    /**
     * The completed time for this object.
     *
     * @return The date the request was completed on.
     */
    @PropertyLocalName("Completed")
    public Date getComplete() {
        return complete;
    }


    /**
     * Set the date the request was completed on.
     *
     * @param complete The date the request was completed on.
     */
    @PropertyLocalName("Completed")
    public void setComplete(Date complete) {
        this.complete = complete;
    }


    /**
     * This method returns  the start date.
     *
     * @return The object containing the start date.
     */
    @PropertyLocalName("Start")
    public Date getStart() {
        return start;
    }


    /**
     * This method sets the start date.
     *
     * @param start The object containing the start date.
     */
    @PropertyLocalName("Start")
    public void setStart(Date start) {
        this.start = start;
    }


    /**
     * This method returns the status of the request.
     *
     * @return The current status of the request.
     */
    @PropertyLocalName("Status")
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the status of the request.
     *
     * @param status The status of the request.
     */
    @PropertyLocalName("Status")
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * This method returns the events for the request object.
     *
     * @return The list of events bound to a request.
     */
    @PropertyLocalName("Events")
    public List<RequestEventRDF> getEvents() {
        return events;
    }


    /**
     * This method sets the events attached to a request.
     *
     * @param events The array of events.
     */
    @PropertyLocalName("Events")
    public void setEvents(List<RequestEventRDF> events) {
        this.events = events;
    }


    /**
     * Add an event to the request.
     *
     * @param event The event to add to the list.
     */
    public void addEvent(RequestEventRDF event) {
        this.events.add(event);
    }

    
    /**
     * This method returns the request information.
     * 
     * @return The request information
     */
    public Request toRequest() {
        List<RequestData> dependencies = new ArrayList<RequestData>();
        for (RequestDataRDF data : this.dependencies) {
            dependencies.add(data.toRequestData());
        }
        List<Request> children = new ArrayList<Request>();
        for (RequestRDF child: this.children) {
            children.add(child.toRequest());
        }
        List<RequestEvent> events = new ArrayList<RequestEvent>();
        for (RequestEventRDF event: this.events) {
            events.add(event.toRequestEvent());
        }
        return new Request(id, project, this.data.toRequestData(), action, 
            dependencies, children, status, start, complete, events);
    }
    
    
    
}
