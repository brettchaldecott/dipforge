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
 * WebServiceInvoker.java
 *
 * The web service invoker inherits from the RPCProvider and is responsible for
 * making the final call on the web service. Doing this makes it responsible for
 * setting the thread context loader appropriatly so that the RMI stubs can be
 * loaded from the appropriat jars.
 */

// The path to the package
package com.rift.coad.lib.webservice;

// java imports
import java.lang.reflect.Method;

// axis imports
import org.apache.axis.MessageContext;
import org.apache.axis.providers.java.RPCProvider;

// coadunation imports
import com.rift.coad.lib.audit.AuditTrail;

/**
 * The web service invoker inherits from the RPCProvider and is responsible for
 * making the final call on the web service. Doing this makes it responsible for
 * setting the thread context loader appropriatly so that the RMI stubs can be
 * loaded from the appropriat jars.
 *
 * @author Brett Chaldecott
 */
public class WebServiceInvoker extends RPCProvider {
    
    // the reference to the class loader
    private ClassLoader classLoader = null;
    
    /** 
     * Creates a new instance of WebServiceInvoker 
     */
    public WebServiceInvoker(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    
    /**
     * This method encapsulates the method invocation.
     *
     * @return The object resulting from the call.
     * @param msgContext The message context for this call.
     * @param method The reference to the method to invoke.
     * @param obj The target object to invoke the method on.
     * @param argValues The method parameters.
     * @exception Exception
     */
    protected Object invokeMethod(MessageContext msgContext, Method method,
            Object obj, Object[] argValues) throws Exception {
        ClassLoader originalClassLoader = Thread.currentThread().
                getContextClassLoader();
        AuditTrail auditTrail = AuditTrail.getAudit(obj.getClass());
        try {
            Thread.currentThread().setContextClassLoader(this.classLoader);
            Object result = method.invoke(obj,argValues);
            auditTrail.logEvent(method.getName());
            return result;
        } catch (java.lang.reflect.InvocationTargetException ex) {
            auditTrail.logEvent(method.getName(),ex.getTargetException());
            throw (Exception)ex.getTargetException();
        } catch (Exception ex) {
            auditTrail.logEvent(method.getName(),ex);
            throw ex;
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }
    
}
