/*
 * DesktopServer: The server responsible for managing the general desktop resources.
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
 * MasterBarImpl.java
 */

// package path
package com.rift.coad.daemon.desktop.master;

// java imports
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// log4j imports
import org.apache.log4j.Logger;

// configuration
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.AuthorizationException;

// desktop server imports
import com.rift.coad.daemon.desktop.DesktopException;
import com.rift.coad.daemon.desktop.GadgetInfo;
import com.rift.coad.daemon.desktop.LaunchInfo;
import com.rift.coad.daemon.desktop.MasterBar;
import com.rift.coad.daemon.desktop.Menu;
import com.rift.coad.daemon.desktop.MenuItem;

/**
 * This object manages the master bar information.
 * 
 * @author brett chaldecott
 */
public class MasterBarImpl implements MasterBar {
    
    // class constants
    private final static String MENU_FILE = "menu_file";
    private final static String GADGETS_FILE = "gadgets_file";
    
    // private member variables
    private Logger log = Logger.getLogger(MasterBarImpl.class);
    
    
    /**
     * The default constructor for the master bar implementation.
     */
    public MasterBarImpl() throws MasterBarException {
        
    }
    
    
    /**
     * This method returns a list of the menu items for the requesting user.
     * 
     * @return The list of menu items for the requesting user.
     * @throws DesktopException
     */
    public List<Menu> getMenus() throws DesktopException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(MasterBarImpl.class);
            MenuParser parser = new MenuParser(config.getString(MENU_FILE));
            List<Menu> menus = new ArrayList<Menu>();
            for (Iterator iter = parser.getMenus().iterator(); iter.hasNext();) {
                MenuParser.Menu source = (MenuParser.Menu)iter.next();
                Menu target = new Menu(source.getIdentifier());
                processMenu(source, target);
                menus.add(target);
            }
            return menus;
        } catch (Exception ex) {
            log.error("Failed to load the master bar : " + ex.getMessage(),ex);
            throw new DesktopException("Failed to load the master bar : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the menus for the application.
     * 
     * @param menus The updated menus for the user making the request.
     * @throws DesktopException
     */
    public void setMenus(List<Menu> menus) throws DesktopException {
        throw new java.lang.UnsupportedOperationException("This method has not been implemented yet");
    }
    
    
    /**
     * This method returns a list of gadgets attached to the master bar.
     * 
     * @return This list of gadgets.
     * @throws DesktopException
     */
    public List<GadgetInfo> getGadgets() throws DesktopException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(MasterBarImpl.class);
            return (new GadgetParser(config.getString(GADGETS_FILE))).getGadgets();
        } catch (Exception ex) {
            log.error("Failed to load the list of gadgets: " + ex.getMessage(),ex);
            throw new DesktopException("Failed to load the list of gadgets : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a new gadget to the master bar.
     * 
     * @param gadget The new gadget to add to the master bar.
     * @throws DesktopException
     */
    public void addGadget(GadgetInfo gadget) throws DesktopException {
        throw new java.lang.UnsupportedOperationException("This method has not been implemented yet");
    }
    
    
    /**
     * This method removes the specified gadget from the users bar.
     * 
     * @param identifier of the gadget to remove.
     * @throws DesktopException
     */
    public void removeGadget(String identifier) throws DesktopException {
        throw new java.lang.UnsupportedOperationException("This method has not been implemented yet");
    }
    
    /**
     * This method is called to process the menu
     * 
     * @param source The source menu.
     * @param target The target.
     */
    private void processMenu(MenuParser.Menu source, Menu target) throws DesktopException {
        try {
            for (Iterator iter = source.getItems().iterator(); iter.hasNext();) {
                Object item = iter.next();
                if (item == null) {
                    target.addItem(new MenuItem());
                } else if (item instanceof MenuParser.Menu) {
                    MenuParser.Menu subSource = (MenuParser.Menu)item;
                    if (subSource.getRole() != null) {
                        try {
                            Validator.validate(this.getClass(), subSource.getRole());
                        } catch (AuthorizationException ex) {
                            continue;
                        }
                    }  
                    Menu subTarget = new Menu(subSource.getIdentifier());
                    target.addItem(new MenuItem(subSource.getIdentifier(), subTarget));
                    processMenu(subSource, subTarget);
                } else {
                    MenuParser.Launcher launcher = (MenuParser.Launcher)item;
                    if (launcher.getRole() != null) {
                        try {
                            Validator.validate(this.getClass(), launcher.getRole());
                        } catch (AuthorizationException ex) {
                            continue;
                        }
                    }
                    if (launcher.getTitle() != null && launcher.getTitle().equals("")) {
                        launcher.setTitle(null);
                    }
                    target.addItem(new MenuItem(launcher.getIdentifier(), 
                            new LaunchInfo(launcher.getIdentifier(), launcher.getName(),
                            launcher.getTitle(), launcher.getMouseOver(), launcher.getUrl(),
                            launcher.getWidth(), launcher.getHeight())));
                }
            }
        } catch (Exception ex) {
            log.error("Failed to process the menu : " + ex.getMessage(),ex);
            throw new DesktopException("Failed to process the menu : " + ex.getMessage(),ex);
        }
    }
}
