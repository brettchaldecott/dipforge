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


package com.rift.coad.crm.objmapping;

// the web semantic import
import thewebsemantic.Id;
import thewebsemantic.binding.Jenabean;
import thewebsemantic.binding.RdfBean;
import com.rift.coad.lib.id.IDGenerator;
import thewebsemantic.Namespace;


/**
 * This object represents a basic entity within the CRM system.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf")

public class Person extends RdfBean<Person> {
    @Id
    private Integer id;
    private String firstNames;
    private String surname;
    private String initials;

    
    /**
     * The default constructor
     */
    public Person() throws ObjException {
        try {
            id = IDGenerator.getInstance().getNextId("ObjPerson");
        } catch (Exception ex) {
            throw new ObjException("Failed to ");
        }
    }
    
    
    
    
}
