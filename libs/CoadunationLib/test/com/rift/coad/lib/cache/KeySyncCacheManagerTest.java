/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * KeySyncCacheManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.cache;

import junit.framework.*;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 *
 * @author mincemeat
 */
public class KeySyncCacheManagerTest extends TestCase {
    
    public KeySyncCacheManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(KeySyncCacheManagerTest.class);
        
        return suite;
    }
    
    
    /**
     * Test of KeySyncCacheManager of class com.rift.coad.lib.cache.KeySyncCacheManager.
     */
    public void testKeySyncCacheManager() throws Exception {
        System.out.println("KeySyncCacheManager");
        
        Object identifier = null;
        KeySyncCacheManager instance = new KeySyncCacheManager();
        
        KeySyncCache keySyncCache1 = instance.getKeySyncCache("key1");
        KeySyncCache keySyncCache2 = instance.getKeySyncCache("key2");
        assertEquals(keySyncCache1, instance.getKeySyncCache("key1"));
        assertEquals(keySyncCache2, instance.getKeySyncCache("key2"));
        
        KeySyncCache.KeySync fred = keySyncCache1.getKeySync("fred");
        KeySyncCache.KeySync bob = keySyncCache1.getKeySync("bob");
        KeySyncCache.KeySync mary = keySyncCache2.getKeySync("mary");
        KeySyncCache.KeySync jill = keySyncCache2.getKeySync("jill");
        assertEquals(fred, keySyncCache1.getKeySync("fred"));
        assertEquals(bob, keySyncCache1.getKeySync("bob"));
        assertEquals(mary, keySyncCache2.getKeySync("mary"));
        assertEquals(jill, keySyncCache2.getKeySync("jill"));
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            fred.touch();
            mary = keySyncCache2.getKeySync("mary");
        }
        
        instance.garbageCollect();
        
        assertEquals(fred, keySyncCache1.getKeySync("fred"));
        assertEquals(mary, keySyncCache2.getKeySync("mary"));
        if (bob == keySyncCache1.getKeySync("bob")) {
            fail("The entry should not have been found");
        }
        if (jill == keySyncCache2.getKeySync("jill")) {
            fail("The entry should not have been found");
        }
        
        instance.clear();
        
        if (keySyncCache1.contains("fred")) {
            fail("The instance still contains fred");
        }
        if (keySyncCache2.contains("mary")) {
            fail("The instance still contains mary");
        }
        
        try {
            instance.getKeySyncCache("key1");
            fail("Should not have been able to retrieve a sync instance");
        } catch (CacheException ex) {
            // ignore
        }
    }

}
