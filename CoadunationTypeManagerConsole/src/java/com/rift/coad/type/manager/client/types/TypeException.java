/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.type.manager.client.types;

/**
 *
 * @author brett
 */
public class TypeException extends Exception {

    /**
     * Creates a new instance of <code>TypeException</code> without detail message.
     */
    public TypeException() {
    }


    /**
     * Constructs an instance of <code>TypeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TypeException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>TypeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TypeException(String msg, Throwable ex) {
        super(msg,ex);
    }

}
