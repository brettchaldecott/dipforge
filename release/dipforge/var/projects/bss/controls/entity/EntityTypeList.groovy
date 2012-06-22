/*
 * bss: Description
 * Copyright (C) Thu Jun 21 06:25:18 SAST 2012 owner 
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
 * EntityTypeList.groovy
 * @author admin
 */

package entity


import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.EntityManagementList");

log.info("Hello from entity type list : " + params)

if (params.node == "root") {
    
    tree += [
        [
            id: "organisation",
            type: "organisation",
            name: "Organisation",
            view: "organisation",
            model: "organisastion",
            leaf: true,
            icon: 'project'        
        ],
        [
            id: "user",
            type: "user",
            name: "User",
            view: "User",
            model: "user",
            leaf: true,
            icon: 'project'        
        ]
        ]
    
}

log.info("Tree " + tree)
builder(tree)
println builder.toString()