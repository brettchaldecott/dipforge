/*
 * TreeQueryAsync.java
 *
 * Created on 29 June 2009, 9:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.audit.gwt.console.client.tree;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 *
 * @author brett
 */
public interface TreeQueryAsync {

    /**
     * The asynchronious version of this method.
     *
     * @param callback The reference to the call back object
     */
    public void getHosts(AsyncCallback callback);


    /**
     * This method returns the source list for the host name via the callback.
     *
     * @param hostname The name of the host.
     * @param callback The object to call back on.
     */
    public void getSources(String hostname, AsyncCallback callback);
}
