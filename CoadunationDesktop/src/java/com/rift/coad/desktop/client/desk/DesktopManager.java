/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2008  Rift IT Contracting
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
 * DesktopManager.java
 */

// the package path
package com.rift.coad.desktop.client.desk;

// imports
import com.rift.coad.desktop.client.desk.event.EventPanel;
import com.rift.coad.desktop.client.desk.feed.DesktopFeed;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwm.client.GDesktopPane;
import org.gwm.client.GFrame;
import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.impl.DefaultGFrame;

// coadunation imports
import com.rift.coad.desktop.client.top.TopPanel;
import org.gwm.client.GInternalFrame;
import org.gwm.client.impl.CoadFrame;
import org.gwm.client.impl.CoadInternalFrame;

/**
 * The desktop manager is responsible for managing the various desktops and what is running on them.
 * 
 * @author brett chaldecott
 */
public class DesktopManager extends Composite {
    // private member variables
    private String name = null;
    private VerticalPanel outer = new VerticalPanel();
    private DesktopPane desktop = new DesktopPane();
    private IconBar bottomPanel = null;
    private CoadDesktopFeed background = null;
    private DesktopFeed newsFeed = null;
    private EventPanel eventPanel = null;
    private ApplicationPanel appPanel = null;
    
    /**
     * The constructor of the desktop manager.
     * 
     * @param name The of the desktop to instanciate
     */
    public DesktopManager(DesktopInfo desktopInfo) {
        this.name = desktopInfo.getName();
        outer.setHeight("100%");
        outer.setWidth("100%");
        outer.setSpacing(0);
        outer.setStyleName("DesktopManager");
        
        // setup the desktop
        desktop.setWidth("100%");
        desktop.setTheme(desktopInfo.getTheme());
        desktop.setBackgroundImage(desktopInfo);
        outer.add((Widget) desktop);
        
        // setup the bottom panel
        bottomPanel =  new IconBar(desktop);
        outer.add(bottomPanel);
        desktop.setIconBar(bottomPanel);
        
        initWidget(outer);
        
        // setup the events
        eventPanel = new EventPanel();
        appPanel = new ApplicationPanel(this.name);
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(eventPanel);
        verticalPanel.add(new HTML("&nbsp;<br>"));
        verticalPanel.add(appPanel);
        
        // setup the feed
        newsFeed = new DesktopFeed(desktopInfo.getName());
        
        // setup the background
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setSpacing(20);
        horizontalPanel.add(verticalPanel);
        horizontalPanel.add(newsFeed);
        background = new CoadDesktopFeed("news",true);
        background.setContent(horizontalPanel);
        desktop.addGFrame(background);
        background.setVisible(true);
        
    }
    
    
    /**
     * Setup the size of the window
     */
    public void onWindowResized(int width, int height) {
        desktop.setHeight("" + (height - 50));
        
        background.setSize(width,height - 50);
        //desktop.setWidgetPosition(background, 0, 0);
        //newsFeed.setSize((width - 42) + "px", (height - 92) + "px");
    }
    
    
    /**
     * This method adds a new window to desktop
     */
    public void addWindow(GInternalFrame window) {
        desktop.addFrame(window);
    }
}
