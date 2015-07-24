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
 * ScopeCache.java
 */

// package path
package com.rift.coad.office.client.documents;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import java.util.List;

/**
 * The class responsible for caching the name space information.
 *
 * @author brett chaldecott
 */
public class ScopeCache implements AsyncCallback {

    // class singleton methods
    private static ScopeCache singleton = null;

    // class private member variables
    private String documentBase;
    private List<String> scopes;

    /**
     * The private constructor of the scopes
     */
    private ScopeCache() {
        DocumentManagerConnector.getService().getHttpBaseUri(this);
        DocumentManagerConnector.getService().listScopes(this);
    }

    /**
     * This method returns an instance of the singleton.
     *
     * @return The reference to the singleton.
     */
    public static synchronized ScopeCache getInstance() {
        if (singleton == null) {
            singleton = new ScopeCache();
        }
        return singleton;
    }

    
    /**
     * This method is called to deal with failures to retrieve name space information.
     * 
     * @param caught The cause of the error.
     */
    public void onFailure(Throwable caught) {
        SC.say("Action Error", "Failed to retrieve scope information " +
                caught.getMessage());
    }


    /**
     * This method is called to deal with a successfull result.
     *
     * @param result The successfull result.
     */
    public void onSuccess(Object result) {
        if (result instanceof String) {
            this.documentBase = (String)result;
        } else {
            this.scopes = (List<String>)result;
        }
    }

    /**
     * The document base.
     *
     * @return
     */
    public String getDocumentBase() {
        return documentBase;
    }



    /**
     * This method returns the scopes of this object.
     *
     * @return The name space for this object.
     */
    public List<String> getScopes() {
        return scopes;
    }


    /**
     * This scope is already present ignoring it.
     *
     * @param scope The scope to add.
     * @return TRUE if the scope exists, FALSE if not.
     */
    public boolean addScope(String scope) {
        if (!scopes.contains(scope)) {
            scopes.add(scope);
            return true;
        }
        return false;
    }

}
