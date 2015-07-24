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
 * XMLConfiguration.java
 *
 * The class containing the configuration information for a specific class.
 */

// package
package com.rift.coad.lib.configuration.xml;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationException;

/**
 * The class containing the configuration information for a specific class.
 *
 * @author Brett Chaldecott
 */
public class XMLConfiguration implements Configuration {
    
    // the classes member varialbes
    private String className = null;
    private Map entries = null;
    
    /** 
     * Creates a new instance of XMLConfiguration 
     *
     * @param className The name of the class.
     */
    public XMLConfiguration(String className) {
        this.className = className;
        this.entries = new HashMap();
    }
    
    
    /**
     * This method returns the name of the class.
     *
     * @return The string containing the name of the class.
     */
    public String getClassName() {
        return className;
    }
    
    /**
     * This method returns TRUE if the key supplied is present in the data.
     *
     * @return TRUE if the key has been found.
     * @param key The key to perform the search for.
     * @exception ConfigurationException
     */
    public boolean containsKey(String key) throws ConfigurationException {
        return entries.containsKey(key);
    }
    
    
    /**
     * This method returns the list of keys
     *
     * @return The set containing the list of keys.
     * @exception ConfigurationException
     */
    public Set getKeys() throws ConfigurationException {
        return entries.keySet();
    }
    
    
    /**
     * This method returns true if the object is a string.
     *
     * @return TRUE if string, FALSE if not.
     * @param key The key to check the string type of.
     * @exception ConfigurationException
     */
    public boolean isString(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        if (entry.getType().getType() == XMLConfigurationType.STRING_VALUE) {
            return true;
        }
        return false;
    }
    
    
    /**
     * The method that returns the string value for the requested key
     *
     * @return The string containing the configuration information.
     * @param key The key to retrieve the value for.
     * @exception ConfigurationException
     */
    public String getString(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        return entry.getStringValue();
    }
    
    
    /**
     * The method that returns the string value for the requested key
     *
     * @return The string containing the configuration information.
     * @param key The key to retrieve the value for.
     * @param defValue The default value.
     * @exception ConfigurationException
     */
    public String getString(String key,String defValue) 
        throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            return defValue;
        }   
        return entry.getStringValue();
    }
    
    
    /**
     * This method set the configuration value for the key.
     *
     * @param key The key to set the value for.
     * @param value The new value to set.
     * @exception ConfigurationException
     */
    public void setString(String key, String value) 
        throws ConfigurationException {
        throw new ConfigurationException("Not Implemented");
    }
    
    
    /**
     * This method returns true if the object is a long.
     *
     * @return TRUE if long, FALSE if not.
     * @param key The key to check the long type of.
     * @exception ConfigurationException
     */
    public boolean isLong(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        if (entry.getType().getType() == XMLConfigurationType.LONG_VALUE) {
            return true;
        }
        return false;
    }
    
    
    /**
     * The method that will retrieve the long value from the configuration file.
     *
     * @return The long value.
     * @param key identifying the long value.
     * @exception ConfigurationException
     */
    public long getLong(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        return entry.getLongValue();
    }
    
    
    /**
     * The method that will retrieve the long value from the configuration file.
     *
     * @return The long value.
     * @param key identifying the long value.
     * @param defValue The default long value.
     * @exception ConfigurationException
     */
    public long getLong(String key,long defValue) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            return defValue;
        }   
        return entry.getLongValue();
    }
    
    
    /**
     * The method to set a configuration key value.
     *
     * @param key The key to set the value for.
     * @param value The value for the key.
     * @exception ConfigurationException
     */
    public void setLong(String key,long value) throws ConfigurationException {
        throw new ConfigurationException("Not Implemented");
    }
    
    
    /**
     * This method returns true if the object is a boolean.
     *
     * @return TRUE if long, FALSE if not.
     * @param key The key to check the long type of.
     * @exception ConfigurationException
     */
    public boolean isBoolean(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        if (entry.getType().getType() == XMLConfigurationType.BOOLEAN_VALUE) {
            return true;
        }
        return false;
    }
    
    
    /**
     * The method that will retrieve the boolean value from the configuration.
     *
     * @return TRUE of FALSE
     * @param key identifying the boolean value.
     * @exception ConfigurationException
     */
    public boolean getBoolean(String key) throws ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            throw new ConfigurationException("The entry [" + key 
                    + "] does not exist");
        }
        return entry.getBooleanValue();
    }
    
    
    /**
     * The method that will retrieve the boolean value from the configuration.
     *
     * @return The boolean value.
     * @param key identifying the boolean value.
     * @param defValue The default boolean value.
     * @exception ConfigurationException
     */
    public boolean getBoolean(String key,boolean defValue) throws 
            ConfigurationException {
        XMLConfigurationEntry entry = (XMLConfigurationEntry)entries.get(key);
        if (entry == null) {
            return defValue;
        }
        return entry.getBooleanValue();
    }
    
    
    /**
     * The method to set a configuration key value.
     *
     * @param key The key to set the value for.
     * @param value The value for the key.
     * @exception ConfigurationException
     */
    public void setBoolean(String key,boolean value) throws 
            ConfigurationException {
        throw new ConfigurationException("Not implemented");
    }
    
    
    /**
     * This method will save the configuration information.
     *
     * @exception ConfigurationException
     */
    public void saveConfiguration() throws ConfigurationException {
        throw new ConfigurationException("Not Implemented");
    }
    
    
    /**
     * This method adds the configuration entry to the list.
     *
     * @param entry The entry to add to the configuration list.
     */
    public void addConfigurationEntry(XMLConfigurationEntry entry){
        entries.put(entry.getKey(),entry);
    }
}
