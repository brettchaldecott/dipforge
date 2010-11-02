/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * EntriesStore.java
 */

// package path
package com.rift.coad.catalog.client.offering;

/**
 * This exception is thrown when there is an error with the offering rpc interface
 *
 * @author brett chaldecott
 */
public class OfferingException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>OfferingException</code> without detail message.
     */
    public OfferingException() {
    }


    /**
     * Constructs an instance of <code>OfferingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public OfferingException(String msg) {
        super(msg);
    }
}
