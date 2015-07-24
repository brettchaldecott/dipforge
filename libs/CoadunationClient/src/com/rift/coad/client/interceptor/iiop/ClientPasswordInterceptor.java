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
 * ClientPasswordInterceptor.java
 *
 * This interceptor is used on the client side of an RMI connection to add the
 * login object to the session.
 *
 * Revision: $ID
 */

// package path
package com.rift.coad.client.interceptor.iiop;

// java imports
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.MissingResourceException;
import org.omg.CORBA.TIMEOUT;
import org.omg.CORBA.LocalObject;
import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ORBInitInfo;

// logging import
import org.apache.log4j.Logger;

// coadunation client imports
import com.rift.coad.lib.interceptor.credentials.Login;
import com.rift.coad.client.naming.CoadunationInitialContextFactory;

/**
 * This interceptor is used on the client side of an RMI connection to add the
 * login object to the session.
 *
 * @author Brett Chaldecott
 */
public class ClientPasswordInterceptor extends LocalObject implements 
        ClientRequestInterceptor {
    
    // the class log variable
    protected static Logger log =
            Logger.getLogger(ClientPasswordInterceptor.class.getName());
    
    /** 
     * Creates a new instance of ClientPasswordInterceptor
     */
    public ClientPasswordInterceptor() {
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
     * @exception ForwardRequest
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
            Login login = (Login)CoadunationInitialContextFactory.userLogin.get();
            if (login != null) {
                ServiceContext securityContext = new ServiceContext(
                    101,serialize((java.io.Serializable)login));
                ri.add_request_service_context(securityContext,true);
            }
        } catch (Exception ex) {
            log.error("Failed to set the service context : " + ex.getMessage(),
                    ex);
        }
    }
    
    /**
     * This method serializes the object passed to it.
     *
     * @return A byte array of the object.
     * @param ref The reference to the object to serialize.
     * @exception CommonException
     */
    public byte[] serialize(Object ref) throws Exception {
        try {
            if (!(ref instanceof java.io.Serializable)) {
                throw new Exception("This object is not serializable. " +
                        "Must implement from java.io.Serializable.");
            }
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteOutput);
            objOutput.writeObject(ref);
            objOutput.flush();
            objOutput.close();
            return byteOutput.toByteArray();
        } catch (Exception ex) {
            throw new Exception("Failed to serialize the object : " +
                    ex.getMessage(),ex);
        }
    }
}
