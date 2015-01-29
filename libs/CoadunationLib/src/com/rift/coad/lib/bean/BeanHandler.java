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
 * BeanHandler.java
 *
 * The handler responsible for pre-processing the requests made on the
 * coadunation beans.
 */

package com.rift.coad.lib.bean;

// java core import
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.lib.ResourceReleasedException;
import com.rift.coad.lib.audit.AuditTrail;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.cache.KeySyncCache;
import com.rift.coad.lib.cache.KeySyncCacheManager;
import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.deployment.BeanInfo;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.lang.ClassLoader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

// logging import
import org.apache.log4j.Logger;
import org.objectweb.carol.jndi.enc.java.javaURLContextFactory;



/**
 * The handler responsible for pre-processing the requests made on the
 * coadunation beans.
 *
 * @author Brett Chaldecott
 */
public class BeanHandler implements InvocationHandler, CacheEntry {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(BeanHandler.class.getName());
    
    // class member variables
    private String id = null;
    private BeanInfo beanInfo = null;
    private String role = null;
    private Object subObject = null;
    private ThreadsPermissionContainer permissions = null;
    private ClassLoader classLoader = null;
    private Date touchTime = null;
    private boolean released = false;
    private BeanCache beanCacheRef = null;
    private TransactionBeanCache transactionBeanCacheRef = null;
    private ProxyCache proxyCacheRef = null;
    private TransactionProxyCache transactionProxyCacheRef = null;
    private KeySyncCache keySyncCache = null;
    private Context context = null;
    private UserTransactionWrapper utw = null;
    private AuditTrail auditTrail = null;
    
    /** Creates a new instance of BeanHandler */
    public BeanHandler(BeanInfo beanInfo, Object subObject, String role,
            ThreadsPermissionContainer permissions,
            ClassLoader classLoader)
            throws BeanException {
        try {
            touch();
            id = RandomGuid.getInstance().getGuid();
            this.beanInfo = beanInfo;
            this.subObject = subObject;
            this.role = role;
            this.permissions = permissions;
            this.classLoader = classLoader;
            context = new InitialContext();
            utw = new UserTransactionWrapper();
            auditTrail = AuditTrail.getAudit(subObject.getClass());
        } catch (Exception ex) {
            throw new BeanException("Failed to instanciate the handler for ["
                    + subObject.getClass().getName() + "] because : "
                    + ex.getMessage(),ex);
        }
    }
    
    /**
     * The invocation handler.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
    throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Calling method [" + method.toString() + "]");
        }
        
        // check if this object has been released
        if (isReleased()) {
            throw new ResourceReleasedException(
                    "This beans resources have been released.");
        }
        
        boolean validated = false;
        touch();
        boolean success = true;
        try {
            Validator.validate(this.getClass(),role);
            validated = true;
            permissions.pushRole(role);
            
            // retrieve the method information
            Method subMethod = subObject.getClass().getMethod(method.getName(),
                    method.getParameterTypes());
            
            // create patterns
            Pattern addPattern = Pattern.compile(BeanPattern.ADD_PATTERN);
            Pattern findPattern = Pattern.compile(BeanPattern.FIND_PATTERN);
            Pattern removePattern = Pattern.compile(BeanPattern.REMOVE_PATTERN);
            
            // check for bean pattern methods
            if (beanInfo.getCacheResults() && !beanInfo.getTransaction() &&
                    addPattern.matcher(method.getName()).find() &&
                    subMethod.getReturnType().isInterface() &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class)) {
                return addMethod(method,args);
            } else if (beanInfo.getCacheResults() && beanInfo.getTransaction() &&
                    addPattern.matcher(method.getName()).find() &&
                    subMethod.getReturnType().isInterface() &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class)) {
                return transactionAddMethod(method,args);
            } else if (beanInfo.getCacheResults() &&
                    !beanInfo.getTransaction() &&
                    findPattern.matcher(method.getName()).find() &&
                    subMethod.getReturnType().isInterface() &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class) &&
                    args.length == 1) {
                return findMethod(method,args);
            } else if (beanInfo.getCacheResults() &&
                    beanInfo.getTransaction() &&
                    findPattern.matcher(method.getName()).find() &&
                    subMethod.getReturnType().isInterface() &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class) &&
                    args.length == 1) {
                return transactionFindMethod(method,args);
            } else if (beanInfo.getCacheResults() &&
                    !beanInfo.getTransaction() &&
                    removePattern.matcher(method.getName()).find()  &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class) &&
                    args.length == 1) {
                return removeMethod(method,args);
            } else if (beanInfo.getCacheResults() &&
                    beanInfo.getTransaction() &&
                    removePattern.matcher(method.getName()).find()  &&
                    !ClassUtil.testForParent(subMethod.getReturnType(),
                    java.io.Serializable.class) &&
                    args.length == 1) {
                return transactionRemoveMethod(method,args);
            }
            
            // make the call
            Object result = null;
            try {
                if (beanInfo.getTransaction()) {
                    utw.begin();
                }
                result = subMethod.invoke(subObject,args);
                // deal with none bean pattern method caching
                if (subMethod.getReturnType().isInterface() &&
                        !ClassUtil.testForParent(subMethod.getReturnType(),
                        java.io.Serializable.class)) {
                    try {
                        BeanHandler handler = new BeanHandler(beanInfo, result,
                                beanInfo.getRole(),permissions,classLoader);
                        Object newProxy = (Object)Proxy.newProxyInstance(
                                classLoader,
                                result.getClass().getInterfaces(),handler);
                        ProxyCache proxyCache = getProxyCache();
                        proxyCache.addCacheEntry(beanInfo.getCacheTimeout(), newProxy,
                                handler);
                        utw.commit();
                        return newProxy;
                    } catch (Exception ex) {
                        log.error("Failed to create the proxy return object : " +
                                ex.getMessage(),ex);
                        throw new BeanException(
                                "Failed to create the proxy return object : " +
                                ex.getMessage(),ex);
                    }
                }
                utw.commit();
                // return the result if not an interface
                return result;
            } catch (Throwable ex) {
                throw ex;
            } finally {
                try {
                    utw.release();
                } catch (Exception ex2) {
                    log.error("Failed to rollback the changes : " +
                            ex2.getMessage(),ex2);
                }
            }
        } catch (InvocationTargetException ex) {
            log.error("An exception was thrown by the sub object : " +
                    ex.getMessage(),ex);
            auditTrail.logEvent(method.getName(),ex.getTargetException());
            success = false;
            throw ex.getTargetException();
        } catch (Throwable ex) {
            log.error("An exception was thrown by the sub object : " +
                    ex.getMessage(),ex);
            auditTrail.logEvent(method.getName(),ex);
            success = false;
            throw ex;
        } finally {
            if (success) {
                auditTrail.logEvent(method.getName());
            }
            if (validated == true) {
                permissions.popRole(role);
            }
        }
    }
    
    
    /**
     * This method will return true if the date is older than the given expiry
     * date.
     *
     * @return TRUE if expired FALSE if not.
     * @param expiryDate The expiry date to perform the check with.
     */
    public boolean isExpired(Date expiryDate) {
        return touchTime.getTime() < expiryDate.getTime();
    }
    
    
    /**
     * This method is called by the cache when releasing this object.
     */
    public synchronized void cacheRelease() {
        if (subObject instanceof Resource) {
            ((Resource)subObject).releaseResource();
        }
        released = true;
    }
    
    
    /**
     * This method is called to update the touch time of the base object.
     */
    public synchronized void touch() {
        touchTime = new Date();
    }
    
    
    /**
     * This method returns the id of this bean handler
     *
     * @return The string containing the id of this handler
     */
    protected String getId() {
        return id;
    }
    
    /**
     * This method returns the hash code of this object.
     */
    public int hashCode() {
        return id.hashCode();
    }
    
    
    /**
     * This method determines if the bean handlers are the same.
     *
     * @return TRUE if the same, FALSE if not.
     * @param rhs The right hand side.
     */
    public boolean equals(Object rhs) {
        if (!(rhs instanceof BeanHandler)) {
            return false;
        }
        return id.equals(((BeanHandler)rhs).getId());
    }
    
    
    /**
     * This method checks the status of the released flag.
     *
     * @return TRUE if released, FALSE if not.
     */
    private synchronized boolean isReleased() {
        return released;
    }
    
    
    /**
     * This method will deal with the add call.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object addMethod(Method subMethod, Object[] args) throws Throwable {
        
        // make the call
        Object result = subMethod.invoke(subObject,args);
        if (result == null)
        {
            return result;
        }
        try {
            BeanHandler handler = new BeanHandler(beanInfo, result,
                    beanInfo.getRole(),permissions,classLoader);
            Object proxy = (Object)Proxy.newProxyInstance(
                    classLoader,
                    result.getClass().getInterfaces(),handler);
            if (result instanceof ResourceIndex) {
                BeanCache beanCache = getBeanCache();
                beanCache.addCacheEntry(beanInfo.getCacheTimeout(),
                        ((ResourceIndex)result).getPrimaryKey(), result,
                        proxy, handler);
            } else {
                ProxyCache proxyCache = getProxyCache();
                proxyCache.addCacheEntry(beanInfo.getCacheTimeout(), proxy,
                        handler);
            }
            return proxy;
        } catch (Exception ex) {
            log.error("Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will deal with the add within a transaction call.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object transactionAddMethod(Method subMethod, Object[] args) throws
            Throwable {
        try {
            utw.begin();
            Object result = subMethod.invoke(subObject,args);
            if (result == null)
            {
                    try {
                        utw.commit();
                    } catch (Throwable ex2) {
                        log.error("Failed to commit the changes : " +
                                ex2.getMessage(),ex2);
                    }
                return result;
            }
            BeanHandler handler = new BeanHandler(beanInfo, result,
                    beanInfo.getRole(),permissions,classLoader);
            Object proxy = (Object)Proxy.newProxyInstance(
                    classLoader,
                    result.getClass().getInterfaces(),handler);
            
            if (result instanceof ResourceIndex) {
                
                TransactionBeanCache transactionBeanCache =
                        getTransactionBeanCache();
                System.out.println("Add a new entry to the cache [" +
                        beanInfo.getCacheTimeout() + "] [" +
                        ((ResourceIndex)result).getPrimaryKey() + "]");
                transactionBeanCache.addCacheEntry(beanInfo.getCacheTimeout(),
                        ((ResourceIndex)result).getPrimaryKey(), result,
                        proxy, handler);
            } else {
                System.out.println("Add a new entry to the cache [" +
                        beanInfo.getCacheTimeout() + "]");
                TransactionProxyCache transactionProxyCache =
                        getTransactionProxyCache();
                transactionProxyCache.addCacheEntry(beanInfo.getCacheTimeout(),
                        proxy, handler);
            }
            utw.commit();
            return proxy;
        } catch (Throwable ex) {
            log.error("Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
        } finally {
            try {
                utw.release();
            } catch (Exception ex2) {
                log.error("Failed to rollback the changes : " +
                        ex2.getMessage(),ex2);
            }
        }
    }
    
    
    /**
     * This method will deal with a find call.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object findMethod(Method subMethod, Object[] args) throws Throwable {
        
        // synchronize this method based on the key that is passed in.
        Object syncObj = getKeySyncCache().getKeySync(args[0]);
        synchronized(syncObj) {
            BeanCache beanCache = null;
            try {
                beanCache = getBeanCache();
                BeanCacheEntry cacheEntry =
                        beanCache.getEntry(args[0]);
                if (cacheEntry != null) {
                    if (cacheEntry.getProxy() == null){
                        BeanHandler handler = new BeanHandler(beanInfo,
                                cacheEntry.getWrappedObject(),
                                beanInfo.getRole(),permissions,classLoader);
                        cacheEntry.setBeanHandler(handler);
                        Object proxy = (Object)Proxy.newProxyInstance(
                                classLoader,
                                cacheEntry.getWrappedObject().getClass().
                                getInterfaces(),handler);
                        cacheEntry.setProxy(proxy);
                    }
                    return cacheEntry.getProxy();
                }
            } catch (Exception ex) {
                log.error("Failed to check the cache : " +
                        ex.getMessage(),ex);
                throw new BeanException(
                        "Failed to check the cache : " +
                        ex.getMessage(),ex);
            }
            
            Object result = subMethod.invoke(subObject,args);
            if (result == null)
            {
                return result;
            }
            try {
                BeanHandler handler = new BeanHandler(beanInfo, result,
                        beanInfo.getRole(),permissions,classLoader);
                Object proxy = (Object)Proxy.newProxyInstance(
                        classLoader,
                        result.getClass().getInterfaces(),handler);
                beanCache.addCacheEntry(beanInfo.getCacheTimeout(),
                        args[0], result, proxy, handler);
                return proxy;
            } catch (Exception ex) {
                log.error("Failed to create the proxy return object : " +
                        ex.getMessage(),ex);
                throw new BeanException(
                        "Failed to create the proxy return object : " +
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method finds a object within a transactional scope.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object transactionFindMethod(Method subMethod, Object[] args) throws
            Throwable {
        try {
            utw.begin();
            TransactionBeanCache transactionBeanCache = null;
            transactionBeanCache = getTransactionBeanCache();
            BeanCacheEntry cacheEntry =
                    transactionBeanCache.getCacheEntry(args[0]);
            if (cacheEntry != null) {
                if (cacheEntry.getProxy() == null){
                    BeanHandler handler = new BeanHandler(beanInfo,
                            cacheEntry.getWrappedObject(),
                            beanInfo.getRole(),permissions,classLoader);
                    cacheEntry.setBeanHandler(handler);
                    Object proxy = (Object)Proxy.newProxyInstance(
                            classLoader,
                            cacheEntry.getWrappedObject().getClass().
                            getInterfaces(),handler);
                    cacheEntry.setProxy(proxy);
                }
                utw.commit();
                return cacheEntry.getProxy();
            }
            Object result = null;
            result = subMethod.invoke(subObject,args);
            if (result == null)
            {
                utw.commit();
                return result;
            }
            BeanHandler handler = new BeanHandler(beanInfo, result,
                    beanInfo.getRole(),permissions,classLoader);
            Object proxy = (Object)Proxy.newProxyInstance(
                    classLoader,
                    result.getClass().getInterfaces(),handler);
            transactionBeanCache.addCacheEntry(beanInfo.getCacheTimeout(),
                    args[0], result, proxy, handler);
            
            utw.commit();
            return proxy;
        } catch (Throwable ex) {
            log.error("Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to create the proxy return object : " +
                    ex.getMessage(),ex);
        } finally {
            try {
                utw.release();
            } catch (Exception ex2) {
                log.error("Failed to rollback the changes : " +
                        ex2.getMessage(),ex2);
            }
        }
    }
    
    
    /**
     * This method will deal with a find call.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object removeMethod(Method subMethod, Object[] args) throws Throwable {
        // synchronize this method based on the key that is passed in.
        Object syncObj = getKeySyncCache().getKeySync(args[0]);
        synchronized(syncObj) {
            Object result = subMethod.invoke(subObject,args);
            
            try {
                BeanCache beanCache = getBeanCache();
                beanCache.removeCacheEntry(args[0]);
            } catch (Exception ex) {
                log.error("Failed to remove bean cache entry : " +
                        ex.getMessage(),ex);
                throw new BeanException(
                        "Failed to remove bean cache entry : " +
                        ex.getMessage(),ex);
            }
            return result;
        }
    }
    
    
    /**
     * This method will deal with a find call.
     *
     * @return The results of the add call.
     * @param subMethod The method to invoke the call on.
     * @param args The arguments to make the call with.
     * @exception Throwable
     */
    private Object transactionRemoveMethod(Method subMethod, Object[] args)
    throws Throwable {
        try {
            utw.begin();
            Object result = null;
            result = subMethod.invoke(subObject,args);
            TransactionBeanCache transactionBeanCache =
                    getTransactionBeanCache();
            transactionBeanCache.removeCacheEntry(args[0]);
            utw.commit();
            return result;
        } catch (Throwable ex) {
            log.error("Failed to remove bean cache entry : " +
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to remove bean cache entry : " +
                    ex.getMessage(),ex);
        } finally {
            try {
                utw.release();
            } catch (Exception ex2) {
                log.error("Failed to rollback the changes : " +
                        ex2.getMessage(),ex2);
            }
        }
    }
    
    
    /**
     * This method returns the cached reference to the bean cache object.
     *
     * @return The reference to the bean cache.
     * @exception Throwable
     */
    private synchronized BeanCache getBeanCache() throws Throwable {
        if (beanCacheRef == null) {
            BeanCacheManager manager = (BeanCacheManager)
            CacheRegistry.getInstance().
                    getCache(BeanCacheManager.class);
            beanCacheRef = manager.getBeanCache(subObject);
        }
        return beanCacheRef;
    }
    
    
    /**
     * This method returns the cached reference to the bean cache object.
     *
     * @return The reference to the bean cache.
     * @exception Throwable
     */
    private synchronized TransactionBeanCache getTransactionBeanCache() throws
            Throwable {
        if (transactionBeanCacheRef == null) {
            TransactionBeanCacheManager manager = (TransactionBeanCacheManager)
            CacheRegistry.getInstance().
                    getCache(TransactionBeanCacheManager.class);
            transactionBeanCacheRef = manager.getBeanCache(subObject);
        }
        return transactionBeanCacheRef;
    }
    
    
    /**
     * This method returns the cached reference to the bean cache object.
     *
     * @return The reference to the bean cache.
     * @exception Throwable
     */
    private synchronized ProxyCache getProxyCache() throws Throwable {
        if (proxyCacheRef == null) {
            proxyCacheRef =
                    (ProxyCache)CacheRegistry.getInstance().
                    getCache(ProxyCache.class);
        }
        return proxyCacheRef;
    }
    
    
    /**
     * This method returns the cached reference to the bean cache object.
     *
     * @return The reference to the bean cache.
     * @exception Throwable
     */
    private synchronized TransactionProxyCache getTransactionProxyCache() throws
            Throwable {
        if (transactionProxyCacheRef == null) {
            transactionProxyCacheRef =
                    (TransactionProxyCache)CacheRegistry.getInstance().
                    getCache(TransactionProxyCache.class);
        }
        return transactionProxyCacheRef;
    }
    
    
    /**
     * This method returns a reference to the key sync cache object.
     *
     * @return The reference to the key sync cache object.
     * @exception Throwable
     */
    private synchronized KeySyncCache getKeySyncCache() throws Throwable {
        try {
            if (keySyncCache == null) {
                KeySyncCacheManager keySyncCacheManager =
                        (KeySyncCacheManager)CacheRegistry.getInstance().
                        getCache(KeySyncCacheManager.class);
                keySyncCache = keySyncCacheManager.getKeySyncCache(this);
            }
            return keySyncCache;
        } catch (Exception ex) {
            log.error("Failed to retrieve the key sync cache object ref : " +
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to retrieve the key sync cache object ref : " +
                    ex.getMessage(),ex);
        }
    }
    
    
}
