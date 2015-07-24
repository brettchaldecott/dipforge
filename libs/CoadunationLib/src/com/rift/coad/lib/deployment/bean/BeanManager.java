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
 * BeanManager.java
 *
 * This object is responsible for managing the loading and unloading of
 * coadunation beans.
 */

// package path
package com.rift.coad.lib.deployment.bean;

// java imports
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;
import java.rmi.Remote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.rmi.PortableRemoteObject;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.bean.BeanWrapper;


/**
 * This object is responsible for managing the loading and unloading of
 * coadunation beans.
 *
 * @author Brett Chaldecott
 */
public class BeanManager {
    
    /**
     * This object wrapps access to the beans.
     *
     * @author Brett Chaldecott
     */
    public class BeanList {
        
        // class constants
        private final static String DEFAULT_PATH = "java:comp/env/bean";
        private final static String BEAN_CTX = "beanctx";
        
        // private member variables
        private Map beans = null;
        private Configuration config = null;
        private Context context = null;
        private ContextManager contextManager = null;
        
        /**
         * The constructor of the bean list.
         *
         * @exception BeanException
         */
        public BeanList() throws BeanException {
            beans = new LinkedHashMap();
            try {
                config = ConfigurationFactory.getInstance().
                        getConfig(this.getClass());
                context = new InitialContext();
                contextManager = new ContextManager(
                        config.getString(BEAN_CTX,DEFAULT_PATH));
            } catch (Exception ex) {
                throw new BeanException("Failed to instanciate the BeanList : "
                        + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method adds a new bean to the list of beans
         *
         * @param key The new key to add.
         * @param bean The object reference to add.
         */
        public synchronized void addBean(String key, Object bean) throws
                BeanException {
            try {
                BeanWrapper wrapper = (BeanWrapper)bean;
                if (wrapper.getTie() != null) {
                    log.info("Adding the [" + key
                            + "] to the portable remote object.");
                    javax.rmi.PortableRemoteObject.exportObject(wrapper.getTie());
                    context.bind(key,wrapper.getTie());
                }
                // local context binding
                contextManager.bind(key,wrapper.getProxy());
                
                // add to maps
                beans.put(key,bean);
            } catch (Exception ex) {
                log.error("Failed to add the bean [" + key + "]",ex);
                throw new BeanException(
                        "Failed to add the bean to the list : " +
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method removes the bean from the map.
         *
         * @param key The key identifying the bean to remove.
         */
        public synchronized void removeBean(String key) throws BeanException {
            try {
                BeanWrapper wrapper = (BeanWrapper)beans.get(key);
                if (wrapper.getTie() != null) {
                    // we do not need to remove the local reference as
                    // this gets removed when the context gets released
                    try {
                        log.info("Unbinding an object [" + key
                                + "] from the portable object");
                        context.unbind(key);
                        javax.rmi.PortableRemoteObject.unexportObject(
                                wrapper.getTie());
                    } catch (org.omg.CORBA.BAD_INV_ORDER ex) {
                        log.info("Failed to unbind the object [" + key
                                + "] as it is not bound.",ex);
                    }
                }
                beans.remove(key);
            } catch (Exception ex) {
                throw new BeanException(
                        "Failed to remove the bean from the list : " +
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns true if the bean list contains the key.
         *
         * @return TRUE if the bean is found FALSE if not.
         * @param key The key to check for.
         */
        public synchronized boolean contains(String key) {
            return beans.containsKey(key);
        }
        
        
        /**
         * This method returns the reference to the object, if it cannot be
         * found it returns null.
         *
         * @return The reference to the object.
         * @param key The key to look for.
         */
        public synchronized Object getBean(String key) {
            return beans.get(key);
        }
        
        
        /**
         * This method returns the keys identifying the beans.
         *
         * @return The list of keys.
         */
        public synchronized Set getKeys() {
            return beans.keySet();
        }
    }
    
    // class constants
    private final static String USERNAME = "bean_user";
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(BeanManager.class.getName());
    
    // the classes private member variables
    private ThreadsPermissionContainer permissions = null;
    
    // the private member variables
    private Map entries = null;
    private BeanList beanList = null;
    private CoadunationThreadGroup threadGroup = null;
    private String username = null;
    
    /**
     * Creates a new instance of BeanManager
     *
     * @param permissions The reference to the permission object.
     * @param threadGroup The reference to the thread group.
     * @exception Exception
     */
    public BeanManager(ThreadsPermissionContainer permissions,
            CoadunationThreadGroup threadGroup) throws Exception {
        this.permissions = permissions;
        this.threadGroup = threadGroup.createThreadGroup();
        entries = new LinkedHashMap();
        beanList = new BeanList();
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(BeanManager.class);
            username = config.getString(USERNAME);
        } catch (Exception ex) {
            throw new BeanException("Failed to start the bean manager [" +
                    ex.getMessage() + "]",ex);
        }
    }
    
    
    /**
     * This method will load beans from a deployment file.
     *
     * @param deploymentLoader The reference reference to the deployment loader.
     * @exception BeanException
     */
    public void load(DeploymentLoader deploymentLoader) throws BeanException,
            Exception {
        if (entries.containsKey(deploymentLoader)) {
            throw new BeanException("This entries has been loaded before.");
        }
        
        // check for classesh
        beanClash(deploymentLoader.getDeploymentInfo().getBeans());
        
        // load the beans
        BeanLoader beanLoader = new BeanLoader(deploymentLoader,permissions,
                threadGroup);
        beanLoader.setContextClassLoader(deploymentLoader.getClassLoader());
        threadGroup.addThread(beanLoader,username);
        beanLoader.start();
        beanLoader.join();
        if (!beanLoader.wasSucessfull()) {
            throw beanLoader.getException();
        }
        
        // add the new beans
        Map beans = beanLoader.getBeans();
        for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            log.info("Load bean [" + key + "]");
            beanList.addBean(key,beans.get(key));
        }
        entries.put(deploymentLoader,beanLoader);
        
    }
    
    
    /**
     * This method will unload the entry from the bean management object.
     *
     * @param deploymentLoader The reference to the deployment loader object.
     * @exception BeanException
     */
    public void unLoad(DeploymentLoader deploymentLoader) throws BeanException {
        if (false == entries.containsKey(deploymentLoader)) {
            // do nothing there is nothing known about this entry
            return;
        }
        
        // remove all the bean references.
        BeanLoader beanLoader = (BeanLoader)entries.get(deploymentLoader);
        
        // stop threads
        beanLoader.stopThreads();
        
        // unload beans
        Map beans = beanLoader.getBeans();
        for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            log.info("Unload bean [" + key + "]");
            beanList.removeBean(key);
        }
        
        // remove the bean
        entries.remove(deploymentLoader);
        
        
    }
    
    
    /**
     * This method returns the list of keys.
     *
     * @return The list of beans managed by this object.
     */
    public Set getKeys() {
        return beanList.getKeys();
    }
    
    
    /**
     * Retrieve the bean that matches the key
     *
     * @return Return the object identified by the key.
     * @param key The key identifying the bean.
     */
    public Object getBean(String key) {
        return beanList.getBean(key);
    }
    
    
    /**
     * This method determines if there are any bean classes based on name.
     *
     * @param newBeans The new beans to add to the list.
     * @exception BeanException
     */
    private void beanClash(Map newBeans) throws BeanException {
        
        for (Iterator iter = newBeans.keySet().iterator(); iter.hasNext();) {
            BeanInfo beanInfo = (BeanInfo)newBeans.get(iter.next());
            if (this.beanList.contains(beanInfo.getBindName())) {
                throw new BeanException(
                        "One of the beans clashes with an existing one [" +
                        beanInfo.getBindName() + "]");
            }
        }
    }
    
    
}
