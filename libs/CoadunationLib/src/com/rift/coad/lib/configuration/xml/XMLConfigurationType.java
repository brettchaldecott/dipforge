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
 * XMLConfigurationType.java
 *
 * The object wrapping the type information for the XML configuration.
 */

package com.rift.coad.lib.configuration.xml;

/**
 * The object wrapping the type information for the XML configuration.
 *
 * @author Brett Chaldecott
 */
public class XMLConfigurationType {
    
    // the classes static variables
    public final static long STRING_VALUE = 1;
    public final static String STRING_ID = "string";
    public final static long LONG_VALUE = 2;
    public final static String LONG_ID = "long";
    public final static long BOOLEAN_VALUE = 3;
    public final static String BOOLEAN_ID = "boolean";
    
    // the classes member variables
    private long type = 0;
    
    /** 
     * Creates a new instance of XMLConfigurationType 
     *
     * @param type The type of xml configurate value.
     * @exception XMLConfigurationException
     */
    public XMLConfigurationType(long type) throws XMLConfigurationException {
        if ((type != STRING_VALUE) &&(type != LONG_VALUE)) {
            throw new XMLConfigurationException(
                    "Invalid type information can either be [STRING] or [LONG]");
        }
        this.type = type;
    }
    
    /** 
     * Creates a new instance of XMLConfigurationType 
     *
     * @param type The type of xml configurate value.
     * @exception XMLConfigurationException
     */
    public XMLConfigurationType(String type) throws XMLConfigurationException {
        setTypeFromString(type);
    }
    
    
    /**
     * The getter method for the type information
     *
     * @return The type contained in the long variable.
     */
    public long getType() {
        return type;
    }
    
    
    /**
     * The setter method for the type infomration.
     *
     * @param type The new type value
     * @exception XMLConfigurationException
     */
    public void setType(long type) throws XMLConfigurationException {
        if ((type != STRING_VALUE) &&(type != LONG_VALUE) && 
                (type != BOOLEAN_VALUE)) {
            throw new XMLConfigurationException(
                    "Invalid type information can either be [STRING] or [LONG] " +
                    "or [BOOLEAN]");
        }
        this.type = type;
    }
    
    
    /**
     * This method sets the type information using the string value extracted
     * from the XML configuration file.
     *
     * @param type The string to retrieve the type information from.
     */
    public void setTypeFromString(String type)
        throws XMLConfigurationException {
        if (type.equalsIgnoreCase(STRING_ID)) {
            this.type = STRING_VALUE;
        } else if (type.equalsIgnoreCase(LONG_ID)) {
            this.type = LONG_VALUE;
        } else if (type.equalsIgnoreCase(BOOLEAN_ID)) {
            this.type = BOOLEAN_VALUE;
        } else {
            throw new XMLConfigurationException("Invalid type [" + type 
                    + "] can either be [string] or [long].");
        }
    }
    
    /**
     * The equals method to check if one type equals another type.
     *
     * @return TRUE if the values match, FALSE if they do not.
     * @param ref The reference to the object to compare.
     */
    public boolean equals(Object ref) {
        if (!(ref instanceof XMLConfigurationType)) {
            return false;
        } else if (((XMLConfigurationType)ref).getType() == type) {
            return true;
        }
        return false;
    }
    
}
