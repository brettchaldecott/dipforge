/*
 * CoadunationLib: The coadunation libraries.
 * Copyright (C) 2007  Rift IT Contracting
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
 * ThreadPoolManager.java
 */

// package path
package com.rift.coad.lib.thread.pool;

// java imports
import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
        
// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This object is responsible for managing a pool of threads assigned to process
 * a task object.
 *
 * @author Brett Chaldecott
 */
public class ThreadPoolManager {
    
    /**
     * This class is responsible for processing the tasks.
     */
    public class PoolThread extends CoadunationThread {
        
        // The classes private member variables
        private ThreadStateMonitor state = new ThreadStateMonitor();
        private ThreadPoolManager threadPoolManager = null;
        private Class taskClass = null;
        
        /**
         * The constructor of pool thread.
         *
         * @param threadPool The reference to the thread pool.
         * @param taskClass The task object to process.
         * @exception Exception
         */
        public PoolThread(ThreadPoolManager threadPoolManager, Class taskClass) 
                throws Exception {
            this.threadPoolManager = threadPoolManager;
            this.taskClass = taskClass;
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            while(!state.isTerminated()) {
                if (!monitor()) {
                    break;
                }
                try {
                    Task task = (Task)taskClass.newInstance();
                    task.process(threadPoolManager);
                } catch (Exception ex) {
                    log.error("Failed to process a task : " + ex.getMessage(),
                            ex);
                }
                processing.decrementAndGet();
            }
            removeThread(this);
            log.debug("Pool thread exiting");
        }


        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public void terminate() {
            state.terminate(true);
        }
        
    }
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(ThreadPoolManager.class.getName());
    
    // privat member variables
    private AtomicInteger processing = new AtomicInteger(0);
    private int currentSize = 0;
    private int minSize = 0;
    private int maxSize = 0;
    private Class taskClass = null;
    private String username = null;
    private Vector threadList = new Vector();
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private int releaseThread = 1;
    
    /** 
     * Creates a new instance of ThreadPoolManager 
     *
     * @param size The size of this thread pool.
     * @param taskClass The class that implements the task interface.
     * @param username The name of the user that the threads will run as.
     * @exception PoolException
     */
    public ThreadPoolManager(int size, Class taskClass, String username) throws
            PoolException {
        validateTask(taskClass);
        this.minSize = size;
        this.maxSize = size;
        this.taskClass = taskClass;
        this.username = username;
        startThreads(minSize);
    }
    
    
    /** 
     * Creates a new instance of ThreadPoolManager 
     *
     * @param minSize The minimum size of this thread pool.
     * @param maxSize The maximum size of this thread pool.
     * @param taskClass The class that implements the task interface.
     * @param username The name of the user that the threads will run as.
     * @exception PoolException
     */
    public ThreadPoolManager(int minSize, int maxSize, Class taskClass, 
            String username) throws PoolException {
        validateTask(taskClass);
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.taskClass = taskClass;
        this.username = username;
        startThreads(minSize);
    }
    
    
    /**
     * This method returns the min size.
     *
     * @return The minimum size of the thread pool.
     */
    public synchronized int getMinSize() {
        return minSize;
    }
    
    
    /**
     * This method sets the minum size of the thread pool.
     *
     * @param minSize The minimum size of the pool.
     * @exception PoolException
     */
    public synchronized void setMinSize(int minSize) throws PoolException {
        checkState();
        if (minSize > maxSize) {
            throw new PoolException("Min size must be smaller than max size.");
        }
        this.minSize = minSize;
        if (currentSize < minSize) {
            startThreads(minSize - currentSize);
        }
        notifyAll();
    }
    
    
    /**
     * This method returns the max size of the thread pool.
     *
     * @return The maximum size of the thread pool.
     */
    public synchronized int getMaxSize() {
        return maxSize;
    }
    
    
    /**
     * This method sets the maximum size of the thread pool.
     *
     * @param maxSize The maximum size of the thread pool.
     */
    public synchronized void setMaxSize(int maxSize) throws PoolException{
        checkState();
        if (maxSize < minSize) {
            throw new PoolException("Max size must be greater than min size.");
        }
        this.maxSize = maxSize;
        notifyAll();
    }
    
    
    /**
     * This method returns the size of the thread pool.
     *
     * @return The size of the thread pool.
     */
    public synchronized int getSize() {
        return maxSize;
    }
    
    
    /**
     * This method sets the size of the thread pool.
     *
     * @param size The size of the thread pool.
     * @exception PoolException
     */
    public synchronized void setSize(int size) throws PoolException {
        checkState();
        this.minSize = size;
        this.maxSize = size;
        if (currentSize < size) {
            startThreads(size - currentSize);
        }
        notifyAll();
    }
    
    
    /**
     * This method releases threads a thread from the pool.
     *
     * @exception PoolException
     */
    public synchronized void releaseThread() throws PoolException {
        int processing = this.processing.get();
        this.releaseThread++;
        processing += releaseThread;
        if (processing > minSize && processing <= maxSize) {
            startThreads(1);
        }
        notify();
    }
    
    
    /**
     * This method is called to terminate the thread pool.
     */
    public void terminate() throws PoolException {
        state.terminate(true);
        Vector threadListCopy = null;
        synchronized(this) {
            threadListCopy = new Vector(threadList);
        }
        for (Iterator iter = threadListCopy.iterator(); iter.hasNext();) {
            CoadunationThread thread = (CoadunationThread)iter.next();
            thread.terminate();
        }
        
        synchronized(this) {
            notifyAll();
        }
    }
    
    
    /**
     * This method validates the task object.
     *
     * @param taskClass The class to test.
     * @exception PoolException
     */
    private void validateTask(Class taskClass) throws PoolException {
        if (!ClassUtil.testForParent(taskClass,Task.class)) {
            throw new PoolException("Task class [" + taskClass.getName() + 
                    "] does not inherit from [" + Task.class.getName() + "]");
        }
    }
    
    
    /**
     * This method is called to start the threads
     *
     * @param size The number of threads to release.
     * @exception PoolException
     */
    private void startThreads(int size) throws PoolException {
        try {
            for (int count = 0; count < size; count++) {
                PoolThread thread = new PoolThread(this,taskClass);
                thread.start(username);
                addThread(thread);
            }
        } catch (Exception ex) {
            log.error("Failed to start the threads : " + 
                    ex.getMessage(),ex);
            throw new PoolException("Failed to start the threads : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is call by the pool threads to monitor the processing.
     *
     * @return TRUE if processing, should continue, FALSE if not.
     * @exception PoolException
     */
    private synchronized boolean monitor() throws PoolException {
        while (true) {
            if (currentSize > maxSize) {
                currentSize--;
                return false;
            } else if (releaseThread > 0) {
                releaseThread--;
                processing.incrementAndGet();
                return true;
            } else if (currentSize > minSize) {
                currentSize--;
                return false;
            } else if (state.isTerminated()) {
                currentSize--;
                return false;
            }
            try {
                wait();
            } catch (Exception ex) {
                log.error("Wait failed : " + ex.getMessage());
            }
        }
    }
    
    
    /**
     * This method adds a thread to the list of threads
     */
    private synchronized void addThread(PoolThread thread) {
        currentSize++;
        threadList.add(thread);
    }
    
    
    /**
     * This method is called to remove a thread from the list.
     *
     * @param thread The thread to remove
     */
    private synchronized void removeThread(PoolThread thread) {
        threadList.remove(thread);
    }
    
    
    /**
     * This method is used to check the state of this pool.
     *
     * @exception PoolException
     */
    private void checkState() throws PoolException {
        if (state.isTerminated()) {
            throw new PoolException("The thread pool has been terminated");
        }
    }
}
