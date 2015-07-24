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
 * Runner.java
 *
 * This class is responsible for instanciating the coadunation environment properly.
 */

package com.rift.coad;

// log 4 j imports
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.BasicConfigurator;

// coadunation imports
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.db.DBSourceManager;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.DeploymentManager;
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.deployment.bean.BeanManager;
import com.rift.coad.lib.deployment.bean.BeanConnector;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanManager;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanConnector;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;
import com.rift.coad.lib.deployment.webservice.WebServiceConnector;
import com.rift.coad.lib.httpd.HttpDaemon;
import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.loader.MasterClassLoader;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.transaction.TransactionDirector;

/**
 *
 * @author Brett Chaldecott
 */
public class Runner {
    
    /**
     * The implementation of the shut down hook. This object will get run when
     * this program is terminated.
     */
    public static class ShutdownHook extends Thread {
        /**
         * The default constructor of the shutdown hook.
         */
        public ShutdownHook() {
        }
        
        
        /**
         * This method will get called to shut down the coadunation base.
         */
        public void run() {
            // alert the waiting thread
            alert();
            synchronized (this){
                try {
                    wait();
                } catch (Exception ex) {
                    
                }
            }
        }
        
        
        /**
         * This method will alert the hook to the fact that this object is being
         * shut down.
         */
        private synchronized void alert() {
            notify();
        }
        
        /**
         * This method will monitor
         */
        public synchronized void monitor() {
            try {
                wait();
            } catch (Exception ex) {
                // do nothing
            }
        }
        
        /**
         * Notify the caller thread to inform them of complete shut down.
         */
        public synchronized void notifyOfCompletion() {
            notifyAll();
        }
    }
    
    // class constants
    public static final String RUNNER_USER = "runner_user";
    
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(Runner.class.getName());
    private ThreadsPermissionContainer permissionContainer = null;
    private UserStoreManager userStoreManager = null;
    private UserSessionManager sessionManager = null;
    private CoadunationThreadGroup threadGroup = null;
    private BeanManager beanManager = null;
    private JMXBeanManager jmxBeanManager = null;
    private WebServiceManager webServiceManager = null;
    private DeploymentManager deploymentManager = null;
    private HttpDaemon httpDaemon = null;
    
    /**
     * Creates a new instance of Main
     */
    public Runner() throws CoadException {
        // Validate the class loader
        System.out.println("Check the class loader");
        if (!(this.getClass().getClassLoader() instanceof 
                com.rift.coad.BaseClassLoader)) {
            log.error("Invalid class loader");
            System.exit(-1);
        }
        System.out.println("Try and init");
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    Runner.class);
            
            // instanciate the user permissions
            log.info("Init the master class loader");
            MasterClassLoader.init();
            
            // instanciate the user permissions
            log.info("Init thread permissions");
            permissionContainer =
                    new ThreadsPermissionContainer();
            ThreadsPermissionContainerAccessor.init(permissionContainer);
            log.info("Init session manager");
            SessionManager.init(permissionContainer);
            log.info("Init user store");
            userStoreManager = new UserStoreManager();
            UserStoreManagerAccessor.init(userStoreManager);
            log.info("Init user session manager");
            sessionManager = new UserSessionManager(
                    permissionContainer,userStoreManager);
            sessionManager.startCleanup();
            UserSessionManagerAccessor.init(sessionManager);
            log.info("Init login module");
            LoginManager.init(sessionManager,userStoreManager);
            
            // add a user to the session for the current thread
            log.info("Init roles");
            RoleManager.getInstance().startBackgroundThread();
            
            // setup a default user for the current thread
            log.info("Init the default user for the runner");
            Long threadId = new Long(Thread.currentThread().getId());
            permissionContainer.putSession(threadId,
                    new ThreadPermissionSession(threadId,userStoreManager.
                    getUserInfo(config.getString(RUNNER_USER))));
            
            // instanciate the thread manager
            log.info("Init thread group");
            threadGroup = new CoadunationThreadGroup(sessionManager,
                    userStoreManager);
            
            // init the interceptor factory
            log.info("Init the interceptor factory");
            InterceptorFactory.init(permissionContainer,sessionManager,
                userStoreManager);
            
            // setup the current thread class loader
            log.info("Init the naming director");
            NamingDirector.init(threadGroup);
            
            log.info("Init the transaction director");
            TransactionDirector.init();
            
            // instanciate the cache registry
            log.info("Init the cache registry");
            CacheRegistry.init(threadGroup);
            
            // instanciate the database sources
            log.info("Init data stources");
            DBSourceManager.init();
            
            // instanciate the bean manager
            log.info("Init coadunation beans");
            beanManager = new BeanManager(permissionContainer,
                    threadGroup);
            BeanConnector.init(beanManager);
            
            // instanciate the jmx bean manager
            log.info("Init JMX Beans");
            jmxBeanManager = new JMXBeanManager(permissionContainer,
                    threadGroup);
            JMXBeanConnector.init(jmxBeanManager);
            
            // instanciate the axis engine
            log.info("Init AXIS");
            AxisManager.init();
            
            // instanciate the web service manager
            log.info("Init Web Service management");
            webServiceManager = new WebServiceManager();
            WebServiceConnector.init(webServiceManager);
            
            // instanciate the thread manager
            log.info("Init Deployment Loader");
            deploymentManager = new DeploymentManager(
                    threadGroup,beanManager,jmxBeanManager, webServiceManager);
            
            // instanciate the http daemon
            log.info("Init Web Service HTTPD");
            httpDaemon = new HttpDaemon(threadGroup);
            
        } catch (Exception ex) {
            System.out.println("Failed to start coadunation : " + ex.getMessage());
            ex.printStackTrace(System.out);
            log.error("Failed to start coadunation : " + ex.getMessage(), ex);
            throw new CoadException(
                    "Failed start the Coadunation base because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will shut down the coadunation base.
     */
    public void shutdown() {
        try {
            log.info("Shutting down HTTPD");
            httpDaemon.shutdown();
            log.info("Shutting down deployment manager");
            deploymentManager.shutdown();
            log.info("Shutting down the cache registry");
            CacheRegistry.getInstance().shutdown();
            log.info("Stopping the transaction director");
            TransactionDirector.getInstance().stop();
            log.info("Shut down the naming director");
            NamingDirector.getInstance().shutdown();
            log.info("Terminating the local thread group");
            threadGroup.terminate();
            log.info("Terminate the back ground thread");
            RoleManager.getInstance().terminateBackgroundThread();
            log.info("Terminating the session manager");
            sessionManager.shutdown();
            
        } catch (Exception ex) {
            log.error("Shutdown failed : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * The main method responsible for starting the coadunation base.
     *
     * @param args the command line arguments
     */
    public static void main() {
        try {
            String logFile = System.getProperty("Log.File");
            if (logFile.endsWith("properties")) {
                System.out.println("Initing the log file from properties.");
                PropertyConfigurator.configure(logFile);
            } else if (logFile.endsWith("xml")) {
                System.out.println("Initing the log file from xml.");
                DOMConfigurator.configure(logFile);
            } else {
                System.out.println("Using the basic configuration.");
                BasicConfigurator.configure();
            }
            System.out.println("Start");
            Runner runner = new Runner();
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            System.out.println("Core initialization complete");
            System.out.println("Waiting for deployment to complete");
            shutdownHook.monitor();
            runner.shutdown();
            shutdownHook.notifyOfCompletion();
            log.info("Shut down complete");
        } catch (Exception ex) {
            System.out.println("Failed to run the Coadunation base [" +
                    ex.getMessage() + "]");
            ex.printStackTrace(System.out);
            System.exit(-1);
        }
    }
    
}
