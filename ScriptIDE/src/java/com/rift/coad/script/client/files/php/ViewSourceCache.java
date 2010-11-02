/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  Rift IT Contracting
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
 * ViewSourceCache.java
 */

package com.rift.coad.script.client.files.php;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

/**
 * This object acts as the cache for the view source object.
 *
 * @author brett chaldecott
 */
public class ViewSourceCache implements AsyncCallback {

    // class singleton
    private static ViewSourceCache singleton = null;


    // private member variables
    private String path;


    /**
     * The default constructor
     */
    private ViewSourceCache() {
        ViewSourceManagerConnector.getService().getWebPath(this);
    }


    /**
     * This method returns a reference to the singleton for this object.
     *
     * @return The view source cache singleton.
     */
    public synchronized static ViewSourceCache getInstance() {
        if (singleton == null) {
            singleton = new ViewSourceCache();
        }
        return singleton;
    }

    /**
     * The on failure method.
     *
     * @param caught This method deals with the failure to retrieve the information.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to retrieve the web directory for the view information : "
                + caught.getMessage());
    }

    /**
     * This method handles the successful result.
     *
     * @param result The result object.
     */
    public void onSuccess(Object result) {
        path = (String)result;
    }

    /**
     * This method returns the path string.
     *
     * @return The string containing the path
     */
    public String getPath() {
        return path;
    }

    

}
