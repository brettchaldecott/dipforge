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
 * DesktopAppParser.java
 */

package com.rift.coad.daemon.desktop;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This object is responsible for parsing the desktop app list.
 * 
 * @author brett chaldecott
 */
public class DesktopAppParser {
    /**
     * This class represents a desktop.
     */
    public class Desktop {
        // private member variables
        private String name = null;
        private List<String> apps = new ArrayList<String>();

        
        /**
         * The constructor of the desktop
         * 
         * @param name The name of the desktop.
         */
        public Desktop(String name) {
            this.name = name;
        }
        
        /**
         * This method returns the name of the desktop.
         * @return
         */
        public String getName() {
            return name;
        }

        
        /**
         * This method returns the list of apps for this desktop.
         * @return This method returns the list of apps.
         */
        public List<String> getApps() {
            return apps;
        }
        
        
        /**
         * This method adds a new app to the list of apps.
         * 
         * @param name The name of the application to add.
         */
        public void addApp(String name) {
            apps.add(name);
        }
        
        
    }
    
    /**
     * The handler responsible for parsing the desktop app list.
     */
    public class DesktopAppHandler  extends DefaultHandler {
        
        // class constants
        private final static String DESKTOPS = "Desktops";
        private final static String DESKTOP ="Desktop";
        private final static String NAME = "name";
        private final static String APPLICATION = "application";
        
        // private member variables
        private Desktop currentDesktop = null;
        
        /**
         * The default constructor for the desktop handler.
         */
        public DesktopAppHandler() {
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
            if (qName.equalsIgnoreCase(DESKTOPS)) {
                return;
            } else if (qName.equalsIgnoreCase(DESKTOP)) {
                currentDesktop = new Desktop(((String)attributes.getValue(NAME)).trim());
            } else if (qName.equalsIgnoreCase(APPLICATION)) {
                currentDesktop.addApp(((String)attributes.getValue(NAME)).trim());
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
            if (qName.equalsIgnoreCase(DESKTOP)) {
                desktops.put(currentDesktop.getName(), currentDesktop);
                currentDesktop = null;
            }
        }
    }
    
    
    // private member variables
    private String filename = null;
    private long touchTime = 0;
    private Map<String,Desktop> desktops = new HashMap<String,Desktop>();

    
    /**
     * The constructor o the app parser
     * 
     * @param fileName The name of the file to parse.
     * @throws DesktopException
     */
    public DesktopAppParser(String filename) throws DesktopException {
        this.filename = filename;
        parseFile();
    }
    
    /**
     * This method returns the desktops keyed on string.
     * 
     * @return The map containing the desktop information.
     */
    public Map<String, Desktop> getDesktops() throws DesktopException  {
        checkFile();
        return desktops;
    }
    
    
    /**
     * This method checks the status of the file being parsed.
     */
    private void checkFile() throws DesktopException  {
        File file = new File(this.filename);
        if (file.lastModified() != touchTime) {
            parseFile();
        }
    }
    
    /**
     * This method parses the file.
     * 
     * @throws DesktopException
     */
    private void parseFile() throws DesktopException {
        try {
            DesktopAppHandler handler = new DesktopAppHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File appFile = new File(filename);
            InputSource source = new InputSource( 
                    new FileReader(appFile)); 
            parser.parse(source,handler);
            touchTime = appFile.lastModified();
        } catch (Exception ex) {
            throw new DesktopException(
                    "Failed load the desktop application information : " + ex.getMessage(),
                    ex);
        }
    }
}
