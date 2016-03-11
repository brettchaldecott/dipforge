/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * ObjectSerializer.java
 *
 * This class is responsible for serializing and deserializing objects.
 */

// package path
package com.rift.coad.lib.common;

// java imports
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/**
 * This class is responsible for serializing and deserializing objects.
 *
 * @author Brett Chaldecott
 */
public class ObjectSerializer {
    
    /**
     * This class overrides the resolve to use the class loader on the
     * thread to find the specified class.
     */
    public static class ClassLoaderObjectInputStream extends ObjectInputStream {
        /**
         * This default constructor of the class loader object input stream.
         *
         * @exception IOException
         */
        public ClassLoaderObjectInputStream() throws IOException {
            super();
        }
        
        
        /**
         * This default constructor of the class loader object input stream.
         *
         * @param in The input stream for this object.
         * @exception IOException
         */
        public ClassLoaderObjectInputStream(InputStream in) throws IOException {
            super(in);
        }
        
        
        /**
         * This method returns the class definition for the requested object.
         *
         * @return The class definition for the requested object.
         * @param desc The description of the object.
         * @exception IOException
         * @exception ClassNotFoundException
         */
        protected Class resolveClass(ObjectStreamClass desc) throws IOException,
                ClassNotFoundException {
            // This works with arrays and classes
            return Class.forName(desc.getName(),false,
                    Thread.currentThread().getContextClassLoader());
        }
        
    }
    
    /** 
     * Creates a new instance of ObjectSerializer 
     */
    private ObjectSerializer() {
    }
    
    
    /**
     * This method serializes the object passed to it.
     *
     * @return A byte array of the object.
     * @param ref The reference to the object to serialize.
     * @exception CommonException
     */
    public static byte[] serialize(Object ref) throws CommonException {
        try {
            if (!(ref instanceof java.io.Serializable)) {
                throw new CommonException("This object is not serializable. " +
                        "Must implement from java.io.Serializable.");
            }
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteOutput);
            objOutput.writeObject(ref);
            objOutput.flush();
            objOutput.close();
            return byteOutput.toByteArray();
        } catch (CommonException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CommonException("Failed to serialize the object : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method serializes the object passed to it.
     *
     * @return A byte array of the object.
     * @param input The input bytes to deserialize
     * @exception CommonException
     */
    public static Object deserialize(byte[] input) throws CommonException {
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(input);
            ClassLoaderObjectInputStream objInput = 
                    new ClassLoaderObjectInputStream(byteInput);
            Object ref = objInput.readObject();
            objInput.close();
            return ref;
        } catch (Exception ex) {
            throw new CommonException("Failed to deserialize the object : " +
                    ex.getMessage(),ex);
        }
    }
}
