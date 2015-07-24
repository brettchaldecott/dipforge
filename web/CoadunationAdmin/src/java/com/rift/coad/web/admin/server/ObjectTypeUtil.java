/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * ObjectTypeUtil.java
 */

// package path
package com.rift.coad.web.admin.server;

// java imports
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

// import log4j
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.web.admin.client.DaemonException;


/**
 * This class is responsible for handling the object type information in a
 * generic way.
 *
 * @author brett chaldecott
 */
public class ObjectTypeUtil {

    // private member variables
    private static Logger log = Logger.getLogger(ObjectTypeUtil.class);

    /** Creates a new instance of ObjectTypeUtil */
    private ObjectTypeUtil() {
    }
    
    
    /**
     * This method returns true if the name identifies a primitive.
     *
     * @return TRUE if a primitive, FALSE if not.
     * @param name The name of the primitive.
     */
    public static boolean isPrimitive(String name) {
        if (name.equals("byte") || name.equals("short") ||
                name.equals("int") || name.equals("long") ||
                name.equals("float") || name.equals("double") ||
                name.equals("boolean") || name.equals("char") ||
                name.equals("void")) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method returns the primitive for the name.
     *
     * @return The reference to the class definition for the primitive
     * @param name The name of the primitive.
     * @exception SAXException
     */
    public static Class getPrimitive(String name) throws DaemonException {
        if (name.equals("byte")) {
            return byte.class;
        } else if (name.equals("short")) {
            return short.class;
        } else if (name.equals("int")) {
            return int.class;
        } else if (name.equals("long")) {
            return long.class;
        } else if (name.equals("float")) {
            return float.class;
        } else if (name.equals("double")) {
            return double.class;
        } else if (name.equals("boolean")) {
            return boolean.class;
        } else if (name.equals("char")) {
            return char.class;
        } else if (name.equals("void")) {
            return void.class;
        }
        throw new DaemonException("Unrecognised basic type : " + name);
    }
    
    
    /**
     * This method retrives the method for the given interface list
     */
    public static Method getMethod(Class[] interfaces, String name, Class[] arguments)
    throws DaemonException{
        
        for (int index = 0; index < interfaces.length; index++) {
            try {
                return interfaces[index].getMethod(name,arguments);
            } catch (java.lang.NoSuchMethodException ex) {
                // ignore this
            } catch (Exception ex) {
                log.error("Failed to find the method [" + name
                        + "] because : " + ex.getMessage(),ex);
                throw new DaemonException("Failed to find the method [" + name
                        + "] because : " + ex.getMessage());
            }
        }
        log.error("Failed to find the method [" + name
                + "] as it does not exist on any interface.");
        throw new DaemonException("Failed to find the method [" + name
                + "] as it does not exist on any interface.");
    }
    
    
    /**
     * This method converts the string object to a class instance.
     *
     * @return The instance of the type.
     * @param type The type the value must be returned for.
     * @param value The value to instanciate.
     * @exception DaemonException
     */
    public static Object getValue(Class type, String value)
    throws DaemonException {
        try
        {
            if ((byte.class == type) || (Byte.class == type)) {
                return new Byte(value);
            } else if ((short.class == type) || (Short.class == type)) {
                return new Short(value);
            } else if ((int.class == type) || (Integer.class == type)) {
                return new Integer(value);
            } else if ((long.class == type) || (Long.class == type)) {
                return new Long(value);
            } else if ((float.class == type) || (Float.class == type)) {
                return new Float(value);
            } else if ((double.class == type) || (Double.class == type)) {
                return new Double(value);
            } else if ((boolean.class == type) || (Boolean.class == type)) {
                return new Boolean(value);
            } else if ((char.class == type) || (Character.class == type)) {
                return new Character(value.charAt(0));
            } else if (void.class == type) {
                return null;
            } else if (type == String.class) {
                return value;
            } else if (type.isArray()) {
                String[] values = value.split(",");
                if (byte[].class == type) {
                    return getBasicArray(byte[].class, new byte[values.length],
                            values);
                } else if (short[].class == type) {
                    return getBasicArray(short[].class, new short[values.length],
                            values);
                } else if (int[].class == type) {
                    return getBasicArray(int[].class, new int[values.length],
                            values);
                } else if (long[].class == type) {
                    return getBasicArray(long[].class, new long[values.length],
                            values);
                } else if (float[].class == type) {
                    return getBasicArray(float[].class, new float[values.length],
                            values);
                } else if (double[].class == type) {
                    return getBasicArray(double[].class, 
                            new double[values.length],values);
                } else if (boolean[].class == type) {
                    return getBasicArray(boolean[].class, 
                            new boolean[values.length],values);
                } else if (char[].class == type) {
                    return getBasicArray(char[].class, 
                            new char[values.length],values);
                } else if (String[].class == type) {
                    return values;
                }
            } else {
                String[] values = value.split(",");
                Constructor[] constructors = type.getDeclaredConstructors();
                for (int index = 0; index < constructors.length; index++) {
                    if (constructors[index].getParameterTypes().length == 
                            values.length) {
                        Object[] arguments = new Object[values.length];
                        for (int argIndex = 0;argIndex < values.length; 
                        argIndex++) {
                            arguments[argIndex] = getValue(constructors[index].
                                    getParameterTypes()[argIndex], 
                                    values[argIndex]);
                        }
                        
                        return constructors[index].newInstance(arguments);
                    }
                }
            }
            log.error("The type [" + type.getName() + "] was not recognised");
            throw new DaemonException(
                    "The type [" + type.getName() + "] was not recognised");
        } catch (DaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the value because : " + 
                    ex.getMessage(),ex);
            throw new DaemonException(
                    "Failed to retrieve the value because : " + 
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method returns an array values matching the basic type.
     *
     * @return The array of basic type.
     * @param type The type of array.
     * @param arrayInstance The array instance to populate.
     * @param value The value to parse.
     * @exception DaemonException
     */
    private static Object getBasicArray(Class type, Object arrayInstance, 
            String[] values) throws DaemonException {
        try {
            for (int index = 0; index < values.length; index++) {
                if (byte[].class == type) {
                    ((byte[])arrayInstance)[index] = 
                            Byte.parseByte(values[index]);
                } else if (short[].class == type) {
                    ((short[])arrayInstance)[index] = 
                            Short.parseShort(values[index]);
                } else if (int[].class == type) {
                    ((int[])arrayInstance)[index] = 
                            Integer.parseInt(values[index]);
                } else if (long[].class == type) {
                    ((long[])arrayInstance)[index] = 
                            Long.parseLong(values[index]);
                } else if (float[].class == type) {
                    ((float[])arrayInstance)[index] = 
                            Float.parseFloat(values[index]);
                } else if (double[].class == type) {
                    ((double[])arrayInstance)[index] = 
                            Double.parseDouble(values[index]);
                } else if (boolean[].class == type) {
                    ((boolean[])arrayInstance)[index] = 
                            Boolean.parseBoolean(values[index]);
                } else if (char[].class == type) {
                    ((char[])arrayInstance)[index] = values[index].charAt(0);
                } 
            }
            return arrayInstance;
        } catch (Exception ex) {
            log.error("Failed to process the value : " + 
                    ex.getMessage(),ex);
            throw new DaemonException(
                    "Failed to process the value : " + 
                    ex.getMessage());
        }
    }
}
