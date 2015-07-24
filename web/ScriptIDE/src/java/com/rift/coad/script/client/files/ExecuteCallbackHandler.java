/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  2015 Burntjam
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
 * ExecuteCallbackHandler.java
 */


// the package path
package com.rift.coad.script.client.files;

//
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;

/**
 * This object represents the call back handler.
 *
 * @author brett chaldecott
 */
public class ExecuteCallbackHandler implements AsyncCallback {

    private FileEditorFactory factory;


    /**
     * The execute calback handler
     *
     * @param factory The factory object reference.
     */
    public ExecuteCallbackHandler(FileEditorFactory factory) {
        this.factory = factory;
    }


    /**
     * This method is called to deal with a failure call.
     *
     * @param caught The exception that was caught.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to execute the script : " + caught.getMessage());
    }


    /**
     * This method deals with the successfull results.
     *
     * @param result The results of the call.
     */
    public void onSuccess(Object result) {
        factory.getPanel().setMessageCanvas(result.toString());
    }

}
