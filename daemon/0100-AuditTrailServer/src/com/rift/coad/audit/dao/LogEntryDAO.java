/*
 * AuditTrail: The audit trail log object.
 * Copyright (C) 2011  2015 Burntjam
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
package com.rift.coad.audit.dao;

// java imports
import java.util.Date;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// coadunation rdf imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.types.network.Host;
import com.rift.coad.rdf.types.network.Service;
import com.rift.coad.rdf.types.operation.User;
import java.io.Serializable;

/**
 * This object represents an audit log entry.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/AuditTrail")
@LocalName("LogEntry")
public class LogEntryDAO implements Serializable {

    /**
     * This enum represents the status of the log entry object.
     */
    public enum Status { COMPLETE, INFO, FAILURE, CRITICAL_FAILURE };


    // private member variables
    private String id;
    private Host host;
    private Service service;
    private User user;
    private Date time;
    private String status;
    private String correlationId;
    private String externalId;
    private String request;

    /**
     * The default constructor.
     */
    public LogEntryDAO() {
        
    }


    /**
     * This constructor sets up all internal member variables.
     *
     * @param id The id of the log entry.
     * @param host The host in the log entry.
     * @param service The service.
     * @param user The user.
     * @param time The time.
     * @param status The status.
     * @param correlationId The correlation id.
     * @param externalId The external ID.
     * @param request The request.
     */
    public LogEntryDAO(String id, Host host, Service service, User user,
            Date time, String status, String correlationId, String externalId,
            String request) {
        this.id = id;
        this.host = host;
        this.service = service;
        this.user = user;
        this.time = time;
        this.status = status;
        this.correlationId = correlationId;
        this.externalId = externalId;
        this.request = request;
    }

    
    /**
     * This method gets the id of the of the log entry.
     *
     * @return The string containing the audit id.
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the log entry.
     *
     * @param id The string containing the id of the log entry.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * This method returns the correlation id.
     *
     * @return The correlation id.
     */
    @PropertyLocalName("CorrelationId")
    public String getCorrelationId() {
        return correlationId;
    }


    /**
     * This method sets the correlation id.
     *
     * @param correlationId The correlation id.
     */
    @PropertyLocalName("CorrelationId")
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }


    /**
     * This method sets the external id.
     *
     * @return The external id.
     */
    @PropertyLocalName("ExternalId")
    public String getExternalId() {
        return externalId;
    }


    /**
     * This method sets the external id for the log entry.
     *
     * @param externalId The external id for the log entry.
     */
    @PropertyLocalName("ExternalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * This method returns the name of the host.
     *
     * @return The string containing the host name.
     */
    @PropertyLocalName("Host")
    public Host getHost() {
        return host;
    }


    /**
     * This method sets the name of the host
     *
     * @param hostname The string containing the host name.
     */
    @PropertyLocalName("Host")
    public void setHost(Host host) {
        this.host = host;
    }

    
    /**
     * The string containing the request information that is being logged.
     *
     * @return The string containing the log information.
     */
    @PropertyLocalName("Request")
    public String getRequest() {
        return request;
    }


    /**
     * This method sets the request information.
     *
     * @param request The string containing the request information.
     */
    @PropertyLocalName("Request")
    public void setRequest(String request) {
        this.request = request;
    }


    /**
     * The string containing the source information.
     *
     * @return This method returns the string containing the source information.
     */
    @PropertyLocalName("Service")
    public Service getService() {
        return service;
    }


    /**
     * This method sets the source information for this log object.
     *
     * @param source The string containing the source information.
     */
    @PropertyLocalName("Service")
    public void setService(Service service) {
        this.service = service;
    }


    /**
     * This method returns the status information.
     *
     * @return The status information.
     */
    @PropertyLocalName("Status")
    public String getStatus() {
        return status;
    }


    /**
     * This method sets the status of the log entry.
     *
     * @param status The status of the log entry.
     */
    @PropertyLocalName("Status")
    public void setStatus(String status) {
        this.status = Status.valueOf(status).name();
    }


    /**
     * This method retrieves the time for the audit entry.
     *
     * @return The time date object.
     */
    @PropertyLocalName("Time")
    public Date getTime() {
        return time;
    }


    /**
     * This method sets the time for the log event.
     *
     * @param time The object containing the time information
     */
    @PropertyLocalName("Time")
    public void setTime(Date time) {
        this.time = time;
    }

    
    /**
     * This method retrieves the user information for this log entry.
     * 
     * @return The string containing the log entry.
     */
    @PropertyLocalName("User")
    public User getUser() {
        return user;
    }


    /**
     * This method sets the user information for the log.
     *
     * @param user The string containing the user information.
     */
    @PropertyLocalName("User")
    public void setUser(User user) {
        this.user = user;
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
        final LogEntryDAO other = (LogEntryDAO) obj;
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
        return String.format("%s %s %s %s %s %s %s \"%s\"%n", host,service,user,time.toString(),
                status,correlationId,externalId,request);
    }


    
}
