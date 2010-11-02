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
 * HttpRequestManager.java
 *
 * This object is responsible for queuing the http requests.
 */

// the package path
package com.rift.coad.lib.httpd;

// java imports
import java.util.Vector;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object is responsible for queuing the http requests.
 *
 * @author Brett Chaldecott
 */
public class HttpRequestManager {
    
    // the http request queue
    public class HttpRequestQueue {
        // the classes private member variables
        private Vector requestQueue = new Vector();
        
        /**
         * The constructor of the http request queue.
         */
        public HttpRequestQueue() {
            
        }
        
        
        /**
         * This method adds a new request to the request queue.
         *
         *
         * @param requestInterface The reference to the http service handler object.
         * @exception HttpdException
         */
        public synchronized void pushRequest(
                Request requestInterface) throws HttpdException  {
            processorPoolManager.notifyThread();
            requestQueue.add(requestInterface);
        }
        
        
        /**
         * This method will pop a request of the queue.
         */
        public synchronized  Request popRequest() {
            if (requestQueue.size() == 0) {
                return null;
            }
            Request requestInterface =
                    (Request)requestQueue.get(0);
            requestQueue.remove(0);
            return requestInterface;
        }
        
        
        /**
         * This method returns the size of the http request manager.
         *
         * @return The size of the http request manager.
         */
        public synchronized int size() {
            return requestQueue.size();
        }
    }
    
    
    /**
     * This thread is responsible controlling the http processing required by
     * clients.
     */
    public class HttpProcessor extends BasicThread {
        
        // The classes private member variables
        private ThreadStateMonitor threadStateMonitor =
                new ThreadStateMonitor();
        
        /**
         * The constructor of the http processor.
         */
        public HttpProcessor() throws Exception {
            
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            log.debug("Process.");
            while(threadStateMonitor.isTerminated() == false) {
                log.debug("Wait for request.");
                if (processorPoolManager.monitor() == false) {
                    break;
                }
                log.debug("Get a request.");
                Request requestInterface =
                        requestQueue.popRequest();
                if (requestInterface != null) {
                    try {
                        log.info("Process a new http request.");
                        requestInterface.handleRequest();
                    } catch (Exception ex) {
                        log.error("Failed to process an HTTP request [" +
                                ex.getMessage() + "].",ex);
                    } finally {
                        requestInterface.destroy();
                    }
                }
            }
            log.debug("Http Processor exiting.");
        }
        
        
        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public void terminate() {
            log.debug("Terminate called on Http Processor");
            threadStateMonitor.terminate(true);
        }
    }
    
    
    /**
     * This object is responsible for controlling the processors. It
     */
    public class ProcessorPoolManager {
        
        // the private member variables
        private boolean running = true;
        private CoadunationThreadGroup threadGroup = null;
        private int min = 0;
        private int max = 0;
        private int currentSize = 0;
        private int waitingThreads = 0;
        private int requestCount = 0;
        private String username = null;
        
        
        /**
         * The constructor of the thread group object.
         *
         * @param threadGroup The reference to the thread group.
         * @param min The minimum value.
         * @param max The maximum value.
         * @param username The guest username.
         */
        public ProcessorPoolManager(CoadunationThreadGroup threadGroup,int min, int max,
                String username)
                throws HttpdException {
            try {
                this.threadGroup = threadGroup.createThreadGroup();
                this.min = min;
                this.max = max;
                this.username = username;
            } catch (Exception ex) {
                throw new HttpdException(
                        "Failed to instanciate the processor pool manager : " +
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method terminates the waiting threads
         */
        public void shutdown() {
            synchronized (this) {
                running = false;
                notifyAll();
            }
            log.debug("Terminate the thread group");
            threadGroup.terminate();
        }
        
        
        /**
         * This method will notify a waiting thread if one is available
         * otherwise it will instanciate a new one.
         */
        public synchronized void notifyThread() throws HttpdException {
            if (running) {
                requestCount++;
                if (waitingThreads > 0) {
                    log.debug("Notify a waiting thread 2");
                    notify();
                } else if (currentSize < max) {
                    try {
                        log.debug("Add a thread");
                        // instanciate the deployment thread
                        HttpProcessor httpProcessor = new HttpProcessor();
                        threadGroup.addThread(httpProcessor,username);
                        httpProcessor.start();
                        currentSize++;
                    } catch (Exception ex) {
                        throw new HttpdException
                                ("Failed create a thread to process task : " +
                                ex.getMessage(),ex);
                    }
                }
            } else {
                throw new HttpdException
                        ("Shut Down can handle not more requests");
            }
        }
        
        
        /**
         * This method will be called a thread to either wait untill there are
         * requests, get an exit notice if not require any more or start
         * processing.
         *
         * @return TRUE if processing is required, FALSE if the thread must exit.
         */
        public synchronized boolean monitor() {
            if (running == false) {
                currentSize--;
                return false;
            } else if (requestCount > 0) {
                requestCount --;
                return true;
            } else if (currentSize > min) {
                currentSize--;
                return false;
            }
            try {
                waitingThreads++;
                wait();
                waitingThreads--;
            } catch (Exception ex) {
                // do nothing
            }
            return running;
        }
    }
    
    // class constants
    private final static String MIN_KEY = "pool_min";
    private final static long MIN_DEFAULT = 10;
    private final static String MAX_KEY = "pool_max";
    private final static long MAX_DEFAULT = 20;
    
    // static member variables
    private Logger log =
            Logger.getLogger(HttpRequestManager.class.getName());
    
    // the privat emember variables
    private Configuration configuration = null;
    private HttpRequestQueue requestQueue = new HttpRequestQueue();
    private ProcessorPoolManager processorPoolManager = null;
    
    
    /**
     * Creates a new instance of HttpRequestManager
     *
     *
     * @param threadGroup The reference to the thread group object.
     * @exception HttpdException
     */
    public HttpRequestManager(CoadunationThreadGroup threadGroup) throws HttpdException {
        try {
            configuration = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            processorPoolManager = new ProcessorPoolManager(threadGroup,
                    (int)configuration.getLong(MIN_KEY,MIN_DEFAULT),
                    (int)configuration.getLong(MAX_KEY,MAX_DEFAULT),
                    configuration.getString(HttpDaemon.USERNAME_KEY));
        } catch (Exception ex) {
            throw new HttpdException(
                    "Failed to instanciate the http request manager : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method stores the http service handler requests.
     *
     *
     * @param requestInterface The reference to the new request to add.
     * @exception HttpdException
     */
    public void addRequest(Request requestInterface)
    throws HttpdException {
        requestQueue.pushRequest(requestInterface);
    }
    
    
    /**
     * This mehtod will shut down this object
     */
    public void shutdown() {
        processorPoolManager.shutdown();
    }
}
