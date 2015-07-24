/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
 *
 * MainEntryPoint.java
 */

// package
package com.rift.coad.catalog.client;


// imports
import com.google.gwt.core.client.EntryPoint;
import com.rift.coad.catalog.client.entry.EntriesStore;
import com.rift.coad.catalog.client.tree.ResourceDataHandler;
import com.rift.coad.catalog.client.types.TypeCache;
import com.rift.coad.gwt.lib.client.console.Console;


/**
 * This is the main entry point for the application.
 *
 * @author brett chaldecott
 */
public class MainEntryPoint implements EntryPoint {
    
    /** Creates a new instance of MainEntryPoint */
    public MainEntryPoint() {
    }
    
    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        EntriesStore.init();
        TypeCache.init();
        Console console = new Console("Change Manager", new ResourceDataHandler());
    }
    
}
