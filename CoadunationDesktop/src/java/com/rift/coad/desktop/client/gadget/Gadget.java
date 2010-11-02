/*
 * DesktopServerClient: The client interface to the desktop server.
 * Copyright (C) 2008  Rift IT Contracting
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
 * Gadget.java
 */

// package path
package com.rift.coad.desktop.client.gadget;

// gwt imports
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

// desktop imports
import com.rift.coad.desktop.client.top.GadgetInfo;

/**
 * This object is responsible for representing a gadget.
 * 
 * @author brett chaldecott
 */
public class Gadget extends Composite {
    // private member variables
    private HTML frame = null;
    
    /**
     * The constructor of the gadget object.
     * 
     * @param info The information about
     */
    public Gadget(GadgetInfo info) {
        frame = new HTML("<iframe src='" + info.getApp().getUrl() + "' width='" + info.getApp().getWidth() +
                "' height='" + info.getApp().getHeight() + "' frameborder=0/>");
        frame.setWidth("" + info.getApp().getWidth());
        frame.setHeight("" + info.getApp().getWidth());
        initWidget(frame);
    }
}
