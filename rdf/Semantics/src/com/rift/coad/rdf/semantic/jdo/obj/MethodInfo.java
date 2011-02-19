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
 * MethodInfo.java
 */

package com.rift.coad.rdf.semantic.jdo.obj;

import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;
import com.rift.coad.rdf.semantic.annotation.PropertyNamespace;
import com.rift.coad.rdf.semantic.annotation.PropertyURI;
import com.rift.coad.rdf.semantic.annotation.helpers.AnnotationHelper;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.util.RDFURIHelper;
import java.lang.reflect.Method;

/**
 * This object is responsible for extracting the RDF information from a method.
 *
 * @author brett chaldecott
 */
public class MethodInfo {
    // class constants
    public final static String GETTER_PREFIX = "get";
    public final static String IS_PREFIX = "is";
    public final static String SETTER_PREFIX = "set";


    // private member variables
    private Method methodRef;
    private String namespace;
    private String localName;
    private DataType type;
    private boolean getter = false;
    private boolean setter = false;
    private boolean operator = false;


    /**
     * This constructor sets the method information
     *
     * @param methodRef The reference to the method.
     * @throws ObjException
     */
    public MethodInfo(Method methodRef, String namespace) throws ObjException {
        this.methodRef = methodRef;
        
        // determine the type of method
        if (methodRef.getName().startsWith(GETTER_PREFIX) ||
                methodRef.getName().startsWith(IS_PREFIX)) {
            this.getter = true;
        } else if (methodRef.getName().startsWith(SETTER_PREFIX)) {
            this.setter = true;
        } else {
            this.operator = true;
        }

        this.namespace = namespace;
        PropertyNamespace propertyNamespace =
                (PropertyNamespace)AnnotationHelper.getAnnotation(
                methodRef.getDeclaredAnnotations(),
                PropertyNamespace.class);
        if (propertyNamespace != null) {
            this.namespace = propertyNamespace.value();
        }
        PropertyLocalName propertyLocalName =
                (PropertyLocalName)AnnotationHelper.getAnnotation(
                methodRef.getDeclaredAnnotations(),
                PropertyLocalName.class);
        if (propertyLocalName != null) {
            this.localName = propertyLocalName.value();
        }
        // attempt to search for a uri value to set things.
        if (localName == null) {
            PropertyURI propertyURI =
                (PropertyURI)AnnotationHelper.getAnnotation(
                methodRef.getDeclaredAnnotations(),
                PropertyURI.class);
            if (propertyURI == null) {
                throw new ObjException("Failed retrieve the local name for [" +
                        methodRef.getName() + "]");
            }
            try {
                RDFURIHelper uriHelper = new RDFURIHelper(propertyURI.value());
                this.namespace = uriHelper.getNamespace();
                this.localName = uriHelper.getLocalName();
            } catch (Exception ex) {
                throw new ObjException("The constructor of the method : " + ex.getMessage(),ex);
            }
        }

        
    }


    /**
     * Thi method returns true if this is a getter.
     *
     * @return The string containing the getter.
     */
    public boolean isGetter() {
        return getter;
    }


    /**
     * This method returns the local name for this method
     * @return
     */
    public String getLocalName() {
        return localName;
    }


    /**
     * This method returns the reference information
     * @return
     */
    public Method getMethodRef() {
        return methodRef;
    }

    public String getNamespace() {
        return namespace;
    }

    public boolean isOperator() {
        return operator;
    }

    public boolean isSetter() {
        return setter;
    }

    public DataType getType() {
        return type;
    }



    
}
