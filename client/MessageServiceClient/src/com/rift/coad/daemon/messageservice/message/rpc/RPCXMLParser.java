/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2007 Rift IT Contracting
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
 * RPCXMLParser.java
 */

// package path
package com.rift.coad.daemon.messageservice.message.rpc;

// java imports
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;



/**
 * This object is responsible for parsing the RPC xml.
 *
 * @author Brett Chaldecott
 */
public class RPCXMLParser implements Serializable {
    
    /**
     *
     */
    
    
    /**
     * This method will handle the parsing of the RPC xml.
     */
    public class RPCXMLHandler extends DefaultHandler implements Serializable {
        // class constants
        private final static String METHOD = "method";
        private final static String NAME = "name";
        private final static String TYPE = "type";
        private final static String ARGUMENT = "argument";
        
        // private member variables
        private List argumentNameList = new ArrayList();
        private List argumentTypeList = new ArrayList();
        private boolean inMethod = false;
        
        /**
         * The constructor of the rpc xml handler
         */
        public RPCXMLHandler() {
            
        }
        
        
        /**
         * Parse the start of an element 
         *
         * @param uri The uri of the start element
         * @param localName The name of local object.
         * @param qName The quick name
         * @param attributes The attributes associated with this element.
         * @exception SAXException
         */
        public void startElement(String uri, String localName, String qName,
                         Attributes attributes) throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(METHOD) == 0) {
                    method = attributes.getValue(NAME);
                    if (isPrimitive(attributes.getValue(TYPE))) {
                        returnType = getPrimitive(attributes.getValue(TYPE));
                    } else {
                        try {
                            returnType = Class.forName(
                                    attributes.getValue(TYPE));
                        } catch (Exception ex) {
                            returnType = Thread.currentThread().
                                    getContextClassLoader().loadClass(
                                    attributes.getValue(TYPE));
                        }
                    }
                    inMethod = true;
                } else if (inMethod &&
                        qName.compareToIgnoreCase(ARGUMENT) == 0) {
                    argumentNameList.add(attributes.getValue(NAME));
                    argumentTypeList.add(attributes.getValue(TYPE));
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to parse the element : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * Handle the end of an element
         *
         * @param uri The uri of the element
         * @param localName The local name of the element. 
         * @param qName The quick name of the element
         * @exception SAXException
         */
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            try {
                // handle a package and retrieve the value information
                if (qName.compareToIgnoreCase(METHOD) == 0) {
                    inMethod = false;
                    argumentTypes = new Class[argumentTypeList.size()];
                    argumentNames = new String[argumentNameList.size()];
                    for (int index = 0; index < argumentTypeList.size(); 
                    index++) {
                        String name = (String)argumentTypeList.get(index);
                        if (isPrimitive(name)) {
                            argumentTypes[index] = getPrimitive(name);
                        } else if (name.contains("[L")) {
                            String parsedName = name.substring(2, name.length() - 1);
                            try {
                                argumentTypes[index] = Array.newInstance(Class.forName(parsedName), 0).getClass();
                            } catch (Exception ex) {
                                argumentTypes[index] = Array.newInstance(Thread.currentThread().
                                        getContextClassLoader().loadClass(parsedName),0).getClass();
                            }
                        }else {
                            try {
                                argumentTypes[index] = Class.forName(name);
                            } catch (Exception ex) {
                                argumentTypes[index] = Thread.currentThread().
                                        getContextClassLoader().loadClass(name);
                            }
                        }
                        argumentNames[index] = 
                                (String)argumentNameList.get(index);
                    }
                }
            } catch (Exception ex) {
                throw new SAXException("Failed to parse the element : " + 
                        ex.getMessage(),ex);
            }
        }
        
        /**
         * This method returns true if the name identifies a primitive.
         *
         * @return TRUE if a primitive, FALSE if not.
         * @param name The name of the primitive.
         */
        private boolean isPrimitive(String name) {
            if (name.equals("byte") || name.equals("short") || 
                    name.equals("int") || name.equals("long") || 
                    name.equals("float") || name.equals("double") ||
                    name.equals("boolean") || name.equals("char") ||
                    name.equals("void")) {
                return true;
            }
            return false;
        }
        
        /**
         * This method returns the primitive for the name.
         *
         * @return The reference to the class definition for the primitive
         * @param name The name of the primitive.
         * @exception SAXException
         */
        private Class getPrimitive(String name) throws SAXException {
            if (name.equals("byte")) {
                return byte.class;
            } else if (name.equals("short")) {
                return short.class;
            } else if (name.equals("int")) {
                return int.class;
            } else if (name.equals("long")) {
                return long.class;
            } else if (name.equals("float")) {
                return float.class;
            } else if (name.equals("double")) {
                return double.class;
            } else if (name.equals("boolean")) {
                return boolean.class;
            } else if (name.equals("char")) {
                return char.class;
            } else if (name.equals("void")) {
                return void.class;
            }
            throw new SAXException("Unrecognised basic type : " + name);
        }
    }
    
    
    // the classes private member variables
    private String rpcXML = null;
    private Object returnType = null;
    private String method = null;
    private Class[] argumentTypes = null;
    private String[] argumentNames = null;
    
    
    /** 
     * Creates a new instance of RPCXMLParser.
     *
     * @param rpcXML The string to parse.
     * @exception RPCXMLException
     */
    public RPCXMLParser(String rpcXML) throws RPCXMLException {
        this.rpcXML = rpcXML;
        try {
            RPCXMLHandler handler = new RPCXMLHandler();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            InputSource source = new InputSource( 
                    new ByteArrayInputStream(rpcXML.getBytes()));
            parser.parse(source,handler);
        } catch (Exception ex) {
            throw new RPCXMLException("Failed to parse the xml : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the RPC xml.
     *
     * @return The string containing the RPC xml.
     */
    public String getRPCXML() {
        return rpcXML;
    }
    
    
    /**
     * This method returns the return type of the rpc call.
     *
     * @return The object containing the return type.
     * @exception RPCXMLException
     */
    public Object getReturnType() {
        return returnType;
    }
    
    
    /**
     * This method returns the name of the method that the XML defines.
     *
     * @return The name of this method.
     */
    public String getMethodName() {
        return method;
    }
    
    
    /**
     * This method returns the list of argument types.
     *
     * @return The array of argument types.
     */
    public Class[] getArgumentTypes() {
        return argumentTypes;
    }
    
    
    /**
     * This method returns the list of argument names
     *
     * @return The array of argument names.
     */
    private String[] getArgumentNames() {
        return argumentNames;
    }
}
