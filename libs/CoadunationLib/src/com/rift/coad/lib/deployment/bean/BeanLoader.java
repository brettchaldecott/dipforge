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
 * BeanLoader.java
 *
 * This class is responsible for loading the coadunation beans in to memory from
 * the jar.
 */

package com.rift.coad.lib.deployment.bean;

// core java imports
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;

// logging import
import org.apache.log4j.Logger;

// coad imports
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.deployment.DeploymentThreadInfo;
import com.rift.coad.lib.bean.BeanWrapper;
import com.rift.coad.lib.bean.BeanThread;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.BasicThread;

/**
 * This class is responsible for loading the coadunation beans in to memory from
 * the jar file.
 *
 * @author Brett Chaldecott
 */
public class BeanLoader extends BasicThread {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(BeanLoader.class.getName());
    
    // the reference to the 
    private DeploymentLoader deploymentLoader = null;
    private Map beans = null;
    private ThreadsPermissionContainer permissions = null;
    private CoadunationThreadGroup threadGroup = null;
    private boolean successful = true;
    private Exception exception = null;
    
    /** 
     * Creates a new instance of BeanLoader.
     *
     * @param deploymentLoader The reference to the deployment loader.
     * @param permissions The permissions assigned to the various threads.
     * @param threadGroup The reference to the thread group.
     * @exception BeanException
     * @exception Exception
     */
    public BeanLoader(DeploymentLoader deploymentLoader,
            ThreadsPermissionContainer permissions, CoadunationThreadGroup threadGroup)
            throws BeanException,Exception {
        this.deploymentLoader = deploymentLoader;
        this.permissions = permissions;
        this.threadGroup = threadGroup.createThreadGroup();
        beans = new LinkedHashMap();
    }
    
    
    /**
     * This method finalizes the threads in this object.
     */
    protected void finalize() {
        stopThreads();
    }
    
    
    /**
     * This method loads the beans from the deployment file.
     *
     * @exception BeanException
     */
    public void process() {
        try {
            log.info("Load the Beans for [" + 
                deploymentLoader.getFile().getPath() + "]");
            Map beanList = deploymentLoader.getDeploymentInfo().getBeans();
            for (Iterator iter = beanList.keySet().iterator(); iter.hasNext();) {
                BeanInfo beanInfo = (BeanInfo)beanList.get(iter.next());
                BeanWrapper beanWrapper = new BeanWrapper(deploymentLoader,
                        beanInfo,permissions);
                beans.put(beanInfo.getBindName(),beanWrapper);
                if (beanWrapper.getSubObject() instanceof BeanRunnable) {
                    BeanThread beanThread = new BeanThread(
                            (BeanRunnable)beanWrapper.getSubObject());
                    threadGroup.addThread(beanThread,beanInfo.getUsername());
                    beanThread.start();
                }
                
                // loop through the bean thread information
                List threadList = beanInfo.getThreadInfoList();
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
            log.error("Failed to instanciate the bean : " + ex.getMessage(),ex);
            stopThreads();
            successful = false;
            exception = new BeanException("Failed to load the beans : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Terminate the processing of this object.
     */
    public void terminate() {
        
    }
    
    
    /**
     * This method is called to perform the unload on this bean loader object.
     */
    public void stopThreads() {
        try {
            if (threadGroup.isTerminated() == false) {
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
