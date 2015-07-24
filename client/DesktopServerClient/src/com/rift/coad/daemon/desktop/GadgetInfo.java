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
 * GadgetInfo.java
 */// the package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;

/**
 * This object represents application to be embedded in the desktop.
 * 
 * @author brett chaldecott
 */
public class GadgetInfo implements Serializable {

    /**
     * This class contains the embedded information. 
     */
    public static class EmbeddedApp {
        // private member variables
        private String url = null;
        private int height;
        private int width;
        private boolean popup = false;
        private String popupURL = null;
        private int popupHeight = 0;
        private int popupWidth = 0;

        /**
         * The constructor that sets the embedded url.
         * 
         * @param url The url of the embedded app.
         */
        public EmbeddedApp(String url, int height, int width) {
            this.url = url;
            this.height = height;
            this.width = width;
            this.popup = false;
        }

        /**
         * The constructor that sets up the complete embedded app
         * @param url The url of the embedded application.
         * @param width The width of the gadget.
         * @param height The height of the gadget.
         * @param popup The popup for the embedded application.
         * @param popupURL The url of the pop up.
         * @param popupHeight The height of the popup
         * @param popupWidth The 
         */
        public EmbeddedApp(String url, int width, int height, boolean popup, String popupURL, int popupHeight, int popupWidth) {
            this.popup = popup;
            this.url = url;
            this.width = width;
            this.height = height;
            this.popupURL = popupURL;
            this.popupHeight = popupHeight;
            this.popupWidth = popupWidth;
        }

        /**
         * Get the value of popupWidth
         *
         * @return the value of popupWidth
         */
        public int getPopupWidth() {
            return popupWidth;
        }

        /**
         * Set the value of popupWidth
         *
         * @param popupWidth new value of popupWidth
         */
        public void setPopupWidth(int popupWidth) {
            this.popupWidth = popupWidth;
        }

        /**
         * Get the value of popup
         *
         * @return the value of popup
         */
        public boolean isPopup() {
            return popup;
        }

        /**
         * Set the value of popup
         *
         * @param popup new value of popup
         */
        public void setPopup(boolean popup) {
            this.popup = popup;
        }

        /**
         * Get the value of popupHeight
         *
         * @return the value of popupHeight
         */
        public int getPopupHeight() {
            return popupHeight;
        }

        /**
         * Set the value of popupHeight
         *
         * @param popupHeight new value of popupHeight
         */
        public void setPopupHeight(int popupHeight) {
            this.popupHeight = popupHeight;
        }

        /**
         * Get the value of popupURL
         *
         * @return the value of popupURL
         */
        public String getPopupURL() {
            return popupURL;
        }

        /**
         * Set the value of popupURL
         *
         * @param popupURL new value of popupURL
         */
        public void setPopupURL(String popupURL) {
            this.popupURL = popupURL;
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
         * The height of the gadegt.
         * 
         * @return The height of the gadget
         */
        public int getHeight() {
            return height;
        }
        
        
        /**
         * This method sets the height of the gadet.
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
        }
        
        
        /**
         * This method sets the height of the embedded gadget.
         * 
         * @return The int containing the height of the embedded gadget.
         */
        public int getWidth() {
            return width;
        }
        
        
        /**
         * Sets the width of the embedded gadget.
         * @param width The integer containing the width of the embedded gadget.
         */
        public void setWidth(int width) {
            this.width = width;
        }
        
        
        
    }
    
    // private member variables
    private String identifier;
    private EmbeddedApp app = null;
    private String name;
    private String url;
    private int width;
    private int height;

    /**
     *  The constructor of the application information object.
     * 
     * @param identifier The identifier of this object.
     * @param name The name of the gadget to launch.
     * @param url The url for this application.
     * @param width The width of the popup.
     * @param height The height of the popup.
     */
    public GadgetInfo(String identifier, String name,  String url, int width, int height) {
        this.identifier = identifier;
        this.name = name;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    /**
     * This consructs an object using the supplied information.
     * 
     * @param identifier The unique identifier for the app.
     * @param app The embedded app information.
     * @param name The name of the application.
     * @param url The url for the application to launch.
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public GadgetInfo(String identifier, EmbeddedApp app, String name, String url, int width, int height) {
        this.identifier = identifier;
        this.app = app;
        this.name = name;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    /**
     * This method returns the reference to the embedded app.
     * 
     * @return The reference to the embedded app.
     */
    public EmbeddedApp getApp() {
        return app;
    }
    
    
    /**
     * This method sets the embedded app reference.
     * 
     * @param app The embedded app information.
     */
    public void setApp(EmbeddedApp app) {
        this.app = app;
    }
    
    
    /**
     * This method retrieves the height of the application initial window.
     * 
     * @return The height of the initial window.
     */
    public int getHeight() {
        return height;
    }
    
    
    /**
     * This method sets the height of the application initial window.
     *
     * @param height The height of the window.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    
    /**
     * This method returns the string identifier for this gadget.
     * 
     * @return The string containing the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
    
    
    /**
     * This method sets the string identifier for this object.
     * 
     * @param identifier The string identifier for this object.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    
    /**
     * This method returns the url for the target gadget application.
     * 
     * @return The string containig the target url.
     */
    public String getUrl() {
        return url;
    }

    
    /**
     * This method sets the target url for the application.
     * 
     * @param url The url to set for the application.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    /**
     * This method retrieves the width of the window to be launched.
     * 
     * @return The int containing the width of the target window.
     */
    public int getWidth() {
        return width;
    }
    
    
    /**
     * This method sets the width of the window to be launched.
     * 
     * @param width The int containing the width of the window.
     */
    public void setWidth(int width) {
        this.width = width;
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
}
