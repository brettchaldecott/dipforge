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
 * TestBean.java
 */

// package path
package com.rift.coad.rdf.semantic.session.test;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.ManagementObject;
import thewebsemantic.MemberVariableName;
import thewebsemantic.ObjTypeId;
import thewebsemantic.Uri;
import thewebsemantic.RdfProperty;


/**
 * 
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/test#")
@RdfType("TestBean")
public class TestBean {
    private String id;
    private String name;
    private String managementObject = null;
    private String memberVariableName = null;
    private String objectType;

    /**
     * The default constructor of the test bean object.
     */
    public TestBean() {
    }


    /**
     * The constructor sets the id and name of this bean.
     * @param id The id of the object.
     * @param name The name of the object.
     */
    public TestBean(String id, String name, String objectType) {
        this.id = id;
        this.name = name;
        this.objectType = objectType;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#Id")
    @Identifier()
    public String getId() {
        return id;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#Id")
    public void setId(String id) {
        this.id = id;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#Name")
    public String getName() {
        return name;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#Name")
    public void setName(String name) {
        this.name = name;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#ManagementObject")
    @ManagementObject()
    public String getManagementObject() {
        return this.managementObject;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#ManagementObject")
    public void setManagementObject(String managementObject) {
        this.managementObject = managementObject;
    }

    /**
     * This method returns the name of the member variable that this object is attached as.
     *
     * @return The string containing the name of the member variable.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#MemberVariableName")
    @MemberVariableName()
    public String getMemberVariableName() {
        return memberVariableName;
    }

    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#MemberVariableName")
    public void setMemberVariableName(String memberVariableName) {
        this.memberVariableName = memberVariableName;
    }


    /**
     * This method is here to test the object type id call.
     *
     * @return The string containing the object type id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#ObjectType")
    @ObjTypeId()
    public String getObjectType() {
        return this.objectType;
    }


    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/test#ObjectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }





     /**
     * This method returns the composite index for the object
     *
     * @return This method returns the composite id.
     */
    @Uri()
    public final String getUri() {
        if (this.getManagementObject() != null) {
            return "http://www.coadunation.net/schema/rdf/1.0/test#TestBean/" + this.getManagementObject() + "/" + this.getObjectType() + "/"  + this.getMemberVariableName() + "/"+ this.getId();
        } else {
            return "http://www.coadunation.net/schema/rdf/1.0/test#TestBean/" + this.getObjectType() + "/"  + this.getId();
        }
    }



}
