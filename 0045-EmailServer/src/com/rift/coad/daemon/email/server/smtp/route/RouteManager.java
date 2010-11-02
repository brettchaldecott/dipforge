/*
 * Email Server: The email server.
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
 * RouteManager.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.route;

// java imports
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

// log4j imports
import org.apache.log4j.Logger;

// email server imports
import com.rift.coad.daemon.email.smtp.Header;


/**
 * This object manages the route entries.
 *
 * @author brett chaldecott
 */
public class RouteManager {
    
    
    /**
     * This class contains the XML logic that will be added to a route entry.
     */
    public class XMLLogic {
        // private member variables
        private Set types = new HashSet();
        private List headers = new ArrayList();
        
        
        /**
         * The constructor of the XML Logic object.
         */
        public XMLLogic() {
            
        }
        
        
        /**
         * This method adds a type to the list of types.
         */
        public void addType(Integer type) {
            types.add(type);
        }
        
        
        /**
         * This method returns the list of types.
         */
        public Set getTypes() {
            return types;
        }
        
        
        /**
         * This method adds the list of headers.
         *
         * @param key The key to set the header.
         * @param value The value for the header.
         */
        public void addHeader(String key, String value) {
            this.headers.add(new Header(key,value));
        }
        
        
        /**
         * This method returns the list of headers.
         *
         * @return The list of headers.
         */
        public List getHeaders() {
            return headers;
        }
    }
    
    /**
     * The inner class responsible for processing the XML route information.
     */
    public class XMLRouteHandler extends DefaultHandler {
        
        // class constants
        private final static String EMAIL_ROUTE = "EmailRoute";
        private final static String ROUTE_ENTRY = "RouteEntry";
        private final static String ROUTE_NAME = "name";
        private final static String ROUTE_JNDI = "jndi";
        private final static String ROUTE_LOGIC = "if";
        private final static String MESSAGE_TYPE = "messagetype";
        private final static String MESSAGE_VALUE = "value";
        private final static String HEADER = "header";
        private final static String HEADER_KEY = "key";
        private final static String HEADER_VALUE = "value";
        
        // private member variables.
        private Stack stack = new Stack();
        private RouteEntry currentEntry = null;
        private XMLLogic entryLogic = null;
        private boolean inEmailRoute = false;
        
        /**
         * The constructor of the XML route handler.
         */
        public XMLRouteHandler() {
            
        }
        
        /**
         * Parse the start of an element
         */
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            // handle a package and retrieve the value information
            if (qName.compareToIgnoreCase(EMAIL_ROUTE) == 0) {
                inEmailRoute = true;
            } else if (inEmailRoute && 
                    (qName.compareToIgnoreCase(ROUTE_LOGIC) == 0)) {
                entryLogic = new XMLLogic();
            } else if (inEmailRoute && entryLogic != null &&
                    (qName.compareToIgnoreCase(MESSAGE_TYPE) == 0)) {
                entryLogic.addType(new Integer(
                        attributes.getValue(MESSAGE_VALUE)));
            } else if (inEmailRoute && entryLogic != null &&
                    (qName.compareToIgnoreCase(HEADER) == 0)) {
                entryLogic.addHeader(attributes.getValue(HEADER_KEY),
                        attributes.getValue(HEADER_VALUE));
            } else if (inEmailRoute &&
                    (qName.compareToIgnoreCase(ROUTE_ENTRY) == 0)) {
                RouteEntry entry = new RouteEntry(
                        attributes.getValue(ROUTE_NAME),
                        attributes.getValue(ROUTE_JNDI));
                entries.put(entry.getName(),entry);
                if (entryLogic != null) {
                    entry.setHeaders(entryLogic.getHeaders());
                    entry.setTypes(entryLogic.getTypes());
                }
                entryLogic = null;
                if (currentEntry != null) {
                    currentEntry.addEntry(entry);
                    stack.push(currentEntry);
                }
                currentEntry = entry;
                if (base == null) {
                    base = entry;
                }
            }
        }
        
        
        /**
         * Handle the end of an element
         */
        public void endElement(String uri, String localName, String qName)
        throws SAXException {
            // handle a package and retrieve the value information
            if (qName.compareToIgnoreCase(EMAIL_ROUTE) == 0) {
                inEmailRoute = false;
            } else if (inEmailRoute && 
                    (qName.compareToIgnoreCase(ROUTE_LOGIC) == 0)) {
                entryLogic = null;
            }  else if (inEmailRoute && entryLogic != null &&
                    (qName.compareToIgnoreCase(MESSAGE_TYPE) == 0)) {
                // ignore
            } else if (inEmailRoute && entryLogic != null &&
                    (qName.compareToIgnoreCase(HEADER) == 0)) {
                // ignore
            } else if (inEmailRoute &&
                    (qName.compareToIgnoreCase(ROUTE_ENTRY) == 0)) {
                if (!stack.empty()) {
                    currentEntry = (RouteEntry)stack.pop();
                } else {
                    currentEntry = null;
                }
            }
        }
    }
    
    // private member variables.
    private Logger log = Logger.getLogger(RouteManager.class);
    private RouteEntry base = null;
    private Map entries = new HashMap();
    
    
    /**
     * Creates a new instance of RouteManager
     *
     * @param path The path to the file.
     */
    public RouteManager(String path) throws RouteException {
        try {
            XMLRouteHandler handler = new XMLRouteHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File configFile = new File(path);
            InputSource source = new InputSource( 
                    new FileReader(configFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            log.error("Failed to parse the route file : " + ex.getMessage(),ex);
            throw new RouteException(
                    "Failed to parse route file : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the base of the
     */
    public RouteEntry getBase() {
        return base;
    }
    
    /**
     *
     */
    public RouteEntry getEntry(String entry) {
        return (RouteEntry)entries.get(entry);
    }
    
}
