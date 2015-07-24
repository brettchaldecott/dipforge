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
 * DesktopParser.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This object is responsible for parsing the desktop xml file.
 * 
 * @author brett chaldecott
 */
public class DesktopParser {
    
    /**
     * The desktop related information gathered from the parsed file.
     */
    public class Desktop {
        // private member variables
        private String name = null;
        private String theme = null;
        private String backgroundImage = null;
        private boolean repeat = false;
        
        /**
         * The constructor responsible for setting the name of the destop
         * 
         * @param name The name of the desktop.
         * @param theme The theme image.
         * @param backgroundImage  The background image.
         */
        public Desktop(String name, String theme, String backgroundImage, boolean repeat) {
            this.name = name;
            this.theme = theme;
            this.backgroundImage = backgroundImage;
            this.repeat = repeat;
        }

        /**
         * The getter for the name of the desktop.
         * 
         * @return The string containing the name of the desktop.
         */
        public String getName() {
            return name;
        }
        
        
        /**
         * This method sets the name of the desktop.
         * 
         * @param name The name of the desktop.
         */
        public void setName(String name) {
            this.name = name;
        }
        
        
        /**
         * This method returns the string containing the theme name.
         * 
         * @return The string containing the theme name.
         */
        public String getTheme() {
            return theme;
        }
        
        
        /**
         * The setter for the theme name
         * 
         * @param theme The new theme name.
         */
        public void setTheme(String theme) {
            this.theme = theme;
        }
        
        
        /**
         * The getter for the backgound image.
         * 
         * @return The url of the backgound image.
         */
        public String getBackgroundImage() {
            return backgroundImage;
        }
        
        
        /**
         * The setterfor the background image.
         * 
         * @param backgroundImage The path to the background image.
         */
        public void setBackgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
        }
        
        /**
         * This method is called to retrieve the repeat flag.
         * 
         * @return TRUE if repeat is set, FALSE if not.
         */
        public boolean isRepeat() {
            return repeat;
        }
        
        
        /**
         * This method sets the repeat value.
         * @param repeat TRUE to repeat, FALSE not to.
         */
        public void setRepeat(boolean repeat) {
            this.repeat = repeat;
        }
        
        
        
    }
    
    /**
     * The handler responsible for parsing the gadget list.
     */
    public class DesktopHandler  extends DefaultHandler {
        
        // private meber variables
        private Desktop currentDesktop = null;
        
        /**
         * The default constructor of the gadget handler
         */
        public DesktopHandler() {
            
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
            if (qName.equalsIgnoreCase("DESKTOPS")) {
                return;
            } else if (qName.equalsIgnoreCase("DESKTOP")) {
                boolean repeatFlag = false;
                String repeat = (String)attributes.getValue("repeat");
                if ((repeat != null) &&  ((repeat.equalsIgnoreCase("true")) || (repeat.equalsIgnoreCase("yes")))) {
                    repeatFlag = true;
                }
                Desktop desktop = new Desktop((String)attributes.getValue("name"),
                        (String)attributes.getValue("theme"),
                        (String)attributes.getValue("background"),
                        repeatFlag);
                currentDesktop = desktop;
                desktops.add(desktop);
            }
        }
        
        
        /**
         * This method is the end element.
         *  
         * @param uri The uri for the end element.
         * @param localName The local name for the end element.
         * @param qName
         * @throws org.xml.sax.SAXException
         */
        @Override
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            
        }
    }
    
    // private member variables
    private List<Desktop> desktops = new ArrayList<Desktop>();
    
    /**
     * The constructor of the desktop parser.
     * 
     * @param file The reference.
     */
    public DesktopParser(String file) throws DesktopException {
        try {
            DesktopHandler handler = new DesktopHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File menuFile = new File(file);
            InputSource source = new InputSource( 
                    new FileReader(menuFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new DesktopException(
                    "Failed load the desktop content : " + ex.getMessage(),
                    ex);
        }
    }
    
    /**
     * This method return the list of desktops.
     * 
     * @return The reference to the list of desktops.
     */
    public List<Desktop> getDesktops() {
        return desktops;
    }
    
    
    
}
