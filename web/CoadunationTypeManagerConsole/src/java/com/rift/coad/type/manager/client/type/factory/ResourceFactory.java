/*
 * CoadunationTypeManagerConsole: The console for managing the types.
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
 * ResourceFactory.java
 */

package com.rift.coad.type.manager.client.type.factory;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.rift.coad.type.manager.client.ManageResourcesUtil;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.type.manager.client.dto.ResourceDefinition;

/**
 * The resource factory.
 *
 * @author brett chaldecott
 */
public class ResourceFactory implements PanelFactory {

    public class CallBack implements AsyncCallback {

        public void onFailure(Throwable caught) {
            if (created) {
                SC.say("Failed to add the entry : " + caught.getMessage());
            } else {
                SC.say("Failed to update the entry : " + caught.getMessage());
            }
        }

        public void onSuccess(Object result) {
            created = false;
        }

    }

    /**
     * This object represents the resource to modify.
     */
    public class ResourcePanel extends ConsolePanel {

        // TODO: Type Panel
        //private TypePanel typePanel;
        private IButton button;


        /**
         * The default constructor for the resource panel.
         */
        public ResourcePanel() {

        }


        /**
         * This method returns a view of the panel.
         *
         * @return The view of the panel
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();
            //typePanel = new TypePanel(resource);
            //layout.addMember(typePanel);
            
            VLayout spacer = new VLayout();
            spacer.setWidth("*");
            spacer.setHeight(10);
            layout.addMember(spacer);

            button = new IButton("Save");
            button.addClickHandler(new ClickHandler(){

                public void onClick(ClickEvent event) {
                    if (created) {
                        //ResourceBase base = typePanel.getResource();
                        //SC.say("Number of attributes is : " + base.getAttributes().length);
                        //ManageResourcesUtil.getService().addType(
                        //        base, new CallBack());
                    } else {
                        //ManageResourcesUtil.getService().updateType(
                        //        typePanel.getResource(), new CallBack());
                    }
                }

            });
            layout.addMember(button);
            return layout;
        }


        /**
         * The name of the panel.
         *
         * @return The method returns the name of this factory.
         */
        @Override
        public String getName() {
            return resource.getLocalname();
        }


        /**
         * This method returns the icon path for this console
         *
         * @return The icon path.
         */
        @Override
        public String getIcon() {
            try {
                //return com.rift.coad.rdf.objmapping.ui.client.tree.type.TypeManager.
                //    getIcon(typePanel.getBasicType());
                return null;
            } catch (Exception ex) {
                SC.say("Failed to return the type : " + ex.getMessage());
                return null;
            }
        }




    }

    // private member variables
    private ResourceDefinition resource;
    private String id;
    private boolean created = false;

    /**
     * The reference to the resource.
     *
     * @param resource The resource reference.
     */
    public ResourceFactory(ResourceDefinition resource) {
       this.resource = resource;
    }


    /**
     * The constructor for the resource factory.
     *
     * @param basicType The basic type information for this resource.
     * @param typeId The type id for this resource.
     */
    public ResourceFactory(String basicType, String typeId) throws FactoryException {
        try {
            //this.resource = (ResourceBase)TypeManager.getType(basicType);
            //this.resource.setIdForDataType(typeId);

            // set the data type name for this type.
            String name = typeId;
            if (typeId.contains(".")) {
                name = typeId.substring(typeId.lastIndexOf(".") + 1);
            }
            //this.resource.setDataName(name);
            
            created = true;
        } catch (Exception ex) {
            SC.say("Error [" + ex.getMessage() + "] failed to create factory");
            throw new FactoryException("Failed to retrieve the resource : " +
                    ex.getMessage());
        }
    }

    /**
     * This method returns the newly created canvas.
     * @return
     */
    public Canvas create() {
        ResourcePanel panel = new ResourcePanel();
        id = panel.getID();
        return panel;
        //return null;
    }

    /**
     * This method returns the id of the resource factory.
     *
     * @return The id of the resource factory.
     */
    public String getID() {
        return id;
    }


    /**
     * The description of this object.
     *
     * @return The description.
     */
    public String getDescription() {
        return "Resource Factory";
    }

}
