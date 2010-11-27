/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.desktop.client.desk;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * This object is responsible for making the connection to the Desktop RCP object.
 * 
 * @author brett chaldecott
 */
public class DesktopRPCHelper {
    /**
     * This method returns a reference to the desktop rpc.
     * 
     * @return a reference to the desktop async rpc object.
     */
    public static DesktopRPCAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        DesktopRPCAsync service = (DesktopRPCAsync) GWT.create(DesktopRPC.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "desk/desktoprpc";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
