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
 * XMLConfigurationEntry.java
 *
 * The object that stores the value for the xml configuration entry in memory.
 */

// The package
package com.rift.coad.lib.configuration.xml;

/**
 * The object that stores the value for the xml configuration entry in memory.
 *
 * @author Brett Chaldecott
 */
public class XMLConfigurationEntry {
    
    // member variables
    private String key = null;
    private XMLConfigurationType type = null;
    private String stringValue = null;
    private long longValue = 0;
    private boolean booleanValue = false;
    
    /** 
     * Creates a new instance of XMLConfigurationEntry 
     */
    public XMLConfigurationEntry() {
    }
    
    
    /**
     * The getter method for the key member variable.
     *
     * @return The string containing the key value.
     */
    public String getKey() {
        return this.key;
    }
    
    
    /**
     * The setter method for the key member variable.
     *
     * @param key The key value to set.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    
    /**
     * The getter method for the type information.
     *
     * @return The object containing the type information.
     */
    public XMLConfigurationType getType() {
        return type;
    }
    
    
    /**
     * The setter method for the type information.
     *
     * @parm type The object containing the type information.
     */
    public void setType(XMLConfigurationType type) {
        this.type = type;
    }
    
    
    /**
     * The getter method for the string value.
     *
     * @return The string containing the value.
     * @exception XMLConfigurationException
     */
    public String getStringValue() throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.STRING_VALUE == type.getType()) {
            return stringValue;
        } else {
            throw new XMLConfigurationException(
                    "The value for [" + key + "]is not of type [string].");
        }
    }
    
    
    /**
     * The setter method for the string value.
     *
     * @param value The string containing the value.
     * @exception XMLConfigurationException
     */
    public void setStringValue(String value) throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.STRING_VALUE == type.getType()) {
            stringValue = value;
        } else {
            throw new XMLConfigurationException(
                    "The value is not of type [string].");
        }
    }
    
    
    /**
     * The getter method for the long value.
     *
     * @return The long value.
     * @exception XMLConfigurationException
     */
    public long getLongValue() throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.LONG_VALUE == type.getType()) {
            return longValue;
        } else {
            throw new XMLConfigurationException(
                    "The value is not of type [long].");
        }
    }
    
    
    /**
     * The setter method for the long value.
     *
     * @param value The long containing the value.
     * @exception XMLConfigurationException
     */
    public void setLongValue(long value) throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.LONG_VALUE == type.getType()) {
            longValue = value;
        } else {
            throw new XMLConfigurationException(
                    "The value is not of type [long].");
        }
    }
    
    
    /**
     * The getter method for the boolean value.
     *
     * @return The boolean value.
     * @exception XMLConfigurationException
     */
    public boolean getBooleanValue() throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.BOOLEAN_VALUE == type.getType()) {
            return booleanValue;
        } else {
            throw new XMLConfigurationException(
                    "The value is not of type [boolean].");
        }
    }
    
    
    /**
     * The setter method for the boolean value.
     *
     * @param value The boolean containing the value.
     * @exception XMLConfigurationException
     */
    public void setBooleanValue(boolean value) throws XMLConfigurationException {
        if (type == null) {
            throw new XMLConfigurationException(
                    "The type has not been initialized");
        } else if (XMLConfigurationType.BOOLEAN_VALUE == type.getType()) {
            booleanValue = value;
        } else {
            throw new XMLConfigurationException(
                    "The value is not of type [boolean].");
        }
    }
    
    
    /**
     * The operator that will set the internal long or string value
     *
     * @param value The string containing the value.
     * @exception XMLConfigurationException
     */
    public void setValueFromString(String value) throws XMLConfigurationException {
        try {
            if (type == null) {
                throw new XMLConfigurationException(
                        "The type has not been initialized");
            } else if (XMLConfigurationType.STRING_VALUE == type.getType()) {
                stringValue = value;
            } else if (XMLConfigurationType.BOOLEAN_VALUE == type.getType()) {
                if (value.trim().equalsIgnoreCase("TRUE")) {
                    booleanValue = true;
                } else {
                    booleanValue = false;
                }
            } else {
                longValue = Long.parseLong(value);
            }
        } catch (Exception ex) {
            throw new XMLConfigurationException("Failed to store the value [" +
                    value + "] because :" + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method will return true if all the member variables have been
     * initialized.
     *
     * @return TRUE if all the member variables have been initialized.
     */
    public boolean isIntiailized() {
       if ((key == null) || (type == null)) {
           return false;
       } else if ((type.getType() == XMLConfigurationType.STRING_VALUE) &&
               stringValue == null) {
           return false;
       }
       return true;
    }
}
