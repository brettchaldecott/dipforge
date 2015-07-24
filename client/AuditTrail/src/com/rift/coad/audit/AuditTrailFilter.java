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
 * AuditTrailFilter.java
 */

// package path
package com.rift.coad.audit;

// java imports
import java.util.Date;

/**
 * This object represents the filter possiblities for the audit trail logs.
 *
 * @author brett chaldecott
 */
public class AuditTrailFilter implements java.io.Serializable {
    // private member variables
    private String hostname;
    private String source;
    private String user;
    private Date minTime = new Date();
    private Date maxTime = new Date();
    private String status;
    private String correlationId;
    private String externalId;
    private int maxRows;

    /**
     * The default constructor.
     */
    public AuditTrailFilter() {
    }


    /**
     * This constructor sets all the values.
     *
     * @param hostname The name of the host the audit trail log entry is attached to.
     * @param source The source of the host the audit trail log entry is attached to.
     * @param user The user of the host the audit trail log entry is attached to.
     * @param status The status of the host the audit trail log entry is attached to.
     * @param correlationId The correlation id of the host the audit trail log entry is attached to.
     * @param externalId The external id of the host the audit trail log entry is attached to.
     */
    public AuditTrailFilter(String hostname, String source, String user,
            String status, String correlationId, String externalId, int maxRows) {
        this.hostname = hostname;
        this.source = source;
        this.user = user;
        this.status = status;
        this.correlationId = correlationId;
        this.externalId = externalId;
        this.maxRows = maxRows;
    }


    /**
     * This method returns the correlation id.
     *
     * @return The id for the correlation.
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
     * The external id.
     *
     * @return The external id.
     */
    public String getExternalId() {
        return externalId;
    }


    /**
     * This method sets the external id.
     *
     * @param externalId The external id.
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * This method returns the string containing the hostname.
     * @return The string containing the hostname.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * This method set the host name fo the filter.
     *
     * @param hostname The name of the host to retrieve.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }


    /**
     * Retrieve the max time to filter against.
     *
     * @return The object containing the max time information.
     */
    public Date getMaxTime() {
        return maxTime;
    }


    /**
     * This method sets the max time to filter the information on.
     *
     * @param maxTime The max time to filter the information on.
     */
    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }


    /**
     * Retrieve the min time to filter the information on.
     *
     * @return The date for the minimum requirements.
     */
    public Date getMinTime() {
        return minTime;
    }


    /**
     * This method sets the min time.
     *
     * @param minTime The minimum time for the filter.
     */
    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }


    /**
     * The string contains the source information.
     *
     * @return The source for the audit trail filter.
     */
    public String getSource() {
        return source;
    }


    /**
     * This method sets the source information for the audit trail filter.
     *
     * @param source The string containing the source information.
     */
    public void setSource(String source) {
        this.source = source;
    }


    /**
     * This method sets the status of the audit trail filter object.
     *
     * @return The status of the audit trail filter objects.
     */
    public String getStatus() {
        return status;
    }


    /**
     * The status for the audit trail filter.
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * The user that the filter must be done for.
     *
     * @return The string containing the user information for the filter.
     */
    public String getUser() {
        return user;
    }


    /**
     * This method sets the user information.
     *
     * @param user The string containing the user information.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * This method returns the max row.
     *
     * @return The max number of rows.
     */
    public int getMaxRows() {
        return maxRows;
    }


    /**
     * This method sets the max number of rows.
     *
     * @param maxRows The max number of rows.
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }





}
