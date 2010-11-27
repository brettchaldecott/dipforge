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
 * DesktopManager.java
 */
package com.rift.coad.desktop.client.widgets;

// imports
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

// coadunation imports
import com.rift.coad.desktop.client.top.TopPanel;

/**
 * This panel displays the widgets on the frontend.
 * 
 * @author brett chaldecott
 */
public class WidgetPanel extends Composite {
    // class constants
    public final static int WIDTH = 102;    // private member variables
    private VerticalPanel widgets = new VerticalPanel();

    /**
     * The constructor of the widget panel.
     */
    public WidgetPanel() {

        // setup the master panel
        HorizontalPanel outer = new HorizontalPanel();
        HorizontalPanel border = new HorizontalPanel();
        border.setStyleName("WidgetPanelBorder");
        border.setHeight("100%");
        outer.add(border);

        // setup the widgets panel
        widgets.setStyleName("WidgetPanel");
        widgets.setHeight("100%");
        outer.add(widgets);
        
        // init the outer panel
        initWidget(outer);
    }

    /**
     * This method is responsible for setting the size of various windows when things
     * get resized.
     * @param width The width of the entire window.
     * @param height The height of the entire window.
     */
    public void onWindowResized(int width, int height) {
        this.setHeight("" + (height - TopPanel.HEIGHT));
    }
}
