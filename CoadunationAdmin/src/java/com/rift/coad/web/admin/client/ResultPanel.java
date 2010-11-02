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
 * ResultPanel.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTML;


/**
 * The result panel
 *
 * @author brett chaldecott
 */
public class ResultPanel extends Composite {
    
    // private member variables
    private ScrollPanel scrollPanel = null;
    private HTML text = null;
    
    /**
     * Creates a new instance of ResultPanel
     */
    public ResultPanel() {
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setStyleName("panel-Border");
        Label objectLabel = new Label("Result");
        objectLabel.setStyleName("header-Label");
        objectLabel.setWidth("100%");
        panel.add(objectLabel);
        text = new HTML();
        text.setHTML("&nbsp;");
        text.setWordWrap(true);
        scrollPanel = new ScrollPanel(text);
        scrollPanel.setWidth("100%");
        scrollPanel.setStyleName("method-Result-Scroller");
        panel.add(scrollPanel);
        
        initWidget(panel);
        
    }
    
    
    /**
     * This method sets the result text.
     */
    public void setResult(String result) {
        text.setHTML(result.replaceAll("<","&lt;").
                replaceAll(">","&gt;").replaceAll("\n","<br>").
                replaceAll(" ","&nbsp;").
                replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;"));
    }
}
