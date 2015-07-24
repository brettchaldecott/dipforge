/*
 * DesktopServer: The server responsible for managing the general desktop resources.
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
 * MimeParser.java
 */

// packages
package com.rift.coad.daemon.desktop;

// java imports
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * This object is responsible for parsing the Mime information
 * 
 * @author brett chaldecott
 */
public class MimeParser {
    
    /**
     * The handler responsible for parsing the gadget list.
     */
    public class MimeHandler  extends DefaultHandler {
        
        // class constants
        private final static String MIME_TYPES = "MimeTypes";
        private final static String MIME = "Mime";
        private final static String NAME = "name";
        private final static String URL = "url";
        private final static String ICON = "icon";
        private final static String WIDTH = "width";
        private final static String HEIGHT = "height";
        
        /**
         * The default constructr of mime handler
         */
        public MimeHandler() {
            
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
            if (qName.equalsIgnoreCase(MIME_TYPES)) {
                return;
            } else if (qName.equalsIgnoreCase(MIME)) {
                String name = (String)attributes.getValue(NAME);
                types.put(name, new MimeType(name, (String)attributes.getValue(URL), 
                        (String)attributes.getValue(ICON), Integer.parseInt((String)attributes.getValue(WIDTH)),
                        Integer.parseInt((String)attributes.getValue(HEIGHT))));
            }
        }
    }

    // private member variales
    private String filePath = null;
    private long touchDate =0;
    private Map<String, MimeType> types = new HashMap<String, MimeType>(); 
    
    
    /**
     * The constructor of the mime parser.
     * 
     * @thows DesktopException
     */
    public MimeParser(String filePath) throws DesktopException {
        this.filePath = filePath;
        parseFile();
    }
    
    
    /**
     * This method returns the mime types.
     * 
     * @return The list of types.
     * @thows DesktopException
     */
    public Map<String,MimeType> getTypes() throws DesktopException {
        checkFile();
        return types;
    }
    
    
    /**
     * This method is called to check the file.
     * 
     * @thows DesktopException
     */
    private synchronized void checkFile() throws DesktopException {
        File path = new File(filePath);
        if (path.lastModified() != touchDate) {
            parseFile();
        }
    }
    
    /**
     * This method is called to parse the file
     * 
     * @thows DesktopException
     */
    private void parseFile() throws DesktopException {
        try {
            MimeHandler handler = new MimeHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            File mimeFile = new File(filePath);
            InputSource source = new InputSource( 
                    new FileReader(mimeFile)); 
            parser.parse(source,handler);
            touchDate = mimeFile.lastModified();
        } catch (Exception ex) {
            throw new DesktopException(
                    "Failed load the desktop content : " + ex.getMessage(),
                    ex);
        }
    }
}
