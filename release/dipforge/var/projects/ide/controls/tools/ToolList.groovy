/*
 * ide: Description
 * Copyright (C) Sat Nov 24 12:53:59 SAST 2012 owner 
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
 * ToolList.groovy
 * @author admin
 */

package tools

    
import groovy.json.*;
import org.apache.log4j.Logger;

def builder = new JsonBuilder()
def log = Logger.getLogger("tools.ToolList");

def tree = [
        [
            id: "T:Tools:sparql",
            project: "Tools",
            file: "sparql console",
            path: "sparql.gsp",
            user: "admin",
            leaf: true,
  		    iconCls: 'file',
  		    editor: 'sparql',
  		    project_dir: false,
  		    mode: ''
        ]
    ]
    
    
log.info("Tree " + tree)
builder(tree)
println builder.toString()
