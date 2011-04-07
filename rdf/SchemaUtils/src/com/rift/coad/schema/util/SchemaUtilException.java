/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.schema.util;

/**
 *
 * @author brettc
 */
public class SchemaUtilException extends Exception {

    /**
     * Creates a new instance of <code>SchemaUtilException</code> without detail message.
     */
    public SchemaUtilException() {
    }


    /**
     * Constructs an instance of <code>SchemaUtilException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SchemaUtilException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>SchemaUtilException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SchemaUtilException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
