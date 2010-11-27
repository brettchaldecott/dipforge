/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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


package com.rift.coad.change.client.action.workflow.piece.call;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.change.client.action.workflow.piece.PieceCall;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;
import com.smartgwt.client.util.SC;

/**
 *
 * @author brett
 */
public class MethodCallbackHandler implements AsyncCallback {

    private PieceCall pieceCall;
    private String method;


    /**
     * This constructor sets the call back value.
     *
     * @param pieceCall The call back value.
     */
    public MethodCallbackHandler(PieceCall pieceCall) {
        this.pieceCall = pieceCall;
    }


    /**
     * This constructor sets the method call back handler.
     *
     * @param pieceCall The call back information.
     * @param method The method to call.
     */
    public MethodCallbackHandler(PieceCall pieceCall, String method) {
        this.pieceCall = pieceCall;
        this.method = method;
    }


    /**
     * The on failure.
     *
     * @param caught The exception that was caught.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to retrieve the method information : " + caught.getMessage());
    }


    /**
     * The success result.
     *
     * @param result The result of the call.
     */
    public void onSuccess(Object result) {
        pieceCall.setMethods((DataMapperMethod[])result, method);
    }

}
