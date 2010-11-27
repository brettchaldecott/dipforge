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
 * HelpDialog.java
 */

// package path
package com.rift.coad.web.admin.client;

// gwt imports
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * This object is responsible for displaying the help information.
 *
 * @author brett chaldecott
 */
public class HelpDialog extends DialogBox{
    
    // private member variables.
    private static HelpDialog singleton = null;
    private HTML content = new HTML();
    
    /**
     * Creates a new instance of HelpDialog
     */
    public HelpDialog() {
        // set the text for this panel
        //setText("Coadunation Help");
        
        // Create a VerticalPanel to contain the 'about' label and the 'OK' button.
        VerticalPanel outer = new VerticalPanel();
        outer.setWidth("100%");
        
        // scrol panel
        content.setHTML("test");
        content.setWordWrap(true);
        ScrollPanel text = new ScrollPanel(content);
        text.setStyleName("help-Scroller");
        text.setWidth("100%");
        //text.setAlwaysShowScrollBars(true);
        outer.add(text);
        
        // set the style on the outer
        outer.setStyleName("help-Text");
        
        // Create the 'OK' button, along with a listener that hides the dialog
        // when the button is clicked.
        outer.add(new Button("Close", new ClickListener() {
          public void onClick(Widget sender) {
            hide();
          }
        }));
        
        // set the width
        setWidget(outer);
        this.center();
        hide();
    }
    
    /**
     * This method returns an instance of the help dialog
     */
    public synchronized static HelpDialog getInstance() {
        if (singleton == null) {
            singleton = new HelpDialog();
        }
        return singleton;
    }
    
    
    /**
     * This method sets the content of the help dialog
     */
    public void setContent(String text) {
        content.setHTML(text);
        this.show();
        
    }
}
