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
 * DesktopTabManager.java
 */

// package path
package com.rift.coad.desktop.client.desk;

// java imports
import java.util.List;

// gwt imports
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TabListener;

// coadunation imports
import com.rift.coad.desktop.client.widgets.WidgetPanel;
import com.rift.coad.desktop.client.top.TopPanel;

/**
 * This object is responsible for managing the desktops.
 * 
 * @author brett chaldecott
 */
public class DesktopTabManager extends Composite implements TabListener {

    /**
     * The class is responsible for handling the async processing of the desktops.
     */
    public class DesktopHandler implements AsyncCallback {

        /**
         * The async on failure method.
         * 
         * @param ex The exception that caused this problem.
         */
        public void onFailure(Throwable ex) {
            Window.alert("Failed to load the desktop information : " + ex.getMessage());
        }

        /**
         * This method deals with the success.
         * @param arg0
         */
        public void onSuccess(Object result) {
            List<DesktopInfo> desktops = (List<DesktopInfo>) result;
            for (DesktopInfo desktopInfo : desktops) {
                DesktopManager desktop = new DesktopManager(desktopInfo);
                desktop.setHeight("100%");
                tabs.add(desktop, desktopInfo.getName());
                desktop.onWindowResized(
                        Window.getClientWidth(), Window.getClientHeight() - TopPanel.HEIGHT);
            }
            tabs.selectTab(0);
            currentDesktop = (DesktopManager) tabs.getWidget(0);

        }
    }    // singleton member variables
    private static DesktopTabManager singleton = null;    // private member variables
    private TabPanel tabs = new TabPanel();
    private DesktopManager currentDesktop = null;

    /**
     * The default constructor of the tab manager
     */
    private DesktopTabManager() {
        tabs.addTabListener(this);
       // tabs.addStyleName("gwt-DecoratedTabBar");
        initWidget(tabs);
        
        getService().listDesktops(new DesktopHandler());
    }

    /**
     * This method returns the synchronized desktop tab manager instance.
     * 
     * @return The reference to the desktop tab manager.
     */
    public static synchronized DesktopTabManager getInstance() {
        if (singleton == null) {
            singleton = new DesktopTabManager();
        }
        return singleton;
    }

    /**
     * This method is responsible for setting the size of various windows when things
     * get resized.
     * @param width The width of the entire window.
     * @param height The height of the entire window.
     */
    public void onWindowResized(int width, int height) {
        //Window.alert("[" + (width) + "][" + (height - TopPanel.HEIGHT) +"]");
        tabs.setSize("" + (width), "" + (height - TopPanel.HEIGHT));
        for (int index = 0; index < tabs.getWidgetCount(); index++) {
            DesktopManager desktop = (DesktopManager) tabs.getWidget(index);
            desktop.onWindowResized(width, height - TopPanel.HEIGHT);
        }
    }

    /**
     * This method listens to the tab on before select event
     * 
     * @param source The source of the event.
     * @param tabId The id of the tab.
     * @return True if the 
     */
    public boolean onBeforeTabSelected(SourcesTabEvents source, int tabId) {
        return true;
    }

    /**
     * This method is called to process the on tab select event.
     * 
     * @param source The source of the tab events.
     * @param tabId The id of the tab being selected.
     */
    public void onTabSelected(SourcesTabEvents source, int tabId) {
        currentDesktop = (DesktopManager) tabs.getWidget(tabId);
    }

    /**
     * This method returns the current desktop reference.
     * 
     * @return The reference to the current desktop.
     */
    public DesktopManager getCurrentDesktop() {
        return currentDesktop;
    }

    /**
     * This method returns a reference to the desktop rpc.
     * 
     * @return a reference to the desktop async rpc object.
     */
    public DesktopRPCAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        DesktopRPCAsync service = (DesktopRPCAsync) GWT.create(DesktopRPC.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "desk/desktoprpc";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
