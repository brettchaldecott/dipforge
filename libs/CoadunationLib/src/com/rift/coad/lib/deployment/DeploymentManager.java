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
 * XMLConfigurationException.java
 *
 * DeploymentManager.java
 *
 * This object is responsible for controlling the loading of Coadunation
 * deployment files using the deployment loader and other management objects.
 */

// the package path
package com.rift.coad.lib.deployment;

// java imports
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

// logging import
import org.apache.log4j.Logger;

// import path
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.bean.BeanManager;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanManager;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadGroupManager;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.transaction.TransactionManager;

/**
 * This object is responsible for controlling the loading of Coadunation
 * deployment files using the deployment loader and other management objects.
 *
 * @author Brett Chaldecott
 */
public class DeploymentManager {
    
    /**
     * The deployment thread responsible for controlling the management of
     * the deployed beans, jmx beans and web services.
     */
    public class DeploymentThread extends BasicThread {
        // the reference to the deployment manager
        private DeploymentManager deploymentManager = null;
        private boolean terminated = false;
        private long delayPeriod = 0;
        
        /**
         * The constructor of the deployment thread.
         *
         * @param deploymentManager The reference to the deployment manager.
         */
        public DeploymentThread(DeploymentManager deploymentManager) throws 
                DeploymentException, Exception  {
            try {
                this.deploymentManager = deploymentManager;
                
                // retrieve the configuration
                Configuration config = ConfigurationFactory.getInstance().
                        getConfig(this.getClass());
                
                delayPeriod = config.getLong("delay");
                
            } catch (Exception ex) {
                throw new DeploymentException(
                        "Failed to init the deployment thread",ex);
            }
        }
        
        
        /**
         * This method will be called to like the run method in a tradition thread.
         */
        public void process() {
            // reset the class loader
            while(isTerminated() == false) {
                deploymentManager.manage();
                delay();
            }
        }
        
        
        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public synchronized void terminate() {
           terminated = true;
           DeploymentMonitor.getInstance().terminate();
        }


        /**
         * This method returns the information about what this thread is processing.
         *
         * @return String containing a description of this thread
         */
        public String getInfo() {
            return "Managing the Coadunation deployment files.";
        }
        
        
        /**
         * This method will be called to check the status of this thread.
         */
        private synchronized boolean isTerminated() {
            return terminated;
        }
        
        
        /**
         * This method will delay the processing for the processing for the given
         * period of time.
         */
        private synchronized void delay() {
            try {
                wait(delayPeriod);
            } catch (Exception ex) {
                // ignore
            }
        }
    }
    
    
    /**
     * This object is responsible for maintaining the loader list. It supplies
     * thread safe access to the list within.
     */
    public class LoaderList {
        // the classes member variables
        private Map loaders = null;
        
        
        /**
         * The constructor of the loader list.
         */
        public LoaderList() {
            loaders = new ConcurrentHashMap();
        }
        
        
        /**
         * This method will return true if the loader represented by the file is
         * contained 
         *
         * @return TRUE if the object is found FALSE if it is not.
         * @param file The file to find in the list
         */
        public synchronized boolean contains(File file) {
            return loaders.containsKey(file);
        }
        
        
        /**
         * This method will add a file to the container.
         *
         * @param file The file to add to the container.
         * @param obj The object to add to the container.
         */
        public synchronized void add(File file, Object obj) {
            loaders.put(file,obj);
        }
        
        
        /**
         * This method will return the reference to the deployment loader
         * identified by the file.
         *
         * @return The reference to the deployment loader.
         * @param file The reference to the file.
         */
        public synchronized DeploymentLoader get(File file) {
            return (DeploymentLoader)loaders.get(file);
        }
        
        
        /**
         * This method removes the loader from the loader list identified by the
         * key.
         *
         * @param file The file to remove from the list.
         */
        public synchronized void remove(File file) {
            loaders.remove(file);
        }
        
        
        /**
         * This method will return the keys for the loaders.
         *
         * @return The keys for the set.
         */
        public synchronized Set getKeys() {
            return loaders.keySet();
        }
    }
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(DeploymentManager.class.getName());
    
    // private member variables
    private LoaderList loaderList = null;
    private CoadunationThreadGroup threadGroup = null;
    private BeanManager beanManager = null;
    private JMXBeanManager jmxBeanManager = null;
    private WebServiceManager webServiceManager = null;
    private DeploymentThread deploymentThread = null;
    private File deploymentDir = null;
    
    
    /**
     * Creates a new instance of DeploymentManager.
     *
     * @param threadGroup The reference to the thread manager.
     * @param beanManager The reference to the bean manager object.
     * @param jmxBeanManager The reference to the jmx bean object.
     * @param webServiceManager The web service manager object.
     * @exception DeploymentException
     */
    public DeploymentManager(CoadunationThreadGroup threadGroup,BeanManager beanManager,
            JMXBeanManager jmxBeanManager, WebServiceManager webServiceManager) 
    throws DeploymentException {
        try {
            // setup the member variables
            loaderList = new LoaderList();
            this.threadGroup = threadGroup.createThreadGroup();
            this.beanManager = beanManager;
            this.jmxBeanManager = jmxBeanManager;
            this.webServiceManager = webServiceManager;
            
            // maintain the tmp directory for the loaders
            maintainTmpDirectory();
            
            // retrieve the configuration
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                        this.getClass());
            
            // retrieve the user information
            String user = config.getString("username");
            
            deploymentDir = new File(config.getString("directory"));
            if (deploymentDir.isDirectory() == false) {
                throw new DeploymentException(
                    "The deployment directory [" + config.getString("directory")
                    + "] is invalid");
            }
            
            // instanciate the deployment thread
            deploymentThread = new DeploymentThread(this);
            this.threadGroup.addThread(deploymentThread,user);
            deploymentThread.start();
            
        } catch (Exception ex) {
            throw new DeploymentException(
                    "Failed to start the deployment loader : " + ex.getMessage()
                    ,ex);
        }
    }
    
    
    /**
     * This method shutdown the processing of the deployment manager.
     */
    public void shutdown() {
        threadGroup.terminate();
        
        // unload all the loaded entries
        try {
            deploymentThread.join();
            Set keys = loaderList.getKeys();
            File[] loadedFiles = new File[keys.size()];
            loadedFiles = (File[])keys.toArray(loadedFiles);
            Arrays.sort(loadedFiles);
            for (int count = 0; count < loadedFiles.length; count++) {
                    removeEntry(loadedFiles[loadedFiles.length - (count + 1)]);
            }
        } catch (Exception ex) {
            log.error("Failed to shut down an entry because : " + ex.
                    getMessage(),ex);
        }
    }
    
    
    /**
     * This private method is responsible for controlling the loading, and unloading
     * of deployment files.
     */
    private void manage() {
        try {
            // retrieve a list of files
            File[] files = FileUtil.filter(deploymentDir.listFiles(),".jar");
            Arrays.sort(files);
            
            // check to see which file dates have changed
            for (int count = 0; count < files.length; count++) {
                File file = files[count];
                if (loaderList.contains(file) == false) {
                    continue;
                } else if (loaderList.get(file).getLastModified() !=
                        file.lastModified()) {
                    removeEntry(file);
                }
            }
            
            
            // load new files
            for (int count = 0; count < files.length; count++) {
                File file = files[count];
                if (loaderList.contains(file) == false) {
                    loadEntry(file);
                }
            }
            
            // unload files that have date changes and are no longer there
            Set keySet = new HashSet(loaderList.getKeys());
            for (Iterator iter = keySet.iterator(); iter.hasNext();)
            {
                // array for entry
                File key = (File)iter.next();
                boolean found = false;
                for (int count = 0; count < files.length; count++) {
                    File file = files[count];
                    if (file.equals(key)) {
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    removeEntry(key);
                }
            }
            
            
        } catch (Throwable ex) {
            log.error("Failed to load the files : " + ex.getMessage(),ex);
        }
        // set the deployment monitor
        if (false == DeploymentMonitor.getInstance().
                isInitDeployComplete()) {
            DeploymentMonitor.getInstance().initDeployCompleted();
        }
    }
    
    
    /**
     * This method will be responsible for removing a loader from memory.
     *
     * @param file The path to the file to remove.
     */
    private void removeEntry(File file) {
        ClassLoader originalLoader = null;
        try {
            // instanciate the 
            DeploymentLoader loader = loaderList.get(file);
            if (loader.getSuccessful() == false) {
                log.info("This object was not loaded succesfully [" 
                        + file.getAbsolutePath() + "] removing internal " +
                        "reference");
                loaderList.remove(file);
                return;
            }
            log.info("Un-load the deployment file [" + file.getAbsolutePath() +
                    "]");
            originalLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(loader.getClassLoader());
            CacheRegistry.getInstance().terminateCache();
            webServiceManager.unLoad(loader);
            beanManager.unLoad(loader);
            jmxBeanManager.unLoad(loader);
            ObjectLockFactory.fin();
            TransactionManager.fin();
            loaderList.remove(file);
            
            // remove the reference to the loader from the class loader lookup
            DeploymentLoader.ClassLoaderLookup.getInstance().removeClassLoader(
                    loader);
            
            NamingDirector.getInstance().releaseContext();
            ThreadGroupManager.getInstance().terminateThreadGroup();
        } catch (Throwable ex) {
            log.error("Failed to unload the file [" + file.getAbsolutePath() +
                    "]",ex);
        } finally {
            try {
                if (originalLoader != null) {
                    // reset the class loader
                    Thread.currentThread().setContextClassLoader(
                            originalLoader);
                }
            } catch (Exception exc) {
                // ignore
            }
        }
    }
    
    
    /**
     * This method will and entry into memory.
     *
     * @param file The file to load into memory.
     */
    private void loadEntry(File file) {
        DeploymentLoader loader = null;
        ClassLoader originalLoader = null;
        try {
            // instanciate the 
            log.info("Load the deployment file [" + file.getAbsolutePath() +
                    "]");
            try {
                loader = new DeploymentLoader(file);
            } catch (Exception ex) {
                log.warn("Failed to load the file [" + file.getAbsolutePath() +
                    "] will try this it again later.",ex);
                return;
            }
            
            loaderList.add(file,loader);
            if (loader.getSuccessful() == false) {
                log.info("The failed to load the [" 
                        + file.getAbsolutePath() + "].");
                return;
            }
            
            // set the class loader for the beans
            originalLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(loader.getClassLoader());
            ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
            NamingDirector.getInstance().initContext();
            CacheRegistry.getInstance().initCache();
            ObjectLockFactory.init();
            TransactionManager.init();
            jmxBeanManager.load(loader);
            beanManager.load(loader);
            webServiceManager.load(loader);
            
            log.info("The file [" 
                        + file.getAbsolutePath() + "] was loaded successfully");
        } catch (Throwable ex) {
            // unload if there was any problem while loading the entry.
            try {
                if (loader != null) {
                    beanManager.unLoad(loader);
                    jmxBeanManager.unLoad(loader);
                    webServiceManager.unLoad(loader);
                }
                
            } catch (Exception exc) {
                // ignore any exceptions
            }
            try {
                CacheRegistry.getInstance().terminateCache();
            } catch (Exception exc) {
                // failed to terminate the cache
                log.error("Failed to terminate the cache [" + exc.getMessage() +
                        "]",ex);
            }
            try {
                ThreadGroupManager.getInstance().terminateThreadGroup();
            } catch (Exception exc) {
                // failed to terminate the cache
                log.error("Failed to terminate the thread group [" 
                        + exc.getMessage() + "]",ex);
            } 
            log.error("Failed to load the file [" + file.getAbsolutePath() +
                    "]",ex);
        } finally {
            try {
                if (originalLoader != null) {
                    // reset the class loader
                    Thread.currentThread().setContextClassLoader(
                            originalLoader);
                }
            } catch (Exception exc) {
                // ignore
            }
        }
    }
    
    
    /**
     * This method maintains the jar temp directory if the directory does not 
     * exist it will create it, and it will delete all misc jar files that it
     * finds.
     *
     * @exception DeploymentException
     */
    private void maintainTmpDirectory() throws DeploymentException {
        try {
            // retrieve the loader configuration.
            Configuration loaderConfig = ConfigurationFactory.getInstance().
                        getConfig(DeploymentLoader.class);
            
            // retrieve the temp directory
            String tmpDirPath = loaderConfig.getString(
                    DeploymentLoader.TEMP_DIRECTORY);
            
            // create the temporary directory
            File tmpDir = new File(tmpDirPath);
            tmpDir.mkdirs();
            if (tmpDir.isDirectory() == false) {
                throw new DeploymentException(
                        "Failed to create the directory [" + tmpDirPath + "]");
            }
            deleteDirectoryRecursive(tmpDir);
        } catch (Throwable ex) {
            throw new DeploymentException("Failed to clean the tmp directory : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to delete all the files from a given directory.
     *
     * @param dir The directory to delete the files from
     */
    private void deleteDirectoryRecursive(File dir) throws Exception {
        // list the files
        File[] tmpFiles = dir.listFiles();
        for (int index = 0; index < tmpFiles.length; index++) {
            if (tmpFiles[index].isDirectory()) {
                deleteDirectoryRecursive(tmpFiles[index]);
            }
            tmpFiles[index].delete();
        }
    }
}
