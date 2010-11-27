/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
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
 * NavigationTree.java
 */

// package path
package com.rift.coad.gwt.lib.client.console;

/**
 * The data handler for getting navigation information.
 *
 * @author brett chaldecott
 */
public interface NavigationDataHandler {

    /**
     * This method gets the child nodes for the given node name.
     *
     * @param name The name of the node to retrieve children for.
     * @param callback The object to call back to set the information on.
     */
    public void getChildNodes(String name, NavigationDataCallback callback);

}
