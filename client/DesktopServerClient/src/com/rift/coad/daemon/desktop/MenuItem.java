/*
 * DesktopServerClient: The client interface to the desktop server.
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
 * MenuItem.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;

/**
 * This object represents a menu item.
 * 
 * @author brett chaldecott
 */
public class MenuItem implements Serializable {
    // private member variables
    private String identifier = null;
    private LaunchInfo info = null;
    private Menu subMenu = null;
    
    
    /**
     * The default constructor.
     */
    public MenuItem() {
    }
    
    
    /**
     * The constructor of the menu item.
     * 
     * @param identifier The string that identifes the item.
     * @param info The lancher.
     */
    public MenuItem(String identifier, LaunchInfo info) {
        this.identifier = identifier;
        this.info = info;
    }
    
    
    /**
     * The constructor of the menu item.
     * 
     * @param identifier The string that identifes the item.
     * @param info The lancher.
     */
    public MenuItem(String identifier, Menu subMenu) {
        this.identifier = identifier;
        this.subMenu = subMenu;
    }
    
    
    /**
     * This method returns the identifier.
     * 
     * @return The string containing the identifer.
     */
    public String getIdentifier() {
        return identifier;
    }
    
    
    /**
     * The setter for the identifier.
     * 
     * @param identifier The new identifier value.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    /**
     * This method returns the launch information.
     * 
     * @return This method returns the info on the launcher.
     */
    public LaunchInfo getInfo() {
        return info;
    }
    
    /**
     * This method sets the launch information.
     * 
     * @param info The new launch information.
     */
    public void setInfo(LaunchInfo info) {
        this.info = info;
    }

    
    /**
     * This method gets the submenu reference.
     * 
     * @return The reference to the menu sub object.
     */
    public Menu getSubMenu() {
        return subMenu;
    }
    
    
    /**
     * This method sets the sub menu reference.
     * 
     * @param subMenu The new sub menu reference.
     */
    public void setSubMenu(Menu subMenu) {
        this.subMenu = subMenu;
    }

    
    /**
     * The equals method.
     * 
     * @param obj The value to perform the equals operation on.
     * @return TRUE if the objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuItem other = (MenuItem) obj;
        if (this.identifier != other.identifier && (this.identifier == null || !this.identifier.equals(other.identifier))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This method returns the hash code for this object.
     * 
     * @return The integer containing the hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.identifier != null ? this.identifier.hashCode() : 0);
        return hash;
    }
    
    
    
}
