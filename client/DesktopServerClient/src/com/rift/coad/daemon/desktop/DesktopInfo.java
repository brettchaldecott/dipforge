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
 * DesktopInfo.java
 */

// package path
package com.rift.coad.daemon.desktop;

// package path
import java.io.Serializable;

/**
 * This object contains th desktop information.
 * 
 * @author brett chaldecott
 */
public class DesktopInfo implements Serializable {
    
    // private member variables
    private String name;
    private String theme;
    private String backgroundImage;
    private boolean repeat;
    
    /**
     * The constructor of the desktop information object.
     * 
     * @param name The name of the desktop.
     * @param theme The theme for the desktop.
     * @param backgroundImage The background image.
     */
    public DesktopInfo(String name, String theme, 
            String backgroundImage, boolean repeat) {
        this.name = name;
        this.theme = theme;
        this.backgroundImage = backgroundImage;
        this.repeat = repeat;
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
     * Get the value of backgroundImage
     *
     * @return the value of backgroundImage
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Set the value of backgroundImage
     *
     * @param backgroundImage new value of backgroundImage
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    
    /**
     * Get the value of theme
     *
     * @return the value of theme
     */
    public String getTheme() {
        return theme;
    }
    
    
    /**
     * Set the value of theme
     *
     * @param theme new value of theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    
    /**
     * This method returns true if the back ground repeats.
     * 
     * @return TRUE if repeat is set.
     */
    public boolean isRepeat() {
        return repeat;
    }
    
    
    /**
     * This method sets the repeat value.
     * 
     * @param repeat True if the image is to be repeated false if not.
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    
    
    
}
