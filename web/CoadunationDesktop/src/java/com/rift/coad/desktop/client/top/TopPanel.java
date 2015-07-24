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
 * TopPanel.java
 */

// the packge path
package com.rift.coad.desktop.client.top;


// java imports
import java.util.List;
import java.util.Iterator;

// gwt imports
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// coadunation imports
import com.rift.coad.desktop.client.gadget.GadgetFactory;
import com.rift.coad.desktop.client.top.Launcher;

/**
 * The top panel for the.
 * 
 * @author brett chaldecott
 */
public class TopPanel extends Composite {

    /**
     * This class handles the processing of the menu
     */
    public static class MenuHandler implements AsyncCallback {
        
        // private member variables
        private MenuBar horizontalMenubar = null;
        
        /**
         * The constructor that takes a reference to the horizontal menubar to manage.
         * @param horizontalMenubar The bar to manage
         */
        public MenuHandler (MenuBar horizontalMenubar) {
            this.horizontalMenubar = horizontalMenubar;
        }
        /**
         * This method is called to deal with an unsucessful RPC request.
         * 
         * @param exception The exception that cased this problem.
         */
        public void onFailure(Throwable exception) {
            Window.alert("Failed to load the menu : " + exception.getMessage());
        }

        /**
         * This method is called when there is an on success.
         * 
         * @param menuObj The object containing the menu items.
         */
        public void onSuccess(Object menuObj) {
            this.horizontalMenubar.clearItems();
            List<Menu> menus = (List<Menu>)menuObj;
            for (Iterator<Menu> iter = menus.iterator(); iter.hasNext();) {
                Menu menu = iter.next();
                MenuBar menubar = new MenuBar(true);
                horizontalMenubar.addItem(menu.getIdentifier(), true, menubar);
                processMenu(menubar,menu);
            }
        }
        
        
        /**
         * process the menu
         */
        public void processMenu(MenuBar menubar, Menu menu) {
            List<MenuItem> items = menu.getItems();
            for (Iterator<MenuItem> iter = items.iterator(); iter.hasNext();) {
                MenuItem item = iter.next();
                if (item.getIdentifier() == null) {
                    menubar.addSeparator();
                } else if (item.getInfo() != null) {
                    LaunchInfo info = item.getInfo();
                    menubar.addItem(info.getIdentifier(), true, new Launcher(info));
                } else {
                    MenuBar subMenubar = new MenuBar(true);
                    menubar.addItem(item.getSubMenu().getIdentifier(), true, subMenubar);
                    processMenu(subMenubar,item.getSubMenu());
                }
            }
        }
    }
    
    
     /**
     * This class handles the processing of the menu
     */
    public static class GadgetHandler implements AsyncCallback {
        
        // private member variables
        private HorizontalPanel gadgetPanel = null;
        
        /**
         * The constructor that takes a reference to the horizontal menubar to manage.
         * @param horizontalMenubar The bar to manage
         */
        public GadgetHandler (HorizontalPanel gadgetPanel) {
            this.gadgetPanel = gadgetPanel;
        }
        
        
        /**
         * This method is called to deal with an unsucessful RPC request.
         * 
         * @param exception The exception that cased this problem.
         */
        public void onFailure(Throwable exception) {
            Window.alert("Failed to load the gadgets : " + exception.getMessage());
        }

        /**
         * This method is called when there is an on success.
         * 
         * @param menuObj The object containing the menu items.
         */
        public void onSuccess(Object gadgetObjects) {
            this.gadgetPanel.clear();
            List<GadgetInfo> gadgets = (List<GadgetInfo>)gadgetObjects;
            for (Iterator<GadgetInfo> iter = gadgets.iterator(); iter.hasNext();) {
                this.gadgetPanel.add(GadgetFactory.getInstance().getGadget(iter.next()));
            }
        }
        
    }
    
    // class singleton
    private static TopPanel singleton = null;
    
    // class constats
    public final static int HEIGHT = 27;
    
    // private member variables
    private HorizontalPanel masterPanel = new HorizontalPanel();
    private MenuBar horizontalMenubar = new MenuBar();
    private HorizontalPanel gadgetPanel = new HorizontalPanel();

    /**
     * The constructor of the top panel. This object is responsible
     * for managing what gets displayed at the top of the desktop.
     */
    private TopPanel() {
        // add the menu bar panel
        masterPanel.add(horizontalMenubar);
        masterPanel.setCellHorizontalAlignment(horizontalMenubar, HorizontalPanel.ALIGN_LEFT);
        
        // add the gadget bar panel
        masterPanel.add(gadgetPanel);
        gadgetPanel.setStyleName("TopGadgetPanel");
        gadgetPanel.setWidth("100%");
        gadgetPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
        gadgetPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        masterPanel.setCellHorizontalAlignment(gadgetPanel, HorizontalPanel.ALIGN_RIGHT);
        
        masterPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
        masterPanel.setWidth("100%");
        
        initWidget(masterPanel);
        this.setWidth("100%");
        this.setStyleName("TopPanel");
        
        loadMenu();
        loadGadgets();
    }

    /**
     * This method returns a reference to the top panel singleton. 
     */
    public static synchronized TopPanel getIntance() {
        if (singleton == null) {
            singleton = new TopPanel();
        }
        return singleton;
    }

    /**
     * This method is responsible for loading the menu
     */
    public void loadMenu() {
        TopBarAsync bar = getTopBarService();
        bar.getMenus(new MenuHandler(this.horizontalMenubar));
    }
    
    /**
     * This method is responsible for loading the gadgets
     */
    public void loadGadgets() {
        TopBarAsync bar = getTopBarService();
        bar.getGadgets(new GadgetHandler(this.gadgetPanel));
    }
    
    /**
     * This method is used to get a referance to the top bar service that wrapps the rpc call.
     * 
     * @return A reference to the top bar async cal object.
     */
    public TopBarAsync getTopBarService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        TopBarAsync service = (TopBarAsync) GWT.create(TopBar.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "/top/topbar";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
