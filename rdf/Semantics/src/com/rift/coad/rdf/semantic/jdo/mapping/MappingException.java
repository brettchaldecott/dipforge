/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.mapping;

/**
 *
 * @author brettc
 */
public class MappingException extends Exception {

    /**
     * Creates a new instance of <code>MappingException</code> without detail message.
     */
    public MappingException() {
    }


    /**
     * Constructs an instance of <code>MappingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MappingException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>MappingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MappingException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
