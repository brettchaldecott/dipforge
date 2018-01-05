/*
 * Semantics: The semantic library for coadunation os
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
 * PersistanceResource.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * The resource responsible for managing the persistance of information.
 *
 * @author brett chaldecott
 */
public interface PersistanceResource {

    /**
     * The unique uri for this resource.
     *
     * @return The uri for this object.
     * @throws PersistanceException
     */
    public URI getURI() throws PersistanceException;
    
    
    /**
     * This method returns a boolean literal value for this property.
     *
     * @return This method returns the literal boolean value.
     * @throws PersistanceException
     */
    public boolean getValueAsBoolean() throws PersistanceException;


    /**
     * This method returns the literal as integer value.
     *
     * @return The long value.
     * @throws PersistanceException
     */
    public int getValueAsInteger() throws PersistanceException;


    /**
     * This method returns the literal as long value.
     *
     * @return The long value.
     * @throws PersistanceException
     */
    public long getValueAsLong() throws PersistanceException;

    
    /**
     * This method retrieves the character information.
     *
     * @return The character value for this property.
     * @throws PersistanceException
     */
    public char getValueAsCharacter() throws PersistanceException;


    /**
     * This method returns the value as double.
     *
     * @return This method returns the double value.
     * @throws PersistanceException
     */
    public double getValueAsDouble() throws PersistanceException;


    /**
     * This method returns the float value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public float getValueAsFloat() throws PersistanceException;


    /**
     * This method method returns the value of the calendar.
     *
     * @return The reference to the calendar value.
     * @throws PersistanceException
     */
    public Calendar getValueAsCalendar() throws PersistanceException;
    

    /**
     * This method returns the float value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public String getValueAsString() throws PersistanceException;

    
    /**
     * This method returns the big integer value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public BigInteger getValueAsBigInteger() throws PersistanceException;
    

    /**
     * This method returns the big decimal value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public BigDecimal getValueAsBigDecimal() throws PersistanceException;
    

    /**
     * The name space for the resource.
     *
     * @return URI name space for this resource.
     */
    public PersistanceIdentifier getPersistanceIdentifier();


    /**
     * This method returns TRUE if the property exists.
     *
     * @param identifier The identifier of the property.
     * @return TRUE if found, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasProperty(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method creates a new property.
     *
     * @param namespace The name space property.
     * @param localName The local name.
     * @return The reference to the newly created property.
     * @throws PersistanceException
     */
    public PersistanceProperty createProperty(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method lists the perstance properties attached to this resource.
     * 
     * @return The list of properties.
     * @throws PersistanceException
     */
    public List<PersistanceProperty> listProperties() throws PersistanceException;


    /**
     * This method returns a list of properties associated with the identifier.
     *
     * @param identifier The identifier to return properties for.
     * @return The list of identifiers.
     * @throws PersistanceException
     */
    public List<PersistanceProperty> listProperties(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * The reference to the property information.
     *
     * @param namespace The name space for the property.
     * @param localName The
     * @return
     * @throws PersistanceException
     */
    public PersistanceProperty getProperty(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method removes the property identified by the identifier.
     * 
     * @param identifier
     * @throws PersistanceException
     */
    public void removeProperty(PersistanceIdentifier identifier) throws
            PersistanceException;


    /**
     * This method removes the property identified by the identifier.
     *
     * @param identifier The identifier.
     * @param resource The resource that has to be removed or unlinked.
     * @throws PersistanceException
     */
    public void removeProperty(PersistanceIdentifier identifier,
            PersistanceResource resource) throws PersistanceException;
}
