/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * MemberVariableHelper.java
 */

// package path
package com.rift.coad.rdf.semantic.annotation;

// java imports
import java.lang.reflect.Method;

/**
 * This class is responsible handling the member variable annotation appropriatly.
 *
 * @author brett chaldecott
 */
public class MemberVariableHelper {

    // private member variables
    private Object obj;

    /**
     * This constructor sets the object reference that the information will be retrieved from.
     *
     * @param obj
     */
    public MemberVariableHelper(Object obj) {
        this.obj = obj;
    }


    /**
     * This method is responsible for returning the management information.
     *
     * @return The string containing the management object information.
     */
    public String getMemberVariableName() throws AnnotationException {
        try {
            Class annotation = thewebsemantic.MemberVariableName.class;
            Method method = MethodAnnotationHelper.getMethodForAnnotation(obj, annotation);
            return (String)method.invoke(obj);
        } catch (Throwable ex) {
            throw new AnnotationException("Failed to retrieve the member variable name :" +
                    ex.getMessage(),ex);
        }
    }
}
