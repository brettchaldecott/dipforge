/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
public class ObjectsPanel extends Composite implements ChangeListener {
    
    // private member variables
    private ObjectsListener listener = null;
    private ListBox objects = new ListBox();
    
    /** 
     * Creates a new instance of ObjectsPanel 
     *
     * @param listener The object listener.
     */
    public ObjectsPanel(ObjectsListener listener) {
        this.listener = listener;
        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("panel-Border");
        Label objectLabel = new Label("Objects");
        objectLabel.setStyleName("header-Label");
        panel.add(objectLabel);
        objects.setVisibleItemCount(10);
        objects.setPixelSize(300,200);
        panel.add(objects);
        objects.addChangeListener(this);
        initWidget(panel);
    }
    
    
    /** 
     * Creates a new instance of ObjectsPanel 
     *
     * @param listener The object listener.
     */
    public ObjectsPanel(ObjectsListener listener, int height) {
        this.listener = listener;
        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("panel-Border");
        Label objectLabel = new Label("Objects");
        objectLabel.setStyleName("header-Label");
        panel.add(objectLabel);
        objects.setVisibleItemCount(10);
        objects.setPixelSize(300,height);
        panel.add(objects);
        objects.addChangeListener(this);
        initWidget(panel);
    }
    
    
    /**
     * This method adds the list of objects
     */
    public void setObjects(String[] entries) {
        objects.clear();
        for (int index = 0; index < entries.length; index++)
        {
            objects.addItem(entries[index]);
        }
    }
    
    
    /**
     * This method will be called when the list changes
     */
    public void onChange(Widget sender) {
        if (sender == objects) {
            listener.objectSelected(
                    objects.getItemText(objects.getSelectedIndex()));
        }
    }
    
}
