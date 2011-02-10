/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

/**
 *
 * @author brettc
 */
public class PersistanceException extends Exception {

    /**
     * Creates a new instance of <code>PersistanceException</code> without detail message.
     */
    public PersistanceException() {
    }


    /**
     * Constructs an instance of <code>PersistanceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PersistanceException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>PersistanceException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of the exception.
     */
    public PersistanceException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
