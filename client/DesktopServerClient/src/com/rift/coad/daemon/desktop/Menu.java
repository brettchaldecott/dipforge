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
 * Menu.java
 */

package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This object defines a menu.
 * 
 * @author brett
 */
public class Menu implements Serializable {
    // private member variables
    private String identifier = null;
    private List<MenuItem> items = new ArrayList<MenuItem>();
    
    /**
     * The default constructor of the menu.
     */
    public Menu() {
        
    }
    
    /**
     * The default constructor of the menu.
     * 
     * @param identifier The identifier value to set.
     */
    public Menu(String identifier) {
        this.identifier = identifier;
    }
    
    
    /**
     * The getter for the identifier.
     * @return The identifier for this menu.
     */
    public String getIdentifier() {
        return identifier;
    }
    
    
    /**
     * The setter for the identifier value.
     * 
     * @param identifier The new identifier value.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    /**
     * This method returns the list of menu item.
     * 
     * @return The list of menu items held within.
     */
    public List<MenuItem> getItems() {
        return items;
    }
    
    
    /**
     * This method adds item to the beginning of the list.
     * 
     * @param item The item to add.
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }
    
    /**
     * This method sets the items list.
     * 
     * @param items The list of items to set.
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
    
    /**
     * This method removes the specified item from the list.
     * 
     * @param item The item to remove from the list.
     */
    public void removeItem(MenuItem item) {
        this.items.remove(item);
    }
    
    
}
