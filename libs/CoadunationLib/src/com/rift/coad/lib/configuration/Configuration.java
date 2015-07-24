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
 * Configuration.java
 *
 * The interface that defines the configuration specific methods.
 */

// package name
package com.rift.coad.lib.configuration;

// java imports
import java.util.Set;

/**
 * The interface that defines the configuration specific methods.
 *
 * @author Brett Chaldecott
 */
public interface Configuration {
    
    /**
     * This method returns the name of the class.
     *
     * @return The string containing the name of the class.
     */
    public String getClassName();
    
    
    /**
     * This method returns TRUE if the key supplied is present in the data.
     *
     * @return TRUE if the key has been found.
     * @param key The key to perform the search for.
     * @exception ConfigurationException
     */
    public boolean containsKey(String key) throws ConfigurationException;
    
    
    /**
     * This method returns the list of keys
     *
     * @return The set containing the list of keys.
     * @exception ConfigurationException
     */
    public Set getKeys() throws ConfigurationException;
    
    
    /**
     * This method returns true if the object is a string.
     *
     * @return TRUE if string, FALSE if not.
     * @param key The key to check the string type of.
     * @exception ConfigurationException
     */
    public boolean isString(String key) throws ConfigurationException;
    
    
    /**
     * The method that returns the string value for the requested key
     *
     * @return The string containing the configuration information.
     * @param key The key to retrieve the value for.
     * @exception ConfigurationException
     */
    public String getString(String key) throws ConfigurationException;
    
    
    /**
     * The method that returns the string value for the requested key
     *
     * @return The string containing the configuration information.
     * @param key The key to retrieve the value for.
     * @param defValue The default value.
     * @exception ConfigurationException
     */
    public String getString(String key,String defValue) 
        throws ConfigurationException;
    
    
    /**
     * This method set the configuration value for the key.
     *
     * @param key The key to set the value for.
     * @param value The new value to set.
     * @exception ConfigurationException
     */
    public void setString(String key, String value) 
        throws ConfigurationException;
    
    
    /**
     * This method returns true if the object is a long.
     *
     * @return TRUE if long, FALSE if not.
     * @param key The key to check the long type of.
     * @exception ConfigurationException
     */
    public boolean isLong(String key) throws ConfigurationException;
    
    
    /**
     * The method that will retrieve the long value from the configuration.
     *
     * @return The long value.
     * @param key identifying the long value.
     * @exception ConfigurationException
     */
    public long getLong(String key) throws ConfigurationException;
    
    
    /**
     * The method that will retrieve the long value from the configuration.
     *
     * @return The long value.
     * @param key identifying the long value.
     * @param defValue The default long value.
     * @exception ConfigurationException
     */
    public long getLong(String key,long defValue) throws ConfigurationException;
    
    
    /**
     * The method to set a configuration key value.
     *
     * @param key The key to set the value for.
     * @param value The value for the key.
     * @exception ConfigurationException
     */
    public void setLong(String key,long value) throws ConfigurationException;
    
    
    /**
     * This method returns true if the object is a boolean.
     *
     * @return TRUE if long, FALSE if not.
     * @param key The key to check the long type of.
     * @exception ConfigurationException
     */
    public boolean isBoolean(String key) throws ConfigurationException;
    
    
    /**
     * The method that will retrieve the boolean value from the configuration.
     *
     * @return TRUE of FALSE
     * @param key identifying the boolean value.
     * @exception ConfigurationException
     */
    public boolean getBoolean(String key) throws ConfigurationException;
    
    
    /**
     * The method that will retrieve the boolean value from the configuration.
     *
     * @return The boolean value.
     * @param key identifying the boolean value.
     * @param defValue The default boolean value.
     * @exception ConfigurationException
     */
    public boolean getBoolean(String key,boolean defValue) throws 
            ConfigurationException;
    
    
    /**
     * The method to set a configuration key value.
     *
     * @param key The key to set the value for.
     * @param value The value for the key.
     * @exception ConfigurationException
     */
    public void setBoolean(String key,boolean value) throws 
            ConfigurationException;
    
    
    /**
     * This method will save the configuration information.
     *
     * @exception ConfigurationException
     */
    public void saveConfiguration() throws ConfigurationException;
    
}
