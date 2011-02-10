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
 * NamespaceHelper.java
 */

// package path
package com.rift.coad.rdf.semantic.annotation;

/**
 * This object is responsible for retrieving the namespace information from the object.
 *
 * @author brett chaldecott
 */
public class NamespaceHelper {
    private Class type;

    /**
     * This object is responsible for handling the namespace information.
     *
     * @param type The type to be passed into this object.
     */
    public NamespaceHelper(Class type) {
        this.type = type;
    }

    /**
     * The method returns the namespace.
     * 
     * @return The string namespace.
     */
    public String getNamespace() throws AnnotationException {
        /*thewebsemantic.Namespace namespace = null;
        if ( null ==
                (namespace = (thewebsemantic.Namespace)AnnotationHelper.getAnnotation(type, thewebsemantic.Namespace.class))) {
            throw new AnnotationException(
                    "The namespace information was not set for the class [" + type.getName() + "]");
        }
        return namespace.value();*/
        return null;
    }


}
