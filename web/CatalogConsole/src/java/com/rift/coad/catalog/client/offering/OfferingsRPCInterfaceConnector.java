/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * EntriesStore.java
 */

package com.rift.coad.catalog.client.offering;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * This object manages the connection to the offerings rpc interface.
 *
 *
 * @author brett chaldecott
 */
public class OfferingsRPCInterfaceConnector {
    /**
     * This method returns a reference to the offerings RPC interface async.
     *
     * @return The asycn version of the offerings rpc interface.
     */
    public static OfferingsRPCInterfaceAsync getService(){
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        OfferingsRPCInterfaceAsync service = (OfferingsRPCInterfaceAsync) GWT.create(OfferingsRPCInterface.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "offering/offeringsrpcinterface";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
