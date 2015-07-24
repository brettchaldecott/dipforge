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
 * FileManager.java
 */

// package path
package com.rift.coad.script.client.files;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * This object is responsible for managing the file editors.
 *
 * @author brett chaldecott
 */
public class FileEditorFactory implements PanelFactory {

    public class EditorPanel extends ConsolePanel {


        private Window messageWindow = null;
        private HTMLPane messagePanel = null;

        /**
         * The default constructor
         */
        public EditorPanel() {
            //setID(scriptInfo.getScope() + "." + scriptInfo.getFileName());
        }
    

        /**
         * This method returns the panel view.
         *
         * @return The reference to the panel.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();

            Window window = new Window();
            window.setTitle(scriptInfo.getScope() + "." + scriptInfo.getFileName());
            window.setWidth100();
            window.setHeight100();
            window.setCanDragReposition(false);
            window.setCanDragResize(true);
            window.setHeaderControls();

            // To work correctly with Firefox the iframe windows must be named
            // individually. Thus it is not possible to use the window src
            // option commented out below. An individual iframe has thus got
            // to be created via the label object and added to the window item list.
            Label label = new Label(
                    "<iframe src =\"/ScriptIDE/FileEditor?scope=" +
                    scriptInfo.getScope() + "&fileName=" + scriptInfo.getFileName() +
                    "\" width=\"100%\" height=\"100%\" scrolling=\"no\" " +
                    "frameborder=0 marginheight=\"0px\" marginwidth=\"0px\" " +
                    "name=\"groovy" + IDGenerator.getId().replace("-", "_") +"\">" +
                    "<p>Your browser does not support iframes.</p>" +
                    "</iframe>");
            label.setWidth100();
            label.setHeight100();
            window.addItem(label);


            //window.setSrc("/ScriptIDE/FileEditor?scope=" +
            //        scriptInfo.getScope() + "&fileName=" + scriptInfo.getFileName());
            layout.addMember(window);

            if (FileSuffixLookup.isFileExecutable(scriptInfo.getFileName())) {
                messageWindow = new Window();
                messageWindow.setTitle("Output");
                messageWindow.setWidth100();
                messageWindow.setHeight("150px");
                messageWindow.setCanDragReposition(false);
                messageWindow.setCanDragResize(true);
                messageWindow.setHeaderControls(HeaderControls.MINIMIZE_BUTTON);

                // set the message label
                //messagePanel = new HTMLPane();
                //messagePanel.setHeight100();
                //messagePanel.setWidth100();
                //messageWindow.addItem(messagePanel);

                layout.addMember(messageWindow);
            }

            return layout;
        }


        /**
         * This method returns the name of this panel
         *
         * @return The string containing the name of this panel
         */
        @Override
        public String getName() {
            return scriptInfo.getFileName();
        }


        /**
         * This method returns the icon path for this console
         *
         * @return The icon path.
         */
        @Override
        public String getIcon() {
            return Constants.FILE_ICON;
        }


        /**
         * This method is called to set the message window value on the canvas.
         *
         * @param value The value to set in the window.
         */
        public void setMessageCanvas(String message) {
            if (messageWindow != null) {
                try {
                    Canvas[] items = messageWindow.getItems();
                    if (items != null) {
                        for (Canvas item : items) {
                            messageWindow.removeItem(item);
                        }
                    }
                    Label label = new Label("<b>Result:</b><br>" + message.replaceAll("\n", "<br>"));
                    label.setWidth100();
                    label.setHeight100();
                    label.setPadding(5);
                    label.setValign(VerticalAlignment.TOP);
                    messageWindow.addItem(label);
                } catch (Exception ex) {
                    SC.say("Failed to set the values : " + ex.getMessage());
                }
            }
        }

    }

    // class singletons
    private static Map<String, FileEditorFactory> mapSingleton =
            new HashMap<String, FileEditorFactory>();

    // private member variables
    private RDFScriptInfo scriptInfo;
    private String id;
    private EditorPanel panel;


    /**
     * This constructor is responsible for
     * @param scriptInfo
     */
    private FileEditorFactory(RDFScriptInfo scriptInfo) {
        this.scriptInfo = scriptInfo;
    }


    /**
     * The reference to the instance singleton.
     * @param scriptInfo The script informaton.
     * @return The reference to the file editor
     */
    public synchronized static FileEditorFactory getInstance(RDFScriptInfo scriptInfo) {
        String id = scriptInfo.getScope() + "." + scriptInfo.getFileName();
        if (!mapSingleton.containsKey(id)) {
            mapSingleton.put(id, new FileEditorFactory(scriptInfo));
        }
        return mapSingleton.get(id);
    }


    /**
     * This method is responsible for creating a new panel.
     *
     * @return The reference to the newly created canvas.
     */
    public Canvas create() {
        if (panel == null) {
            panel = new EditorPanel();
            id = panel.getID();
        } else if (!panel.isDrawn()) {
            panel = new EditorPanel();
            panel.draw();
            id = panel.getID();
        }

        return panel;
    }


    /**
     * This method returns the reference to the panel.
     * 
     * @return The reference to the panel.
     */
    public EditorPanel getPanel() {
        if (panel != null && !panel.isVisible()) {
            panel.setVisible(true);
        }
        return panel;
    }


    /**
     * This method returns the id of the panel instance.
     *
     * @return The reference to the panel id.
     */
    public String getID() {
        return id;
    }


    /**
     * The description of this factory.
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return "File Editor Factory";
    }

}
