/*
 * RSSReaderClient: The RSS Reader.
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
 * RSSXMLConfigHelper.java
 */


package com.rift.coad.daemon.rss;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

// log4j imports
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This object is responsible for parsing the RSS initial configuration.
 * 
 * @author brett chaldecott
 */
public class RSSXMLConfigHelper {
    /**
     * This class handles the parsing of the menu xml.
     */
    public class FeedsHandler extends DefaultHandler {
        
        // class constants
        private final static String FEEDS = "Feeds";
        private final static String FEED = "Feed";
        private final static String NAME = "name";
        private final static String APPLICATION = "application";
        private final static String URL = "url";
        
        
        /**
         * The default constructor of the feed handler.
         */
        public FeedsHandler() {
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
            if (qName.equalsIgnoreCase(FEEDS)) {
                return;
            } else if (qName.equalsIgnoreCase(FEED)) {
                String name = (String)attributes.getValue(NAME);
                feedMap.put(name,new Feed(name,(String)attributes.getValue(APPLICATION),
                        (String)attributes.getValue(URL)));
            }
        }
    }
    
    // private member variables
    private static Logger log = Logger.getLogger(RSSXMLConfigHelper.class);
    private Map<String,Feed> feedMap = new HashMap<String,Feed>();
    
    
    /**
     * The constructor of the RSS xml config helper.
     * 
     * @param filename The path to the XML configuration file.
     */
    public RSSXMLConfigHelper(String filename) throws RSSClientException {
        try {
            FeedsHandler handler = new FeedsHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File menuFile = new File(filename);
            InputSource source = new InputSource( 
                    new FileReader(menuFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            log.error("Failed to load the xml RSS configuration : " + ex.getMessage(),
                    ex);
            throw new RSSClientException(
                    "Failed to load the xml RSS configuration : " + ex.getMessage(),
                    ex);
        }
    }

    /**
     * This method returns the feed map information
     * 
     * @return
     */
    public Map<String, Feed> getFeedMap() {
        return feedMap;
    }
    
    
    
}
