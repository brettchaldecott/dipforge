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
 * InterceptorPermissionStack.java
 *
 * This class is responsible for keeping track of the thread permissions assigned
 * to a thread and pushing and popping them to deal with threads being granted
 * new permissions or releasing old permissions.
 */

// package path
package com.rift.coad.lib.interceptor;

// java imports
import java.util.Stack;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;


/**
 * This class is responsible for keeping track of the thread permissions assigned
 * to a thread and pushing and popping them to deal with threads being granted
 * new permissions or releasing old permissions.
 *
 * @author Brett Chaldecott
 */
public class InterceptorPermissionStack {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(InterceptorPermissionStack.class.getName());
    
    // private member variables
    private ThreadsPermissionContainer permissionContainer = null;
    private ThreadLocal threadLocal = new ThreadLocal();
    
    
    /**
     * Creates a new instance of InterceptorPermissionStack
     *
     * @param permissionContainer The reference to the permission container
     */
    public InterceptorPermissionStack(
            ThreadsPermissionContainer permissionContainer) {
        this.permissionContainer = permissionContainer;
    }
    
    
    /**
     * This method pushes the current user session onto a stack so it can
     * be popped back on when processing is completed.
     *
     * @param userSession The user session to apply to the current thread 
     *          session.
     * @exception InterceptorException
     */
    public void push(UserSession userSession) throws InterceptorException {
        try {
            Stack stack = (Stack)threadLocal.get();
            if (stack == null) {
                stack = new Stack();
                threadLocal.set(stack);
            }
            Thread currentThread = Thread.currentThread();
            stack.push(
                    permissionContainer.getSession(currentThread.getId()));
            permissionContainer.putSession(currentThread.getId(),
                    new ThreadPermissionSession(
                    new Long(Thread.currentThread().getId()),userSession));
        } catch (Exception ex) {
            log.error("Failed to push an entry on the interceptor permission " +
                    "stack : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method pops the user permissions of the stack and applies them to
     * the current thread. Thus reveting them to the permissions that existed
     * before the call on the push.
     *
     * @exception InterceptorException
     */
    public void pop() throws InterceptorException {
        try {
            Stack stack = (Stack)threadLocal.get();
            if (stack == null) {
                throw new InterceptorException(
                        "The interceptors are not getting used correctly there" +
                        "is not session for this thread.");
            }
            Thread currentThread = Thread.currentThread();
            ThreadPermissionSession permission = 
                    (ThreadPermissionSession)stack.pop();
            if (permission != null) {
                permissionContainer.putSession(currentThread.getId(),
                        permission);
            } else {
                permissionContainer.removeSession(currentThread.getId());
            }
        } catch (InterceptorException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to pop an entry off the interceptor permission " +
                    "stack : " + ex.getMessage(),ex);
        }
    }
}
