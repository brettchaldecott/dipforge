/*
 * CoadunationClient: The client libraries for Coadunation. (RMI/CORBA)
 * Copyright (C) 2006  2015 Burntjam
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
 * ClientInterceptorIntializer.java
 *
 * This object is responsible for initializing the Client interceptors.
 *
 * Revision: $ID
 */

package com.rift.coad.client.interceptor.iiop;

// imports
import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ServerRequestInterceptor;


/**
 * This object is responsible for initializing the Client interceptors.
 *
 * @author Brett Chaldecott
 */
public class ClientInterceptorIntializer extends LocalObject implements
        ORBInitializer {
    
    /** 
     * Creates a new instance of ClientInterceptorIntializer 
     */
    public ClientInterceptorIntializer() {
    }
    
    
    /**
     * Called during ORB initialization.
     *
     * @param info The object to add the interceptors to
     */
    public void pre_init(ORBInitInfo info) {
        
    }
    
    
    /**
     * Called during ORB initialization.
     *
     * @param info The object to add the interceptors to
     */
    public void post_init(ORBInitInfo info) {
        try {
            info.add_client_request_interceptor(
                    (ClientRequestInterceptor)new ClientPasswordInterceptor());
        } catch (Exception ex) {
            System.out.println("Failed to initialize the interceptor");
            ex.printStackTrace(System.out);
        }
    }
}
