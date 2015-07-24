/*
 * 0250-CoadunationCRMServer: The CRM server.
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
 * Person.java
 */


package com.rift.coad.rdf.objmapping.client.person;

// the web semantic import

// coadunation imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.base.name.FirstNames;
import com.rift.coad.rdf.objmapping.client.base.name.Surname;
import com.rift.coad.rdf.objmapping.client.exception.ObjException;
import com.rift.coad.rdf.objmapping.*;


/**
 * This object represents a basic entity within the CRM system.
 * 
 * @author brett chaldecott
 */
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
     * This method returns the id of the person.
     *
     * @return The unique identifier of this person.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the person.
     *
     * @param id The id of the person.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The getter for the first name.
     *
     * @return This method returns the first names.
     */
    public String getFirstNames() {
        return firstNames;
    }


    /**
     * This method sets the first names for this person.
     *
     * @param firstNames The new first name value.
     */
    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }


    /**
     * This
     * @return
     */
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    
    
}
