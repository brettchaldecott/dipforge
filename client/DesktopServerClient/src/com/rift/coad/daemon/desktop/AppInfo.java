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
 * AppInfo.java
 */

// the package path
package com.rift.coad.daemon.desktop;

/**
 * This object represents the application information.
 * 
 * @author brett chaldecott
 */
public class AppInfo {
    // private member variables
    private String identifier;
    private String name;
    protected String title;
    private String url;
    private int height;
    private int width;
    private int xpos;
    private int ypos;
    protected int zpos;

    /**
     * The constructor of the application information.
     * 
     * @param identifier The identifier for the application.
     * @param name The name of this application.
     * @param title The title for the application.
     * @param url The url to launch for this application.
     * @param height The height of the application window.
     * @param width The width of the application window.
     * @param xpos The xpos for the application.
     * @param ypos The ypos for the application.
     * @param zpos The zpos for the application.
     */
    public AppInfo(String identifier, String name, String title, String url, int height, int width, int xpos, int ypos, int zpos) {
        this.identifier = identifier;
        this.name = name;
        this.title = title;
        this.url = url;
        this.height = height;
        this.width = width;
        this.xpos = xpos;
        this.ypos = ypos;
        this.zpos = zpos;
    }

    /**
     * Get the value of identifier
     *
     * @return the value of identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    
    /**
     * Set the value of identifier
     *
     * @param identifier new value of identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Get the value of xpos
     *
     * @return the value of xpos
     */
    public int getXpos() {
        return xpos;
    }

    /**
     * Set the value of xpos
     *
     * @param xpos new value of xpos
     */
    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    /**
     * Get the value of ypos
     *
     * @return the value of ypos
     */
    public int getYpos() {
        return ypos;
    }

    /**
     * Set the value of ypos
     *
     * @param ypos new value of ypos
     */
    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    /**
     * Get the value of zpos
     *
     * @return the value of zpos
     */
    public int getZpos() {
        return zpos;
    }

    /**
     * Set the value of zpos
     *
     * @param zpos new value of zpos
     */
    public void setZpos(int zpos) {
        this.zpos = zpos;
    }
}
