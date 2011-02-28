/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * BasicJDOSession.java
 */


package com.rift.coad.rdf.semantic.jdo.basic;

// package
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import net.sf.cglib.proxy.Proxy;

/**
 * This object is responsible for creating a new proxy for the class.
 *
 * @author brett chaldecott
 */
public class BasicJDOProxyFactory {

    public static <T> T createJDOProxy(Class<T> type, PersistanceResource resource)
            throws BasicJDOException {
        try {
            T result = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[] {type,Resource.class}, null);

            return result;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to create the JDO proxy : " +
                    ex.getMessage(),ex);
        }
    }
}
