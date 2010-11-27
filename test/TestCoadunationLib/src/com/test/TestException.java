/*
 * TestException.java
 *
 * Created on December 6, 2006, 6:24 PM
 *
 * Copyright December 6, 2006 Rift Marketing CC
 *
 * <add comments here>
 */

package com.test;

/**
 *
 * @author mincemeat
 */
public class TestException extends java.lang.Exception implements 
        java.io.Serializable {
    
    /**
     * Creates a new instance of <code>TestException</code> without detail message.
     */
    public TestException() {
    }
    
    
    /**
     * Constructs an instance of <code>TestException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TestException(String msg) {
        super(msg);
    }
}
