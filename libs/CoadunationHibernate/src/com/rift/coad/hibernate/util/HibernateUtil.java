/*
 * CoadunationHibernate: The hibernate configuration.
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
 * HibernateUtil.java
 */

package com.rift.coad.hibernate.util;

// java imports
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


// logging import
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * This class sets up a hibernate SessionFactory.
 */
public class HibernateUtil implements XAResource {
    
    // class constants
    public final static String RESOURCES_CONFIG = "hibernate_resource_config";
    public final static String HIBERNATE_SQL = "hibernate_sql";
    public final static String DB_SOURCE = "db_datasource";
    public final static String HBM2DDL = "hibernate_hbm2ddl_auto";
    public final static String TRANSACTION_TIMEOUT = "transaction_timeout";
    public final static int DEFAULT_TRANSACTION_TIMEOUT = 0;
    
    // class singleton
    private static Map singletons = new HashMap();
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(HibernateUtil.class.getName());
    
    // class private member variables
    private int timeout = 0;
    private Context context = null;
    private SessionFactory sessionFactory = null;
    private Map sessions = new ConcurrentHashMap();
    private ThreadLocal currentSession = new ThreadLocal();
    
    
    /**
     * The constructor of the hibernate util object.
     *
     * @param configId The identifier of the configuration.
     * @exception MessageServiceException
     */
    private HibernateUtil(Class configId) throws HibernateUtilException {
        try {
            // retrieve the initial context
            context = new InitialContext();
            
            // Retrieve the configuration for the message service implementation
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance()
                    .getConfig(configId);
            
            // retrieve the default transaction timeout
            timeout = (int)coadConfig.getLong(TRANSACTION_TIMEOUT,
                    DEFAULT_TRANSACTION_TIMEOUT);
            
            // switch off jdbc 2 support
            org.hibernate.cfg.Configuration config = null;
            if (coadConfig.getBoolean("JDBC2_SUPPORT",true))
            {
                // init the configuration for the hibernate object.
                config = new
                    org.hibernate.cfg.Configuration()
                    .configure(coadConfig.getString(RESOURCES_CONFIG))
                    .setProperty("hibernate.dialect",
                    coadConfig.getString("db_dialect"))
                    .setProperty("hibernate.connection.provider_class",
                    "org.hibernate.connection.DatasourceConnectionProvider")
                    .setProperty("hibernate.connection.datasource",
                    coadConfig.getString(DB_SOURCE))
                    .setProperty("hibernate.connection.release_mode","after_transaction")
                    //.setProperty("hibernate.transaction.auto_close_session","true")
                    .setProperty("hibernate.transaction.flush_before_completion","true")
                    .setProperty("hibernate.current_session_context_class","jta")
                    .setProperty("hibernate.transaction.factory_class",
                    "org.hibernate.transaction.JTATransactionFactory")
                    .setProperty("jta.UserTransaction",
                    "java:comp/UserTransaction")
                    .setProperty("hibernate.transaction.manager_lookup_class",
                    "org.hibernate.transaction.JOTMTransactionManagerLookup")
                    //.setProperty("hibernate.connection.autocommit","false")
                    //.setProperty("hibernate.cache.provider_class",
                    //"org.hibernate.cache.NoCacheProvider")
                    .setProperty("hibernate.show_sql",
                    coadConfig.getString(HIBERNATE_SQL,"false"))
                    .setProperty("hibernate.hbm2ddl.auto",
                    coadConfig.getString(HBM2DDL,"update"));
            }
            else
            {
                // init the configuration for the hibernate object.
                // without JDBC 2 driver support
                config = new
                    org.hibernate.cfg.Configuration()
                    .configure(coadConfig.getString(RESOURCES_CONFIG))
                    .setProperty("hibernate.dialect",
                    coadConfig.getString("db_dialect"))
                    .setProperty("hibernate.connection.provider_class",
                    "org.hibernate.connection.DatasourceConnectionProvider")
                    .setProperty("hibernate.connection.datasource",
                    coadConfig.getString(DB_SOURCE))
                    .setProperty("hibernate.connection.release_mode","after_transaction")
                    //.setProperty("hibernate.transaction.auto_close_session","true")
                    .setProperty("hibernate.transaction.flush_before_completion","true")
                    .setProperty("hibernate.current_session_context_class","jta")
                    .setProperty("hibernate.transaction.factory_class",
                    "org.hibernate.transaction.JTATransactionFactory")
                    .setProperty("jta.UserTransaction",
                    "java:comp/UserTransaction")
                    .setProperty("hibernate.transaction.manager_lookup_class",
                    "org.hibernate.transaction.JOTMTransactionManagerLookup")
                    //.setProperty("hibernate.connection.autocommit","false")
                    //.setProperty("hibernate.cache.provider_class",
                    //"org.hibernate.cache.NoCacheProvider")
                    //.setProperty("hibernate.jdbc.batch_size","0")
                    //.setProperty("hibernate.jdbc.use_scrollable_resultsets","false")
                    .setProperty("hibernate.show_sql",
                    coadConfig.getString(HIBERNATE_SQL,"false"))
                    .setProperty("hibernate.hbm2ddl.auto",
                    coadConfig.getString(HBM2DDL,"update"));
            }
            
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory " +
                    "creation failed: " + ex.getMessage(),ex);
            throw new HibernateUtilException("Initial SessionFactory " +
                    "creation failed: " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Configures up hibernate programmatically using Coadunations configuration
     * file.
     *
     * @return The reference to the hibernate util singleton.
     * @param configId The id of the configuration.
     * @excepiton MessageServiceException
     */
    public synchronized static HibernateUtil getInstance(Class configId) throws 
            HibernateUtilException {
        HibernateUtil singleton = null;
        if (!singletons.containsKey(configId)) {
            singleton = new HibernateUtil(configId);
            singletons.put(configId,singleton);
        } else {
            singleton = (HibernateUtil)singletons.get(configId);
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the session factory object.
     *
     * @return Returns the current session for this thread.
     * @exception 
     */
    public Session getSession() throws HibernateUtilException {
        try {
            Transaction transaction = getTransaction();
            transaction.enlistResource(this);
            return (Session)currentSession.get();
        } catch (HibernateUtilException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the current session for this thread : " 
                    + ex.getMessage(),ex);
            throw new HibernateUtilException(
                    "Failed to retrieve the current session for this thread : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for handling the committing of a transaction
     * identified by the xid.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        if (this.sessions.containsKey(xid)) {
            Session session = (Session)sessions.get(xid);
            sessions.remove(xid);
            //try {
            //    session.connection().setAutoCommit(true);
            //} catch (Exception ex) {
            //    log.error("Failed to reset the auto commit flag on the " +
            //            "connection : " + ex.getMessage(),ex);
            //}
        }
    }
    
    
    /**
     * The resource manager has dissociated this object from the transaction.
     *
     * @param xid The id of the transaction that is getting ended.
     * @param flags The flags associated with this operation.
     * @exception XAException
     */
    public void end(Xid xid, int flags) throws XAException {
    }
    
    
    /**
     * The transaction has been completed and must be forgotten.
     *
     * @param xid The id of the transaction to forget.
     * @exception XAException
     */
    public void forget(Xid xid) throws XAException {
        if (this.sessions.containsKey(xid)) {
            Session session = (Session)sessions.get(xid);
            sessions.remove(xid);
            //try {
            //    session.connection().setAutoCommit(true);
            //} catch (Exception ex) {
            //    log.error("Failed to reset the auto commit flag on the " +
            //            "connection : " + ex.getMessage(),ex);
            //}
        }
    }
    
    
    /**
     * This method returns the transaction timeout for this object.
     *
     * @return The int containing the transaction timeout.
     * @exception XAException
     */
    public int getTransactionTimeout() throws XAException {
        return timeout;
    }
    
    
    /**
     * This method returns true if this object is the resource manager getting
     * queried.
     *
     * @return TRUE if this is the resource manager, FALSE if not.
     * @param xaResource The resource to perform the check against.
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
     * @param flags The flags
     * @exception XAException
     */
    public Xid[] recover(int flags) throws XAException {
        return null;
    }
    
    
    /**
     * This method is called to roll back the specified transaction.
     *
     * @param xid The id of the transaction to roll back.
     * @exception XAException
     */
    public void rollback(Xid xid) throws XAException {
        if (this.sessions.containsKey(xid)) {
            Session session = (Session)sessions.get(xid);
            sessions.remove(xid);
            //try {
            //    session.connection().setAutoCommit(true);
            //} catch (Exception ex) {
            //    log.error("Failed to reset the auto commit flag on the " +
            //            "connection : " + ex.getMessage(),ex);
            //}
            
        }
    }
    
    
    /**
     * This method sets the transaction timeout for this resource manager.
     *
     * @return TRUE if the transaction timeout can be set successfully.
     * @param transactionTimeout The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int transactionTimeout) throws 
            XAException {
        timeout = transactionTimeout;
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param flags The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int i) throws XAException {
        Session session = null;
        if (this.sessions.containsKey(xid)) {
            session = (Session)sessions.get(xid);
        } else {
            try {
                session = sessionFactory.openSession();
                //if (session.connection().getAutoCommit()) {
                //    session.connection().setAutoCommit(false);
                //}
                sessions.put(xid,session);
            } catch (Exception ex) {
                log.error("Failed to start the transaction : " 
                        + ex.getMessage(),ex);
                /*try {
                    session.disconnect();
                } catch (Exception ex2) {
                    log.error("Failed to force the disconnect : " + 
                            ex.getMessage(),ex);
                }*/
                
                /*try {
                    session.close();
                } catch (Exception ex2) {
                    log.error("Failed to force the close : " + 
                            ex.getMessage(),ex);
                }*/
                throw new XAException(
                        "Failed to start the transaction : " + ex.getMessage());
            }
        }
        this.currentSession.set(session);
    }
    
    
    /**
     * This method returns the transaction for this thread.
     *
     * @return The transaction for this thread.
     * @exception HibernateUtilException
     */
    private Transaction getTransaction() throws HibernateUtilException {
        try {
            TransactionManager transactionManager = (TransactionManager)
                    context.lookup("java:comp/TransactionManager");
            return transactionManager.getTransaction();
        } catch (Exception ex) {
            log.error("Failed to retrieve the current transaction because : " 
                    + ex.getMessage(),ex);
            // Make sure you log the exception, as it might be swallowed
            throw new HibernateUtilException(
                    "Failed to retrieve the current transaction because : " 
                    + ex.getMessage(),ex);
        }
    }
}
