/*
 * CoadunationLib: The coaduntion library.
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
 * TransactionManager.java
 */

package com.rift.coad.util.transaction;

import junit.framework.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;


// coadunation imports
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.db.DBSourceManager;

import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.transaction.TransactionDirector;

/**
 * This object tests the coadunation hash map.
 *
 * @author brett chaldecott
 */
public class CoadunationHashMapTest extends TestCase {
    
    public CoadunationHashMapTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of size method, of class com.rift.coad.util.transaction.CoadunationHashMap.
     */
    public void testCoadunationHashMap() throws Exception {
        // init the session information
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
                userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        InterceptorFactory.init(permissions,sessionManager,userStoreManager);
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("test1", set);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        try {
            TransactionManager.getInstance();
            fail("Could retrieve a tranaction manager reference before init");
        } catch (TransactionException ex) {
            System.out.println(ex.getMessage());
        }
        
        // init the transaction manager
        ObjectLockFactory.init();
        TransactionManager.init();
        
        UserTransactionWrapper instance = new UserTransactionWrapper();
        
        // Instanciate the map
        Map map = new CoadunationHashMap();
        try {
            map.isEmpty();
            fail("This should not be allowed as there is not transaction.");
        } catch (Exception ex) {
            // pass
        }
        
        // start a transaction
        instance.begin();
        
        assertEquals(true, map.isEmpty());
        assertEquals(false, map.containsKey("test1"));
        assertEquals(false, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test3"));
        assertEquals(0, map.keySet().size());
        
        map.put("test1","testvalue1");
        map.put("test2","testvalue2");
        map.put("test3","testvalue3");
        map.put("test3","testvalue3.3");
        map.put("test4","testvalue4");
        
        assertEquals(true, map.isEmpty());
        assertEquals(false, map.containsKey("test1"));
        assertEquals(false, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test"));
        assertEquals(0, map.keySet().size());
        
        
        // commit
        instance.commit();
        instance.release();
        
        
        // start a transaction
        instance.begin();
        
        assertEquals(false, map.isEmpty());
        assertEquals(true, map.containsKey("test1"));
        assertEquals(true, map.containsValue("testvalue1"));
        assertEquals("testvalue3.3", map.get("test3"));
        assertEquals(4, map.keySet().size());
        
        map.remove("test3");
        map.remove("test2");
        
        assertEquals("testvalue3.3", map.get("test3"));
        
        // commit
        instance.commit();
        instance.release();
        
        // start a transaction
        instance.begin();
        
        assertEquals(false, map.isEmpty());
        assertEquals(true, map.containsKey("test1"));
        assertEquals(true, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test3"));
        assertEquals(2, map.keySet().size());
        
        map.remove("test1");
        map.remove("test4");
        
        assertEquals("testvalue4", map.get("test4"));
        
        // rollback
        instance.release();
        
        // start a transaction
        instance.begin();
        
        assertEquals(false, map.isEmpty());
        assertEquals(true, map.containsKey("test1"));
        assertEquals(true, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test3"));
        assertEquals(2, map.keySet().size());
        
        instance.release();
        
        // start transaction
        instance.begin();
        map.clear();
        instance.release();
        
        // start a transaction
        instance.begin();
        
        assertEquals(false, map.isEmpty());
        assertEquals(true, map.containsKey("test1"));
        assertEquals(true, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test3"));
        assertEquals(2, map.keySet().size());
        
        instance.release();
        
        // start transaction
        instance.begin();
        map.clear();
        instance.commit();
        instance.release();
        
        // start a transaction
        instance.begin();
        
        assertEquals(true, map.isEmpty());
        assertEquals(false, map.containsKey("test1"));
        assertEquals(false, map.containsValue("testvalue1"));
        assertEquals(null, map.get("test3"));
        assertEquals(0, map.keySet().size());
        
        instance.release();
        
    }

    
}
