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
 * TestClass.java
 */

package com.rift.coad.rdf.semantic.annotation.test;

// the semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.ObjTypeId;
import thewebsemantic.ManagementObject;
import thewebsemantic.MemberVariableName;

/**
 * This class is used to test the annotation helps required by the
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/test#")
@RdfType("TestClass")
public class TestClass {
    
    /**
     * This method is here to test the Management Object annotation find method.
     *
     * @return The string containing the name of the member variable.
     */
    @ManagementObject()
    public String getManagementObject() {
        return "Test";
    }

    /**
     * This method returns the name of the member variable that this object is attached as.
     *
     * @return The string containing the name of the member variable.
     */
    @MemberVariableName()
    public String getMemberVariableName() {
        return "name";
    }

    /**
     * This method is here to test the object type id call.
     *
     * @return The string containing the object type id.
     */
    @ObjTypeId()
    public String getObjectTypeId() {
        return this.getClass().getName();
    }
}
