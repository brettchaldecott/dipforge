/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * TypeManager.java
 */

// package path
package com.rift.coad.rdf.objmapping.util.client.type;

// the data type
import com.google.gwt.user.client.ui.Composite;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.smartgwt.client.util.SC;

/**
 * This object is responsible for creating the specified type object.
 *
 * @author brett chaldecot
 */
public class TypeManager {
    // class constants

    public static final String[] GROUPING = new String[]{
        "Basic", "Date", "Address", "ID", "IP", "Name", "Password",
        "Phone", "Serial", "Number", "String", "Organisation",
        "Person", "Inventory", "Service", "Misc"};

    public static final String[] NONE_SCOPE_GROUPING = new String[]{
        "Simple"};


    public final static String[] TYPES = new String[]{
        "com.rift.coad.rdf.objmapping.inventory.Hardware",
        "com.rift.coad.rdf.objmapping.inventory.Network",
        "com.rift.coad.rdf.objmapping.inventory.Stock",
        "com.rift.coad.rdf.objmapping.inventory.Software",
        "com.rift.coad.rdf.objmapping.inventory.Inventory",
        "com.rift.coad.rdf.objmapping.inventory.Rack",
        "com.rift.coad.rdf.objmapping.inventory.Host",
        "com.rift.coad.rdf.objmapping.base.Str",
        "com.rift.coad.rdf.objmapping.base.SerialNumber",
        "com.rift.coad.rdf.objmapping.base.Name",
        "com.rift.coad.rdf.objmapping.base.AddressCode",
        "com.rift.coad.rdf.objmapping.base.address.PostalCode",
        "com.rift.coad.rdf.objmapping.base.address.ZipCode",
        "com.rift.coad.rdf.objmapping.base.RDFBase",
        "com.rift.coad.rdf.objmapping.base.Address",
        "com.rift.coad.rdf.objmapping.base.Description",
        "com.rift.coad.rdf.objmapping.base.UniqueId",
        "com.rift.coad.rdf.objmapping.base.URL",
        "com.rift.coad.rdf.objmapping.base.id.IdNumber",
        "com.rift.coad.rdf.objmapping.base.serial.ISSN",
        "com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber",
        "com.rift.coad.rdf.objmapping.base.serial.ISBN",
        "com.rift.coad.rdf.objmapping.base.str.ValidatedString",
        "com.rift.coad.rdf.objmapping.base.str.GenericString",
        "com.rift.coad.rdf.objmapping.base.Domain",
        "com.rift.coad.rdf.objmapping.base.name.FirstNames",
        "com.rift.coad.rdf.objmapping.base.name.Username",
        "com.rift.coad.rdf.objmapping.base.name.Surname",
        "com.rift.coad.rdf.objmapping.base.Email",
        "com.rift.coad.rdf.objmapping.base.ip.IPv6",
        "com.rift.coad.rdf.objmapping.base.ip.IPv4",
        "com.rift.coad.rdf.objmapping.base.Password",
        "com.rift.coad.rdf.objmapping.base.Country",
        "com.rift.coad.rdf.objmapping.base.IPAddress",
        "com.rift.coad.rdf.objmapping.base.password.ClearTextPassword",
        "com.rift.coad.rdf.objmapping.base.password.MD5Password",
        "com.rift.coad.rdf.objmapping.base.password.SHAPassword",
        "com.rift.coad.rdf.objmapping.base.Phone",
        "com.rift.coad.rdf.objmapping.base.DataType",
        "com.rift.coad.rdf.objmapping.base.phone.CellPhone",
        "com.rift.coad.rdf.objmapping.base.phone.Fax",
        "com.rift.coad.rdf.objmapping.base.Title",
        "com.rift.coad.rdf.objmapping.resource.ResourceBase",
        "com.rift.coad.rdf.objmapping.resource.GenericResource",
        "com.rift.coad.rdf.objmapping.person.Person",
        "com.rift.coad.rdf.objmapping.person.User",
        "com.rift.coad.rdf.objmapping.service.IPService",
        "com.rift.coad.rdf.objmapping.service.SoftwareService",
        "com.rift.coad.rdf.objmapping.organisation.Organisation",
        "com.rift.coad.rdf.objmapping.base.RDFNumber",
        "com.rift.coad.rdf.objmapping.base.number.RDFLong",
        "com.rift.coad.rdf.objmapping.base.number.RDFInteger",
        "com.rift.coad.rdf.objmapping.base.number.RDFFloat",
        "com.rift.coad.rdf.objmapping.base.number.RDFDouble",
        "com.rift.coad.rdf.objmapping.base.RDFDate",
        "com.rift.coad.rdf.objmapping.base.date.DateTime"
    };

    /**
     * This method returns the list of types for the given group.
     *
     * @param group The group to retrieve the type list for.
     * @return The list of types for the given group.
     * @throws com.rift.coad.rdf.objmapping.util.client.type.TypeException
     */
    public static String[] getTypesForGroup(String group) throws TypeException {
        if (group.equalsIgnoreCase("Basic")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.Country",
                        "com.rift.coad.rdf.objmapping.base.Description",
                        "com.rift.coad.rdf.objmapping.base.Domain",
                        "com.rift.coad.rdf.objmapping.base.Email",
                        "com.rift.coad.rdf.objmapping.base.Phone",
                        "com.rift.coad.rdf.objmapping.base.Title",
                        "com.rift.coad.rdf.objmapping.base.URL"};
        } else if (group.equalsIgnoreCase("Date")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.date.DateTime"
                    };
        } else if (group.equalsIgnoreCase("Address")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.Address",
                        "com.rift.coad.rdf.objmapping.base.address.PostalCode",
                        "com.rift.coad.rdf.objmapping.base.address.ZipCode"};
        } else if (group.equalsIgnoreCase("ID")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.id.IdNumber"};
        } else if (group.equalsIgnoreCase("IP")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.ip.IPv6",
                        "com.rift.coad.rdf.objmapping.base.ip.IPv4"};
        } else if (group.equalsIgnoreCase("Name")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.Name",
                        "com.rift.coad.rdf.objmapping.base.name.FirstNames",
                        "com.rift.coad.rdf.objmapping.base.name.Username",
                        "com.rift.coad.rdf.objmapping.base.name.Surname"};
        } else if (group.equalsIgnoreCase("Password")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.password.ClearTextPassword",
                        "com.rift.coad.rdf.objmapping.base.password.MD5Password",
                        "com.rift.coad.rdf.objmapping.base.password.SHAPassword"};
        } else if (group.equalsIgnoreCase("Phone")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.phone.CellPhone",
                        "com.rift.coad.rdf.objmapping.base.phone.Fax"};

        } else if (group.equalsIgnoreCase("Serial")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.serial.ISSN",
                        "com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber",
                        "com.rift.coad.rdf.objmapping.base.serial.ISBN"};
        } else if (group.equalsIgnoreCase("Number")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.number.RDFLong",
                        "com.rift.coad.rdf.objmapping.base.number.RDFInteger",
                        "com.rift.coad.rdf.objmapping.base.number.RDFFloat",
                        "com.rift.coad.rdf.objmapping.base.number.RDFDouble"
                    };
        } else if (group.equalsIgnoreCase("String")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.str.ValidatedString",
                        "com.rift.coad.rdf.objmapping.base.str.GenericString"};
        } else if (group.equalsIgnoreCase("Organisation")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.organisation.Organisation"
                    };
        } else if (group.equalsIgnoreCase("Person")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.person.Person",
                        "com.rift.coad.rdf.objmapping.person.User"
                    };
        } else if (group.equalsIgnoreCase("Inventory")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.inventory.Hardware",
                        "com.rift.coad.rdf.objmapping.inventory.Network",
                        "com.rift.coad.rdf.objmapping.inventory.Stock",
                        "com.rift.coad.rdf.objmapping.inventory.Software",
                        "com.rift.coad.rdf.objmapping.inventory.Inventory",
                        "com.rift.coad.rdf.objmapping.inventory.Rack",};
        } else if (group.equalsIgnoreCase("Service")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.service.IPService",
                        "com.rift.coad.rdf.objmapping.service.SoftwareService"
                    };
        } else if (group.equalsIgnoreCase("Misc")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.resource.GenericResource"};
        }


        // none scope based groupings
        if (group.equalsIgnoreCase("Simple")) {
            return new String[]{
                        "com.rift.coad.rdf.objmapping.base.number.RDFLong",
                        "com.rift.coad.rdf.objmapping.base.number.RDFInteger",
                        "com.rift.coad.rdf.objmapping.base.number.RDFFloat",
                        "com.rift.coad.rdf.objmapping.base.number.RDFDouble",
                        "com.rift.coad.rdf.objmapping.base.str.GenericString"
                    };
        }

        return null;
    }


    /**
     * This method returns the type information
     *
     * @param name
     * @return
     */
    public static DataType getType(String name) throws TypeException {
        DataType result = null;
        try {
            if (name.equals("com.rift.coad.rdf.objmapping.inventory.Hardware")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Hardware();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Hardware");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Hardware");
                result.setDataName("Hardware");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Network")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Network();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Network");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Network");
                result.setDataName("Network");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Stock")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Stock();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Stock");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Stock");
                result.setDataName("Stock");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Software")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Software();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Software");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Software");
                result.setDataName("Software");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Inventory")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Inventory();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Inventory");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Inventory");
                result.setDataName("Inventory");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Rack")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Rack();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Rack");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Rack");
                result.setDataName("Rack");
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Host")) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Host();
                result.setBasicType("com.rift.coad.rdf.objmapping.inventory.Host");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.inventory.Host");
                result.setDataName("Host");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Str")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Str is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.SerialNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.SerialNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Name")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Name();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Name");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Name");
                result.setDataName("Name");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.AddressCode")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.AddressCode is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.PostalCode")) {
                result = new com.rift.coad.rdf.objmapping.client.base.address.PostalCode();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.address.PostalCode");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.address.PostalCode");
                result.setDataName("PostalCode");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.ZipCode")) {
                result = new com.rift.coad.rdf.objmapping.client.base.address.ZipCode();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.address.ZipCode");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.address.ZipCode");
                result.setDataName("ZipCode");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.RDFBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Address")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Address();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Address");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Address");
                result.setDataName("Address");
            } else if (name.equals("com.rift.coad.rdf.objmapping.organisation.Organisation")) {
                result = new com.rift.coad.rdf.objmapping.client.organisation.Organisation();
                result.setBasicType("com.rift.coad.rdf.objmapping.organisation.Organisation");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.organisation.Organisation");
                result.setDataName("Organisation");
                
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Description")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Description();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Description");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Description");
                result.setDataName("Description");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.UniqueId")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.UniqueId is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.URL")) {
                result = new com.rift.coad.rdf.objmapping.client.base.URL();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.URL");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.URL");
                result.setDataName("URL");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.id.IdNumber")) {
                result = new com.rift.coad.rdf.objmapping.client.base.id.IdNumber();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.id.IdNumber");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.id.IdNumber");
                result.setDataName("IdNumber");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISSN")) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.ISSN();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.serial.ISSN");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.serial.ISSN");
                result.setDataName("ISSN");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber")) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.GenericSerialNumber();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber");
                result.setDataName("ISSN");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISBN")) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.ISBN();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.serial.ISBN");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.serial.ISBN");
                result.setDataName("ISBN");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.ValidatedString")) {
                result = new com.rift.coad.rdf.objmapping.client.base.str.ValidatedString();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.str.ValidatedString");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.str.ValidatedString");
                result.setDataName("ValidatedString");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.GenericString")) {
                result = new com.rift.coad.rdf.objmapping.client.base.str.GenericString();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.str.GenericString");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.str.GenericString");
                result.setDataName("GenericString");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Domain")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Domain();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Domain");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Domain");
                result.setDataName("Domain");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.FirstNames")) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.FirstNames();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.name.FirstNames");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.name.FirstNames");
                result.setDataName("FirstNames");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Username")) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.Username();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.name.Username");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.name.Username");
                result.setDataName("Username");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Surname")) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.Surname();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.name.Surname");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.name.Surname");
                result.setDataName("Surname");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Email")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Email();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Email");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Email");
                result.setDataName("Email");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv6")) {
                result = new com.rift.coad.rdf.objmapping.client.base.ip.IPv6();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.ip.IPv6");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.ip.IPv6");
                result.setDataName("IPv6");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv4")) {
                result = new com.rift.coad.rdf.objmapping.client.base.ip.IPv4();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.ip.IPv4");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.ip.IPv4");
                result.setDataName("IPv4");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Password")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Password is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Country")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Country();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Country");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Country");
                result.setDataName("Country");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.IPAddress")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.IPAddress is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword")) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.ClearTextPassword();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword");
                result.setDataName("ClearTextPassword");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.MD5Password")) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.MD5Password();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.password.MD5Password");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.password.MD5Password");
                result.setDataName("MD5Password");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.SHAPassword")) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.SHAPassword();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.password.SHAPassword");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.password.SHAPassword");
                result.setDataName("SHAPassword");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Phone")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Phone();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Phone");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Phone");
                result.setDataName("Phone");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.DataType")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.DataType is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.CellPhone")) {
                result = new com.rift.coad.rdf.objmapping.client.base.phone.CellPhone();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.phone.CellPhone");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.phone.CellPhone");
                result.setDataName("CellPhone");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.Fax")) {
                result = new com.rift.coad.rdf.objmapping.client.base.phone.Fax();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.phone.Fax");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.phone.Fax");
                result.setDataName("Fax");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Title")) {
                result = new com.rift.coad.rdf.objmapping.client.base.Title();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.Title");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.Title");
                result.setDataName("Title");
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.ResourceBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.resource.ResourceBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.GenericResource")) {
                result = new com.rift.coad.rdf.objmapping.client.resource.GenericResource();
                result.setBasicType("com.rift.coad.rdf.objmapping.resource.GenericResource");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.resource.GenericResource");
                result.setDataName("GenericResource");
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.Person")) {
                result = new com.rift.coad.rdf.objmapping.client.person.Person();
                result.setBasicType("com.rift.coad.rdf.objmapping.person.Person");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.person.Person");
                result.setDataName("Person");
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.User")) {
                result = new com.rift.coad.rdf.objmapping.client.person.User();
                result.setBasicType("com.rift.coad.rdf.objmapping.person.User");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.person.User");
                result.setDataName("User");
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.IPService")) {
                result = new com.rift.coad.rdf.objmapping.client.service.IPService();
                result.setBasicType("com.rift.coad.rdf.objmapping.service.IPService");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.service.IPService");
                result.setDataName("IPService");
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.SoftwareService")) {
                result = new com.rift.coad.rdf.objmapping.client.service.SoftwareService();
                result.setBasicType("com.rift.coad.rdf.objmapping.service.SoftwareService");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.service.SoftwareService");
                result.setDataName("SoftwareService");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFLong")) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFLong();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.number.RDFLong");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.number.RDFLong");
                result.setDataName("RDFLong");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFInteger")) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFInteger();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.number.RDFInteger");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.number.RDFInteger");
                result.setDataName("RDFInteger");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFFloat")) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFFloat();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.number.RDFFloat");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.number.RDFFloat");
                result.setDataName("RDFFloat");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFDouble")) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFDouble();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.number.RDFDouble");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.number.RDFDouble");
                result.setDataName("RDFDouble");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFDate")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFDate is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.date.DateTime")) {
                result = new com.rift.coad.rdf.objmapping.client.base.date.DateTime();
                result.setBasicType("com.rift.coad.rdf.objmapping.base.date.DateTime");
                result.setIdForDataType("com.rift.coad.rdf.objmapping.base.date.DateTime");
                result.setDataName("DateTime");
            }

        } catch (TypeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TypeException("Failed to instanciate a new type : " + ex.getMessage(), ex);
        }
        if (result != null) {
            return result;
        }
        throw new TypeException("Unrecognised type : " + name);

    }


    /**
     * This method returns the duplicated type
     *
     * @param type The type to duplicate
     * @return The duplicated type.
     * @throws com.rift.coad.rdf.objmapping.util.client.type.TypeException
     */
    public static DataType duplicateType(DataType type) throws TypeException {
        return getType(type.getBasicType());
    }




    /**
     * This method returns a copy of the type object.
     *
     * @param type The type to copy
     * @return
     */
    public static DataType copyType(DataType type) throws TypeException {
        DataType result = null;
        try {
            if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Hardware.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Hardware();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Network.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Network();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Stock.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Stock();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Software.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Software();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Inventory.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Inventory();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Rack.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Rack();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.inventory.Host.class)) {
                result = new com.rift.coad.rdf.objmapping.client.inventory.Host();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Str.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Str is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.SerialNumber.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.SerialNumber is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Name.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Name();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.AddressCode.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.AddressCode is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.address.PostalCode.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.address.PostalCode();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.address.ZipCode.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.address.ZipCode();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.RDFBase.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.RDFBase is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Address.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Address();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.organisation.Organisation.class)) {
                result = new com.rift.coad.rdf.objmapping.client.organisation.Organisation();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Description.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Description();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.UniqueId.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.UniqueId is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.URL.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.URL();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.id.IdNumber.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.id.IdNumber();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.serial.ISSN.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.ISSN();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.serial.GenericSerialNumber.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.GenericSerialNumber();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.serial.ISBN.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.serial.ISBN();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.str.ValidatedString.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.str.ValidatedString();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.str.GenericString.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.str.GenericString();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Domain.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Domain();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.name.FirstNames.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.FirstNames();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.name.Username.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.Username();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.name.Surname.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.name.Surname();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Email.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Email();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.ip.IPv6.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.ip.IPv6();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.ip.IPv4.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.ip.IPv4();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Password.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Password is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Country.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Country();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.IPAddress.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.IPAddress is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.password.ClearTextPassword.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.ClearTextPassword();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.password.MD5Password.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.MD5Password();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.password.SHAPassword.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.password.SHAPassword();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Phone.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Phone();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.DataType.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.DataType is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.phone.CellPhone.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.phone.CellPhone();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.phone.Fax.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.phone.Fax();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.Title.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.Title();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.resource.ResourceBase.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.resource.ResourceBase is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.resource.GenericResource.class)) {
                result = new com.rift.coad.rdf.objmapping.client.resource.GenericResource();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.person.Person.class)) {
                result = new com.rift.coad.rdf.objmapping.client.person.Person();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.person.User.class)) {
                result = new com.rift.coad.rdf.objmapping.client.person.User();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.service.IPService.class)) {
                result = new com.rift.coad.rdf.objmapping.client.service.IPService();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.service.SoftwareService.class)) {
                result = new com.rift.coad.rdf.objmapping.client.service.SoftwareService();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.RDFNumber.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFNumber is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.number.RDFLong.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFLong();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.number.RDFInteger.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFInteger();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.number.RDFFloat.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFFloat();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.number.RDFDouble.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.number.RDFDouble();
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.RDFDate.class)) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFDate is abstract and cannot be instanciated");
            } else if (type.getClass().equals(com.rift.coad.rdf.objmapping.client.base.date.DateTime.class)) {
                result = new com.rift.coad.rdf.objmapping.client.base.date.DateTime();
            }
            result.setBasicType(type.getBasicType());
            result.setIdForDataType(type.getIdForDataType());
            result.setDataName(type.getDataName());
            if (type instanceof ResourceBase) {
                ResourceBase base = (ResourceBase)type;
                if (base.getAttributes() != null) {
                    DataType[] types = new DataType[base.getAttributes().length];
                    for (int index = 0; index < base.getAttributes().length; index++) {
                        types[index] = copyType(base.getAttributes()[index]);
                    }
                    base.setAttributes(types);
                }
            }

        } catch (TypeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TypeException("Failed to instanciate a new type : " + ex.getMessage(), ex);
        }
        if (result != null) {
            return result;
        }
        throw new TypeException("Unrecognised type : " + type.getDataName());

    }
}
