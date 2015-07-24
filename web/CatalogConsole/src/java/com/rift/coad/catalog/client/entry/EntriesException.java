/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * EntriesStore.java
 */

package com.rift.coad.catalog.client.entry;

/**
 * This exception is thrown when there 
 *
 * @author brett chaldecott
 */
public class EntriesException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>EntriesException</code> without detail message.
     */
    public EntriesException() {
    }


    /**
     * Constructs an instance of <code>EntriesException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EntriesException(String msg) {
        super(msg);
    }
}
