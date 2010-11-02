/*
 * CoadunationLib: The coadunation core library.
 * Copyright (C) 2007 Rift IT Contracting
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
 * Task.java
 */

package com.rift.coad.lib.thread.pool;

/**
 * The definition of thread pool task. This task is called by a thread in the
 * pool to perform a specific task and than exit. It must never loop indefinitly
 * but perform on task than exit.
 *
 * @author Brett Chaldecott
 */
public interface Task {
    
    /**
     * This method is called by a thread in the thread pool. It must perform a
     * single task and then exit. It must not run indefinitly like a thread.
     *
     * @param pool The reference to the pool object.
     * @exception Exception
     */
    public void process(ThreadPoolManager pool) throws Exception;
}
