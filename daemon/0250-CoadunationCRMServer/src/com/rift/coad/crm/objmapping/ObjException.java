/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.crm.objmapping;

/**
 *
 * @author brett
 */
public class ObjException extends Exception {

    /**
     * Creates a new instance of <code>ObjException</code> without detail message.
     */
    public ObjException() {
    }


    /**
     * Constructs an instance of <code>ObjException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ObjException(String msg) {
        super(msg);
    }
    
}
