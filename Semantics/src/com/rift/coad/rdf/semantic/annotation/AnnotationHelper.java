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
 * AnnotationHelper.java
 */

package com.rift.coad.rdf.semantic.annotation;

// java imports
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This class defines standard methods for retrieveing information from annotated classes.
 *
 * @author brett chaldecott
 */
public class AnnotationHelper {

    /**
     * The default constructor of the annotation helper.
     */
    public AnnotationHelper() {
    }


    /**
     * This method returns the annotation matching the one specified, It first searches class annotations
     * than it searches method parameter and method annotations.
     *
     * @param source The source to retrieve the annotations from.
     * @param type The class type to match the annotation to.
     * @return The return annotation.
     */
    public static Annotation getAnnotation(Class source, Class type) {
        if (source.isAnnotationPresent(type)) {
            return source.getAnnotation(type);
        }
        // check parameters
        for (Field field : source.getFields()) {
            if (field.isAnnotationPresent(type)) {
                return field.getAnnotation(type);
            }
        }

        // check methods
        for (Method method : source.getMethods()) {
            if (method.isAnnotationPresent(type)) {
                return method.getAnnotation(type);
            }
        }
        return null;
    }


    /**
     * This method is responsible for returning the annotation identified by the class type.
     *
     * @param annotations The list of annotations to process.
     * @param type The class type that the annotation must be retrieve for.
     * @return The return tye annotation.
     */
    public static Annotation getAnnotation(Annotation[] annotations, Class type) {
        for (Annotation annot : annotations) {
            if (annot.annotationType().equals(type)) {
                return annot;
            }
        }
        return null;
    }


    /**
     * This method gets the specified annotations.
     *
     * @param source The source class to query.
     * @param type The type to look through.
     * @return The reference to the annotation to retrieve.
     */
    public static Annotation[] getAnnotationGroup(Class source, Class type) {
        if (source.isAnnotationPresent(type)) {
            return source.getAnnotations();
        }
        // check parameters
        for (Field field : source.getFields()) {
            if (field.isAnnotationPresent(type)) {
                return field.getAnnotations();
            }
        }

        // check methods
        for (Method method : source.getMethods()) {
            if (method.isAnnotationPresent(type)) {
                return method.getAnnotations();
            }
        }
        return null;
    }


    

}
