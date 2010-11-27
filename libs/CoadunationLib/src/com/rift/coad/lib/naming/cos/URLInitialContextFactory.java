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
 * URLInitialContextFactory.java
 *
 * The object responsible for constructing the initial context factory.
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


/**
 * The object responsible for constructing the initial context factory.
 *
 * @author Brett Chaldecott
 */
public class URLInitialContextFactory implements InitialContextFactory {
    
    /** 
     * The default constructor does nothing
     */
    public URLInitialContextFactory() {
    }
    
    
    /**
     * This method is responsible for instanciating the initial context using
     * the supplied environment.
     *
     * @return The context to return.
     * @param environment The environment to use to instanciate the url context.
     * @exception NamingException
     */
    public Context getInitialContext(Hashtable environment) throws 
            NamingException {
        return new URLContext(environment);
    }
}
