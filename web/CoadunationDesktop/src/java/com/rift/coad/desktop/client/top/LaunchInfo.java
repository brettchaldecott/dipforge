/*
 * DesktopServerClient: The client interface to the desktop server.
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
 * LaunchInfo.java
 */

// package path
package com.rift.coad.desktop.client.top;

// imports
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * The object containing the information necessary to launch the application.
 * 
 * @author brett chaldecott
 */
public class LaunchInfo implements IsSerializable {
    // private member variables
    private String identifier = null;
    private String name = null;
    private String title = null;
    private String mouseOver = null;
    private String url = null;
    private int width = 0;
    private int height = 0;

    /**
     * The default constructor of the launch information.
     */
    public LaunchInfo() {
    }

    /**
     * This constructor is responsible for setting up an object with the supplied initial values.
     * 
     * @param identifier The identifier of this entry.
     * @param name The name of the application to launch.
     * @param title The title of the launcher.
     * @param mouseOver The mouse over.
     * @param url The url of this request.
     * @param width The width of this window.
     * @param height The height of he window
     */
    public LaunchInfo(String identifier, String name, String title, String mouseOver, String url, int width, int height) {
        this.identifier = identifier;
        this.name = name;
        this.title = title;
        this.mouseOver = mouseOver;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    /**
     * The setter for the height member variable
     * 
     * @param height This method sets the height of the object.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * The setter for the identifier.
     * 
     * @param identifier The identifier string value.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    /**
     * This method is responsible for setting the name of the application to launch.
     * 
     * @param The name of the application to launch.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * The setter for the title.
     * 
     * @param title The new title value.
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * This method sets the mouse over value.
     * 
     * @param mouseOver The string containing the mouse over identifier.
     */
    public void setMouseOver(String mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * This method set the url value.
     * 
     * @param url The string containing the new url value.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method sets the width of the launched window.
     * 
     * @param width The width of the window to be launged
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * This method retrieves the height of the window to be launched.
     * 
     * @return The int containing the height of the window to launch.
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method returns the identifier of the string.
     *  
     * @return The string containing the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * This method returns the name of the application to launch.
     * 
     * @return The string containing the name of the application to launch.
     */
    public String getName() {
        return name;
    }
    
    /**
     * This method returns the title for this object.
     *  
     * @return The string containing the identifier.
     */
    public String getTitle() {
        return this.title;
    }
    
    
    /**
     * This method returns the mouse over value.
     * 
     * @return The string containing the mouse over value.
     */
    public String getMouseOver() {
        return mouseOver;
    }

    /**
     * This method returns the url that will be launched.
     * 
     * @return The string containing the url to launch.
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method returns the width of the launch window.
     * 
     * @return The integer containing the width of the launch window.
     */
    public int getWidth() {
        return width;
    }

    /**
     * The equals method
     * @param obj The object to perform the equals on.
     * @return The result of the equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LaunchInfo other = (LaunchInfo) obj;
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
        hash = 97 * hash + (this.identifier != null ? this.identifier.hashCode() : 0);
        return hash;
    }
}
