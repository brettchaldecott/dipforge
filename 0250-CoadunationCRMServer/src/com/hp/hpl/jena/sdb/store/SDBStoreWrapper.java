/*
 * 0047-CoadunationCRMServer: The CRM server.
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
 * SDBStoreWrapper.java
 */


package com.hp.hpl.jena.sdb.store;

// imports
import com.hp.hpl.jena.sdb.Store;

/**
 * This is a wrapper to manage any funny stuff with the store implementations.
 * 
 * @author brett chaldecott
 */
public class SDBStoreWrapper {
    public static void close (Store store) {
        if (store instanceof StoreBaseHSQL) {
            // this is a nasty fix to work around the store shutting down
            // the hsqldb database instance.
            System.out.println("###################################");
            StoreBaseHSQL storeBase = (StoreBaseHSQL)store;
            storeBase.currentlyOpen = false;
            storeBase.close();
            System.out.println("Closed the store base hsql");
            System.out.println("###################################");
        } else {
            store.close();
        }
    }
}
