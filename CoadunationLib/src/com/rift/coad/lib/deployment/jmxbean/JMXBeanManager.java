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
 * XMLConfigurationException.java
 *
 * JMXBeanManager.java
 *
 * The JMX Bean manager is responsible for loading and unloading of jmx beans.
 * It is controlled by the DeploymentManager.
 */

package com.rift.coad.lib.deployment.jmxbean;

// java imports
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.rmi.Remote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.rmi.PortableRemoteObject;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanWrapper;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.deployment.JMXBeanInfo;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.naming.ContextManager;
import com.rift.coad.lib.bean.BeanWrapper;

/**
 * The JMX Bean manager is responsible for loading and unloading of jmx beans.
 * It is controlled by the DeploymentManager.
 *
 * @author Brett Chaldecott
 */
public class JMXBeanManager {
    
    /**
     * This object maintains the beans in memory.
     */
    public class BeanList {
        // class constants
        private final static String DEFAULT_PATH = "java:comp/env/jmx";
        private final static String JMX_CTX = "jmxctx";
        
        // the list of beans
        private Map objectList = null;
        private Map bindList = null;
        private Configuration config = null;
        private Context context = null;
        private ContextManager contextManager = null;
        
        
        /**
         * The default constructor of the bean list.
         *
         * @exception JMXException
         */
        public BeanList() throws JMXException {
            objectList = new LinkedHashMap();
            bindList = new LinkedHashMap();
            try {
                config = ConfigurationFactory.getInstance().
                        getConfig(this.getClass());
                context = new InitialContext();
                contextManager = new ContextManager(
                        config.getString(JMX_CTX,DEFAULT_PATH));
            } catch (Exception ex) {
                throw new JMXException("Failed to instanciate the BeanList : "
                        + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method will add the jmx bean to the object and bind list.
         *
         * @param objectName The object name to use in the object list.
         * @param bean The reference to the bean to add.
         */
        public synchronized void addBean(String objectName, Object bean) throws
                JMXException{
            BeanWrapper wrapper = (BeanWrapper)bean;
            try {
                if (wrapper.getTie() != null) {
                    log.info("Adding the [" + wrapper.getBindName()
                    + "] to the portable remote object.");
                    javax.rmi.PortableRemoteObject.exportObject(wrapper.getTie());
                    context.bind(wrapper.getBindName(),wrapper.getTie());
                }
                // Add to local context
                contextManager.bind(wrapper.getBindName(),wrapper.getProxy());
                
                // add to object lists
                objectList.put(objectName,bean);
                bindList.put(wrapper.getBindName(),bean);
                
            } catch (Exception ex) {
                log.error("Failed to add the bean [" + objectName + "][" +
                        wrapper.getBindName() + "]",ex);
                throw new JMXException(
                        "Failed to add the jmx bean to the list : " +
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method removed the bean from the lists.
         *
         * @param objectName The name of the object to remove from the list.
         */
        public synchronized void removeBean(String objectName) throws
                JMXException{
            try {
                BeanWrapper wrapper = (BeanWrapper)objectList.get(objectName);
                if (wrapper != null) {
                    // we do not need to remove the local reference as
                    // this gets removed when the context gets released
                    bindList.remove(wrapper.getBindName());
                    if (wrapper.getTie() != null) {
                        try{
                            log.info("Unbinding an object [" + objectName
                                    + "] from the portable object");
                            context.unbind(wrapper.getBindName());
                            javax.rmi.PortableRemoteObject.unexportObject(
                                    wrapper.getTie());
                        } catch (org.omg.CORBA.BAD_INV_ORDER ex) {
                            log.info("Failed to unbind the object [" + objectName
                                    + "] as it is not bound.",ex);
                        }
                    }
                }
                objectList.remove(objectName);
            } catch (Exception ex) {
                throw new JMXException(
                        "Failed to remove the jmx bean from the list : " +
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns the list of keys identifying the objects.
         *
         * @return The keys identifying the objects in the system.
         */
        public synchronized Set getObjectKeys() {
            return objectList.keySet();
        }
        
        
        /**
         * This method returns the object reference identified by the JMX object
         * name.
         *
         * @return The object identified by the JMX object name, or null if it
         *      is not found.
         * @param key The key identifying the object.
         */
        public synchronized Object getObject(String key) {
            return objectList.get(key);
        }
        
        
        /**
         * This method returns true if the object list contains the specified
         * key.
         *
         * @return TRUE if the key is found FALSE if not.
         * @param key The key identifying the object.
         */
        public synchronized boolean containsObject(String key) {
            return objectList.containsKey(key);
        }
        
        
        /**
         * This method returns the list of bind keys.
         *
         * @return The key set containing the bind keys.
         */
        public synchronized Set getBindKeys() {
            return bindList.keySet();
        }
        
        
        /**
         * Thist method returns the bind information.
         *
         * @return The bind information.
         * @param key The bind key identifying the object.
         */
        public synchronized Object getBindObject(String key) {
            return bindList.get(key);
        }
        
        
        /**
         * This method returns true if the list contains the bind object.
         *
         * @return TRUE if found FALSE if not.
         * @param key The key to find.
         */
        public synchronized boolean containsBindObject(String key) {
            return bindList.containsKey(key);
        }
    }
    
    // class constants
    private final static String USERNAME = "jmxbean_user";
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(JMXBeanManager.class.getName());
    
    
    // class member variables
    private final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    private ThreadsPermissionContainer permissions = null;
    private Map loaders = null;
    private BeanList beanList = null;
    private CoadunationThreadGroup threadGroup = null;
    private String username = null;
    
    /**
     * Creates a new instance of JMXBeanManager
     *
     * @param permissions The reference thread permissions.
     * @param threadGroup The group of threads.
     * @exception Exception
     */
    public JMXBeanManager(
            ThreadsPermissionContainer permissions,
            CoadunationThreadGroup threadGroup) throws Exception {
        this.permissions = permissions;
        loaders = new LinkedHashMap();
        beanList = new BeanList();
        this.threadGroup = threadGroup.createThreadGroup();
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(JMXBeanManager.class);
            username = config.getString(USERNAME);
        } catch (Exception ex) {
            throw new JMXException("Failed to start the JMX Bean manager [" +
                    ex.getMessage() + "]",ex);
        }
    }
    
    
    /**
     * This method loads the JMX bean from the deployment file using the jmx
     * bean deployment loader.
     *
     * @param loader The reference to the loader object.
     * @exception JMXException
     * @exception Exception
     */
    public void load(DeploymentLoader loader) throws JMXException, Exception {
        if (loaders.containsKey(loader)) {
            throw new JMXException("This entries has been loaded before.");
        }
        
        // check for clashes
        beanClash(loader.getDeploymentInfo().getJmxBeans());
        
        // load the beans
        JMXBeanLoader jmxBeanLoader = new JMXBeanLoader(mbs,loader,permissions,
                threadGroup);
        jmxBeanLoader.setContextClassLoader(loader.getClassLoader());
        threadGroup.addThread(jmxBeanLoader,username);
        jmxBeanLoader.start();
        jmxBeanLoader.join();
        if (!jmxBeanLoader.wasSucessfull()) {
            throw jmxBeanLoader.getException();
        }
        
        // add the beans to the bean list
        Map beans = jmxBeanLoader.getBeans();
        for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            log.info("Load bean [" + key + "]");
            beanList.addBean(key,beans.get(key));
        }
        
        // add the beans
        loaders.put(loader,jmxBeanLoader);
    }
    
    
    /**
     * This method unloads the deployment loader.
     *
     * @param loader The loader to unload.
     * @exception JMXException
     */
    public void unLoad(DeploymentLoader loader) throws JMXException {
        if (false == loaders.containsKey(loader)) {
            // do nothing there is nothing known about this entry
            return;
        }
        // remove all the bean references.
        JMXBeanLoader jmxBeanLoader = (JMXBeanLoader)loaders.get(loader);
        
        // stop threads
        jmxBeanLoader.stopThreads();
        
        // unload
        Map beans = jmxBeanLoader.getBeans();
        for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            log.info("Unload bean [" + key + "]");
            beanList.removeBean(key);
        }
        
        // remove the bean
        jmxBeanLoader.unRegisterBeans();
        loaders.remove(loader);
    }
    
    
    /**
     * Retrieve the objects keys. These are the standard lookup keys.
     *
     * @return The list of beans managed by this object.
     */
    public Set getObjectKeys() {
        return beanList.getObjectKeys();
    }
    
    
    /**
     * This method will return the reference to the wrapper object using the
     * bind key identifier.
     *
     * @return The object identified by the bind key.
     * @param key The bind key to retrieve the object for.
     */
    public Object getObject(String key) {
        return beanList.getObject(key);
    }
    
    
    /**
     * Retrieve the bind keys. These are the standard lookup keys.
     *
     * @return The list of beans managed by this object.
     */
    public Set getBindKeys() {
        return beanList.getBindKeys();
    }
    
    
    /**
     * This method will return the reference to the wrapper object using the
     * bind key identifier.
     *
     * @return The object identified by the bind key.
     * @param key The bind key to retrieve the object for.
     */
    public Object getBindObject(String key) {
        return beanList.getBindObject(key);
    }
    
    
    /**
     * This method checks to see if there are any clashing beans.
     *
     * @param beans The list of beans to perform the check on.
     * @exception JMXException
     */
    private void beanClash(Map beans) throws JMXException {
        for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
            JMXBeanInfo jmxBeanInfo = (JMXBeanInfo)beans.get(iter.next());
            if (beanList.containsObject(jmxBeanInfo.getObjectName())) {
                throw new JMXException("The object with the name [" +
                        jmxBeanInfo.getObjectName() + "] is already bound");
            }
            if (beanList.containsBindObject(jmxBeanInfo.getBindName())) {
                throw new JMXException("The object with the bind name [" +
                        jmxBeanInfo.getBindName() + "] is already bound");
            }
        }
    }
    
    
}
