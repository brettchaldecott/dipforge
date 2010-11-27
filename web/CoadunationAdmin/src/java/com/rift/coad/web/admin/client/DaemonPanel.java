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
 * DaemonPanel.java
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Widget;


/**
 * This panel supplies access to the daemons running in Coadunation.
 *
 * @author brett chaldecott
 */
public class DaemonPanel extends Composite implements ObjectsListener,
        MethodsListener, MethodListener, ClickListener {
    
    // private member variables
    private DockPanel dockPanel = null;
    private ObjectsPanel objectsPanel = null;
    private MethodsPanel methodsPanel = null;
    private MethodPanel methodPanel = null;
    private ResultPanel resultPanel = null;
    private DaemonManagerAsync service = null;
    private Label help = null;
    
    // member variables
    private String daemon = null;
    private String method = null;
    
    /**
     * Creates a new instance of DaemonPanel
     */
    public DaemonPanel() {
        VerticalPanel lhs = new VerticalPanel();
        lhs.setSpacing(2);
        objectsPanel = new ObjectsPanel(this);
        lhs.add(objectsPanel);
        methodsPanel = new MethodsPanel(this);
        lhs.add(methodsPanel);
        
        help = new Label("Help");
        help.addClickListener(this);
        help.setStyleName("click-Label");
        help.setWidth("100%");
        lhs.add(help);
        
        VerticalPanel rhs = new VerticalPanel();
        rhs.setWidth("100%");
        rhs.setSpacing(2);
        methodPanel = new MethodPanel(this);
        rhs.add(methodPanel);
        resultPanel = new ResultPanel();
        resultPanel.setWidth("100%");
        rhs.add(resultPanel);
        
        
        dockPanel = new DockPanel();
        dockPanel.setWidth("100%");
        dockPanel.add(lhs,DockPanel.WEST);
        dockPanel.add(rhs,DockPanel.CENTER);
        dockPanel.setSpacing(2);
        dockPanel.setCellWidth(rhs, "100%");
        
        // init the daemon manager
        initDaemonManager();
        
        // Create an asynchronous callback to handle the result.
        service.getDaemons(new AsyncCallback() {
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
    private void initDaemonManager() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        service = (DaemonManagerAsync) GWT.create(DaemonManager.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "daemonmanager";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    
    
    /**
     * This method is responsible for dealing with the entry selected events.
     *
     * @param key The key identifying the selected entry.
     */
    public void objectSelected(String key) {
        daemon = key;
        // Create an asynchronous callback to handle the result.
        service.getMethods(daemon,new AsyncCallback() {
            public void onSuccess(Object result) {
                methodPanel.resetMethod();
                methodsPanel.setMethods((String[])result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Failed to load the list of objects :" +
                        caught.getMessage());
            }
        });
    }
    
    
    /**
     * This method deals with selected methods
     *
     * @param key The key identifying the selected method
     */
    public void methodSelected(String key) {
        method = key;
        // Create an asynchronous callback to handle the result.
        service.getMethod(daemon,method,new AsyncCallback() {
            public void onSuccess(Object result) {
                methodPanel.setMethod((MethodDef)result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retrieve the method information : " +
                        caught.getMessage());
            }
        });
    }
    
    
    /**
     * This method is invoked to handle a method being called.
     *
     * @param method The method being invoked.
     */
    public void methodInvoked(MethodDef method) {
        // Create an asynchronous callback to handle the result.
        service.invokeMethod(daemon,method,new AsyncCallback() {
            public void onSuccess(Object result) {
                resultPanel.setResult((String)result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Failed to invoke the method : " +
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
                    "The Daemon Admin Panel enables users to interact directly " +
                    "<br>with the deployed Daemons. This means it is possible to" +
                    "<br>manage the daemons without deploying a custom frontend." +
                    "<br>" +
                    "<br>To interact with a deployed daemon follow these steps." +
                    "<br>" +
                    "<br><b>Step 1:</b>" +
                    "<br>Select the daemon to interact with by selecting the " +
                    "<br>name from the objects panel." +
                    "<br><img src='images/Objects.gif'/>" +
                    "<br>" +
                    "<br><b>Step 2:</b>" +
                    "<br>Select the method to invoke by selecting the method" +
                    "<br>name from the methods option panel." +
                    "<br><img src='images/Methods.gif'/>" +
                    "<br>" +
                    "<br><b>Step 3:</b>" +
                    "<br>Enter the parameter information need to invoke the call" +
                    "<br>and invoke the method by clicking on the invoke button." +
                    "<br><i>Note:</i> Array values can be passed as comma " +
                    "<br>delimated strings." +
                    "<br><img src='images/Method.gif'/>" +
                    "<br>" +
                    "<br><b>Step 4:</b>" +
                    "<br>View the result in the result panel. If an error occurs" +
                    "<br>a full stack trace will be printed." +
                    "<br><img src='images/Result.gif'/>");
            //HelpDialog.getInstance().show();
        }
    }
}
