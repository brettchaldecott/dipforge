/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicJDOResourceInvocationHandler.java
 */
// package path
package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.ResourceException;
import com.rift.coad.rdf.semantic.jdo.obj.MethodInfo;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.*;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import com.rift.coad.rdf.semantic.util.RDFURIHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.cglib.proxy.InvocationHandler;
import org.apache.log4j.Logger;

/**
 * This object is responsible for invoking the resource calls.
 *
 * @author brett chaldecott
 */
public class BasicJDOResourceInvocationHandler implements InvocationHandler {

    // class constants
    private final static String GET_URI = "getURI";
    private final static String GET = "get";
    private final static String ADD_PROPERTY = "addProperty";
    private final static String SET_PROPERTY = "setProperty";
    private final static String GET_PROPERTY = "getProperty";
    private final static String REMOVE_PROPERTY = "removeProperty";
    private final static String REMOVE_PROPERTY_RESOURCE = "removePropertyResource";
    private final static String LIST_PROPERTIES = "listProperties";
    
    // class singletons
    private static Logger log = Logger.getLogger(BasicJDOInvocationHandler.class);
    // private member variables
    private PersistanceSession persistanceSession;
    private PersistanceResource resource;
    private OntologySession ontologySession;
    private OntologyClass ontologyClass;

    /**
     * This constructor sets up the invocation handler.
     *
     * @param persistanceSession The session that the resource is from.
     * @param resource The resource.
     */
    public BasicJDOResourceInvocationHandler(
            PersistanceSession persistanceSession, PersistanceResource resource,
            OntologySession ontologySession) throws BasicJDOException {
        this.persistanceSession = persistanceSession;
        this.resource = resource;
        this.ontologySession = ontologySession;

        // retrieve the ontology class
        try {
                PersistanceIdentifier typeIdentifier = PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE, RDFConstants.TYPE_LOCALNAME);
            ontologyClass = ontologySession.getClass(
                    resource.getProperty(typeIdentifier).getValueAsResource().getURI());
        } catch (Exception ex) {
            log.error("Failed to retrieve the resource type information : "
                    + ex.getMessage(), ex);
            throw new BasicJDOException("Failed to retrieve the resource type information : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method invokes the method
     *
     * @param method The method information inorder to identify the request.
     * @param args The arguments.
     * @return The result of the call.
     * @throws Throwable The cause of the exception.
     * @throws InvocationTargetException
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invoke(method, args);
    }

    /**
     * The invoke method.
     *
     * @param info The method to invoke
     * @param args The arguments for the method.
     * @return The result object
     * @throws Throwable
     * @throws InvocationTargetException
     */
    public Object invoke(Method info, Object[] args) throws
            Throwable, InvocationTargetException {
        if (info.getName().equals(GET_URI)) {
            return getURI();
        } else if (info.getName().equals(GET)) {
            return get(args);
        } else if (info.getName().equals(ADD_PROPERTY)) {
            return addProperty(args);
        } else if (info.getName().equals(SET_PROPERTY)) {
            return setProperty(args);
        } else if (info.getName().equals(GET_PROPERTY)) {
            return getProperty(args);
        } else if (info.getName().equals(REMOVE_PROPERTY)) {
            return removeProperty(args);
        } else if (info.getName().equals(
                REMOVE_PROPERTY_RESOURCE)) {
            return removePropertyResource(args);
        } else if (info.getName().equals(LIST_PROPERTIES) && args.length == 0) {
            return listProperties();
        } else if (info.getName().equals(LIST_PROPERTIES) && args.length == 1) {
            return listProperties((String)args[0]);
        } 
        return null;
    }

    /**
     * This method returns the URI of this object.
     *
     * @return The uri of this object.
     * @throws ResourceException
     */
    private Object getURI() throws ResourceException {
        try {
            return resource.getURI();
        } catch (Exception ex) {
            log.error("Failed to get the uri because : "
                    + ex.getMessage(), ex);
            throw new ResourceException("Failed to get the uri because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the object instance identifed by the type
     * information.
     *
     * @param <T> The type information for this call.
     * @param t The type to perform the cast to.
     * @return This method contains the type information.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    private Object get(Object[] args) throws ResourceException {
        try {
            Class type = (Class)args[0];
            if (type.equals(String.class)) {
                return this.resource.getValueAsString();
            } else if (type.equals(Date.class)) {
                return new Date(this.resource.getValueAsCalendar().getTimeInMillis());
            } else if (type.equals(Calendar.class)) {
                return this.resource.getValueAsCalendar();
            } else if (type.equals(Integer.class)) {
                return (Integer) (int) this.resource.getValueAsLong();
            } else if (type.equals(int.class)) {
                return (int) this.resource.getValueAsLong();
            } else if (type.equals(Long.class)) {
                return (Long) this.resource.getValueAsLong();
            } else if (type.equals(long.class)) {
                return (long) this.resource.getValueAsLong();
            } else if (type.equals(Double.class)) {
                return (Double) this.resource.getValueAsDouble();
            } else if (type.equals(double.class)) {
                return (double)this.resource.getValueAsDouble();
            } else if (type.equals(Float.class)) {
                return (Float) this.resource.getValueAsFloat();
            } else if (type.equals(float.class)) {
                return (float) this.resource.getValueAsFloat();
            } else if (type.equals(boolean.class)) {
                return (boolean)this.resource.getValueAsBoolean();
            } else if (type.equals(Boolean.class)) {
                return (Boolean)this.resource.getValueAsBoolean();
            } else {
                return BasicJDOProxyFactory.createJDOProxy((Class) args[0],
                        persistanceSession, resource, ontologySession);
            }
        } catch (Exception ex) {
            log.error("Failed to get the uri because : "
                    + ex.getMessage(), ex);
            throw new ResourceException("Failed to get the uri because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * The method adds properties to the resource.
     *
     * @param url The url for the property.
     * @param value The value to add.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    private Object addProperty(Object[] args) throws ResourceException {
        try {
            RDFURIHelper uriHelper = new RDFURIHelper((String) args[0]);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    uriHelper.getNamespace(), uriHelper.getLocalName());

            if (ClassTypeInfo.isBasicType(args[1].getClass())) {
                PersistanceProperty property =
                        this.resource.createProperty(identifier);
                setBasicProperty(property, args[1]);
                return args[1];
            } else {
                BasicJDOPersistanceHandler handler = new BasicJDOPersistanceHandler(
                        args[1], persistanceSession, ontologySession);
                PersistanceResource propertyResource = handler.persist();
                this.resource.createProperty(identifier).setValue(propertyResource);
                if (args[1] instanceof Resource) {
                    return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                            persistanceSession, propertyResource, ontologySession);
                } else {
                    return BasicJDOProxyFactory.createJDOProxy(args[1].getClass(),
                            persistanceSession, propertyResource, ontologySession);
                }
            }
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the object : " + ex.getMessage(), ex);
            throw new ResourceException("Failed to persist the object : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * The method adds properties to the resource.
     *
     * @param url The url for the property.
     * @param value The value to add.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    private Object setProperty(Object[] args) throws ResourceException {
        try {
            RDFURIHelper uriHelper = new RDFURIHelper((String) args[0]);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    uriHelper.getNamespace(), uriHelper.getLocalName());

            if (ClassTypeInfo.isBasicType(args[1].getClass())) {
                PersistanceProperty property =
                        this.resource.getProperty(identifier);
                setBasicProperty(property, args[1]);
                return args[1];
            } else {
                BasicJDOPersistanceHandler handler = new BasicJDOPersistanceHandler(
                        args[1], persistanceSession, ontologySession);
                PersistanceResource propertyResource = handler.persist();
                this.resource.getProperty(identifier).setValue(propertyResource);
                if (args[1] instanceof Resource) {
                    return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                            persistanceSession, propertyResource, ontologySession);
                } else {
                    return BasicJDOProxyFactory.createJDOProxy(args[1].getClass(),
                            persistanceSession, propertyResource, ontologySession);
                }
            }
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the object : " + ex.getMessage(), ex);
            throw new ResourceException("Failed to persist the object : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method sets the property value to the object supplied.
     *
     * @param property The property value.
     * @param value The value
     * @throws ResourceException
     */
    private void setBasicProperty(PersistanceProperty property, Object value)
            throws ResourceException, PersistanceException {
        if (value instanceof String) {
            property.setValue(value.toString());
        } else if (value instanceof Date) {
            Date dateValue = (Date) value;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateValue);
            property.setValue(calendar);
        } else if (value instanceof Calendar) {
            property.setValue((Calendar) value);
        } else if (value instanceof Integer) {
            property.setValue((long) (Integer) value);
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
        } else if (value.getClass().equals(boolean.class)) {
            property.setValue(boolean.class.cast(value));
        } else if (value.getClass().equals(Boolean.class)) {
            property.setValue(Boolean.class.cast(value));
        } else {
            throw new ResourceException("Unsupported type ["
                    + value.getClass().getName() + "]");
        }
    }

    /**
     * The method adds properties to the resource.
     *
     * @param url The url for the property.
     * @param obj The object to add to the property.
     * @return The resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public Object getProperty(Object[] args) throws ResourceException {
        try {
            Class type = (Class) args[0];
            RDFURIHelper uriHelper = new RDFURIHelper((String) args[1]);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    uriHelper.getNamespace(), uriHelper.getLocalName());
            // check for type uri
            PersistanceIdentifier typeIdentifier = PersistanceIdentifier.
                    getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            if (identifier.toURI().equals(typeIdentifier.toURI()) && 
                    type.equals(OntologyClass.class)) {
                return this.ontologyClass;
            } else if (identifier.toURI().equals(typeIdentifier.toURI()) && 
                    !type.equals(OntologyClass.class)) {
                throw new ResourceException(
                        "Cannot return the OntologyClass as a resource");
            }
            
            PersistanceProperty property = this.resource.getProperty(identifier);
            if (type.equals(String.class)) {
                return property.getValueAsString();
            } else if (type.equals(Date.class)) {
                return new Date(property.getValueAsCalendar().getTimeInMillis());
            } else if (type.equals(Calendar.class)) {
                return property.getValueAsCalendar();
            } else if (type.equals(Integer.class)) {
                return (Integer) (int) property.getValueAsLong();
            } else if (type.equals(int.class)) {
                return (int) property.getValueAsLong();
            } else if (type.equals(Long.class)) {
                return (Long) property.getValueAsLong();
            } else if (type.equals(long.class)) {
                return (long) property.getValueAsLong();
            } else if (type.equals(Double.class)) {
                return (Double) property.getValueAsDouble();
            } else if (type.equals(double.class)) {
                return (double) property.getValueAsDouble();
            } else if (type.equals(Float.class)) {
                return (Float) property.getValueAsFloat();
            } else if (type.equals(float.class)) {
                return (float) property.getValueAsFloat();
            } else if (type.equals(boolean.class)) {
                return (boolean)property.getValueAsBoolean();
            } else if (type.equals(Boolean.class)) {
                return (Boolean)property.getValueAsBoolean();
            } else {
                return BasicJDOProxyFactory.createJDOProxy(type,
                        persistanceSession, this.resource.getProperty(identifier).
                        getValueAsResource(), ontologySession);
            }
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the property : " + ex.getMessage(), ex);
            throw new ResourceException("Failed to get the property : " + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method removes the property.
     *
     * @param url The url of the propety.
     * @param objectType The object type.
     * @param identifier The identifier.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public Object removeProperty(Object[] args) throws ResourceException {
        try {
            RDFURIHelper uriHelper = new RDFURIHelper((String) args[0]);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    uriHelper.getNamespace(), uriHelper.getLocalName());
            this.resource.removeProperty(identifier);
            return null;
        } catch (Exception ex) {
            log.error("Failed to remove the property : " + ex.getMessage(), ex);
            throw new ResourceException("Failed to remove the property : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to remove the property from the resource.
     *
     * @param url The url of the property to remove.
     * @param resource The resource name
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public Object removePropertyResource(Object[] args) throws ResourceException {
        try {
            RDFURIHelper uriHelper = new RDFURIHelper((String) args[0]);
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    uriHelper.getNamespace(), uriHelper.getLocalName());
            PersistanceResource persisanceResource =
                    persistanceSession.getResource(
                    Resource.class.cast(args[1]).getURI());
            this.resource.removeProperty(identifier, persisanceResource);
            return null;
        } catch (Exception ex) {
            log.error("Failed to remove the property : " + ex.getMessage(), ex);
            throw new ResourceException("Failed to remove the property : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method lists properties.
     *
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List listProperties() throws ResourceException {
        try {
            List<PersistanceProperty> resourceProperties = resource.listProperties();
            List resultList = new ArrayList();

            for (PersistanceProperty resourceProperty : resourceProperties) {
                // check for type uri
                PersistanceIdentifier typeIdentifier = PersistanceIdentifier.
                        getInstance(RDFConstants.SYNTAX_NAMESPACE,
                        RDFConstants.TYPE_LOCALNAME);
                if (resourceProperty.getURI().equals(typeIdentifier.toURI())) {
                    resultList.add(ontologyClass.getURI().toString());
                    continue;
                }
                OntologyProperty ontologyProperty = ontologyClass.getProperty(resourceProperty.getURI());
                DataType typeName = ontologyProperty.getType();
                if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsString());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_BOOLEAN).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsBoolean());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_FLOAT).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsFloat());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DOUBLE).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsDouble());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_INTEGER).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            new Long(resourceProperty.getValueAsLong()).intValue());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_LONG).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsLong());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_INT).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            new Long(resourceProperty.getValueAsLong()).intValue());
                } else if ((XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_SHORT).getURI()
                        .equals(typeName.getURI()))
                        || (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_BYTE).getURI()
                        .equals(typeName.getURI()))) {
                    // type is currently not supported
                    throw new ResourceException("Unsupported data type of byte");
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DATE).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsCalendar());
                } else {
                    resultList.add(BasicJDOProxyFactory.createJDOProxy(Resource.class,
                            persistanceSession, resourceProperty.getValueAsResource(),
                            ontologySession));
                }
            }

            return resultList;
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list or properties : "
                    + ex.getMessage(), ex);
            throw new ResourceException("Failed to retrieve the list or properties : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method lists properties.
     *
     * @param url The url of the property to retrieve.
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List listProperties(String url) throws ResourceException {
        try {
            RDFURIHelper helper = new RDFURIHelper(url);
            PersistanceIdentifier listIdentifier = 
                    PersistanceIdentifier.getInstance(helper.getNamespace(), 
                    helper.getLocalName());
            List<PersistanceProperty> resourceProperties =
                    resource.listProperties(listIdentifier);
            List resultList = new ArrayList();

            for (PersistanceProperty resourceProperty : resourceProperties) {
                // check for type uri
                PersistanceIdentifier typeIdentifier = PersistanceIdentifier.
                        getInstance(RDFConstants.SYNTAX_NAMESPACE,
                        RDFConstants.TYPE_LOCALNAME);
                if (resourceProperty.getURI().equals(typeIdentifier.toURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            ontologyClass.getURI().toString());
                    continue;
                }
                OntologyProperty ontologyProperty = ontologyClass.getProperty(resourceProperty.getURI());
                DataType typeName = ontologyProperty.getType();
                if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsString());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_BOOLEAN).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsBoolean());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_FLOAT).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsFloat());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DOUBLE).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsDouble());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_INTEGER).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            new Long(resourceProperty.getValueAsLong()).intValue());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_LONG).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsLong());
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_INT).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            new Long(resourceProperty.getValueAsLong()).intValue());
                } else if ((XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_SHORT).getURI()
                        .equals(typeName.getURI()))
                        || (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_BYTE).getURI()
                        .equals(typeName.getURI()))) {
                    // type is currently not supported
                    throw new ResourceException("Unsupported data type of byte");
                } else if (XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DATE).getURI()
                        .equals(typeName.getURI())) {
                    resultList.add(resourceProperty.getURI().toString() + ":" + 
                            resourceProperty.getValueAsCalendar());
                } else {
                    resultList.add(BasicJDOProxyFactory.createJDOProxy(Resource.class,
                            persistanceSession, resourceProperty.getValueAsResource(),
                            ontologySession));
                }
            }

            return resultList;
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list or properties : "
                    + ex.getMessage(), ex);
            throw new ResourceException("Failed to retrieve the list or properties : "
                    + ex.getMessage(), ex);
        }
    }
}
