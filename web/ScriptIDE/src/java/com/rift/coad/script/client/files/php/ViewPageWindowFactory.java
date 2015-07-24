/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  2015 Burntjam
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
 * ViewSourceCache.java
 */

// package path
package com.rift.coad.script.client.files.php;

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
public class ViewPageWindowFactory {

    // private member variables
    private Window win;
    private FileEditorFactory factory;
    private String file;


    /**
     * Responsible for a new instance.
     * @param factory The factory that the window is attached to.
     * @param file The file that will be viewed by the
     * @param fileName The name of the file.
     */
    public ViewPageWindowFactory(FileEditorFactory factory, String scope, String fileName) {
        this.factory = factory;
        this.file = scope.replaceAll("[.]", "/") + "/" + fileName;
    }


    /**
     * This method creates a new window
     *
     * @return
     */
    public void createWindow() {
        if (win == null) {
            win = new Window();
            win.setTitle("View: http://[host]:[port]" + ViewSourceCache.getInstance().getPath()
                    + "/" + file);
            win.setKeepInParentRect(true);
            win.setWidth(520);
            win.setHeight(460);
            int windowTop = 40;
            int windowLeft = factory.getPanel().getWidth()- (win.getWidth() + 20);
            win.setLeft(windowLeft);
            win.setTop(windowTop);
            win.setCanDragReposition(true);
            win.setCanDragResize(true);

            win.setSrc(ViewSourceCache.getInstance().getPath() + "/" + file);

            factory.getPanel().addChild(win);
        }
        win.draw();
    }
}
