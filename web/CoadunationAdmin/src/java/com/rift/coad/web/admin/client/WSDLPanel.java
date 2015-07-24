/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * WSDLPanel.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;


/**
 * This object is responsible for displaying the WSDL panel information.
 *
 * @author brett chaldecott
 */
public class WSDLPanel extends Composite {
    
    // class constants
    private final static int MAX_DEFAULT_ROWS = 5;
    
    // scroll panel
    private MethodListener listener = null;
    private HTML url = new HTML();
    private HTML wsdl = new HTML();
    
    /**
     * Creates a new instance of WSDLPanel
     */
    public WSDLPanel() {
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        
        // setup the label
        Label objectLabel = new Label("WSDL");
        objectLabel.setStyleName("header-Label");
        objectLabel.setWidth("100%");
        panel.add(objectLabel);
        
        VerticalPanel wsdlVerticalPanel = new VerticalPanel();
        wsdlVerticalPanel.setWidth("100%");
        url.setWidth("100%");
        url.setStyleName("url-Row");
        url.setHTML("&nbsp;");
        wsdlVerticalPanel.add(url);
        
        
        wsdl.setHTML("&nbsp;");
        wsdl.setWordWrap(true);
        ScrollPanel wsdlScrollPanel = new ScrollPanel(wsdl);
        wsdlScrollPanel.setStyleName("wsdl-Scroller");
        wsdlScrollPanel.setWidth("100%");
        wsdlVerticalPanel.add(wsdlScrollPanel);
        
        panel.add(wsdlVerticalPanel);
        
        // border
        panel.setStyleName("panel-Border");
        
        initWidget(panel);
        
    }
    
    
    /**
     * This method sets the web service definition
     */
    public void setWebServiceDef(WebServiceDef def) {
        url.setHTML(def.getURL());
        String wsdl =def.getWSDL().replaceAll("<","&lt;").
                replaceAll(">","&gt;").replaceAll("\n","<br>").
                replaceAll(" ","&nbsp;").
                replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;");
        this.wsdl.setHTML(wsdl);
    }
}
