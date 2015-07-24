/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2007  2015 Burntjam
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
 * MainEntryPoint.java
 */

// package path
package com.rift.coad.desktop.client;

// gwt imports
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;

// coadunation imports
import com.rift.coad.desktop.client.top.TopPanel;
import com.rift.coad.desktop.client.desk.DesktopTabManager;
import com.rift.coad.desktop.client.widgets.WidgetPanel;

/**
 * This is the main entry point for the coadunation desktop.
 * 
 * @author brett chaldecott
 */
public class MainEntryPoint implements EntryPoint, WindowResizeListener {

    // private member variables
    //private WidgetPanel widgets = null;
    
    /** Creates a new instance of MainEntryPoint */
    public MainEntryPoint() {
    }

    /** 
        The entry point method, called automatically by loading a module
        that declares an implementing class as an entry-point
    */
    public void onModuleLoad() {
        DockPanel outer = new DockPanel();
        outer.setSize("100%", "100%");
        outer.setBorderWidth(0);
        
        // add the top panel
        TopPanel.getIntance().setStyleName("TopPanel");
        outer.add(TopPanel.getIntance(), DockPanel.NORTH);
        
        // add the tab destop manager
        outer.add(DesktopTabManager.getInstance(), DockPanel.CENTER);
        
        // add the widgets panel
        //widgets = new WidgetPanel();
        //outer.add(widgets, DockPanel.EAST);
        
        // add to outer
        RootPanel.get().add(outer);
        
        // setup the window
        Window.addWindowResizeListener(this);
        Window.enableScrolling(false);

        // Call the window resized handler to get the initial sizes setup. Doing
        // this in a deferred command causes it to occur after all widgets' sizes
        // have been computed by the browser.
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            onWindowResized(Window.getClientWidth(), Window.getClientHeight());
          }
        });

        onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    }
    
    
    /**
     * This method is responsible for setting the size of various windows when things
     * get resized.
     * @param width The width of the entire window.
     * @param height The height of the entire window.
     */
    public void onWindowResized(int width, int height) {
        DesktopTabManager.getInstance().onWindowResized(width, height);
        //widgets.onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    }

}
