/*
 * AuditTrailConsole: The audit trail console.
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
 * TreeQuery.java
 */

package com.rift.coad.audit.gwt.console.client.tree;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Methods to retrieves the list of hosts for the audit trail console.
 *
 * @author brett chaldecott
 */
public interface TreeQuery extends RemoteService{
    /**
     * This method returns an array of hosts that have created exceptions.
     *
     * @return This array of host names.
     * @throws com.rift.coad.audit.gwt.console.client.tree.TreeException
     */
    public String[] getHosts() throws TreeException;


    /**
     * This method returns the sources for the given host name.
     *
     * @param hostname The host name to perform the tree query for.
     * @return The list of sources.
     * @throws com.rift.coad.audit.gwt.console.client.tree.TreeException
     */
    public String[] getSources(String hostname) throws TreeException;
}
