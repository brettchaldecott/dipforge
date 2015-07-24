/*
 * OfficeSuite: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2010  2015 Burntjam
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
 * TreeDataHandler.java
 */

// package path
package com.rift.coad.office.client.tree;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.NavigationDataHandler;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.rift.coad.office.client.documents.Constants;
import com.rift.coad.office.client.documents.DocumentFileSuffixLookup;
import com.rift.coad.office.client.documents.DocumentManagerConnector;
import com.rift.coad.office.client.documents.DocumentMenu;
import com.rift.coad.office.client.documents.ScopeCache;
import com.rift.coad.office.client.documents.ScopeMenu;
import com.rift.coad.office.client.documents.ckeditor.CKEditorFactory;
import com.rift.coad.office.client.documents.spreadsheet.SpreadSheetEditorFactory;
import com.rift.coad.office.client.email.ViewEmailFactory;
import com.smartgwt.client.util.SC;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the tree data handler.
 *
 * @author brett chaldecott
 */
public class TreeDataHandler implements NavigationDataHandler {

    // private member variables
    public class HandleResourceList implements AsyncCallback {

        private String name;
        private String scope;
        private NavigationDataCallback callback;

        /**
         * This constructor sets up the information necessary to add the child nodes
         * after the request has returned.
         *
         * @param name The name of the node
         * @param scope The scope of the request.
         * @param callback The node doing the call back on.
         */
        public HandleResourceList(String name, String scope, NavigationDataCallback callback) {
            this.name = name;
            this.scope = scope;
            this.callback = callback;
        }

        /**
         * This method is called to handle a failure.
         *
         * @param caught The exception that has been caught.
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to retrieve the file information : " +
                    caught.getMessage());
        }

        /**
         * This method is responsible for dealing with successful calls.
         *
         * @param result The result of this call.
         */
        public void onSuccess(Object result) {
            List<String> list = (List<String>) result;
            List<NavigationTreeNode> nodes = new ArrayList<NavigationTreeNode>();
            for (int index = 0; index < list.size(); index++) {
                String name = list.get(index);
                NavigationTreeNode node = null;
                if (DocumentFileSuffixLookup.getSuffixForName(name).equals(Constants.FILE_SUFFIXES[0])) {
                    CKEditorFactory factory = CKEditorFactory.getInstance(scope, name);
                    node = new NavigationTreeNode(name,
                            scope + "/" + name, "scope:" + scope, "text-html.png",
                            factory, true, "", false, new DocumentMenu(scope, name, factory));
                    nodes.add(node);
                } else if (DocumentFileSuffixLookup.getSuffixForName(name).equals(Constants.FILE_SUFFIXES[1])) {
                    SpreadSheetEditorFactory factory = SpreadSheetEditorFactory.getInstance(scope, name);
                    node = new NavigationTreeNode(name,
                            scope + "/" + name, "scope:" + scope, "x-office-spreadsheet.png",
                            factory, true, "", false, new DocumentMenu(scope, name, factory));
                    nodes.add(node);
                }
            }
            this.callback.addChildren(nodes.toArray(new NavigationTreeNode[0]));
        }
    }

    /**
     * The default constructor of the tree data handler.
     */
    public TreeDataHandler() {
    }

    /**
     * This method is responsible for getting the child nodes.
     * 
     * @param name The list of child nodes.
     * @param callback The call back.
     */
    public void getChildNodes(String name, NavigationDataCallback callback) {
        try {
            if (name.equals("root")) {
                callback.addChildren(new NavigationTreeNode[]{
                            new NavigationTreeNode("Documents", "documents", "root", "folder.png", null, true, "", true, new ScopeMenu(callback)),
                            //new NavigationTreeNode("Calendar", "calendar", "root", "office-calendar.png", null, true, "", false),
                            new NavigationTreeNode("Email", "email", "root", "internet-mail.png", ViewEmailFactory.getInstance(), true, "", false)});
            } else if (name.equals("documents")) {
                addDocumentScopes(name, callback);
            } else if (name.startsWith("scope:")) {
                addDocuments(name, callback);
            }
        } catch (Exception ex) {
            SC.say("Failed to render tree changes : " + ex.getMessage());
        }
    }

    /**
     * This method is called to add the document scopes
     * @param name The name of the section.
     * @param callback The call back.
     */
    private void addDocumentScopes(String name, NavigationDataCallback callback) {
        List<String> scopes = ScopeCache.getInstance().getScopes();
        NavigationTreeNode[] nodes = new NavigationTreeNode[scopes.size()];
        for (int index = 0; index < scopes.size(); index++) {
            String scope = scopes.get(index);
            nodes[index] = new NavigationTreeNode(scope, "scope:" + scope, name, "folder.png",
                    null, true, "", true,
                    new ScopeMenu(callback));
        }
        callback.addChildren(nodes);
    }

    /**
     * This method adds the document.
     *
     * @param name The name of the parent that these nodes will be attached to.
     * @param callback The call back.
     */
    private void addDocuments(String name, NavigationDataCallback callback) {
        String scope = name.substring(name.indexOf(":") + 1).trim();
        DocumentManagerConnector.getService().listFiles(scope,
                new HandleResourceList(name, scope, callback));
    }
}
