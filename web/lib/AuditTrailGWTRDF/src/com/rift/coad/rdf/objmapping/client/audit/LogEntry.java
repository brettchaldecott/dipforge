/*
 * AuditTrail: The audit trail log object.
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
 * LogEntry.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.audit;

// java imports
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

// coadunation rdf imports
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object represents an audit log entry.
 *
 * @author brett chaldecott
 */
public class LogEntry extends DataType {

    /**
     * This enum represents the status of the log entry object.
     */
    public enum Status { COMPLETE, INFO, FAILURE, CRITICAL_FAILURE };


    // private member variables
    private String id;
    private String hostname;
    private String source;
    private String user;
    private Date time = new Date();
    private String status;
    private String correlationId = "";
    private String externalId = "";
    private String request;
    private List<DataType> associated = new ArrayList<DataType>();

    /**
     * The default constructor.
     */
    public LogEntry() {
    }

    /**
     * This constructor sets all the relevant member variables.
     *
     * @param source The source name of the log entry.
     * @param status The status of the log entry.
     * @param user The name of the user.
     * @param correlationId The correlation id for the log entry.
     * @param externalId The external id for the the log entry.
     * @param request The request.
     */
    public LogEntry(String source, String status, String user,
            String correlationId, String externalId, String request) {
        this.source = source;
        this.user = user;
        this.status = Status.valueOf(status).name();
        this.correlationId = correlationId;
        this.externalId = externalId;
        this.request = request;
    }


    /**
     * The constructor that sets all internal member variables.
     *
     * @param hostname The name of the host that the request came from.
     * @param source The string containing the source object information.
     * @param user The name of the user.
     * @param time The time the event occurred.
     * @param status The status of the event.
     * @param correlationId The correlation id to attache the log entries together.
     * @param externalId The external id.
     * @param request The request.
     */
    public LogEntry(String hostname, String source, String user, Date time,
            String status, String correlationId, String externalId, String request,
            List<DataType> associated) {
        this.hostname = hostname;
        this.source = source;
        this.user = user;
        this.time = time;
        this.status = Status.valueOf(status).name();
        this.correlationId = correlationId;
        this.externalId = externalId;
        this.request = request;
        this.associated = associated;
    }


    /**
     * This method gets the id of the of the log entry.
     *
     * @return The string containing the audit id.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the log entry.
     *
     * @param id The string containing the id of the log entry.
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method returns the correlation id.
     *
     * @return The correlation id.
     */
    public String getCorrelationId() {
        return correlationId;
    }


    /**
     * This method sets the correlation id.
     *
     * @param correlationId The correlation id.
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }


    /**
     * This method sets the external id.
     *
     * @return The external id.
     */
    public String getExternalId() {
        return externalId;
    }


    /**
     * This method sets the external id for the log entry.
     *
     * @param externalId The external id for the log entry.
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * This method returns the name of the host.
     *
     * @return The string containing the host name.
     */
    public String getHostname() {
        return hostname;
    }


    /**
     * This method sets the name of the host
     *
     * @param hostname The string containing the host name.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    
    /**
     * The string containing the request information that is being logged.
     *
     * @return The string containing the log information.
     */
    public String getRequest() {
        return request;
    }


    /**
     * This method sets the request information.
     *
     * @param request The string containing the request information.
     */
    public void setRequest(String request) {
        this.request = request;
    }


    /**
     * The string containing the source information.
     *
     * @return This method returns the string containing the source information.
     */
    public String getSource() {
        return source;
    }


    /**
     * This method sets the source information for this log object.
     *
     * @param source The string containing the source information.
     */
    public void setSource(String source) {
        this.source = source;
    }


    /**
     * This method returns the status information.
     *
     * @return The status information.
     */
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the status of the log entry.
     *
     * @param status The status of the log entry.
     */
    public void setStatus(String status) {
        this.status = Status.valueOf(status).name();
    }


    /**
     * This method retrieves the time for the audit entry.
     *
     * @return The time date object.
     */
    public Date getTime() {
        return time;
    }


    /**
     * This method sets the time for the log event.
     *
     * @param time The object containing the time information
     */
    public void setTime(Date time) {
        this.time = time;
    }

    
    /**
     * This method retrieves the user information for this log entry.
     * 
     * @return The string containing the log entry.
     */
    public String getUser() {
        return user;
    }


    /**
     * This method sets the user information for the log.
     *
     * @param user The string containing the user information.
     */
    public void setUser(String user) {
        this.user = user;
    }


    /**
     * Theis method retrieves the objects associated with this audit trail event.
     *
     * @return The data object associaited with this.
     */
    public List<DataType> getAssociated() {
        return associated;
    }


    /**
     * This method sets the objects associated with this audit entry.
     *
     * @param associated The associated audit entry objects.
     */
    public void setAssociated(List<DataType> associated) {
        this.associated = associated;
    }
    
    
    /**
     * This method performs an equals check on the log entry.
     * 
     * @param obj The object to perform the equals check on.
     * @return The return result.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogEntry other = (LogEntry) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the log entry.
     *
     * @return The hash code for the log entry.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string representation of the entry.
     *
     * @return The string result for this log entry.
     */
    @Override
    public String toString() {
        return hostname + " " + source + " " + user + " " + time.toString() + " " +
                status + " " + correlationId + " " + externalId + " " + request + " " + this.associated.toString();
    }


}
