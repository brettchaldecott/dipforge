/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2008  2015 Burntjam
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
package com.rift.coad.desktop.client.desk.feed;

// java imports
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


// the composite import
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import java.util.Iterator;

/**
 * The object responsible for displaying the desktop feed for a user.
 * 
 * @author brett chaldecott
 */
public class DesktopFeed extends SimplePanel {
    // private member variables

    private String desktopName = null;
    private Map<String, VerticalPanel> contents = new HashMap<String, VerticalPanel>();
    private Map<String, ScrollPanel> scrollPanels = new HashMap<String, ScrollPanel>();
    private Map<String, FlexTable> panelWidgets = new HashMap<String, FlexTable>();
    private TabPanel tabs = new TabPanel();
    //private ScrollPanel scrollPanel = new ScrollPanel();

    /**
     * The constructor of the desktop feed.
     */
    public DesktopFeed(String desktopName) {
        this.desktopName = desktopName;
        
        this.setWidget(tabs);
        DesktopFeedManager.getInstance().registerDesktopFeed(this);
    }
    
    /**
     * This method returns the desktop name.
     * 
     * @return This method return the name.
     */
    public String getDesktopName() {
        return desktopName;
    }
    
    
    
    /**
     * Set the size of the scroll panel
     */
    public void setSize(int width, int height) {
        for (Iterator<String> iter = scrollPanels.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            this.panelWidgets.get(key).setSize("600px", (height - 35) + "px");
        }
        tabs.setSize("600px", height + "px");
    }

    /**
     * This method is responsible for setting the list of tabs for this desktop feed.
     * 
     * @param tabs The list of tabs to loop through
     */
    public void setTabs(String[] tabs) {
        for (int index = 0; index < tabs.length; index++) {
            if (contents.containsKey(tabs[index])) {
                continue;
            }
            FlexTable flexTable = new FlexTable();
            ScrollPanel scrollPanel = new ScrollPanel();
            VerticalPanel content = new VerticalPanel();
            scrollPanel.add(content);
            scrollPanel.setAlwaysShowScrollBars(true);
            scrollPanel.setStyleName("DesktopFeed-ScrollPanel");
            flexTable.setWidget(0, 0, scrollPanel);
            this.scrollPanels.put(tabs[index], scrollPanel);
            this.contents.put(tabs[index], content);
            this.panelWidgets.put(tabs[index], flexTable);
            
        }
        
        // loop through the scroll panel entries
        for (Iterator<String> iter = scrollPanels.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            boolean found = false;
            for (int index = 0; index < tabs.length; index++) {
                if (tabs[index].equals(key)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.scrollPanels.remove(key);
                this.contents.remove(key);
                this.panelWidgets.remove(key);
            }
        }
        
        // clear and reset the tabs
        this.tabs.clear();
        for (Iterator<String> iter= this.panelWidgets.keySet().iterator(); iter.hasNext();) {
            String identifier = iter.next();
            this.tabs.add(panelWidgets.get(identifier), identifier);
        }
        this.tabs.selectTab(0);
    }
    
    
    /**
     * This method adds content to the specified tab identifier.
     */
    public void addContent(String identifier, FeedEvent[] events) {
        VerticalPanel content = this.contents.get(identifier);
        content.clear();
        for (int index = 0; index < events.length; index++) {
            content.add(new DesktopEventLauncher(events[index]));
        }
        
    } 
    
}
