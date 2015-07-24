/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * JNDICallbackHandler.java
 */

// package path
package com.rift.coad.change.client.action.workflow.piece.call;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.change.client.action.workflow.piece.PieceCall;
import com.smartgwt.client.util.SC;


/**
 * This object is the implementation of the jndi call.
 *
 * @author brett chaldecott
 */
public class JNDICallbackHandler implements AsyncCallback {
    
    
    private PieceCall pieceCall;
    private String jndi;

    
    /**
     * This constructor sets the piece call value.
     * 
     * @param pieceCall The piece call.
     */
    public JNDICallbackHandler(PieceCall pieceCall) {
        this.pieceCall = pieceCall;
    }
    

    
    /**
     * This constructor sets the piece call value.
     * 
     * @param pieceCall The piece call.
     */
    public JNDICallbackHandler(PieceCall pieceCall, String jndi) {
        this.pieceCall = pieceCall;
        this.jndi = jndi;
    }

    
    
    /**
     * This method handles the failure.
     *
     * @param caught The exception that was caught.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to retrieve the jndi reference : " + caught.getMessage());
    }


    /**
     * This method deals with the successful call.
     *
     * @param result The result of the call.
     */
    public void onSuccess(Object result) {
        this.pieceCall.getJndiReference().setValueMap((String[])result);
        if (this.jndi != null) {
            this.pieceCall.getJndiReference().setValue(this.jndi);
        }
    }

}
