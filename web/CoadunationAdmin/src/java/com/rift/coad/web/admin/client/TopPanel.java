/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * TopPanel.java
 */

// package
package com.rift.coad.web.admin.client;

// images
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Image;

/**
 * This panel displays the coadunation logo.
 *
 * @author brett chaldecott
 */
public class TopPanel extends Composite {
    
    // private member variables
    private VerticalPanel panel = new VerticalPanel();
    
    /** 
     * Creates a new instance of TopPanel 
     */
    public TopPanel() {
        panel.setWidth("100%");
        //panel.add(new Image("images/AdminLogo.gif"));
        initWidget(panel);
    }
    
}
