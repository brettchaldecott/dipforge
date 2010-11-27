/*
 * <Add library description here>
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
 * NamedQueueImpl.java
 */

package com.rift.coad.daemon.messageservice.named;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageManager;
import com.rift.coad.daemon.messageservice.MessageQueue;
import com.rift.coad.daemon.messageservice.MessageQueueManager;
import com.rift.coad.daemon.messageservice.MessageServiceManagerMBean;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.MessageServiceManager;
import com.rift.coad.daemon.messageservice.NamedQueue;
import com.rift.coad.daemon.messageservice.TimeoutException;
import com.rift.coad.daemon.messageservice.message.MessageImpl;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.message.MessageManagerImpl;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The implementation of the Queue. This object represents a named database
 * queue.
 *
 * @author Brett Chaldecott
 */
public class NamedQueueImpl implements NamedQueue,ResourceIndex,Resource {
    
    // class constants
    private final static String TIMEOUT = "QUEUE_TIMEOUT";
    private final static long DEFAULT_TIMEOUT = 30000;
    
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(NamedQueueImpl.class.getName());
    
    
    // private member variables
    private String queueName = null;
    private long maxTimeout = DEFAULT_TIMEOUT;
    
    
    /**
     * The constructor of the queue.
     *
     * @param queueName The name of the queue.
     * @exception MessageServiceException
     */
    public NamedQueueImpl(String queueName) throws MessageServiceException {
        try {
            this.queueName = queueName;
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            maxTimeout = config.getLong(TIMEOUT,DEFAULT_TIMEOUT);
        } catch (Exception ex) {
            log.error("Failed to init the Named Queue : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to init the Named Queue : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a message for processing. If that message is not
     * acknowledged by the target with in a configured time it will be made
     * available for processing again.
     *
     * @return The reference to the Message for processing.
     * @param delay The delay before returning a null reference.
     * @exception RemoteException
     * @exception MessageServiceException
     * @exception TimeoutException
     */
    public Message receive(long delay) throws RemoteException,
            MessageServiceException, TimeoutException {
        try {
            return NamedMemoryQueue.getInstance(queueName).poll(delay);
        } catch (Throwable ex) {
            log.error("Failed to retrieve message : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to retrieve message : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a service to the list of services used to identify this
     * queue, by the service broker.
     *
     * @param service The string containing the service name.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void addService(String service) throws RemoteException,
            MessageServiceException {
        try {
            Session session = HibernateUtil.getInstance(
                    MessageServiceManager.class).getSession();
            com.rift.coad.daemon.messageservice.db.MessageQueue mq = 
                    (com.rift.coad.daemon.messageservice.db.MessageQueue)
                    session.get(com.rift.coad.daemon.messageservice.db.
                    MessageQueue.class,queueName);
            MessageQueueService messageQueueService = new MessageQueueService(
                    service,mq);
            session.persist(messageQueueService);
            ServiceBroker broker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    "ServiceBroker");
            List serviceList = new ArrayList();
            serviceList.add(service);
            broker.registerService(MessageServiceManagerMBean.JNDI_URL,serviceList);
        } catch (Exception ex) {
            log.error("Failed to add a service : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to add a service : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list of services used to identify this queue to the
     * service broker.
     *
     * @return The list of service used to identify this queue to the service
     *      broker.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public List listServices() throws RemoteException,
            MessageServiceException {
        try {
            Session session = HibernateUtil.getInstance(
                    MessageServiceManager.class).getSession();
            List entries = session.createQuery(
                    "SELECT mqs.service FROM MessageQueueService as mqs " +
                    "WHERE mqs.messageQueue.named = ?").
                    setString(0,this.queueName).list();
            List result = new ArrayList();
            for (int index = 0; index < entries.size(); index++) {
                result.add(((Object[])entries.get(index))[index]);
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to add a service : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to add a service : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes a service from the list of services.
     *
     * @param service The name of the service to remove.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void removeService(String service) throws RemoteException,
            MessageServiceException {
        try {
            Session session = HibernateUtil.getInstance(
                    MessageServiceManager.class).getSession();
            session.createQuery(
                    "DELETE FROM MessageQueueService as mqs " +
                    "WHERE mqs.service = ? AND mqs.messageQueue.named = ?").
                    setString(0,service).setString(1,this.queueName).
                    executeUpdate();
            ServiceBroker broker = (ServiceBroker)ConnectionManager.
                    getInstance().getConnection(ServiceBroker.class,
                    "ServiceBroker");
            List mqs = session.createQuery(
                    "FROM MessageQueueService as mqs " +
                    "WHERE mqs.service = ?").setString(0,service).list();
            if (mqs.size() == 0) {
                List serviceList = new ArrayList();
                serviceList.add(service);
                broker.removeServiceProviders(MessageServiceManagerMBean.JNDI_URL,
                        serviceList);
            }
        } catch (Exception ex) {
            log.error("Failed to remove a service : " + ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to remove a service : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the primary key of this resource to enable
     * indexing.
     *
     * @return The primary key of this object.
     */
    public Object getPrimaryKey() {
        return queueName;
    }
    
    
    /**
     * This method returns the name of the resource.
     *
     * @return The string containing the name of the resource.
     */
    public String getResourceName() {
        return queueName;
    }
    
    
    /**
     * This method will be called to release the resources controlled by
     * this object.
     *
     * @param This method adds a new resource.
     */
    public void releaseResource() {
        
    }
    
}
