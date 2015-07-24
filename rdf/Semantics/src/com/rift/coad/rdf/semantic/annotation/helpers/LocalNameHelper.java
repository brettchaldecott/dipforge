/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * LocalNameHelper.java
 */
// the package path
package com.rift.coad.rdf.semantic.annotation.helpers;

import com.rift.coad.rdf.semantic.annotation.LocalName;

/**
 * This object is responsible for returning the RDF type helper.
 * 
 * @author brett chaldecott
 */
public class LocalNameHelper {

    private Class type;

    /**
     * This constructor sets the rdf type information
     * @param type
     */
    public LocalNameHelper(Class type) {
        this.type = type;
    }

    /**
     * This method returns the rdf type information for for the object.
     * @return The 
     * @throws com.rift.coad.rdf.semantic.annotation.AnnotationException
     */
    public String getLocalName() throws AnnotationException {
        LocalName localName = null;
        if (null
                == (localName = (LocalName) AnnotationHelper.getAnnotation(type, LocalName.class))) {
            throw new AnnotationException(
                    "The RDF type information has not been set for the class [" + type.getName() + "]");
        }
        return localName.value();
    }
}
