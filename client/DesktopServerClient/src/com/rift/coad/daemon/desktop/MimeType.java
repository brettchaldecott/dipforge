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
 * Mime.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;

/**
 * The mime information about applications.
 * 
 * @author brett chaldecott
 */
public class MimeType implements Serializable {
    
     // private member variables
    private String name;
    private String url;
    private String icon;
    private int width;
    private int height;
    
    
    /**
     * The default constructor of the mime object.
     */
    public MimeType() {
        
    }
    
    
    /**
     * 
     * @param name
     * @param url
     * @param icon
     * @param width 
     * @param height
     */
    public MimeType(String name, String url, String icon, int width, int height) {
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.width = width;
        this.height = height;
    }
    
    

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }
    
   
    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the value of icon
     *
     * @return the value of icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Set the value of icon
     *
     * @param icon new value of icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
    /**
     * Get the value of width
     *
     * @return the value of width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the value of width
     *
     * @param width new value of width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    
    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(int height) {
        this.height = height;
    }


}
