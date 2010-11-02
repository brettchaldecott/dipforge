/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2007  Rift IT Contracting
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
 * TomcatXMLContext.java
 */

// package path
package com.rift.coad.daemon.tomcat;

// java imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;


/**
 * This class is responsible for reading the tomcat xml context file.
 *
 * @author brett
 */
public class TomcatXMLContext {
    
    /**
     * This class is responsible for handling the parsing of the context.
     */
    public class ContextHandler extends DefaultHandler {
        // private class contants
        private static final String CONTEXT = "Context";
        private static final String PATH = "path";
        
        
        /**
         * The constructor of the context handler
         */
        public ContextHandler() {
            
        }
        
        
        /**
         * Parse the start of an element 
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            // handle a package and retrieve the value information
            if (qName.compareToIgnoreCase(CONTEXT) == 0) {
                context = attributes.getValue(PATH);
            }
        }
        
    }
    // class constants
    private final static String RESOURCE_PATH_VAR = 
            "ContextResourcePath";
    private final static String RESOURCE_PATH = "META-INF/context.xml";
    
    // private member variables
    private String context = null;
    
    
    /** 
     * Creates a new instance of TomcatXMLContext
     *
     * @param loader The reference to the class loader.
     * @exception TomcatException
     */
    public TomcatXMLContext(ClassLoader loader) throws TomcatException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    TomcatXMLContext.class);
            String resource = config.getString(RESOURCE_PATH_VAR,
                    RESOURCE_PATH);
            InputSource source = new InputSource(
                    loader.getResourceAsStream(resource)); 
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ContextHandler handler = new ContextHandler();
            parser.parse(source,handler);
            
        } catch (Exception ex) {
            throw new TomcatException("Failed to parse the tomcat xml file :" +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the context string for the tomcat xml file.
     */
    public String getContext() {
        return context;
    }
}
