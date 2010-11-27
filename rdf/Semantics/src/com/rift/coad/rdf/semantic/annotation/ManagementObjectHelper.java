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
 * ManagementObjectHelper.java
 */

package com.rift.coad.rdf.semantic.annotation;

// java imports
import java.lang.reflect.Method;

/**
 * This object is responsible for wrapping the complexity of retrieving the ManagementObject information.
 *
 * @author brett chaldecott
 */
public class ManagementObjectHelper {
    // private member variables.
    private Object obj;

    /**
     * This constructor is responsible for setting the object information.
     *
     * @param obj The reference to the object information.
     */
    public ManagementObjectHelper(Object obj) {
        this.obj = obj;
    }

    /**
     * This method is responsible for returning the management information.
     *
     * @return The string containing the management object information.
     */
    public String getManagementInfo() throws AnnotationException {
        try {
            Class annotation = thewebsemantic.ManagementObject.class;
            Method method = MethodAnnotationHelper.getMethodForAnnotation(obj, annotation);
            return (String)method.invoke(obj);
        } catch (Throwable ex) {
            throw new AnnotationException("Failed to retrieve the management information :" +
                    ex.getMessage(),ex);
        }
    }
}
