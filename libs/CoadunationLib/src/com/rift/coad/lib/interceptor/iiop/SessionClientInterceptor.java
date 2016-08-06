/*
 * CoadunationLib: The coaduntion implementation library.
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
 * SessionClientInterceptor.java
 *
 * This interceptor is responsible for setting up the client side of the
 * connection so that a session can be setup on the server side appropriatly.
 */

// package path
package com.rift.coad.lib.interceptor.iiop;

// java imports
import java.util.MissingResourceException;
import org.omg.CORBA.TIMEOUT;
import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ORBInitInfo;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.interceptor.ClientInterceptor;


/**
 * This interceptor is responsible for setting up the client side of the
 * connection so that a session can be setup on the server side appropriatly.
 *
 * @author Brett Chaldecott
 */
public class SessionClientInterceptor extends InterceptorWrapper implements
        ClientRequestInterceptor {
    
    // the class log variable
    protected static Logger log =
            Logger.getLogger(SessionClientInterceptor.class.getName());
    /**
     * Creates a new instance of SessionClientInterceptor
     */
    public SessionClientInterceptor(ORBInitInfo info) {
    }
    
    
    /**
     * This method returns the name of this interceptor.
     *
     * @return A string containing the name of this interceptor.
     */
    public String name() {
        return "SessionClientInterceptor";
    }
    
    
    /**
     * This method is called to distory this object.
     */
    public void destroy() {
        // do nothing for time being
    }
    
    
    /**
     * Indicates to the interceptor that an exception occurred.
     */
    public void receive_exception(ClientRequestInfo ri) throws ForwardRequest {
        
    }
    
    
    /**
     * Allows an Interceptor to query the information available when a request
     * results in something other than a normal reply or an exception.
     */
    public void receive_other(ClientRequestInfo ri) throws ForwardRequest {
        
    }
    
    
    /**
     * Allows an Interceptor to query the information on a reply after it is 
     * returned from the server and before control is returned to the client.
     *
     * @param ri The client request information.
     */
    public void receive_reply(ClientRequestInfo ri) {
        
    }
    
    
    /**
     * Allows an Interceptor to query information during a Time-Independent 
     * Invocation(TII) polling get reply sequence.
     */
    public void send_poll(ClientRequestInfo ri) throws TIMEOUT {
        
    }
    
    
    /**
     * Allows an Interceptor to query request information and modify the service
     * context before the request is sent to the server.
     */
    public void send_request(ClientRequestInfo ri) throws ForwardRequest {
        try {
            log.debug("Send a request on the client side");
            ClientInterceptor clientInterceptor = this.getClientInterceptor();
            Credential credential = clientInterceptor.getSessionCredential();
            ServiceContext securityContext = new ServiceContext(
                    Constants.STANDARD_SECURITY_CONTEXT_ID,ObjectSerializer.
                    serialize(credential));
            ri.add_request_service_context(securityContext,true);
        } catch (Exception ex) {
            log.debug(
                    "Failed to setup the context for the call to the server : " + 
                    ex.getMessage());
        }
    }
    
}
