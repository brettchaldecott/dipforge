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
package com.rift.coad.audit.gwt.console.client.tree;

// coadunation gwt imports
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.rift.coad.audit.gwt.console.client.query.Factory;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.NavigationDataHandler;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.smartgwt.client.util.SC;

/**
 * The audit tree handler
 *
 * @author brett chaldecott
 */
public class AuditTreeHandler implements NavigationDataHandler {

    /**
     * The default constructor for the audit tree handler
     */
    public AuditTreeHandler() {
    }

    /**
     * This method retrieves the child nodes for the audit tree.
     * 
     * @param name The name of node being retrieved.
     * @param callback Operation to call back
     */
    public void getChildNodes(final String name, final NavigationDataCallback callback) {
        if (name.equals("root")) {
            callback.addChildren(new NavigationTreeNode[]{
                        new NavigationTreeNode("Hosts", "hosts", "root", "applications-internet.png", null, true, "", true),
                        new NavigationTreeNode("General Reports", "general-reports", "root", "printer.png", new Factory(), true, "", false)});
        } else if (name.equals("hosts")) {
            getService().getHosts(new AsyncCallback() {
                public void onSuccess(Object result) {
                    String[] hosts = (String[]) result;
                    NavigationTreeNode[] treeNodes = new NavigationTreeNode[hosts.length];
                    for (int index = 0; index < hosts.length; index++) {
                        treeNodes[index] =
                                new NavigationTreeNode(hosts[index], hosts[index], "hosts", "computer.png", null, true, "", true);
                    }
                    callback.addChildren(treeNodes);
                }
                public void onFailure(Throwable caught) {
                    SC.say("Failed to retrieve host nodes : " + caught.getMessage());
                }
            });
        } else {
            getService().getSources(name,new AsyncCallback() {
                public void onSuccess(Object result) {
                    String[] sources = (String[]) result;
                    NavigationTreeNode[] treeNodes = new NavigationTreeNode[sources.length];
                    for (int index = 0; index < sources.length; index++) {
                        treeNodes[index] =
                                new NavigationTreeNode(sources[index], sources[index], "hosts", "applications-system.png", 
                                    new Factory(name,sources[index]), true, "", false);
                    }
                    callback.addChildren(treeNodes);
                }
                public void onFailure(Throwable caught) {
                    SC.say("Failed to retrieve the list of sources for a host : " + caught.getMessage());
                }
            });
        }
    }
    

    /**
     * This method returns the service.
     *
     * @return The reference to the service
     */
    private TreeQueryAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        TreeQueryAsync service = (TreeQueryAsync) GWT.create(TreeQuery.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getHostPageBaseURL() + "tree/treequery";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
