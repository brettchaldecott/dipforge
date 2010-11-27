/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.desktop.client.desk;

// gwt imports
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This exception gets thrown when there is a problem with the desktop rpc request.
 * 
 * @author brett chaldecott
 */
public class DesktopRPCException extends Exception implements IsSerializable {

    /**
     * Creates a new instance of <code>DesktopRPCException</code> without detail message.
     */
    public DesktopRPCException() {
    }


    /**
     * Constructs an instance of <code>DesktopRPCException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DesktopRPCException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>DesktopRPCException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DesktopRPCException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
