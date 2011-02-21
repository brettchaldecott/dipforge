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
 * ClassInfo.java
 */


// package path
package com.rift.coad.rdf.semantic.jdo.obj;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.helpers.AnnotationHelper;
import com.rift.coad.rdf.semantic.util.MethodUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This object is responsible for interragating a class and retrieving all the
 * Semantic information.
 *
 * @author brett chaldecott
 */
public class ClassInfo {
    private Class classRef;
    private String namespace;
    private String localName;
    private MethodInfo idMethod = null;
    private List<MethodInfo> getters = new ArrayList<MethodInfo>();
    private List<MethodInfo> setters = new ArrayList<MethodInfo>();
    private List<MethodInfo> operators = new ArrayList<MethodInfo>();
    private List<MethodInfo> methods = new ArrayList<MethodInfo>();

    /**
     * This constructor
     *
     * @param classRef The reference to the class to interrogate.
     * @throws ObjException
     */
    private ClassInfo(Class classRef) throws ObjException {
        try {
            this.classRef = classRef;
            Namespace namespace =
                    (Namespace)AnnotationHelper.getAnnotation(classRef,
                    Namespace.class);
            if (namespace == null) {
                throw new ObjException("There is no name space attached to this class");
            }
            this.namespace = namespace.value();
            LocalName localName = (LocalName)AnnotationHelper.getAnnotation(classRef,
                    LocalName.class);
            if (localName == null) {
                throw new ObjException("There is no local name attached to this class");
            }
            this.localName = localName.value();
            for (Method method: this.classRef.getMethods()) {
                if (MethodUtils.isExcludableMethod(method)) {
                    continue;
                }
                MethodInfo methodInfo = new MethodInfo(method,this.namespace);
                if (methodInfo.getMethodRef().getAnnotation(Identifier.class) != null) {
                    idMethod = methodInfo;
                }
                methods.add(methodInfo);
                if (methodInfo.isGetter()) {
                    getters.add(methodInfo);
                } else if (methodInfo.isSetter()) {
                    setters.add(methodInfo);
                } else {
                    operators.add(methodInfo);
                }
            }
            if (idMethod == null) {
                throw new ObjException("No id field for this class : " +
                        classRef.getName());
            }
        } catch (ObjException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ObjException("Failed to interrogate the class [" +
                    classRef.getName() + "] because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a reference to the class information object once the
     * class has been interrogated for information.
     *
     * @param classRef The reference to the class to interrogate.
     * @return The class information once the class has been interrogated.
     * @throws ObjException
     */
    public static ClassInfo interrogateClass(Class classRef) throws ObjException {
        return new ClassInfo(classRef);
    }


    /**
     * The reference to the class that has been interrogated for its information.
     *
     * @return The class to interrogate.
     */
    public Class getClassRef() {
        return classRef;
    }


    /**
     * The local name of the class.
     *
     * @return The localname for the class.
     */
    public String getLocalName() {
        return localName;
    }


    /**
     * The list of methods attached to this class.
     *
     * @return The list of methods attached to this class.
     */
    public List<MethodInfo> getMethods() {
        return methods;
    }


    /**
     * The namespace for this class.
     *
     * @return The namespace for this class.
     */
    public String getNamespace() {
        return namespace;
    }


    /**
     * This method returns the list of getters associated with this class.
     *
     * @return The list of getters associated with this class.
     */
    public List<MethodInfo> getGetters() {
        return getters;
    }


    /**
     * This method returns the list of operators associated with this class.
     *
     * @return The list of operators.
     */
    public List<MethodInfo> getOperators() {
        return operators;
    }


    /**
     * This method returns the list of setters.
     *
     * @return The list of setters.
     */
    public List<MethodInfo> getSetters() {
        return setters;
    }

    /**
     * The reference to the id field.
     * @return
     */
    public MethodInfo getIdMethod() {
        return idMethod;
    }



}
