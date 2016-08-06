/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007 2015 Burntjam
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
 * ChangeLog.java
 */

// package path
package com.rift.coad.util.change;

// java imports
import java.io.Serializable;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationException;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.transaction.TransactionException;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.util.logging.Level;

/**
 * This object is responsible for applying changes to the database the message
 * objects.
 *
 * @author Brett Chaldecott
 */
public class ChangeLog implements XAResource {
    
    /**
     * This class overrides the resolve to use the class loader on the
     * thread to find the specified class.
     */
    public static class ClassLoaderObjectInputStream extends ObjectInputStream {
        /**
         * This default constructor of the class loader object input stream.
         *
         * @exception IOException
         */
        public ClassLoaderObjectInputStream() throws IOException {
            super();
        }
        
        
        /**
         * This default constructor of the class loader object input stream.
         *
         * @param in The input stream for this object.
         * @exception IOException
         */
        public ClassLoaderObjectInputStream(InputStream in) throws IOException {
            super(in);
        }
        
        
        /**
         * This method returns the class definition for the requested object.
         *
         * @return The class definition for the requested object.
         * @param desc The description of the object.
         * @exception IOException
         * @exception ClassNotFoundException
         */
        protected Class resolveClass(ObjectStreamClass desc) throws IOException,
                ClassNotFoundException {
            try {
                return Class.forName(desc.getName());
            } catch (Exception ex) {
                return Thread.currentThread().getContextClassLoader().loadClass(
                        desc.getName());
            }
        }
        
    }
    
    
    /**
     * This object is responsible for managing the batching of changes
     */
    public class ChangeLogBatchManager {
        
        private ClassLoader currentLoader = null;
        private ClassLoader changeClassLoader = null;
        private UserTransactionWrapper utw = null;
        private long maxBatchSize;
        private long batchSize = 0;
        
        /**
         * The default constructor
         */
        public ChangeLogBatchManager(long maxBatchSize) {
            this.maxBatchSize = maxBatchSize;
            try {
                this.utw = new UserTransactionWrapper();
            } catch (TransactionException ex) {
                log.error("Failed to create a user transaction wrapepr : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        public void beginTransaction(Change change) throws Exception {
            ClassLoader changeClassLoader = change.getClass().getClassLoader();
            this.currentLoader = Thread.currentThread().
                        getContextClassLoader();
            
            if (null != this.changeClassLoader && this.changeClassLoader != changeClassLoader) {
                Thread.currentThread().setContextClassLoader(
                        this.changeClassLoader);
                try {
                    this.utw.commit();
                } catch (Exception ex) {
                    log.error("Failed to commit : " + ex.getMessage(),ex);
                } finally {
                    this.utw.release();
                }
                Thread.currentThread().setContextClassLoader(
                        this.currentLoader);
                this.batchSize = 0;
                this.changeClassLoader = null;
            }
            
            
            
            if (this.changeClassLoader == null) {
                this.changeClassLoader = changeClassLoader;
                Thread.currentThread().setContextClassLoader(
                    this.changeClassLoader);
                utw.begin();
            } else {
                Thread.currentThread().setContextClassLoader(
                    this.changeClassLoader);
            }
            
            
            
        }
        
        
        /**
         * This method is called to commit the batch transaction
         */
        public void commitTransaction() {
            this.batchSize++;
            
            if (this.batchSize > this.maxBatchSize) {
                try {
                    this.utw.commit();
                } catch(Exception ex) {
                    log.error("Failed to commit the transaction : " + 
                            ex.getMessage(),ex);
                } finally {
                    this.utw.release();
                }
                this.batchSize = 0;
                this.changeClassLoader = null;
            }
            
            Thread.currentThread().setContextClassLoader(
                        this.currentLoader);
        }
        
        
        /**
         * This method is called to force the commit of a change log transaction
         */
        public void forceCommitTransaction() {
            if (this.changeClassLoader != null) {
                // logic to handle the class loader if we are called
                // from an exception
                if (this.changeClassLoader != Thread.currentThread().
                        getContextClassLoader()) {
                    this.currentLoader = Thread.currentThread().
                        getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(
                            this.changeClassLoader);
                }
                // try and commit within an exception block so that
                // the thread context can be reset properly
                try {
                    this.utw.commit();
                } catch (Exception ex) {
                    log.error("Failed to commit : " + ex.getMessage(),ex);
                } finally {
                    this.utw.release();
                }
                Thread.currentThread().setContextClassLoader(
                        this.currentLoader);
                this.batchSize = 0;
                this.changeClassLoader = null;
            }
        }
    }
    
    
    /**
     * This object is responsible for processing entries in the change log.
     */
    public class ChangeLogProcessor extends CoadunationThread {
        
        // private member variables
        private ThreadStateMonitor state = new ThreadStateMonitor();
        private Context context = null;
        private boolean process = false;
        
        /**
         * The contructor of the change log processor.
         *
         * @exception Exception
         */
        public ChangeLogProcessor() throws Exception {
            
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            synchronized(this) {
                if (process == false) {
                    try {
                        wait();
                    } catch(Exception ex) {
                        log.error("Wait threw and exception : " +
                                ex.getMessage(),ex);
                    }
                }
            }
            while(!state.isTerminated()) {
                ChangeEntry change = null;
                
                change = (ChangeEntry)changes.poll();
                if (change == null) {
                    try {
                        batchManager.forceCommitTransaction();
                    } catch (Exception ex) {
                        log.error("Failed to force the commit of the "
                                + "transaction : " + ex.getMessage(),ex);
                    }
                    // I suspect that the commit is synchronized between 
                    // threads. So in order to prevent a deadlock between it
                    // and the lock on changes. I have moved the synchronize below
                    // the force commit and have added a double check on it.
                    synchronized(changes) {
                        if (changes.isEmpty()) {
                            try {
                                changes.wait(500);
                            } catch (Exception ex) {
                                log.error("Failed to wait : " + ex.getMessage(),ex);
                            }
                        }
                    }
                    continue;
                }
                while(true) {
                    try {
                        change.setBatchManager(batchManager);
                        change.applyChanges();
                        break;
                    } catch (Exception ex) {
                        log.error("Failed to apply the changes : " +
                                ex.getMessage(),ex);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch(Exception ex2) {
                        log.error("Failed to back off : " +
                                ex2.getMessage(),ex2);
                    }
                }
            }
            try {
                batchManager.forceCommitTransaction();
            } catch (Exception ex) {
                log.error("Failed to force the commit of the "
                        + "transaction : " + ex.getMessage(),ex);
            }
            
        }
        
        
        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public void terminate() {
            state.terminate(true);
            synchronized(this) {
                notifyAll();
            }
        }
        
        
        /**
         * This method starts the processing
         */
        public synchronized void startProcessing() {
            process = true;
            notify();
        }
    }
    
    /**
     * This object tracks the changes to do with a transaction.
     */
    public static class ChangeEntry implements Serializable {
        
        // private member variables
        private List<Change> changes = new ArrayList();
        transient private ChangeLogBatchManager batchManager;
        
        /**
         * The constructor of
         */
        public ChangeEntry() {
            
        }

        
        /**
         * This method is called to set the batch manager
         * 
         * @param batchManager 
         */
        protected void setBatchManager(ChangeLogBatchManager batchManager) {
            this.batchManager = batchManager;
        }
        
        
        /**
         * This method adds a change to the list of changes for this change
         * change entry.
         *
         * @param change The object representing the change object.
         */
        public void addChange(Change change) {
            changes.add(change);
        }
        
        
        /**
         * This method applys the list of changes.
         *
         * @exception ChangeException
         */
        public void applyChanges() throws ChangeException {
            for (Change change : changes) {
                // set the class loader
                
                try {
                    batchManager.beginTransaction(change);
                    change.applyChanges();
                    batchManager.commitTransaction();
                } catch (Exception ex) {
                    log.error("Failed to apply the changes : " + ex.getMessage(),ex);
                    batchManager.forceCommitTransaction();
                }
            }
        }
    }
    
    // class constants
    private final static String USERNAME = "changelog_username";
    private final static String DATA_DIR = "changelog_data_dir";
    private final static String DATA_FILE = "changelog.dmp";
    private final static String CHANGE_LOG_BATCH_SIZE = "change_log_batch_size";
    private final static long CHANGE_LOG_BATCH_SIZE_DEFAULT = 20;
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(ChangeLog.class.getName());
    
    // class singleton
    private static Map singletons = new HashMap();
    
    // class member variables
    private ChangeLogBatchManager batchManager;
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private Map changesMap = new ConcurrentHashMap();
    private ThreadLocal currentChange = new ThreadLocal();
    private ChangeLogProcessor processor = null;
    private Queue changes = new ConcurrentLinkedQueue();
    private String dataDirectory = null;
    
    
    /**
     * Creates a new instance of MessageChangeLog
     *
     * @param username The name of the user that this object will run as.
     */
    private ChangeLog(Class configInfo) throws ChangeException {
        try {
            
            Configuration configuration = ConfigurationFactory.getInstance().
                    getConfig(configInfo);
            
            batchManager = new ChangeLogBatchManager(
                    configuration.getLong(CHANGE_LOG_BATCH_SIZE,
                            CHANGE_LOG_BATCH_SIZE_DEFAULT));
            dataDirectory = configuration.getString(DATA_DIR);
            loadData();
            applyChanges();
            processor = new ChangeLogProcessor();
            processor.start(configuration.getString(USERNAME));
        } catch (Exception ex) {
            log.error("Failed to instanciate the change " +
                    "log object : " + ex.getMessage(),ex);
            throw new ChangeException("Failed to instanciate the change " +
                    "log object : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for instanciating the MessageChangeLog.
     *
     * @param configInfo The configuration information for this object
     * @exception ChangeException
     */
    public synchronized static void init(Class configInfo) throws
            ChangeException {
        synchronized (singletons) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (!singletons.containsKey(loader)) {
                ChangeLog changeLog = new ChangeLog(configInfo);
                singletons.put(loader,changeLog);
            }
        }
    }
    
    
    /**
     * This method returns a reference to the singelton instance.
     *
     * @return A reference to the singleton instance.
     * @throws ChangeException
     */
    public static ChangeLog getInstance() throws
            ChangeException {
        synchronized(singletons) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ChangeLog changeLog = (ChangeLog)singletons.get(loader);
            if (changeLog == null) {
                throw new ChangeException(
                        "The change log has not been instanciated.");
            }
            return changeLog;
        }
    }
    
    
    /**
     * The singleton method used to terminate the change log.
     */
    public static void terminate() throws
            ChangeException {
        ChangeLog changeLog = null;
        synchronized(singletons) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            changeLog = (ChangeLog)singletons.get(loader);
            if (changeLog == null) {
                throw new ChangeException(
                        "The change log has not been instanciated.");
            }
            singletons.remove(loader);
        }
        log.info("Terminate change log");
        changeLog.terminateChangeLog();
        log.info("Change log terminated");
    }
    
    /**
     * This method is called to terminate the change log.
     */
    protected void terminateChangeLog() {
        try {
            log.info("Set state and call terminate");
            state.terminate(false);
            log.info("Call terminate on processor");
            processor.terminate();
            log.info("Wait for processor");
            try {
                processor.join(60 * 1000);
                if (processor.isAlive()) {
                    log.info("Thread is still alive attempting to interrupt");
                    processor.interrupt();
                    log.info("Waiting for thread to shut down.");
                    processor.join(30 * 1000);
                }
            } catch (Exception ex) {
              // ignore
            }
            log.info("Store data");
            storeData();
            log.info("Data stored");
        } catch (Exception ex) {
            log.error("Failed to terminate the change log object : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method starts the change log processing the changes.
     */
    public void start() throws ChangeException {
        processor.startProcessing();
    }
    
    
    /**
     * This method adds an object to the list of changes.
     *
     * @param change The object containing the changes to apply.
     * @exception ChangeException
     */
    public void addChange(Change change) throws ChangeException {
        if (state.isTerminated()) {
            log.error("The change log has been terminated cannot accept " +
                    "anymore changes.");
            throw new ChangeException(
                    "The change log has been terminated cannot accept " +
                    "anymore changes.");
        }
        try {
            TransactionManager.getInstance().bindResource(this,false);
            ChangeEntry changeEntry = (ChangeEntry)currentChange.get();
            changeEntry.addChange(change);
        } catch (Exception ex) {
            log.error("Failed to add the change to the list :"
                    + ex.getMessage(),ex);
            throw new ChangeException("Failed to add the change to the list :"
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the size of the change log
     * 
     * @return The size of the change log
     */
    public int getLogSize() {
        return changes.size();
    }
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param b If true a one phase commit should be used.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        synchronized (changes) {
            changes.add(changesMap.remove(xid));
            changes.notify();
        }
    }
    
    
    /**
     * The resource manager has dissociated this object from the transaction.
     *
     * @param xid The id of the transaction that is getting ended.
     * @param i The flags associated with this operation.
     * @exception XAException
     */
    public void end(Xid xid, int i) throws XAException {
    }
    
    
    /**
     * The transaction has been completed and must be forgotten.
     *
     * @param xid The id of the transaction to forget.
     * @exception XAException
     */
    public void forget(Xid xid) throws XAException {
        changesMap.remove(xid);
    }
    
    
    /**
     * This method returns the transaction timeout for this object.
     *
     * @return The int containing the transaction timeout.
     * @exception XAException
     */
    public int getTransactionTimeout() throws XAException {
        return -1;
    }
    
    
    /**
     * This method returns true if this object is the resource manager getting
     * queried.
     *
     * @return TRUE if this is the resource manager, FALSE if not.
     * @param xAResource The resource to perform the check against.
     * @exception XAException
     */
    public boolean isSameRM(XAResource xAResource) throws XAException {
        return this == xAResource;
    }
    
    
    /**
     * This is called before a transaction is committed.
     *
     * @return The results of the transaction.
     * @param xid The id of the transaction to check against.
     * @exception XAException
     */
    public int prepare(Xid xid) throws XAException {
        return XAResource.XA_OK;
    }
    
    
    /**
     * This method returns the list of transaction branches for this resource
     * manager.
     *
     * @return The list of resource branches.
     * @param i The flags
     * @exception XAException
     */
    public Xid[] recover(int i) throws XAException {
        return null;
    }
    
    
    /**
     * This method is called to roll back the specified transaction.
     *
     * @param xid The id of the transaction to roll back.
     * @exception XAException
     */
    public void rollback(Xid xid) throws XAException {
        changesMap.remove(xid);
    }
    
    
    /**
     * This method sets the transaction timeout for this resource manager.
     *
     * @return TRUE if the transaction timeout can be set successfully.
     * @param i The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int i) throws XAException {
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param i The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int i) throws XAException {
        if (changesMap.containsKey(xid)) {
            currentChange.set(changesMap.get(xid));
        } else {
            ChangeEntry changeEntry = new ChangeEntry();
            changesMap.put(xid,changeEntry);
            currentChange.set(changeEntry);
        }
    }
    
    
    /**
     * This method loads the data
     */
    private void loadData() throws ChangeException {
        try {
            File dataFile = new File(dataDirectory,DATA_FILE);
            if (!dataFile.exists()) {
                return;
            }
            FileInputStream in = new FileInputStream(dataFile);
            ClassLoaderObjectInputStream ois = new 
                    ClassLoaderObjectInputStream(in);
            changes = (ConcurrentLinkedQueue)ois.readObject();
            ois.close();
            in.close();
        } catch (Exception ex) {
            log.error("Failed to load the data : " +
                    ex.getMessage(),ex);
            // If the data does not load, ignore it.
            //throw new ChangeException("Failed to load the data : " +
            //        ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method stores the data
     */
    private void storeData() throws ChangeException {
        try {
            File dataFile = new File(dataDirectory,DATA_FILE);
            if (changes.size() == 0) {
                // no data to delete the file if one exists
                if (dataFile.exists()) {
                    dataFile.delete();
                }
                return;
            }
            FileOutputStream out = new FileOutputStream(dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(changes);
            oos.close();
            out.close();
        } catch (Exception ex) {
            log.error("Failed to store the data : " +
                    ex.getMessage(),ex);
            throw new ChangeException("Failed to store the data : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for applying all the changes.
     */
    private void applyChanges() throws ChangeException {
        log.info("Applying changes from change log");
        while(changes.size() > 0) {
            ChangeEntry change = (ChangeEntry)changes.poll();
            while(true) {
                try {
                    change.setBatchManager(batchManager);
                    change.applyChanges();
                    break;
                } catch (Exception ex) {
                    log.error("Failed to apply the changes : " +
                            ex.getMessage(),ex);
                }
                try {
                    Thread.sleep(1000);
                } catch(Exception ex2) {
                    log.error("Failed to back off : " +
                            ex2.getMessage(),ex2);
                }
            }
        }
        log.info("After applying changes from change log");
    }
}
