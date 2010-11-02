/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * XMLUserParser.java
 *
 * The parser for the XML user file.
 */

package com.rift.coad.lib.security.user.xml;

// java imports
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

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.user.UserException;

/**
 * The parser for the XML user file.
 *
 * @author Brett Chaldecott
 */
public class XMLUserParser {
    
    /**
     * The inner class responsible for handling the contents of the Coadunation
     * xml user document.
     */
    public class XMLUserHandler extends DefaultHandler {
        
        // class constant static member variables
        private final static String USERS = "users";
        private final static String USER = "user";
        private final static String USER_NAME = "name";
        private final static String USER_PASSWD = "password";
        private final static String PRINCIPAL = "principal";
        
        // the member variables
        private Map users = null;
        
        // tracking variables
        private boolean inUsers = false;
        private boolean inUser = false;
        private boolean inPrincipal = false;
        private String inData = null;
        private UserData userData = null;
        
        
        
        /**
         * The constructor of the xml users handler.
         *
         * @param users The list of users.
         */
        public XMLUserHandler(Map users) {
            this.users = users;
        }
        
        /**
         * Parse the starting element
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(USERS) == 0) {
                    inUsers = true;
                } else if (inUsers &&
                        qName.compareToIgnoreCase(USER) == 0) {
                    String username = (String)attributes.getValue(USER_NAME);
                    String password = (String)attributes.getValue(USER_PASSWD);
                    userData = new UserData(username,password);
                    inUser = true;
                } else if (inUsers && inUser &&
                        qName.compareToIgnoreCase(PRINCIPAL) == 0) {
                    inData = new String();
                    inPrincipal = true;
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to process the user information :"
                        + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * Read in the characters
         */
        public void characters(char[] ch, int start, int length) {
            if (inPrincipal) {
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
                if (qName.compareToIgnoreCase(USERS) == 0) {
                    inUsers = false;
                } else if (inUsers &&
                        qName.compareToIgnoreCase(USER) == 0) {
                    users.put(userData.getUsername(),userData);
                    inUser = false;
                } else if (inUsers && inUser &&
                        qName.compareToIgnoreCase(PRINCIPAL) == 0) {
                    userData.addPrincipal(inData.trim());
                    inPrincipal = false;
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to set the end element : " + 
                        ex.getMessage(),ex);
            }
        }
        
    }
    
    // class static variabls
    private final static String PASSWD_FILE = "password_file";
    
    // static member variables
    private static final Logger log = Logger.getLogger(
            XMLUserParser.class.getName());
    
    // the classes private member variables
    private long lastModifyTime = 0;
    private String fileName = null;
    private Map users = new HashMap();
    
    
    /** 
     * Creates a new instance of XMLUserParser 
     */
    public XMLUserParser() throws UserException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(getClass());
            this.users = users;
            
            XMLUserHandler handler = new XMLUserHandler(users);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            fileName = config.getString(PASSWD_FILE);
            File passwordFile = new File(fileName);
            lastModifyTime = passwordFile.lastModified();
            InputSource source = new InputSource( 
                    new FileReader(passwordFile)); 
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new UserException("Failed to parse the user xml file : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the map containing the users.
     *
     * @return The object containing the list of users.
     */
    public Map getUsers() {
        return users;
    }
    
    
    /**
     * Check the password file and reload if necessary.
     */
    public synchronized void reload() {
        try {
            File passwordFile = new File(fileName);
            if (passwordFile.lastModified() == this.lastModifyTime) {
                return;
            }
            // re-load the password file
            this.lastModifyTime = passwordFile.lastModified();
            users = new HashMap();
            XMLUserHandler handler = new XMLUserHandler(users);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            lastModifyTime = passwordFile.lastModified();
            InputSource source = new InputSource( 
                    new FileReader(passwordFile)); 
            parser.parse(source,handler);
            log.info("Reloaded the user file");
        } catch (Exception ex) {
            log.error("Failed to reload the password file : " + ex.getMessage(),
                    ex);
        }
    }
}
