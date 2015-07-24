/*
 * WebLibs: Misc web utils and tools
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
 * BeanCopy.java
 */

// the package path
package com.rift.coad.web.utils.copy;

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
public class BeanCopy {

    /**
     * This method is responsible for copying the object value.
     * @param <T>
     * @param c
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    public static <T> T copy(Class<T> type, Object value) throws CopyException {
        try {
            // assume a default constructor
            if (value == null) {
                return null;
            }
            // assume a default constructor
            T result = type.newInstance();

            for (Method getter : value.getClass().getMethods()) {
                try {
                    if (!getter.getName().matches("get[A-Za-z0-9-]*")) {
                        continue;
                    } else if (getter.getParameterTypes().length != 0) {
                        continue;
                    }
                    Method setter = null;
                    Method targetGetter = type.getMethod(getter.getName());
                    if (isBasicType(getter.getReturnType())) {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                            getter.getReturnType());
                        setter.invoke(result, new Object[]{getter.invoke(value, new Object[]{})});
                        continue;
                    } else {
                        setter = result.getClass().getMethod(replaceGetWithSet(getter.getName()),
                                targetGetter.getReturnType());
                    }

                    // check for basic type
                    if (!getter.getReturnType().isArray() && !getter.getReturnType().isAssignableFrom(Collection.class)
                            && !getter.getReturnType().isAssignableFrom(List.class)) {
                        setter.invoke(result, new Object[]{copy(setter.getReturnType(),
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    } else if (getter.getReturnType().isArray()) {
                        setter.invoke(result, new Object[]{copyToArray(targetGetter.getReturnType().getComponentType(),
                                    getter.invoke(value, new Object[]{}))});
                        continue;
                    }
                } catch (java.lang.NoSuchMethodException ex) {
                    // ignore
                }
            }

            return result;
        } catch (Exception ex) {
            throw new CopyException("Failed to copy the object information : " +
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
     * This method is responsible for copying the object of type array to a matching
     * client object.
     *
     * @param internalArray The array to be copied
     * @return The resultant object.
     * @throws com.rift.coad.rdf.objmapping.util.UtilException
     */
    public static <T> T[] copyToArray(Class<T> type, Object internalArray) throws CopyException {
        try {
            if (internalArray == null) {
                return null;
            }
            T[] resultArray = (T[])Array.newInstance(type,
                    Array.getLength(internalArray));
            for (int index = 0; index < Array.getLength(internalArray); index++) {
                Object currentObj = Array.get(internalArray, index);
                if (isBasicType(currentObj.getClass())) {
                    Array.set(resultArray, index, currentObj);
                    continue;
                } else {
                    Array.set(resultArray, index,copy(type,currentObj));
                }
            }
            return resultArray;
        } catch (Exception ex) {
            throw new CopyException("Failed to copy the array : " + ex.getMessage(),ex);
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
