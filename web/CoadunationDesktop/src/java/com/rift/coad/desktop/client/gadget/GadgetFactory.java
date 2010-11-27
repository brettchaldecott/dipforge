/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
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
 * GadgetFactory.java
 */

// package path
package com.rift.coad.desktop.client.gadget;

// gwt imports
import com.google.gwt.user.client.ui.Widget;
import com.rift.coad.desktop.client.top.GadgetInfo;

/**
 * This object is responsible for spanning the gadgets for the desktop.
 * 
 * @author brett chaldecott
 */
public class GadgetFactory {
    // private member variables
    private static GadgetFactory singleton  = null;
    
    /**
     * The private constructor of the gadget factory.
     */
    private GadgetFactory() {
        
    }
    
    
    /**
     * This method returns the singleton instance of the gadget factory.
     * 
     * @return The reference to the gadget factory.
     */
    public synchronized static GadgetFactory getInstance() {
        if (singleton == null) {
            singleton = new GadgetFactory();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the appropriage gadget
     */
    public Widget getGadget(GadgetInfo info) {
        if (info.getIdentifier().equals("UserGadget")) {
            return new UserGadget();
        }
        return new Gadget(info);
    }
}
