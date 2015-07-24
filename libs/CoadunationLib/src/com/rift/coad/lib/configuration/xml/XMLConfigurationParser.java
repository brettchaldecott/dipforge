/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * XMLConfigurationException.java
 *
 * XMLConfigurationParser.java
 *
 * The parser for the XML configuration file.
 */

// the package definition
package com.rift.coad.lib.configuration.xml;

// java imports
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;


/**
 * This class is responsible for reading in the configuration information.
 *
 * @author Brett Chaldecott
 */
public class XMLConfigurationParser {
    
    // the classes static member variables
    private static final String GENERAL_SECTION = "GENERAL";
    private static final String XML_CONFIG_PATH = "xml.config.path";
    
    /**
     * This class stores the configuration information for a 
     */
    public class XMLConfigInfo {
        
        // the classes private member variables
        private String className = null;
        private Map configInfo = null;
        
        
        /**
         * The constructor of the xml configuration information
         */
        public XMLConfigInfo(String className) { 
            this.className = className;
            configInfo = new HashMap();
        }
        
        
        /**
         * The method that returns the name of the class.
         *
         * @return The name of the class for which the configuration is stored.
         */
        public String getClassName() {
            return className;
        }
        
        
        /**
         * This method addes an entry to the config information map.
         *
         * @param entry The entry to add to the map.
         */
        public void addEntry(XMLConfigurationEntry entry) {
            configInfo.put(entry.getKey(),entry);
        }
        
        
        /**
         * This method returns the configuration information map.
         *
         * @return The map containing the configuration information.
         */
        public Map getConfigInfo() {
            return configInfo;
        }
    }
    
    /**
     * The inner class responsible for handling the contents of the Coadunation
     * xml source document.
     */
    public class XMLConfigurationHandler extends DefaultHandler {
        
        // the classes static constant
        private static final String CONFIGURATION = "configuration";
        private static final String OBJECT_SECTION = "object";
        private static final String OBJECT_SECTION_NAME = "name";
        private static final String OBJECT_ENTRY = "entry";
        private static final String OBJECT_ENTRY_KEY = "key";
        private static final String OBJECT_ENTRY_TYPE = "type";
        
        // in section section variables
        private boolean inConfiguration = false;
        private boolean inObjectSection = false;
        private boolean inObjectEntry = false ;
        
        // data variables
        private String inData = null;
        private XMLConfigInfo sectionConfig = null;
        private XMLConfigurationEntry entry = null;
        
        // the classes private member variables
        private Map configSections = null;
        
        /**
         * The constructor of the xml configuration handler.
         *
         * @param configSections The configurtion sections
         */
        public XMLConfigurationHandler(Map configSections) {
            this.configSections = configSections;
        }
        
        
        /**
         * Parse the start of an element 
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(CONFIGURATION) == 0) {
                    inConfiguration = true;
                } else if (inConfiguration &&
                        qName.compareToIgnoreCase(OBJECT_SECTION) == 0) {
                    String name = (String)attributes.getValue(OBJECT_SECTION_NAME);
                    if (name == null) {
                        name = XMLConfigurationParser.GENERAL_SECTION;
                    }
                    sectionConfig = new XMLConfigInfo(name);
                    inObjectSection = true;
                } else if (inConfiguration && inObjectSection &&
                        qName.compareToIgnoreCase(OBJECT_ENTRY) == 0) {
                    String name = (String)attributes.getValue(OBJECT_ENTRY_KEY);
                    String type = (String)attributes.getValue(OBJECT_ENTRY_TYPE);
                    entry = new XMLConfigurationEntry();
                    entry.setKey(name);
                    entry.setType(new XMLConfigurationType(type));
                    inData = "";
                    inObjectEntry = true;
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to handle the start element : " + 
                        ex.getMessage());
            }
        }
        
        /**
         * Read in the characters
         */
        public void characters(char[] ch, int start, int length) {
            if (inObjectEntry) {
                inData += new String(ch,start,length);
            }
        }
        
        /**
         * Handle the end of an element
         */
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(CONFIGURATION) == 0) {
                    inConfiguration = false;
                } else if (inConfiguration &&
                        qName.compareToIgnoreCase(OBJECT_SECTION) == 0) {
                    configSections.put(sectionConfig.getClassName(),sectionConfig);
                    inObjectSection = false;
                } else if (inConfiguration && inObjectSection &&
                        qName.compareToIgnoreCase(OBJECT_ENTRY) == 0) {
                    entry.setValueFromString(inData.trim());
                    if (entry.isIntiailized() == false) {
                        throw new SAXException(
                                "An entry has not been initialized");
                    }
                    sectionConfig.addEntry(entry);
                    entry = null;
                    inData = "";
                    inObjectEntry = false;
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to handle the end element : " + 
                        ex.getMessage(),ex);
            }
        }
        
    }
    
    // classes private member variables
    private Map configSections = null;
    private XMLConfigurationHandler handler = null;
    private long lastModifiedAtRead = 0;
    private File configFile = null;
    
    /** 
     * Creates a new instance of XMLConfigurationParser 
     */
    public XMLConfigurationParser() throws XMLConfigurationException {
        try {
            configSections = new HashMap();
            handler = new XMLConfigurationHandler(configSections);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            configFile = new File(
                    System.getProperty(XML_CONFIG_PATH));
            InputSource source = new InputSource( 
                    new FileReader(configFile)); 
            parser.parse(source,handler);
            lastModifiedAtRead = configFile.lastModified();
        } catch (Exception ex) {
            throw new XMLConfigurationException(
                    "Failed load the configuration : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method checks to see if the configuration file has been modified
     * since it was last read and returns true if this is the case.
     *
     * @return TRUE if modified, FALSE if not.
     */
    public boolean modified() {
        if (lastModifiedAtRead != configFile.lastModified()) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method returns a reference to the configuration class object.
     *
     * @return The reference to the xml configuration object
     * @param classRef The reference to the class.
     */
    public XMLConfiguration getConfig(Class classRef) {
        XMLConfiguration configuration = new XMLConfiguration(
                classRef.getName());
        
        copyConfig((XMLConfigInfo)configSections.get(GENERAL_SECTION),
                configuration);
        copyConfig((XMLConfigInfo)configSections.get(
                classRef.getName()),configuration);
        
        return configuration;
    }
    
    
    /**
     * This method copies the xml configuration.
     *
     * @param info The xml configuration information to copy.
     * @param config The configuration class to copy the information into.
     */
    private void copyConfig(XMLConfigInfo info,XMLConfiguration config) {
        if (info == null) {
            return;
        }
        Map entries = info.getConfigInfo();
        for (Iterator iter = entries.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            config.addConfigurationEntry(
                    (XMLConfigurationEntry)entries.get(key));
        }
    }
}
