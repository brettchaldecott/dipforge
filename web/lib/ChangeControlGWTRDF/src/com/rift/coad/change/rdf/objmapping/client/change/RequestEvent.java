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
 * RequestEvent.java
 */

package com.rift.coad.change.rdf.objmapping.client.change;

// java imports
import java.util.Date;


//
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object represents a request event.
 *
 * @author brett chaldecott
 */
public class RequestEvent extends DataType {

    // private member variables
    private String id;
    private Date occurrence;
    private String status;
    private String message;

    /**
     * The default constructor
     */
    public RequestEvent() {
        occurrence = new Date();
    }


    /**
     * This constructor sets the values pertinant to a new event.
     *
     * @param status The status of the message.
     * @param message The message describing this event.
     */
    public RequestEvent(String status, String message) {
        occurrence = new Date();
        this.status = status;
        this.message = message;
    }


    /**
     * This constructor sets all private member variables.
     *
     * @param id The id of the request.
     * @param occurrence The date of the occurence of this event.
     * @param status The status that caused the event.
     * @param message The message describing this event.
     */
    public RequestEvent(String id, Date occurrence, String status, String message) {
        this.id = id;
        this.occurrence = occurrence;
        this.status = status;
        this.message = message;
    }


    /**
     * This method returns the id of the request event.
     *
     * @return The id of the request event.
     */
    public String getId() {
        return id;
    }

    
    /**
     * This method sets the id of the request event.
     * 
     * @param id The id of the request event.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the message describing this event.
     *
     * @return The message describing this event.
     */
    public String getMessage() {
        return message;
    }


    /**
     * This method sets the message string in the event.
     *
     * @param message The string containing the message for the event.
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * This method retrieves the occurence of the event.
     *
     * @return The object containing the date of the occurrence of the event.
     */
    public Date getOccurrence() {
        return occurrence;
    }


    /**
     * This method sets the occurence of the event.
     *
     * @param occurrence The object containing the occurence of the event.
     */
    public void setOccurrence(Date occurrence) {
        this.occurrence = occurrence;
    }


    /**
     * The status of the event.
     *
     * @return The string containing the status of the event.
     */
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the message status
     *
     * @param status The status of the event.
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * The equals operator.
     *
     * @param obj The object to perform the comparison on.
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
        final RequestEvent other = (RequestEvent) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code for the object.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * The string representation of the event.
     *
     * @return The string representing this event.
     */
    @Override
    public String toString() {
        return "[" + id + ":" + occurrence.toString() + ":" +  status + ":" +
                message;
    }

    
}
