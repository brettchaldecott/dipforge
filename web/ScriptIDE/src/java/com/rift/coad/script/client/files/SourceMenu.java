/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * SourceMenu.java
 */


package com.rift.coad.script.client.files;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.gwt.lib.client.console.Console;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * The source menu.
 *
 * @author brett chaldecott
 */
public class SourceMenu extends Menu implements ItemClickHandler {

    
    /**
     * The delete handler
     */
    public class DeleteHandler implements AsyncCallback {
        private FileEditorFactory factory;
        /**
         * The default constructor.
         */
        public DeleteHandler(FileEditorFactory factory) {
            this.factory = factory;
        }

        /**
         * Handle the delete failure.
         *
         * @param caught The cause of the problem
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to delete the entry : " + caught.getMessage());
        }


        /**
         * The on success full call.
         *
         * @param result The result of the call.
         */
        public void onSuccess(Object result) {
            String id = scriptInfo.getScope() + "." + scriptInfo.getFileName();
            try {
                Console.getInstance().removeTreeNode(id);
            } catch (Exception ex) {
                // ignore
            }
            try {
                Console.getInstance().close(factory.getID());
            } catch(Exception ex) {
                // ignore
            }
        }

    }


    // private member variables
    private RDFScriptInfo scriptInfo;
    private FileEditorFactory factory;
    
    
    /**
     * This constructor sets the script information and creates the basic menu.
     *
     * @param scriptInfo The reference to the script information.
     * @param factory The reference to the factory.
     */
    public SourceMenu(RDFScriptInfo scriptInfo, FileEditorFactory factory) {
        this.scriptInfo = scriptInfo;
        this.factory = factory;
        List<MenuItem> items = new ArrayList<MenuItem>();
        
        MenuItem deleteEntry = new MenuItem("Delete");
        items.add(deleteEntry);
        if (FileSuffixLookup.isFileExecutable(scriptInfo.getFileName())) {
            MenuItem runEntry = new MenuItem("Run");
            items.add(runEntry);
            MenuItem dataMappingEntry = new MenuItem("Mapping");
            items.add(dataMappingEntry);
            MenuItem scheduleEntry = new MenuItem("Schedule");
            items.add(scheduleEntry);
        }
        setItems(items.toArray(new MenuItem[0]));

        addItemClickHandler(this);


    }

    
    /**
     * This method is called to handle the delete
     *
     * @param event This method handles the onclick event.
     */
    public void onItemClick(ItemClickEvent event) {
        if (event.getItem().getTitle().equalsIgnoreCase("Delete")) {
            FileManagerConnector.getService().deleteFile(scriptInfo.getScope(),
                    scriptInfo.getFileName(), new DeleteHandler(factory));
        } else if (event.getItem().getTitle().equalsIgnoreCase("Run")) {
            FileManagerConnector.getService().executeFile(scriptInfo.getScope(),
                    scriptInfo.getFileName(), new ExecuteCallbackHandler(factory));
        } else if (event.getItem().getTitle().equalsIgnoreCase("Mapping")) {
            new MappingFactory(scriptInfo,factory).createWindow();
        } else if (event.getItem().getTitle().equalsIgnoreCase("Schedule")) {
            new ScheduleWindow(scriptInfo, factory).createWindow();
        }
    }


}
