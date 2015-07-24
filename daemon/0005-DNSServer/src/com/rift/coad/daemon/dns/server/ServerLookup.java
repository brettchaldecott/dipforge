/*
 * DNSServer: The dns server implementation.
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
 * ServerLookupInterface.java
 */

// this is a package path
package com.rift.coad.daemon.dns.server;

// java imports
import java.io.FileWriter;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

// apache imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// the dns imports
import org.xbill.DNS.Name;
import org.xbill.DNS.RRset;
import org.xbill.DNS.SetResponse;
import org.xbill.DNS.Cache;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Record;

/**
 * This class contains the server lookup methods.
 *
 * @author brett chaldecott
 */
public class ServerLookup {
    
    // class constants
    private final static String RECURSIVE_LOOKUP = "recursive_lookup";
    private final static boolean DEFAULT_RECURSIVE_LOOKUP = true;
    private final static String NAMESERVER_LIST = "nameserver_list";
    
    // class logger
    private Logger log = Logger.getLogger(ServerLookup.class);
    
    // private member variables
    private Map caches = new HashMap();
    private boolean recurse = false;
    
    /**
     * The constructor of the server lookup object.
     */
    public ServerLookup() throws 
            ServerException {
        try {
            // retrieve the resolver information from the central configuration.
            Configuration centralConfig = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            if (centralConfig.getBoolean(RECURSIVE_LOOKUP,
                    DEFAULT_RECURSIVE_LOOKUP)) {
                recurse = true;
                Lookup.setDefaultResolver(new ExtendedResolver(
                        centralConfig.getString(NAMESERVER_LIST).split("[,]")));
            }
            // instanciate the internet cache object.
            getCache(DClass.IN);
            
        } catch (Exception ex) {
            log.error("Failed to instanciate the server lookup tool because : "
                    + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to instanciate the server lookup tool because : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method performs a lookup first using the available cache and then by
     * preforming a recursive lookup and updating the cache if not found.
     *
     * @return The found record set.
     * @param name The name of the zone to perform the search on.
     * @param type The type of record to perform the search on.
     * @param dclass The class record type.
     * @param glue Look for glue records.
     */
    public RRset[] findRecords(Name name, int type, int dclass, boolean glue) {
        // calling this method first ensures that the default cache for the
        // lookup tool gets set correctly for this type.
        log.debug("Get the cache setup");
        Cache cache = getCache(dclass);
        
        // perform the lookup, if the lookup is not in the cache the lookup
        // object will use the resolve information setup top to look for the
        // address and then update the default cache for this dclass.
        if (recurse) {
            log.debug("lookup [" + name + "][" + type + "][" + dclass + "]");
            Lookup lookup = new Lookup(name,type,dclass);
            Record[] record = lookup.run();
            for (int index = 0; (record != null) && 
                    (index < record.length); index++) {
                log.debug("Records : " + record[index].toString());
            }
        }
        
        // if there is a glue request
        log.debug("Return the results.");
        if (glue) {
            return cache.findAnyRecords(name, type);
        } else {
            return cache.findRecords(name, type);
        }
    }
    
    
    /**
     * Looks up Records in the Cache.  This follows CNAMEs and handles negatively
     * cached data.
     * @param name The name to look up
     * @param type The type to look up
     * @param minCred The minimum acceptable credibility
     * @return A SetResponse object
     * @see SetResponse
     * @see Credibility
     */
    public SetResponse lookupRecords(Name name, int type, int dclass,
            int minCred) {
        
        // calling this method first ensures that the default cache for the
        // lookup tool gets set correctly for this type.
        log.debug("Get the cache");
        Cache cache = getCache(dclass);
        
        // perform the lookup, if the lookup is not in the cache the lookup
        // object will use the resolve information setup top to look for the
        // address and then update the default cache for this dclass.
        if (recurse) {
            log.debug("lookup [" + name + "][" + type + "][" + dclass + "]");
            Lookup lookup = new Lookup(name,type,dclass);
            Record[] record = lookup.run();
            for (int index = 0; (record != null) && 
                    (index < record.length); index++) {
                log.debug("Records : " + record[index].toString());
            }
        }
        
        // return the response from the cache
        log.debug("Returns the response");
        return cache.lookupRecords(name,type,minCred);
    }
    
    
    /**
     * This method returns the appropriate cache server.
     */
    private Cache getCache(int dclass) {
        synchronized (caches) {
            Cache cache = (Cache)caches.get(new Integer(dclass));
            if (cache == null) {
                cache = new Cache(dclass);
                caches.put(new Integer(dclass),cache);
                Lookup.setDefaultCache(cache,dclass);
            }
            return cache;
        }
    }
    
}
