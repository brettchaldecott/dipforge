/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  2015 Burntjam
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

package com.rift.coad.change.rdf.objmapping.client.change;

// java import
import java.util.Date;
import java.util.Arrays;

// data type imports
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * The request information.
 *
 * @author brett chaldecott
 */
public class Request extends DataType {

    // private member variables
    private String id;
    private String action;
    private DataType data;
    private DataType[] dependancies;
    private Request[] children;
    private String status = RequestConstants.UNPROCESSED;
    private Date start;
    private Date complete;
    private RequestEvent[] events;



    /**
     * The default constructor
     */
    public Request() {
    }

    
    /**
     * This constructor sets the action information.
     *
     * @param action The action name that is being performed by this request.
     */
    public Request(String action) {
        this.action = action;
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
     * This method returns the data attached to a given request.
     *
     * @return The data attached to a request.
     */
    public DataType getData() {
        return data;
    }


    /**
     * This method sets the data for the request.
     *
     * @param data The request data.
     */
    public void setData(DataType data) {
        this.data = data;
    }


    /**
     * The dependancies.
     *
     * @return The list of dependancies.
     */
    public DataType[] getDependancies() {
        return dependancies;
    }


    /**
     * 
     * @param dependancies
     */
    public void setDependancies(DataType[] dependancies) {
        this.dependancies = dependancies;
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


    /**
     * This is the equals operator.
     *
     * @param obj The equals operator.
     * @return TRUE if the object is equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Request other = (Request) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code value.
     *
     * @return The new hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }




}
