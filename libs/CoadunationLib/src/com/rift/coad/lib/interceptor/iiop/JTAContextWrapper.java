/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2008  2015 Burntjam
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * JTAContextWrapper.java
 */

// package path
package com.rift.coad.lib.interceptor.iiop;

// jotm imports
import org.objectweb.jotm.TransactionContext;

/**
 * This is a wrapper for the JTA context.
 *
 * @author brett chaldecott
 */
public class JTAContextWrapper implements java.io.Serializable {
    
    // private member variables
    private String instanceId = null;
    private TransactionContext txCtx = null;

    
    /** 
     * Creates a new instance of JTAContextWrapper 
     */
    public JTAContextWrapper(String instanceId, TransactionContext txCtx) {
        this.instanceId = instanceId;
        this.txCtx = txCtx;
    }
    
    
    /**
     * This method returns the instance id.
     */
    public String getInstancedId() {
        return instanceId;
    }
    
    
    /**
     * This method returns transaction context.
     */
    public TransactionContext getTxCtx() {
        return txCtx;
    }
}
