/*
 * 0250-CoadunationCRMServer: The CRM server.
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
 * Person.java
 */


package com.rift.coad.rdf.objmapping.person;

// the web semantic import

// jena imports
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

// coadunation imports
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.*;


/**
 * This object represents a basic entity within the CRM system.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/person#")
@RdfType("Person")
public class Person extends ResourceBase {
    private String id;
    private String firstNames;
    private String surname;

    
    /**
     * The default constructor
     */
    public Person() {
    }


    /**
     * The person the constructor
     * @param attributes
     * @param id
     * @param firstNames
     * @param surname
     * @param initials
     */
    public Person(DataType[] attributes, String id, String firstNames, String surname) {
        super(attributes);
        this.id = id;
        this.firstNames = firstNames;
        this.surname = surname;
    }



    /**
     * This method returns the id of the object.
     * 
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return this.id;
    }


    /**
     * This method returns the id of the person.
     *
     * @return The unique identifier of this person.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Id")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the person.
     *
     * @param id The id of the person.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Id")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The getter for the first name.
     *
     * @return This method returns the first names.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#FirstNames")
    public String getFirstNames() {
        return firstNames;
    }


    /**
     * This method sets the first names for this person.
     *
     * @param firstNames The new first name value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#FirstNames")
    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }


    /**
     * This method returns the surname of the person.
     *
     * @return The string containing the surname of the person.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Surname")
    public String getSurname() {
        return surname;
    }


    /**
     * This method sets the surname of the person.
     *
     * @param surname The string containing the new surname.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    
    
}
