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
 * MainEntryPoint.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * The entry point for the Coadunation Admin Console.
 *
 * @author brett chaldecott
 */
public class MainEntryPoint implements EntryPoint, WindowResizeListener {
    
    // private member variables
    //private ShortCutBar shortCutBar = new ShortCutBar();
    private TopPanel topPanel = new TopPanel();
    private TabPanel tabPanel = new TabPanel();
    private MXPanel mxPanel = null;
    private DaemonPanel daemonPanel = null;
    private WebServicePanel webServicePanel = null;
    
    
    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }
    
    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        
        topPanel.setWidth("100%");
        tabPanel.setWidth("100%");
        
        // mx panel
        mxPanel = new MXPanel();
        mxPanel.setWidth("100%");
        tabPanel.add(mxPanel,"MX Beans");
        
        // daemon panel
        daemonPanel = new DaemonPanel();
        daemonPanel.setWidth("100%");
        tabPanel.add(daemonPanel,"Daemons");
        
        // web service panel
        webServicePanel = new WebServicePanel();
        webServicePanel.setWidth("100%");
        tabPanel.add(webServicePanel,"Web Services");
        
        // set the tab index
        tabPanel.selectTab(0);
        
        // the dock panel
        DockPanel outer = new DockPanel();
        outer.add(topPanel, DockPanel.NORTH);
        outer.add(tabPanel, DockPanel.CENTER);
        outer.setSize("100%","100%");
        
        outer.setSpacing(4);
        outer.setCellWidth(tabPanel, "100%");
        
        // Hook the window resize event, so that we can adjust the UI.
        Window.addWindowResizeListener(this);
        
        // Get rid of scrollbars, and clear out the window's built-in margin,
        // because we want to take advantage of the entire client area.
        //Window.enableScrolling(false);
        Window.setMargin("0px");
        
        // Finally, add the outer panel to the RootPanel, so that it will be
        // displayed.
        RootPanel.get().add(outer);
        
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
     * This method is responsible for adjusting the size of the various components
     *
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    public void onWindowResized(int width, int height) {
    }
    
    
    
}
