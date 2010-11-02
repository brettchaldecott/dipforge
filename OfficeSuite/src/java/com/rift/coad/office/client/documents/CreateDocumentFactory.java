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
 * CreateFileFactory.java
 */
package com.rift.coad.office.client.documents;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This object is responsible for displaying the create
 *
 * @author brett chaldecott
 */
public class CreateDocumentFactory {

    // class constants
    /**
     * This object is responsible for displaying the action information.
     */
    public class CreateFilePanel extends VLayout {

        private CreateFilePanel panel;
        private SelectItem fileType;
        private TextItem nameItem;
        private ComboBoxItem scope;

        /**
         * The default contructor of the create file panel.
         */
        public CreateFilePanel() {
            this.panel = this;

            DynamicForm form = new DynamicForm();
            form.setWidth(500);

            fileType = new SelectItem();
            fileType.setValueMap(Constants.FILE_TYPES);
            fileType.setDefaultValue(Constants.FILE_TYPES[0]);
            fileType.setRequired(true);
            fileType.setTitle("Type");
            fileType.setWidth("*");

            scope = new ComboBoxItem();

            scope.setTitle("Directory");
            scope.setRequired(true);
            scope.setWidth("*");
            scope.setValueMap(ScopeCache.getInstance().getScopes().toArray(new String[0]));

            // the text field
            nameItem = new TextItem();
            nameItem.setTitle("Name");
            nameItem.setRequired(true);
            nameItem.setLength(200);
            nameItem.setWidth("*");
            //nameItem.disable();
            //nameItem.setDefaultValue(types[0]);


            // set the value
            fileType.addChangeHandler(new ChangeHandler() {

                public void onChange(ChangeEvent event) {
                    if (event.getValue().toString().equals(Constants.FILE_TYPES[0])) {
                        //nameItem.disable();
                    } else {
                        //nameItem.enable();
                    }
                }
            });

            ButtonItem createButton = new ButtonItem("Create");
            createButton.setLeft(300);
            createButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                public void onClick(ClickEvent event) {
                    handleCreate();
                }
            });

            form.setFields(new FormItem[]{fileType, scope, nameItem, createButton});
            addMember(form);


        }

        private void handleCreate() {
            try {
                win.hide();
                String fileTypeName = fileType.getValue().toString();
                String fileScopeName = "";
                if (scope.getValue() != null) {
                    fileScopeName = scope.getValue().toString();
                }
                String fileName = null;
                try {
                    fileName = nameItem.getValue().toString();
                } catch (Exception ex) {
                    // ignore
                }
                DocumentManagerConnector.getService().createFile(fileTypeName, fileScopeName, fileName,
                        new DocumentCreateCallbackHandler(callback, fileTypeName, fileScopeName, fileName));
            } catch (Exception ex) {
                SC.say("Failed to instanciate the panel : " + ex.getMessage());
            }
        }
    }

    // private member variables
    private Window win;
    private NavigationDataCallback callback;

    /**
     * The default constructor for the create file factory.
     */
    public CreateDocumentFactory( NavigationDataCallback callback) {
        this.callback = callback;
    }

    /**
     * This method is responsible for creating the file factory.
     * 
     * @return The reference to the file factory canvas
     */
    public Canvas createWindow() {
        if (win != null) {
            return win;
        }
        win = new Window();
        win.setTitle("Mapping");
        win.setKeepInParentRect(true);
        win.setWidth(520);
        win.setHeight(460);
        win.setCanDragReposition(true);
        win.setCanDragResize(true);
        win.setMembersMargin(5);
        win.addItem(new CreateFilePanel());
        win.draw();
        return win;
    }
}
