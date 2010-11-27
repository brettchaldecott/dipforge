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
 * MethodAnnotationHelper.java
 */

// package path
package com.rift.coad.rdf.semantic.annotation;

// java imports
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * The method annotation helper.
 *
 * @author brett chaldecott
 */
public class MethodAnnotationHelper {
    
    /**
     * This method returns the method identified by the annotation.
     * @param objectType The object type.
     * @param annotation The annotation.
     * @return The method to call that is identified by a specific annotation.
     */
    public static Method getMethodForAnnotation(Object objectType, Class annotation) {
        Method[] methods = objectType.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        return null;
    }

}
