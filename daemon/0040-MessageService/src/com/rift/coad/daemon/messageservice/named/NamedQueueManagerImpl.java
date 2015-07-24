/*
 * MessageService: The message service daemon
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
 * QueueManagerImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.named;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.transaction.Status;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.daemon.messageservice.*;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.bean.TransactionBeanCache;
import com.rift.coad.lib.cache.CacheRegistry;


/**
 * The implementation of the queue manager object.
 *
 * @author Brett Chaldecott
 */
public class NamedQueueManagerImpl implements QueueManager {
    
    
    // the queue manager singleton method
    private static NamedQueueManagerImpl singleton = null;
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(NamedQueueManagerImpl.class.getName());
    
    // private member variables
    private Map queues = new HashMap();
    
    /**
     * Creates a new instance of QueueManagerImpl
     */
    public NamedQueueManagerImpl() throws MessageServiceException {
        singleton = this;
    }
    
    
    /**
     * This method returns the queue specified by the name. If the queue does
     * not exist it gets created.
     *
     * @return The queue identified by the name.
     * @param name The name of the queue to retrieve.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public synchronized NamedQueue getNamedQueue(String name) throws RemoteException,
            MessageServiceException {
        try {
            Boolean value = (Boolean)queues.get(name);
            if (value != null) {
                if (value.booleanValue()) {
                    return new NamedQueueImpl(name);
                } else {
                    log.error("This is not a named queue [" + name +
                            "].");
                    throw new MessageServiceException
                            ("This is not a named queue [" + name +
                            "].");
                }
            }
            Session session = HibernateUtil.
                    getInstance(MessageServiceManager.class).getSession();
            List list = session.createQuery("FROM MessageQueue AS queue " +
                    "WHERE queue.messageQueueName = ?").setString(0,name).list();
            NamedQueueImpl queue = new NamedQueueImpl(name);
            if (list.size() == 1) {
                com.rift.coad.daemon.messageservice.db.MessageQueue dbQueue =
                        (com.rift.coad.daemon.messageservice.db.MessageQueue)
                        list.get(0);
                if ((dbQueue.getNamed() == null) ||
                        (dbQueue.getNamed() == 0)) {
                    addCheckEntry(name, false);
                    log.error("This is not a named queue [" + name +
                            "].");
                    throw new MessageServiceException
                            ("This is not a named queue [" + name +
                            "].");
                }
                addCheckEntry(name, true);
                return queue;
            }
            com.rift.coad.daemon.messageservice.db.MessageQueue dbQueue = new
                    com.rift.coad.daemon.messageservice.db.MessageQueue(name);
            dbQueue.setNamed(1);
            session.persist(dbQueue);
            addCheckEntry(name, true);
            return queue;
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the named message queue [" +
                    name + "] : " + ex.getMessage(),ex);
            throw new MessageServiceException
                    ("Failed to retrieve the named message queue [" +
                    name + "] : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns true if the queue with the specified name exists.
     *
     * @return TRUE if found, FALSE if not.
     * @param name The name of the queue to check for.
     * @exception MessageServiceException
     */
    public synchronized boolean checkForNamedQueue(String name, boolean create)
    throws MessageServiceException {
        try {
            Boolean value = (Boolean)queues.get(name);
            if (value != null) {
                return value.booleanValue();
            }
            Session session = HibernateUtil.
                    getInstance(MessageServiceManager.class).getSession();
            List list = session.createQuery("FROM MessageQueue AS queue " +
                    "WHERE queue.messageQueueName = ?").setString(0,name).list();
            if (list.size() == 1) {
                com.rift.coad.daemon.messageservice.db.MessageQueue queue =
                        (com.rift.coad.daemon.messageservice.db.MessageQueue)
                        list.get(0);
                if ((queue.getNamed() != null) && (queue.getNamed() == 1)) {
                    addCheckEntry(name, true);
                    return true;
                } else {
                    addCheckEntry(name, false);
                    return false;
                }
            } else if (create) {
                com.rift.coad.daemon.messageservice.db.MessageQueue dbQueue = new
                        com.rift.coad.daemon.messageservice.db.MessageQueue(name);
                dbQueue.setNamed(1);
                session.persist(dbQueue);
                addCheckEntry(name, true);
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("Failed to check for the named queue [" +
                    name + "] : " + ex.getMessage(),ex);
            throw new MessageServiceException
                    ("Failed to check for the named queue [" +
                    name + "] : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the singleton instance.
     *
     * @return A reference to the singleton instance.
     * @exception MessageServiceException
     */
    public static synchronized NamedQueueManagerImpl getInstance() throws
            MessageServiceException {
        if (singleton == null) {
            throw new MessageServiceException(
                    "Message service singleton not initialized");
        }
        return singleton;
    }
    
    
    /**
     * This method addes a check flag value to the memory queues list
     *
     * @param name The name to add.
     * @praam the type of queue. TRUE if named
     */
    private void addCheckEntry(String name, boolean queueType) {
        queues.put(name,new Boolean(queueType));
    }
}
