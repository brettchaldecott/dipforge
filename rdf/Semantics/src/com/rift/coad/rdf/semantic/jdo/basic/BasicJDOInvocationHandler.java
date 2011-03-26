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
 * BasicJDOInvocationHandler.java
 */

// package path
package com.rift.coad.rdf.semantic.jdo.basic;

// java imports
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.jdo.basic.collection.BasicJDOList;
import com.rift.coad.rdf.semantic.jdo.mapping.JavaRDFTypeMapping;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import com.rift.coad.rdf.semantic.jdo.obj.MethodInfo;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.ReflectUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author brett chaldecott
 */
public class BasicJDOInvocationHandler implements MethodInterceptor {

    // class singletons
    private static Logger log = Logger.getLogger(BasicJDOInvocationHandler.class);
    // private member variablees
    private Class type;
    private ClassInfo classInfo;
    private PersistanceSession persistanceSession;
    private PersistanceResource resource;
    private OntologySession ontologySession;
    private OntologyClass ontologyClass;
    private BasicJDOResourceInvocationHandler resourceInvocationHandler;
    private Map<String,Object> checkMap = new HashMap<String,Object>();
    private Object ref;

    /**
     * The constructor of the basic jdo invocation handler.
     *
     * @param type The class type.
     * @param resource The resource information.
     * @throws BasicJDOException
     */
    public BasicJDOInvocationHandler(Class type, PersistanceSession persistanceSession,
            PersistanceResource resource,
            OntologySession ontologySession)
            throws BasicJDOException {
        try {
            this.type = type;
            this.persistanceSession = persistanceSession;
            this.ontologySession = ontologySession;
            classInfo = ClassInfo.interrogateClass(type);
            ontologyClass = ontologySession.getClass(
                    PersistanceIdentifier.getInstance(classInfo.getNamespace(),
                    classInfo.getLocalName()).toURI());
            this.resource = resource;
            this.resourceInvocationHandler =
                    new BasicJDOResourceInvocationHandler(
                    persistanceSession, resource,
                    ontologySession);
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to instanciate the basic jdo "
                    + "invocation handler : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method is used to init the object. This is done by calling all the getters.
     *
     * @param ref The reference to the object to init.
     */
    public void initObject(Object ref) {
        try {
            for (MethodInfo method : classInfo.getGetters()) {
                Method methodRef = method.getMethodRef();
                if (ClassTypeInfo.isBasicType(methodRef.getReturnType())) {
                    methodRef.invoke(ref, new Object[0]);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to initialize the object "
                    + "because : " + ex.getMessage(), ex);
            throw new BasicJDOException("Failed to initialize the object "
                    + "because : " + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method is responsible to handling the invocation request.
     * 
     * @param proxy The proxy interface the call was invoked on.
     * @param method The method to perform the invocation call on.
     * @param args The list of arguments to pass to the method.
     * @return The result of the call.
     * @throws Throwable
     */
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
            MethodProxy proxy) throws Throwable {
        try {
            if (!typeHasMethod(method)) {
                return invokeResource(method, args);
            } else {
                return invokeObject(obj, proxy, method, args);
            }
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to invoke the call : " + ex.getMessage(), ex);
            throw ex;
        }
    }


    /**
     * This method invokes the call onto the object that is being wrapped.
     *
     * @param proxy The proxy object that the called is being mapped form.
     * @param method The method to call.
     * @param args The arguments to call.
     * @return The returns of the call.
     * @throws Throwable
     */
    private Object invokeObject(Object obj, MethodProxy proxy, Method method, Object[] args)
            throws Throwable, InvocationTargetException {
        try {
            MethodInfo info = new MethodInfo(method, classInfo.getNamespace());
            PersistanceIdentifier identifier =
                    PersistanceIdentifier.getInstance(
                    info.getNamespace(), info.getLocalName());
            PersistanceProperty property = resource.getProperty(identifier);
            if (info.isGetter()) {
                return getResult(obj,proxy,info, args);
            } else if (info.isSetter()) {
                Object value = args[0];
                persistObject(obj,proxy, method, args, value, info,resource,
                        identifier,ontologyClass);
                return null;
            } else {
                return proxy.invokeSuper(obj, args);
            }
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to invoke the call : " + ex.getMessage(), ex);
            throw ex;
        }
    }

    
    /**
     * This method is called to invoke the call on the persistance resource on behalf of an
     * interface resource.
     *
     * @param proxy The interface type that that call was made on.
     * @param method The method that is being called
     * @param args The list of arguments.
     * @return The object retrieved form the resource.
     * @throws Throwable
     */
    private Object invokeResource(Method method, Object[] args)
            throws Throwable, InvocationTargetException {
        try {
            return resourceInvocationHandler.invoke(method, args);
        } catch (Throwable ex) {
            log.error("Failed to invoke the call : " + ex.getMessage(), ex);
            throw ex;
        }
    }


    /**
     * This method returns the result from the call.
     *
     * @param info The method information.
     * @param args The list of arguments.
     * @return The result object retrieved.
     * @throws BasicJDOException
     */
    public Object getResult(Object obj, MethodProxy proxy, MethodInfo info, Object[] args)
            throws BasicJDOException, InvocationTargetException {
        try {
            Class classType = info.getMethodRef().getReturnType();
            PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                    info.getNamespace(), info.getLocalName());
            String methodName = generateGenericMethodName(info.getMethodRef());
            if (checkMap.containsKey(methodName)) {
                return proxy.invokeSuper(obj, args);
            }
            Object result = null;
            if (ClassTypeInfo.isBasicType(classType)) {
                result = getBasicObject(info, identifier, classType);
            } else if (ClassTypeInfo.isCollection(classType)) {
                result = getCollectionObject(info, resource,identifier, classType);
            } else {
                result = BasicJDOProxyFactory.createJDOProxy(classType,
                        persistanceSession, resource.getProperty(identifier).
                        getValueAsResource(), ontologySession);
            }
            checkMap.put(methodName, result);
            MethodProxy setProxy = proxy.find(obj.getClass(),
                    ReflectUtils.getSignature(type.getMethod("s" + methodName, 
                    new Class[] {info.getReturnType()} )));
            setProxy.invokeSuper(obj, new Object[] {result} );
            return result;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to get the result because : " + ex.getMessage(),ex);
            throw new BasicJDOException
                    ("Failed to get the result because : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method is called to persist an object.
     *
     * @param value
     * @param methodInfo
     * @param resource
     * @param identifier
     * @param ontologyClass
     * @throws BasicJDOException
     */
    private void persistObject(Object obj, MethodProxy proxy, Method method, Object[] args,
            Object value, MethodInfo info, PersistanceResource resource, PersistanceIdentifier identifier,
            OntologyClass ontologyClass) throws BasicJDOException, InvocationTargetException {
        try {
            String methodName = generateGenericMethodName(info.getMethodRef());
            Class type = value.getClass();
            if (ClassTypeInfo.isBasicType(type)) {
                persistBasicType(value, info, resource, identifier,
                        ontologyClass);
                proxy.invokeSuper(obj, args);
            } else if (type.isArray()) {
                throw new BasicJDOException("The array type is not supported");
            } else if (ClassTypeInfo.isCollection(type)) {
                checkMap.remove(methodName);
                persistCollection(value, info, resource, identifier, ontologyClass);
            } else {
                checkMap.remove(methodName);
                BasicJDOPersistanceHandler handler =
                            new BasicJDOPersistanceHandler(value, persistanceSession,
                            ontologySession);
                PersistanceResource childResource = handler.persist();
                resource.createProperty(identifier).setValue(childResource);
            }
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to persist the object : "
                    + ex.getMessage(), ex);
            throw new BasicJDOException(
                    "Failed to persist the object : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method is called to persist the basic type
     *
     * @param methodInfo The method to persist the basic type.
     * @param resource The resource reference
     * @throws BasicJDOException
     */
    private void persistBasicType(Object value, MethodInfo methodInfo,
            PersistanceResource resource, PersistanceIdentifier identifier,
            OntologyClass ontologyClass)
            throws BasicJDOException, InvocationTargetException {
        try {
            resource.removeProperty(identifier);
            if (value == null) {
                return;
            }
            if (!ontologyClass.hasProperty(identifier.toURI())) {
                throw new BasicJDOException("The ontology class does not have the property : "
                        + identifier.toURI().toString());
            }
            OntologyProperty ontologyProperty = ontologyClass.getProperty(identifier.toURI());
            if (!ontologyProperty.getType().getURI().equals(
                    JavaRDFTypeMapping.getRDFTypeURI(value.getClass()).getURI())) {
                throw new BasicJDOException("The ontology type requires a type of ["
                        + ontologyProperty.getURI().toString() + "] but received ["
                        + identifier.toURI().toString());
            }
            PersistanceProperty property =
                    resource.createProperty(identifier);
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
            } else {
                throw new BasicJDOException("Unsupported type ["
                        + value.getClass().getName() + "]");
            }
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the basic type : "
                    + ex.getMessage(), ex);
            throw new BasicJDOException("Failed to persist the basic type : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the basic objects.
     *
     * @param info The information about the method that is being invoked.
     * @param args The arguments being invoked for the method.
     * @return The object that results from the call.
     */
    private Object getBasicObject(MethodInfo info, PersistanceIdentifier identifier, Class classType)
            throws BasicJDOException {
        try {
            
            if (!resource.hasProperty(identifier)) {
                throw new BasicJDOException(
                        "The property [" + identifier.toURI().toString() +
                        "] does not exist for this resource [" +
                        resource.getURI().toString() + "]");
            }
            PersistanceProperty property =
                    resource.getProperty(identifier);
            return getBasicTypeFromProperty(property, classType);
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed get the basic object information because : " + ex.getMessage(),ex);
            throw new BasicJDOException
                    ("Failed get the basic object information because : " + ex.getMessage(),ex);
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
    private void persistCollection(Object value, MethodInfo methodInfo,
            PersistanceResource resource, PersistanceIdentifier identifier,
            OntologyClass ontologyClass)
            throws BasicJDOException, InvocationTargetException {
        try {
            resource.removeProperty(identifier);
            Collection collection =
                    (Collection) value;
            for (Object obj : collection) {
                if (obj == null) {
                    // ignore a null value
                    continue;
                }
                // if this object is a basic type it cannot be stored like this.
                if (ClassTypeInfo.isBasicType(obj.getClass())) {
                    persistBasicType(obj, methodInfo, resource, identifier,
                            ontologyClass);

                } else {
                    BasicJDOPersistanceHandler handler =
                            new BasicJDOPersistanceHandler(obj, persistanceSession,
                            ontologySession);
                    PersistanceResource childResource = handler.persist();
                    resource.createProperty(identifier).setValue(childResource);
                }
            }
        } catch (InvocationTargetException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the collection : "
                    + ex.getMessage(), ex);
            throw new BasicJDOException("Failed to persist the collection : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the colletion information.
     *
     * @param info The reference to the metho.
     * @param identifier The identifier for the entry
     * @param classType The class type.
     * @return
     * @throws BasicJDOException
     */
    private Object getCollectionObject(MethodInfo info, PersistanceResource resource,
            PersistanceIdentifier identifier, Class classType)
            throws BasicJDOException {
        try {
            
            Class objectType = info.getParameterType();
            List base = new ArrayList();
            List<PersistanceProperty> properties = resource.listProperties(identifier);
            for (PersistanceProperty property : properties) {
                // this logic makes the assumption that there will not be lists of lists
                if (ClassTypeInfo.isBasicType(objectType)) {
                    base.add(getBasicTypeFromProperty(property,objectType));
                } else {
                    base.add(BasicJDOProxyFactory.createJDOProxy(objectType,
                        persistanceSession, property.getValueAsResource(),
                        ontologySession));
                }
            }
            
            BasicJDOList result = new BasicJDOList(base, persistanceSession,
                ontologySession,identifier);
            return base;
        } catch (Exception ex) {
            log.error("Failed to retrieve the collection : "
                    + ex.getMessage(), ex);
            throw new BasicJDOException("Failed to retrieve the collection : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method returns the basic property from the object.
     *
     * @param property
     * @param classType
     * @return
     * @throws BasicJDOException
     */
    private Object getBasicTypeFromProperty(
            PersistanceProperty property, Class classType)
            throws BasicJDOException {
        try  {
            if (String.class.equals(classType)) {
                return property.getValueAsString();
            } else if (Date.class.equals(classType)) {
                Calendar calendar = property.getValueAsCalendar();
                return calendar.getTime();
            } else if (Calendar.class.equals(classType)) {
                return property.getValueAsCalendar();
            } else if (Long.class.equals(classType)) {
                return new Long(property.getValueAsLong()).intValue();
            } else if (classType.equals(int.class)) {
                return new Long(property.getValueAsLong()).intValue();
            } else if (Long.class.equals(classType)) {
                return property.getValueAsLong();
            } else if (classType.equals(long.class)) {
                return property.getValueAsLong();
            } else if (Double.class.equals(classType)) {
                return property.getValueAsDouble();
            } else if (classType.equals(double.class)) {
                return property.getValueAsDouble();
            } else if (Float.class.equals(classType)) {
                return property.getValueAsFloat();
            } else if (classType.equals(float.class)) {
                return property.getValueAsFloat();
            } else {
                throw new BasicJDOException("Unsupported type ["
                        + classType.getName() + "]");
            }
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed get the basic object information because : " + ex.getMessage(),ex);
            throw new BasicJDOException
                    ("Failed get the basic object information because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns true if the type is contained on this object.
     *
     * @param method The method to perform the check on.
     */
    private boolean typeHasMethod(Method method) {
        try {
            type.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }


    /**
     * This method generates a generic method name using the method passed in.
     * @param method The method name
     * @return The string result from this manipulation
     */
    private String generateGenericMethodName(Method method) {
        return method.getName().substring(1);
    }
}
