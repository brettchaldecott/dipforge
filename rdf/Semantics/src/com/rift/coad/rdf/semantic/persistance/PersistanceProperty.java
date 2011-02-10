/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * PersistanceProperty.java
 */

package com.rift.coad.rdf.semantic.persistance;

import java.net.URI;

/**
 * This object represents a property.
 *
 * @author brett chaldecott
 */
public interface PersistanceProperty {

    /**
     * The unique uri for this resource.
     *
     * @return The uri for this object.
     * @throws PersistanceException
     */
    public URI getURI() throws PersistanceException;

    /**
     * The name space for the resource.
     *
     * @return URI name space for this resource.
     */
    public PersistanceIdentifier getPersistanceIdentifier();


    /**
     * This method sets the literal value.
     *
     * @param boolValue The boolean containing the literal value
     */
    public void setValue(boolean boolValue) throws PersistanceException;


    /**
     * This method returns a boolean literal value for this property.
     *
     * @return This method returns the literal boolean value.
     * @throws PersistanceException
     */
    public boolean getValueAsBoolean() throws PersistanceException;


    /**
     * This method sets the literal value.
     *
     * @param longValue The long value.
     * @throws PersistanceException
     */
    public void setValue(long longValue) throws PersistanceException;


    /**
     * This method returns the literal as long value.
     *
     * @return The long value.
     * @throws PersistanceException
     */
    public long getValueAsLong() throws PersistanceException;

    
    /**
     * This method sets the character value.
     *
     * @param characterValue The character value to set.
     * @throws PersistanceException
     */
    public void setValue(char characterValue) throws PersistanceException;


    /**
     * This method retrieves the character information.
     *
     * @return The character value for this property.
     * @throws PersistanceException
     */
    public char getValueAsCharacter() throws PersistanceException;


    /**
     * This method sets the literal double value.
     *
     * @param doubleValue The double value.
     * @throws PersistanceException
     */
    public void setValue(double doubleValue) throws PersistanceException;


    /**
     * This method returns the value as double.
     *
     * @return This method returns the double value.
     * @throws PersistanceException
     */
    public double getValueAsDouble() throws PersistanceException;


    /**
     * This method sets the literal value for a float.
     *
     * @param floatValue The float literal value.
     * @throws PersistanceException
     */
    public void setValue(float floatValue) throws PersistanceException;


    /**
     * This method returns the float value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public float getValueAsFloat() throws PersistanceException;


    /**
     * This method sets the literal object value.
     *
     * @param objectValue The object value.
     * @throws PersistanceException
     */
    public void setValue(PersistanceResource objectValue) throws PersistanceException;


    /**
     * This method returns property values as a resource.
     *
     * @return The resource value.
     * @throws PersistanceException
     */
    public PersistanceResource getValueAsResource() throws PersistanceException;
}
