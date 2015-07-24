/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicJDOPersistanceHandler.java
 */
// package path
package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.Constants;
import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.jdo.mapping.JavaRDFTypeMapping;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import com.rift.coad.rdf.semantic.jdo.obj.MethodInfo;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.DefaultPersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceSession;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import com.rift.coad.rdf.semantic.util.ClassURIBuilder;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * This object is responsible for
 * @author brett chaldecott
 */
public class BasicJDOPersistanceHandler {

    private Object dataSource;
    private PersistanceSession session;
    private OntologySession ontologySession;

    public BasicJDOPersistanceHandler(Object dataSource,
            PersistanceSession session, OntologySession ontologySession) {
        this.dataSource = dataSource;
        this.session = session;
        this.ontologySession = ontologySession;
    }


    /**
     * This method is called to persist the object to the session
     * @return
     * @throws BasicJDOException
     */
    public PersistanceResource persist() throws BasicJDOException, InvocationTargetException {
        PersistanceManager persistance = null;
        try {
            PersistanceResource resource = persist(dataSource);
            return resource;
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source : "
                    + ex.getMessage(), ex);
        } finally {
            try {
                persistance.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }


    /**
     * This method is responsible for the persistence session.
     *
     * @param dataSource The data source that has to be persisted.
     * @return The uri for the object being persisted.
     * @throws BasicJDOException
     */
    private PersistanceResource persist(Object dataSource)
            throws BasicJDOException, InvocationTargetException {
        try {
            if (dataSource == null) {
                return null;
            }
            if (dataSource instanceof Resource) {
                return getPersistanceResource((Resource)dataSource);
            }
            ClassInfo classInfo = ClassInfo.interrogateClass(dataSource.getClass());
            OntologyClass ontologyClass = ontologySession.getClass(
                    PersistanceIdentifier.getInstance(classInfo.getNamespace(),
                    classInfo.getLocalName()).toURI());
//            System.out.println(classInfo.getNamespace() + "#" +
//                    classInfo.getLocalName() + "/" +
//                    classInfo.getIdMethod().getMethodRef().getName());
            URI resourceUri = ClassURIBuilder.generateClassURI(dataSource.getClass(),
                    classInfo.getIdMethod().getMethodRef().invoke(dataSource).toString());
            PersistanceResource typeResource = session.createResource(PersistanceIdentifier.getInstance(
                    classInfo.getNamespace(), classInfo.getLocalName()));
            PersistanceResource resource = session.createResource(
                    resourceUri,typeResource);
            for (MethodInfo methodInfo : classInfo.getGetters()) {
                PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                        methodInfo.getNamespace(), methodInfo.getLocalName());
                Class returnType =
                        methodInfo.getMethodRef().getReturnType();
                if (ClassTypeInfo.isBasicType(returnType)) {
                    persistBasicType(dataSource,methodInfo, resource, identifier,
                            ontologyClass);
                } else if (returnType.isArray()) {
                    throw new BasicJDOException("The array type is not supported");
                } else if (ClassTypeInfo.isCollection(returnType)) {
                    persistCollection(dataSource,methodInfo, resource, identifier);
                } else {
                    this.persistObject(dataSource,methodInfo, resource, identifier);
                }
            }
            return resource;
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source ["
                    + dataSource.getClass().getName() + "]: " + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to persist the basic type
     *
     * @param methodInfo The method to persist the basic type.
     * @param resource The resource reference
     * @throws BasicJDOException
     */
    private void persistBasicType(Object dataSource, MethodInfo methodInfo,
            PersistanceResource resource, PersistanceIdentifier identifier,
            OntologyClass ontologyClass)
            throws BasicJDOException, InvocationTargetException {
        try {
            resource.removeProperty(identifier);
            Object value = methodInfo.getMethodRef().invoke(dataSource);
            if (value == null) {
                return;
            }
            if (!ontologyClass.hasProperty(identifier.toURI())) {
                throw new BasicJDOException("The ontology class does not have the property : " +
                        identifier.toURI().toString());
            }
            OntologyProperty ontologyProperty = ontologyClass.getProperty(identifier.toURI());
            if (!ontologyProperty.getType().getURI().equals(
                    JavaRDFTypeMapping.getRDFTypeURI(value.getClass()).getURI()) ) {
                throw new BasicJDOException("The ontology type requires a type of [" +
                        ontologyProperty.getURI().toString() + "] but received [" +
                        identifier.toURI().toString());
            }
            PersistanceProperty property =
                    resource.createProperty(identifier);
            if (value instanceof String) {
                property.setValue(value.toString());
            } else if (value instanceof Date) {
                Date dateValue = (Date)value;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateValue);
                property.setValue(calendar);
            } else if (value instanceof Calendar) {
                property.setValue((Calendar)value);
            } else if (value instanceof Integer) {
                property.setValue((long)(Integer)value);
            } else if (value.getClass().equals(int.class)) {
                property.setValue(long.class.cast(value));
            } else if (value instanceof Long) {
                property.setValue(Long.class.cast(value));
            } else if (value.getClass().equals(long.class)) {
                property.setValue(long.class.cast(value));
            } else if (value instanceof Double) {
                property.setValue(Double.class.cast(value));
            } else if (value.getClass().equals(double.class)) {
                property.setValue(double.class.cast(value));
            } else if (value instanceof Float) {
                property.setValue(Float.class.cast(value));
            } else if (value.getClass().equals(float.class)) {
                property.setValue(float.class.cast(value));
            } else {
                throw new BasicJDOException("Unsupported type [" +
                        value.getClass().getName() + "]");
            }
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the basic type : "
                    + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method persists the collection information
     *
     * @param methodInfo The method information.
     * @param resource The resource information.
     * @param identifier The identifier.
     * @throws BasicJDOException
     */
    private void persistCollection(Object dataSource, MethodInfo methodInfo,
            PersistanceResource resource,PersistanceIdentifier identifier)
            throws BasicJDOException, InvocationTargetException {
        try {
            resource.removeProperty(identifier);
            Collection collection =
                    (Collection) methodInfo.getMethodRef().invoke(dataSource);
            for (Object obj : collection) {
                if (obj == null) {
                    // ignore a null value
                    continue;
                }
                // if this object is a basic type it cannot be stored like this.
                if (ClassTypeInfo.isBasicType(obj.getClass())) {
                    resource.createProperty(identifier).setValue(obj.toString());

                } else {
                    PersistanceResource childResource = persist(obj);
                    if (childResource != null) {
                        resource.createProperty(identifier).setValue(childResource);
                    }
                }
            }
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the collection : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * Persist the object
     *
     * @param methodInfo The method reference.
     * @param resource The resource.
     * @param identifier The identifier.
     * @throws BasicJDOException
     */
    private void persistObject(Object dataSource, MethodInfo methodInfo,
            PersistanceResource resource, PersistanceIdentifier identifier)
            throws BasicJDOException, InvocationTargetException {
        try {
            resource.removeProperty(identifier);
            Object value = methodInfo.getMethodRef().invoke(dataSource);
            if (value == null) {
                return;
            }
            PersistanceProperty property =
                    resource.createProperty(identifier);
            property.setValue(
                    persist(value));
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the object : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the persistence resource from the session store
     *
     * @param dataSource The resource to retrieve.
     * @return The persistence resource to connect with.
     * @throws BasicJDOException
     */
    private PersistanceResource getPersistanceResource(Resource dataSource)
            throws BasicJDOException {
        try {
            return session.getResource(dataSource.getURI());
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the resource : " +
                    ex.getMessage(),ex);
        }
    }


}
