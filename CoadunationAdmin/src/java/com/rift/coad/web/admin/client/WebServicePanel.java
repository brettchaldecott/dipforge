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
 * WebServicePancle.java
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
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * This panel is responsible for displaying the web servicde information.
 *
 * @author brett chaldecott
 */
public class WebServicePanel extends Composite implements ObjectsListener,
        ClickListener {
    
    // private member variables
    private DockPanel dockPanel = null;
    private ObjectsPanel objectsPanel = null;
    private WSDLPanel wsdlPanel = null;
    private WebServiceManagerAsync service = null;
    private String webService = null;
    private Label help = null;
    
    /**
     * Creates a new instance of WebServicePancle
     */
    public WebServicePanel() {
        VerticalPanel lhs = new VerticalPanel();
        lhs.setSpacing(2);
        objectsPanel = new ObjectsPanel(this,450);
        lhs.add(objectsPanel);
        
        help = new Label("Help");
        help.addClickListener(this);
        help.setStyleName("click-Label");
        help.setWidth("100%");
        lhs.add(help);
        
        VerticalPanel rhs = new VerticalPanel();
        rhs.setWidth("100%");
        rhs.setSpacing(2);
        wsdlPanel = new WSDLPanel();
        wsdlPanel.setWidth("100%");
        rhs.add(wsdlPanel);
        
        
        dockPanel = new DockPanel();
        dockPanel.setWidth("100%");
        dockPanel.add(lhs,DockPanel.WEST);
        dockPanel.add(rhs,DockPanel.CENTER);
        dockPanel.setSpacing(2);
        dockPanel.setCellWidth(rhs, "100%");
        
        // init the daemon manager
        initWebServiceManager();
        
        // Create an asynchronous callback to handle the result.
        service.getServices(new AsyncCallback() {
            public void onSuccess(Object result) {
                objectsPanel.setObjects((String[])result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Failed to load the list of objects : " +
                        caught.getMessage());
            }
        });
        
        
        // init the widget
        initWidget(dockPanel);
    }
    
    /**
     * init the daemon manager
     */
    private void initWebServiceManager() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        service = (WebServiceManagerAsync) GWT.create(WebServiceManager.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "webservicemanager";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    /**
     * This method is called when a wsdl object is selected.
     *
     * @param key The selected key.
     */
    public void objectSelected(String key) {
        webService = key;
        // Create an asynchronous callback to handle the result.
        service.getWebServiceDef(webService,new AsyncCallback() {
            public void onSuccess(Object result) {
                wsdlPanel.setWebServiceDef((WebServiceDef)result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Failed to load the list of objects :" +
                        caught.getMessage());
            }
        });
    }
    
    
    /**
     * This method is responsible for displaying the help information.
     */
    public void onClick(Widget widget) {
        if (help == widget) {
            HelpDialog.getInstance().setContent(
                    "The Web Services Admin Panel enables users to view the " +
                    "<br>web service information for the Coadunation managed " +
                    "<br>web services." +
                    "<br>" +
                    "<br>Follow these steps to view the information for a " +
                    "<br>deployed web service." +
                    "<br>" +
                    "<br><b>Step 1:</b>" +
                    "<br>Select the web service by name from the objects panel." +
                    "<br><img src='images/WebServiceObjects.gif'/>" +
                    "<br>" +
                    "<br><b>Step 2:</b>" +
                    "<br>View the WSDL information displayed in the right hand " +
                    "<br>side panel." +
                    "<br><img src='images/WSDL.gif'/>");
            //HelpDialog.getInstance().show();
        }
    }
    
}
