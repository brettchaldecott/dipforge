/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2012  Rift IT Contracting
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
 * BasicProperty.java
 */
package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.Property;
import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.ResourceException;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.util.RDFURIHelper;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

/**
 * This object represents the basic property information.
 * 
 * @author brett chaldecott
 */
public class BasicProperty implements Property {
    
    // private member variables
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;
    private PersistanceResource resource;
    private PersistanceProperty property;

    
    /**
     * The basic property information 
     */
    public BasicProperty(PersistanceSession persistanceSession,
            OntologySession ontologySession,
            PersistanceResource resource,
            PersistanceProperty property) {
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
        this.resource = resource;
        this.property = property;
    }
    
    
    /**
     * The get type uri.
     * 
     * @return The reference to the uri.
     * @throws ResourceException 
     */
    public URI getTypeURI() throws ResourceException {
        try {
            return property.getTypeURI();
        } catch (Exception ex) {
            throw new ResourceException("Failed to retrieve the type URI : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * TRUE if the basic type information.
     * 
     * @return TRUE if this is a basic type.
     * @throws ResourceException 
     */
    public boolean isBasicType() throws ResourceException {
        try {
            return resource.getProperty(property.getPersistanceIdentifier()).isBasicType();
        } catch (Exception ex) {
            throw new ResourceException("Failed to retrieve the basic type: " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns the requested type.
     * @param <T> The type information.
     * @param type The class type result.
     * @return The reference to the resulting object.
     * @throws ResourceException 
     */
    public <T> T get(Class<T> type) throws ResourceException {
        try {
            // check for type uri
            PersistanceIdentifier typeIdentifier = PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            if (this.property.getTypeURI().equals(typeIdentifier.toURI())
                    && type.equals(OntologyClass.class)) {
                return (T)ontologySession.getClass(
                        property.getURI());
            } else if (this.property.getTypeURI().equals(typeIdentifier.toURI())
                    && !type.equals(OntologyClass.class)) {
                throw new ResourceException(
                        "Cannot return the OntologyClass as a resource");
            }

            if (type.equals(String.class)) {
                return (T)property.getValueAsString();
            } else if (type.equals(Date.class)) {
                return (T)new Date(property.getValueAsCalendar().getTimeInMillis());
            } else if (type.equals(Calendar.class)) {
                return (T)property.getValueAsCalendar();
            } else if (type.equals(Integer.class)) {
                return (T)(Long)property.getValueAsLong();
            } else if (type.equals(int.class)) {
                return (T)(Integer)(int)property.getValueAsLong();
            } else if (type.equals(Long.class)) {
                return (T)(Long) property.getValueAsLong();
            } else if (type.equals(long.class)) {
                return (T)(Long) property.getValueAsLong();
            } else if (type.equals(Double.class)) {
                return (T)(Double) property.getValueAsDouble();
            } else if (type.equals(double.class)) {
                return (T)(Double) property.getValueAsDouble();
            } else if (type.equals(Float.class)) {
                return (T)(Float) property.getValueAsFloat();
            } else if (type.equals(float.class)) {
                return (T)(Float) property.getValueAsFloat();
            } else if (type.equals(boolean.class)) {
                return (T)(Boolean) property.getValueAsBoolean();
            } else if (type.equals(Boolean.class)) {
                return (T)(Boolean) property.getValueAsBoolean();
            } else {
                return BasicJDOProxyFactory.createJDOProxy(type,
                        persistanceSession, property.getValueAsResource(),
                        ontologySession);
            }
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResourceException("Failed to get the property : " + 
                    ex.getMessage(), ex);
        }
    }

    /**
     * The to string method.
     * 
     * @return 
     */
    @Override
    public String toString() {
        try {
            return "BasicProperty{" + "property=[" + 
                    property.getURI().toString() + ":" +
                    (property.isBasicType()? resource.getProperty(property.getPersistanceIdentifier()).getValueAsString() : 
                    resource.getProperty(property.getPersistanceIdentifier()).getValueAsResource().getURI().toString()) + '}';
        } catch (Exception ex) {
            ex.printStackTrace();
            return "N/A : " + ex.getMessage();
        }
    }
    
    
    
}
