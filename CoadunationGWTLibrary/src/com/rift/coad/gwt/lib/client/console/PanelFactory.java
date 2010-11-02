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
 * PanelFactory.java
 */

// package path
package com.rift.coad.gwt.lib.client.console;

// smart gwt canvas
import com.smartgwt.client.widgets.Canvas;

public interface PanelFactory {

    /**
     * This method return a new canvas.
     *
     * @return This method creates a new canvas.
     */
    Canvas create();

    
    /**
     * This method returns the id fo the canvas factory.
     *
     * @return The id of the panel factory.
     */
    String getID();


    /**
     * The description of this panel factory.
     *
     * @return The string containing the description of this panel factory.
     */
    String getDescription();
}
