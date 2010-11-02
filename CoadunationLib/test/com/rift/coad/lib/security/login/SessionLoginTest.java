/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * SessionLoginTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.security.login;

import junit.framework.*;

// java imports
import org.apache.log4j.BasicConfigurator;

// coadunation imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.security.sudo.Sudo;
import com.rift.coad.lib.security.sudo.SudoCallbackHandler;

/**
 * The class that tests the session login
 *
 * @author Brett Chaldecott
 */
public class SessionLoginTest extends TestCase implements SudoCallbackHandler {
    
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
    
    public SessionLoginTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SessionLoginTest.class);
        
        return suite;
    }

    /**
     * Test of login method, of class com.rift.coad.lib.security.login.SessionLogin.
     */
    public void testLogin() throws Exception {
        System.out.println("login");
        
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        ThreadsPermissionContainerAccessor.init(permissions);
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserStoreManagerAccessor.init(userStoreManager);
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        UserSessionManagerAccessor.init(sessionManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        SessionLogin instance = new SessionLogin(
                new PasswordInfoHandler("test1","112233"));
        
        instance.login();
        
        // retrieve the user object
        UserSession user = instance.getUser();
        if (!user.getName().equals("test1")) {
            fail("Login failed");
        }
        
        // run as a user
        called = false;
        SudoBySessionIdTest sudoBySessionIdTest = new
                SudoBySessionIdTest (user.getSessionId(), this);
        threadGroup.addThread(sudoBySessionIdTest,"test1");
        sudoBySessionIdTest.start();
        sudoBySessionIdTest.join();
        if (!called) {
            fail("Failed to call");
        }
    }
    
    
    /**
     * This method is called to process
     */
    public void process() {
        called = true;
    }
}
