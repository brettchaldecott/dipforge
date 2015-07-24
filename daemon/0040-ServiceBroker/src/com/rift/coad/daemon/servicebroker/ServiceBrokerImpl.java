/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * ServiceBrokerImpl.java
 */

package com.rift.coad.daemon.servicebroker;

// local imports
import com.rift.coad.daemon.servicebroker.db.Service;
import com.rift.coad.daemon.servicebroker.db.ServicePK;
//import com.rift.coad.daemon.servicebroker.db.util.HibernateUtil;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.ConfigurationException;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.naming.NamingConstants;
import com.rift.coad.lib.thread.ThreadStateMonitor;

// java imports
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

// log4j import
import org.apache.log4j.Logger;

// hibernate import
import org.hibernate.Session;

/**
 *
 * @author Glynn Chaldecott
 */
public class ServiceBrokerImpl implements ServiceBroker, BeanRunnable {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(ServiceBrokerImpl.class.getName());
    
    private Context ctx = null;
    
    private ThreadStateMonitor state = null;
    
    private ConcurrentLinkedQueue serviceQueue = new ConcurrentLinkedQueue();
    
    /** Creates a new instance of ServiceBrokerImpl */
    public ServiceBrokerImpl() throws NamingException, ServiceBrokerException {
        ctx = new InitialContext();
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig = 
                        com.rift.coad.lib.configuration.ConfigurationFactory.
                        getInstance().getConfig(com.rift.coad.daemon.servicebroker.
                        ServiceBrokerImpl.class);
            state = new ThreadStateMonitor(coadConfig.getLong("sleep_time"));
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     *
     * @param JNDI This is the JNDI of the daemon that a service will be
     *          linked to.
     * @param services This is a list of the services that a daemon can perform.
     */
    public void registerService(String JNDI, List services) throws
            RemoteException, ServiceBrokerException {
        boolean ownTransaction = false;
        
        try {
            if (!NamingDirector.getInstance().isPrimary()) {
                sendToParent(JNDI, services);
            }
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.servicebroker.
                                ServiceBrokerImpl.class)
                        .getSession();
            for (int i = 0; i < services.size(); i ++) {
                String temp = (String) services.get(i);
                List list = session.createSQLQuery("SELECT * FROM Service WHERE"
                        + " jndi = '" + JNDI + "' AND service = '"
                        + temp + "'").list();
                if (list.size() == 0) {
                    ServicePK servicePK = new ServicePK(JNDI,temp);
                    Service service = new Service(servicePK,0);
                    session.save(service);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to register service :"
                    + ex.getMessage(),ex);
            throw new ServiceBrokerException("Failed to register service :"
                    + ex.getMessage());
        }
    }
    
    private void sendToParent(String JNDI, List services) {
        try {
            Object obj = ctx.lookup(NamingDirector.getInstance().
                    getPrimaryJNDIUrl() + "/ServiceBroker");
            com.rift.coad.daemon.servicebroker.ServiceBroker
                    beanInterface = (com.rift.coad.daemon.servicebroker
                    .ServiceBroker)
                    PortableRemoteObject.narrow(obj,com.rift.coad.
                    daemon.servicebroker.ServiceBroker.class);
            beanInterface.registerService(
                    NamingDirector.getInstance().getJNDIBase() + "/" + JNDI,
                    services);
        } catch(Exception ex) {
            log.error("Failed to pass service to parent:"
                    + ex.getMessage(),ex);
            List temp = new ArrayList();
            temp.add(JNDI);
            temp.add(services);
            serviceQueue.offer(temp);
        }
    }
    
    /**
     *
     * @param services This is a list of the services you wish to access.
     * @return This method will return a String containing the JNDI of the
     *          Daemon linked to the service.
     */
    public String getServiceProvider(List services) throws
            RemoteException, ServiceBrokerException {
        boolean ownTransaction = false;
        String returnValue = "";
        try {
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.servicebroker.
                                ServiceBrokerImpl.class)
                        .getSession();
            
            String query = "SELECT DISTINCT jndi,service,counter FROM Service" +
                    " WHERE ";
            String orVal = "";
            
            for (int i = 0; i < services.size(); i ++) {
                query += orVal + "service = '" + (String) services.get(i) + "' ";
                orVal = "or ";
            }
            
            List list = session.createSQLQuery(query + "ORDER BY counter ASC")
            .list();
            
            Object[] row = (Object[]) list.get(0);
            String JNDI = (String) row[0];
            String service = (String) row[1];
            int counter = Integer.parseInt(row[2].toString());
            counter ++;
            
            int update = session.createSQLQuery("UPDATE Service SET counter = "
                    + counter + " WHERE jndi = '" + JNDI + "' AND service = '"
                    + service + "'").executeUpdate();
            
            returnValue = JNDI;
            
            
        } catch (Exception ex) {
            log.error("Failed to retrieve single service :"
                    + ex.getMessage(),ex);
            throw new ServiceBrokerException("Failed to retrieve single " +
                    "service :" + ex.getMessage());
        }
        return returnValue;
    }
    
    /**
     *
     * @param services This is a List of the services' JNDI's you wish to
     *          retrieve from the database.
     * @return This method returns the JNDI for multiple daemons.
     */
    public List getServiceProviders(List services) throws
            RemoteException, ServiceBrokerException {
        boolean ownTransaction = false;
        List returnValue = null;
        try {
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.servicebroker.
                                ServiceBrokerImpl.class)
                        .getSession();
            
            String query = "SELECT DISTINCT jndi FROM Service WHERE ";
            String orVal = "";
            
            for (int i = 0; i < services.size(); i ++) {
                query += orVal + "service = '" + (String) services.get(i) + "' ";
                orVal = "or ";
            }
            
            returnValue = session.createSQLQuery(query).list();
            
            
        } catch (Exception ex) {
            log.error("Failed to retrieve multiple services :"
                    + ex.getMessage(),ex);
            throw new ServiceBrokerException("Failed to retrieve multiple" +
                    " services :" + ex.getMessage());
        }
        return returnValue;
    }
    
    /**
     *
     *
     * @param JNDI This is the JNDI of the service you want to delete.
     * @param services This is a List containing the services linked to the 
     *          daemon you wish to delete.
     */
    public void removeServiceProviders(String JNDI, List services) throws
            RemoteException, ServiceBrokerException {
        UserTransaction ut = null;
        boolean ownTransaction = false;
        try {
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.servicebroker.
                                ServiceBrokerImpl.class)
                        .getSession();
            
            for (int i = 0; i < services.size(); i ++) {
                String temp = (String) services.get(i);
                session.createQuery("DELETE FROM Service as ser WHERE " +
                        "ser.comp_id.service = ? AND ser.comp_id.jndi = ?")
                        .setString(0,temp).setString(1,JNDI).executeUpdate();
            }
            
            
        } catch (Exception ex) {
            log.error("Failed to delete the service :"
                    + ex.getMessage(),ex);
            throw new ServiceBrokerException("Failed to delete the service :"
                    + ex.getMessage());
        }
    }
    
    
    /**
     * This method is run to register services with the parent.
     */
    public void process() {
        while (!state.isTerminated()) {
            List temp = (List) serviceQueue.poll();
            if (temp != null) {
                String JNDI = (String) temp.get(0);
                List services = (List) temp.get(1);
                try {
                    sendToParent(JNDI, services);
                } catch (Exception ex) {
                    log.error("There was an error registering to the " +
                            "parent:" + ex.getMessage(),ex);
                }
            }
            state.monitor();
        }
    }

    public void terminate() {
        state.terminate(true);
    }
    
    
}
