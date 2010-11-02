/*
 * OfficeSuite: The implementation of the coadunation office product suite.
 * Copyright (C) 2010  Rift IT Contracting
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
 * ViewDocumentWindowFactory.java
 */

// package path
package com.rift.coad.office.client.documents;

import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * This object is responsible for displaying a php file.
 *
 * @author brett chaldecott
 */
public class ViewDocumentWindowFactory {

    // private member variables
    private Window win;
    private PanelFactory factory;
    private String file;


    /**
     * Responsible for a new instance.
     * @param factory The factory that the window is attached to.
     * @param file The file that will be viewed by the
     * @param fileName The name of the file.
     */
    public ViewDocumentWindowFactory(PanelFactory factory, String scope, String fileName) {
        this.factory = factory;
        this.file = scope + "/" + fileName;
    }


    /**
     * This method creates a new window
     *
     * @return
     */
    public void createWindow() {
        if (win == null) {
            win = new Window();
            win.setTitle("View: http://[host]:[port]" + ScopeCache.getInstance().getDocumentBase()
                    + "/" + file);
            win.setKeepInParentRect(true);
            win.setWidth(520);
            win.setHeight(460);
            int windowTop = 40;
            // assume the factory has been implemented as a singleton that manages
            // the panels appropriatly
            int windowLeft = factory.create().getWidth()- (win.getWidth() + 20);
            win.setLeft(windowLeft);
            win.setTop(windowTop);
            win.setCanDragReposition(true);
            win.setCanDragResize(true);

            win.setSrc(ScopeCache.getInstance().getDocumentBase() + "/"
                    + file);

            // assume the factory has been implemented as a singleton that manages
            // the panels appropriatly
            factory.create().addChild(win);
        }
        win.draw();
    }
}
