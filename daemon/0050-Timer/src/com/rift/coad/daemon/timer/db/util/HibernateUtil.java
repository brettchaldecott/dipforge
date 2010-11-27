/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  Rift IT Contracting
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

package com.rift.coad.daemon.timer.db.util;

//coadunation imports
import com.rift.coad.daemon.timer.TimerException;
import com.rift.coad.lib.configuration.*;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * This class sets up a hibernate SessionFactory.
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    /**
     * Configures up hibernate programmatically using Coadunations configuration
     * file.
     */
    public static void init() throws TimerException {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            com.rift.coad.lib.configuration.Configuration coadConfig = 
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance()
                    .getConfig(com.rift.coad.daemon.timer.TimerImpl.class);
            org.hibernate.cfg.Configuration config = new 
                    org.hibernate.cfg.Configuration()
            .addResource("com/rift/coad/daemon/timer/db/Schedule.hbm.xml")
            .setProperty("hibernate.dialect",coadConfig.getString("db_dialect"))
            .setProperty("hibernate.connection.datasource",
                    coadConfig.getString("db_datasource"))
            .setProperty("hibernate.current_session_context_class","jta")
            .setProperty("hibernate.transaction.factory_class",
                    "org.hibernate.transaction.JTATransactionFactory")
            .setProperty("hibernate.transaction.manager_lookup_class",
                    "org.hibernate.transaction.JOTMTransactionManagerLookup")
            .setProperty("hibernate.cache.provider_class",
                    "org.hibernate.cache.NoCacheProvider")
            .setProperty("hibernate.show_sql",
                    coadConfig.getString("hibernate_sql","false"))
            .setProperty("hibernate.hbm2ddl.auto",
                    coadConfig.getString("hibernate_hbm2ddl_auto","update"));
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            throw new TimerException("Initial SessionFactory creation failed: " 
                    + ex);
        }
    }
    
    /**
     * @return Returns the Hibernate SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}