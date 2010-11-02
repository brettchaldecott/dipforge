/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * ResourceDataHandler.java
 */

package com.rift.coad.script.client.tree;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.audit.gwt.console.client.query.Factory;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.NavigationDataHandler;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
//import com.rift.coad.type.manager.client.ManageResourcesUtil;
//import com.rift.coad.type.manager.client.type.factory.ResourceCreationFactory;
//import com.rift.coad.type.manager.client.type.factory.ResourceFactory;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.smartgwt.client.util.SC;
import java.util.List;

/**
 * The resource data handler is responsible for getting the resource together.
 *
 * @author brett chaldecott
 */
public class ResourceDataHandler implements NavigationDataHandler {

    // private member variables
    public class HandleResourceList  implements AsyncCallback {

        private String name;
        private NavigationDataCallback callback;

        /**
         * This constructor sets up the information necessary to add the child nodes
         * after the request has returned.
         *
         * @param name The name of the node
         * @param callback The node doing the call back on.
         */
        public HandleResourceList(String name, NavigationDataCallback callback) {
            this.name = name;
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
            List<RDFScriptInfo> list = (List<RDFScriptInfo>)result;
            NavigationTreeNode[] nodes = new NavigationTreeNode[list.size()];
            for (int index = 0; index < list.size(); index++) {
                RDFScriptInfo info = list.get(index);
                com.rift.coad.script.client.files.FileEditorFactory factory =
                        com.rift.coad.script.client.files.FileEditorFactory.getInstance(info);
                NavigationTreeNode node = new NavigationTreeNode(info.getFileName(),
                            info.getScope() + "." + info.getFileName(), this.name,
                            com.rift.coad.script.client.files.Constants.FILE_ICON,
                            factory, true, "",false, new 
                            com.rift.coad.script.client.files.SourceMenu(info,factory));
                nodes[index] = node;
            }
            this.callback.addChildren(nodes);
        }

    }


    // private member variables
    public class PHPHandleResourceList  implements AsyncCallback {

        private String name;
        private NavigationDataCallback callback;

        /**
         * This constructor sets up the information necessary to add the child nodes
         * after the request has returned.
         *
         * @param name The name of the node
         * @param callback The node doing the call back on.
         */
        public PHPHandleResourceList(String name, NavigationDataCallback callback) {
            this.name = name;
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
            List<RDFScriptInfo> list = (List<RDFScriptInfo>)result;
            NavigationTreeNode[] nodes = new NavigationTreeNode[list.size()];
            for (int index = 0; index < list.size(); index++) {
                RDFScriptInfo info = list.get(index);
                com.rift.coad.script.client.files.php.FileEditorFactory factory =
                        com.rift.coad.script.client.files.php.FileEditorFactory.getInstance(info);
                NavigationTreeNode node = new NavigationTreeNode(info.getFileName(),
                            "php." + info.getScope() + "." + info.getFileName(), this.name,
                            com.rift.coad.script.client.files.Constants.FILE_ICON,
                            factory, true, "",false, new
                            com.rift.coad.script.client.files.php.SourceMenu(info,factory));
                nodes[index] = node;
            }
            this.callback.addChildren(nodes);
        }

    }


    // private member variables
    public class GroovyHandleResourceList  implements AsyncCallback {

        private String name;
        private NavigationDataCallback callback;

        /**
         * This constructor sets up the information necessary to add the child nodes
         * after the request has returned.
         *
         * @param name The name of the node
         * @param callback The node doing the call back on.
         */
        public GroovyHandleResourceList(String name, NavigationDataCallback callback) {
            this.name = name;
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
            List<RDFScriptInfo> list = (List<RDFScriptInfo>)result;
            NavigationTreeNode[] nodes = new NavigationTreeNode[list.size()];
            for (int index = 0; index < list.size(); index++) {
                RDFScriptInfo info = list.get(index);
                com.rift.coad.script.client.files.groovy.FileEditorFactory factory =
                        com.rift.coad.script.client.files.groovy.FileEditorFactory.getInstance(info);
                NavigationTreeNode node = new NavigationTreeNode(info.getFileName(),
                            "groovy." + info.getScope() + "." + info.getFileName(), this.name,
                            com.rift.coad.script.client.files.Constants.FILE_ICON,
                            factory, true, "",false, new
                            com.rift.coad.script.client.files.groovy.SourceMenu(info,factory));
                nodes[index] = node;
            }
            this.callback.addChildren(nodes);
        }

    }

    /**
     * The default constructor.
     */
    public ResourceDataHandler() {
    }

    
    /**
     * This method gets the child nodes
     * 
     * @param name
     * @param callback
     */
    public void getChildNodes(String name, NavigationDataCallback callback) {
        try {
            if (name.equals("root")) {
                callback.addChildren(new NavigationTreeNode[] {
                            new NavigationTreeNode("Development", "development", "root", "applications-development.png", null, true, "",true),
                            new NavigationTreeNode("Management", "management", "root", "preferences-system.png", null, true, "",true),
                            new NavigationTreeNode("Audit Trail", "audittrail", "root", "media-record.png",
                                    new Factory("","com.rift.coad.script.broker.ScriptManagerDaemonImpl"), true, "",false)});
            } else if (name.equals("development")) {
                callback.addChildren(new NavigationTreeNode[] {
                            new NavigationTreeNode("Script", "script", "development", "folder-remote.png", null, true, "",true,
                                    new com.rift.coad.script.client.files.ScopeMenu(callback)),
                            new NavigationTreeNode("PHP-Web", "php", "development", "folder-remote.png", null, true, "",true,
                                    new com.rift.coad.script.client.files.php.ScopeMenu(callback)),
                            new NavigationTreeNode("Groovy-Web", "groovy", "development", "folder-remote.png", null, true, "",true,
                                    new com.rift.coad.script.client.files.groovy.ScopeMenu(callback))});
            } else if (name.equals("management")) {
                callback.addChildren(new NavigationTreeNode[] {
                            new NavigationTreeNode("Revisions", "revisions", "management", "applications-system.png", null, true, "",false),
                            new NavigationTreeNode("Distribution", "distribution", "management", "applications-system.png", null, true, "",false)});
            } else if (name.equals("script")) {
                addScriptScopes(name,callback);
            } else if (name.startsWith("scope:")) {
                addScriptFiles(name,callback);
            } else if (name.equals("php")) {
                addPhpScopes(name,callback);
            } else if (name.startsWith("phpscope:")) {
                addPhpFiles(name,callback);
            } else if (name.equals("groovy")) {
                addGroovyScopes(name,callback);
            } else if (name.startsWith("groovyscope:")) {
                addGroovyFiles(name,callback);
            } else {
                //QueryConnector.getService().listTypes(TypeManager.getTypesForGroup(name),new HandleResourceList(callback,name));
            }
        } catch (Exception ex) {
            SC.say("Failed to render tree changes : " + ex.getMessage());
        }
    }


    /**
     * This method adds the tree nodes.
     *
     * @param name The name of the tree node.
     */
    private void addScriptScopes(String name,NavigationDataCallback callback) {
        List<String> scopes = com.rift.coad.script.client.scope.ScopeCache.getInstance().getScopes();
        NavigationTreeNode[] nodes = new NavigationTreeNode[scopes.size()];
        for (int index = 0; index < scopes.size(); index++) {
            String scope  = scopes.get(index);
            nodes[index] = new NavigationTreeNode(scope, "scope:" + scope, name, "folder.png",
                                    null, true, "",true,
                                    new com.rift.coad.script.client.files.ScopeMenu(callback));
        }
        callback.addChildren(nodes);
    }


    /**
     * This method is responsible for starting the process of retrieving the file name
     * information for the given scope.
     * @param name The name that the scope entry falls below.
     * @param callback The call back.
     */
    private void addScriptFiles(String name,NavigationDataCallback callback) {
        String scope = name.substring(name.indexOf(":") + 1).trim();
        com.rift.coad.script.client.files.FileManagerConnector.getService().listFiles(scope, new HandleResourceList(name, callback));
    }


    /**
     * This method adds the tree nodes.
     *
     * @param name The name of the tree node.
     */
    private void addPhpScopes(String name,NavigationDataCallback callback) {
        List<String> scopes = com.rift.coad.script.client.scope.PHPScopeCache.getInstance().getScopes();
        NavigationTreeNode[] nodes = new NavigationTreeNode[scopes.size()];
        for (int index = 0; index < scopes.size(); index++) {
            String scope  = scopes.get(index);
            nodes[index] = new NavigationTreeNode(scope, "phpscope:" + scope, name, "folder.png",
                                    null, true, "",true,
                                    new com.rift.coad.script.client.files.php.ScopeMenu(callback));
        }
        callback.addChildren(nodes);
    }


    /**
     * This method is responsible for starting the process of retrieving the file name
     * information for the given scope.
     * @param name The name that the scope entry falls below.
     * @param callback The call back.
     */
    private void addPhpFiles(String name,NavigationDataCallback callback) {
        String scope = name.substring(name.indexOf(":") + 1).trim();
        com.rift.coad.script.client.files.php.PHPFileManagerConnector.getService().
                listFiles(scope, new PHPHandleResourceList(name, callback));
    }


    /**
     * This method adds the tree nodes.
     *
     * @param name The name of the tree node.
     */
    private void addGroovyScopes(String name,NavigationDataCallback callback) {
        List<String> scopes = com.rift.coad.script.client.scope.GroovyScopeCache.getInstance().getScopes();
        NavigationTreeNode[] nodes = new NavigationTreeNode[scopes.size()];
        for (int index = 0; index < scopes.size(); index++) {
            String scope  = scopes.get(index);
            nodes[index] = new NavigationTreeNode(scope, "groovyscope:" + scope, name, "folder.png",
                                    null, true, "",true,
                                    new com.rift.coad.script.client.files.groovy.ScopeMenu(callback));
        }
        callback.addChildren(nodes);
    }


    /**
     * This method is responsible for starting the process of retrieving the file name
     * information for the given scope.
     * @param name The name that the scope entry falls below.
     * @param callback The call back.
     */
    private void addGroovyFiles(String name,NavigationDataCallback callback) {
        String scope = name.substring(name.indexOf(":") + 1).trim();
        com.rift.coad.script.client.files.groovy.GroovyFileManagerConnector.getService().
                listFiles(scope, new GroovyHandleResourceList(name, callback));
    }
}
