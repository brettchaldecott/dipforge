/*
 * ProjectUtils: The utils used by the project system
 * Copyright (C) 2011  2015 Burntjam
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
 * XMLMethodMappingParser.java
 */
package com.rift.dipforge.project.method;

import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.rdf.types.mapping.ParameterMapping;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * This object is responsible for parsing the xml containing the method mapping
 * information.
 * 
 * @author brett chaldecott
 */
public class XMLMethodMappingParser {
    
    // class static variables
    private static Logger log = Logger.getLogger(XMLMethodMappingParser.class);
    
    /**
     * The xml type info
     */
    public class XMLMethodMappingHandler extends DefaultHandler {
        
        // class constants
        private static final String MAPPING_KEY = "mapping";
        private static final String JNDI_KEY = "jndi";
        private static final String PROJECT_KEY = "project";
        private static final String METHOD_KEY = "method";
        private static final String PARAMETER_KEY = "parameter";
        
        public final static String NAME = "name";
        public final static String SERVICE = "service";
        public final static String CLASS = "class";
        public final static String TYPE = "type";
        
        
        
        // class member variables
        private boolean inMapping = false;
        private boolean inJNDI = false;
        private boolean inProject = false;
        private String projectName = "";
        private boolean inMethod = false;
        private String jndi = null;
        private String service = null;
        private MethodMapping method = null;
        private List<MethodMapping> methods = null;
        
        
        
        /**
         * The default constructor
         */
        public XMLMethodMappingHandler() {
            
        }
        
        
        /**
         * This method is called at the start of an element.
         *
         * @param uri The uri.
         * @param localName The local name.
         * @param qName The qName
         * @param attributes The list of attributes
         * @throws SAXException
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(MAPPING_KEY) == 0) {
                    inMapping = true;
                } else if (inMapping && qName.compareToIgnoreCase(JNDI_KEY) == 0) {
                    inJNDI = true;
                    jndi = (String)attributes.getValue(NAME);
                    service = attributes.getValue(SERVICE);
                } else if (inJNDI && qName.compareToIgnoreCase(PROJECT_KEY) == 0) {
                    inProject = true;
                    projectName = (String)attributes.getValue(NAME);
                    methods = new ArrayList<MethodMapping>();
                } else if (inProject && qName.compareToIgnoreCase(METHOD_KEY) == 0) {
                    method = new MethodMapping(this.jndi, this.service, this.projectName,
                            (String)attributes.getValue(CLASS),
                            (String)attributes.getValue(NAME),
                            (String)attributes.getValue(TYPE));
                    
                } else if (method != null &&
                        qName.compareToIgnoreCase(PARAMETER_KEY) == 0) {
                    method.getParameters().add(new ParameterMapping(
                            method.getId(),(String)attributes.getValue(NAME),
                            (String)attributes.getValue(TYPE)));
                }
                
            } catch (Exception ex) {
                log.error("Failed to handle the start element : " + ex.getMessage(),ex);
                throw new SAXException(
                        "Failed to handle the start element : " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method is called to mark an end element
         * 
         * @param uri The end of the element uri.
         * @param localName The local name.
         * @param qName The qName identifier.
         * @throws SAXException 
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (inMapping && qName.compareToIgnoreCase(MAPPING_KEY) == 0) {
                    inMapping = false;
                } else if (inMapping && inJNDI &&
                        qName.compareToIgnoreCase(JNDI_KEY) == 0) {
                    inJNDI = false;
                    jndi = null;
                } else if (inJNDI && inProject &&
                        qName.compareToIgnoreCase(PROJECT_KEY) == 0) {
                    if (mappings.containsKey(projectName)) {
                        mappings.get(projectName).addAll(methods);
                    } else {
                        mappings.put(projectName, methods);
                    }
                    methods = null;
                    inProject = false;
                    projectName = null;
                } else if (inProject && method != null && 
                        qName.compareToIgnoreCase(METHOD_KEY) == 0) {
                    methods.add(method);
                    method = null;
                }
                
            } catch (Exception ex) {
                log.error("Failed to handle the start element : " + ex.getMessage(),ex);
                throw new SAXException(
                        "Failed to handle the start element : " + ex.getMessage(),ex);
            }
        }
    }
    
    
    // private member variables
    private String xml;
    private Map<String,List<MethodMapping>> mappings = 
            new HashMap<String,List<MethodMapping>>();
    
    /**
     * This constructor xml type info parser.
     */
    public XMLMethodMappingParser(String xml) throws XMLMethodMappingException {
        this.xml = xml;
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
            parser.parse(in, new XMLMethodMappingHandler());
            in.close();
        } catch (Exception ex) {
            log.error("Failed to parser the xml : " + ex.getMessage(),ex);
            throw new XMLMethodMappingException
                    ("Failed to parser the xml : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the list of jndi identifiers.
     * 
     * @return The jndi reference. 
     */
    public List<String> getProjectList() {
        List<String> result = new ArrayList<String>();
        result.addAll(mappings.keySet());
        return result;
    }
    
    
    /**
     * This method returns a method mapping list for the specified JNDI identifier.
     * @param jndi The jndi identifier.
     * @return  The list of method mappings for the identifier.
     */
    public List<MethodMapping> getMethodMapping(String project) {
        return mappings.get(project);
    }
}
