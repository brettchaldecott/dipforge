/*
 * DesktopServer: The server responsible for managing the general desktop resources.
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
 * MenuParser.java
 */

// package path
package com.rift.coad.daemon.desktop.master;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import java.util.Stack;

/**
 * This object is responsible for processing the menu requests.
 * 
 * @author brett chaldecott
 */
public class MenuParser {

    /**
     *  This class is responsible for representing a launch item.
     */
    public class Launcher {
        // private member variables
        private String identifier = null;
        private String name = null;
        private String title = null;
        private String mouseOver = null;
        private String url = null;
        private int width = 0;
        private int height = 0;
        private String role = null;

        /**
         * The constructor that sets all the parameters
         * 
         * @param identifier The string based identifier of this object.
         * @param name The name of this object.
         * @param title The title of this object.
         * @param mouseOver The mouse over value for this object.
         * @param url The url for this object.
         * @param width The width for this object.
         * @param height The height for this object.
         * @param role The role for this object.
         */
        public Launcher(String identifier, String name, String title, 
                String mouseOver, String url, int width, int height, String role) {
            this.identifier = identifier;
            this.name = name;
            this.title = title;
            this.mouseOver = mouseOver;
            this.url = url;
            this.width = width;
            this.height = height;
            this.role = role;
        }
        
        
        /**
         * The height of the object.
         * @return The height of this object.
         */
        public int getHeight() {
            return height;
        }

        /**
         * Set the height of the object.
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
        }
        
        /**
         * This method retrieves the identifier.
         * @return The string containing the identifier.
         */
        public String getIdentifier() {
            return identifier;
        }
        
        
        /**
         * This methd sets the identifier value 
         * @param identifier The string containing the new identifier value.
         */
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
        
        /**
         * This method retrieves the mouse over value.
         * @return The stringing containing the mouse over value.
         */
        public String getMouseOver() {
            return mouseOver;
        }
        
        /**
         * This method sets the new mouse over value.
         * @param mouseOver The new mouse over value.
         */
        public void setMouseOver(String mouseOver) {
            this.mouseOver = mouseOver;
        }
        
        
        /**
         * This method returns the name of the menu
         * 
         * @return The string containing the name of this entry.
         */
        public String getName() {
            return name;
        }
        
        
        /**
         * This method sets the name of the launcher.
         * 
         * @param name The string containing the name of the item.
         */
        public void setName(String name) {
            this.name = name;
        }
        
        
        /**
         * This method returns the title of the menu parser.
         * 
         * @return The string containing the title for the launched item.
         */
        public String getTitle() {
            return title;
        }
        
        
        /**
         * This method sets the title for the launched item.
         * 
         * @param title The string containing the title of the launched item.
         */
        public void setTitle(String title) {
            this.title = title;
        }
        
        
        /**
         * This method returns the url for the item to launch.
         * 
         * @return The string containing the url of the item to launch.
         */
        public String getUrl() {
            return url;
        }
        
        
        /**
         * This method sets the url for the item to launch.
         * 
         * @param url This method sets the url for the item.
         */
        public void setUrl(String url) {
            this.url = url;
        }
        
        
        /**
         * This method returns the width of the item being launched in pixels.
         * 
         * @return The width of the window to launch.
         */
        public int getWidth() {
            return width;
        }
        
        
        /**
         * This method sets the width of the window to launch.
         * 
         * @param width The integer containing the width of the window to launch.
         */
        public void setWidth(int width) {
            this.width = width;
        }
        
        
        /**
         * This method returns the role that is allowed to view this item.
         * 
         * @return The role that is allowed to view this item.
         */
        public String getRole() {
            return role;
        }
        
        
        /**
         * This method sets the role that is allowed to view this item.
         * 
         * @param role The role that is allowed to view this item.
         */
        public void setRole(String role) {
            this.role = role;
        }
        
        
    }

    /**
     * A menu object.
     */
    public class Menu {
        // private member variables
        private String identifier;
        private List items = new ArrayList();
        private String role = null;

        /**
         * The constructor responsible for creating a new menu object.
         * 
         * @param identifier The string based identifier for he menu
         */
        private Menu(String identifier, String role) {
            this.identifier = identifier;
            this.role = role;
        }

        public String getIdentifier() {
            return identifier;
        }

        /**
         * This method retrieves the list of items.
         *  
         * @return The list of items.
         */
        public List getItems() {
            return items;
        }

        /**
         * The setter for the list of items.
         * @param items The list of items.
         */
        public void setItems(List items) {
            this.items = items;
        }

        /**
         * This method addes a new item to the menu
         * @param item The new item.
         */
        public void addItem(Object item) {
            this.items.add(item);
        }
        
        /**
         * This method retrieves the role.
         * 
         * @return The string containing the role.
         */
        public String getRole() {
            return role;
        }
        
        /**
         * This method sets the role.
         * 
         * @param role The string containing the role
         */
        public void setRole(String role) {
            this.role = role;
        }
        
        
    }
    
    
    /**
     * This class handles the parsing of the menu xml.
     */
    public class MenuHandler extends DefaultHandler {
        
        // class constants
        private final static String MENUS = "menus";
        private final static String MENU = "menu";
        private final static String LAUNCHER = "launcher";
        private final static String DIV = "div";
        private final static String IDENTIFIER = "identifier";
        private final static String ROLE = "role";
        
        // private member variables
        private Stack menuStack = new Stack();
        private Menu currentMenu = null;
        
        /**
         * The default constructor of the menu handler
         */
        public MenuHandler() {
        }
        
        
        /**
         * This method is responsible for processing the start element operation.
         * 
         * @param uri The uri to process the request for.
         * @param localName The local the request is being processed for.
         * @param qName The qname
         * @param attributes The attributes.
         * @throws org.xml.sax.SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase(MENUS)) {
                return;
            } else if (qName.equalsIgnoreCase(MENU)) {
                Menu menu = new Menu((String)attributes.getValue(IDENTIFIER),
                        (String)attributes.getValue("role"));
                if (menuStack.size() == 0) {
                    menus.add(menu);
                } else {
                    currentMenu.addItem(menu);
                }
                currentMenu = menu;
                menuStack.push(menu);
            } else if (qName.equalsIgnoreCase(LAUNCHER)) {
                currentMenu.addItem(new Launcher((String)attributes.getValue(IDENTIFIER), 
                        (String)attributes.getValue("name"),
                        (String)attributes.getValue("title"), 
                        (String)attributes.getValue("mouseOver"),
                        (String)attributes.getValue("url"), 
                        Integer.parseInt((String)attributes.getValue("width")),
                        Integer.parseInt((String)attributes.getValue("height")),
                        (String)attributes.getValue("role")));
            } else if (qName.equalsIgnoreCase(DIV)) {
                currentMenu.addItem(null);
            }
        }
        
        
        /**
         * Handle the end of an element
         */
        @Override
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            if (qName.equalsIgnoreCase(MENUS)) {
                return;
            } else if (qName.equalsIgnoreCase(MENU)) {
                menuStack.pop();
                if (menuStack.size() > 0) {
                    currentMenu = (Menu)menuStack.peek();
                } else {
                    currentMenu = null;
                }
            }
        }
    }
    
    // private member variable
    private List menus = new ArrayList();
    
    /**
     * The constructor of the menu parser
     * 
     * @param file The string containing the path to the menu xml file.
     * @exception MasterBarException
     */
    public MenuParser(String file) throws MasterBarException {
        try {
            MenuHandler handler = new MenuHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File menuFile = new File(file);
            InputSource source = new InputSource( 
                    new FileReader(menuFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new MasterBarException(
                    "Failed load the menu information : " + ex.getMessage(),
                    ex);
        }
    }
    
    /**
     * This method returns the parsed menus.
     * @return The parsed menu objects.
     */
    public List getMenus() {
        return menus;
    }
    
    
}

    