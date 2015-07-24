/*
 * CoadunationTypeManagerConsole: The console for managing the types.
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
 * ResourceCreationFactory.java
 */

// package path
package com.rift.coad.type.manager.client.type.factory;

// smart gwt includes
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.NavigationTree;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.smartgwt.client.widgets.Canvas;

// The coadunation includes
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This is the organisation creation factory. It is responsible for creating a
 * panel that enables the use to create new organisation types.
 *
 * @author brett chaldecott
 */
public class ResourceCreationFactory implements PanelFactory {

    /**
     * This object represents an organisation creation panel.
     */
    public class ResourceCreationPanel extends ConsolePanel {

        /**
         * The default constructor for the organisation creation panel.
         */
        public ResourceCreationPanel() {
        }

        /**
         * This method returns the panel information.
         * 
         * @return The reference to the newly created canvas.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();

            final DynamicForm form = new DynamicForm();
            form.setWidth(500);

            final SelectItem baseType = new SelectItem();
            baseType.setValueMap(types);
            baseType.setDefaultValue(types[0]);
            baseType.setRequired(true);
            baseType.setTitle("Base Type");
            baseType.setWidth("*");
            
            final TextItem typeItem = new TextItem();
            typeItem.setTitle("Type");
            typeItem.setRequired(true);
            typeItem.setLength(200);
            typeItem.setWidth("*");
            typeItem.setDefaultValue(types[0]);

            // set the value
            baseType.addChangeHandler(new ChangeHandler () {
                public void onChange(ChangeEvent event) {
                    typeItem.setValue(event.getValue().toString());
                }
            });

            ButtonItem createButton = new ButtonItem("Create");
            createButton.setLeft(300);
            createButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                public void onClick(ClickEvent event) {
                    try {
                        close();
                        String typeName = typeItem.getValue().toString();
                        String baseTypeStr = baseType.getValue().toString();
                        ResourceFactory factory = new ResourceFactory(baseTypeStr,typeName);
                        // TODO : correct image
                        NavigationTreeNode node = new NavigationTreeNode(typeName, typeName, name, "application-system.png",
                                        factory, true, "",false);
                        addPanel(node, (ConsolePanel)factory.create());
                    } catch (Exception ex) {
                        SC.say("Failed to instanciate the panel : " + ex.getMessage());
                    }
                }
            });

            form.setFields(new FormItem[]{baseType, typeItem,createButton});
            layout.addMember(form);

            return layout;
        }

        /**
         * This method returns the name for this panel
         * 
         * @return
         */
        @Override
        public String getName() {
            return name;
        }
    }
    // private member variables
    private String id;
    private String path;
    private String name;
    private String[] types = null;


    /**
     * The default constructor for the organisation.
     */
    public ResourceCreationFactory(String path, String name, String[] types) {
        this.path = path;
        this.name = name;
        this.types = types;
    }

    /**
     * This method returns a new canvas.
     *
     * @return The reference to the canvas.
     */
    public Canvas create() {
        ResourceCreationPanel panel = new ResourceCreationPanel();
        id = panel.getID();
        return panel;
    }

    /**
     * This method returns the id of this organisation.
     *
     * @return The string containing the id of the organisation.
     */
    public String getID() {
        return id;
    }

    /**
     * The string containing the description of the organisation.
     *
     * @return The string containing the description of the organisation.
     */
    public String getDescription() {
        return "Resource Creation Factory";
    }
}
