/*
 * SchemaUtils: Utilities implemented for the RDF schema.
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
 * XMLListParser.java
 */


// package path
package com.rift.coad.schema.util;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 *
 * @author brett chaldecott
 */
public class XMLListParser {

    private static String FILES_TAG = "files";
    private static String FILE_TAG = "file";
    private static String FILE_ATTRIBUTE_NAME = "name";



    /**
     * The handler for the xml list.
     */
    public class XMLListHandler extends DefaultHandler {
        private boolean inFiles;

        /**
         * The default constructor for the XML List handler;
         */
        public XMLListHandler() {
            inFiles = false;
        }


        /**
         * This method handles the start of an element
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            try {
                if (qName.compareToIgnoreCase(FILES_TAG) == 0) {
                    inFiles = true;
                } else if (inFiles && qName.compareToIgnoreCase(FILE_TAG) == 0) {
                    urls.add(new URL((String)attributes.getValue(FILE_ATTRIBUTE_NAME)));
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to process the start element : "
                        + ex.getMessage(),ex);
            }
        }


    }

    // private member variables
    private List<URL> urls;

    {
        urls = new ArrayList<URL>();
    }

    /**
     * This constructor is used to parse the XML list information.
     *
     * @param contents The contents of the file to parse.
     * @throws XMLListParserException
     */
    public XMLListParser(String contents) throws XMLListParserException {
        try {

            XMLListHandler handler = new XMLListHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            ByteArrayInputStream in = new ByteArrayInputStream(contents.getBytes());
            InputSource source = new InputSource(in);
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new XMLListParserException(
                    "Failed parse the XML list : " + ex.getMessage(),
                    ex);
        }
    }


    /**
     * This constructor sets up the location information.
     *
     * @param location The location of the file to read.
     */
    public XMLListParser(URL location) throws XMLListParserException {
        try {
            XMLListHandler handler = new XMLListHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            InputSource source = new InputSource(location.openStream());
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new XMLListParserException(
                    "Failed parse the XML list : " + ex.getMessage(),
                    ex);
        }
    }

    
    /**
     * This method returns the list of file
     *
     * @return This method returns a list of urls extracted from the contents passed in.
     */
    public List<URL> getURLs() {
        return urls;
    }
    


}
