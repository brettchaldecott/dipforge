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
 * AuditTreeHandler.java
 */


// package path
package com.rift.coad.audit.gwt.console.server.tree;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import com.rift.coad.audit.AuditTrailServer;
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.audit.gwt.console.client.tree.TreeException;
import com.rift.coad.audit.gwt.console.client.tree.TreeQuery;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The tree query implementation object.
 *
 * @author brett chaldecott
 */
public class TreeQueryImpl extends RemoteServiceServlet implements
        TreeQuery {
    // private member variables
    private static Logger log = Logger.getLogger(TreeQueryImpl.class);

    /**
     * The default constructor.
     */
    public TreeQueryImpl() {

    }

    
    /**
     * This method returns the list of hosts.
     * 
     * @return The array list of hosts.
     * @throws com.rift.coad.audit.gwt.console.client.tree.TreeException
     */
    public String[] getHosts() throws TreeException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "audit/AuditTrailServer");
            return server.listHosts().toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of hosts : " + ex.getMessage(),ex);
            throw new TreeException
                ("Failed to retrieve the list of hosts : " + ex.getMessage());
        }
    }
    
    
    /**
     * This method returns the sources for the given host name.
     * 
     * @param hostname The name of the host to retrieve the sources for.
     * @return The list of sources.
     * @throws com.rift.coad.audit.gwt.console.client.tree.TreeException
     */
    public String[] getSources(String hostname) throws TreeException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "audit/AuditTrailServer");
            // make the sub call and replace underscores with dashes as GWT appears to do
            // the reverse.
            return server.listSources(hostname.replaceAll("_", "-")).toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of sources : " + ex.getMessage(),ex);
            throw new TreeException
                ("Failed to retrieve the list of sources : " + ex.getMessage());
        }
    }



    
}
