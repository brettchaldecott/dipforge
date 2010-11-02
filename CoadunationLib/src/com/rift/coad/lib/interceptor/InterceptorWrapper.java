/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * RMIInterceptor.java
 *
 * This class wrapps the access to the rmi interceptor factory object.
 */

package com.rift.coad.lib.interceptor;

// java imports
import org.omg.CORBA.LocalObject;

/**
 * This class wrapps the access to the rmi interceptor factory object.
 *
 * @author Brett Chaldecott
 */
public class InterceptorWrapper extends LocalObject {
    
    /** Creates a new instance of RMIInterceptor */
    public InterceptorWrapper() {
    }
    
    
    /**
     * This method returns the reference to the client interceptor object.
     *
     * @return The reference to the client interceptor.
     * @exception InterceptorException
     */
    protected ClientInterceptor getClientInterceptor() throws 
            InterceptorException {
        return InterceptorFactory.getInstance().getClientInterceptor();
    }
    
    
    /**
     * This method returns the reference to the server interceptor object.
     *
     * @return The reference to the server interceptor.
     * @exception InterceptorException
     */
    protected ServerInterceptor getServerInterceptor() throws 
            InterceptorException {
        return InterceptorFactory.getInstance().getServerInterceptor();
    }
}
