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
 * ScopeCache.java
 */

package com.rift.coad.script.client.files.php;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.rift.coad.script.client.files.CreateFileFactory.CreateFilePanel;
import com.rift.coad.script.client.scope.PHPScopeCache;
import com.smartgwt.client.util.SC;
import java.util.Date;

/**
 *
 * @author brett chaldecott
 */
public class FileCreateCallbackHandler implements AsyncCallback {

    // private member variables
    private String fileTypeName;
    private String fileScopeName;
    private String fileName;
    private NavigationDataCallback callback;

    /**
     * This constructor sets up the scope cache information.
     * 
     * @param panel The panel to create.
     * @param fileTypeName The file type name.
     * @param fileScopeName The file scope name.
     * @param fileName The file name.
     */
    public FileCreateCallbackHandler(NavigationDataCallback callback, String fileTypeName,
            String fileScopeName, String fileName) {
        this.callback = callback;
        this.fileTypeName = fileTypeName;
        this.fileScopeName = fileScopeName;
        this.fileName = fileName;
    }


    /**
     * THis method handles an asynchronous failure.
     *
     * @param caught The exception that was caught.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to add the entry : " + caught.getMessage());
    }


    /**
     * This method handles the successfull result.
     *
     * @param result The result value.
     */
    public void onSuccess(Object result) {
        if (fileTypeName.equals(Constants.FILE_TYPES[0])) {
            PHPScopeCache.getInstance().addScope(fileScopeName);
            this.callback.addChild(new NavigationTreeNode(fileScopeName,
                    "phpscope:" + fileScopeName, "php", "folder.png",
                                    null, true, "",true, new ScopeMenu(callback)));
        } else {
            if (PHPScopeCache.getInstance().addScope(fileScopeName)) {
                this.callback.addChild(new NavigationTreeNode(fileScopeName,
                    "phpscope:" + fileScopeName, "php", "folder.png",
                                    null, true, "",true, new ScopeMenu(callback)));
            }
            String name = fileName + "." + PHPFileSuffixLookup.getSuffix(fileTypeName);
            RDFScriptInfo scriptInfo = new RDFScriptInfo(fileScopeName,name, new Date());
            FileEditorFactory factory = FileEditorFactory.getInstance(scriptInfo);
            this.callback.addPanel(new NavigationTreeNode(name,
                    "php." + fileScopeName + "." + name, "phpscope:" + fileScopeName, "text-x-generic.png",
                                    factory, true, "",false, new SourceMenu(scriptInfo,factory)),
                                    (ConsolePanel)factory.create());
        }
    }

}
