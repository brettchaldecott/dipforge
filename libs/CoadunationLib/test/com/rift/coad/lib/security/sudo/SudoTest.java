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
 * SudoTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.security.sudo;

// java imports
import java.util.Set;
import java.util.HashSet;

// junit imports
import junit.framework.*;

// log 4j imports
import org.apache.log4j.Logger;

// imports
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.login.SessionLogin;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * Sudo test
 *
 * @author Brett Chaldecott
 */
public class SudoTest extends TestCase implements SudoCallbackHandler {
    
    /**
     * This object will sudo by user
     */
    public class SudoByUserTest extends BasicThread {
        
        private SudoCallbackHandler handler = null;
        
        /**
         * The constructor of the handler
         */
        public SudoByUserTest (SudoCallbackHandler handler) throws Exception {
            this.handler = handler;
        }
        
        
        /**
         * This method will process the result
         */
        public void process() {
            try {
                Sudo.sudoThreadByUser("test", handler);
            } catch (Exception ex) {
                System.out.println("Failed to process : " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }
    }
    
    
    /**
     * This object will sudo by user
     */
    public class SudoBySessionIdTest extends BasicThread {
        
        private String sessionId = null;
        private SudoCallbackHandler handler = null;
        
        /**
         * The constructor of the handler
         */
        public SudoBySessionIdTest (String sessionId, 
                SudoCallbackHandler handler) throws Exception {
            this.sessionId = sessionId;
            this.handler = handler;
        }
        
        
        /**
         * This method will process the result
         */
        public void process() {
            try {
                Sudo.sudoThreadBySessionId(sessionId, handler);
            } catch (Exception ex) {
                System.out.println("Failed to process : " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }
    }
    
    
    // private member variables
    private boolean called = false;
    
    public SudoTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SudoTest.class);
        
        return suite;
    }

    /**
     * Test of testSudo method, of class com.rift.coad.lib.security.sudo.Sudo.
     */
    public void testSudo() throws Exception {
        System.out.println("testSudo");
        
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        ThreadsPermissionContainerAccessor.init(permissions);
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserStoreManagerAccessor.init(userStoreManager);
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        UserSessionManagerAccessor.init(sessionManager);
        LoginManager.init(sessionManager,userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // sudo the user
        called = false;
        SudoByUserTest sudoByUserTest = new SudoByUserTest(this);
        threadGroup.addThread(sudoByUserTest,"test1");
        sudoByUserTest.start();
        sudoByUserTest.join();
        if (called == false) {
            fail("Failed to call as user test1");
        }
        
        // log the user in
        SessionLogin sessionLogin = new SessionLogin(
                new PasswordInfoHandler("test","112233"));
        sessionLogin.login();
        
        called = false;
        SudoBySessionIdTest sudoBySessionIdTest = new SudoBySessionIdTest(
                sessionLogin.getUser().getSessionId(),this);
        threadGroup.addThread(sudoBySessionIdTest,"test1");
        sudoBySessionIdTest.start();
        sudoBySessionIdTest.join();
        if (called == false) {
            fail("Failed to sudo the user to the session id");
        }
        
        
        called = false;
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        set.add("test1");
        set.add("test2");
        set.add("test3");
        UserSession user = new UserSession("test", set);
        ThreadPermissionSession currentSession = new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                currentSession);
        
        Sudo.sudoThreadByUser("test2", this);
        
        if (called == false) {
            fail("Failed to sudo the user to the session id");
        }
        
        if (currentSession != permissions.getSession(
                new Long(Thread.currentThread().getId()))) {
            fail("Failed permission were not reset properly");
        }
        
        called = false;
        Sudo.sudoThreadBySessionId(sessionLogin.getUser().getSessionId(),this);
        
        
        if (called == false) {
            fail("Failed to sudo the user to the session id");
        }
        
        if (currentSession != permissions.getSession(
                new Long(Thread.currentThread().getId()))) {
            fail("Failed permission were not reset properly");
        }
        
    }
    
    
    /**
     * The method that will run the thread as a user.
     */
    public void process() {
        called = true;
    }
}
