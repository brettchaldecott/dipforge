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
 * TopBarImpl.java
 */

// package path
package com.rift.coad.desktop.server.top;

// java imports
import com.rift.coad.desktop.client.top.GadgetInfo;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

// log 4j imports
import org.apache.log4j.Logger;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// coadunation desktop imports
import com.rift.coad.desktop.client.top.LaunchInfo;
import com.rift.coad.desktop.client.top.Menu;
import com.rift.coad.desktop.client.top.MenuItem;
import com.rift.coad.desktop.client.top.TopBar;

// coadunation desktop server imports
import com.rift.coad.daemon.desktop.MasterBar;

/**
 * The implementation of the top bar.
 * 
 * @author brett
 */
public class TopBarImpl extends RemoteServiceServlet implements
        TopBar {
    // private member variables
    private Logger log = Logger.getLogger(TopBarImpl.class);

    /**
     * The constructor of the top bar
     */
    public TopBarImpl() {
    }

    /**
     * This method returns the list of menu items.
     * 
     * @return The list of menu items.
     */
    public List<Menu> getMenus() {
        List<Menu> menus = new ArrayList<Menu>();
        try {
            MasterBar bar = getMasterBar();
            if (bar == null) {
                return menus;
            }
            List<com.rift.coad.daemon.desktop.Menu> barMenus = bar.getMenus();
            for (Iterator<com.rift.coad.daemon.desktop.Menu> iter = barMenus.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.desktop.Menu barMenu = iter.next();
                Menu menu = new Menu(barMenu.getIdentifier());
                addMenu(menu, barMenu);
                menus.add(menu);
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve the menu information : " + ex.getMessage(), ex);
        }
        return menus;
    }

    
    /**
     * This method is responsible for adding the new menu.
     * 
     * @param targetMenu The menu to add the items to.
     * @param sourceMenu The source for the menu items.
     */
    private void addMenu(Menu targetMenu, com.rift.coad.daemon.desktop.Menu sourceMenu) {
        for (Iterator<com.rift.coad.daemon.desktop.MenuItem> iter = sourceMenu.getItems().iterator(); iter.hasNext();) {
            com.rift.coad.daemon.desktop.MenuItem item = iter.next();
            if (item.getSubMenu() == null && item.getInfo() == null) {
                targetMenu.addItem(new MenuItem());
            } else if (item.getSubMenu() != null) {
                Menu subMenu = new Menu(item.getSubMenu().getIdentifier());
                addMenu(subMenu, item.getSubMenu());
                targetMenu.addItem(new MenuItem(item.getIdentifier(), subMenu));
            } else {
                com.rift.coad.daemon.desktop.LaunchInfo info = item.getInfo();
                targetMenu.addItem(new MenuItem(item.getIdentifier(), new LaunchInfo(info.getIdentifier(),
                        info.getName(), info.getTitle(), info.getMouseOver(), info.getUrl(), info.getWidth(),
                        info.getHeight())));
            }
        }
    }

    /**
     * This method attempts to make a connection to the master bar to request the menu structure.
     * 
     * @return The reference to the master bar.
     */
    private MasterBar getMasterBar() {
        try {
            Context ctx = new InitialContext();
            Object ref = ctx.lookup("desktop/MasterBar");
            if (ref != null) {
                return (MasterBar) PortableRemoteObject.narrow(ref, MasterBar.class);
            }
            log.error("Failed to narrow the master bar");
        } catch (Exception ex) {
            log.error("Fail to narrow the master bar : " + ex.getMessage(), ex);
        }
        return null;
    }

    
    /**
     * This method returns the list of gadgets to display on the fronend.
     * 
     * @return The list of gadgets to display on the frontend.
     */
    public List<GadgetInfo> getGadgets() {
        try {
            List<GadgetInfo> gadgets = new ArrayList<GadgetInfo>();
            List<com.rift.coad.daemon.desktop.GadgetInfo> daemonInfo = getMasterBar().getGadgets();
            for (Iterator<com.rift.coad.daemon.desktop.GadgetInfo> iter = 
                    daemonInfo.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.desktop.GadgetInfo daemonGadget = iter.next();
                GadgetInfo info = new GadgetInfo(daemonGadget.getIdentifier(),
                        daemonGadget.getName(),  daemonGadget.getUrl(),
                        daemonGadget.getWidth(), daemonGadget.getHeight());
                if (daemonGadget.getApp() != null) {
                    info.setApp(new GadgetInfo.EmbeddedApp(daemonGadget.getApp().getUrl(), daemonGadget.getApp().getWidth(), 
                            daemonGadget.getApp().getHeight(), daemonGadget.getApp().isPopup(),
                            daemonGadget.getApp().getPopupURL(), daemonGadget.getApp().getHeight(),
                            daemonGadget.getApp().getPopupWidth()));
                }
                gadgets.add(info);
            }
            return gadgets;
        } catch (Exception ex) {
            log.error("Failed to retrieve the desktop gadget information  : " + ex.getMessage(),ex);
        }
        return null;
    }
}
