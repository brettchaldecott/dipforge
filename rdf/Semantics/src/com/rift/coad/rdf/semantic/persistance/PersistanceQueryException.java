/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

/**
 *
 * @author brettc
 */
public class PersistanceQueryException extends Exception {

    /**
     * Creates a new instance of <code>PersistanceQueryException</code> without detail message.
     */
    public PersistanceQueryException() {
    }


    /**
     * Constructs an instance of <code>PersistanceQueryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PersistanceQueryException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>PersistanceQueryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PersistanceQueryException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
