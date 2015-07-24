/*
 * ProjectClient: The project client interface.
 * Copyright (C) 2012  2015 Burntjam
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
 * XMLActionInfoParser.java
 */
package com.rift.dipforge.project.action;

// imports
import com.rift.coad.change.ActionInfo;
import com.rift.dipforge.project.type.TypeException;
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
 * This object is responsible for parsing the action information.
 * 
 * @author brett chaldecott
 */
public class XMLActionInfoParser {
    
    // class static variables
    private static Logger log = Logger.getLogger(XMLActionInfoParser.class);
    
    /**
     * This xml action information handler
     */
    public class XMLActionInfoHandler extends DefaultHandler {
        
        // private final
        private final static String ACTIONS = "actions";
        private final static String PROJECT = "project";
        private final static String ACTION = "action";
        private final static String NAME = "name";
        private final static String TYPE = "type";
        private final static String FILE = "file";
        private final static String ROLE = "role";
        
        // private member variables
        private boolean inActions = false;
        
        
        /**
         * The type info handler
         */
        public XMLActionInfoHandler() {
            
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
                if (qName.compareToIgnoreCase(ACTIONS) == 0) {
                    inActions = true;
                    project = (String)attributes.getValue(PROJECT);
                } else if (inActions && qName.compareToIgnoreCase(ACTION) == 0) {
                    if (attributes.getValue(NAME) == null || 
                            (String)attributes.getValue(TYPE) == null ||
                            (String)attributes.getValue(FILE) == null ||
                            (String)attributes.getValue(ROLE) == null) {
                        throw new SAXException("Must provide [name,type,file,role]");
                    }
                    actions.add(new ActionInfo(
                            (String)attributes.getValue(NAME),
                            project,
                            (String)attributes.getValue(TYPE),
                            (String)attributes.getValue(FILE),
                            (String)attributes.getValue(ROLE)));
                }
            } catch (SAXException ex) {
                throw ex;
            } catch (Exception ex) {
                log.error("Failed to process the start element : " 
                        + ex.getMessage(),ex);
                throw new SAXException("Failed to process the start element : " 
                        + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * The end element.
         * 
         * @param uri The uri information.
         * @param localName
         * @param qName
         * @throws SAXException 
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(ACTIONS) == 0) {
                    inActions = false;
                }
            } catch (Exception ex) {
                log.error("Failed to process the end element : " 
                        + ex.getMessage(),ex);
                throw new SAXException("Failed to process the end element : " 
                        + ex.getMessage(),ex);
            }
        }
        
    }
    
    
    // private member variables
    private String xml;
    private String project;
    private List<ActionInfo> actions = new ArrayList<ActionInfo>();
    
    
    public XMLActionInfoParser(String xml) throws ActionException {
        this.xml = xml;
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
            parser.parse(in, new XMLActionInfoHandler());
            in.close();
        } catch (Exception ex) {
            log.error("Failed to parser the xml : " + ex.getMessage(),ex);
            throw new ActionException
                    ("Failed to parser the xml : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method returns the list of actions.
     * 
     * @return The list of actions.
     */
    public List<ActionInfo> getActions() {
        return actions;
    }
    
    
    
}
