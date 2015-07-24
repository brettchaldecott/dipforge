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
 * BeanWrapper.java
 *
 * This object is responsible for wrapping a bean and loading it into memory.
 */

// package definition
package com.rift.coad.lib.bean;

// java imports
import java.lang.reflect.Proxy;
import java.lang.reflect.Constructor;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.security.ThreadsPermissionContainer;

/**
 * This object is responsible for wrapping a bean and loading it into memory.
 *
 * @author Brett Chaldecott
 */
public class BeanWrapper {
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(BeanWrapper.class.getName());
    
    // The classes member variables
    private DeploymentLoader loader = null;
    private ThreadsPermissionContainer permissions = null;
    private String bindName = null;
    private BeanHandler handler = null;
    private Object proxy = null;
    private java.rmi.Remote tie = null;
    private Object subObject = null;
    private Class interfaceRef = null;
    
    
    /** 
     * Creates a new instance of BeanWrapper 
     *
     * @param deploymentLoader The reference to the deployment loader.
     * @param beanInfo The reference to the bean information object.
     * @exception BeanException
     */
    public BeanWrapper(DeploymentLoader deploymentLoader, BeanInfo beanInfo,
            ThreadsPermissionContainer permissions) 
        throws BeanException {
        try {
            this.loader = deploymentLoader;
            this.permissions = permissions;
            bindName = beanInfo.getBindName();
            subObject = deploymentLoader.getClassLoader().
                    loadClass(beanInfo.getClassName()).newInstance();
            interfaceRef = deploymentLoader.getClassLoader().
                    loadClass(beanInfo.getInterfaceName());
            handler = new BeanHandler(beanInfo, subObject, 
                    beanInfo.getRole(),permissions,
                    deploymentLoader.getClassLoader());
            proxy = (Object)Proxy.newProxyInstance(
                    deploymentLoader.getClassLoader(),
                    new Class[] {deploymentLoader.getClass(
                            beanInfo.getInterfaceName())},handler);
            tie = loadTie(beanInfo, subObject);
        } catch (Exception ex) {
            log.error("Failed to instanciate the bean wrapper : " 
                    + ex.getMessage(),ex);
            throw new BeanException("Failed to instanciate the bean wrapper : " 
                    + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * The bind name that will be used to search for this proxy object or
     * container object.
     *
     * @return The string containing the bind information.
     */
    public String getBindName() {
        return bindName;
    }
    
    
    /**
     * This method returns the reference to the proxy object.
     *
     * @return The reference to the proxy object
     */
    public Object getProxy() {
        return proxy;
    }
    
    
    /**
     * This method returns the reference to the tie class.
     *
     * @return The reference to the tie object.
     */
    public java.rmi.Remote getTie() {
        return tie;
    }
    
    
    /**
     * This method returns the reference to the sub object.
     *
     * @return The reference to the sub object.
     */
    public Object getSubObject() {
        return subObject;
    }
    
    
    /**
     * The interface this class must run as.
     *
     * @return The class this object must run as.
     */
    public Class getInterface() {
        return interfaceRef;
    }
    
    
    /**
     * This method loads the tie class.
     *
     * @return The reference to the loaded tie or null if it does not inherit
     *          from remote.
     * @param beanInfo The information 
     * @param subObject The sub object uppon which all the call will be made.
     */
    private java.rmi.Remote loadTie(BeanInfo beanInfo, Object subObject) 
            throws BeanException {
        if (!(subObject instanceof java.rmi.Remote)) {
            return null;
        }
        String className = subObject.getClass().getName() + 
                BeanPattern.TIE_SUFFIX;
        try {
            Class classRef = loader.getClassLoader().loadClass(className);
            // note: Must be exact matching class when doing the constructor
            // look up. It does not take inheritance into account.
            Class[] parameterTypes = new Class[] {
                    ClassLoader.class,
                    subObject.getClass(), String.class, 
                    ThreadsPermissionContainer.class,
                    BeanInfo.class};
            Constructor constructor  = classRef.getConstructor(parameterTypes);
            return (java.rmi.Remote)constructor.newInstance(
                    loader.getClassLoader(),subObject,beanInfo.getRole(),
                    permissions,beanInfo);
        } catch (Exception ex) {
            throw new BeanException("Failed to load the class [" + className 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
}
