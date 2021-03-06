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
 * ClassLoaderInterceptor.java
 */

// package path
package com.rift.coad.lib.interceptor.iiop;

// java imports
import java.util.Stack;
import java.util.MissingResourceException;
import java.lang.ThreadLocal;
import org.omg.CORBA.TIMEOUT;
import org.omg.IOP.ServiceContext;
import org.omg.PortableInterceptor.ServerRequestInterceptor;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.omg.PortableInterceptor.ForwardRequest;
import org.omg.PortableInterceptor.ORBInitInfo;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.interceptor.ServerInterceptor;


/**
 * This class is responsible for setting the class loader correctly for the in
 * bound thread.
 *
 * @author Brett Chaldecott
 */
public class ClassLoaderInterceptor extends InterceptorWrapper implements
        ServerRequestInterceptor {
    
    // the class log variable
    protected static Logger log =
            Logger.getLogger(ClassLoaderInterceptor.class.getName());
    
    // private member variables
    private ThreadLocal loaderStack = new ThreadLocal();
    
    
    /** Creates a new instance of ClassLoaderInterceptor */
    public ClassLoaderInterceptor(ORBInitInfo info) {
        
    }
    
    
    /**
     * This method returns the name of this interceptor.
     *
     * @return A string containing the name of this interceptor.
     */
    public String name() {
        return "ClassLoaderInterceptor";
    }
    
    
    /**
     * This method is called to distory this object.
     */
    public void destroy() {
        // do nothing for time being
    }
    
    
    /**
     * Allows the interceptor to process service context information.
     *
     * @param ri The reference to the request information
     */
    public void receive_request_service_contexts(ServerRequestInfo ri) {
        
    }
    
    
    /**
     * Allows an Interceptor to query request information after all the
     * information, including operation parameters, are available.
     */
    public void receive_request(ServerRequestInfo ri) {
        log.debug("receive request");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            loader = NamingDirector.getInstance().getPOA().id_to_servant(
                    ri.object_id()).getClass().getClassLoader();
        } catch (Exception ex) {
            // ignore exception
        }
        Stack stack = getLoaderStack();
        stack.push(Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(loader);
    }
    
    
    /**
     * Allows an Interceptor to query the exception information and modify the
     * reply service context before the exception is thrown to the client.
     */
    public void send_exception(ServerRequestInfo ri) {
        log.debug("send exception");
        Stack stack = getLoaderStack();
        Thread.currentThread().setContextClassLoader((ClassLoader)stack.pop());
    }
    
    
    /**
     * Allows an Interceptor to query the information available when a request
     * results in something other than a normal reply or an exception.
     */
    public void send_other(ServerRequestInfo ri) {
        
    }
    
    
    /**
     * Allows an Interceptor to query reply information and modify the reply
     * service context after the target operation has been invoked and before
     * the reply is returned to the client.
     */
    public void send_reply(ServerRequestInfo ri) {
        log.debug("send reply");
        Stack stack = getLoaderStack();
        Thread.currentThread().setContextClassLoader((ClassLoader)stack.pop());
    }
    
    
    /**
     * This method returns the stack for the given thread.
     */
    private Stack getLoaderStack() {
        Stack stack = (Stack)loaderStack.get();
        if (stack == null) {
            stack = new Stack();
            loaderStack.set(stack);
        }
        return stack;
    }
}
