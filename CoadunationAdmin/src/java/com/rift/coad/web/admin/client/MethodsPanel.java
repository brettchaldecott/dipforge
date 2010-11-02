/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * ObjectsPanel.java
 */

package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;


/**
 * This panel lists the objects that will be displayed on the frontend.
 *
 * @author brett chaldecott
 */
public class MethodsPanel extends Composite implements ChangeListener {
    
    // private member variables
    private MethodsListener listener = null;
    private ListBox methods = new ListBox();
    
    /** 
     * Creates a new instance of ObjectsPanel 
     *
     * @param listener The reference to the object listening for events on this
     *      object.
     */
    public MethodsPanel(MethodsListener listener) {
        this.listener = listener;
        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("panel-Border");
        Label objectLabel = new Label("Methods");
        objectLabel.setStyleName("header-Label");
        panel.add(objectLabel);
        methods.setVisibleItemCount(10);
        methods.setPixelSize(300,200);
        methods.addChangeListener(this);
        panel.add(methods);
        
        initWidget(panel);
    }
    
    
    /**
     * This method adds the list of objects
     */
    public void setMethods(String[] entries) {
        methods.clear();
        for (int index = 0; index < entries.length; index++)
        {
            methods.addItem(entries[index]);
        }
    }
    
    /**
     * Deal with events on the methods panel
     */
    public void onChange(Widget sender) {
        if (sender == methods) {
            listener.methodSelected(
                    methods.getItemText(methods.getSelectedIndex()));
        }
    }
    
}
