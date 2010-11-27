/*
 * ScriptBroker: The script broker daemon.
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
 * ScriptDistributionDaemonAsync.java
 */


package com.rift.coad.script.broker;

// the java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// rdf script information
import com.rift.coad.script.broker.rdf.RDFScriptInfo;


/**
 * The asynchronous interface for the script distribution daemon.
 *
 * @author brett chaldecott
 */
public interface ScriptDistributionDaemonAsync extends Remote {
    /**
     * This method accepts changes.
     *
     * @param hostname The host name.
     * @param changes The changes.
     * @return The string containing the id of the message.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public String acceptChanges(String hostname, List<RDFScriptInfo> changes) throws ScriptBrokerException, RemoteException;
}
