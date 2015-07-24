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
 * JMXBeanLoader.java
 *
 * This object is responsible for loading the JMX Bean into memory from the 
 * deployment loader passed to it.
 */

package com.rift.coad.lib.deployment.jmxbean;

// import 
import javax.management.ObjectName;
import javax.management.MBeanServer;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;

// logging import
import org.apache.log4j.Logger;

// coad imports
import com.rift.coad.lib.bean.BeanWrapper;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.bean.BeanThread;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.JMXBeanInfo;
import com.rift.coad.lib.deployment.DeploymentThreadInfo;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.BasicThread;
        
/**
 * The JMX Bean loader is responsibe for loading the beans contained in a 
 * Coadunation jar file.
 *
 * @author Brett Chaldecott
 */
public class JMXBeanLoader extends BasicThread {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(JMXBeanLoader.class.getName());
    
    // The classes memember variables
    private MBeanServer beanServer = null;
    private DeploymentLoader deploymentLoader = null;
    private ThreadsPermissionContainer permissions = null;
    private Map beans = null;
    private CoadunationThreadGroup threadGroup = null;
    private boolean successful = true;
    private Exception exception = null;
    
    /** 
     * Creates a new instance of JMXBeanLoader loader.
     *
     * @param beanServer The reference to the bean server.
     * @param deploymentLoader The reference to the deployment loader.
     * @param permissions The threads permissions container.
     * @param threadGroup The reference to the thread group.
     * @exception JMXException
     * @exception Exception
     */
    public JMXBeanLoader(MBeanServer beanServer, 
            DeploymentLoader deploymentLoader,
            ThreadsPermissionContainer permissions,
            CoadunationThreadGroup threadGroup) throws JMXException, Exception {
        this.beanServer = beanServer;
        this.deploymentLoader = deploymentLoader;
        this.permissions = permissions;
        this.threadGroup = threadGroup.createThreadGroup();
        beans = new LinkedHashMap();
        
    }
    
    
    /**
     * The finalize method for the jmx bean loader.
     */
    protected void finalize() {
        stopThreads();
    }
    
    
    /**
     * The method that process the request
     */
    public void process() {
        try {
            loadBeans();
            registerBeans();
        } catch (Exception ex) {
            log.error("Failed to load the JMX Beans because : " + 
                    ex.getMessage(),ex);
            successful = false;
            exception = ex;
        }
    }
    
    /**
     * Load the beans into memory.
     *
     * @exception JMXException
     */
    private void loadBeans() throws JMXException {
        try {
            log.info("Load the JMX Beans for [" + 
                    deploymentLoader.getFile().getPath() + "]");
            Map beanList = deploymentLoader.getDeploymentInfo().getJmxBeans();
            for (Iterator iter = beanList.keySet().iterator(); iter.hasNext();) {
                JMXBeanInfo jmxBeanInfo = (JMXBeanInfo)beanList.get(iter.next());
                BeanWrapper beanWrapper = 
                        new BeanWrapper(deploymentLoader, jmxBeanInfo,
                        permissions);
                beans.put(jmxBeanInfo.getObjectName(),beanWrapper);
                if (beanWrapper.getSubObject() instanceof BeanRunnable) {
                    BeanThread beanThread = new BeanThread(
                            (BeanRunnable)beanWrapper.getSubObject());
                    threadGroup.addThread(beanThread,jmxBeanInfo.getUsername());
                    beanThread.start();
                }
                
                // loop through the bean thread information
                List threadList = jmxBeanInfo.getThreadInfoList();
                for (int index = 0; index < threadList.size(); index++) {
                    DeploymentThreadInfo threadInfo = 
                            (DeploymentThreadInfo)threadList.get(index);
                    threadGroup.startThreads(
                            deploymentLoader.getClass(threadInfo.getClassName())
                            ,threadInfo.getUsername(),
                            (int)threadInfo.getThreadNumber());
                }
            }
        } catch (Exception ex) {
            stopThreads();
            throw new JMXException("Failed to load the beans : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The operator method responsible for registering the beans with the JMX
     * bean server.
     *
     * @exception JMXException
     */
    private void registerBeans() throws JMXException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Register the Beans for [" + 
                        deploymentLoader.getFile().getPath() + "]");
            }
            
            for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
                String objectName = (String)iter.next();
                log.info("Register the jmx bean [" + 
                         objectName + "]");
                BeanWrapper beanWrapper = (BeanWrapper)beans.get(objectName);
                beanServer.registerMBean(beanWrapper.getSubObject(),
                        new ObjectName(objectName));
            }
        } catch (Exception ex) {
            throw new JMXException("Failed to register the beans : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to un-register beans from the bean server.
     */
    public void unRegisterBeans() throws JMXException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Un-register the Beans for [" + 
                        deploymentLoader.getFile().getPath() + "]");
            }
            
            for (Iterator iter = beans.keySet().iterator(); iter.hasNext();) {
                String objectName = (String)iter.next();
                log.info("Un-register the jmx bean [" + 
                         objectName + "]");
                beanServer.unregisterMBean(
                        new ObjectName(objectName));
            }
        } catch (Exception ex) {
            throw new JMXException("Failed to un-register the beans : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * This method stops the bean processing
     */
    public void stopThreads() {
        try {
            if (threadGroup.isTerminated()  == false) {
                threadGroup.terminate();
            }
        } catch(Exception ex) {
            log.error("Failed to unload the beans [" + 
                    deploymentLoader.getFile().getPath() + "] because : " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns the loaded beans.
     *
     * @return The list of loaded beans.
     */
    public Map getBeans() {
        return beans;
    }
    
    
    /**
     * This method returns true if the loading was successfull and false
     * otherwise.
     */
    public boolean wasSucessfull() {
        return successful;
    }
    
    
    /**
     * The exception that caused the problem
     */
    public Exception getException() {
        return exception;
    }

}
