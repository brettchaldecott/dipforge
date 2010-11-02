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
 * javaURLContextFactory.java
 *
 * This object is responsible for instanciating URL context objects on demand.
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;


/**
 * This object is responsible for instanciating URL context objects on demand.
 *
 * @author Brett Chaldecott
 */
public class javaURLContextFactory implements ObjectFactory {
    
    /** Creates a new instance of javaURLContextFactory */
    public javaURLContextFactory() {
    }
    
    
    /**
     * This method returns a new instance of the  URL Context object.
     *
     * @return The newly created object.
     * @param obj An object reference.
     * @param name The name of the sub context.
     * @param nameCtx The sub context.
     * @param environment The environment.
     * @exception Exception
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, 
            Hashtable environment) throws Exception {
        return new URLContext(name,environment);
    }
}
