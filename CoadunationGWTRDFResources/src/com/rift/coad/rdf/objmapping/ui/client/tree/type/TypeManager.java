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
package com.rift.coad.rdf.objmapping.ui.client.tree.type;

// smart gwt imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;


// the data type
import com.rift.coad.rdf.objmapping.util.client.type.*;
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.smartgwt.client.util.SC;


/**
 * This object is responsible for creating the specified type object.
 *
 * @author brett chaldecot
 */
public class TypeManager {

    /**
     * This method returns the type information
     *
     * @param name
     * @return
     */
    public static void getTree(Tree tree, String name) throws TypeException {
        try {
            DataTypeTreeNode[] nodes = null;
            if (name.equals("com.rift.coad.rdf.objmapping.inventory.Hardware")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Hardware", "rdf/network-server.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Hardware") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Network")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Network", "rdf/network-workgroup.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Network") ,false,
                                new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Stock")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Stock", "rdf/package-x-generic.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Stock") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Software")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Software", "rdf/media-optical.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Software") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Inventory")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Inventory", "rdf/edit-paste.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Inventory") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Rack")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Rack","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Rack") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png",false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Host")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Host","rdf/network-server.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.inventory.Host") ,false,
                                new DataTypeTreeNode("Hostname", "rdf/network-server.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Str")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Str is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.SerialNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.SerialNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Name")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Name","rdf/system-users.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Name") ,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.AddressCode")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.AddressCode is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.PostalCode")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Postal Code", "rdf/internet-mail.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.address.PostalCode") ,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.ZipCode")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Zip Code", "rdf/internet-mail.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.address.ZipCode") ,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.RDFBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Address")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Address","rdf/internet-mail.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Address") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Value","rdf/edit-paste.png", false),
                                new DataTypeTreeNode("Code", "rdf/internet-mail.png", false),
                                new DataTypeTreeNode("Country", "rdf/internet-web-browser.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.organisation.Organisation")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Organisation", "rdf/system-users.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.organisation.Organisation") ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Description")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Description","rdf/edit-paste.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Description") ,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.UniqueId")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.UniqueId is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.URL")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("URL", "rdf/preferences-system-network-proxy.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.URL"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.id.IdNumber")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("ID","rdf/system-users.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.id.IdNumber") ,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISSN")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("ISSN","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.serial.ISSN"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Generic Serial Number","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISBN")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("ISBN","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.serial.ISBN"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.ValidatedString")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Validated String", "rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.str.ValidatedString"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.GenericString")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Generic String","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.str.GenericString"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Domain")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Domain","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Domain"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.FirstNames")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("First Names","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.name.FirstNames"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Username")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Username","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.name.Username"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Surname")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Surname","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.name.Surname"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Email")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Email","rdf/internet-mail.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Email"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv6")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("IPv6","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.ip.IPv6"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv4")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("IPv4","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.ip.IPv4"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Password")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Password is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Country")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Country","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Country"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.IPAddress")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.IPAddress is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Clear Text Password","rdf/system-lock-screen.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.password.ClearTextPassword"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.MD5Password")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("MD5 Password","rdf/system-lock-screen.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.password.MD5Password"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.SHAPassword")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("SSHA Password","rdf/system-lock-screen.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.password.SHAPassword"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Phone")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Phone","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Phone"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.DataType")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.DataType is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.CellPhone")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Cell Phone","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.phone.CellPhone"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.Fax")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Fax","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.phone.Fax"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Title")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Title","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.Title"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.ResourceBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.resource.ResourceBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.GenericResource")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Generic Resource","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.resource.GenericResource"),true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.Person")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Person","rdf/system-users.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.person.Person"),true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("First Names", "rdf/system-users.png", false),
                                   new DataTypeTreeNode("Surname","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.User")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("User","rdf/system-users.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.person.User"),true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Forename", "rdf/system-users.png", false),
                                   new DataTypeTreeNode("Surname","rdf/system-users.png", false),
                                   new DataTypeTreeNode("Username","rdf/system-users.png", false),
                                   new DataTypeTreeNode("Password","rdf/system-lock-screen.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.IPService")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("IP Service","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.service.IPService"),false,
                                   new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Hostname", "rdf/network-server.png", false),
                                   new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.SoftwareService")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Software Service","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.service.SoftwareService"),false,
                                   new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Hostname","rdf/network-server.png", false),
                                   new DataTypeTreeNode("Name","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFLong")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Long","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.number.RDFLong"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFInteger")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Integer","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.number.RDFInteger"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFFloat")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Float","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.number.RDFFloat"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFDouble")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Double","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.number.RDFDouble"),false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFDate")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFDate is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.date.DateTime")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("dateTime","rdf/applications-system.png",
                                   com.rift.coad.rdf.objmapping.util.client.type.TypeManager.getType(
                                   "com.rift.coad.rdf.objmapping.base.date.DateTime"),false)};
            }

            if (nodes != null) {
                tree.addList(nodes, tree.findById("root"));
            }

            
        } catch (TypeException ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw new TypeException("Failed to instanciate a new type : " + ex.getMessage(), ex);
        }
        
        
    }


    /**
     * This method returns the tree
     *
     * @param name The name of the type.
     * @return The tree nodes.
     * @throws com.rift.coad.rdf.objmapping.util.client.type.TypeException
     */
    public static DataTypeTreeNode[] getTree(DataType dataType) throws TypeException {
        try {
            DataTypeTreeNode[] nodes = null;
            String name = dataType.getBasicType();
            String nodeName = dataType.getDataName();
            if (name.equals("com.rift.coad.rdf.objmapping.inventory.Hardware")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/network-server.png",dataType ,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Network")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/network-workgroup.png",dataType,false,
                                new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Stock")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/package-x-generic.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Software")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/media-optical.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Inventory")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/edit-paste.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Rack")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name", "rdf/system-users.png",false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Host")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/network-server.png",dataType,false,
                                new DataTypeTreeNode("Hostname", "rdf/network-server.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Str")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Str is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.SerialNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.SerialNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Name")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-users.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.AddressCode")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.AddressCode is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.PostalCode")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/internet-mail.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.ZipCode")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/internet-mail.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.RDFBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Address")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/internet-mail.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Value","rdf/edit-paste.png", false),
                                new DataTypeTreeNode("Code", "rdf/internet-mail.png", false),
                                new DataTypeTreeNode("Country", "rdf/internet-web-browser.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.organisation.Organisation")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/system-users.png",dataType,false,
                                new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                new DataTypeTreeNode("Name","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Description")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/edit-paste.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.UniqueId")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.UniqueId is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.URL")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/preferences-system-network-proxy.png",
                                   dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.id.IdNumber")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-users.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISSN")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISBN")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.ValidatedString")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.GenericString")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Domain")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.FirstNames")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Username")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Surname")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Email")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/internet-mail.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv6")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv4")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Password")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Password is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Country")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.IPAddress")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.IPAddress is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-lock-screen.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.MD5Password")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-lock-screen.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.SHAPassword")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-lock-screen.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Phone")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.DataType")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.DataType is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.CellPhone")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.Fax")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Title")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.ResourceBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.resource.ResourceBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.GenericResource")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName, "rdf/applications-system.png",dataType,true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.Person")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-users.png",dataType,true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("First Names", "rdf/system-users.png", false),
                                   new DataTypeTreeNode("Surname","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.User")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/system-users.png",dataType,true,
                                   new DataTypeTreeNode("Id", "rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Forename", "rdf/system-users.png", false),
                                   new DataTypeTreeNode("Surname","rdf/system-users.png", false),
                                   new DataTypeTreeNode("Username","rdf/system-users.png", false),
                                   new DataTypeTreeNode("Password","rdf/system-lock-screen.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.IPService")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,true,
                                   new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Hostname", "rdf/network-server.png", false),
                                   new DataTypeTreeNode("Name", "rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.SoftwareService")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,true,
                                   new DataTypeTreeNode("Id","rdf/x-office-address-book.png", false),
                                   new DataTypeTreeNode("Hostname","rdf/network-server.png", false),
                                   new DataTypeTreeNode("Name","rdf/system-users.png", false))};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFLong")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFInteger")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFFloat")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFDouble")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFDate")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFDate is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.date.DateTime")) {
                nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode(nodeName,"rdf/applications-system.png",dataType,false)};
            }

            return nodes;
        } catch (TypeException ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw new TypeException("Failed to instanciate a new type : " + ex.getMessage(), ex);
        }


    }


    /**
     * This method returns the icon path
     *
     * @param name The name of the icon.
     * @return The string containing the icon.
     */
    public static String getIcon(String name) throws TypeException {
        try {
            if (name.equals("com.rift.coad.rdf.objmapping.inventory.Hardware")) {
                return "rdf/network-server.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Network")) {
                return "rdf/network-workgroup.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Stock")) {
                return "rdf/package-x-generic.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Software")) {
                return "rdf/media-optical.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Inventory")) {
                return "rdf/edit-paste.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Rack")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.inventory.Host")) {
                return "rdf/network-server.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Str")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Str is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.SerialNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.SerialNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Name")) {
                return "rdf/system-users.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.AddressCode")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.AddressCode is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.PostalCode")) {
                return "rdf/internet-mail.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.address.ZipCode")) {
                return "rdf/internet-mail.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.RDFBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Address")) {
                return "rdf/internet-mail.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.organisation.Organisation")) {
                return "rdf/system-users.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Description")) {
                return "rdf/edit-paste.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.UniqueId")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.UniqueId is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.URL")) {
                return "rdf/preferences-system-network-proxy.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.id.IdNumber")) {
                return "rdf/system-users.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISSN")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.GenericSerialNumber")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.serial.ISBN")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.ValidatedString")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.str.GenericString")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Domain")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.FirstNames")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Username")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.name.Surname")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Email")) {
                return "rdf/internet-mail.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv6")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.ip.IPv4")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Password")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.Password is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Country")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.IPAddress")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.IPAddress is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.ClearTextPassword")) {
                return "rdf/system-lock-screen.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.MD5Password")) {
                return "rdf/system-lock-screen.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.password.SHAPassword")) {
                return "rdf/system-lock-screen.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Phone")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.DataType")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.base.DataType is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.CellPhone")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.phone.Fax")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.Title")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.ResourceBase")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.resource.ResourceBase is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.resource.GenericResource")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.Person")) {
                return "rdf/system-users.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.person.User")) {
                return "rdf/system-users.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.IPService")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.service.SoftwareService")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFNumber")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFNumber is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFLong")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFInteger")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFFloat")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.number.RDFDouble")) {
                return "rdf/applications-system.png";
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.RDFDate")) {
                throw new TypeException(
                        "The type com.rift.coad.rdf.objmapping.client.base.RDFDate is abstract and cannot be instanciated");
            } else if (name.equals("com.rift.coad.rdf.objmapping.base.date.DateTime")) {
                return "rdf/applications-system.png";
            }

        } catch (TypeException ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            SC.say("Failed to add the node : " + ex.getMessage());
            throw new TypeException("Failed to instanciate a new type : " + ex.getMessage(), ex);
        }

        throw new TypeException("The type [" + name + "] was not recognised");
    }
}
