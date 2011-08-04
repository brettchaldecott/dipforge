/*
 * ProjectClient: The project client interface.
 * Copyright (C) 2011  Rift IT Contracting
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
 * FileDTO.java
 */

package com.rift.dipforge.project.type;

import com.rift.coad.type.dto.RDFDataType;
import com.rift.coad.type.dto.ResourceDefinition;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The XML type info parser
 *
 * @author brett chaldecott
 */
public class XMLTypeInfoParser {

    // class static variables
    private static Logger log = Logger.getLogger(XMLTypeInfoParser.class);

    /**
     * The xml type info
     */
    public class XMLTypeInfoHandler extends DefaultHandler {

        // the list of types
        public final static String TYPES = "types";
        public final static String NAMESPACE = "namespace";
        public final static String TYPE = "type";
        public final static String PROPERTY = "property";
        public final static String LINK = "link";

        // list of common keys
        public final static String PROJECT = "project";
        public final static String NAME = "name";
        public final static String VALUE = "value";
        public final static String DATATYPE = "datatype";


        // boolean flags
        private boolean inTypes = false;
        private boolean inType = false;

        private ResourceDefinition resource = null;

        /**
         * The type info handler
         */
        public XMLTypeInfoHandler() {
            
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
                if (qName.compareToIgnoreCase(TYPES) == 0) {
                    inTypes = true;
                    project = (String)attributes.getValue(PROJECT);
                } else if (inTypes && qName.compareToIgnoreCase(NAMESPACE) == 0) {
                    namespace = (String)attributes.getValue(VALUE);
                } else if (inTypes && qName.compareToIgnoreCase(TYPE) == 0) {
                    inType = true;
                    resource = new ResourceDefinition(namespace, (String)attributes.getValue(NAME));
                    types.add(resource);
                } else if (inType && qName.compareToIgnoreCase(PROPERTY) == 0) {
                    String name = (String)attributes.getValue(NAME);
                    RDFDataType types = new RDFDataType(namespace,
                            (String)attributes.getValue(NAME),
                            (String)attributes.getValue(DATATYPE));
                    resource.addProperty(name, types);
                }
                
            } catch (Exception ex) {
                log.error("Failed to handle the start element : " + ex.getMessage(),ex);
                throw new SAXException(
                        "Failed to handle the start element : " + ex.getMessage(),ex);
            }
        }


        /**
         * This method sets the end element.
         *
         * @param uri The string uri.
         * @param localName The local name
         * @param qName The qName
         * @throws SAXException
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(TYPES) == 0) {
                    inTypes = false;
                } else if (inType && qName.compareToIgnoreCase(TYPE) == 0) {
                    inType = false;
                    resource = null;
                }

            } catch (Exception ex) {
                log.error("Failed to handle the end element : " + ex.getMessage(),ex);
                throw new SAXException(
                        "Failed to handle the end element : " + ex.getMessage(),ex);
            }
        }

    }

    // private member variables
    private String project;
    private String namespace;
    private String xml;
    private List<ResourceDefinition> types = new ArrayList<ResourceDefinition>();


    /**
     * This constructor xml type info parser.
     */
    public XMLTypeInfoParser(String xml) throws TypeException {
        this.xml = xml;
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
            parser.parse(in, new XMLTypeInfoHandler());
            in.close();
        } catch (Exception ex) {
            log.error("Failed to parser the xml : " + ex.getMessage(),ex);
            throw new TypeException
                    ("Failed to parser the xml : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns the namespace.
     * 
     * @return The string containing the namespace.
     */
    public String getNamespace() {
        return namespace;
    }


    /**
     * This method returns the project name.
     *
     * @return The string containing the project information.
     */
    public String getProject() {
        return project;
    }




    /**
     * This method is called to retrieve the list of types once the processing 
     * has been performed on it.
     * 
     * @return The list of resource definitions
     */
    public List<ResourceDefinition> getTypes() {
        return types;
    }



}
