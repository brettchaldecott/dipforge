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
 * JenaStoreTypes.java
 */


// the package path
package com.rift.coad.rdf.semantic.persistance.jena;

/**
 * This is the constants for the store types provided by jena.
 *
 * @author brett chaldecott
 */
public class JenaStoreTypes {

    /**
     * The property that must be supplied to jena.
     */
    public final static String JENA_STORE_TYPE = "jena_store_type";

    /**
     * The SDB or standard database store. The default persisted data store used
     * by dipforge.
     */
    public final static String SDB = "sdb";

    
    /**
     * The java binary store of RDF information. The best scaling option with
     * JENA but it requires a 64 bit JVM and a lot of extra configuration.
     */
    public final static String TDB = "tdb";


    /**
     * This store uses XML as the backend data store.
     */
    public final static String XML = "xml";


    /**
     * This store uses http as the backend data store.
     */
    public final static String HTTP = "http";

}
