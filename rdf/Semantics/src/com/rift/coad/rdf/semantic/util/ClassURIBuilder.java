/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicJDOPersistanceHandler.java
 */

package com.rift.coad.rdf.semantic.util;

import com.rift.coad.rdf.semantic.Constants;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import java.io.Serializable;
import java.net.URI;

/**
 * This class is responsible for building a uri based on the class and identify passed in.
 *
 * @author brett chaldecott
 */
public class ClassURIBuilder {
    
    /**
     * This method returns the URI generated for this class.
     * 
     * @param type The type of object to identify.
     * @param identifier The identifier.
     * @return The URI return results.
     * @throws UtilException
     */
    public static URI generateClassURI(Class type, Serializable identifier) throws UtilException {
        try {
            ClassInfo classInfo = ClassInfo.interrogateClass(type);
            return new URI(String.format(Constants.RESOURCE_URI_FORMAT,
                    classInfo.getNamespace(), classInfo.getLocalName(),
                    identifier.toString()));
        } catch (Exception ex) {
            throw new UtilException("Failed get the class URI : " + ex.getMessage(),ex);
        }
    }
}
