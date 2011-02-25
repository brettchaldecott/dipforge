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

package com.rift.coad.rdf.semantic.persistance.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.rift.coad.rdf.semantic.SemanticException;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
 * This object represents a property.
 *
 * @author brett chaldecott
 */
public class JenaPersistanceProperty implements PersistanceProperty {

    // private logger
    private static Logger log = Logger.getLogger(JenaPersistanceProperty.class);

    // private member variables
    private Model jenaModel;
    private Resource resource;
    private Property property;
    private Statement statement;

    
    /**
     * The constructor used to create a property.
     * 
     * @param resource The string containing the resource reference.
     * @param property The property reference.
     */
    protected JenaPersistanceProperty(Model jenaModel, Resource resource,
            Property property) {
        this.jenaModel = jenaModel;
        this.resource = resource;
        this.property = property;
    }



    
    /**
     * This method sets the resource information and the name information.
     *
     * @param property The property to
     * @param resource The string containing he resource information.
     * @param name The name information.
     */
    protected JenaPersistanceProperty(Model jenaModel, Property property,
            Statement statement) {
        this.jenaModel = jenaModel;
        this.property = property;
        this.statement = statement;
    }



    

    /**
     * The unique uri for this resource.
     *
     * @return The uri for this object.
     */
    public URI getURI() throws SemanticException {
        try {
            return new URI(property.getURI());
        } catch (URISyntaxException ex) {
            log.error("Failed to retrieve the uri : " + ex.getMessage(),ex);
            throw new SemanticException
                    ("Failed to retrieve the uri : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * The name space for the resource.
     *
     * @return URI name space for this resource.
     */
    public PersistanceIdentifier getPersistanceIdentifier() {
        Property property  = statement.getPredicate();
        return PersistanceIdentifier.getInstance(property.getNameSpace(),
                property.getNameSpace());
    }


    /**
     * This method sets the literal value.
     *
     * @param boolValue The boolean containing the literal value
     */
    public void setValue(boolean boolValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, boolValue).
                        getProperty(property);
            } else {
                statement.changeLiteralObject(boolValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a boolean literal value for this property.
     *
     * @return This method returns the literal boolean value.
     * @throws PersistanceException
     */
    public boolean getValueAsBoolean() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getBoolean();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the literal value.
     *
     * @param longValue The long value.
     * @throws PersistanceException
     */
    public void setValue(long longValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, longValue).
                        getProperty(property);
            } else {
                statement.changeLiteralObject(longValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the literal as long value.
     *
     * @return The long value.
     * @throws PersistanceException
     */
    public long getValueAsLong() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getLong();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the character value.
     *
     * @param characterValue The character value to set.
     * @throws PersistanceException
     */
    public void setValue(char characterValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, characterValue).
                        getProperty(property);
            } else {
                statement.changeLiteralObject(characterValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the character information.
     *
     * @return The character value for this property.
     * @throws PersistanceException
     */
    public char getValueAsCharacter() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getChar();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the literal double value.
     *
     * @param doubleValue The double value.
     * @throws PersistanceException
     */
    public void setValue(double doubleValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, doubleValue).
                        getProperty(property);

            } else {
                statement.changeLiteralObject(doubleValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the value as double.
     *
     * @return This method returns the double value.
     * @throws PersistanceException
     */
    public double getValueAsDouble() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getDouble();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the literal value for a float.
     *
     * @param floatValue The float literal value.
     * @throws PersistanceException
     */
    public void setValue(float floatValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, floatValue).
                        getProperty(property);
            } else {
                statement.changeLiteralObject(floatValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the float value for this property.
     *
     * @return The float value.
     * @throws PersistanceException
     */
    public float getValueAsFloat() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getFloat();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the calendar value.
     *
     * @param calendarValue The calendar value.
     * @throws PersistanceException
     */
    public void setValue(Calendar calendarValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property,
                        jenaModel.createTypedLiteral(calendarValue))
                        .getProperty(property);
            } else {
                statement.changeObject(
                        jenaModel.createTypedLiteral(calendarValue));
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method method returns the value of the calendar.
     *
     * @return The reference to the calendar value.
     * @throws PersistanceException
     */
    public Calendar getValueAsCalendar() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return (Calendar)statement.getLiteral().getValue();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the value.
     *
     * @param stringValue The string value.
     * @throws PersistanceException
     */
    public void setValue(String stringValue) throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                statement = resource.addLiteral(property, 
                        jenaModel.createTypedLiteral(stringValue))
                        .getProperty(property);
            } else {
                statement.changeObject(stringValue);
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the value.
     *
     * @return The return value.
     * @throws PersistanceException
     */
    public String getValueAsString() throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return statement.getString();
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the literal object value.
     *
     * @param objectValue The object value.
     * @throws PersistanceException
     */
    public void setValue(PersistanceResource objectValue)
            throws PersistanceException {
        try {
            JenaPersistanceResource jenaPersistanceResource =
                    (JenaPersistanceResource)objectValue;
            if (statement == null && resource != null) {
                statement = resource.addProperty(property,
                        jenaPersistanceResource.getResource()).
                        getProperty(property);
            } else {
                statement.changeObject(
                        jenaPersistanceResource.getResource());
            }
        } catch (Exception ex) {
            log.error("Failed to set the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException("Failed to set the value : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns property values as a resource.
     *
     * @return The resource value.
     * @throws PersistanceException
     */
    public PersistanceResource getValueAsResource() 
            throws PersistanceException {
        try {
            if (statement == null && resource != null) {
                throw new PersistanceException("The property [" +
                        property.getURI() + "]has not been set.");
            } else {
                return new JenaPersistanceResource(jenaModel,
                        statement.getResource());
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the value : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to get the value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the string value for the property.
     *
     * @return The string value for the property.
     */
    @Override
    public String toString() {
        if (resource != null) {
            return "JenaPersistanceProperty{" + "resource=" + resource.getURI() + '}';
        } else if (property != null) {
            return "JenaPersistanceProperty{" + "property=" + property.getURI() + '}';
        }
        return "JenaPersistanceProperty{" + "unknown" + '}';
    }


}
