/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * EntriesStore.java
 */

// package path
package com.rift.coad.catalog.client.entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * This object is responsible for managing the connection to the  entries rpc
 * interface.
 *
 * @author brett chaldecott
 */
public class EntriesRPCInterfaceConnector {
    /**
     * This method returns a reference to the Entries RPC interface.
     *
     * @return The reference to the rpc interface.
     */
    public static EntriesRPCInterfaceAsync getService(){
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        EntriesRPCInterfaceAsync service = (EntriesRPCInterfaceAsync) GWT.create(EntriesRPCInterface.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "entry/entriesrpcinterface";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
