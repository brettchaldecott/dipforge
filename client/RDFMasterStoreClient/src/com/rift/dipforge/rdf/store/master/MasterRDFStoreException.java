/*
 * MasterRDFStoreClient: The master RDF store interface
 * Copyright (C) 2011  2015 Burntjam
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
 * MasterRDFStoreException.java
 */
package com.rift.dipforge.rdf.store.master;

/**
 * The master rdf store.
 * 
 * @author brett chaldecott
 */
public class MasterRDFStoreException extends Exception {

    /**
     * Creates a new instance of
     * <code>MasterRDFStoreException</code> without detail message.
     */
    public MasterRDFStoreException() {
    }

    /**
     * Constructs an instance of
     * <code>MasterRDFStoreException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MasterRDFStoreException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of
     * <code>MasterRDFStoreException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param cause The cause of the exception
     */
    public MasterRDFStoreException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
