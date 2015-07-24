/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * ResourceDataHandler.java
 */

package com.rift.coad.catalog.client.tree;

import com.rift.coad.audit.gwt.console.client.query.Factory;
import com.rift.coad.catalog.client.entry.EntriesFactory;
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.rift.coad.gwt.lib.client.console.NavigationDataHandler;
import com.rift.coad.gwt.lib.client.console.NavigationTreeNode;
import com.smartgwt.client.util.SC;

/**
 * This object is responsible for
 *
 * @author brett chaldecott
 */
public class ResourceDataHandler implements NavigationDataHandler {

    /**
     * The default constructor for the resource data handler.
     */
    public ResourceDataHandler() {
    }




    /**
     * This method sets the child node information.
     *
     * @param name The name of the node that is parent.
     * @param callback
     */
    public void getChildNodes(String name, NavigationDataCallback callback) {
        if (name.equals("root")) {
            callback.addChildren(new NavigationTreeNode[] {
                        new NavigationTreeNode("Entries", "entries", "root", "entries.png", new EntriesFactory(), true, "",false),
                        new NavigationTreeNode("Offerings", "Offerings", "root", "offerings.png", null, true, "",false),
                        new NavigationTreeNode("Pricing", "Pricing", "root", "pricing.png", null, true, "",false),
                        new NavigationTreeNode("Catalogs", "Catalogs", "root", "catalogs.png", null, true, "",true),
                        new NavigationTreeNode("Audit Trail", "audittrail", "root", "media-record.png",
                                new Factory("","com.rift.coad.catalog.CatalogManagerDaemonImpl"), true, "",false)});
        }
    }

}
