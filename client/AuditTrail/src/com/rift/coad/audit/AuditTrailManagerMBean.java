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
 * AuditTrailManagerMBean.java
 */

package com.rift.coad.audit;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.audit.dto.LogEntry;

/**
 * This interface exposes the bean management functionality.
 *
 * @author brett chaldecott
 */
public interface AuditTrailManagerMBean extends Remote {

    /**
     * This method returns the version information.
     *
     * @return The string containing the version information for the audit trail manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of audit trail manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this audit trail manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the this trail manager.
     *
     * @return The string containing the name of the audit trail manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of this implementation of the audit trail manager")
    @Version(number="1.0")
    @Result(description="The string containing the name of the implementation of the audit trail manager")
    public String getName() throws RemoteException;


    /**
     * The description of the audit trail manager.
     *
     * @return This method returns the description of the audit trail manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of this implementation of the audit trail manager")
    @Version(number="1.0")
    @Result(description="The string containing the description of the implementation of the audit trail manager")
    public String getDescription() throws RemoteException;


    /**
     *
     * @return
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the list of hosts that can be found in the logs")
    @Version(number="1.0")
    @Result(description="The list of hosts that can be found in the logs")
    public List<String> listHosts() throws AuditTrailException, RemoteException;


    /**
     * This method lists the sources that can be found in the logs.
     *
     * @return The list of sources.
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the list of sources that can be found in the logs")
    @Version(number="1.0")
    @Result(description="The list of source that can be found in the logs")
    public List<String> listSources(
            @ParamInfo(name="hostname",
            description="The name of the host that the sources will be attached to.")String host)
            throws AuditTrailException, RemoteException;


    /**
     * This method is designed to supply a means to query the audit trail logs.
     *
     * @param hostname The name of the host to store
     * @param source The source of the request.
     * @param user The user the request has been made by.
     * @param status The status of the request.
     * @param correlationId The correlation id for the log entry.
     * @param externalId The external id for the request.
     * @param maxRows The max number of rows that can be returned by the request.
     * @return The list of log entries.
     */
    @MethodInfo(description="This method returns the log entries that match the search criteria")
    @Version(number="1.0")
    @Result(description="The list of log entries that match the search criteria.")
    public List<LogEntry> query(
            @ParamInfo(name="hostname",
            description="The name of the host the logs must come from or blank.")String hostname,
            @ParamInfo(name="source",
            description="The source of the log events or a blank value.")String source,
            @ParamInfo(name="user",
            description="The name of the user to perform the search for.")String user,
            @ParamInfo(name="status",
            description="The status value to perform the search for [COMPLETE, INFO, FAILURE, CRITICAL_FAILURE].")String status,
            @ParamInfo(name="correlationId",
            description="The id that correlates a group of log entries.")String correlationId,
            @ParamInfo(name="externalId",
            description="The id assigned by an external source.")String externalId,
            @ParamInfo(name="maxRows",
            description="The maximum number of results to retrieve.")int maxRows)
            throws AuditTrailException, RemoteException;
}
