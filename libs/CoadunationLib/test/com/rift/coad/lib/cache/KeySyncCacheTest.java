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
 * KeySyncCacheTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.cache;

import junit.framework.*;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 *
 * @author mincemeat
 */
public class KeySyncCacheTest extends TestCase {
    
    public KeySyncCacheTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(KeySyncCacheTest.class);
        
        return suite;
    }

    /**
     * Test of getKeySync method, of class com.rift.coad.lib.cache.KeySyncCache.
     */
    public void testKeySyncCache() throws Exception {
        System.out.println("KeySyncCache");
        
        KeySyncCache instance = new KeySyncCache();
        
        KeySyncCache.KeySync fred = instance.getKeySync("fred");
        KeySyncCache.KeySync bob = instance.getKeySync("bob");
        KeySyncCache.KeySync mary = instance.getKeySync("mary");
        assertEquals(fred, instance.getKeySync("fred"));
        assertEquals(bob, instance.getKeySync("bob"));
        assertEquals(mary, instance.getKeySync("mary"));
        
        for (int count = 0; count < 4; count++) {
            Thread.sleep(500);
            fred.touch();
            mary = instance.getKeySync("mary");
        }
        
        instance.garbageCollect();
        
        assertEquals(fred, instance.getKeySync("fred"));
        assertEquals(mary, instance.getKeySync("mary"));
        if (bob == instance.getKeySync("bob")) {
            fail("The entry should not have been found");
        }
        
        instance.clear();
        
        if (instance.contains("fred")) {
            fail("The instance still contains fred");
        }
        if (instance.contains("mary")) {
            fail("The instance still contains mary");
        }
        
        try {
            instance.getKeySync("fred");
            fail("Should not have been able to retrieve a sync instance");
        } catch (CacheException ex) {
            // ignore
        }
    }

    
}
 