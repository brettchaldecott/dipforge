/*
 * News Feed Server: This is the implementation of the news feed server.
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
 * FeedXMLConfigHelper.java
 */

// package path
package com.rift.coad.daemon.event;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
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
 * The feed xml config helper.
 * 
 * @author brett chaldecott
 */
public class FeedXMLConfigHelper {
    
    /**
     * This object stores the desktop feed information.
     */
    public class Desktop {
        // private member varibles
        private String name = null;
        private List<String> identifiers = new ArrayList<String>();
        /**
         * The constructor of the desktop object.
         */
        public Desktop(String name) {
            this.name = name;
        }

        /**
         * This method returns the name of this object.
         * 
         * @return The string containing the name of this object.
         */
        public String getName() {
            return name;
        }
        
        
        
        /**
         * This method addes a new identifier to the list of identifiers.
         */
        public void addIdentifiers(String identifier) {
            this.identifiers.add(identifier);
        }
        
        /**
         * This method returns the list of identifies.
         * 
         * @return the list of identifiers.
         */
        public List<String> getIdentifiers() {
            return identifiers;
        }
    }
    
    /**
     * This contains the configuration information for a feed.
     */
    public class Feed {
        // the private member variables.
        private String name = null;
        private List<String[]> filters = new ArrayList<String[]>();
        
        /**
         * The constructor of the feed.
         */
        public Feed(String name) {
            this.name = name;
        }
        
        /**
         * This method returns the name of the feed.
         * 
         * @return The string containing the name.
         */
        public String getName() {
            return name;
        }
        
        
        /**
         * This method addes a new filter.
         */
        public void addFilter(String[] values) {
            filters.add(values);
        }
        
        /**
         * This method returns the list of filters.
         * 
         * @return The list of filters.
         */
        public List<String[]> getFilters() {
            return filters;
        }
        
    }
    
    /**
     * This class handles the parsing of the menu xml.
     */
    public class FeedHandler extends DefaultHandler {
        
        // class constants
        private final static String FEED_CONFIG= "FeedConfig";
        private final static String DESKTOP = "Desktop";
        private final static String IDENTIFIER = "identifier";
        private final static String FEED = "Feed";
        private final static String FILTER = "filter";
        
        private final static String NAME = "name";
        private final static String META_DATA = "meta-data";
        
        // local member variables
        private Desktop currentDesktop = null;
        private Feed currentFeed = null;
        
        /**
         * The default constructor of the feed handler
         */
        public FeedHandler() {
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
            if (qName.equalsIgnoreCase(FEED_CONFIG)) {
                return;
            } else if (qName.equalsIgnoreCase(DESKTOP)) {
                currentDesktop = new Desktop((String)attributes.getValue(NAME));
            } else if (qName.equalsIgnoreCase(IDENTIFIER)) {
                currentDesktop.addIdentifiers((String)attributes.getValue(NAME));
            } else if (qName.equalsIgnoreCase(FEED)) {
                currentFeed = new Feed((String)attributes.getValue(NAME));
            } else if (qName.equalsIgnoreCase(FILTER)) {
                currentFeed.addFilter(new String[] {
                        (String)attributes.getValue(META_DATA),
                        (String)attributes.getValue(NAME)});
            }
        }
        
        
        /**
         * Handle the end of an element
         */
        @Override
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            if (qName.equalsIgnoreCase(DESKTOP)) {
                desktops.put(currentDesktop.getName(), currentDesktop);
                currentDesktop = null;
            }else if (qName.equalsIgnoreCase(FEED)) {
                feeds.put(currentFeed.getName(), currentFeed);
            } 
        }
        
    }
    
    // private member variables
    private String fileName = null;
    private long readDate = 0;
    private Map<String,Desktop> desktops = new HashMap<String,Desktop>();
    private Map<String,Feed> feeds = new HashMap<String, Feed>();
    
    /**
     * The constructor of the feed xml config helper.
     */
    public FeedXMLConfigHelper(String fileName) throws EventException {
        this.fileName = fileName;
        parseConfig();
    }
    
    
    /**
     * This method returns the list of feed identifiers.
     * 
     * @return The list of identifiers.
     * @param desktopName  The name of the desktop.
     */
    public List<String> listFeedIdentifiers(String desktopName) throws EventException {
        checkFile();
        return desktops.get(desktopName).getIdentifiers();
    }
    
    
    /**
     * This method return the list of filters for the given feed identifier.
     * 
     * @return The list of filters for the feed identifier.
     * @param feed The string identifier.
     */
    public List<String[]> getFilters(String feed) throws EventException {
        checkFile();
        return this.feeds.get(feed).getFilters();
    }
    
    /**
     * This method is called to check if a file needs parsing.
     */
    private void checkFile() throws EventException {
        File menuFile = new File(fileName);
        if (menuFile.lastModified() != readDate) {
            parseConfig();
        }
    }
    
    /**
     * This method is called to parse te configration file.
     */
    private void parseConfig() throws EventException {
        try {
            FeedHandler handler = new FeedHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File menuFile = new File(fileName);
            readDate = menuFile.lastModified();
            InputSource source = new InputSource( 
                    new FileReader(menuFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new EventException(
                    "Failed load the xml feed configuration : " + ex.getMessage(),
                    ex);
        }
    }
}
