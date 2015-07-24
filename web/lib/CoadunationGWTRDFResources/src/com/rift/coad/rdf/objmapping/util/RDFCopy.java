/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  2015 Burntjam
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
 * RDFCopy.java
 */

// the package path
package com.rift.coad.rdf.objmapping.util;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.lang.reflect.Array;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.util.Iterator;
import java.util.List;

/**
 * This object is responsible for copying the contents of an object either from
 * the client packages to the standard rdf names space or vice versa.
 *
 * @author brett chaldecott
 */
public class RDFCopy {

    /**
     * This method is responsible for copying the object value.
     * @param <T>
     * @param c
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    public static Object copyToClient(Object value) throws UtilException {
        try {
            // assume a default constructor
            if (value == null) {
                return null;
            }
            // assume a default constructor
            Object result = getClientType(value.getClass()).newInstance();

            for (Method getter : value.getClass().getMethods()) {
                try {
                    if (!getter.getName().matches("get[A-Za-z0-9-]*")) {
                        continue;
                    } else if (getter.getParameterTypes().length != 0) {
                        continue;
                    }
                    Method setter = null;
                    if (isBasicType(getter.getReturnType())) {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                            getter.getReturnType());
                        setter.invoke(result, new Object[]{getter.invoke(value, new Object[]{})});
                        continue;
                    } else {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                            getClientType(getter.getReturnType()));
                    }

                    // check for basic type
                    if (!getter.getReturnType().isArray() && !getter.getReturnType().isAssignableFrom(Collection.class)
                            && !getter.getReturnType().isAssignableFrom(List.class)) {
                        setter.invoke(result, new Object[]{copyToClient(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    } else if (getter.getReturnType().isArray()) {
                        setter.invoke(result, new Object[]{copyToClientArray(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    } else if (getter.getReturnType().isAssignableFrom(Collection.class) ||
                            !getter.getReturnType().isAssignableFrom(List.class)) {
                        setter.invoke(result, new Object[]{copyToClientCollection(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    }
                } catch (java.lang.NoSuchMethodException ex) {
                    // ignore
                }
            }

            return result;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy the object information : " +
                    ex.getMessage(), ex);
        }
    }


    /**
     * This method is responsible for copying the object value.
     * @param <T>
     * @param c
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    public static Object copyFromClient(Object value) throws UtilException {
        try {
            // assume a default constructor
            if (value == null) {
                return null;
            }

            Object result = getTypeFromClient(value.getClass()).newInstance();
            if (result instanceof com.rift.coad.rdf.objmapping.base.DataType) {
                com.rift.coad.rdf.objmapping.base.DataType copyValue = (com.rift.coad.rdf.objmapping.base.DataType)result;
                System.out.println("Target to copy : " + copyValue.getDataName());
                try {
                    System.out.println("Target value to copy : " + copyValue.toString());
                } catch (java.lang.NullPointerException ex) {
                    // ignore
                }
            }
            for (Method getter : value.getClass().getMethods()) {
                try {
                    if (!getter.getName().matches("get[A-Za-z0-9-]*")) {
                        continue;
                    } else if (getter.getParameterTypes().length != 0) {
                        continue;
                    }

                    Method setter = null;
                    if (isBasicType(getter.getReturnType())) {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                            getter.getReturnType());
                        setter.invoke(result, new Object[]{getter.invoke(value, new Object[]{})});
                        continue;
                    } else {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                            getTypeFromClient(getter.getReturnType()));
                    }

                    // check for basic type
                    if (!getter.getReturnType().isArray() && !getter.getReturnType().equals(Collection.class)) {
                        setter.invoke(result, new Object[]{copyFromClient(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    } else if (getter.getReturnType().isArray()) {
                        setter.invoke(result, new Object[]{copyFromClientArray(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    } else if (getter.getReturnType().equals(Collection.class)) {
                        setter.invoke(result, new Object[]{copyFromClientCollection(
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    }
                } catch (java.lang.NoSuchMethodException ex) {
                    // ignore
                }
            }
            if (value instanceof DataType) {
                DataType copyValue = (DataType)value;
                System.out.println("Value to copy : " + copyValue.getDataName());
                try {
                    System.out.println("Value to copy : " + copyValue.toString());
                } catch (java.lang.NullPointerException ex) {
                    // ignore
                }
            }
            
            return result;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy the copy the object information : " +
                    ex.getMessage(), ex);
        }
    }

    /**
     * This method returns TRUE if the type returns is of basic type.
     *
     * @param <T> The type of object handled by this object.
     * @param c The type being delt with.
     * @return TRUE if of basic type, FALSE if not.
     */
    private static <T> boolean isBasicType(Class<T> c) {
        if (c.equals(String.class)) {
            return true;
        } else if (c.equals(Date.class)) {
            return true;
        } else if (c.equals(Calendar.class)) {
            return true;
        } else if (c.equals(BigDecimal.class)) {
            return true;
        } else if (Integer.TYPE.equals(c)) {
            return true;
        } else if (Long.TYPE.equals(c)) {
            return true;
        } else if (Double.TYPE.equals(c)) {
            return true;
        } else if (Float.TYPE.equals(c)) {
            return true;
        } else if (Character.TYPE.equals(c)) {
            return true;
        } else if (Short.TYPE.equals(c)) {
            return true;
        } else if (c.isPrimitive()) {
            return true;
        }
        return false;
    }

    
    /**
     * This method assumes the client mapping to rdf object and attempts to
     * find the appropriate class in the current class loader.
     *
     * @param name The name of the class to perform the transformation on.
     * @return The resulting class object.
     */
    private static Class getClientType(Class rdfType) throws UtilException {
        try {
            // perform a search for the client version of the given type
            String name = rdfType.getName();
            int index = 0;
            int currentPos = name.length();
            while (-1 != (index = name.lastIndexOf(".", currentPos))) {
                String className = name.substring(0,index) + ".client" +
                        name.substring(index);
                try {
                    return Class.forName(className);
                } catch (java.lang.ClassNotFoundException ex) {
                    // ignore the exception
                }
                currentPos = index - 1;
            }
            // assume there is no matching type return the input value and hope
            return rdfType;
        } catch (Exception ex) {
            throw new UtilException("Failed to retrieve the client object : " +
                    ex.getMessage(), ex);
        }
    }

    /**
     * This method assumes the client mapping to rdf object and attempts to
     * find the appropriate class in the current class loader.
     *
     * @param name The name of the class to perform the transformation on.
     * @return The resulting class object.
     */
    private static Class getTypeFromClient(Class rdfType) throws UtilException {
        try {
            if (rdfType.isPrimitive()) {
                throw new UtilException("Failed to copy as primitive is not supported");
            }
            return Class.forName(rdfType.getName().replace(".client", ""));
        } catch (Exception ex) {
            throw new UtilException("Failed to retrieve the client object : " +
                    ex.getMessage(), ex);
        }
    }


    /**
     * This method is responsible for copying the object of type array to a matching
     * client object.
     *
     * @param internalArray The array to be copied
     * @return The resultant object.
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    public static Object copyToClientArray(Object internalArray) throws UtilException {
        try {
            if (internalArray == null) {
                return null;
            }
            Object resultArray = Array.newInstance(
                    getClientType(internalArray.getClass()).getComponentType(),
                    Array.getLength(internalArray));
            for (int index = 0; index < Array.getLength(internalArray); index++) {
                Object currentObj = Array.get(internalArray, index);
                if (isBasicType(currentObj.getClass())) {
                    Array.set(resultArray, index, currentObj);
                    continue;
                } else if (currentObj.getClass().equals(Collection.class)) {
                    Array.set(resultArray, index, copyToClientCollection(currentObj));
                    continue;
                } else if (currentObj.getClass().isArray()) {
                    Array.set(resultArray, index, copyToClientArray(currentObj));
                    continue;
                }
                Array.set(resultArray, index,copyToClient(currentObj));
            }
            return resultArray;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy the array : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method is used to copy the client collection object.
     */
    private static Object copyToClientCollection(Object collection) throws UtilException {
        try {
            if (collection == null) {
                return null;
            }
            Collection current = (Collection) collection;
            Collection result = (Collection) collection.getClass().newInstance();
            for (Iterator iter = current.iterator(); iter.hasNext();) {
                Object currentObj = iter.next();
                if (isBasicType(currentObj.getClass())) {
                    result.add(currentObj);
                    continue;
                } else if (currentObj.getClass().isArray()) {
                    result.add(copyToClientArray(currentObj));
                    continue;
                } else if (currentObj.getClass().equals(Collection.class)) {
                    result.add(copyToClientCollection(currentObj));
                    continue;
                }
                result.add(copyToClient(currentObj));
            }
            return result;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy the collection : ");
        }
    }


    /**
     * This method is responsible for copying the object of type array to a matching
     * client object.
     *
     * @param internalArray The array to be copied
     * @return The resultant object.
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    private static Object copyFromClientArray(Object internalArray) throws UtilException {
        try {
            if (internalArray == null) {
                return null;
            }
            Object resultArray = Array.newInstance(
                    getTypeFromClient(internalArray.getClass()).getComponentType(),
                    Array.getLength(internalArray));
            for (int index = 0; index < Array.getLength(internalArray); index++) {
                Object currentObj = Array.get(internalArray, index);

                if (isBasicType(currentObj.getClass())) {
                    Array.set(resultArray, index, currentObj);
                    continue;
                } else if (currentObj.getClass().equals(Collection.class)) {
                    Array.set(resultArray, index, copyFromClientCollection(currentObj));
                    continue;
                } else if (currentObj.getClass().isArray()) {
                    Array.set(resultArray, index, copyFromClientArray(currentObj));
                    continue;
                }
                Array.set(resultArray, index,
                        copyFromClient(currentObj));
            }
            return resultArray;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy an array from client : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method is used to copy the client collection object.
     */
    private static Object copyFromClientCollection(Object collection) throws UtilException {
        try {
            if (collection == null) {
                return null;
            }
            Collection current = (Collection) collection;
            Collection result = (Collection) collection.getClass().newInstance();
            for (Iterator iter = current.iterator(); iter.hasNext();) {
                Object currentObj = iter.next();
                if (isBasicType(currentObj.getClass())) {
                    result.add(currentObj);
                    continue;
                } else if (currentObj.getClass().isArray()) {
                    result.add(copyFromClientArray(currentObj));
                    continue;
                } else if (currentObj.getClass().equals(Collection.class)) {
                    result.add(copyFromClientCollection(currentObj));
                    continue;
                }
                result.add(copyFromClient(currentObj));
            }
            return result;
        } catch (Exception ex) {
            throw new UtilException("Failed to copy the collection : ");
        }
    }


    /**
     * This method is called to replace get with set
     *
     * @param method The string containing the method name to replace.
     * @return The result
     */
    private static String replaceGetWithSet(String method) throws java.lang.NoSuchMethodException {
        if (!method.startsWith("get")) {
            throw new java.lang.NoSuchMethodException("The get method is not formatted correctly");
        }
        return "set" + method.substring(3);
    }
}
