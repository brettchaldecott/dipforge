/*
 * DesktopServer: The server responsible for managing the general desktop resources.
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
 * GadgetParser.java
 */

// package path
package com.rift.coad.daemon.desktop.master;

// java imports
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

// coadunation imports 
import com.rift.coad.daemon.desktop.GadgetInfo;


/**
 * The xml gadget parser.
 * 
 * @author brett chaldecott
 */
public class GadgetParser {
    /**
     * The handler responsible for parsing the gadget list.
     */
    public class GadgetHandler  extends DefaultHandler {
        
        // class constants
        private final static String GADGETS = "gadgets";
        private final static String GADGET = "gadget";
        private final static String APP  = "app";
        
        // private member variable
        private GadgetInfo currentGadget = null;
        
        /**
         * The default constructor of the gadget handler
         */
        public GadgetHandler() {
            
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
            if (qName.equalsIgnoreCase(GADGETS)) {
                return;
            } else if (qName.equalsIgnoreCase(GADGET)) {
                currentGadget = new GadgetInfo(
                        attributes.getValue("identifier"), 
                        attributes.getValue("name"),  
                        attributes.getValue("url"), 
                        Integer.parseInt(attributes.getValue("width")), 
                        Integer.parseInt(attributes.getValue("height")));
                gadgets.add(currentGadget);
            } else if (currentGadget != null && qName.equalsIgnoreCase(APP)) {
                boolean popup = false;
                if (attributes.getValue("popup")  != null &&
                        attributes.getValue("popup") .equalsIgnoreCase("true")) {
                    popup = true;
                }
                currentGadget.setApp(new GadgetInfo.EmbeddedApp(
                        attributes.getValue("url"),
                        Integer.parseInt(attributes.getValue("width")), 
                        Integer.parseInt(attributes.getValue("height")), 
                        popup, attributes.getValue("popupURL"), 
                        Integer.parseInt(attributes.getValue("popupHeight")), 
                        Integer.parseInt(attributes.getValue("popupWidth"))));
            }
        }
        
        /**
         * Handle the end of an element
         */
        @Override
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            
        }
    }
    
    // the private member variables
    private List<GadgetInfo> gadgets = new ArrayList<GadgetInfo>();
    
    /**
     * The constructor of the gadget parser.
     * 
     * @param file The string that contains the path to the xml file to be parsed.
     * @exception MasterBarException
     */
    public GadgetParser(String file) throws MasterBarException {
        try {
            GadgetHandler handler = new GadgetHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File menuFile = new File(file);
            InputSource source = new InputSource( 
                    new FileReader(menuFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new MasterBarException(
                    "Failed load the gadget information : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method returns the list of gadgets extracted from the xml file.
     * 
     * @return the list of gadgets extracted from the xml file.
     */
    public List<GadgetInfo> getGadgets() {
        return gadgets;
    }
}
