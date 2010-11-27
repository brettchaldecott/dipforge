/*
 * CoadunationGWTRDFResources: The rdf resource object mappings.
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
 * TypeStack.java
 */


// package path
package com.rift.coad.rdf.objmapping.ui.client.tree.stack;

// smart stack imports
import com.rift.coad.rdf.objmapping.util.client.type.TypeException;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;


/**
 * This object is responsible for displaying the type stack.
 *
 * @author brett chaldecott
 */
public class TypeStack extends SectionStack {


    /**
     * The default constructor for the type stack.
     */
    public TypeStack() {
        setVisibilityMode(VisibilityMode.MUTEX);
        setWidth(200);
        setHeight100();

        boolean expanded = true;
        for (String group : TypeManager.GROUPING) {
            try {
                SectionStackSection section = new SectionStackSection(group);
                section.setExpanded(expanded);
                String[] types = TypeManager.getTypesForGroup(group);
                section.addItem(new TypeTreeGrid(types));
                //section1.addItem(new Img("pieces/48/pawn_blue.png", 48, 48));
                this.addSection(section);
                expanded = false;
            } catch (Exception ex) {
                // ignore
            }
        }
        
    }



}
